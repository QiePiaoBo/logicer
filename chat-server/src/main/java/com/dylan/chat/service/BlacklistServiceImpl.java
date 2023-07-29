package com.dylan.chat.service;

import com.dylan.chat.mapper.BlacklistMapper;
import com.dylan.chat.model.BlacklistInsertModel;
import com.dylan.chat.service.BlacklistService;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Classname MsgRecordService
 * @Description MsgRecordService
 * @Date 6/28/2023 3:27 PM
 */
@Service
public class BlacklistServiceImpl implements BlacklistService {

    private static final MyLogger logger = MyLoggerFactory.getLogger(BlacklistServiceImpl.class);

    @Resource
    private BlacklistMapper blacklistMapper;


    /**
     * 添加黑名单
     * @param model
     * @return
     */
    public boolean addBlackList(BlacklistInsertModel model){

        // todo 检查参数 参数预处理
        return blacklistMapper.addBlacklist(model) > 0;
    }

}
