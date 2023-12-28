package com.dylan.commserver.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dylan.commserver.comp.CompManager;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 
 * </p>
 *
 * @author Dylan
 * @since 2020-05-24
 */
@TableName("lgc_talk_team")
public class TalkTeam implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String teamName;

    private String teamOwnerId;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Integer delFlag;

    public TalkTeam() {
    }

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

    public String getTeamOwnerId() {
        return teamOwnerId;
    }

    public void setTeamOwnerId(String teamOwnerId) {
        this.teamOwnerId = teamOwnerId;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        try {
            return CompManager.jackson_object_mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return super.toString();
    }
}
