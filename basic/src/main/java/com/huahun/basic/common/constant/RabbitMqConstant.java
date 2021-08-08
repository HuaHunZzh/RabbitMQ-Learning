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

    /**
     * RabbitMQ配置常量
     */
    @Value("${spring.rabbitmq.host}")
    public static final String RABBITMQ_HOST = "192.168.88.130";

    @Value("${spring.rabbitmq.port}")
    public static final String RABBITMQ_PORT = "5672";

    @Value("${spring.rabbitmq.username}")
    public static final String RABBITMQ_USERNAME = "guest";

    @Value("${spring.rabbitmq.password}")
    public static final String RABBITMQ_PASSWORD = "guest";



    /**
     * queue名称常量
     */

    @Value("${myConfig.rabbitmq.queues.simple}")
    public static final String RABBITMQ_QUEUE_NAME_SIMPLE = "queue_simple";

    @Value("${myConfig.rabbitmq.queues.work.polling}")
    public static final String RABBITMQ_QUEUE_NAME_WORK_POLLING = "queue_work_polling";

    @Value("${myConfig.rabbitmq.queues.work.ack}")
    public static final String RABBITMQ_QUEUE_NAME_WORK_ACK = "queue_work_ack";

    @Value("${myConfig.rabbitmq.queues.work.endurance}")
    public static final String RABBITMQ_QUEUE_NAME_WORK_ENDURANCE = "queue_work_endurance";

    @Value("${myConfig.rabbitmq.queues.work.confirm}")
    public static final String RABBITMQ_QUEUE_NAME_WORK_CONFIRM = "queue_work_confirm";

    @Value("${myConfig.rabbitmq.queues.direct.worker01}")
    public static final String RABBITMQ_QUEUE_NAME_DIRECT_WOKER01 = "queue_direct_worker01";

    @Value("${myConfig.rabbitmq.queues.direct.worker02}")
    public static final String RABBITMQ_QUEUE_NAME_DIRECT_WOKER02 = "queue_direct_worker02";

    @Value("${myConfig.rabbitmq.queues.topic.worker01}")
    public static final String RABBITMQ_QUEUE_NAME_TOPIC_WOKER01 = "queue_topic_worker01";

    @Value("${myConfig.rabbitmq.queues.topic.worker02}")
    public static final String RABBITMQ_QUEUE_NAME_TOPIC_WOKER02 = "queue_topic_worker02";

    /**
     * exchange名称常量
     */
    @Value("${myConfig.rabbitmq.exchanges.fanout}")
    public static final String RABBITMQ_EXCHANGE_NAME_FANOUT = "exchange_fanout";

    @Value("${myConfig.rabbitmq.exchanges.direct}")
    public static final String RABBITMQ_EXCHANGE_NAME_DIRECT = "exchange_direct";

    @Value("${myConfig.rabbitmq.exchanges.topic}")
    public static final String RABBITMQ_EXCHANGE_NAME_TOPIC = "exchange_topic";


    /**
     * routingKey名称常量
     */
    @Value("${myConfig.rabbitmq.routingKeys.direct.worker01.key1}")
    public static final String RABBITMQ_ROUTINGKEYS_NAME_DIRECT_WORKER01_KEY1 = "key1";

    @Value("${myConfig.rabbitmq.routingKeys.direct.worker01.key2}")
    public static final String RABBITMQ_ROUTINGKEYS_NAME_DIRECT_WORKER01_KEY2 = "key2";

    @Value("${myConfig.rabbitmq.routingKeys.direct.worker02.key1}")
    public static final String RABBITMQ_ROUTINGKEYS_NAME_DIRECT_WORKER02_KEY1 = "key1";


    @Value("${myConfig.rabbitmq.routingKeys.topic.worker01.key1}")
    public static final String RABBITMQ_ROUTINGKEYS_NAME_TOPIC_WORKER01_KEY1 = "*.orange.*";

    @Value("${myConfig.rabbitmq.routingKeys.topic.worker02.key1}")
    public static final String RABBITMQ_ROUTINGKEYS_NAME_TOPIC_WORKER02_KEY1 = "*.*.rabbit";

    @Value("${myConfig.rabbitmq.routingKeys.topic.worker02.key2}")
    public static final String RABBITMQ_ROUTINGKEYS_NAME_TOPIC_WORKER02_KEY2 = "lazy.#";

}
