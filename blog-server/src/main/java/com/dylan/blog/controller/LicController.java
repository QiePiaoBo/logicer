package com.dylan.blog.controller;

import com.dylan.blog.service.impl.UserService;
import com.dylan.framework.annos.AdminPermission;
import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.framework.model.vo.PersonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * blog接口集，
 * @author Dylan
 */
@RestController
@RequestMapping("user")
public class LicController {

    @Autowired
    UserService userService;

    /**
     * 获取当前登录用户
     * @return
     */
    @AdminPermission
    @RequestMapping("getUser")
    public HttpResult getUser(){
        PersonVo userVO = userService.getUser();
        return new HttpResult(Status.SUCCESS.getStatus(), Message.SUCCESS.getMsg(), userVO);
    }


}
