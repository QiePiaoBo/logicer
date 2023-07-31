package com.dylan.licence.service;


import com.dylan.framework.model.page.MyPage;
import com.dylan.framework.model.result.HttpResult;
import com.dylan.licence.model.UserNameIdModel;
import com.dylan.licence.model.dto.UserDTO;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Dylan
 * @since 2020-05-24
 */
public interface UserService {

    /**
     * 获取用户列表
     * @param page
     * @return
     */
    HttpResult selectUserList(MyPage page);

    /**
     * 获取单个用户
     * @param userDTO
     * @return
     */
    HttpResult selectOne(UserDTO userDTO);

    /**
     * 添加一个用户
     * @param userDTO
     * @return
     */
    HttpResult addUser(UserDTO userDTO);

    /**
     * 删除一个用户
     * @param userDTO
     * @return
     */
    HttpResult deleteOne(UserDTO userDTO);

    /**
     * 修改一个用户
     * @param userDTO
     * @return
     */
    HttpResult exchange(UserDTO userDTO);

    /**
     * 用户登录
     * @param userDTO
     * @return
     */
    HttpResult login(UserDTO userDTO);

    /**
     * 用户登出
     * @return
     */
    HttpResult logout();

    /**
     * 获取客户端当前的用户
     * @return
     */
    HttpResult getCurrentUser();

    /**
     * 根据用户名获取用户名Id映射
     * @param userNames
     * @return
     */
    List<UserNameIdModel> getUserNameId(List<String> userNames);
}
