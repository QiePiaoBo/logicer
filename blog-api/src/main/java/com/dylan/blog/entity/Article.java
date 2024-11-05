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
    private String title;
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
    private Integer fileId;
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
     * 是否禁止访问(管理员)
     */
    private Integer delFlag;
    /**
     * 是否进行展示(作者)
     */
    private Integer isLock;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getCacheKey() {
        final StringBuilder sb = new StringBuilder("article_cache_key");
        if (id != null) {
            sb.append(id).append("_");
        }
        if (title != null) {
            sb.append(title).append("_");
        }
        if (subTitle != null) {
            sb.append(subTitle).append("_");
        }
        if (description != null) {
            sb.append(description).append("_");
        }
        if (fileType != null) {
            sb.append(fileType).append("_");
        }
        if (fileId != null) {
            sb.append(fileId).append("_");
        }
        if (userId != null) {
            sb.append(userId).append("_");
        }
        if (createTime != null) {
            sb.append(createTime).append("_");
        }
        sb.append(0).append("_");
        sb.append(0);
        return sb.toString();

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"id\":")
                .append(id)
                .append(", \"title\":")
                .append(title)
                .append(", \"subTitle\":")
                .append(subTitle)
                .append(", \"description\":")
                .append(description)
                .append(", \"fileType\":")
                .append(fileType)
                .append(", \"filePath\":")
                .append(fileId)
                .append(", \"userId\":")
                .append(userId)
                .append(", \"createTime\":")
                .append(createTime)
                .append(", \"isDel\":")
                .append(delFlag)
                .append(", \"isLock\":")
                .append(isLock)
                .append('}');
        return sb.toString();
    }
}