package com.dylan.licence.service.impl;

import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.mapper.UserRoleMapper;
import com.dylan.licence.service.IRoleService;
import com.dylan.licence.service.IUserRoleService;
import com.dylan.licence.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Dylan
 * @Description UserRoleServiceImpl
 * @Date : 2022/5/10 - 23:11
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {

    @Resource
    private IUserService userService;

    @Resource
    private IRoleService roleService;

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 获取用户的角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public HttpResult getRoleList4User(Integer userId) {
        List<Integer> roleIds = userRoleMapper.getRoleList4User(userId);
        return roleService.selectRoleListByIds(roleIds);
    }
}
