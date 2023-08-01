package com.dylan.comm.client.logicer.client1;

import com.dylan.comm.client.logicer.LogicerClientInitializer;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.logicer.LogicerMessageBuilder;
import com.dylan.logicer.base.logicer.LogicerTalkWord;
import com.dylan.logicer.base.logicer.LogicerUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @author Dylan
 * @Date : 2022/4/13 - 15:06
 */
public class LogicerClient1 {

    private Bootstrap b;
    private EventLoopGroup group;

    private String sessionId = "client1";

    private static final MyLogger logger = MyLoggerFactory.getLogger(LogicerUtil.class);

    public static void main(String[] args) throws InterruptedException {
        LogicerClient1 logicerClient1 = new LogicerClient1();
        logicerClient1.init();
        logicerClient1.connect();
    }

    private void init() {
        b = new Bootstrap();
        group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
                .handler(new LogicerClientInitializer(sessionId));
    }

    public void connect() throws InterruptedException {
        try {
            // Start the connection attempt.
            Channel ch = b.connect("localhost", 8001).sync().channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String nextLine = scanner.nextLine();
                logger.debug("即将发送：" + nextLine);
                if ("exit".equals(nextLine)){
                    logger.info("即将断开连接");
                    ch.writeAndFlush(LogicerMessageBuilder.buildExitMessage(sessionId));
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        logger.error("failed to sleep, but its ok.");
                    }
                    ch.close();
                    break;
                } else if ("connect".equals(nextLine)) {
                    connect();
                    break;
                }
                if (LogicerUtil.isLoginStr(nextLine)){
                    ch.writeAndFlush(LogicerMessageBuilder.buildLoginMessage(nextLine, sessionId));
                }else {
                    // 不是登录类型的消息 就默认使用talk子协议进行发送
                    // ch.writeAndFlush(LogicerMessageBuilder.buildMessage(nextLine, sessionId));
                    LogicerTalkWord msg = new LogicerTalkWord();
                    msg.setType("0");
                    msg.setTo("duke");
                    msg.setMsg(nextLine);
                    ch.writeAndFlush(LogicerMessageBuilder.buildMessage(new ObjectMapper().writeValueAsString(msg), sessionId));
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
