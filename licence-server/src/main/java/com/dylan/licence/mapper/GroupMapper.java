package com.dylan.licence.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dylan.framework.model.page.MyPage;
import com.dylan.licence.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname GroupMapper
 * @Description GroupMapper
 * @Date 5/10/2022 4:00 PM
 */
@Mapper
public interface GroupMapper extends BaseMapper<Group> {

    /**
     * 分页查询Group
     * @param myPage
     * @return
     */
    List<Group> selectGroupList(@Param("myPage") MyPage myPage);

    /**
     * 查询生效的总条数
     * @return
     */
    Long selectGroupTotal();

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    Integer logicalDeletionById(@Param("id") Integer id);


}