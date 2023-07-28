package com.dylan.licence.mapper;

import com.dylan.licence.entity.Access;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname RoleAccessMapper
 * @Description RoleAccessMapper
 * @Date 5/11/2022 9:40 AM
 */
@Mapper
public interface RoleAccessMapper {

    /**
     * 根据角色id查询权限列表
     * @param roleId
     * @return
     */
    List<Integer> getAccessIds4Role(@Param("roleId") Integer roleId);

    /**
     * 根据角色id查询权限列表
     * @param ids
     * @return
     */
    List<Access> getAccesses4RoleIds(@Param("ids") List<Integer> ids);

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    Integer logicalDeletionById(@Param("id") Integer id);

}