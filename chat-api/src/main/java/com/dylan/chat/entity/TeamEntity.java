package com.dylan.chat.entity;


import java.sql.Timestamp;

/**
 * @author Dylan
 * @Description Team
 * @Date : 2022/6/12 - 15:24
 */
public class TeamEntity {

    private Integer id;

    private String teamName;

    private String teamDesc;

    private Integer teamOwnerId;

    private Integer delFlag;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamOwnerId() {
        return teamOwnerId;
    }

    public void setTeamOwnerId(Integer teamOwnerId) {
        this.teamOwnerId = teamOwnerId;
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

    public String getTeamDesc() {
        return teamDesc;
    }

    public void setTeamDesc(String teamDesc) {
        this.teamDesc = teamDesc;
    }

    @Override
    public String toString() {
        return "TeamEntity{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", teamDesc='" + teamDesc + '\'' +
                ", teamOwnerId=" + teamOwnerId +
                ", delFlag=" + delFlag +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
