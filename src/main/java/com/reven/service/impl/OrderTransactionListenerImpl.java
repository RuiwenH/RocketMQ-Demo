package com.reven.service.impl;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import com.reven.core.ServiceException;
import com.reven.entity.Order;
import com.reven.service.IOrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("orderTransactionListenerImpl")
public class OrderTransactionListenerImpl implements TransactionListener {

    private ConcurrentHashMap<String, Integer> countHashMap = new ConcurrentHashMap<>();

    @Resource
    private IOrderService orderService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("LocalTransaction start," + msg.getTransactionId() + "," + msg.toString());
        // 模拟下订单
        try {
//          模拟本地应用宕机——发送消息，但本地事务未提交。——本地事务调用完成后，打个断点，就会看到MQ发起消息确认请求
            Order order = (Order) arg; // 模拟打断点位置

            // 模拟本地事务失败
            // order.setOrderId(1);// 设置一个已经存在的主键id
            orderService.save(order);
        } catch (Exception e) {
            // 如果报错，外层只会显示Transaction rolled back because it has been marked as
            // rollback-only，不方便排查问题，这里打印一下错误日志
            log.error(e.getMessage());
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        // 注意： 如果这里执行时间比较长，MQ服务器会已经调用了checkLocalTransaction！！
        log.info("LocalTransaction end," + msg.getTransactionId());
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        log.info("checkLocalTransaction," + msg.getTransactionId() + "," + msg.toString());
        String orderNo = msg.getKeys();
        try {
            Order order = orderService.findBy("orderNo", orderNo);
            if (order != null) {
                // 如果存在，则返回COMMIT_MESSAGE
                log.info(
                        "checkLocalTransaction status=COMMIT_MESSAGE," + msg.getTransactionId() + "," + msg.toString());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rollBackOrUnown(msg);
    }

    public LocalTransactionState rollBackOrUnown(MessageExt msg) {
        // 如果本地事务未提交，也查不到数据，不能直接rollback
        // MQ默认1分钟检查一次
        // 查询10次后，才认为本地事务失败，countHashMap最好是替换为redis。
        Integer num = countHashMap.get(msg.getTransactionId());

        if (num != null && ++num > 2) {
            countHashMap.remove(msg.getTransactionId());
            log.info("checkLocalTransaction status=ROLLBACK_MESSAGE," + msg.getTransactionId() + "," + msg.toString());
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        if (num == null) {
            num = new Integer(1);
        }

        countHashMap.put(msg.getTransactionId(), num);
        log.info("checkLocalTransaction status=UNKNOW,times={},msgTransactionId=", num, msg.getTransactionId());
        return LocalTransactionState.UNKNOW;

    }
}