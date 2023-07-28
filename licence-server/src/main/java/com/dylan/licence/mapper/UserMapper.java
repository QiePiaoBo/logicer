package com.dylan.licence.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dylan.framework.model.page.MyPage;
import com.dylan.licence.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Dylan
 * @since 2020-05-24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户列表 mapper
     * @param myPage
     * @return
     */
    List<User> selectUserList(@Param("myPage") MyPage myPage);

    /**
     * 获取user总数量
     * @return
     */
    Long selectUserTotal();

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    Integer logicalDeletionById(@Param("id") Integer id);

    /**
     * 根据userId从group里获取角色id
     * @param id
     * @return
     */
    Integer getRoleIdFromGroup(@Param("id") Integer id);

    /**
     * 获取用户的所有的角色
     * @param id
     * @return
     */
    List<Integer> getAllRole4User(@Param("id") Integer id);
}
