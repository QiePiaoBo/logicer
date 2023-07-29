package com.dylan.chat.service;

import com.dylan.chat.entity.TeamEntity;

public interface TeamService {
    /**
     * 添加黑名单
     *
     * @param teamName
     * @return
     */
    TeamEntity getTeamByTeamName(String teamName);
}
