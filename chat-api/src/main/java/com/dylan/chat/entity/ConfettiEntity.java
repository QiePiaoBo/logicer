package com.dylan.chat.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname MsgRecordEntity
 * @Description MsgRecordEntity
 * @Date 6/20/2023 5:14 PM
 */
@Data
public class ConfettiEntity {


    private Integer id;

    private Integer userId;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer delFlag;
}
