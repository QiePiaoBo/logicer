package com.dylan.blog.service.impl;

import com.dylan.framework.model.vo.PersonVo;
import com.dylan.framework.session.UserContext;
import org.springframework.stereotype.Service;

/**
 * 用户类服务
 * @author Dylan
 */
@Service
public class UserService{
    /**
     * 获取当前登录用户
     * @return
     */
    public PersonVo getUser(){
        PersonVo p = UserContext.getCurrentUser();
        return p;
    }
}
