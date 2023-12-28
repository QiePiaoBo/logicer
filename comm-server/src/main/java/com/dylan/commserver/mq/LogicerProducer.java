package com.dylan.commserver.mq;

import com.dylan.commserver.config.rabbitmq.RabbitMQCenter;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.mq.LogicerTalkMqConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Dylan
 * @Date : 2022/4/14 - 15:23
 */
public class LogicerProducer {

    private static final MyLogger logger = MyLoggerFactory.getLogger(LogicerProducer.class);
    /**
     * 什么是concurrentSkipListMap
     * 线程安全有序的一个哈希表，适用于高并发的情况
     * 1. 轻松的将序号和消息关联、
     * 2、轻松的批量删除条目，只需要给出序号
     * 3、支持并发访问
     */
    private static final ConcurrentSkipListMap<Long, String> unconfirmedMessageMap = new ConcurrentSkipListMap<>();

    private Channel produceChannel = null;

    public LogicerProducer() {
        try {
            this.produceChannel = RabbitMQCenter.getMqConnection().createChannel();
            logger.info("Created produceChannel: {}", produceChannel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量发送Logicer类型的消息，并在channel中异步确认发送成功
     * @param messages
     */
    public void batchSendLogicerMessageAsync(String... messages) {
        this.batchSendCommonMessageAsync(LogicerTalkMqConstant.LOGICER_DIRECT_EXCHANGE,
                LogicerTalkMqConstant.LOGICER_QUEUE_LOGICER,
                LogicerTalkMqConstant.LOGICER_ROUTING_KEY_LOGICER,
                messages);
    }

    /**
     * 批量发送Talk类型的消息，并在channel中异步确认发送成功
     * @param messages
     */
    public void batchSendTalkMessageAsync(String... messages){
        this.batchSendCommonMessageAsync(LogicerTalkMqConstant.LOGICER_DIRECT_EXCHANGE,
                LogicerTalkMqConstant.LOGICER_QUEUE_TALK,
                LogicerTalkMqConstant.LOGICER_ROUTING_KEY_TALK,
                messages);
    }

    /**
     * 批量发送Command类型的消息，并在channel中异步确认发送成功
     * @param messages
     */
    public void batchSendCommandMessageAsync(String... messages) {
        this.batchSendCommonMessageAsync(LogicerTalkMqConstant.LOGICER_DIRECT_EXCHANGE,
                LogicerTalkMqConstant.LOGICER_QUEUE_COMMAND,
                LogicerTalkMqConstant.LOGICER_ROUTING_KEY_COMMAND,
                messages);
    }
    /**
     * 批量发送消息，并在channel中异步确认发送成功
     * @param messages
     */
    public void batchSendCommonMessageAsync(String exchange, String queue, String routingKey, String... messages){
        try {
            // 开启发布确认 该channel中的每条消息都会配置上唯一ID或消息唯一的序列号
            produceChannel.confirmSelect();
            // 声明回调-成功发送
            ConfirmCallback ackCallBack = (deliverTag, multiple) ->{
                if (multiple){
                    logger.info("Message before {}-{} send successfully.",deliverTag, unconfirmedMessageMap.getOrDefault(deliverTag, "EMPTY_MESSAGE"));
                    // 返回的是小于等于当前序列号的未确认消息
                    ConcurrentNavigableMap<Long, String> confirmedMap = unconfirmedMessageMap.headMap(deliverTag, true);
                    // 将这些确认状态的消息清除掉
                    confirmedMap.clear();
                }else {
                    if (unconfirmedMessageMap.containsKey(deliverTag)){
                        logger.info("Message:{} send successfully.", unconfirmedMessageMap.getOrDefault(deliverTag, "EMPTY_MESSAGE"));
                        // 移除当前确认的 消息序号
                        unconfirmedMessageMap.remove(deliverTag);
                    }
                }
            };
            // 声明回调-发送失败
            ConfirmCallback nackCallBack = (deliverTag, multiple) ->{
                String nackMsg = unconfirmedMessageMap.get(deliverTag);
                logger.error("Error, failed send message: {}", nackMsg);
            };
            // 为channel添加发布确认监听器
            produceChannel.addConfirmListener(ackCallBack, nackCallBack);
            for (String message : messages){
                // 所有消息都有一个id与其对应 添加到map中
                unconfirmedMessageMap.put(produceChannel.getNextPublishSeqNo(), message);
                // 发送消息
                produceChannel.basicPublish(exchange, routingKey, null, message.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
