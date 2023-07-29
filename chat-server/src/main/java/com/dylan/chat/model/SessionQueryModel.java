package com.dylan.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Classname MsgQueryModel
 * @Description MsgQueryModel
 * @Date 6/20/2023 5:25 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionQueryModel {

    private Integer sessionId;

    private Integer senderId;

    private Integer recipientId;

    private Integer talkTeamId;

    /**
     * 保证小的Id在前面 大的Id在后面
     */
    public void confirmId() {
        Integer recipientId = getRecipientId();
        Integer senderId = getSenderId();
        setSenderId(Math.min(recipientId, senderId));
        setRecipientId(Math.max(recipientId, senderId));
    }

    public boolean isValid() {
        // 所有属性都为空 false
        if (Objects.isNull(getSessionId()) && Objects.isNull(getSenderId()) && Objects.isNull(getRecipientId()) && Objects.isNull(getTalkTeamId())) {
            return false;
        }
        // 发起者和接收者必须都为空或者都不为空
        if (
                (Objects.isNull(getSenderId()) && Objects.nonNull(getRecipientId()))
                        || (Objects.nonNull(getSenderId()) && Objects.isNull(getRecipientId()))
        ) {
            return false;
        }
        return true;
    }

}
