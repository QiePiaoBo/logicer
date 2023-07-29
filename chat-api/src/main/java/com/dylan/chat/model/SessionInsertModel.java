package com.dylan.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class SessionInsertModel {

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

    /**
     * 两个Id同时不为空 或者teamId不为空
     * @return
     */
    public boolean isOk() {
        if (Objects.nonNull(getSenderId()) && Objects.nonNull(getRecipientId())){
            return true;
        }
        if (Objects.isNull(getSenderId()) && Objects.isNull(getRecipientId()) && Objects.nonNull(getTalkTeamId())){
            return true;
        }
        return false;
    }

}
