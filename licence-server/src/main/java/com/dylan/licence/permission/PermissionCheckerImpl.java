package com.dylan.licence.permission;


import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.framework.model.vo.PersonVo;
import com.dylan.framework.session.UserContext;
import com.dylan.framework.utils.PermissionChecker;
import com.dylan.licence.service.IUserAccessService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Classname PermissionCheckerImpl
 * @Description PermissionCheckerImpl
 * @Date 5/11/2022 6:52 PM
 */
@RefreshScope
@Component
public class PermissionCheckerImpl implements PermissionChecker {

    @Resource
    private IUserAccessService userAccessService;

    @Value("${permissionCheckGrade:1}")
    private Integer permissionCheckGrade;

    /**
     * 当前登录用户是否有type url的权限
     *
     * @param type
     * @param url
     * @return
     */
    @Override
    public boolean hasPermission(Integer type, String url) {
        /**
         * 需要同时满足
         * 1. 接口所需的权限 >= 用户的userType
         * 2. 当前用户的权限列表中存在该url
         */
        PersonVo currentUser = UserContext.getCurrentUser();
        if (Objects.isNull(currentUser)){
            return false;
        }
        if (type < currentUser.getUserType()){
            return false;
        }
        // 检查权限校验等级 1 只检查type 2 检查type并检查权限列表中是否包含当前url
        if (permissionCheckGrade <= NumberUtils.INTEGER_ONE){
            return true;
        }
        HttpResult httpResult = userAccessService.hasPermission(currentUser.getId(), url);
        return Objects.equals(httpResult.getStatus(), Status.SUCCESS.getStatus());
    }
}
