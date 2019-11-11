/*
 * Copyright (c) 2000-2019 All Rights Reserved.
 */
package com.reven.service.impl;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例
 * 
 * @author reven
 * @date 2019年11月8日
 */
@Slf4j
@Component
public class OrderTransactionConsumer {

    private OrderTransactionConsumer() {
        super();
    }

    // 对象创建并赋值之后调用
    @PostConstruct
    public void init() throws MQClientException {
        log.info("OrderTransactionConsumer   @PostConstruct...");
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group_order");

        // Specify name server addresses.
        consumer.setNamesrvAddr("10.1.203.68:9876");

        /**
         * 订阅指定topic下tags分别等于TagA或TagC或TagD
         */
        consumer.subscribe("Topic_ORDER_MSG", "tag_fruit");

        // Register callback to execute on arrival of messages fetched from brokers.
        /**
         * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
         */
        consumer.setConsumeMessageBatchMaxSize(1);
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                log.info("%s Receive New Messages: {} {}", Thread.currentThread().getName(), msgs);
                MessageExt msg = msgs.get(0);
                log.info(new String(msg.getBody()));
                // TODO 待完善消费消息实战代码
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        /**
         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         */
        consumer.start();
        log.info("OrderTransactionConsumer   @PostConstruct end");
    }

}
