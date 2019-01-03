## Shiro权限认证&系统设计

V1.0.0

| 版本&日期  | 修订内容 |
| ---------- | -------- |
| 2018-11-11 |          |
|            |          |
|            |          |

[TOC]

### 业务概述

​	在springBoot框架中实现权限认证和系统设计通用技术。

### 数据库设计

数据库:shiro

- 概述

  此数据库分为三类数据类型即：用户表(user_account)、角色表（user_role）、权限表(user_permission)。

- 常用表定义及sql语句

  - 表名：user_account_info 用户基本信息表

    - 描述：

    - 字段定义

      | 字段              | 字段描述                                      | 字段类型      | 备注                  |
      | ----------------- | --------------------------------------------- | ------------- | --------------------- |
      | user_id           | 用户主键id                                    | int(8)        | 自增长主键            |
      | mobile_number     | 用户电话号码                                  | varchar(15)   |                       |
      | email             | 用户电子邮件                                  | varchar(256)  |                       |
      | country_code      | 用户国码                                      | int(12)       |                       |
      | display_name      | 用户别名                                      | varchar(1240) | 不能为空              |
      | parent_id         | 用户父级id                                    | varchar(8)    | 默认为-1              |
      | security_password | 用户安全密码                                  | varchar(256)  | 唯一                  |
      | salt              | 用户密码盐                                    | varchar(12)   | 不能为空              |
      | statu             | 用户状态（1、黑名单；2、白名单；3、正常用户） | tinyint       | 默认为3               |
      | modify_name       | 用户信息修改时间                              | timestamp     | 默认current_timestamp |
      | create_time       | 用户信息创建时间                              | datetime      | 不能为空              |

    - sql语句

    ```mysql
    drop table if exists user_account_info;
    create table user_account_info(
    	user_id int(8) auto_increment primary key comment "用户主键id",
        mobile_number varchar(15) comment "用户电话号码",
        email varchar(256) comment "用户电子邮件",
        country_code int(12) comment "国码",
        display_name varchar(128) not null comment "用户别名",
        parent_id int(8) default -1 comment "用户父级id",
        security_password varchar(256) not null comment "系统安全密码",
        salt varchar(12) not null comment "用户密码盐",
        statu tinyint default 3 comment "用户状态",
        modify_name timestamp default current_timestamp on update current_timestamp comment "账户更改时间",
        create_time datetime not null comment "账户确认生成时间"
    )ENGINE=InnoDB auto_increment=1001 DEFAULT CHARSET=utf8;
    alter table user_account_info add unique(display_name);
    ```



  - 表名：user_account_modify 用户信息修改表

    - 描述：针对用户信息进行修改的记录信息

    - 字段定义

      | 字段         | 字段描述                                      | 字段类型     | 备注                  |
      | ------------ | --------------------------------------------- | ------------ | --------------------- |
      | ua_modify_id | 修改编号                                      | int(8)       | 从0开始自增长         |
      | user_id      | 用户主键id                                    | int(8)       | 自增长主键            |
      | type         | 修改类型（1、电话号码：2、电子邮箱：3、密码） | tinyint      | not null              |
      | old_msg      | 老信息                                        | varchar(256) | not null              |
      | new_msg      | 新信息                                        | varchar(256) |                       |
      | modify_name  | 用户信息修改时间                              | timestamp    | 默认current_timestamp |
      | create_time  | 用户信息创建时间                              | datetime     | 不能为空              |

    - sql语句

    ```mysql
    drop table if exists user_account_modify;
    create table user_account_modify(
    	ua_modify_id int(8) auto_increment primary key comment "修改编号",
        user_id int(8) comment "修改类型（1、电话号码：2、电子邮箱：3、密码）",
        type tinyint comment "修改类型",
        old_msg varchar(256) not null comment "老信息",
        new_msg varchar(256) not null comment "新信息",
        modify_name timestamp default current_timestamp on update current_timestamp comment "账户更改时间",
        create_time datetime not null comment "账户确认生成时间"
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    ```



  - 表名：user_role_info 角色表信息

    - 描述：记录系统相关的角色信息。
    - 字段定义

    | 字段        | 字段描述         | 字段类型    | 备注                  |
    | ----------- | ---------------- | ----------- | --------------------- |
    | role_id     | 角色主键id       | int(8)      | 主键自增长id          |
    | role_name   | 角色名称         | varchar(24) | 唯一                  |
    | modify_time | 角色信息修改时间 | timestamp   | 默认current_timestamp |
    | create_time | 角色信息创建时间 | datetime    | 不能为空              |

    - sql语句

    ```mysql
    drop table if exists user_role_info;
    create table user_role_info(
    	role_id int(8) auto_increment primary key comment "角色主键id",
        role_name varchar(24) not null comment "角色名称",
        modify_time timestamp default current_timestamp on update current_timestamp comment "记录修改时间",
        create_time datetime not null comment "记录创建时间"
    )ENGINE=InnoDB auto_increment=1001 DEFAULT CHARSET=utf8;
    alter table user_role_info add unique(role_name);
    ```



  - 表名：user_permission_visit 访问权限

    - 描述：记录相关角色权限信息。

    - 字段定义

      | 字段         | 字段描述     | 字段类型      | 备注                  |
      | ------------ | ------------ | ------------- | --------------------- |
      | pv_id        | 访问权限主键 | int(8)        | 自增长主键            |
      | url          | 访问url      | varchar(1240) | not null              |
      | dispaly_name | 显示名称     | varchar(24)   | not null              |
      | menu_key     | 菜单名称     | varchar(24)   | 唯一                  |
      | modify_time  | 信息修改时间 | timestamp     | 默认current_timestamp |
      | create_time  | 信息创建时间 | datetime      | 不能为空              |

    - sql语句

    ```mysql
    drop table if exists user_permission_visit;
    create table user_permission_visit(
    	pv_id int(8) auto_increment primary key comment "访问权限自增张主键",
        url varchar(1240) not null comment "访问url",
        dispaly_name varchar(24) not null comment "显示名称",
        menu_key varchar(24) not null comment "菜单名称",
        modify_time timestamp default current_timestamp on update current_timestamp comment "记录修改时间",
        create_time datetime not null comment "记录创建时间"
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    alter table user_permission_visit add unique(menu_key);
    ```




  - 表名：user_permission_operator 操作权限

    - 主要针对按钮的操作权限进行权限的设计。

    - 字段定义

      | 字段         | 字段描述         | 字段类型    | 备注                  |
      | ------------ | ---------------- | ----------- | --------------------- |
      | po_id        | 操作权限权限主键 | int(8)      | 自增长主键            |
      | dispaly_name | 显示名称         | varchar(24) | not null              |
      | button_key   | 菜单名称         | varchar(24) | 唯一                  |
      | modify_time  | 信息修改时间     | timestamp   | 默认current_timestamp |
      | create_time  | 信息创建时间     | datetime    | 不能为空              |

    - sql语句

    ```mysql
    drop table if exists user_permission_operator;
    create table user_permission_operator(
    	po_id int(8) auto_increment primary key comment "操作权限自增张主键",
        dispaly_name varchar(24) not null comment "显示名称",
        button_key varchar(24) not null comment "按钮名称",
        modify_time timestamp default current_timestamp on update current_timestamp comment "记录修改时间",
        create_time datetime not null comment "记录创建时间"
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
    alter table user_permission_operator add unique(button_key);
    ```


### 接口说明

- 概述

  针对用户行为进行的安全操作

  - 随机数生成接口

    - url：/account/register

    - method：post

    - request-parameter:

      | 参数名称  | 参数类型 | 是否必须 | 备注         |
      | --------- | -------- | -------- | ------------ |
      | loginName | String   | true     | 用户登录名   |
      | loginPass | String   | true     | 用户登录密码 |

    - response-parameter:

      ```json
      {
          "status":"success",
          "code":"0",
          "message":"请求成功"
      }
      ```


  - 获取原始数据

    - url：/account/login

    - 说明：获取最近多少次原始数据

    - method：post

    - request-parameter:

      | 参数名称  | 参数类型 | 是否必须 | 备注         |
      | --------- | -------- | -------- | ------------ |
      | loginName | String   | true     | 用户登录名   |
      | loginPass | String   | true     | 用户登录密码 |

    - response-parameter:

      ```json
      {
          "status":"success",
          "code":"0",
          "message":"请求成功",
          "data":{
              "token":xxxx
          } 
      }
      ```


  - 获取正反统计数据数据

    - url：/random/r_number

    - 说明：获取最近多少次原始数据

    - method：post

    - request-parameter:

      | 参数名称 | 参数类型 | 是否必须 | 备注       |
      | -------- | -------- | -------- | ---------- |
      | position | Long     | true     | 随机数位置 |

    - response-parameter:

      ```json
      {
          "status":"success",
          "code":"0",
          "message":"请求成功",
          "data":{
              "positionT":23,
              "r0":"12",
              "r1":"11"
          } 
      }
      ```


  - 获取数值次数数据

    - url：/random/r_figure

    - 说明：获取历史数据数值次数信息

    - method：post

    - request-parameter:

      | 参数名称 | 参数类型 | 是否必须 | 备注       |
      | -------- | -------- | -------- | ---------- |
      | position | Long     | true     | 随机数位置 |

    - response-parameter:

      ```json
      {
          "status":"success",
          "code":"0",
          "message":"请求成功",
          "data":{
              "zero":[
                  {"key":1,"value":12},
                  {"key":2,"value":13}...
              ],
              "one":[
                  {"key":1,"value":12},
                  {"key":2,"value":11}...
              ],
              "total":[
                  {"key":1,"value":12},
                  {"key":2,"value":13}...
              ]
          }
      }
      ```


### 测试

​	针对框架做的测试性记录：

- 创建数据库

  ~~~mysql
  drop table if exists user_account;
  create table user_account(
  	id int(8) auto_increment primary key comment "主键",
      loginName varchar(10) not null comment "账户名称",
      loginPass varchar(20) not null comment "账户密码",
      role varchar(10) not null comment "用户角色"
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;
  insert into user_account (loginName,loginPass,role) values ('howie','1234','user');
  insert into user_account (loginName,loginPass,role) values ('swit','1234','admin');
  ~~~



  ~~~mysql
  
  ~~~
