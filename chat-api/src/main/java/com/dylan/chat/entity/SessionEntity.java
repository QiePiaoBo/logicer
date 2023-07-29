package com.dylan.chat.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname MsgRecordEntity
 * @Description MsgRecordEntity
 * @Date 6/20/2023 5:14 PM
 */
@Data
public class SessionEntity {

    private Integer sessionId;

    private Integer senderId;

    private Integer recipientId;

    private Integer talkTeamId;

    private LocalDateTime createdAt;

    private LocalDateTime expirationAt;

    private Integer delFlag;

}
