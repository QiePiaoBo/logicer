package com.dylan.logicer.base.logicer;



import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Dylan
 * @Date : 2022/4/13 - 14:43
 */
public class LogicerDecoder extends ByteToMessageDecoder {

    private static final MyLogger logger = MyLoggerFactory.getLogger(LogicerDecoder.class);

    private boolean isClient;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int totalLength = byteBuf.readInt();
        int readable = byteBuf.readableBytes();
        if (totalLength >= readable){
            totalLength = readable;
        }
        CharSequence charSequence = byteBuf.readCharSequence(totalLength, StandardCharsets.UTF_8);
        String lgcMsg = charSequence.toString();
        LogicerMessage logicerMessage = LogicerConstant.COMMON_OBJECT_MAPPER.readValue(lgcMsg, LogicerMessage.class);
        list.add(logicerMessage);
        byteBuf.slice().retain();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf){
            ByteBuf cur = (ByteBuf) msg;
            ByteBuf in2 = cur.retainedDuplicate();
            int length = in2.readInt();
            byte[] res = new byte[in2.readableBytes()];
            in2.readBytes(res);
            if (!isClient){
                logger.debug("length: {}, msg: {}",length, new String(res));
            }
        }
        super.channelRead(ctx, msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Error", cause);
    }

    public LogicerDecoder() {
    }

    public LogicerDecoder(boolean isClient) {
        this.isClient = isClient;
    }

    public boolean isClient() {
        return isClient;
    }

    public void setClient(boolean client) {
        isClient = client;
    }
}
