package com.huahun.basic.work.endurance.consumer;

import com.huahun.basic.common.constant.RabbitMqConstant;
import com.huahun.basic.common.utils.RabbitMqUtils;
import com.huahun.basic.common.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @ClassName Worker02
 * @Description TODO
 * @Author zzh
 * @Date 2021/8/6 17:27
 * @Version 1.0
 */
@Slf4j
public class Worker02 {
    public static void main(String[] args) {
        Channel channel = RabbitMqUtils.getChannel();
        //接收成功的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            /*
             *    沉睡是为了模拟上产环境中不同消费者延迟不同。
             *    队列工作在工作模式（即队列轮询分发消息给各个消费者）。
             *    当轮到消费者Worker02接收消息时，由于等待时间较长，可在Worker02接收到消息之前，通过手动关闭线程来模拟生产环境中服务器的宕机现象，
             * 此时即可看到手动应答“消息自动重新入队”的效果：即使消费者Worker02宕机了，消息仍然能够自动重新发往消费者Worker01处理。
             */
            SleepUtils.sleep(30);
            log.info("消费者Worker02成功接收消息：" + new String(message.getBody()));
            //手动应答，且不批量应答未应答消息
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };

        //取消接收的回调函数
        CancelCallback cancelCallback = (consumerTag) -> {
            log.info("消费者Worker02接收消息被中断");
        };
        try {
            /*
             *      设置不公平分发
             *      参数prefetchCount的意思：类似于该通道的最大暂存（即未被确认的）消息数量。比如若prefetchCount=1，当消费者收到一条消息且
             * 来不起及处理，即该消息未被确认，则该消息会被暂存在通道上。此时若再收到一条消息，由于超过预取值prefetchCount，该消息会被队列分发到
             * 其它消费者。当然，此时队列不再严格遵循轮询策略。在极端情况下，所有的消费者都没有完成手头上的任务，队列还在不停地添加新任务，队列有
             * 可能遇到被撑满地情况，这个时候就只能添加新的消费者或者改变其它存储任务的策略。
             */
            channel.basicQos(1);
            //采用手动应答
            channel.basicConsume(RabbitMqConstant.RABBITMQ_QUEUE_NAME_WORK_ENDURANCE,false,deliverCallback,cancelCallback);
            log.info("消费者Worker02启动完毕，正在等待接受消息...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}