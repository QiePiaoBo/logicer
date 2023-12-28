package com.dylan.commserver.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dylan.commserver.comp.CompManager;
import com.dylan.commserver.util.LogicerMessageUtil;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.logicer.LogicerConstant;
import com.dylan.logicer.base.logicer.LogicerMessage;
import com.dylan.logicer.base.logicer.LogicerMessageBuilder;
import com.dylan.logicer.base.logicer.LogicerUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import java.net.SocketAddress;
import java.util.Objects;

/**
 * @author Dylan
 * @Date : 2022/4/13 - 15:03
 */
public class LogicerHandler extends SimpleChannelInboundHandler<LogicerMessage> {

    MyLogger logger = MyLoggerFactory.getLogger(LogicerHandler.class);

    private final LogicerExecutor executor;

    public LogicerHandler(LogicerExecutor executor) {
        this.executor = executor;
    }

    /**
     * 接收消息时触发的方法
     * @param ctx
     * @param logicerMessage
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogicerMessage logicerMessage) throws Exception {
        // 当前的连接通道
        Channel channel = ctx.channel();
        executor.executeMessage(channel, logicerMessage);
    }


    /**
     * 出现异常时触发的方法
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("exception occurred : {}", cause.getMessage(), cause);
    }

    /**
     * 客户端连接建立时触发的方法
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 当前的连接通道
        Channel channel = ctx.channel();
        // 该连接中对方服务器的地址
        SocketAddress socketAddress = channel.remoteAddress();
        String nickName = executor.getNickNameForChannel(socketAddress);
        // 默认名称
        if (Objects.isNull(nickName)){
            channel.writeAndFlush(LogicerMessageBuilder.buildMessage(0, LogicerConstant.ASK_FOR_LOGIN_INFO, "logicer"));
        }else {
            // 向全体主机发送新成员加入的消息 todo 只对部分成员发送消息（ChannelMatcher）
            LogicerMessageUtil.sendToAimUser(LogicerConstant.MESSAGE_AT_ALL, LogicerMessageBuilder.buildMessage(0, LogicerUtil.getPackagedLogicerMessage("New member - " + nickName + " is active.", "@all"), "logicer"));
        }
        executor.clientConnected(channel);
        logger.info("Member active: {}", socketAddress);
    }

    /**
     * 客户端断开时触发的方法
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 当前的连接通道
        Channel channel = ctx.channel();
        // 该连接中对方服务器的地址
        SocketAddress socketAddress = channel.remoteAddress();
        String userName = executor.getNickNameForChannel(socketAddress);
        if (StringUtils.isBlank(userName)){
            logger.error("Nickname for {} not found.", socketAddress);
            userName = "Logicer";
        }
        // 默认名称
        // 向全体成员发送消息 todo 只对部分成员发送消息（ChannelMatcher）
        LogicerMessageUtil.sendToAimUser(LogicerConstant.MESSAGE_AT_ALL,
                LogicerMessageBuilder.buildMessage(0, LogicerUtil.getPackagedLogicerMessage("Member - " + userName + " left.", "@all"), "logicer"));
        logger.info("Member inactive: {}@{}.", userName, socketAddress);
        executor.clientDisconnected(socketAddress);
    }

    /**
     * 用于保持长连接
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        Channel channel = ctx.channel();
        SocketAddress socketAddress = channel.remoteAddress();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String eventType = "";
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    eventType = "READER_IDLE";
                    break;
                case WRITER_IDLE:
                    eventType = "WRITER_IDLE";
                    break;
                case ALL_IDLE:
                    eventType = "ALL_IDLE";
                    break;
            }
            logger.debug(eventType + "_________" + socketAddress +" need ack.");
            Integer integer = CompManager.socketAddress_disconnectNum_mapper.getOrDefault(socketAddress, 0);
            if (integer + 1 <= LogicerConstant.MAX_DISCONNECT_TIME) {
                CompManager.socketAddress_disconnectNum_mapper.put(socketAddress, integer + 1);
            } else {
                logger.error(eventType + " more than " + LogicerConstant.MAX_DISCONNECT_TIME +" time, closing this channel.");
                channel.close();
            }
            channel.writeAndFlush(LogicerMessageBuilder.buildMessage(3, LogicerConstant.LOGICER_STATE_ASK, "logicer"));
        }
    }

}
