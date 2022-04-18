package com.qb.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * @author qiuBo
 * @Description: rabbitMQ的消费者  接受消息
 * @date 2022/4/18 16:18
 */
public class Consumer {


    /**
     * 队列名称 需要和生产者的消息名称一致
     */
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //rabbitmq安装地址  端口号 用户名 密码
        connectionFactory.setHost("192.168.182.217");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        //创建一个连接
        Connection connection = connectionFactory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        //声明回调
        DeliverCallback deliverCallback = ( consumerTag,  message) ->{
            System.out.println(new String(message.getBody()));
        };
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };
        //消费者消费消息
        /**
         * 1.消费那个队列
         * 2.消费成功后是否自动应答  true代表自动应答
         * 3.消费者消息传递时的回调
         * 4.消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }

}
