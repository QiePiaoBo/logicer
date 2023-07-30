package com.dylan.licence.service;

import com.dylan.framework.model.result.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Classname UserAccessServiceTest
 * @Description UserAccessServiceTest
 * @Date 5/11/2022 2:50 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccessServiceTest {

    @Resource
    private UserAccessService userAccessService;

    @Test
    public void test1(){
        HttpResult accesses4User = userAccessService.getAccesses4User(1);
        log.info("accesses 4 user 1 is : {}", accesses4User);
    }


}
