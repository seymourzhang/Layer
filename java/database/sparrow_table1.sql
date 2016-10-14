/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/1/27 18:00:56                           */
/*==============================================================*/


drop table if exists s_b_abnormal_info;

drop table if exists s_b_alarm_done;

drop table if exists s_b_alarm_inco;

drop table if exists s_b_bbfar;

drop table if exists s_b_breed_detail;

drop table if exists s_b_constants;

drop table if exists s_b_data_input;

drop table if exists s_b_dayage_temp;

drop table if exists s_b_dayage_temp_sub;

drop table if exists s_b_devi_house;

drop table if exists s_b_farm_breed;

drop table if exists s_b_house_alarm;

drop table if exists s_b_house_breed;

drop table if exists s_b_house_probe;

drop table if exists s_b_monitor_curr;

drop table if exists s_b_monitor_hist;

drop table if exists s_b_temp_setting;

drop table if exists s_b_temp_setting_sub;

drop table if exists s_b_user_house;

drop table if exists s_b_user_role;

drop table if exists s_d_area_china;

drop table if exists s_d_code;

drop table if exists s_d_device;

drop table if exists s_d_farm;

drop table if exists s_d_house;

drop table if exists s_d_role;

drop table if exists s_d_user;

drop table if exists s_d_weather_code;

drop table if exists s_l_pw_trace;

drop table if exists s_l_user_logon;

drop table if exists s_b_user_farm;

/*==============================================================*/
/* Table: s_b_user_farm                                         */
/*==============================================================*/
create table s_b_user_farm
(
   id                   int not null auto_increment,
   user_id              int not null,
   user_code            varchar(40),
   farm_id              int not null,
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);


/*==============================================================*/
/* Table: s_b_abnormal_info                                     */
/*==============================================================*/
create table s_b_abnormal_info
(
   farm_id              int not null,
   house_id             int not null,
   alarm_code           varchar(40) not null,
   status               varchar(1) not null comment 'Y-报警；N-正常',
   start_time           datetime,
   standar_delay        int not null,
   deal_status          varchar(2) comment '01-待处理；02-处理中',
   deal_delay           int,
   deal_time            datetime,
   modify_time          datetime,
   primary key (farm_id, house_id, alarm_code)
);

/*==============================================================*/
/* Table: s_b_alarm_done                                        */
/*==============================================================*/
create table s_b_alarm_done
(
   id                   int not null,
   alarm_code           varchar(40) not null,
   alarm_Name           varchar(40),
   farm_id              int not null,
   house_id             int not null,
   farm_breed_id        int not null,
   house_breed_id       int not null,
   monitor_id           int,
   actual_value         numeric(16,2),
   set_value            numeric(16,2),
   value_unit           varchar(40),
   alarm_time           datetime not null,
   deal_status          varchar(2) comment '01-待处理;02-处理中',
   deal_delay           int,
   deal_time            datetime,
   response_person      int,
   is_remove            varchar(1),
   remove_time          datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: s_b_alarm_inco                                        */
/*==============================================================*/
create table s_b_alarm_inco
(
   id                   int not null auto_increment,
   alarm_code           varchar(40) not null,
   alarm_name           varchar(40),
   farm_id              int not null,
   house_id             int not null,
   farm_breed_id        int not null,
   house_breed_id       int not null,
   monitor_id           int,
   actual_value         numeric(16,2),
   set_value            numeric(16,2),
   value_unit           varchar(40),
   alarm_time           datetime not null,
   deal_status          varchar(2) comment '01-待处理;02-处理中',
   deal_delay           int,
   deal_time            datetime,
   response_person      int,
   is_remove            varchar(1),
   remove_time          datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: s_b_bbfar                                             */
/*==============================================================*/
create table s_b_bbfar
(
   id                   int not null auto_increment,
   date_time            datetime not null,
   keyid                varchar(50) not null,
   t1                   varchar(50),
   t2                   varchar(50),
   t3                   varchar(50),
   t4                   varchar(50),
   t5                   varchar(50),
   t6                   varchar(50),
   h1                   varchar(50),
   h2                   varchar(50),
   p                    varchar(50),
   station              varchar(50),
   gps                  varchar(50),
   help                 varchar(50),
   bak1                 varchar(50),
   bak2                 varchar(50),
   bak3                 varchar(50),
   primary key (id)
);

/*==============================================================*/
/* Table: s_b_breed_detail                                      */
/*==============================================================*/
create table s_b_breed_detail
(
   house_breed_id       int not null,
   age                  int not null,
   growth_date          datetime not null,
   death_am             int,
   death_pm             int,
   culling_am           int,
   culling_pm           int,
   cur_cd               int,
   acc_cd               int,
   cur_feed             numeric(16,2),
   acc_feed             numeric(16,2),
   cur_weight           numeric(16,2),
   cur_amount           int,
   ytd_amount           int,
   int_bak1             int,
   int_bak2             int,
   int_bak3             int,
   num_bak1             numeric(16,2),
   num_bak2             numeric(16,2),
   num_bak3             numeric(16,2),
   var_bak1             varchar(50),
   var_bak2             varchar(50),
   var_bak3             varchar(50),
   date_bak1            datetime,
   date_bak2            datetime,
   date_bak3            datetime,
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (house_breed_id, age)
);

/*==============================================================*/
/* Table: s_b_constants                                         */
/*==============================================================*/
create table s_b_constants
(
   codetype             varchar(40) not null,
   code                 varchar(80) not null,
   primary key (codetype, code)
);

/*==============================================================*/
/* Table: s_b_data_input                                        */
/*==============================================================*/
create table s_b_data_input
(
   id                   int not null auto_increment,
   farm_breed_id        int not null,
   house_breed_id       int not null,
   farm_id              int not null,
   house_id             int not null,
   age                  int not null,
   data_type            varchar(5) not null comment 'D0001-死亡数量(上午)
            D0002-死亡数量(下午)
            D0003-淘汰数量(上午)
            D0004-淘汰数量(下午)
            D0005-均重
            D0006-饲料量
            D0007-入雏数量
            D0008-出栏数量
            ',
   value_type           varchar(2) not null comment '01-数量
            02-数值
            03-字符
            04-日期',
   val_int              int,
   val_num              numeric(16,2),
   val_varc             varchar(50),
   val_date             datetime,
   freeze_status        varchar(2) not null comment '1-删除；0-未删除',
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_b_dayage_temp                                       */
/*==============================================================*/
create table s_b_dayage_temp
(
   id                   int not null auto_increment,
   farm_id              int not null,
   house_id             int not null,
   feed_batch           int not null,
   memo                 varchar(500),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_b_dayage_temp_sub                                   */
/*==============================================================*/
create table s_b_dayage_temp_sub
(
   uid_num              int not null,
   day_age              int not null,
   record_datetime      datetime not null,
   set_temp             numeric(16,2),
   high_alarm_temp      numeric(16,2),
   low_alarm_temp       numeric(16,2),
   set_humidity         numeric(19,6),
   memo                 varchar(500),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (uid_num, day_age, record_datetime)
);

/*==============================================================*/
/* Table: s_b_devi_house                                        */
/*==============================================================*/
create table s_b_devi_house
(
   id                   int not null auto_increment,
   device_code          varchar(100) not null,
   farm_id              int not null,
   house_id             int not null,
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_b_farm_breed                                        */
/*==============================================================*/
create table s_b_farm_breed
(
   id                   int not null auto_increment,
   farm_id              int not null,
   batch_code           varchar(20) not null,
   place_date           datetime not null,
   place_num            int,
   breed_days           int,
   market_date          datetime,
   doc_vendors          varchar(5),
   breed                varchar(5),
   settle_date          datetime,
   batch_status         varchar(2) not null comment '01-新建批次
            02-已结算',
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);


/*==============================================================*/
/* Table: s_b_house_alarm                                       */
/*==============================================================*/
create table s_b_house_alarm
(
   farm_id              int not null,
   house_id             int not null,
   alarm_delay          int not null comment '按分钟计算',
   temp_cpsation        varchar(2) not null comment '0-无
            1-有',
   temp_cordon          numeric(16,2),
   alarm_way            varchar(2) comment '01-铃声
            02-振动
            03-铃声加振动',
   alarm_probe          varchar(2) comment '01-探头温度
            02-平均温度',
   point_alarm          numeric(16,2),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (farm_id, house_id)
);

/*==============================================================*/
/* Table: s_b_house_breed                                       */
/*==============================================================*/
create table s_b_house_breed
(
   id                   int not null auto_increment,
   farm_breed_id        int not null,
   farm_id              int not null,
   house_id             int not null,
   place_date           datetime not null,
   place_num            int not null,
   place_weight         numeric(16,2),
   moveout_num          int,
   moveout_weight       numeric(16,2),
   batch_status         varchar(2) not null comment '01-新建批次
            02-已结算',
   market_date          datetime,
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_b_house_probe                                       */
/*==============================================================*/
create table s_b_house_probe
(
   farm_id              int not null,
   house_id             int not null,
   probe_code           varchar(20) not null comment 'tempLeft1-前区温度1
            tempLeft2-前区温度2
            tempMiddle1-中区温度1
            tempMiddle2-中区温度2
            tempRight1-后区温度1
            tempRight2-后区温度2',
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (farm_id, house_id, probe_code)
);

/*==============================================================*/
/* Table: s_b_monitor_curr                                      */
/*==============================================================*/
create table s_b_monitor_curr
(
   id                   int not null auto_increment,
   farm_id              int not null,
   house_id             int not null,
   collect_datetime     datetime not null,
   deal_status          int not null comment '0-未处理
            1-已处理',
   date_age             int not null,
   week_age             int,
   inside_temp1         numeric(16,2) comment 't1',
   inside_temp2         numeric(16,2) comment 't2',
   inside_temp3         numeric(16,2) comment 't3',
   inside_temp4         numeric(16,2),
   inside_temp5         numeric(16,2) comment 't4',
   inside_temp6         numeric(16,2) comment 't5',
   inside_temp7         numeric(16,2),
   inside_temp8         numeric(16,2),
   inside_temp9         numeric(16,2),
   inside_avg_temp      numeric(16,2),
   inside_set_temp      numeric(16,2),
   high_alarm_temp      numeric(16,2),
   low_alarm_temp       numeric(16,2),
   outside_temp         numeric(16,2) comment 't6',
   inside_humidity      numeric(16,2) comment 'h1',
   point_temp_diff      numeric(16,2),
   target_humidity      numeric(16,2),
   co2                  numeric(16,2),
   power_status         int comment '0   ---正常
            1   --断电',
   update_datetime      dateTime,
   primary key (id)
);

/*==============================================================*/
/* Table: s_b_monitor_hist                                      */
/*==============================================================*/
create table s_b_monitor_hist
(
   id                   int not null,
   farm_id              int not null,
   house_id             int not null,
   collect_datetime     datetime not null,
   deal_status          int not null comment '0-未处理
            1-已处理',
   date_age             int default 0,
   week_age             int default 0,
   inside_temp1         numeric(16,2) default 0,
   inside_temp2         numeric(16,2) default 0,
   inside_temp3         numeric(16,2) default 0,
   inside_temp4         numeric(16,2) default 0,
   inside_temp5         numeric(16,2) default 0,
   inside_temp6         numeric(16,2) default 0,
   inside_temp7         numeric(16,2) default 0,
   inside_temp8         numeric(16,2) default 0,
   inside_temp9         numeric(16,2) default 0,
   inside_avg_temp      numeric(16,2) default 0,
   inside_set_temp      numeric(16,2) default 0,
   high_alarm_temp      numeric(16,2) default 0,
   low_alarm_temp       numeric(16,2) default 0,
   outside_temp         numeric(16,2) default 0,
   inside_humidity      numeric(16,2) default 0,
   point_temp_diff      numeric(16,2),
   target_humidity      numeric(16,2),
   co2                  numeric(16,2),
   power_status         int comment '0   ---正常
            1   --断电',
   update_datetime      datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: s_b_temp_setting                                      */
/*==============================================================*/
create table s_b_temp_setting
(
   id                   int not null auto_increment,
   farm_id              int not null,
   house_id             int not null,
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_b_temp_setting_sub                                  */
/*==============================================================*/
create table s_b_temp_setting_sub
(
   uid_num              int not null,
   day_age              int not null,
   set_temp             numeric(16,2),
   high_alarm_temp      numeric(16,2),
   low_alarm_temp       numeric(16,2),
   set_humidity         numeric(16,2),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (uid_num, day_age)
);

/*==============================================================*/
/* Table: s_b_user_house                                        */
/*==============================================================*/
create table s_b_user_house
(
   id                   int not null auto_increment,
   user_id              int not null,
   user_code            varchar(40),
   farm_id              int not null,
   house_id             int not null,
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_b_user_role                                         */
/*==============================================================*/
create table s_b_user_role
(
   id                   int not null auto_increment,
   user_id              int not null,
   user_code            varchar(40),
   role_id              int not null,
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_d_area_china                                        */
/*==============================================================*/
create table s_d_area_china
(
   id                   int not null,
   parent_id            int,
   name                 varchar(100),
   short_name           varchar(100),
   longitude            numeric(20,6),
   latitude             numeric(20,6),
   level                int,
   sort                 int,
   status               int,
   primary key (id)
);

/*==============================================================*/
/* Table: s_d_code                                              */
/*==============================================================*/
create table s_d_code
(
   code_type            varchar(40) not null,
   biz_code             varchar(80) not null,
   code_name            varchar(500) not null,
   bak1                 varchar(500),
   bak2                 varchar(500),
   code_desc            varchar(1000),
   primary key (code_type, biz_code)
);

/*==============================================================*/
/* Table: s_d_device                                            */
/*==============================================================*/
create table s_d_device
(
   device_code          varchar(100) not null,
   device_factory       varchar(40) not null,
   d_status             varchar(1) not null comment '0-未销售
            1-已销售
            2-已激活
            3-报废',
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (device_code)
);

/*==============================================================*/
/* Table: s_d_farm                                              */
/*==============================================================*/
create table s_d_farm
(
   id                   int not null auto_increment,
   farm_code            varchar(40),
   farm_name_chs        varchar(500),
   farm_name_en         varchar(500),
   feed_method          varchar(2) comment '01-地面平养;
            02-网上平养;
            03-笼养;
            04-其他;
            ',
   layers               int,
   rows                 int,
   feed_density         numeric(20,6),
   h_length             numeric(20,6),
   h_width              numeric(20,6),
   h_height             numeric(20,6),
   corporation          varchar(500),
   farm_add1            varchar(500),
   farm_add2            varchar(500),
   farm_add3            varchar(500),
   farm_add4            varchar(500),
   farm_add5            varchar(500),
   memo_1               varchar(500),
   memo_2               varchar(500),
   freeze_status        varchar(2) not null comment '1-删除；0-未删除',
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_d_house                                             */
/*==============================================================*/
create table s_d_house
(
   id                   int not null auto_increment,
   house_code           varchar(40) not null,
   house_name           varchar(500),
   farm_id              int,
   feed_density         numeric(20,6),
   house_length         numeric(20,6),
   house_width          numeric(20,6),
   house_height         numeric(20,6),
   memo_1               varchar(500),
   memo_2               varchar(500),
   freeze_status        varchar(2) not null comment '1-已删除
            0-未删除',
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_d_role                                              */
/*==============================================================*/
create table s_d_role
(
   id                   int not null auto_increment,
   role_name            varchar(500),
   role_desc            varchar(500),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_d_user                                              */
/*==============================================================*/
create table s_d_user
(
   id                   int not null auto_increment,
   user_code            varchar(40) not null,
   user_real_name       varchar(500),
   user_real_name_en    varchar(500),
   user_password        varchar(40) not null,
   user_mobile_1        varchar(40),
   user_mobile_2        varchar(40),
   user_tel_1           varchar(40),
   user_tel_2           varchar(40),
   user_email           varchar(500),
   user_status          varchar(2) not null comment '1-正常',
   freeze_status        varchar(2) not null comment '1-已删除
            0-未删除',
   memo_1               varchar(500),
   memo_2               varchar(500),
   memo_3               varchar(500),
   memo_4               varchar(500),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_d_weather_code                                      */
/*==============================================================*/
create table s_d_weather_code
(
   id1                  varchar(20),
   name1                varchar(200),
   id2                  varchar(20),
   name2                varchar(200),
   id3                  varchar(20),
   name3                varchar(200),
   bak2                 varchar(100),
   bak3                 varchar(100),
   bak4                 varchar(100)
);

/*==============================================================*/
/* Table: s_l_pw_trace                                          */
/*==============================================================*/
create table s_l_pw_trace
(
   id                   int not null auto_increment,
   user_id              int not null,
   user_code            varchar(40) not null,
   old_password         varchar(500) not null,
   new_password         varchar(500) not null,
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: s_l_user_logon                                        */
/*==============================================================*/
create table s_l_user_logon
(
   id                   int not null auto_increment,
   user_id              int not null,
   user_code            varchar(40) not null,
   login_ip             varchar(40),
   mac_addr             varchar(40),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

