package com.dylan.blog.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Classname MsgRecordEntity
 * @Description MsgRecordEntity
 * @Date 6/20/2023 5:14 PM
 */
public class ConfettiQueryModel implements Serializable {

    private Integer id;

    private Integer userId;

    /**
     * 标题
     */
    private String title;

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

    /**
     * QueryModel要求三个参数必须有一个值不为空
     * @return
     */
    public boolean isValid() {
        if (Objects.isNull(getId()) && Objects.isNull(getUserId()) && StringUtils.isBlank(title)){
            return false;
        }
        return true;
    }

    public String getCacheKey(){
        StringBuilder sb = new StringBuilder("confettiQueryModel_cache_key");
        if (Objects.nonNull(id)){
            sb.append(id).append("_");
        }
        if (Objects.nonNull(userId)){
            sb.append(userId).append("_");
        }
        if (StringUtils.isNotBlank(title)){
            sb.append(title).append("_");
        }
        return sb.toString();
    }
}
