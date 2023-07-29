package com.dylan.chat.client;

import com.dylan.chat.config.BusinessClientDTO;
import com.dylan.chat.config.ConversationUtil;
import com.dylan.chat.config.NettyClientConstant;
import com.dylan.chat.ws.WebSocketUtil;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.logicer.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Classname LogicerNettyClient
 * @Description LogicerNettyClient
 * @Date 4/27/2023 11:02 AM
 */
public class LogicerNettyClient {

    private static final MyLogger logger = MyLoggerFactory.getLogger(LogicerNettyClient.class);

    private final String userName;

    private final String password;

    private final String talkWith;

    private final String sessionId;

    private final String msgAreaType;

    private Bootstrap bootstrap;

    private EventLoopGroup group;

    private Channel ch;

    public LogicerNettyClient(String userName, String password, String talkWith, String sessionId, String msgAreaType) {
        this.userName = userName;
        this.password = password;
        this.sessionId = sessionId;
        this.msgAreaType = msgAreaType;
        this.talkWith = talkWith;
        this.init();
    }

    private void init() {
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        BusinessClientDTO dto = BusinessClientDTO
                .builder()
                .sessionId(getSessionId())
                .userName(getUserName())
                .password(getPassword())
                .msgAreaType(getMsgAreaType())
                .build();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
                .handler(new LogicerNettyClientInitializer(dto));
    }

    public void connect(String serverAddr, Integer port) throws InterruptedException {
        try {
            if (Objects.nonNull(ch) && ch.isOpen()){
                return;
            }
            // Start the connection attempt.
            ch = bootstrap.connect(serverAddr, port).sync().channel();
            ch.writeAndFlush(LogicerMessageBuilder.buildLoginMessage(getUserName() + "@" + password, getSessionId()));
            // 该用户的Client存在时
            while (Objects.nonNull(NettyClientConstant.USER_NETTY_CLIENT_CENTER.getOrDefault(getConversationClientKey(), null))){
                if (!ch.isOpen()){
                    logger.error("<LogicerNettyClient> Channel closed. UserName: {}", getUserName());
                    NettyClientConstant.USER_NETTY_CLIENT_CENTER.remove(getConversationClientKey());
                    NettyClientConstant.USER_MESSAGE_CENTER.remove(getConversationClientKey());
                    WebSocketUtil.disconnectForUser(getConversationClientKey());
                }
                LinkedBlockingQueue<String> messageCenter = NettyClientConstant.USER_MESSAGE_CENTER.getOrDefault(getConversationClientKey(), null);
                if (Objects.nonNull(messageCenter) && messageCenter.size() > 0){
                    String nextLine = messageCenter.poll();
                    //logger.info("即将发送：" + nextLine);
                    String realMsg = WebSocketUtil.getCompleteMsg(nextLine);
                    if (Objects.isNull(realMsg)){
                        continue;
                    }
                    if (LogicerUtil.isLoginStr(realMsg)){
                        ch.writeAndFlush(LogicerMessageBuilder.buildLoginMessage(realMsg, getSessionId()));
                    }else if (realMsg.startsWith(LogicerConstant.COMMAND_MSG_START_STR)){
                        // 如果是指令类型的消息 就组装指令消息
                        LogicerMessage commandMessage = LogicerMessageBuilder.buildMessage(2, realMsg, getSessionId());
                        ch.writeAndFlush(commandMessage);
                    } else {
                        // 不是登录类型的消息 就默认使用talk子协议进行发送
                        // ch.writeAndFlush(LogicerMessageBuilder.buildMessage(nextLine, getSessionId()));
                        LogicerTalkWord msg = new LogicerTalkWord();
                        msg.setType(getMsgAreaType());
                        msg.setFrom(getUserName());
                        String messageAimingUser = WebSocketUtil.getMessageAimingUser(nextLine);
                        if (StringUtils.isNotBlank(messageAimingUser) && StringUtils.isNotBlank(realMsg)){
                            logger.info("<LogicerNettyClient> msg: {}, to: {}", realMsg, messageAimingUser);
                            msg.setTo(messageAimingUser);
                            msg.setMsg(realMsg);
                            ch.writeAndFlush(LogicerMessageBuilder.buildMessage(new ObjectMapper().writeValueAsString(msg), getSessionId()));
                        }
                    }
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (JsonProcessingException e) {
            logger.error("Connection failed: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            logger.info("Connection closing. {}:{}, current user is : {}", serverAddr, port, getUserName());
            group.shutdownGracefully();
        }
    }

    /**
     * 判断是否已经连接
     * @return
     */
    public boolean isConnecting() {
        if (Objects.isNull(ch)){
            return false;
        }
        return ch.isOpen();
    }

    /**
     * 获取 会话发起者-会话接收者 的值作为会话-Netty客户端的key
     * @return
     */
    public String getConversationClientKey() {
        return ConversationUtil.getConversationMapKey(getUserName(), getTalkWith());
    }

    public String getUserName() {
        return userName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public EventLoopGroup getGroup() {
        return group;
    }

    public String getPassword() {
        return password;
    }

    public String getTalkWith() {
        return talkWith;
    }

    public String getMsgAreaType() {
        return msgAreaType;
    }
}
