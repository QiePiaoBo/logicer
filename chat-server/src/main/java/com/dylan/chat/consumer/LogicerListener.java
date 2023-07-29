package com.dylan.chat.consumer;

import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.mq.LogicerTalkMqConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname LogicerTalkListener
 * @Description LogicerTalkListener
 * @Date 6/17/2022 3:21 PM
 */
@Component
public class LogicerListener {

    private final MyLogger logger = MyLoggerFactory.getLogger(LogicerListener.class);

    @RabbitListener(queues = {LogicerTalkMqConstant.LOGICER_QUEUE_LOGICER})
    public void receiveMsg(String msg){
        // todo 添加LOGICER类型消息的处理逻辑（入库）
        logger.info("message:{}", msg);
    }

}
