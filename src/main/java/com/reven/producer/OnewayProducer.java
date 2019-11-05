package com.reven.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 适度的可靠性，如日志收集
 * 
 * @author reven
 * @date 2019年10月9日
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("oneway_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("10.1.203.68:9876");
        // Launch the instance.
        producer.start();
        for (int i = 0; i < 10; i++) {
            // Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest" /* Topic */, "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // Call send message to deliver message to one of brokers.
            /**
             * 1、只投递消息一次 <br/>
             * 2、内部一些异常，不重试 <br/>
             * 3、未知异常或broker离线等异常需要developer处理
             */
            producer.sendOneway(msg);

        }
        // Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
