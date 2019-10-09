package com.reven.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @ClassName:  SyncProducer   
 * @Description: 可靠同步发送消息，适用场景：消息通知、短信通知、
 * @author reven
 * @date   2019年10月9日
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("sync_group_test");
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
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        // Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
