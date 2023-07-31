package com.dylan.licence.controller;


import com.dylan.framework.annos.AdminPermission;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.model.dto.UserDTO;
import com.dylan.licence.service.UserService;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dylan
 * 用户行为中心
 */
@RestController
@RequestMapping("act")
public class UserActController {

    private static final MyLogger logger = MyLoggerFactory.getLogger(UserActController.class);
    @Autowired
    UserService userService;

    /**
     * 登入
     * @param userDTO
     * @return
     */
    @PostMapping("login")
    public HttpResult login(@RequestBody UserDTO userDTO){
        return userService.login(userDTO);
    }

    /**
     * 登出
     * @return
     */
    @AdminPermission
    @GetMapping("logout")
    public HttpResult logout(){
        return userService.logout();
    }

    /**
     * 获取当前登录用户
     * @return
     */
    @AdminPermission
    @GetMapping("who")
    public HttpResult who(){
        logger.info("who is logging");
        return userService.getCurrentUser();
    }

}
