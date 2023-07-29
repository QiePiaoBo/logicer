package com.dylan.file.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Classname FileUploadDTO
 * @Description FileUploadDTO
 * @Date 9/15/2022 6:06 PM
 */
public class FileStorageDTO {

    private MultipartFile file;


    public FileStorageDTO() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
