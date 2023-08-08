package com.dylan.framework.utils;


/**
 * @Classname PermissionChecker
 * @Description PermissionChecker
 * @Date 5/11/2022 6:42 PM
 */
public interface PermissionChecker {
    /**
     * 当前登录用户是否有type url的权限
     * @param type
     * @param url
     * @return
     */
    boolean hasPermission(Integer type, String url);
}
