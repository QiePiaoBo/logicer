package com.dylan.comm.dubbotest;

import com.dylan.comm.action.ConfigAction;
import com.dylan.comm.config.dubbo.DubboCenter;
import com.dylan.comm.config.rabbitmq.RabbitMQCenter;
import com.dylan.licence.model.UserNameIdModel;
import com.dylan.licence.service.UserService;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Dylan
 * @Date : 2022/4/14 - 14:52
 */
public class DubboTest {

    private static final MyLogger logger = MyLoggerFactory.getLogger(DubboTest.class);

    @Test
    public void test01(){
        ConfigAction.initConfigurations();
        UserService service = DubboCenter.getService(UserService.class);
        List<UserNameIdModel> userNameId = service.getUserNameId(Arrays.asList("dylan", "duke"));
        logger.info("res: {}", userNameId);
    }

    @Test
    public void test02(){
    }

}
