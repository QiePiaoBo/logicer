package com.dylan.blog.vo;


import java.io.Serializable;

public class ConfettiVO implements Serializable {

    private Integer id;

    private Integer userId;

    private String userName;

    private String title;

    private String content;

    private Integer lockFlag;

    public Integer getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(Integer lockFlag) {
        this.lockFlag = lockFlag;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"id\":")
                .append(id)
                .append(", \"userId\":")
                .append(userId)
                .append(", \"userName\":")
                .append(userName)
                .append(", \"title\":")
                .append(title)
                .append(", \"content\":")
                .append(content)
                .append(", \"lockFlag\":")
                .append(lockFlag)
                .append('}');
        return sb.toString();
    }
}
