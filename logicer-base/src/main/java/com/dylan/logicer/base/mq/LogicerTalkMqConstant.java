package com.dylan.logicer.base.mq;

/**
 * @author Dylan
 * @Date : 2022/4/14 - 15:13
 */
public class LogicerTalkMqConstant {

    /**
     * 交换机名称
     */
    public static final String LOGICER_DIRECT_EXCHANGE = "LOGICER_DIRECT_EXCHANGE";
    /**
     * LOGICER队列名称
     */
    public static final String LOGICER_QUEUE_LOGICER = "LOGICER_QUEUE_LOGICER";
    /**
     * LOGICER队列routing key
     */
    public static final String LOGICER_ROUTING_KEY_LOGICER = "LOGICER_ROUTING_KEY_LOGICER";

    /**
     * COMMAND队列名称
     */
    public static final String LOGICER_QUEUE_COMMAND = "LOGICER_QUEUE_COMMAND";
    /**
     * COMMAND队列routing key
     */
    public static final String LOGICER_ROUTING_KEY_COMMAND = "LOGICER_ROUTING_KEY_COMMAND";

    /**
     * COMMAND队列名称
     */
    public static final String LOGICER_QUEUE_TALK = "LOGICER_QUEUE_TALK";
    /**
     * COMMAND队列routing key
     */
    public static final String LOGICER_ROUTING_KEY_TALK = "LOGICER_ROUTING_KEY_TALK";

}
