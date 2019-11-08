package com.reven.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reven.core.AbstractService;
import com.reven.entity.Demo;
import com.reven.mapper.DemoMapper;
import com.reven.service.IDemoService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author reven
 */
@Slf4j
@Service
@Transactional
public class DemoServiceImpl extends AbstractService<Demo> implements IDemoService {
    @Resource
    private DemoMapper demoMapper;

    @Override
    public void saveOrder(Demo demo) {
        /**
         * 演示发送事务消息
         * 1、发送半消息
         * 2、
         */
        
        try {
            // 只有収送消息设置了tags，消费方在订阅消息时，才可以利用tags在broker做消息过滤。
            Message msg = new Message("TopicTest1234", tags[i % tags.length], "KEY" + i,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 每个消息在业务局面的唯一标识码，要设置到keys字段，方便将来定位消息丢失问题。
            msg.setKeys("tmsg_" + i);
            // 服务器会为每个消息创建索引（哈希索引），应用可以通过topic，key来查询返条消息内容，以及消息被谁消费。
            // 由于是哈希索引，请务必保证key尽可能唯一，返样可以避免潜在的哈希冲突。
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            log.info("%s%n", sendResult);
            // 消息収送成功戒者失败，要打印消息日志，务必要打印sendresult和key字段。
            Thread.sleep(10);
        } catch (MQClientException | UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            // 对于发送顺序消息的应用，由于顺序消息的局限性，可能会涉及到主备自动切换问题.
            // 所以如果sendresult中的status字段不等于SEND_OK，就应该尝试重试。对于其他应用，则没有必要这样。

            // 对于消息不可丢失应用，务必要有消息重发机制
            // 例如果消息发送失败，存储到数据库能有定时程序尝试重或者人工触。
        }
    }
    @Override
    public PageInfo<Demo> findAll(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Demo> list = super.findAll();
        PageInfo<Demo> pageInfo = new PageInfo<Demo>(list);
        return pageInfo;
    }


}
