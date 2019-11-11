/*
 * Copyright (c) 2000-2019 All Rights Reserved.
 */
package com.reven.service.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
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
@Scope(value = "singleton") // 指定为单例模式
public class OrderTransactionProducerFactory{

    private TransactionMQProducer producer;

    @Resource
    private OrderTransactionListenerImpl transactionListener;
    
    private OrderTransactionProducerFactory() {
        super();
    }

    // 对象创建并赋值之后调用
    @PostConstruct
    public void init() throws MQClientException {
        log.info("OrderTransactionProducer   @PostConstruct...");
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("client-transaction-msg-check-thread");
                        return thread;
                    }
                });

        producer = new TransactionMQProducer("transaction_unique_group_name");
        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
        producer.setNamesrvAddr("10.1.203.68:9876");
        producer.start();
        log.info("OrderTransactionProducer   @PostConstruct end");
    }
    
    

    /**
     * @return the producer
     */
    public TransactionMQProducer getProducer() {
        return producer;
    }


    // 容器移除对象之前
    @PreDestroy
    public void destroy() {
        log.info("OrderTransactionProducer   @PreDestroy...");
        producer.shutdown();
        log.info("OrderTransactionProducer   @PreDestroy end");
    }

}
