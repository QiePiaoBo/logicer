package com.dylan.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dylan.chat.entity.TeamEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author Dylan
 * @Description TeamMapper
 * @Date : 2022/6/12 - 15:29
 */
@Mapper
public interface TeamMapper extends BaseMapper<TeamEntity> {


    /**
     * 根据群名获取群对象
     * @param teamName
     * @return
     */
    TeamEntity getTeamByTeamName(String teamName);

}
