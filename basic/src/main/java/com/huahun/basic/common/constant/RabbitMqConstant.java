package com.huahun.basic.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName RabbitMqConstant
 * @Description RabbitMq常量
 * @Author zzh
 * @Date 2021/8/6 13:57
 * @Version 1.0
 */
@Component
public class RabbitMqConstant {
    @Value("${spring.rabbitmq.host}")
    public static final String RABBITMQ_HOST = "192.168.88.130";

    @Value("${spring.rabbitmq.port}")
    public static final String RABBITMQ_PORT = "5672";

    @Value("${spring.rabbitmq.username}")
    public static final String RABBITMQ_USERNAME = "guest";

    @Value("${spring.rabbitmq.password}")
    public static final String RABBITMQ_PASSWORD = "guest";

    @Value("${myConfig.rabbitmq.queues.simple}")
    public static final String RABBITMQ_QUEUE_NAME_SIMPLE = "queue_simple";

    @Value("${myConfig.rabbitmq.queues.work.polling}")
    public static final String RABBITMQ_QUEUE_NAME_WORK_POLLING = "queue_work_polling";

    @Value("${myConfig.rabbitmq.queues.work.ack}")
    public static final String RABBITMQ_QUEUE_NAME_WORK_ACK = "queue_work_ack";
}
