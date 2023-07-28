package com.dylan.logicer.base.logicer;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @author Dylan
 * @Date : 2022/4/13 - 14:40
 */
public class LogicerMessage implements Serializable {

    private LogicerHeader logicerHeader;

    private LogicerContent logicerContent;

    public LogicerMessage() {
    }

    public LogicerMessage(LogicerHeader logicerHeader, LogicerContent logicerContent) {
        this.logicerHeader = logicerHeader;
        this.logicerContent = logicerContent;
    }

    public LogicerMessage(LogicerHeader logicerHeader, String content) {
        this.logicerHeader = logicerHeader;
        // 在这里将字符串转化成LogicerContent然后赋值
        this.logicerContent = LogicerUtil.getLogicerContentFromStr(content);
    }

    /**
     * Message类型 0 logicer 1 talk 2 command
     * @param type
     */
    public LogicerMessage(Integer type){
        switch (type){
            case 0:
                this.logicerHeader = new LogicerHeader(LogicerSubProtocol.LOGICER, LogicerConstant.LOGICER_VERSION, 0, LogicerConstant.DEFAULT_SESSION_ID);
                break;
            case 1:
                this.logicerHeader = new LogicerHeader(LogicerSubProtocol.TALK, LogicerConstant.LOGICER_VERSION, 0, LogicerConstant.DEFAULT_SESSION_ID);
                break;
            case 2:
                this.logicerHeader = new LogicerHeader(LogicerSubProtocol.COMMAND, LogicerConstant.LOGICER_VERSION, 0, LogicerConstant.DEFAULT_SESSION_ID);
                break;
            default:
                this.logicerHeader = new LogicerHeader(LogicerSubProtocol.HEARTBEAT, LogicerConstant.LOGICER_VERSION, 0, LogicerConstant.DEFAULT_SESSION_ID);
                break;
        }
    }

    public LogicerHeader getLogicerHeader() {
        return logicerHeader;
    }

    public void setLogicerHeader(LogicerHeader logicerHeader) {
        this.logicerHeader = logicerHeader;
    }

    public LogicerContent getLogicerContent() {
        return logicerContent;
    }

    public void setLogicerContent(LogicerContent logicerContent) {
        // ByteBufUtil.utf8Bytes(logicerContent.toString())
        this.logicerHeader.setContentLength(logicerContent.toString().getBytes(StandardCharsets.UTF_8).length);
        this.logicerContent = logicerContent;
    }

    @Override
    public String toString() {
        return String.format("[protocolCode = %d, version=%d, contentLength=%d, sessionId=%s, content=%s]",
                logicerHeader.getLogicerProtocol().getCode(),
                logicerHeader.getVersion(),
                logicerHeader.getContentLength(),
                logicerHeader.getSessionId(),
                getLogicerContent().toString());
    }
}
