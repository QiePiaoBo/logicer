package com.dylan.blog.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class ConfettiMergeModel implements Serializable {

    private Integer curUserId;

    private String secretKey;

    private Integer mergeFrom;

    private Integer mergeTo;

    public boolean isRight() {
        return secretKey.equals("logicer") && Objects.nonNull(getMergeFrom()) && Objects.nonNull(getMergeTo());
    }
}
