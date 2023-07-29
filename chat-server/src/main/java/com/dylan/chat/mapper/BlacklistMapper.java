package com.dylan.chat.mapper;

import com.dylan.chat.model.BlacklistInsertModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname MsgRecordMapper
 * @Description TODO
 * @Date 6/20/2023 5:13 PM
 */
@Mapper
public interface BlacklistMapper {

    /**
     * 添加黑名单
     * @param insertModel
     * @return
     */
    Integer addBlacklist(BlacklistInsertModel insertModel);

}
