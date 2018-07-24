# spring-boot-quartz

基于spring-boot+quartz的CRUD动态任务管理系统，适用于中小项目。
 
## 几种任务调度

- Timer，简单无门槛，一般也没人用。

- spring @Scheduled注解，一般集成于项目中，小任务很方便。

- 开源工具 Quartz，分布式集群开源工具，以下两个分布式任务应该都是基于Quartz实现的，可以说是中小型公司必选，当然也视自身需求而定。

- 分布式任务 XXL-JOB，是一个轻量级分布式任务调度框架，支持通过 Web 页面对任务进行 CRUD 操作，支持动态修改任务状态、暂停/恢复任务，以及终止运行中任务，支持在线配置调度任务入参和在线查看调度结果。

- 分布式任务 Elastic-Job，是一个分布式调度解决方案，由两个相互独立的子项目 Elastic-Job-Lite 和 Elastic-Job-Cloud 组成。定位为轻量级无中心化解决方案，使用 jar 包的形式提供分布式任务的协调服务。支持分布式调度协调、弹性扩容缩容、失效转移、错过执行作业重触发、并行调度、自诊。


## 开发环境

JDK1.7、Maven、Eclipse

## 技术栈

SpringBoot1.5.2、thymeleaf、quartz2.3.0、coment4j、iview、vue、layer、AdminLTE、bootstrap

## 启动说明
- 项目使用的数据库为MySql，选择resources/sql中的tables_mysql_innodb.sql + log.sql 文件初始化数据库信息。
- 在resources/application.properties文件中替换为自己的数据源。
- 运行Application main方法，启动项目。
- 项目访问地址：http://localhost:8088/quartz
- API接口地址：http://localhost:8088/quartz/swagger-ui.html


## 已实现功能

- 任务列表
- 任务新增和修改
- 任务执行
- 表达式生成器(集成：https://gitee.com/finira/cronboot) 建议使用--> http://cron.qqe2.com/
- 任务移除
- Job中注入service为空的问题
- 系统启动，如果数据库任务为零则初始化测试任务，用于测试
- 日志展示
- 定时任务执行情况的监视
- 任务完成成功与否由服务端推送到前端提示

## 待集成功能

- 系统登录以及权限管理
- 任务停止和开启
- 任务列表搜索以及分页


