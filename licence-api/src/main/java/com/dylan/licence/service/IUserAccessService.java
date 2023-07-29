package com.dylan.licence.service;

import com.dylan.framework.model.result.HttpResult;

/**
 * @Classname IUserAccessService
 * @Description IUserAccessService
 * @Date 5/11/2022 2:28 PM
 */
public interface IUserAccessService {

    /**
     * 根据用户id获取其所拥有的权限
     * @param id
     * @return
     */
    HttpResult getAccesses4User(Integer id);

    /**
     * id为id的用户是否拥有url的权限
     * @param id
     * @param url
     * @return
     */
    HttpResult hasPermission(Integer id, String url);

    /**
     * 当前用户是否拥有url的权限
     * @param url
     * @return
     */
    HttpResult hasPermission(String url);

}
