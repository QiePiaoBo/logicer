package com.dylan.licence.mapper;


import com.dylan.framework.utils.PermissionChecker;
import com.dylan.licence.entity.Access;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname UserMapperTest
 * @Description UserMapperTest
 * @Date 5/11/2022 2:56 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleAccessMapperTest {

    @Resource
    private RoleAccessMapper roleAccessMapper;

    @Resource
    private PermissionChecker permissionChecker;
    private static final MyLogger logger = MyLoggerFactory.getLogger(UserMapperTest.class);

    @Test
    public void getRoleId4UserFromGroup(){
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(1);
        List<Access> accesses4RoleIds = roleAccessMapper.getAccesses4RoleIds(roleIds);
        logger.info("accesses for roleIds is : {}", accesses4RoleIds);
    }

    @Test
    public void permissionCheckerTest(){
        permissionChecker.hasPermission(1, "1");
    }


}
