package com.dylan.licence.service.impl;

import com.dylan.framework.model.exception.MyException;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.framework.model.vo.PersonVo;
import com.dylan.framework.session.UserContext;
import com.dylan.framework.utils.Safes;
import com.dylan.licence.entity.Access;
import com.dylan.licence.mapper.RoleAccessMapper;
import com.dylan.licence.mapper.UserMapper;
import com.dylan.licence.model.vo.AccessVO;
import com.dylan.licence.service.UserAccessService;
import com.dylan.licence.transformer.AccessTransformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Classname UserAccessServiceImpl
 * @Description UserAccessServiceImpl
 * @Date 5/11/2022 2:29 PM
 */
@Service
public class UserAccessServiceImpl implements UserAccessService {

    @Resource
    private RoleAccessMapper roleAccessMapper;

    @Resource
    private UserMapper userMapper;


    /**
     * 根据用户id获取其所拥有的权限
     *
     * @param id
     * @return
     */
    @Override
    public HttpResult getAccesses4User(Integer id) {
        // 两种途径获取角色列表
        List<Integer> roleIds = userMapper.getAllRole4User(id);
        // 根据roleIds获取accesses
        List<Access> accesses = roleAccessMapper.getAccesses4RoleIds(roleIds);
        // 根据accesses获取accessVOS
        List<AccessVO> accessVOS = Safes.of(accesses)
                .stream()
                .map(AccessTransformer::access2AccessVO)
                .collect(Collectors.toList());
        return DataResult
                .success()
                .data(accessVOS)
                .build();
    }

    /**
     * id为id的用户是否拥有url的权限
     *
     * @param id
     * @param url
     * @return
     */
    @Override
    public HttpResult hasPermission(Integer id, String url) {
        // 两种途径获取角色列表
        List<Integer> roleIds = userMapper.getAllRole4User(id);
        // 根据roleIds获取accesses
        List<Access> accesses = roleAccessMapper.getAccesses4RoleIds(roleIds);
        for (Access a : Safes.of(accesses)){
            // 如果权限url不包含 * 直接判断目标url与有权限的uri是否匹配
            if (!a.getAccessUri().contains("**")){
                if (Objects.equals(url, a.getAccessUri())){
                    return DataResult.success().data(true).build();
                }
            }else {
                // 如果权限url包含** 那么就截取**之前的部分
                String accessUri = a.getAccessUri();
                String front = StringUtils.substringBefore(accessUri, "**");
                // 如果**之前的部分不为空且目的url以**之前的部分开头 那就判定为有权限
                if (StringUtils.isNotEmpty(front) && url.startsWith(front)){
                    return DataResult.success().data(true).build();
                }
            }
        }
        return DataResult.fail().build();
    }

    /**
     * 当前用户是否拥有url的权限
     *
     * @param url
     * @return
     */
    @Override
    public HttpResult hasPermission(String url) {
        PersonVo currentUser = UserContext.getCurrentUser();
        if (Objects.isNull(currentUser)){
            throw new MyException("未登录");
        }
        return hasPermission(currentUser.getId(), url);
    }
}