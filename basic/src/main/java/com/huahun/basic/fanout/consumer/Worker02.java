package com.huahun.basic.fanout.consumer;

import com.huahun.basic.common.constant.RabbitMqConstant;
import com.huahun.basic.common.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @ClassName Worker02
 * @Description TODO
 * @Author zzh
 * @Date 2021/8/6 14:26
 * @Version 1.0
 */
@Slf4j
public class Worker02 {
    public static void main(String[] args) {
        Channel channel = RabbitMqUtils.getChannel();
        String queueName = null;
        try {
            //声明一个交换机
            channel.exchangeDeclare(RabbitMqConstant.RABBITMQ_EXCHANGE_NAME_FANOUT, "fanout");
            //声明一个随机名字的临时队列
            queueName = channel.queueDeclare().getQueue();
            //绑定交换机与队列
            channel.queueBind(queueName, RabbitMqConstant.RABBITMQ_EXCHANGE_NAME_FANOUT, "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //接收成功的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            log.info("消费者Worker01成功接收消息：" + new String(message.getBody()));
        };

        //取消接收的回调函数
        CancelCallback cancelCallback = (consumerTag) -> {
            log.info("消费者Worker01接收消息被中断");
        };
        try {
            channel.basicConsume(queueName,true,deliverCallback,cancelCallback);
            log.info("消费者Worker01启动完毕，正在等待接受消息...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
