package com.dylan.framework.annos;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dylan
 *  自定义注解，用于加在接口上用于注明该接口需要什么等级的权限
 *  权限分级
 *      0：需要登录，用户至少是超级管理员
 *      1：需要登录，用户至少是普通管理员
 *      2：需要登陆，用户至少是普通用户
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface AdminPermission {
    int userType() default 2;
}