package com.dylan.licence.service;

import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.mapper.UserMapperTest;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.annotation.Resource;

/**
 * @Classname UserAccessServiceTest
 * @Description UserAccessServiceTest
 * @Date 5/11/2022 2:50 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccessServiceTest {

    @Resource
    private UserAccessService userAccessService;

    private static final MyLogger logger = MyLoggerFactory.getLogger(UserMapperTest.class);
    @Test
    public void test1(){
        HttpResult accesses4User = userAccessService.getAccesses4User(1);
        logger.info("accesses 4 user 1 is : {}", accesses4User);
    }


}
