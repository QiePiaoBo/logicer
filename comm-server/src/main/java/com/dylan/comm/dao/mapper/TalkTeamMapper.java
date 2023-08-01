package com.dylan.comm.dao.mapper;

import com.dylan.comm.dao.entity.TalkTeam;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @author Dylan
 * @Date : 2022/3/27 - 19:28
 * @Description :
 * @Function :
 */
@Mapper
public interface TalkTeamMapper {

    /**
     * 获取群列表
     * @return
     */
    List<TalkTeam> selectActiveTalkTeamList();




}
