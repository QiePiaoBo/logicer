package com.dylan.commserver.action;

import com.dylan.commserver.comp.CompManager;
import com.dylan.commserver.config.MyCoreConfig;
import com.dylan.commserver.config.rabbitmq.RabbitMQCenter;
import com.dylan.commserver.config.redis.RedisCenter;
import com.dylan.commserver.handler.LogicerInitializer;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.mq.LogicerTalkMqConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.io.IOException;

/**
 * @author Dylan
 * @Date : 2022/4/13 - 15:44
 */
public class ServerAction {

    private static final MyLogger logger = MyLoggerFactory.getLogger(ServerAction.class);
    /**
     * @Description:  启动netty端口监听
     * @return void
     * @Author Dylan
     * @CreateTime 2022/4/13 15:41
     */
    public static void openNettyServer() {
        logger.info("Opening netty server...");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            StatefulRedisConnection<String, String> connection = CompManager.redis_config.getConnection();
            RedisCommands<String, String> redisCommands = CompManager.redis_config.getRedisCommands();

            String result = redisCommands.set("name", "dylan", RedisCenter.setArgs);
            logger.info("Result of set name : {}", result);
            result = redisCommands.get("name");
            logger.info("Result of get name : {}", result);
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new LogicerInitializer())
            ;
            ChannelFuture channelFuture = bootstrap.bind(MyCoreConfig.NETTY_SERVER_PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void prepareMQEnv(){
        logger.info("Preparing env of rabbitmq...");
        Connection mqConnection = RabbitMQCenter.getMqConnection();
        try {
            Channel channel = mqConnection.createChannel();
            // 声明交换机
            channel.exchangeDeclare(LogicerTalkMqConstant.LOGICER_DIRECT_EXCHANGE, "direct");
            // 声明队列
            channel.queueDeclare(LogicerTalkMqConstant.LOGICER_QUEUE_LOGICER, true, false, false, null);
            channel.queueDeclare(LogicerTalkMqConstant.LOGICER_QUEUE_COMMAND, true, false, false, null);
            channel.queueDeclare(LogicerTalkMqConstant.LOGICER_QUEUE_TALK, true, false, false, null);
            // 将路由规则、队列和交换机进行绑定
            channel.queueBind(LogicerTalkMqConstant.LOGICER_QUEUE_LOGICER,LogicerTalkMqConstant.LOGICER_DIRECT_EXCHANGE, LogicerTalkMqConstant.LOGICER_ROUTING_KEY_LOGICER);
            channel.queueBind(LogicerTalkMqConstant.LOGICER_QUEUE_COMMAND,LogicerTalkMqConstant.LOGICER_DIRECT_EXCHANGE, LogicerTalkMqConstant.LOGICER_ROUTING_KEY_COMMAND);
            channel.queueBind(LogicerTalkMqConstant.LOGICER_QUEUE_TALK, LogicerTalkMqConstant.LOGICER_DIRECT_EXCHANGE, LogicerTalkMqConstant.LOGICER_ROUTING_KEY_TALK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
