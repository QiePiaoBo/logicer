package com.dylan.comm.dao.service;


import com.dylan.comm.anno.MyService;
import com.dylan.comm.comp.CompManager;
import com.dylan.comm.config.mybatisplus.MybatisConfig;
import com.dylan.comm.dao.entity.TalkTeam;
import com.dylan.comm.dao.mapper.TalkTeamMapper;
import org.apache.ibatis.session.SqlSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MyService
public class TalkTeamService {

    private static final MybatisConfig mybatisConfig = Objects.isNull(CompManager.mybatis_configuration) ? new MybatisConfig() : CompManager.mybatis_configuration;

    /**
     * 获取所有的群
     * @return
     */
    public List<TalkTeam> getActiveTeams(){
        SqlSession sqlSession = mybatisConfig.getSqlSession();
        TalkTeamMapper mapper = sqlSession.getMapper(TalkTeamMapper.class);
        if (Objects.isNull(mapper)){
            return new ArrayList<>();
        }
        return mapper.selectActiveTalkTeamList();
    }

}