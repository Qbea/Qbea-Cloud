# Qbea
# Quickly Boot Enterprise Application
致力于解决  Spring Cloud 企业级应用开发、测试、打包、部署框架



应用类型 | 选型 | 版本
---- | --- | ---
框架架构 | Spring Cloud | Greenwich.SR2
版本控制 | Git | 待定
依赖控制 | Maven | 待定
持续集成 | Jenkins | 待定
应用部署 | Linux/Docker | CentOS 7
配置中心 | Apollo | 待定
微服务组件 | Netflix | 待定
缓存 | Redis | 待定
数据源 | MySQL | 5.7
任务调度 | Quartz | 待定
授权认证 | Oauth2 + Spring Security | 待定
分布式事务 | Seata | 1.0
前端框架 | vue+ElementUI | vue 2.0
实时计算 | flink | 待定
工作流 | activiti | 待定
规则引擎 | aviator | 待定

RoadMap:

1.0.0 Release

application | owner | feature
---- | --- | ---
identity(认证中心)  | RadishT | 用户体系管理（注册用户、匿名用户、菜单权限）
qbea-ui(前端)  | hifangfang | FYI
decition-platform | RadishT | 决策平台（指标、规则）
decition-engine | RadishT | 规则引擎，aviator 
decition-flow | RadishT | activiti工作流，集成规则引擎
