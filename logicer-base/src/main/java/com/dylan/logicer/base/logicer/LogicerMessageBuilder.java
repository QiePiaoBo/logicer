package com.dylan.logicer.base.logicer;

import java.util.Objects;

/**
 * @author Dylan
 * @Date : 2022/4/16 - 17:20
 */
public class LogicerMessageBuilder {

    private static final LogicerMessage LOGICER = new LogicerMessage(0);
    private static final LogicerMessage DEFAULT = new LogicerMessage(1);
    private static final LogicerMessage COMMAND = new LogicerMessage(2);
    private static final LogicerMessage HEATBEAT = new LogicerMessage(3);

    /**
     * 构造默认的消息 默认消息子协议为talk
     * @param msg
     * @return
     */
    public static LogicerMessage buildMessage(String msg){
        LogicerContent contentFromStr = LogicerUtil.getLogicerContentFromStr(msg);
        DEFAULT.setLogicerContent(contentFromStr);
        return DEFAULT;
    }

    /**
     * 构造默认的消息 默认消息子协议为talk 支持设置sessionId
     * @param msg
     * @return
     */
    public static LogicerMessage buildMessage(String msg, String sessionId){
        LogicerContent contentFromStr = LogicerUtil.getLogicerContentFromStr(msg);
        DEFAULT.setLogicerContent(contentFromStr);
        DEFAULT.getLogicerHeader().setSessionId(sessionId);
        return DEFAULT;
    }

    /**
     * 组装不同子协议的消息 0 logicer 1 talk 2 command 3 heartbeat
     * @param type
     * @param msg
     * @return
     */
    public static LogicerMessage buildMessage(Integer type, String msg){
        switch (type){
            case 0:
                LOGICER.setLogicerContent(LogicerUtil.getLogicerContentFromStr(msg));
                return LOGICER;
            case 2:
                return buildCommandMessage(msg);
            case 3:
                HEATBEAT.setLogicerContent(LogicerUtil.getHeartBeatContentFromStr(msg));
                return HEATBEAT;
            default:
                DEFAULT.setLogicerContent(LogicerUtil.getTalkContentFromStr(msg));
                return DEFAULT;
        }
    }

    /**
     * 组装不同子协议的消息 0 logicer 1 talk 2 command 3 heartbeat 支持设置sessionId
     * @param type
     * @param msg
     * @return
     */
    public static LogicerMessage buildMessage(Integer type, String msg, String sessionId){
        switch (type){
            case 0:
                LOGICER.getLogicerHeader().setSessionId(sessionId);
                LOGICER.setLogicerContent(LogicerUtil.getLogicerContentFromStr(msg));
                return LOGICER;
            case 2:
                return buildCommandMessage(msg, sessionId);
            case 3:
                HEATBEAT.setLogicerContent(LogicerUtil.getHeartBeatContentFromStr(msg));
                HEATBEAT.getLogicerHeader().setSessionId(sessionId);
                return HEATBEAT;
            default:
                DEFAULT.setLogicerContent(LogicerUtil.getTalkContentFromStr(msg));
                DEFAULT.getLogicerHeader().setSessionId(sessionId);
                return DEFAULT;
        }
    }

    /**
     * 构造断开连接的消息 支持设置sessionId
     * @return
     */
    public static LogicerMessage buildExitMessage(){
        LOGICER.setLogicerContent(new LogicerContent("EXIT", "BYE"));
        return LOGICER;
    }


    /**
     * 构造断开连接的消息
     * @return
     */
    public static LogicerMessage buildExitMessage(String sessionId){
        LOGICER.setLogicerContent(new LogicerContent("EXIT", "BYE"));
        LOGICER.getLogicerHeader().setSessionId(sessionId);
        return LOGICER;
    }

    /**
     * 构建登陆消息
     * @return
     */
    public static LogicerMessage buildLoginMessage(String loginStr){
        LOGICER.setLogicerContent(new LogicerContent("LOGIN", loginStr));
        return LOGICER;
    }

    /**
     * 构建登陆消息
     * @return
     */
    public static LogicerMessage buildLoginMessage(String loginStr, String sessionId){
        LOGICER.setLogicerContent(new LogicerContent("LOGIN", loginStr));
        LOGICER.getLogicerHeader().setSessionId(sessionId);
        return LOGICER;
    }

    /**
     * 构造command消息
     * @return
     */
    public static LogicerMessage buildCommandMessage(String commandWordStr){
        LogicerContent logicerContent = LogicerUtil.getCommandContentFromStr(commandWordStr);
        if (Objects.isNull(logicerContent)){
            COMMAND.setLogicerContent(new LogicerContent());
        }else {
            COMMAND.setLogicerContent(logicerContent);
        }
        return COMMAND;
    }


    /**
     * 构造command消息
     * @return
     */
    public static LogicerMessage buildCommandMessage(String commandWordStr, String sessionId){
        LogicerContent logicerContent = LogicerUtil.getCommandContentFromStr(commandWordStr);
        if (Objects.isNull(logicerContent)){
            COMMAND.setLogicerContent(new LogicerContent());
        }else {
            COMMAND.setLogicerContent(logicerContent);
        }
        COMMAND.getLogicerHeader().setSessionId(sessionId);
        return COMMAND;
    }
}
