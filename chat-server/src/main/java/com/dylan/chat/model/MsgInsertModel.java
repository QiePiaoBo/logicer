package com.dylan.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Classname MsgRecordEntity
 * @Description MsgRecordEntity
 * @Date 6/20/2023 5:14 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgInsertModel {

    /**
     * 会话Id
     */
    private Integer sessionId;

    /**
     * 消息类型 logicer talk command
     */
    private Integer msgType;

    private Integer msgAreaType;

    private Integer fromId;

    private Integer toId;

    private String msgContent;

    private String msgHash;

    private LocalDateTime msgTimestamp;

    /**
     * 是否符合插入标准
     * @return
     */
    public boolean isOk() {
        return Objects.nonNull(getSessionId()) && Objects.nonNull(getMsgType()) && Objects.nonNull(getMsgAreaType());
    }

}
