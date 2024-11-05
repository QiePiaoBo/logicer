package com.dylan.comm.mybatis;

import com.dylan.commserver.action.ConfigAction;
import com.dylan.commserver.comp.CompManager;
import com.dylan.commserver.dao.entity.TalkTeam;
import com.dylan.commserver.dao.entity.User;
import com.dylan.commserver.dao.service.TalkTeamService;
import com.dylan.commserver.dao.service.UserService;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.junit.Test;

import java.util.List;

/**
 * @author Dylan
 * @Date : 2022/3/27 - 19:14
 * @Description :
 * @Function :
 */
public class MybatisTest {

    private static final MyLogger logger =  MyLoggerFactory.getLogger(MybatisTest.class);

    @Test
    public void test01(){

        char s = 'a';
        s -=32;
        logger.info("******** " + String.valueOf(s));

    }

    @Test
    public void testTalkTeamList() {

        ConfigAction.initConfigurations();
        TalkTeamService talkTeamService = (TalkTeamService) CompManager.serviceName_myService_mapper.get("talkTeamService");

        List<TalkTeam> activeTeams = talkTeamService.getActiveTeams();

        logger.info("res: {}", activeTeams);

    }
    @Test
    public void testGetUserWithTeamId() {
        ConfigAction.initConfigurations();
        UserService userService = (UserService)CompManager.serviceName_myService_mapper.get("userService");

        User dylan = userService.login("dylan", "19970413");

        logger.info("res: {}", dylan);
    }

}
