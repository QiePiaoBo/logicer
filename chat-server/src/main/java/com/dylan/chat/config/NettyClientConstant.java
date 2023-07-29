package com.dylan.chat.config;


import com.dylan.chat.client.LogicerNettyClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Classname NettyClientConstant
 * @Description NettyClientConstant
 * @Date 4/27/2023 11:22 AM
 */
public class NettyClientConstant {

    /**
     * startUser&->talkWith - 会话消息栈
     */
    public static final Map<String, LinkedBlockingQueue<String>> USER_MESSAGE_CENTER = new HashMap<>();

    /**
     * startUser&->talkWith - netty客户端
     */
    public static final Map<String, LogicerNettyClient> USER_NETTY_CLIENT_CENTER = new HashMap<>();
}
