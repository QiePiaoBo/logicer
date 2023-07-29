package com.dylan.chat.model;

import lombok.Data;

import java.util.Objects;

@Data
public class ConfettiMergeModel {

    private Integer curUserId;

    private String secretKey;

    private Integer mergeFrom;

    private Integer mergeTo;

    public boolean isRight() {
        return secretKey.equals("logicer") && Objects.nonNull(getMergeFrom()) && Objects.nonNull(getMergeTo());
    }
}
