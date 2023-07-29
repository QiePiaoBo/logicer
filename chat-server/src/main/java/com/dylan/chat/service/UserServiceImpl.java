package com.dylan.chat.service;

import com.dylan.chat.mapper.UserMapper;
import com.dylan.chat.model.UserNameIdModel;
import com.dylan.framework.model.result.DataResult;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname MsgRecordService
 * @Description MsgRecordService
 * @Date 6/28/2023 3:27 PM
 */
@Service
public class UserServiceImpl implements UserService {

    private static final MyLogger logger = MyLoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;


    /**
     * 获取用户Id
     * @param userName
     * @return
     */
    @Override
    public HttpResult getUserId(String userName){
        List<UserNameIdModel> userNameIds = userMapper.getUserNameId(Arrays.asList(userName));
        if (userNameIds.size() != 1) {
            return DataResult.getBuilder().data(0).build();
        }
        return DataResult.getBuilder().data(userNameIds.get(0).getId()).build();
    }

}
