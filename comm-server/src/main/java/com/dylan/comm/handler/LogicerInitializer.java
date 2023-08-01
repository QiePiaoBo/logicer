package com.dylan.comm.handler;

import com.dylan.comm.comp.CompManager;
import com.dylan.logicer.base.logicer.LogicerDecoder;
import com.dylan.logicer.base.logicer.LogicerEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Dylan
 * @Date : 2022/4/13 - 15:01
 */
public class LogicerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // 将 observeOutput 属性设置为 true，即同时观察输入和输出操作的空闲状态。这样，当在指定的时间段内既没有读取也没有写入数据时，将触发 ALL_IDLE 空闲状态事件。
        // 添加idle触发条件 当前设置的含义: 60秒 没有往缓冲区内写入数据（客户端发送数据）也没有从缓冲区读取数据时会触发ALL_IDLE事件
        pipeline.addLast(new IdleStateHandler(true, 0,0, CompManager.maxAllIdle, TimeUnit.SECONDS));
        // 添加编解码器, 由于ByteToMessageDecoder的子类无法使用@Sharable注解,
        // 这里必须给每个Handler都添加一个独立的Decoder.
        pipeline.addLast(new LengthFieldBasedFrameDecoder(10240, 0, 4));
        pipeline.addLast(new LogicerDecoder());
        pipeline.addLast(new LogicerEncoder());

        // 添加逻辑层控制器
        pipeline.addLast(new LogicerHandler(CompManager.logicer_executor));
    }
}
