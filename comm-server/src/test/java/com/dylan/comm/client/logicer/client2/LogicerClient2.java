package com.dylan.comm.client.logicer.client2;

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
public class LogicerClient2 {

    private static final MyLogger logger = MyLoggerFactory.getLogger(LogicerClient2.class);


    public static void main(String args[]) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
                    .handler(new LogicerClientInitializer());

            // Start the connection attempt.
            Channel ch = b.connect("logicer.top", 8001).sync().channel();

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String nextLine = scanner.nextLine();
                logger.debug("即将发送：" + nextLine);
                if ("exit".equals(nextLine)){
                    logger.info("即将断开连接");
                    ch.writeAndFlush(LogicerMessageBuilder.buildExitMessage());
                    ch.close();
                    break;
                }
                if (LogicerUtil.isLoginStr(nextLine)){
                    ch.writeAndFlush(LogicerMessageBuilder.buildLoginMessage(nextLine));
                }else {
                    if (nextLine.contains("COMMAND : ")){
                        // 非登录消息 使用默认类型的子协议
                        ch.writeAndFlush(LogicerMessageBuilder.buildCommandMessage(nextLine));
                    }else {
                        // 不是登录类型的消息 就默认使用talk子协议进行发送
                        // ch.writeAndFlush(LogicerMessageBuilder.buildMessage(nextLine));
                        LogicerTalkWord msg = new LogicerTalkWord();
                        msg.setType("0");
                        msg.setTo("dylan");
                        msg.setMsg(nextLine);
                        ch.writeAndFlush(LogicerMessageBuilder.buildMessage(new ObjectMapper().writeValueAsString(msg)));
                    }
                }
            }
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        finally {
            group.shutdownGracefully();
        }
    }
}
