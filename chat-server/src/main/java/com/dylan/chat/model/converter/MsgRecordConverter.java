package com.dylan.chat.model.converter;

import com.dylan.logicer.base.logicer.LogicerConstant;
import com.dylan.logicer.base.logicer.LogicerMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.dylan.chat.dal.entity.MsgRecordEntity;
import com.dylan.chat.model.MsgInsertModel;
import com.dylan.chat.model.vo.MsgRecordVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * @Classname MsgRecordConverter
 * @Description MsgRecordConverter
 * @Date 6/28/2023 3:33 PM
 */
public class MsgRecordConverter {

    /**
     * entity获取VO
     * @param entity
     * @return
     */
    public static MsgRecordVO getVoForEntity(MsgRecordEntity entity){
        MsgRecordVO msgRecordVO = new MsgRecordVO();
        BeanUtils.copyProperties(entity, msgRecordVO);
        return msgRecordVO;
    }

    /**
     * 根据LogicerMessage组装MsgInsertModel
     * @param message
     * @return
     */
    public static MsgInsertModel getInsertModel(LogicerMessage message, Integer fromId, Integer toId, Integer msgAreaType){
        if (Objects.isNull(fromId) || Objects.isNull(toId)){
            return null;
        }
        // 计算sessionId
        String sessionIdStr = message.getLogicerHeader().getSessionId();
        Integer sessionId = -1;
        if (StringUtils.isNumeric(sessionIdStr)){
            sessionId = Integer.parseInt(sessionIdStr);
        }
        // 计算content
        String msgContent = "Empty";
        try {
            msgContent = LogicerConstant.COMMON_OBJECT_MAPPER.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        MsgInsertModel model = new MsgInsertModel();
        model.setSessionId(sessionId);
        model.setMsgContent(msgContent);
        model.setMsgTimestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(message.getLogicerContent().getActionTimeStamp()), ZoneId.systemDefault()));
        model.setFromId(fromId);
        model.setToId(toId);
        model.setMsgAreaType(msgAreaType);
        return model;
    }



}
