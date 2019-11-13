package com.reven.service.impl;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reven.core.AbstractService;
import com.reven.entity.Order;
import com.reven.mapper.OrderMapper;
import com.reven.service.IOrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by CodeGenerator on 2019/11/11.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl extends AbstractService<Order> implements IOrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderTransactionProducerFactory orderTransactionProducerFactory;

    @Override
    @Transactional(rollbackFor = Throwable.class) //需要在这里添加事务
    public void saveWithMQ(Order order) throws UnsupportedEncodingException {
        
        // 订单消息的body，仅限参考，下游尽可能通过订单编号，反查订单微服务获取订单详情
        Message msg = new Message("Topic_ORDER_MSG", "tag_fruit",
                (order.toString()).getBytes(RemotingHelper.DEFAULT_CHARSET));
        // 每个消息在业务局面的唯一标识码，要设置到keys字段，方便将来定位消息丢失问题。
        // 服务器会为每个消息创建索引（哈希索引），应用可以通过topic，key来查询返条消息内容，以及消息被谁消费。
        // 由于是哈希索引，请务必保证key尽可能唯一，返样可以避免潜在的哈希冲突。
        msg.setKeys(order.getOrderNo());
        try {
            SendResult sendResult = orderTransactionProducerFactory.getProducer().sendMessageInTransaction(msg, order);
            
            log.info(sendResult.toString());
            // 消息发送成功或者失败，要打印消息日志，务必要打印sendresult和key字段。
        } catch (MQClientException e) {
            log.error("错误消息：{}", e.getMessage(), e);
            // 对于发送顺序消息的应用，由于顺序消息的局限性，可能会涉及到主备自动切换问题.
            // 所以如果sendresult中的status字段不等于SEND_OK，就应该尝试重试。对于其他应用，则没有必要这样。

            // 对于消息不可丢失应用，务必要有消息重发机制
            // 例如果消息发送失败，存储到数据库能有定时程序尝试重或者人工触。
        }
    }

}
