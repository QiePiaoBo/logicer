package com.dylan.comm.client.logicer;

import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.logicer.LogicerConstant;
import com.dylan.logicer.base.logicer.LogicerHeader;
import com.dylan.logicer.base.logicer.LogicerMessage;
import com.dylan.logicer.base.logicer.LogicerMessageBuilder;
import com.dylan.logicer.base.logicer.LogicerSubProtocol;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;

/**
 * @author Dylan
 * @Date : 2022/4/13 - 15:10
 */
public class LogicerClientHandler extends SimpleChannelInboundHandler<LogicerMessage> {

    private static final MyLogger logger = MyLoggerFactory.getLogger(LogicerClientHandler.class);

    private String sessionId;

    public LogicerClientHandler() {
    }

    public LogicerClientHandler(boolean autoRelease) {
        super(autoRelease);
    }

    public LogicerClientHandler(Class<? extends LogicerMessage> inboundMessageType) {
        super(inboundMessageType);
    }

    public LogicerClientHandler(Class<? extends LogicerMessage> inboundMessageType, boolean autoRelease) {
        super(inboundMessageType, autoRelease);
    }

    public LogicerClientHandler(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogicerMessage logicerMessage){
        if (Objects.isNull(logicerMessage)){
            return;
        }
        Channel channel = channelHandlerContext.channel();
        LogicerHeader logicerHeader = logicerMessage.getLogicerHeader();
        LogicerSubProtocol logicerProtocol = logicerHeader.getLogicerProtocol();
        // 如果是心跳报文
        if (LogicerSubProtocol.HEARTBEAT.equals(logicerProtocol)){
            // logger.debug("Server: {}", logicerMessage.getLogicerContent().getActionWord());
            // 如果content是心跳检测字符串 就发送对应的回应报文
            if (LogicerConstant.LOGICER_STATE_ASK.equals(logicerMessage.getLogicerContent().getActionWord())){
                channel.writeAndFlush(LogicerMessageBuilder.buildMessage(3, LogicerConstant.LOGICER_STATE_ANSWER, sessionId));
            }
        }
        // 如果是Logicer报文
        if (LogicerSubProtocol.LOGICER.equals(logicerProtocol)){
            logger.info("Server: {}", logicerMessage.getLogicerContent().getActionWord());
            if (LogicerConstant.ASK_FOR_LOGIN_INFO.equals(logicerMessage.getLogicerContent().toString())){
                logger.info("Please write your name and password like [name@password]!");
            }
        }
        // 如果是talk类型的消息
        if (LogicerSubProtocol.TALK.equals(logicerProtocol)){
            // logger.debug("server: {}", logicerMessage.getLogicerContent().getActionWord());
            String actionWord = logicerMessage.getLogicerContent().getActionWord();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(actionWord);
                logger.info("#{}: {}", jsonNode.get("from").textValue(), jsonNode.get("msg").textValue());
            }catch (JsonProcessingException e){
                logger.error("{}", e.getMessage(), e);
            }
        }
        if (LogicerSubProtocol.COMMAND.equals(logicerProtocol)){
            logger.info("Got command msg: {}", logicerMessage.getLogicerContent());
            String actionWord = logicerMessage.getLogicerContent().getActionWord();
            if (actionWord.equals("BYE")){
                channelHandlerContext.channel().closeFuture();
            }
        }
    }
}
