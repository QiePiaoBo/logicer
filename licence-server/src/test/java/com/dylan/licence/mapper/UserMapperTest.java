package com.dylan.licence.mapper;

import com.dylan.licence.LicenceApplication;
import com.dylan.licence.model.UserNameIdModel;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname UserMapperTest
 * @Description UserMapperTest
 * @Date 5/11/2022 2:56 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LicenceApplication.class)
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    private static final MyLogger logger = MyLoggerFactory.getLogger(UserMapperTest.class);

    @Test
    public void getRoleId4UserFromGroup(){
        Integer roleIdFromGroup = userMapper.getRoleIdFromGroup(1);
        logger.info("role id from group for user 1 is : {}", roleIdFromGroup);
    }

    @Test
    public void getAllRole4UserTest(){
        List<Integer> allRole4User = userMapper.getAllRole4User(1);
        logger.info("all role of user 1 is : {}", allRole4User);
    }

    @Test
    public void getNameIdTest() {
        List<UserNameIdModel> userNameId = userMapper.getUserNameId(Arrays.asList("dylan", "lucifer"));
        logger.info("res: {}", userNameId);
    }
}
