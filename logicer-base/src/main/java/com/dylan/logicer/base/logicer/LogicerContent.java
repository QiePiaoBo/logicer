package com.dylan.logicer.base.logicer;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.Serializable;

/**
 * @Classname LogicerContent
 * @Description Logicer协议内容对象
 * @Date 4/28/2022 10:29 AM
 */
public class LogicerContent implements Serializable {

    private String actionName;

    private String actionWord;

    private Long actionTimeStamp;

    public LogicerContent() {
    }

    public LogicerContent(String actionName, String actionWord) {
        this.actionName = actionName;
        this.actionWord = actionWord;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionWord() {
        return actionWord;
    }

    public void setActionWord(String actionWord) {
        this.actionWord = actionWord;
    }

    public Long getActionTimeStamp() {
        return actionTimeStamp;
    }

    public void setActionTimeStamp(Long actionTimeStamp) {
        this.actionTimeStamp = actionTimeStamp;
    }

    @Override
    public String toString() {
        try {
            return LogicerConstant.COMMON_OBJECT_MAPPER.writeValueAsString(this);
        }catch (JsonProcessingException e){
            return getActionName() + LogicerConstant.LOGICER_CONTENT_STR + getActionWord();
        }
    }
}
