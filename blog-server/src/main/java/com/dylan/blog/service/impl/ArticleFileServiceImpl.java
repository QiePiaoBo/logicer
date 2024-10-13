package com.dylan.blog.service.impl;

import com.dylan.blog.dto.ArticleDto;
import com.dylan.blog.entity.Article;
import com.dylan.blog.service.ArticleFileService;
import com.dylan.blog.service.ArticleService;
import com.dylan.file.entity.FileStorage;
import com.dylan.file.service.FileStorageService;
import com.dylan.framework.constant.FileConstant;
import com.dylan.framework.model.constants.FileUploadConstants;
import com.dylan.framework.model.exception.MyException;
import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.vo.PersonVo;
import com.dylan.framework.file.service.FileUploadService;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.qiniu.http.Response;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;

/**
 * 文件接口实现
 *
 * @author Dylan
 */
@Service
public class ArticleFileServiceImpl implements ArticleFileService {

    private static final MyLogger log = MyLoggerFactory.getLogger(ArticleFileServiceImpl.class);

    @Resource
    LicService licService;
    @Resource
    ArticleService articleService;
    @Resource
    private FileUploadService fileUploadService;
    @Resource
    private FileStorageService fileStorageService;


    /**
     * 上传文件
     *
     * @param articleDto
     * @param uploadWhere
     * @return
     */
    @Override
    public DataResult uploadFile(ArticleDto articleDto, String uploadWhere) {
        // 获取传来的文件
        MultipartFile multipartFile = articleDto.getFile();
        if (Objects.isNull(multipartFile) || Objects.isNull(multipartFile.getOriginalFilename())) {
            return DataResult.getBuilder(Status.ERROR_BASE.getStatus(), Message.ERROR.getMsg()).build();
        }
        // 设置最大大小
        long maxSize = 52428800;
        long size = multipartFile.getSize();
        Integer fileId = null;
        // 文件大小是否超过最大大小。
        if (size == 0 || size > maxSize) {
            return DataResult.getBuilder(Status.OUTOF_SIZE_ERROR.getStatus(), Message.OUTOF_SIZE_ERROR.getMsg()).build();
        }
        try {
            if (Objects.equals(FileUploadConstants.FILE_UPLOAD_PLACE_QINIU, uploadWhere)) {
                // 上传至七牛云
                Response response = fileUploadService.upload2QiNiu(multipartFile);
                if (Objects.nonNull(response) && response.isOK()){
                    log.info("response: {}", response);
                    String fileURI = FileConstant.QINIU_FILE_PREFIX + multipartFile.getName();
                    String responseInfo = response.getInfo();
                    log.info("fileURI: {}, responseInfo: {}", fileURI, responseInfo);
                    FileStorage fileStorage = fileStorageService.insertByQiNiuResponse(response.getInfo());
                    if (Objects.nonNull(fileStorage)) {
                        fileId = fileStorage.getId();
                    }
                }
            }else if (Objects.equals(FileUploadConstants.FILE_UPLOAD_PLACE_ALIYUN_OSS, uploadWhere)) {
                String fileOssName = fileUploadService.upload2OSS(multipartFile);
                FileStorage fileStorage  = fileStorageService.insertByOSSFileName(fileOssName);
                if (Objects.nonNull(fileStorage)) {
                    fileId = fileStorage.getId();
                }
            }else {
                throw new Exception("Invalid upload place!");
            }
        }catch (Exception e) {
            // 上传至服务器
            String serverFilePath = fileUploadService.upload2Server(multipartFile);
            FileStorage fileStorage = fileStorageService.insertByOSFilePath(serverFilePath);
            if (Objects.nonNull(fileStorage)) {
                fileId = fileStorage.getId();
            }
        }
        if (Objects.isNull(fileId)) {
            throw new MyException("Error uploading and save file info!");
        }
        // 入库并返回结果
        return this.insertToDatabase(fileId, articleDto);
    }


    /**
     * 入库 并返回各种情况的结果
     * @param fileId
     * @param articleDto
     * @return
     */
    private DataResult insertToDatabase(Integer fileId, ArticleDto articleDto) {
        // 插入数据库
        PersonVo currentUser = licService.getUser();
        Article article = new Article();
        if (currentUser != null) {
            BeanUtils.copyProperties(articleDto, article);
            // 默认不封
            article.setDelFlag(0);
            // 设置创建时间
            article.setCreateTime(new Date());
            // 设置作者id为上传者的id
            article.setUserId(currentUser.getId());
            // 设置真实文件路径
            article.setFilePath("FILE_UPLOAD_URL://" + fileId);
            // 设置文件上传id
            article.setFileId(fileId);
            // 设置文章是否显示
            article.setIsLock(articleDto.getIsLock() != null ? articleDto.getIsLock() : 0);
        } else {
            return DataResult.getBuilder(Status.PERMISSION_ERROR.getStatus(), Message.PERMISSION_ERROR.getMsg()).build();
        }
        // 插入的结果是否为空
        Article res = articleService.insert(article);
        if (res != null) {
            // 判断上传至七牛云是否成功
            return DataResult.getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg()).data(res).build();
        }
        return DataResult.getBuilder(Status.INSERT_ERROR.getStatus(), Message.INSERT_ERROR.getMsg()).data(res).build();
    }
}