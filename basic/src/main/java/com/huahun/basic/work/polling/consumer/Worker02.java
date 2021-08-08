package com.huahun.basic.work.polling.consumer;

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
        //接收成功的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            log.info("消费者Worker02成功接收消息：" + new String(message.getBody()));
        };

        //取消接收的回调函数
        CancelCallback cancelCallback = (consumerTag) -> {
            log.info("消费者Worker02接收消息被中断");
        };
        try {
            Channel channel = RabbitMqUtils.getChannel();
            channel.basicConsume(RabbitMqConstant.RABBITMQ_QUEUE_NAME_WORK_POLLING,true,deliverCallback,cancelCallback);
            log.info("消费者Worker02启动完毕，正在等待接受消息...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
