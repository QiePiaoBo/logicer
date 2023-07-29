package com.dylan.chat.service;

import com.dylan.chat.model.BlacklistInsertModel;

public interface BlacklistService {

    /**
     * 添加黑名单
     * @param model
     * @return
     */
    boolean addBlackList(BlacklistInsertModel model);

}
