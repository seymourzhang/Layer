drop table if exists s_b_house_breed_tsk;

/*==============================================================*/
/* Table: s_b_house_breed_tsk                                   */
/*==============================================================*/
create table s_b_house_breed_tsk
(
   id                   int not null auto_increment,
   house_id             int not null,
   farm_breed_id        int not null,
   age                  int,
   task_id              int not null,
   task_code            varchar(8) not null,
   task_name            varchar(500) not null,
   task_type            varchar(2) not null comment '01-入雏前准备
            02-入雏后日常工作
            03-光照程序
            04-免疫用药程序
            05-其他临时任务',
   deal_status          varchar(2) not null comment '00-未处理
            01-完成
            02-延迟
            03-取消',
   deal_time            datetime,
   deal_log             varchar(500),
   bak1                 varchar(20),
   bak2                 varchar(20),
   bak3                 varchar(500),
   bak4                 varchar(500),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);


drop table if exists s_b_farm_task;

/*==============================================================*/
/* Table: s_b_farm_task                                         */
/*==============================================================*/
create table s_b_farm_task
(
   id                   int not null auto_increment,
   farm_id              int not null,
   task_source          varchar(2) not null comment '01-标准模板
            02-自定义',
   task_code            varchar(8) not null,
   task_name            varchar(500) not null,
   task_type            varchar(2) not null comment '01-入雏前准备
            02-入雏后日常工作
            03-光照程序
            04-免疫用药程序
            05-其他临时任务',
   task_status          varchar(1) not null comment 'Y-有效
            N-无效',
   age_infos            varchar(500) not null,
   bak1                 varchar(20),
   bak2                 varchar(20),
   bak3                 varchar(500),
   bak4                 varchar(500),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

drop table if exists s_d_task;

/*==============================================================*/
/* Table: s_d_task                                              */
/*==============================================================*/
create table s_d_task
(
   task_code            varchar(8) not null,
   task_name            varchar(500) not null,
   task_type            varchar(2) not null comment '01-入雏前准备
            02-入雏后日常工作
            03-光照程序
            04-免疫用药程序
            05-其他临时任务',
   task_status          varchar(1) not null comment 'Y-有效
            N-无效',
   age_infos            varchar(500) not null,
   bak1                 varchar(20),
   bak2                 varchar(20),
   bak3                 varchar(500),
   bak4                 varchar(500),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (task_code)
);

drop table if exists s_d_serialno;

/*==============================================================*/
/* Table: s_d_serialno                                          */
/*==============================================================*/
create table s_d_serialno
(
   IncreID              int not null auto_increment,
   primary key (IncreID)
);


drop table if exists s_b_chicken_standar;

/*==============================================================*/
/* Table: s_b_chicken_standar                                   */
/*==============================================================*/
create table s_b_chicken_standar
(
   id                   int not null auto_increment,
   farm_id              int not null,
   feed_type            varchar(40) not null comment 'cock-公鸡
            hen-母鸡
            mixed-公母混养',
   age                  int,
   weight_per           numeric(16,2),
   weight_diff_per      numeric(16,2),
   feed_conversion      numeric(16,2),
   acc_feed             numeric(16,2),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id, farm_id, feed_type)
);
