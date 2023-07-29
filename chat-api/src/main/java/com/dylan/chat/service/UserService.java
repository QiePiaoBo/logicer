package com.dylan.chat.service;

import com.dylan.framework.model.result.HttpResult;

public interface UserService {
    /**
     * 获取用户Id
     *
     * @param userName
     * @return
     */
    HttpResult getUserId(String userName);
}
