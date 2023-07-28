package com.dylan.logicer.base.logicer;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.Serializable;

/**
 * @author Dylan
 * @Date : 2022/4/13 - 14:20
 */
public class LogicerHeader implements Serializable {

    private LogicerSubProtocol logicerSubProtocol;

    private int version;

    private int contentLength;

    private String sessionId;

    public LogicerHeader() {
    }

    public LogicerHeader(LogicerSubProtocol logicerSubProtocol, int version, int contentLength, String sessionId) {
        this.logicerSubProtocol = logicerSubProtocol;
        this.version = version;
        this.contentLength = contentLength;
        this.sessionId = sessionId;
    }

    public LogicerSubProtocol getLogicerProtocol() {
        return logicerSubProtocol;
    }

    public void setLogicerProtocol(LogicerSubProtocol logicerSubProtocol) {
        this.logicerSubProtocol = logicerSubProtocol;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        try {
            return LogicerConstant.COMMON_OBJECT_MAPPER.writeValueAsString(this);
        }catch (JsonProcessingException e){
            return "{" + "\"logicerSubProtocol\":" +
                    logicerSubProtocol +
                    ",\"version\":" +
                    version +
                    ",\"contentLength\":" +
                    contentLength +
                    ",\"sessionId\":" + (sessionId == null ? "" : "\"") +
                    sessionId + (sessionId == null ? "" : "\"") +
                    '}';
        }
    }
}
