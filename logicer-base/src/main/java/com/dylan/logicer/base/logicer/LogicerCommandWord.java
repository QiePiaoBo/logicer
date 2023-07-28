package com.dylan.logicer.base.logicer;

import java.io.Serializable;

/**
 * @author Dylan
 * @Description LogicerCommandWord
 * @Date : 2022/6/8 - 21:30
 */
public class LogicerCommandWord implements Serializable {

    /**
     * 发送者
     */
    private String sourceMemberName;

    /**
     * 接收者
     */
    private String aimMemberName;

    /**
     * 相关的群
     */
    private String aimTeamName;

    /**
     * 操作详情，备用字段
     */
    private String operationContent;

    public LogicerCommandWord() {
    }

    public LogicerCommandWord(String sourceMemberName, String aimMemberName, String aimTeamName, String operationContent) {
        this.sourceMemberName = sourceMemberName;
        this.aimMemberName = aimMemberName;
        this.aimTeamName = aimTeamName;
        this.operationContent = operationContent;
    }

    public String getSourceMemberName() {
        return sourceMemberName;
    }

    public void setSourceMemberName(String sourceMemberName) {
        this.sourceMemberName = sourceMemberName;
    }

    public String getAimMemberName() {
        return aimMemberName;
    }

    public void setAimMemberName(String aimMemberName) {
        this.aimMemberName = aimMemberName;
    }

    public String getAimTeamName() {
        return aimTeamName;
    }

    public void setAimTeamName(String aimTeamName) {
        this.aimTeamName = aimTeamName;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }
}