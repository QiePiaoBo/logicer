package com.dylan.licence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Dylan
 * @Description 权限实体类
 * @Date : 2022/5/9 - 23:25
 */
public class Access implements Serializable {

    private Integer id;

    private String accessCode;

    private String accessName;

    private Integer accessType;

    private String accessUri;

    private String accessDescription;

    private Integer accessStatus;

    private Integer parentMenu;

    private Integer requestType;

    public Integer getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Integer parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    private Integer delFlag;

    private Timestamp updatedAt;

    private Timestamp createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    public String getAccessUri() {
        return accessUri;
    }

    public void setAccessUri(String accessUri) {
        this.accessUri = accessUri;
    }

    public String getAccessDescription() {
        return accessDescription;
    }

    public void setAccessDescription(String accessDescription) {
        this.accessDescription = accessDescription;
    }

    public Integer getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(Integer accessStatus) {
        this.accessStatus = accessStatus;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Access{" +
                "id=" + id +
                ", accessCode='" + accessCode + '\'' +
                ", accessName='" + accessName + '\'' +
                ", accessType=" + accessType +
                ", accessUri='" + accessUri + '\'' +
                ", accessDescription='" + accessDescription + '\'' +
                ", accessStatus=" + accessStatus +
                ", delFlag=" + delFlag +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
