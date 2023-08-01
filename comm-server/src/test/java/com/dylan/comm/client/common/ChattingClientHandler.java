package com.dylan.comm.client.common;

import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Dylan
 * @Date : 2022/3/26 - 18:05
 * @Description :
 * @Function :
 */
public class ChattingClientHandler extends ChannelInboundHandlerAdapter {

    MyLogger logger = MyLoggerFactory.getLogger(ChattingClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf buf = (ByteBuf) msg;
            String rev = getMessage(buf);
            logger.info("Channel {} got msg: {}", ctx.channel().remoteAddress(), rev);
        }catch (Exception e){
            e.printStackTrace();
        }
        super.channelRead(ctx, msg);
    }

    public String getMessage(ByteBuf buf){
        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        return new String(con);
    }
}
