package com.dylan.chat.model.vo;

import lombok.Data;

/**
 * @Classname MsgRecordEntity
 * @Description MsgRecordEntity
 * @Date 6/20/2023 5:14 PM
 */
@Data
public class LgcTalkBlacklistVO {

    private Integer blockUserId;

    private Integer blockedUserId;

    private String blockReason;

}
