package com.dylan.blog.model;

import java.io.Serializable;
import java.util.Objects;

public class ConfettiMergeModel implements Serializable {

    private Integer curUserId;

    private String secretKey;

    private Integer mergeFrom;

    private Integer mergeTo;

    public Integer getCurUserId() {
        return curUserId;
    }

    public void setCurUserId(Integer curUserId) {
        this.curUserId = curUserId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getMergeFrom() {
        return mergeFrom;
    }

    public void setMergeFrom(Integer mergeFrom) {
        this.mergeFrom = mergeFrom;
    }

    public Integer getMergeTo() {
        return mergeTo;
    }

    public void setMergeTo(Integer mergeTo) {
        this.mergeTo = mergeTo;
    }

    public boolean isRight() {
        return secretKey.equals("logicer") && Objects.nonNull(getMergeFrom()) && Objects.nonNull(getMergeTo());
    }
}
