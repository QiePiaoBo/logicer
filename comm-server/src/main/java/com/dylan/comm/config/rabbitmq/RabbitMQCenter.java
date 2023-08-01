package com.dylan.comm.config.rabbitmq;

import com.dylan.comm.config.ConfigReader;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * @author Dylan
 * @Date : 2022/4/14 - 14:01
 */
public class RabbitMQCenter {

    private static final MyLogger logger = MyLoggerFactory.getLogger(RabbitMQCenter.class);

    private static Connection MQ_CONNECTION = null;

    /**
     * 初始化连接
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    private static Connection getConnection() throws IOException, TimeoutException{
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ConfigReader.getComplexConfigStringWithDefault("rabbitmq.host", "logicer.top"));
        factory.setPort(ConfigReader.getComplexConfigIntegerWithDefault("rabbitmq.port", 5672));
        factory.setVirtualHost(ConfigReader.getComplexConfigStringWithDefault("rabbitmq.virtualHost", "logicer"));
        factory.setUsername(ConfigReader.getComplexConfigStringWithDefault("rabbitmq.userName", "logicer"));
        factory.setPassword(ConfigReader.getComplexConfigStringWithDefault("rabbitmq.password", "19970413"));
        return factory.newConnection();
    }

    /**
     * 获取RabbitMQ连接
     * @return
     */
    public static Connection getMqConnection() {
        if (Objects.isNull(MQ_CONNECTION)){
            synchronized (RabbitMQCenter.class){
                if (Objects.isNull(MQ_CONNECTION)){
                    try {
                        MQ_CONNECTION = getConnection();
                    } catch (IOException | TimeoutException e) {
                        logger.error("Error at getting connection for rabbitmq: {}", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return MQ_CONNECTION;
    }


}
