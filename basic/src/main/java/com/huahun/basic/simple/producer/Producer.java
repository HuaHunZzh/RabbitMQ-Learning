package com.huahun.basic.simple.producer;

import com.huahun.basic.common.constant.RabbitMqConstant;
import com.huahun.basic.common.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @ClassName Producer
 * @Description 发送消息
 * @Author zzh
 * @Date 2021/8/6 14:18
 * @Version 1.0
 */
@Slf4j
public class Producer {

    public static void main(String[] args) {
        log.info("输入你想发送的消息：");
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        try {
            Channel channel = RabbitMqUtils.getChannel();
            channel.queueDeclare(RabbitMqConstant.RABBITMQ_QUEUE_NAME_SIMPLE, false, false, false,null);
            channel.basicPublish("", RabbitMqConstant.RABBITMQ_QUEUE_NAME_SIMPLE, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
