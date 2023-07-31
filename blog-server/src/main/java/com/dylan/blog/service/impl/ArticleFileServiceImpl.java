package com.dylan.blog.service.impl;

import com.dylan.blog.config.BlogConstants;
import com.dylan.blog.entity.Article;
import com.dylan.blog.dto.ArticleDto;
import com.dylan.blog.service.ArticleFileService;
import com.dylan.blog.service.ArticleService;
import com.dylan.file.filesdk.QiNiuSdk;
import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.vo.PersonVo;
import com.dylan.framework.sysinfo.SysInfo;
import com.dylan.framework.utils.FileUtils;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件接口实现
 * @author Dylan
 */
@Service
public class ArticleFileServiceImpl implements ArticleFileService {

    private static final MyLogger log = MyLoggerFactory.getLogger(ArticleFileServiceImpl.class);

    @Resource
    LicService licService;
    @Resource
    ArticleService articleService;

    @Value("${qiniu.accesskey}")
    String accessKey;
    @Value("${qiniu.secretkey}")
    String secretKey;
    @Value("${qiniu.bucketname}")
    String bucketName;

    /**
     * 上传文件 布尔值控制是否上传至七牛云
     * @param articleDto
     * @param uploadWhere
     * @return
     */
    @Override
    public DataResult uploadFile(ArticleDto articleDto, String uploadWhere){
        // 获取传来的文件
        MultipartFile multipartFile = articleDto.getFile();
        // 设置最大大小
        long maxSize = 52428800;
        long size= multipartFile.getSize();
        String filepath = "";
        // 文件大小是否超过最大大小。
        if(size == 0 || size > maxSize){
            return DataResult.getBuilder(Status.OUTOF_SIZE_ERROR.getStatus(), Message.OUTOF_SIZE_ERROR.getMsg()).build();
        }
        // 文件的名字
        String name=multipartFile.getOriginalFilename();
        // 取得文件后缀
        String subffix = name.substring(name.lastIndexOf("."));
        // 文件保存进来，我给他重新命名，新名字存入数据库。
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + name;
        // 文件子目录
        String sonPath = new SimpleDateFormat("yyyyMM").format(new Date());
        Response response = null;
        if ("qiniu".equals(uploadWhere)){
            // 上传至七牛云
            try {
                response = this.upload2QiNiu(FileUtils.multi2File(multipartFile));
                filepath = "http://pic.logicer.top/" + multipartFile.getOriginalFilename();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            // 上传至服务器
            filepath = upload2Server(subffix, sonPath, fileName);
        }
        // 入库并返回结果
        return this.insertToDatabase(response, filepath, articleDto);
    }

    /**
     * 上传到七牛云
     * @param file
     * @return
     */
    public Response upload2QiNiu(File file){
        Response response = null;
        try {
            response = QiNiuSdk.uploadToQiniu(file, accessKey, secretKey, bucketName);
        }catch (QiniuException e){
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 上传到服务器
     * @param subffix
     * @param sonPath
     * @param fileName
     */
    public String  upload2Server(String subffix, String sonPath, String fileName){
        // 判断是否是windows平台
        boolean isWindows = SysInfo.isWindows();
        String filepath = "";
        // 如果上传至服务器，则根据服务器所处平台更改文件存储位置
        if (isWindows){
            // 获取文件存放位置
            filepath = BlogConstants.WINDOWS_FILE_DIR + subffix.substring(1) + "\\" + sonPath + "\\";
            // 文件全路径拼接上文件名
            filepath += fileName;
            File winFile = new File(filepath);
            // 目录不存在就创建
            if(!winFile.exists()){
                boolean mkWinDirs = winFile.mkdirs();
                log.info(mkWinDirs ? "created a path" : "do not need to create");
            }
        } else {
            // 获取文件存放位置
            filepath = BlogConstants.LINUX_FILE_DIR + subffix.substring(1) + "/" + sonPath + "/";
            // 文件全路径拼接上文件名
            filepath += fileName;
            File linuxFile = new File(filepath);
            // 目录不存在就创建
            if(!linuxFile.exists()){
                boolean mkLinuxDirs = linuxFile.mkdirs();
                log.info(mkLinuxDirs ? "created a path" : "do not need to create");
            }
        }
        return filepath;
    }

    /**
     * 入库 并返回各种情况的结果
     * @param response
     * @param filePath
     * @param articleDto
     * @return
     */
    private DataResult insertToDatabase(Response response, String filePath, ArticleDto articleDto){
        // 插入数据库
        PersonVo currentUser = licService.getUser();
        Article article = new Article();
        if (currentUser != null){
            BeanUtils.copyProperties(articleDto, article);
            // 默认不封
            article.setIsDel(0);
            // 设置创建时间
            article.setCreateTime(new Date());
            // 设置作者id为上传者的id
            article.setUserId(currentUser.getId());
            // 设置真实文件路径
            article.setFilePath(filePath);
            // 设置文章是否显示
            article.setIsLock(articleDto.getIsLock()!=null ? articleDto.getIsLock():0);
        } else{
            return DataResult.getBuilder(Status.PERMISSION_ERROR.getStatus(), Message.PERMISSION_ERROR.getMsg()).build();
        }
        // 插入的结果是否为空
        Article res = articleService.insert(article);
        if (res != null){
            // 判断上传至七牛云是否成功
            if (response != null){
                if (response.error != null){
                    return DataResult.getBuilder(Status.UPLOAD_ERROR.getStatus(), Message.UPLOAD_ERROR.getMsg()).data(response.error).build();
                }
                return DataResult.getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg()).data(res).build();
            }else {
                try {
                    // 检查文件是否存在
                    articleDto.getFile().transferTo(new File(filePath));
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return DataResult.getBuilder(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg()).data(res).build();
        }
        return DataResult.getBuilder(Status.INSERT_ERROR.getStatus(), Message.INSERT_ERROR.getMsg()).data(res).build();
    }
}