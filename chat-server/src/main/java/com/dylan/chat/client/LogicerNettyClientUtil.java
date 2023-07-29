package com.dylan.chat.client;

import com.dylan.chat.config.ConversationUtil;
import com.dylan.chat.config.NettyClientConstant;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Classname LogicerNettyClientUtil
 * @Description LogicerNettyClientUtil
 * @Date 4/27/2023 3:09 PM
 */
public class LogicerNettyClientUtil {

    /**
     * 发送消息
     * @param conversationMapKey 会话唯一键
     * @param message
     */
    public static void sendMessage(String conversationMapKey, String message) throws InterruptedException {
        LinkedBlockingQueue<String> userMessageCenter = NettyClientConstant.USER_MESSAGE_CENTER.getOrDefault(conversationMapKey, null);
        if (Objects.nonNull(userMessageCenter)){
            userMessageCenter.put(message);
        }
    }

    /**
     * 用户登出
     * @param conversationMapKey
     */
    public static void userLogout(String conversationMapKey){
        // 这里传入的userName参数就是 ConversationUtil.getConversationMapKey的值
        LogicerNettyClient savedClient = NettyClientConstant.USER_NETTY_CLIENT_CENTER.getOrDefault(conversationMapKey, null);

        if (Objects.nonNull(savedClient)){
            // 删除当前用户-客户端
            NettyClientConstant.USER_NETTY_CLIENT_CENTER.remove(conversationMapKey);
            // 删除当前用户-消息栈
            NettyClientConstant.USER_MESSAGE_CENTER.remove(conversationMapKey);
            // 这里需要判断是否所有的WS客户端都断开连接 如果不是 就不能断开netty连接因为一个Netty连接可能对应多个WS客户端 如果是清理连接netty服务端的资源 关闭与netty服务端的连接
            if (!ConversationUtil.hasMoreNettyClientForUser(NettyClientConstant.USER_NETTY_CLIENT_CENTER.keySet(), conversationMapKey)){
                savedClient.getGroup().shutdownGracefully();
            }
        }
    }


}
