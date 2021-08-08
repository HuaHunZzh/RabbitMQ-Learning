package com.huahun.basic.work.endurance.producer;

import com.huahun.basic.common.constant.RabbitMqConstant;
import com.huahun.basic.common.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @ClassName Producer
 * @Description 消息在手动应答时不丢失
 * @Author zzh
 * @Date 2021/8/6 16:57
 * @Version 1.0
 */
@Slf4j
public class Producer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Channel channel = RabbitMqUtils.getChannel();
        try {
            /*
             * 声明队列时指明队列需要持久化
             * 注意：如果一个队列已存在，且没有声明为持久化，那么下面的代码会报错，
             * 必须将其删除后，重新创建并声明为持久化。
             */
            channel.queueDeclare(RabbitMqConstant.RABBITMQ_QUEUE_NAME_WORK_ACK, true, false, false,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("输入你想发送的消息：");
        while(scanner.hasNextLine()) {
            String message = scanner.nextLine();
            try {
                /*
                 * 消息持久化是在生产者发送消息时就要声明的。
                 * 注意：将消息标记为持久化并不能完全保证丢失消息。尽管它告诉RabbitMQ将消息保存到外存，但是这里依然存在当消息刚准备存储在磁盘
                 * 的时候，但是还没有存储完，消息还在缓存的一个间隔点。此时并没有真正写入外存，持久性保证并不强。
                 */
                channel.basicPublish("", RabbitMqConstant.RABBITMQ_QUEUE_NAME_WORK_ACK, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("输入你想发送的消息：");
        }
    }
}
