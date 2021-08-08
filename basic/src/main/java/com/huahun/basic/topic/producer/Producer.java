package com.huahun.basic.topic.producer;

import com.huahun.basic.common.constant.RabbitMqConstant;
import com.huahun.basic.common.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
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
        Map<String, String> bindingKeyMap = new HashMap();
        bindingKeyMap.put("quick.orange.rabbit", "被队列RABBITMQ_QUEUE_NAME_TOPIC_WOKER01和RABBITMQ_QUEUE_NAME_TOPIC_WOKER02收到");
        bindingKeyMap.put("lazy.orange.elephant", "被队列RABBITMQ_QUEUE_NAME_TOPIC_WOKER01和RABBITMQ_QUEUE_NAME_TOPIC_WOKER02收到");
        bindingKeyMap.put("quick.orange.fox", "被队列RABBITMQ_QUEUE_NAME_TOPIC_WOKER01收到");
        bindingKeyMap.put("lazy.brown.fox", "被队列RABBITMQ_QUEUE_NAME_TOPIC_WOKER02收到");
        bindingKeyMap.put("lazy.pink.rabbit", "虽然满足两个绑定但只被队列被队列RABBITMQ_QUEUE_NAME_TOPIC_WOKER02接收一次");
        Channel channel = RabbitMqUtils.getChannel();
        try {
            //声明一个交换机
            channel.exchangeDeclare(RabbitMqConstant.RABBITMQ_EXCHANGE_NAME_TOPIC, BuiltinExchangeType.TOPIC);
            //声明一个队列
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("输入你想发送的消息：");
        while(scanner.hasNextLine()) {
            String message = scanner.nextLine();
            try {
                for(Map.Entry<String, String> bindingKeyEntry: bindingKeyMap.entrySet()) {
                    channel.basicPublish(RabbitMqConstant.RABBITMQ_EXCHANGE_NAME_TOPIC,
                            bindingKeyEntry.getKey(),
                            null, message.getBytes(StandardCharsets.UTF_8));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("输入你想发送的消息：");
        }
    }
}
