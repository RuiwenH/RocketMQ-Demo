package com.reven.service.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import com.reven.entity.Demo;
import com.reven.service.IDemoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("demoTransactionListenerImpl")
public class DemoTransactionListenerImpl implements TransactionListener {
    @Resource
    private IDemoService demoService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//        localTrans.put(msg.getTransactionId(), status);
        //模拟下订单
        Demo model =new Demo();
        model.setName("订单001");
        model.setKey("order");
        demoService.save(model );
        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        log.info(msg.getTransactionId()+","+msg.toString());
//            case 0:
//                return LocalTransactionState.UNKNOW;
//            case 1:
//                return LocalTransactionState.COMMIT_MESSAGE;
//            case 2:
//                return LocalTransactionState.ROLLBACK_MESSAGE;
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}