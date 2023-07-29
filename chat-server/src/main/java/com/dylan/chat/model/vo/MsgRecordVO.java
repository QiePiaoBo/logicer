package com.dylan.chat.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname MsgRecordVO
 * @Description MsgRecordVO
 * @Date 6/28/2023 3:29 PM
 */
@Data
public class MsgRecordVO {
    /**
     * 消息Id
     */
    private Long msgId;

    /**
     * 会话Id
     */
    private String sessionId;

    /**
     * 消息类型 logicer talk command
     */
    private Integer msgType;

    private Integer fromId;

    private Integer toId;

    private String msgContent;

    private String msgHash;

    private LocalDateTime msgTimestamp;

    private Integer delFlag;
}
