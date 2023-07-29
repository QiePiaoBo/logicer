package com.dylan.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dylan.chat.entity.UserEntity;
import com.dylan.chat.model.UserNameIdModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author Dylan
 * @Description TeamMapper
 * @Date : 2022/6/12 - 15:29
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    /**
     * 获取用户名-id列表
     * @param userNames
     * @return
     */
    List<UserNameIdModel> getUserNameId(List<String> userNames);

}
