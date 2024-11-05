package com.dylan.chat.client;

import com.dylan.chat.config.ConversationUtil;
import com.dylan.chat.config.NettyClientConstant;
import com.dylan.framework.model.exception.MyException;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname ClientBuilder
 * @Description ClientBuilder
 * @Date 4/27/2023 2:03 PM
 */
@Service
public class LogicerNettyClientBuildService {

    @Resource
    @Qualifier("nettyClientExecutor")
    private ThreadPoolExecutor nettyClientExecutor;

    private final MyLogger logger = MyLoggerFactory.getLogger(LogicerNettyClientBuildService.class);

    /**
     * 创建并启动netty客户端
     * @param userName
     * @param password
     * @param talkWith
     * @param sessionId
     */
    public void startConnection(String userName, String password, String talkWith, String sessionId, String msgAreaType){
        // 创建netty客户端
        LogicerNettyClient logicerNettyClient = getLogicerNettyClient(userName, password, talkWith, sessionId, msgAreaType);
        logger.info("thread condition: corePoolSize={}, poolSize={}, activeCount={}, completedTaskCount={}",
                nettyClientExecutor.getCorePoolSize(),
                nettyClientExecutor.getPoolSize(),
                nettyClientExecutor.getActiveCount(),
                nettyClientExecutor.getCompletedTaskCount());
        // 启动netty客户端
        nettyClientExecutor.execute(() -> {
            try {
                if (!logicerNettyClient.isConnecting()){
                    logicerNettyClient.connect("172.26.0.152", 8001);
                }else {
                    logger.info("Netty Client already connected, userName: {}, talkWith: {}, msgAreaType: {}", userName, talkWith, msgAreaType);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 创建netty客户端
     * @param userName
     * @param password
     * @return
     */
    private synchronized LogicerNettyClient getLogicerNettyClient(String userName, String password, String talkWith, String sessionId, String msgAreaType) {
        // 确定这个会话的唯一键
        String conversationMappingKey = ConversationUtil.getConversationMapKey(userName, talkWith);
        LogicerNettyClient logicerNettyClient = NettyClientConstant.USER_NETTY_CLIENT_CENTER.getOrDefault(conversationMappingKey, null);
        LinkedBlockingQueue<String> messageCenter = NettyClientConstant.USER_MESSAGE_CENTER.getOrDefault(conversationMappingKey, null);
        // 该唯一键的netty客户端和消息中心都不为空 此时可以直接返回netty客户端
        if (Objects.nonNull(logicerNettyClient) && Objects.nonNull(messageCenter)){
            return logicerNettyClient;
        }
        Set<String> keysForClient = NettyClientConstant.USER_NETTY_CLIENT_CENTER.keySet();
        Set<String> keysForMessage = NettyClientConstant.USER_MESSAGE_CENTER.keySet();
        // netty客户端连接复用 如果已有Client中 有userName&->*的key 表示该用户已经与netty客户端建立了连接 此时需要复用连接
        String existKeyForClient = null;
        for (String key : keysForClient){
            if (key.startsWith(userName)){
                if (existKeyForClient != null){
                    throw new MyException("Error, more than 1 client for " + userName);
                }
                existKeyForClient = key;
            }
        }
        // 消息栈复用 如果已有消息栈中，有userName&->*的key 表示该用户已经与netty客户端建立了连接 此时需要复用消息栈
        String existKeyForMessage = null;
        for (String key : keysForMessage){
            if (key.startsWith(userName)){
                if (existKeyForMessage != null){
                    throw new MyException("Error, more than 1 message center for " + userName);
                }
                existKeyForMessage = key;
            }
        }
        // 如果客户端为空 且不存在已有netty客户端 就创建; 如果客户端为空 且存在已有netty客户端 就复用
        if (Objects.isNull(logicerNettyClient)){
            if (existKeyForClient == null){
                logicerNettyClient = new LogicerNettyClient(userName, password, talkWith, sessionId, msgAreaType);
            }else {
                logicerNettyClient = NettyClientConstant.USER_NETTY_CLIENT_CENTER.get(existKeyForClient);
            }
            // 复用
            NettyClientConstant.USER_NETTY_CLIENT_CENTER.put(conversationMappingKey, logicerNettyClient);
        }
        // 如果消息中心为空 且不存在已有消息中心 就创建；如果消息中心为空 且存在已有消息中心 就复用
        if (Objects.isNull(messageCenter)){
            if (existKeyForMessage == null){
                messageCenter = new LinkedBlockingQueue<>();
            }else {
                messageCenter = NettyClientConstant.USER_MESSAGE_CENTER.get(existKeyForMessage);
            }
            // 复用
            NettyClientConstant.USER_MESSAGE_CENTER.put(conversationMappingKey, messageCenter);
        }
        if (Objects.nonNull(messageCenter) && Objects.nonNull(logicerNettyClient)){
            return logicerNettyClient;
        }else {
            throw new MyException("Error create resource for " + userName);
        }
    }

}
