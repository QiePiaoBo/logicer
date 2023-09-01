package com.dylan.licence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dylan.licence.entity.User;
import com.dylan.licence.mapper.UserMapper;
import com.dylan.licence.model.vo.UserVO;
import com.dylan.licence.service.UserBaseInfoService;
import com.dylan.licence.transformer.UserInfoTransformer;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 *  用户服务实现类
 * </p>
 * @author Dylan
 * @since 2020-05-24
 */
@Service
@RefreshScope
@DubboService(version = "1.0.0")
public class UserBaseInfoServiceImpl extends ServiceImpl<UserMapper, User> implements UserBaseInfoService {

    @Resource
    private UserMapper userMapper;

    private static final MyLogger logger = MyLoggerFactory.getLogger(UserBaseInfoServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户Id获取用户基础信息VO
     * @param userId
     * @return
     */
    @Override
    public UserVO getUserVOById(Integer userId) {

        if (Objects.isNull(userId)){
            logger.error("Error param: userId={}", userId);
        }
        User user = userMapper.getUserById(userId);
        return UserInfoTransformer.getUserVO(user);
    }

}