package com.huahun.basic.work.confirm.producer;

import com.huahun.basic.common.constant.RabbitMqConstant;
import com.huahun.basic.common.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @ClassName Producer
 * @Description 三种发布确认策略速度的测试
 * @Author zzh
 * @Date 2021/8/6 16:57
 * @Version 1.0
 */
@Slf4j
public class Producer {
    /**
     * 批量发消息的个数
     */
    public static final Integer MESSAGE_COUNT = 10000;


    /**
     * 单个确认
     * 相同条件下，单个确认模式耗时4871ms
     *
     * @param: []
     * @return: void
     * @author: zzh
     * @date: 2021/8/8
     */
    @Test
    public void publicMessageIndividually(){
        Channel channel = RabbitMqUtils.getChannel();
        try {
            /*
             * 声明队列时指明队列需要持久化
             * 注意：如果一个队列已存在，且没有声明为持久化，那么下面的代码会报错，
             * 必须将其删除后，重新创建并声明为持久化。
             */
            channel.queueDeclare(RabbitMqConstant.RABBITMQ_QUEUE_NAME_WORK_CONFIRM, true, false, false,null);
            //开启发布确认
            channel.confirmSelect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //开始时间
        Long beginTime = System.currentTimeMillis();
        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            try {
                channel.basicPublish("", RabbitMqConstant.RABBITMQ_QUEUE_NAME_WORK_CONFIRM, null, message.getBytes(StandardCharsets.UTF_8));
                if(channel.waitForConfirms()){
                    log.info("消息" + message + "发送成功");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        Long endTime = System.currentTimeMillis();
        log.info("相同条件下，单个确认模式耗时" + (endTime - beginTime) + "ms");

    }

    /**
     * 批量确认
     * 相同条件下，批量确认模式耗时363ms
     * @param: []
     * @return: void
     * @author: zzh
     * @date: 2021/8/8
     */
    @Test
    public void publicMessageBatch(){
        Channel channel = RabbitMqUtils.getChannel();
        try {
            /*
             * 声明队列时指明队列需要持久化
             * 注意：如果一个队列已存在，且没有声明为持久化，那么下面的代码会报错，
             * 必须将其删除后，重新创建并声明为持久化。
             */
            channel.queueDeclare(RabbitMqConstant.RABBITMQ_QUEUE_NAME_WORK_CONFIRM, true, false, false,null);
            //开启发布确认
            channel.confirmSelect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //开始时间
        Long beginTime = System.currentTimeMillis();
        Integer batchSize = 1000;
        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            try {
                channel.basicPublish("", RabbitMqConstant.RABBITMQ_QUEUE_NAME_WORK_CONFIRM, null, message.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(i%batchSize==0){
                try {
                    if(channel.waitForConfirms()){
                        log.info("批量发送了" + batchSize + "消息");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            if(channel.waitForConfirms()){
                log.info("所有消息发送成功");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long endTime = System.currentTimeMillis();
        log.info("相同条件下，批量确认模式耗时" + (endTime - beginTime) + "ms");

    }

    /**
     * 异步发布确认
     * 相同条件下，异步确认模式耗时141ms
     * （实际上更快，因为打印了更多字符）
     * @param: []
     * @return: void
     * @author: zzh
     * @date: 2021/8/8
     */
    @Test
    public void publicMessageAsync(){
        Channel channel = RabbitMqUtils.getChannel();
        try {
            /*
             * 声明队列时指明队列需要持久化
             * 注意：如果一个队列已存在，且没有声明为持久化，那么下面的代码会报错，
             * 必须将其删除后，重新创建并声明为持久化。
             */
            channel.queueDeclare(RabbitMqConstant.RABBITMQ_QUEUE_NAME_WORK_CONFIRM, true, false, false,null);
            //开启发布确认
            channel.confirmSelect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 定义一个线程安全的哈希表，适用于高并发的情况下
         */
        ConcurrentSkipListMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();
        //消息确认成功的回调函数
        ConfirmCallback ackCallback = (deliveryTag, mutiple) ->{
            log.info("确认消息：" + deliveryTag);
            //2.删除掉已经确认的消息，剩下的就是未确认的消息
            if(mutiple) {
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(deliveryTag);
                confirmed.clear();
            }else{
                outstandingConfirms.remove(deliveryTag);
            }
        };
        //消息确认失败的回调函数
        ConfirmCallback nackCallback = (deliveryTag, mutiple) ->{
            //3.此处处理未确认的消息
            String unConfirmedMessage = outstandingConfirms.get(deliveryTag);
            log.info("未确认消息是：" + unConfirmedMessage + ",未确认消息的标记是：" + deliveryTag);
        };

        //准备消息的监听器，监听哪些消息成功了，哪些消息失败了
        channel.addConfirmListener(ackCallback, nackCallback);

        //开始时间
        Long beginTime = System.currentTimeMillis();
        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            try {
                channel.basicPublish("", RabbitMqConstant.RABBITMQ_QUEUE_NAME_WORK_CONFIRM, null, message.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //1.此处记录下所有要发送的消息的总和
            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
        }
        Long endTime = System.currentTimeMillis();
        log.info("相同条件下，异步确认模式耗时" + (endTime - beginTime) + "ms");

    }

}
