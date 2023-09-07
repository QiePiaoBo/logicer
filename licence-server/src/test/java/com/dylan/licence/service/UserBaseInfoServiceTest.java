package com.dylan.licence.service;

import com.dylan.blog.vo.UserVO;
import com.dylan.licence.entity.User;
import com.dylan.licence.mapper.UserMapper;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserBaseInfoServiceTest {

    private static final MyLogger logger = MyLoggerFactory.getLogger(UserBaseInfoServiceTest.class);
    @Resource
    private UserBaseInfoService userBaseInfoService;
    @Resource
    private UserMapper userMapper;


    @Test
    public void testGetUsersByIds(){
        List<Integer> userIds = Arrays.asList(1, 2);
        Map<Integer, String> map = userBaseInfoService.getUserVOsByIds(userIds);
        logger.info("res: {}",map);

    }

}
