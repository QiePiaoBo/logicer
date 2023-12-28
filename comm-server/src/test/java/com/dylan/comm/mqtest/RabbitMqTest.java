package com.dylan.comm.mqtest;

import com.dylan.commserver.config.rabbitmq.RabbitMQCenter;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Dylan
 * @Date : 2022/4/14 - 14:52
 */
public class RabbitMqTest {

    @Test
    public void test01() throws IOException, TimeoutException {
        String msg2Send = "HelloWorld.";
        Connection mqConnection = RabbitMQCenter.getMqConnection();
        Channel channel = mqConnection.createChannel();
        // 声明队列，如果队列已经存在，将不会进行任何操作
        //定义队列(使用Java代码在MQ中新建一个队列)
        //参数1：定义的队列名称
        //参数2：队列中的数据是否持久化（如果选择了持久化）
        //参数3: 是否排外（当前队列是否为当前连接私有）
        //参数4：自动删除（当此队列的连接数为0时，此队列会销毁（无论队列中是否还有数据））
        //参数5：设置当前队列的参数
        channel.queueDeclare("test01", false, false, false, null);

        //参数1：交换机名称，如果直接发送信息到队列，则交换机名称为""
        //参数2：目标队列名称
        //参数3：设置当前这条消息的属性（设置过期时间 10）
        //参数4：消息的内容
        channel.basicPublish("","test01",null, msg2Send.getBytes());
        channel.close();
        mqConnection.close();

    }

    @Test
    public void test02(){
        System.out.println("nihao".length());
    }

}
