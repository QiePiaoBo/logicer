package com.dylan.blog.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (Article)实体类
 *
 * @author Dylan
 * @since 2020-06-14 20:24:19
 */
public class Article implements Serializable {
    private static final long serialVersionUID = -66199659751436277L;
    /**
    * 文章主键
    */
    private Integer id;
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
    * 文章路径
    */
    private String filePath;
    /**
    * 作者id
    */
    private Integer userId;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    *  是否禁止访问(管理员)
    */
    private Integer isDel;
    /**
    * 是否进行展示(作者)
    */
    private Integer isLock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }
}