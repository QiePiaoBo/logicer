package com.dylan.framework.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Dylan
 * @Date : Created in 15:01 2020/8/21
 * @Description :
 * @Function :
 */
public class QiNiuUtil {
    private static final long expireSeconds = 3600;

    /**
     * 上传文件到七牛云
     * @param file
     * @param accessKey
     * @param secretKey
     * @param bucketName
     * @return
     * @throws QiniuException
     */
    public static Response uploadToQiniu(File file, String accessKey, String secretKey, String bucketName) throws QiniuException {
        // 默认本地文件名作为上传的文件名
        String fileName = file.getName();
        // 自定义返回格式
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        // 凭证管理器 uploadToken的第二个参数null
        Auth auth = Auth.create(accessKey, secretKey);
        String accessToken = auth.uploadToken(bucketName, null, expireSeconds, putPolicy);
        // 上传配置，目前只设置了存储地址
        Configuration configuration = new Configuration(Region.autoRegion());
        // 上传管理器
        UploadManager uploadManager = new UploadManager(configuration);
        // 上传文件至七牛 第一个参数是文件，第二个是存储时的名字，第三个是存储空间的token
        return uploadManager.put(file, fileName, accessToken);
    }


    /**
     * 查询空间文件列表
     * @param bucketName
     * @param prefix
     * @param accessKey
     * @param secretKey
     * @param delimiter
     * @return
     */
    public static List<FileInfo> queryFileList(String bucketName, String prefix, String accessKey, String secretKey, String delimiter){
        List<FileInfo> fileInfos = new ArrayList<>();
        Configuration cfg = new Configuration(Region.region0());
        // 凭证管理器 uploadToken的第二个参数null
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        // 每次查询的长度限制 最大1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        delimiter = (Objects.isNull(delimiter) || delimiter.length() == 0) ? "" : delimiter;
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucketName, prefix, limit, delimiter);
        while (fileListIterator.hasNext()){
            FileInfo[] next = fileListIterator.next();
            fileInfos.addAll(Arrays.asList(next));
        }
        return fileInfos;
    }

}
