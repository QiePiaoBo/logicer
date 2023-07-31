package com.dylan.licence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dylan.framework.model.page.MyPage;
import com.dylan.licence.entity.Access;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @Classname AccessMapper
 * @Description AccessMapper
 * @Date 5/9/2022 11:20 AM
 */
@Mapper
public interface AccessMapper extends BaseMapper<Access> {

    /**
     * 查询角色列表
     * @param myPage
     * @return
     */
    List<Access> selectAccessList(@Param("myPage") MyPage myPage);
    /**
     * 获取生效的access总数量
     * @return
     */
    Long selectAccessTotal();

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    Integer logicalDeletionById(@Param("id") Integer id);

    /**
     * 根据id列表查询权限列表
     * @param ids
     * @return
     */
    List<Access> selectAccessListByIds(@Param("ids") List<Integer> ids);
}