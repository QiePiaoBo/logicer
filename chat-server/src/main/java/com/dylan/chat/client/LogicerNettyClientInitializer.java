package com.dylan.chat.client;


import com.dylan.chat.config.BusinessClientDTO;
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
public class LogicerNettyClientInitializer extends ChannelInitializer<SocketChannel> {

    private final BusinessClientDTO businessClientDTO;

    public LogicerNettyClientInitializer(BusinessClientDTO businessClientDTO) {
        this.businessClientDTO = businessClientDTO;
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
        pipeline.addLast(new LogicerNettyClientHandler(getBusinessClientDTO()));
    }

    public BusinessClientDTO getBusinessClientDTO() {
        return businessClientDTO;
    }
}
