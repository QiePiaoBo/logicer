package com.dylan.chat.entity;

import java.sql.Timestamp;

/**
 * @author Dylan
 * @Description TeamMember
 * @Date : 2022/6/12 - 15:24
 */
public class TeamMemberEntity {

    private Integer id;

    private Integer userId;

    private String nickName;

    private Integer userType;

    private Integer teamId;

    private Integer delFlag;

    private Timestamp createdAt;

    private Timestamp updatedAt;

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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "TeamMemberEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", userType=" + userType +
                ", teamId=" + teamId +
                ", delFlag=" + delFlag +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
