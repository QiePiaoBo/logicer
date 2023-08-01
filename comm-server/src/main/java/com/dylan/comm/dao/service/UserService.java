package com.dylan.comm.dao.service;

import com.dylan.comm.anno.MyService;
import com.dylan.comm.comp.CompManager;
import com.dylan.comm.config.mybatisplus.MybatisCenter;
import com.dylan.comm.dao.entity.User;
import com.dylan.comm.dao.mapper.UserMapper;
import com.dylan.logicer.base.util.PasswordUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MyService
public class UserService {

    private static final MybatisCenter MYBATIS_CENTER = Objects.isNull(CompManager.mybatis_configuration) ? new MybatisCenter() : CompManager.mybatis_configuration;

    /**
     * 获取所有的用户
     * @return
     */
    public List<User> getActiveUsers(){
        SqlSession sqlSession = MYBATIS_CENTER.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        if (Objects.isNull(mapper)){
            return new ArrayList<>();
        }
        return mapper.selectActiveUserList();
    }

    /**
     * 登录动作
     * @param userName
     * @param password
     * @return
     */
    public User login(String userName, String password){
        SqlSession sqlSession = MYBATIS_CENTER.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        if (Objects.isNull(mapper)){
            return null;
        }
        User user = mapper.selectUserByNickName(userName);
        if (Objects.nonNull(user) && match(password, user.getUserPassword())){
            return user;
        }
        return null;
    }

    /**
     * 输入的密码 password是否正确
     * @param password
     * @param dtoPassword
     * @return
     */
    private boolean match(String password, String dtoPassword){
         return PasswordUtil.authenticatePassword(dtoPassword, password);
    }

}