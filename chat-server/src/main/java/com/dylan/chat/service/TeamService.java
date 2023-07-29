package com.dylan.chat.service;

import com.dylan.chat.dal.entity.TeamEntity;
import com.dylan.chat.dal.mapper.TeamMapper;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Classname MsgRecordService
 * @Description MsgRecordService
 * @Date 6/28/2023 3:27 PM
 */
@Service
public class TeamService {

    private static final MyLogger logger = MyLoggerFactory.getLogger(TeamService.class);

    @Resource
    private TeamMapper teamMapper;


    /**
     * 添加黑名单
     * @param teamName
     * @return
     */
    public TeamEntity getTeamByTeamName(String teamName){
        if (StringUtils.isBlank(teamName)){
            return null;
        }
        return teamMapper.getTeamByTeamName(teamName);
    }

}
