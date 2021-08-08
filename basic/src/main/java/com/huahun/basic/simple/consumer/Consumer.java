package com.huahun.basic.simple.consumer;

import com.huahun.basic.common.constant.RabbitMqConstant;
import com.huahun.basic.common.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @ClassName Worker01
 * @Description TODO
 * @Author zzh
 * @Date 2021/8/6 14:26
 * @Version 1.0
 */
@Slf4j
public class Consumer {
    public static void main(String[] args) {
        //接收成功的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            log.info("成功接收消息：" + new String(message.getBody()));
        };

        //取消接收的回调函数
        CancelCallback cancelCallback = (consumerTag) -> {
            log.info("接收消息被中断");
        };
        log.info("消费者Consumer启动完毕，正在等待接受消息...");
        try {
            Channel channel = RabbitMqUtils.getChannel();
            channel.basicConsume(RabbitMqConstant.RABBITMQ_QUEUE_NAME_SIMPLE,true,deliverCallback,cancelCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
