package com.dylan.framework.file.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.StringUtils;
import com.aliyun.oss.model.PutObjectResult;
import com.dylan.framework.model.constants.FileUploadConstants;
import com.dylan.framework.sysinfo.SysInfo;
import com.dylan.framework.utils.FileUtils;
import com.dylan.framework.utils.QiNiuUtil;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.qiniu.http.Response;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


@Service
public class FileUploadService {

    private static final MyLogger log = MyLoggerFactory.getLogger(FileUploadService.class);


    @Value("${qiniu.accesskey}")
    String accessKey;
    @Value("${qiniu.secretkey}")
    String secretKey;
    @Value("${qiniu.bucketname}")
    String bucketName;

    @Resource
    private OSS ossClientBeijing;


    /**
     * 上传到七牛云
     *
     * @param multipartFile
     * @return
     */
    public Response upload2QiNiu(MultipartFile multipartFile) {
        Response response = null;
        try {
            File file = FileUtils.multi2File(multipartFile);
            if (Objects.nonNull(file)) {
                response = QiNiuUtil.uploadToQiniu(file, accessKey, secretKey, bucketName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * 上传到服务器
     * @param multipartFile
     * @return
     */
    public String upload2Server(MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile) || StringUtils.isNullOrEmpty(multipartFile.getOriginalFilename())) {
            return null;
        }
        // 文件的名字
        String name = multipartFile.getOriginalFilename();
        // 取得文件后缀
        String suffix = name.substring(name.lastIndexOf("."));
        // 文件保存进来，我给他重新命名，新名字存入数据库。
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + name;
        String sonPath = new SimpleDateFormat("yyyyMM").format(new Date());

        // 判断是否是windows平台
        boolean isWindows = SysInfo.isWindows();
        String filepath = "";
        // 如果上传至服务器，则根据服务器所处平台更改文件存储位置
        if (isWindows) {
            // 获取文件存放位置
            filepath = FileUploadConstants.WINDOWS_FILE_DIR + suffix.substring(1) + "\\" + sonPath + "\\";
            // 文件全路径拼接上文件名
            filepath += fileName;
            File winFile = new File(filepath);
            // 目录不存在就创建
            if (!winFile.exists()) {
                boolean mkWinDirs = winFile.mkdirs();
                log.info(mkWinDirs ? "created a path" : "do not need to create");
            }
        } else {
            // 获取文件存放位置
            filepath = FileUploadConstants.LINUX_FILE_DIR + suffix.substring(1) + "/" + sonPath + "/";
            // 文件全路径拼接上文件名
            filepath += fileName;
            File linuxFile = new File(filepath);
            // 目录不存在就创建
            if (!linuxFile.exists()) {
                boolean mkLinuxDirs = linuxFile.mkdirs();
                log.info(mkLinuxDirs ? "created a path" : "do not need to create");
            }
        }
        return filepath;
    }

    /**
     * 上传文件到aliyun oss
     * @param multipartFile
     * @return
     */
    public String upload2OSS(MultipartFile multipartFile) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        stringBuilder.append("_");
        stringBuilder.append(multipartFile.getOriginalFilename());
        String ossUploadFileName = new String(stringBuilder);
        boolean bucketExist = ossClientBeijing.doesBucketExist(FileUploadConstants.OSS_BUCKET_NAME_LOGICER);
        if (!bucketExist) {
            ossClientBeijing.createBucket(FileUploadConstants.OSS_BUCKET_NAME_LOGICER);
        }
        try {
            // 上传文件流到OSS
            PutObjectResult putObjectResult = ossClientBeijing.putObject(FileUploadConstants.OSS_BUCKET_NAME_LOGICER, ossUploadFileName, multipartFile.getInputStream());
            log.info("Oss upload res: {}", putObjectResult);
        }catch (OSSException | FileNotFoundException oe) {
            log.error("oe message: {}", oe.getMessage(), oe);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ossUploadFileName;
    }

}
