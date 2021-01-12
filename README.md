#人员综合管理评价系统——基于开源bladex实现
**此系统可以用作公司内部人员评价管理，当前作为作者大学毕业设计软件系统。**  
此产品可以用于员工定期填写近期工作内容（包括各项指标），通过公司拟定的规则计算出绩效。同时还可以实现请假/加班流程审批，最终可以通过当前绩效和请假/加班情况计算出每个员工的应发工资。



## Bladex官网
* 官网地址：[https://bladex.vip](https://bladex.vip)

## SpringBlade微服务开发平台
* 采用前后端分离的模式，前端开源两个框架：[Sword](https://gitee.com/smallc/Sword) (基于 React、Ant Design)、[Saber](https://gitee.com/smallc/Saber) (基于 Vue、Element-UI)
* 后端采用SpringCloud全家桶，并同时对其基础组件做了高度的封装，单独开源出一个框架：[BladeTool](https://github.com/chillzhuang/blade-tool)
* [BladeTool](https://github.com/chillzhuang/blade-tool)已推送至Maven中央库，直接引入即可，减少了工程的臃肿，也可更注重于业务开发
* 集成Sentinel从流量控制、熔断降级、系统负载等多个维度保护服务的稳定性。
* 注册中心、配置中心选型Nacos，为工程瘦身的同时加强各模块之间的联动。
* 使用Traefik进行反向代理，监听后台变化自动化应用新的配置文件。
* 极简封装了多租户底层，用更少的代码换来拓展性更强的SaaS多租户系统。
* 借鉴OAuth2，实现了多终端认证系统，可控制子系统的token权限互相隔离。
* 借鉴Security，封装了Secure模块，采用JWT做Token认证，可拓展集成Redis等细颗粒度控制方案。
* 稳定生产了两年，经历了从Camden -> Hoxton的技术架构，也经历了从fat jar -> docker -> k8s + jenkins的部署架构
* 项目分包明确，规范微服务的开发模式，使包与包之间的分工清晰。

## 架构图
<img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-framework.png"/>

## 工程结构
``` 
SpringBlade
├── blade-auth -- 授权服务提供
├── blade-common -- 常用工具封装包
├── blade-gateway -- Spring Cloud 网关
├── blade-ops -- 运维中心
├    ├── blade-admin -- spring-cloud后台管理
├    ├── blade-develop -- 代码生成
├    ├── blade-resource -- 资源管理
├    ├── blade-seata-order -- seata分布式事务demo
├    ├── blade-seata-storage -- seata分布式事务demo
├── blade-service -- 业务模块
├    ├── blade-desk -- 工作台模块 
├    ├── blade-log -- 日志模块 
├    ├── blade-log -- 主要功能模块
├    ├── blade-system -- 系统模块 
├    └── blade-user -- 用户模块 
├── blade-service-api -- 业务模块api封装
├    ├── blade-desk-api -- 工作台api 
├    ├── blade-dict-api -- 字典api 
├    ├── blade-system-api -- 系统api 
└──  └── blade-user-api -- 用户api 
```

##主要功能模块划分
此系统主要功能可以拆分为五个系统模块以优先级顺序排列为
- 用于控制不同级别的用户登录系统呈现出不同的界面以及提供不同的功能。  
- 目前用户等级可分为四种：普通员工、经理/总监、人事、管理员

1. ###员工管理/工资管理模块
- 此模块仅人事和管理员可进入，用于添加/删除/修改人员信息，填写人员基础工资，修改绩效评定标准。 
2. ###员工绩效填写/审核/计算模块
- 普通员工进入此模块填写自己的各项指标和工作内容，同时可以查看自己最终绩效评价。  
- 经理/总监进入此模块除填写内容外还可以对下级进行审批，最终可以使普通员工的填写内容通过或者通知修改（也可以直接修改）。
人事进入此模块同普通员工。  
- 管理员进入此模块同经理/总监。
4. ###员工请假/加班填写/审核模块
- 普通员工进入此模块填写自己的请假/加班原因，时间，并可以查看是否通过。
- 经理/总监进入此模块除填写内容外还可以对下级进行审批，最终可以使普通员工的填写内容通过或者通知修改（也可以直接修改）。
- 人事进入此模块同普通员工。
- 管理员进入此模块同经理/总监。

5. ###工资核算功能模块
- 此模块仅人事和管理员可进入，可以查看员工绩效评定情况和加班/请假情况，可以选定员工并意见计算工资。
