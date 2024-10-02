package com.dylan.blog;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.util.Date;

@Slf4j
@SpringBootTest
public class AlibabaTest {

    @Value("${alibaba.alibaba_cloud_access_key_id}")
    private String alibabaCloudAccessKeyId;

    @Value("${alibaba.alibaba_cloud_access_key_secret}")
    private String alibabaCloudAccessKeySecret;

    @Resource
    private OSS ossClientBeijing;

    @Test
    public void testAK() throws IOException {
        log.info("accessKeyId: {}, accessKeySecret: {}", alibabaCloudAccessKeyId, alibabaCloudAccessKeySecret);
    }

    @Test
    public void testBucketCreate() {
        try {
            ossClientBeijing.createBucket("test-bucket-from-code");
        }catch (OSSException oe) {
            log.error("oe message: {}", oe.getMessage(), oe);
        }
    }

    @Test
    public void testBucketFileUpload() {
        try {
            File file2Upload = new File("C:\\Users\\Dylan\\Desktop\\TestOSSUpload.md"); // 修正文件路径
            // 3. 上传文件流到OSS
            PutObjectResult putObjectResult = ossClientBeijing.putObject("test-bucket-from-code", "TestOSSUpload.md", new FileInputStream(file2Upload));
            log.info("res: {}", putObjectResult);

        }catch (OSSException | FileNotFoundException oe) {
            log.error("oe message: {}", oe.getMessage(), oe);
        }
    }

    @Test
    public void testGeneratePresignedUrl() {
        try {
            // 设置签名URL过期时间，单位为毫秒。本示例以设置过期时间为1小时为例。
            Date expiration = new Date(new Date().getTime() + 180 * 1000L);
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            URL url = ossClientBeijing.generatePresignedUrl("test-bucket-from-code", "TestOSSUpload.md", expiration);
            log.info("Url: {}", url);
        }catch (OSSException oe) {
            log.error("oe message: {}", oe.getMessage(), oe);
        }
    }



}
