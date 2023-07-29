package com.dylan.chat.model;

import lombok.Data;

/**
 * @Classname MsgQueryModel
 * @Description MsgQueryModel
 * @Date 6/20/2023 5:25 PM
 */
@Data
public class MsgQueryModel {

    private Integer fromId;

    private Integer toId;

    private String partition;

    private Integer sessionId;

    private Integer msgAreaType;

}
