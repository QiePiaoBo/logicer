package com.dylan.chat.entity;

import java.time.LocalDateTime;

/**
 * @author Dylan
 * @Description Team
 * @Date : 2022/6/12 - 15:24
 */
public class UserEntity {

    private Integer id;
    private Integer userType;
    private String userName;
    private String userPhone;
    private String userPassword;
    private Integer userGroup;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer delFlag;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"userType\":")
                .append(userType);
        sb.append(",\"userName\":").append(userName == null ? "" : "\"")
                .append(userName).append(userName == null ? "" : "\"");
        sb.append(",\"userPhone\":").append(userPhone == null ? "" : "\"")
                .append(userPhone).append(userPhone == null ? "" : "\"");
        sb.append(",\"userPassword\":").append(userPassword == null ? "" : "\"")
                .append(userPassword).append(userPassword == null ? "" : "\"");
        sb.append(",\"userGroup\":")
                .append(userGroup);
        sb.append(",\"createdAt\":")
                .append(createdAt);
        sb.append(",\"updatedAt\":")
                .append(updatedAt);
        sb.append(",\"delFlag\":")
                .append(delFlag);
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(Integer userGroup) {
        this.userGroup = userGroup;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
