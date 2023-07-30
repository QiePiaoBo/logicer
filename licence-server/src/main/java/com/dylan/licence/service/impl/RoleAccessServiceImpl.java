package com.dylan.licence.service.impl;


import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.mapper.RoleAccessMapper;
import com.dylan.licence.service.AccessService;
import com.dylan.licence.service.RoleAccessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname RoleAccessServiceImpl
 * @Description RoleAccessServiceImpl
 * @Date 5/11/2022 9:39 AM
 */
@Service
public class RoleAccessServiceImpl implements RoleAccessService {

    @Resource
    private RoleAccessMapper roleAccessMapper;

    @Resource
    private AccessService accessService;


    /**
     * 根据角色id获取权限列表
     *
     * @param rId
     * @return
     */
    @Override
    public HttpResult getAccesses4Role(Integer rId) {
        List<Integer> accessIds = roleAccessMapper.getAccessIds4Role(rId);
        return accessService.selectAccessListByIds(accessIds);
    }
}