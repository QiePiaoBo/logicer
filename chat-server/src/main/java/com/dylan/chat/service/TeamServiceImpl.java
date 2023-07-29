package com.dylan.chat.service;

import com.dylan.chat.entity.TeamEntity;
import com.dylan.chat.mapper.TeamMapper;
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
public class TeamServiceImpl implements TeamService {

    private static final MyLogger logger = MyLoggerFactory.getLogger(TeamServiceImpl.class);

    @Resource
    private TeamMapper teamMapper;


    /**
     * 添加黑名单
     * @param teamName
     * @return
     */
    @Override
    public TeamEntity getTeamByTeamName(String teamName){
        if (StringUtils.isBlank(teamName)){
            return null;
        }
        return teamMapper.getTeamByTeamName(teamName);
    }

}
