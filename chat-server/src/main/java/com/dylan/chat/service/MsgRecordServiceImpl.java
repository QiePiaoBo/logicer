package com.dylan.chat.service;

import com.dylan.framework.utils.Safes;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.chat.entity.MsgRecordEntity;
import com.dylan.chat.mapper.MsgRecordMapper;
import com.dylan.chat.model.MsgInsertModel;
import com.dylan.chat.model.MsgQueryModel;
import com.dylan.chat.model.converter.MsgRecordConverter;
import com.dylan.chat.model.vo.MsgRecordVO;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Classname MsgRecordService
 * @Description MsgRecordService
 * @Date 6/28/2023 3:27 PM
 */
@Service
public class MsgRecordServiceImpl implements MsgRecordService {

    private static final MyLogger logger = MyLoggerFactory.getLogger(MsgRecordServiceImpl.class);

    @Resource
    private MsgRecordMapper msgRecordMapper;

    /**
     * 获取消息列表
     *
     * @return
     */
    @Override
    public List<MsgRecordVO> getMsgRecordForClient(MsgQueryModel queryModel) {
        List<MsgRecordEntity> msgRecordEntities = msgRecordMapper.queryMsgRecords(queryModel);
        return Safes.of(msgRecordEntities).stream().map(MsgRecordConverter::getVoForEntity).collect(Collectors.toList());
    }


    /**
     * 消息批量插入
     *
     * @param insertModels
     * @return
     */
    @Override
    public boolean msgRecordBatchInsert(List<MsgInsertModel> insertModels) {
        // 消息处理
        insertModels = checkAndDealingMsg(insertModels);
        Integer integer = msgRecordMapper.batchInsertMsgRecord(insertModels);
        return integer > 0;
    }

    /**
     * 处理待插入消息
     *
     * @param insertModels
     */
    private List<MsgInsertModel> checkAndDealingMsg(List<MsgInsertModel> insertModels) {
        // sessionId为空的消息 不允许插入并打印消息
        return Safes.of(insertModels).stream()
                .peek(m -> {
                    if (Objects.isNull(m.getSessionId()) || Objects.isNull(m.getMsgAreaType())) {
                        logger.error("msg invalid: {}", m);
                    }
                })
                .filter(m -> Objects.nonNull(m.getSessionId()))
                .filter(m -> Objects.nonNull(m.getMsgAreaType()))
                .collect(Collectors.toList());
    }


}
