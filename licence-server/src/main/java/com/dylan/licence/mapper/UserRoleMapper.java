package com.dylan.licence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author Dylan
 * @Description UserRoleMapper
 * @Date : 2022/5/10 - 23:04
 */
@Mapper
public interface UserRoleMapper {

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    Integer logicalDeletionById(@Param("id") Integer id);

    /**
     * 根据userId获取角色列表
     * @param userId
     * @return
     */
    List<Integer> getRoleList4User(@Param("userId") Integer userId);

}
