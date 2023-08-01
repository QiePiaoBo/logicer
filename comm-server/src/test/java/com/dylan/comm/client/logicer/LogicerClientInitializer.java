package com.dylan.comm.client.logicer;

import com.dylan.logicer.base.logicer.LogicerDecoder;
import com.dylan.logicer.base.logicer.LogicerEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author Dylan
 * @Date : 2022/4/13 - 15:08
 */
public class LogicerClientInitializer extends ChannelInitializer<SocketChannel> {

    private String sessionId = "";

    public LogicerClientInitializer() {
    }

    public LogicerClientInitializer(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // 添加编解码器, 由于ByteToMessageDecoder的子类无法使用@Sharable注解,
        // 这里必须给每个Handler都添加一个独立的Decoder.
        pipeline.addLast(new LengthFieldBasedFrameDecoder(10240, 0, 4));
        pipeline.addLast(new LogicerEncoder());
        pipeline.addLast(new LogicerDecoder(true));

        // and then business logic.
        pipeline.addLast(new LogicerClientHandler(sessionId));
    }
}
