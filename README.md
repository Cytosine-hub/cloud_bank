# cloud_bank
手机直销银行，后端微服务版本
按照消费者和提供者对项目进行微服务拆分。
功能模块共分为：账户、理财、用户、产品和核心模块
网关采用spring gateway
注册中心使用 nacos
分布式事务使用seata
服务调用使用openfeign
自定义starter，各个模块基于starter进行拓展
