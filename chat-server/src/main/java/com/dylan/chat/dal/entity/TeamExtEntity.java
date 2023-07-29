package com.dylan.chat.dal.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author Dylan
 * @Description Team
 * @Date : 2022/6/12 - 15:24
 */
@TableName("lgc_talk_team_ext")
public class TeamExtEntity {

    private Integer id;

    private Integer teamId;

    private Integer delFlag;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"teamId\":")
                .append(teamId);
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
}
