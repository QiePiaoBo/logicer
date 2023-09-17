package com.dylan.framework.defaultbean;


import com.dylan.framework.session.UserContext;
import com.dylan.framework.utils.PermissionChecker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @Classname DefaultPermissionChecker
 * @Description DefaultPermissionChecker
 * @Date 5/11/2022 7:28 PM
 */
@Configuration
public class PermissionDefaultBeans {

    /**
     * 如果PermissionChecker并没有实现并注册到spring容器 就在这里初始化并注册到spring
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(PermissionChecker.class)
    public PermissionChecker permissionChecker(){
        return (type, url) -> {
            if (Objects.isNull(UserContext.getCurrentUser())){
                return false;
            }
            return type >= UserContext.getCurrentUser().getUserType();
        };
    }
}
