package com.dylan.chat.service;

import com.dylan.chat.model.MsgInsertModel;
import com.dylan.chat.model.MsgQueryModel;
import com.dylan.chat.model.vo.MsgRecordVO;

import java.util.List;

public interface MsgRecordService {
    /**
     * 获取消息列表
     *
     * @return
     */
    List<MsgRecordVO> getMsgRecordForClient(MsgQueryModel queryModel);

    /**
     * 消息批量插入
     *
     * @param insertModels
     * @return
     */
    boolean msgRecordBatchInsert(List<MsgInsertModel> insertModels);
}
