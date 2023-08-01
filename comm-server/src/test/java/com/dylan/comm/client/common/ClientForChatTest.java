package com.dylan.comm.client.common;

import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

/**
 * @author Dylan
 * @Date : 2022/3/26 - 17:39
 * @Description :
 * @Function :
 */
public class ClientForChatTest {

    static MyLogger logger = MyLoggerFactory.getLogger(ClientForChatTest.class);

    @Test
    public void multipartClientInit(){
        startClient4Chat1();
        startClient4Chat2();
    }

    @Test
    public void startClient4Chat1(){
        logger.info("Starting Chatting Client......");
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(new ChattingClientHandler());
                }
            });

            ChannelFuture future = bootstrap.connect("192.168.2.110", 8888).sync();
            String msg = "0010asdadsadaf$_";
            future.channel().writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));
            Thread.sleep(10000);
            String end = "#channel_shutdown$_";
            future.channel().writeAndFlush(Unpooled.copiedBuffer(end.getBytes()));
            future.channel().closeFuture().sync();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    @Test
    public void startClient4Chat2(){
        logger.info("Starting Chatting Client......");
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(new ChattingClientHandler());
                }
            });

            ChannelFuture future = bootstrap.connect("192.168.2.110", 8888).sync();
            String msg = "0010asdadsadaf$_";
            future.channel().writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));
            Thread.sleep(10000);
            String end = "#11channel_shutdown$_";
            future.channel().writeAndFlush(Unpooled.copiedBuffer(end.getBytes()));
            future.channel().closeFuture().sync();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }
}
