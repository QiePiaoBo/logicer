package com.dylan.licence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dylan.framework.model.page.MyPage;
import com.dylan.licence.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname RoleMapper
 * @Description RoleMapper
 * @Date 5/9/2022 11:20 AM
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询角色列表
     * @param myPage
     * @return
     */
    List<Role> selectRoleList(@Param("myPage") MyPage myPage);

    /**
     * 查询生效的角色的总条数
     * @return
     */
    Long selectRoleTotal();

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    Integer logicalDeletionById(@Param("id") Integer id);

    /**
     * 根据id列表查询角色列表
     * @param ids
     * @return
     */
    List<Role> selectRoleListByIds(@Param("ids") List<Integer> ids);
}
