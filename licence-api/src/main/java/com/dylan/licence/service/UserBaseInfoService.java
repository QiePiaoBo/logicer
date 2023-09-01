package com.dylan.licence.service;

import com.dylan.licence.model.vo.UserVO;

/**
 * @Classname UserBaseInfoService
 * @Description UserBaseInfoService
 * @Date 9/1/2023 3:47 PM
 */
public interface UserBaseInfoService {


    /**
     * 根据用户Id获取用户基础信息VO
     * @param userId
     * @return
     */
    UserVO getUserVOById(Integer userId);

}
