# RocketMQ事务消息实战代码

# 软件版本
* springboot 2.1.1
* rocketmq 4.5.2

# 事务消息实战场景
* 订单子系统生成订单后，通知库存子系统发货，使用RocketMQ的发送事务消息，保证消息必须发送成功。
* 模拟库存子系统消费订单消息——消息去重校验

# 测试
* 下订单并发送消息，http://localhost:8080/order/add?userId=7&date=2019-11-11&orderNo=O201911110001
* 模拟本地事务失败，见注释代码OrderTransactionListenerImpl.java   //order.setOrderId(1);
* 模拟本地应用宕机本地事务未提交——发送消息，但本地事务未提交。——本地事务调用过程中，打个断点，将应用关闭，启动应用后会看到MQ发起消息确认请求（大概1分钟后），最终事务消息回滚。
* 模拟本地应用宕机消息未commit——发送消息，本地事务已经提交，未commit消息。————本地事务调用完成后，打个断点，断开消息服务器的网络（消息无法提交），执行断点后（本地事务提交），将应用关闭，启动应用后，MQ发起消息确认请求，最终事务消息提交。注意生产环境要启动多个节点，不然在本节点宕机后，MQ在有限次数内无法确认消息的状态时，最终会标记为事务回滚。
* 模拟发送重复消息，测试消费端是否去重
* 模拟消费失败，休息一分钟。