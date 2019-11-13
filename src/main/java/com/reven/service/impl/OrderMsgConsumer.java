/*
 * Copyright (c) 2000-2019 All Rights Reserved.
 */
package com.reven.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import com.reven.entity.Dispatch;
import com.reven.service.IDispatchService;
import com.reven.service.IOrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: OrderMsgConsumer
 * @Description: 模拟库存子系统消费订单消息
 * @author reven
 * @date 2019年11月13日
 */
@Slf4j
@Component
public class OrderMsgConsumer {

    @Resource
    private IDispatchService dispatchService;

    private OrderMsgConsumer() {
        super();
    }

    // 对象创建并赋值之后调用
    @PostConstruct
    public void init() throws MQClientException {
        log.info("OrderMsgConsumer   @PostConstruct...");
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
                String keys = msg.getKeys();
                log.info("msg keys:{}", keys);
                log.info("msg body:{}", new String(msg.getBody()));

                try {
                    
                    Dispatch dispatch = dispatchService.findBy("orderNo", keys);
                    if (dispatch != null) {
                        log.info("重复消息,msgId={},msgKeys={}", msg.getMsgId(), msg.getKeys());
                        // 标记已经消费
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } else {
                        // 生产发货单
                        Dispatch dispatchNew = new Dispatch();
                        dispatchNew.setOrderNo(keys);
                        // ... 其他发货信息
                        
                        //模拟保存失败O201911110030已经存在，数据库设置了唯一约束
                        dispatchNew.setOrderNo("O201911110030");
                        dispatchService.save(dispatchNew);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    try {
                        //异常有时候再次消费也不会成功，可以先让程序暂停一段时间，也可以考虑邮件通知相关人员
                        //消费失败如果不能丢弃消息，必须马上排查处理
                        Thread.sleep(60000);
                    } catch (InterruptedException e1) {
                    }
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        /**
         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         */
        consumer.start();
        log.info("OrderMsgConsumer   @PostConstruct end");
    }

}
