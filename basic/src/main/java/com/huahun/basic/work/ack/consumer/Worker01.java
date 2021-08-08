package com.huahun.basic.work.ack.consumer;

import com.huahun.basic.common.constant.RabbitMqConstant;
import com.huahun.basic.common.utils.RabbitMqUtils;
import com.huahun.basic.common.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @ClassName Worker01
 * @Description TODO
 * @Author zzh
 * @Date 2021/8/6 17:27
 * @Version 1.0
 */
@Slf4j
public class Worker01 {
    public static void main(String[] args) {
        Channel channel = RabbitMqUtils.getChannel();
        //接收成功的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            /*
             * 沉睡是为了模拟上产环境中不同消费者延迟不同。
             */
            SleepUtils.sleep(1);
            log.info("消费者Worker01成功接收消息：" + new String(message.getBody()));
            //手动应答，且不批量应答未应答消息
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };

        //取消接收的回调函数
        CancelCallback cancelCallback = (consumerTag) -> {
            log.info("消费者Worker01接收消息被中断");
        };
        try {
            //采用手动应答
            channel.basicConsume(RabbitMqConstant.RABBITMQ_QUEUE_NAME_WORK_ACK,false,deliverCallback,cancelCallback);
            log.info("消费者Worker01启动完毕，正在等待接受消息...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}