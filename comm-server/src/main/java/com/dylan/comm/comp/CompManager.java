package com.dylan.comm.comp;

import com.dylan.comm.config.ConfigReader;
import com.dylan.comm.config.mybatisplus.MybatisConfig;
import com.dylan.comm.config.redis.RedisConfig;
import com.dylan.comm.dao.entity.User;
import com.dylan.comm.handler.LogicerExecutor;
import com.dylan.comm.mq.LogicerProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashBiMap;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dylan
 * @Date : 2022/4/14 - 16:09
 */
public class CompManager {

    /**
     * redis设置 包括lettuce的客户端、Connection和Commands
     */
    public static RedisConfig redis_config = new RedisConfig();

    /**
     * guava提供的可以根据value查key的map <userName - socketAddress>
     */
    public static HashBiMap<String, SocketAddress> name_socketAddress_mapper = HashBiMap.create();
    /**
     * guava提供的可以根据value查key的map <userName - socketAddress>
     */
    public static Map<String, User> name_user_mapper = new HashMap<>();

    /**
     * SocketAddress与ChannelId之间的关系 便于根据SocketAddress从ChannelGroup中找到channel
     */
    public static Map<SocketAddress, ChannelId> socketAddress_channelId_mapper = new ConcurrentHashMap<>();

    /**
     * 所有的MyService类的名称与实例的映射关系
     */
    public static Map<String, Object> serviceName_myService_mapper = new ConcurrentHashMap<>();

    /**
     *  维持长连接使用 <socketAddress - 连接失效次数>
     */
    public static Map<SocketAddress, Integer> socketAddress_disconnectNum_mapper = new ConcurrentHashMap<>();

    /**
     * jackson的对象解析器实例
     */
    public static final ObjectMapper jackson_object_mapper = new ObjectMapper();

    /**
     * 定义连接线程组 所有连接到服务端的线程都会在这个线程组中存在 当某一个连接断开时线程组中代表这个断开连接的数据会被移除
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * MybatisPlus与数据库交互时应该使用时的必要对象实例
     */
    public static MybatisConfig mybatis_configuration = new MybatisConfig();

    /**
     * LogicerExecutor对象实例
     */
    public static LogicerExecutor logicer_executor = new LogicerExecutor();

    /**
     * rabbitMQ消息发送对象
     */
    public static LogicerProducer logicer_mq_producer = new LogicerProducer();

    /**
     * 读空闲触发时间 单位固定为秒
     */
    public static Integer maxReadIdle = ConfigReader.getComplexConfigIntegerWithDefault("server.maxReadIdle", 60);

    /**
     * 写空闲触发时间 单位固定为秒
     */
    public static Integer maxWriteIdle = ConfigReader.getComplexConfigIntegerWithDefault("server.maxWriteIdle", 60);

    /**
     * 读写空闲触发时间 单位固定为秒
     */
    public static Integer maxAllIdle = ConfigReader.getComplexConfigIntegerWithDefault("server.maxAllIdle", 60);


}
