package com.huahun.basic.common.utils;


import com.huahun.basic.common.constant.RabbitMqConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName RabbitMqUtils
 * @Description TODO
 * @Author zzh
 * @Date 2021/8/6 14:01
 * @Version 1.0
 */
public class RabbitMqUtils {
    public static Channel getChannel(){
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RabbitMqConstant.RABBITMQ_HOST);
        connectionFactory.setUsername(RabbitMqConstant.RABBITMQ_USERNAME);
        connectionFactory.setPassword(RabbitMqConstant.RABBITMQ_PASSWORD);

        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return channel;
    }
}
