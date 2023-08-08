package com.dylan.blog.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Classname MsgRecordEntity
 * @Description MsgRecordEntity
 * @Date 6/20/2023 5:14 PM
 */
public class ConfettiInsertModel implements Serializable {

    private Integer id;

    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 插入时校验参数是否正常
     * @return
     */
    public boolean insertValid() {
        if (Objects.nonNull(getId())){
            // 插入时不可以指定Id
            return false;
        }
        // 用户Id不可为空 标题和内容不可为空
        return !Objects.isNull(getUserId()) && !StringUtils.isBlank(getTitle()) && !StringUtils.isBlank(getContent());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
