package com.dylan.logicer.base.logicer;

/**
 * @author szh
 * @date 2022/5/3 10:14
 */
public class LogicerTalkWord {

    /**
     * 0 点对点消息 1 群消息
     */
    private String type;

    /**
     * 消息发送者
     */
    private String from;

    /**
     * 消息接收者
     */
    private String to;

    /**
     * 消息内容
     */
    private String msg;

    public LogicerTalkWord(String type, String from, String to, String msg) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.msg = msg;
    }

    public LogicerTalkWord() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LogicerTalkContent{" +
                "type='" + type + '\'' +
                ", user='" + from + '\'' +
                ", group='" + to + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
