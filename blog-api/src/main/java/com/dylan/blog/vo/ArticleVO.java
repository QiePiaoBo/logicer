package com.dylan.blog.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname ArticleVO
 * @Description ArticleVO
 * @Date 8/31/2023 6:05 PM
 */
public class ArticleVO implements Serializable {
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
     * 创建时间
     */
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
