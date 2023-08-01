package com.dylan.comm.dao.mapper;

import com.dylan.comm.dao.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author Dylan
 * @Date : 2022/3/27 - 19:28
 * @Description :
 * @Function :
 */
@Mapper
public interface UserMapper {

    /**
     * 获取用户列表
     * @return
     */
    List<User> selectActiveUserList();


    /**
     * 根据用户昵称获取用户信息
     * @param userName
     * @return
     */
    User selectUserByNickName(@Param("userName") String userName);
}
