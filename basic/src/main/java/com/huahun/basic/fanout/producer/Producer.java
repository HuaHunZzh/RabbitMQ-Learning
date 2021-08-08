package com.huahun.basic.fanout.producer;

import com.huahun.basic.common.constant.RabbitMqConstant;
import com.huahun.basic.common.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
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
            //声明一个交换机
            channel.exchangeDeclare(RabbitMqConstant.RABBITMQ_EXCHANGE_NAME_FANOUT, "fanout");
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("输入你想发送的消息：");
        while(scanner.hasNextLine()) {
            String message = scanner.nextLine();
            try {
                channel.basicPublish(RabbitMqConstant.RABBITMQ_EXCHANGE_NAME_FANOUT, "", null, message.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("输入你想发送的消息：");
        }
    }
}
