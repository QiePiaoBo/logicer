package com.dylan.licence.service;

import com.dylan.licence.model.vo.UserVO;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据用户Id列表获取用户基础信息VO列表
     * @param userIds
     * @return
     */
    Map<Integer, String> getUserVOsByIds(List<Integer> userIds);
}
