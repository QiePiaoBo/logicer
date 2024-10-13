package com.dylan.blog;

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


}
