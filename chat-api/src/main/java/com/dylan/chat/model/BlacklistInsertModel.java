package com.dylan.chat.model;

import lombok.Data;

/**
 * @Classname MsgRecordEntity
 * @Description MsgRecordEntity
 * @Date 6/20/2023 5:14 PM
 */
@Data
public class BlacklistInsertModel {

    private Integer id;

    private Integer blockUserId;

    private Integer blockedUserId;

    private String blockReason;

}
