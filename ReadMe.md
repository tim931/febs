### 编辑器记得安装lombok插件

### FEBS-Shiro 2.0
![https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square](https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/springboot-2.1.8-yellow.svg?style=flat-square](https://img.shields.io/badge/springboot-2.1.8-yellow.svg?style=flat-square)
![https://img.shields.io/badge/shiro-1.4.0-orange.svg?longCache=true&style=flat-square](https://img.shields.io/badge/shiro-1.4.0-orange.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/layui-2.5.5-brightgreen.svg?longCache=true&style=flat-square](https://img.shields.io/badge/layui-2.5.5-brightgreen.svg?longCache=true&style=flat-square)

FEBS-Shiro是一款简单高效的后台权限管理系统，使用Spring Boot，Shiro和Layui构建。FEBS意指：**F**ast，**E**asy use，**B**eautiful和**S**afe。相信无论作为企业级应用，私活开发脚手架或者权限系统构建学习，FEBS-Shiro都会是一个不错的选择。

### 演示地址

[https://shiro.mrbird.cn:8080](https://shiro.mrbird.cn:8080)

演示环境账号密码：

账号 | 密码| 权限
---|---|---
scott | 1234qwer | 注册账户，拥有查看，新增权限（新增用户除外）和导出Excel权限


本地部署账号密码：

账号 | 密码| 权限
---|---|---
mrbird | 1234qwer |超级管理员，拥有所有增删改查权限
scott | 1234qwer | 注册账户，拥有查看，新增权限（新增用户除外）和导出Excel权限
micaela | 1234qwer |系统监测员，负责整个系统监控模块
Jana   | 1234qwer  |跑批人员，负责任务调度跑批模块

### 更多版本
当前分支为2.0版本，页面采用Layui全新构建，FEBS的其他版本：

名称 | 描述| 地址
---|---|---
FEBS-Shiro 1.x | Spring Boot 2.0.4 & Shiro1.4.0 权限管理系统（单页）。 | [https://github.com/wuyouzhuguli/FEBS-Shiro/tree/mysql](https://github.com/wuyouzhuguli/FEBS-Shiro/tree/mysql)
FEBS-Security | Spring Boot 2.0.4 & Spring Security 5.0.7 权限管理系统（单页）。 | [https://github.com/wuyouzhuguli/FEBS-Security](https://github.com/wuyouzhuguli/FEBS-Security)
FEBS-Vue | FEBS-Shiro前后端分离版本，前端架构采用Vue全家桶。 | [https://github.com/wuyouzhuguli/FEBS-Vue](https://github.com/wuyouzhuguli/FEBS-Vue)

### 系统模块
系统功能模块组成如下所示：
```
├─系统管理
│  ├─用户管理
│  ├─角色管理
│  ├─菜单管理
│  └─部门管理
├─系统监控
│  ├─在线用户
│  ├─系统日志
│  ├─登录日志
│  ├─Redis监控
│  ├─Redis终端
│  ├─请求追踪
│  ├─系统信息
│  │  ├─JVM信息
│  │  ├─TOMCAT信息
│  │  └─服务器信息
├─任务调度
│  ├─定时任务
│  └─调度日志
├─代码生成
│  ├─生成配置
│  ├─代码生成
└─其他模块
   ├─FEBS组件
   │  ├─表单组件
   │  ├─表单组合
   │  ├─FEBS工具
   │  ├─系统图标
   │  └─其他组件
   ├─APEX图表
   ├─高德地图
   └─导入导出
```
