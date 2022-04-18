package com.qb.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author qiuBo
 * @Description: rabbitMQ的生产者 发消息
 * @date 2022/4/18 16:18
 */
public class Producer {

    /**
     * 队列名称
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
        //生成一个队列
        /**
         * 1.队列名称
         * 2.队列里面的消息是否持久化（存储在磁盘上）  默认消息存储在内存中，即不持久化
         * 3.该队列是否只供一个消费者进行消费，是否排他  true表示只能一个消费者
         * 4.是否字段删除 最后一个消费者离开连接以后  是否自动删除  true 自动删除
         * 5.设置队列的其他一些参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发消息
        String message = "hello world";
        //发送消息
        /**
         * 1.发送到那个交换机
         * 2.路由的key 本次是队列名称
         * 3.其它参数信息
         * 4.发送的消息
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("发送消息完成");
    }

}
