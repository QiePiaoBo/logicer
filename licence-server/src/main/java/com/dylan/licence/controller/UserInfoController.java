package com.dylan.licence.controller;


import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.model.dto.UserInfoDTO;
import com.dylan.licence.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Classname UserInfoController
 * @Description UserInfoController
 * @Date 5/7/2022 4:45 PM
 */
@Slf4j
@RestController
@RequestMapping("userinfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 根据userId查询该用户的信息
     * @param userId
     * @return
     */
    @GetMapping("get-by-userid")
    public HttpResult getUserInfoByUserId(@Param("userId") Integer userId){
        return userInfoService.selectUserInfoByUserId(userId);
    }

    @GetMapping
    public HttpResult getPagedUserInfo(@Param("page") Integer page, @Param("limit") Integer limit){
        return userInfoService.getPagedUserInfo(page, limit);
    }

    /**
     * 根据userId查询该用户的信息
     * @param userInfoDTO
     * @return
     */
    @PatchMapping
    public HttpResult updateUserInfo(@RequestBody UserInfoDTO userInfoDTO){

        int nullOfId = 0;
        nullOfId = Objects.isNull(userInfoDTO.getIdType()) ? nullOfId : nullOfId + 1;
        nullOfId = Objects.isNull(userInfoDTO.getIdCode()) ? nullOfId : nullOfId + 1;
        if (nullOfId == 1){
            log.error("Error userInfoDTO:{}, Id type must with id code.", userInfoDTO);
            return DataResult.fail().build();
        }
        return userInfoService.updateUserInfo(userInfoDTO);
    }

}
