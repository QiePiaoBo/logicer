package com.dylan.blog.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 输入参数
 * @author Dylan
 */
public class ArticleDto implements Serializable {
    /**
     * 文件本体
     */
    private MultipartFile file;
    /**
     * 文章名
     */
    private String fileName;
    /**
     * 文章二级标题
     */
    private String subTitle;
    /**
     * 文章内容梗概
     */
    private String description;
    /**
     * 文章类型
     */
    private String fileType;
    /**
     * 是否进行展示(作者)
     */
    private Integer isLock;

    /**
     * 文件发送到哪去
     */
    private String sendPlace;

    /**
     * 作者id
     */
    private int authorId;
    /**
     * 创建时间
     */
    private String createTime;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getSendPlace() {
        return sendPlace;
    }

    public void setSendPlace(String sendPlace) {
        this.sendPlace = sendPlace;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
