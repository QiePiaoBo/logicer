package com.dylan.licence.transformer;

import com.dylan.framework.utils.Safes;
import com.dylan.licence.entity.UserInfo;
import com.dylan.licence.model.dto.UserInfoDTO;
import com.dylan.licence.model.vo.UserInfoVO;

import java.util.Objects;

/**
 * @Classname UserInfoTransformer
 * @Description UserInfoTransformer
 * @Date 5/7/2022 5:12 PM
 */
public class UserInfoTransformer {

    /**
     * userInfo -> userInfoVO
     * @param userInfo
     * @return
     */
    public static UserInfoVO userInfo2UserInfoVO(UserInfo userInfo){
        UserInfoVO userInfoVO = new UserInfoVO();
        if (Objects.isNull(userInfo)){
            return userInfoVO;
        }
        userInfoVO.setId(userInfo.getId());
        userInfoVO.setUserId(userInfo.getUserId());
        userInfoVO.setGender(userInfo.getGender());
        userInfoVO.setMail(Safes.of(userInfo.getMail()));
        userInfoVO.setRealName(Safes.of(userInfo.getRealName()));
        userInfoVO.setWechatCode(Safes.of(userInfo.getWechatCode()));
        return userInfoVO;
    }

    /**
     * userInfoDTO -> userInfo
     * @param userInfoDTO
     * @return
     */
    public static UserInfo userInfoDTO2UserInfo(UserInfoDTO userInfoDTO){
        UserInfo userInfo = new UserInfo();
        if (Objects.isNull(userInfoDTO)){
            return userInfo;
        }
        userInfo.setId(userInfoDTO.getId());
        userInfo.setUserId(userInfoDTO.getUserId());
        userInfo.setMail(userInfoDTO.getMail());
        userInfo.setGender(userInfoDTO.getGender());
        userInfo.setRealName(userInfoDTO.getRealName());
        userInfo.setIdType(userInfoDTO.getIdType());
        userInfo.setIdCode(userInfoDTO.getIdCode());
        userInfo.setWechatCode(userInfoDTO.getWechatCode());
        return userInfo;
    }


}
