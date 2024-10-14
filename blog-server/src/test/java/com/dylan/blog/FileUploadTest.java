package com.dylan.blog;

import com.dylan.file.entity.FileStorage;
import com.dylan.file.service.FileStorageService;
import com.dylan.framework.file.service.FileUploadService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@SpringBootTest
public class FileUploadTest {

    @Resource
    private FileUploadService fileUploadService;
    @Resource
    private FileStorageService fileStorageService;


    @Test
    public void testUploadFile() throws IOException {

        File file = new File("C:\\Users\\Dylan\\Desktop\\TestOSSUpload.md"); // 修正文件路径
        FileInputStream fileInputStream = new FileInputStream(file);

        // 创建MockMultipartFile，三个参数分别是：文件名称、文件原始名称、文件内容类型（可以为null）和文件的字节流
        MultipartFile multipartFile = new MockMultipartFile(file.getName(),
                file.getName(), "text/plain", fileInputStream);
        String upload2OSS = fileUploadService.upload2OSS(multipartFile);
        log.info("res: {}", upload2OSS);
    }

    @Test
    public void testUploadAndSave() throws IOException {

        // 指定要读取的目录路径
        String directoryPath = "C:\\Users\\Dylan\\Desktop\\temp\\qiniu";

        // 创建File对象
        File directory = new File(directoryPath);

        // 判断该路径是否是一个目录
        if (directory.isDirectory()) {
            // 获取该目录下的所有文件和子目录
            File[] files = directory.listFiles();
            // 判断目录是否为空
            if (files != null && files.length > 0) {
                // 遍历所有文件和子目录
                for (File file : files) {
                    FileInputStream fileInputStream = new FileInputStream(file);

                    // 创建MockMultipartFile，三个参数分别是：文件名称、文件原始名称、文件内容类型（可以为null）和文件的字节流
                    MultipartFile multipartFile = new MockMultipartFile(file.getName(),
                            file.getName(), "text/plain", fileInputStream);
                    log.info("multipart file name : {}",multipartFile.getName());
                    if (multipartFile.getName().contains("README")) {
                        String fileOssName = fileUploadService.upload2OSS(multipartFile);
                        FileStorage fileStorage  = fileStorageService.insertByOSSFileName(fileOssName);
                        log.info("res: {}", fileStorage);
                    }
                }
            }

        }

        File file = new File("C:\\Users\\Dylan\\Desktop\\TestOSSUpload.md"); // 修正文件路径


    }

    @Test
    public void testFileUrl() {

        String fileUrl = fileStorageService.selectFileUrlById(1);
    }

}
