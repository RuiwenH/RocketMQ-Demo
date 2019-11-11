# RocketMQ事务消息实战代码

# 软件版本
* springboot 2.1.1
* rocketmq 4.5.2

# 事务消息实战场景
* 订单子系统生成订单后，通知购物车子系统删除商品，使用RocketMQ的事务发送消息。

# 测试
* 下订单并发送消息，http://localhost:8080/order/add?userId=7&date=2019-11-11&orderNo=O201911110001
* 模拟其他系统消费消息，见OrderTransactionConsumer.java
* 模拟本地事务失败，见注释代码OrderTransactionListenerImpl.java   //order.setOrderId(1);
* 模拟本地应用宕机——发送消息，但本地事务未提交。——本地事务调用完成后，打个断点，默认1分钟后就会看到MQ发起消息确认请求，最终事务消息回滚
* 模拟本地应用宕机——发送消息，本地事务已经提交，未commit消息。————本地事务调用完成后，打个断点，断开消息服务器的网络（消息无法提交），执行断点后（本地事务提交），将应用关闭，启动应用后，MQ发起消息确认请求，最终事务消息提交。