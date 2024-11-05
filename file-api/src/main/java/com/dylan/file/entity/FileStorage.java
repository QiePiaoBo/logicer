package com.dylan.file.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 文件对象存储信息
 * @Classname FileUpload
 * @Description FileUpload
 * @Date 9/19/2022 6:40 PM
 */
public class FileStorage implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer type;

    private String uploadInfo;

    private String fileName;

    private String fileUri;

    private Long fileSize;

    private String hash;

    private String bucket;

    private Boolean delFlag;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public FileStorage() {
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUploadInfo() {
        return uploadInfo;
    }

    public void setUploadInfo(String uploadInfo) {
        this.uploadInfo = uploadInfo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
