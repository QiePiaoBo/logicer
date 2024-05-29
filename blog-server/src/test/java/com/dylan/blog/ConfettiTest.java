package com.dylan.blog;

import com.dylan.blog.entity.ConfettiEntity;
import com.dylan.blog.mapper.ConfettiMapper;
import com.dylan.blog.model.ConfettiInsertModel;
import com.dylan.blog.model.ConfettiQueryModel;
import com.dylan.blog.service.ConfettiService;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfettiTest {

    private static final MyLogger logger = MyLoggerFactory.getLogger(ConfettiTest.class);

    @Resource
    private ConfettiMapper confettiMapper;

    @Resource
    private ConfettiService confettiService;

    @Test
    public void testAddConfetti(){

        ConfettiInsertModel insertModel = new ConfettiInsertModel();
        insertModel.setTitle("Test");
        insertModel.setLockFlag(1);
        insertModel.setContent("Test content.");
        insertModel.setUserId(1);
        Integer integer = confettiMapper.addConfetti(insertModel);

        logger.info("res: {}", integer);
    }

    @Test
    public void testGetConfetti(){
        ConfettiQueryModel confettiQueryModel = new ConfettiQueryModel();
        confettiQueryModel.setUserId(1);
        List<ConfettiEntity> confettiForUsers = confettiMapper.getConfettiForUsers(confettiQueryModel);
        logger.info("res: {}", confettiForUsers);
    }

    @Test
    public void testGetConfettiForUser(){
        ConfettiQueryModel queryModel = new ConfettiQueryModel();
        queryModel.setUserId(0);
        HttpResult confettiForUser = confettiService.getConfettiForUser(queryModel);
        logger.info("res: {}", confettiForUser);
    }



}
