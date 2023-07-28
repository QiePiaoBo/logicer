package com.dylan.logicer.base.logicer;


import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * @author Dylan
 * @Date : 2022/4/13 - 14:50
 */
public class LogicerEncoder extends MessageToByteEncoder<LogicerMessage> {

    private static final MyLogger logger = MyLoggerFactory.getLogger(LogicerEncoder.class);
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, LogicerMessage logicerMessage, ByteBuf byteBuf) throws Exception {
        byteBuf.clear();
        // logicerMessage序列化时sessionId必须重写
        if (logicerMessage.getLogicerHeader().getSessionId().equals(LogicerConstant.DEFAULT_SESSION_ID)){
            logger.error("<LogicerEncoder.encode> Error, session id need rewrite. current header: {}", logicerMessage);
            return;
        }
        String lgcMsg = LogicerConstant.COMMON_OBJECT_MAPPER.writeValueAsString(logicerMessage);
        int writerIndex = byteBuf.writerIndex();
        byteBuf.writeInt(0);
        int length = ByteBufUtil.writeUtf8(byteBuf, lgcMsg);
        byteBuf.setInt(writerIndex, length);
    }



}
