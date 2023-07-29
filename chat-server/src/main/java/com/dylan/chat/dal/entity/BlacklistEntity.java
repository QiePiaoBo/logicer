package com.dylan.chat.dal.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname MsgRecordEntity
 * @Description MsgRecordEntity
 * @Date 6/20/2023 5:14 PM
 */
@Data
public class BlacklistEntity {


    private Integer id;

    private Integer blockUserId;

    private Integer blockedUserId;

    private String blockReason;

    private LocalDateTime createdAt;

    private LocalDateTime expirationAt;

    private Integer delFlag;

}
