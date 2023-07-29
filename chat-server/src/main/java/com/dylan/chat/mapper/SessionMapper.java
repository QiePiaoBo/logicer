package com.dylan.chat.mapper;

import com.dylan.chat.entity.SessionEntity;
import com.dylan.chat.model.SessionInsertModel;
import com.dylan.chat.model.SessionQueryModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname MsgRecordMapper
 * @Description TODO
 * @Date 6/20/2023 5:13 PM
 */
@Mapper
public interface SessionMapper {

    /**
     * 根据请求参数查询对应的会话
     * @param queryModel
     * @return
     */
    List<SessionEntity> querySessions(SessionQueryModel queryModel);

    /**
     * 批量插入会话
     * @param insertModels
     * @return
     */
    Integer batchInsertSession(List<SessionInsertModel> insertModels);


    /**
     * 添加单个会话
     * @param insertModel
     * @return
     */
    Integer insertSession(SessionInsertModel insertModel);

    /**
     * 根据名字查询会话
     * @param userName
     * @param talkWith
     * @return
     */
    SessionEntity getSessionByUserName(@Param("userName") String userName, @Param("talkWith") String talkWith);


}
