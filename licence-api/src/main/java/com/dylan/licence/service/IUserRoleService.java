package com.dylan.licence.service;


import com.dylan.framework.model.result.HttpResult;

/**
 * @author Dylan
 * @Description IUserRoleService
 * @Date : 2022/5/10 - 23:11
 */
public interface IUserRoleService {

    /**
     * 获取用户的角色列表
     * @param userId
     * @return
     */
    HttpResult getRoleList4User(Integer userId);

}
