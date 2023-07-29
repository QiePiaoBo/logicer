package com.dylan.licence.service;


import com.dylan.framework.model.result.HttpResult;

/**
 * @Classname IRoleAccessService
 * @Description TODO
 * @Date 5/11/2022 9:39 AM
 */
public interface IRoleAccessService {

    /**
     * 根据角色id获取权限列表
     * @param rId
     * @return
     */
    HttpResult getAccesses4Role(Integer rId);

}
