package com.dylan.licence.service.impl;

import com.dylan.framework.model.info.Message;
import com.dylan.framework.model.info.Status;
import com.dylan.framework.model.page.MyPage;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.framework.model.result.PageDataResult;
import com.dylan.framework.utils.Safes;
import com.dylan.licence.entity.UserInfo;
import com.dylan.licence.mapper.UserInfoMapper;
import com.dylan.licence.model.dto.UserInfoDTO;
import com.dylan.licence.model.vo.UserInfoVO;
import com.dylan.licence.service.UserInfoService;
import com.dylan.licence.transformer.UserInfoTransformer;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Classname UserInfoServiceImpl
 * @Description UserInfoServiceImpl
 * @Date 5/7/2022 4:43 PM
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper mapper;

    private static final MyLogger logger = MyLoggerFactory.getLogger(UserInfoServiceImpl.class);
    /**
     * 根据userId获取该用户对应的信息
     * @param userId
     * @return
     */
    @Override
    public HttpResult selectUserInfoByUserId(Integer userId) {
        UserInfo userInfo = mapper.selectUserInfoByUserId(userId);
        return DataResult.success().data(UserInfoTransformer.userInfo2UserInfoVO(userInfo)).build();
    }

    /**
     * 分页获取用户信息
     * @param page
     * @param limit
     * @return
     */
    @Override
    public HttpResult getPagedUserInfo(Integer page, Integer limit) {
        MyPage myPage = new MyPage(page, limit);
        List<UserInfo> userInfos = mapper.getPagedUserInfo(myPage);
        List<UserInfoVO> userInfoVOS = new ArrayList<>();
        Safes.of(userInfos).forEach(m ->{
            userInfoVOS.add(UserInfoTransformer.userInfo2UserInfoVO(m));
        });
        logger.info("MyPage: {}, userInfoVos: {}", myPage, userInfoVOS);
        return PageDataResult
                .success()
                .page(page)
                .size(limit)
                .data(userInfoVOS)
                .total(mapper.selectUserInfoTotal())
                .build();
    }

    /**
     * 根据id更新用户信息
     * @param userInfoDTO
     * @return
     */
    @Override
    public HttpResult updateUserInfo(UserInfoDTO userInfoDTO) {
        if (Objects.isNull(userInfoDTO.getId())){
            return DataResult.fail().build();
        }
        UserInfo userInfo = UserInfoTransformer.userInfoDTO2UserInfo(userInfoDTO);
        logger.info("UserInfo to insert : {}", userInfo);
        int updated = mapper.updateById(userInfo);
        if (updated <= 0){
            return DataResult.fail(Status.UPDATE_ERROR.getStatus(), Message.UPDATE_ERROR.getMsg()).build();
        }
        UserInfoVO userInfoVO = UserInfoTransformer.userInfo2UserInfoVO(mapper.selectById(userInfo.getId()));
        return DataResult.success().data(userInfoVO).build();
    }

}
