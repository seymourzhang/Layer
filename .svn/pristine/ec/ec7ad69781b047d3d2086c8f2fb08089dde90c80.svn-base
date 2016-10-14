drop table if exists s_b_layer_farm_breed;

/*==============================================================*/
/* Table: s_b_layer_farm_breed                                  */
/*==============================================================*/
create table s_b_layer_farm_breed
(
   id                   int not null auto_increment,
   farm_id              int not null,
   batch_code           varchar(20) not null,
   place_date           datetime not null,
   place_day_age        int not null,
   place_week_age       int not null,
   place_num            int not null,
   market_date          datetime,
   bak_int1             int,
   bak_int2             int,
   bak_int3             int,
   bak_num1             numeric(16,2),
   bak_num2             numeric(16,2),
   bak_num3             numeric(16,2),
   bak_var1             varchar(20),
   bak_var2             varchar(20),
   bak_var3             varchar(20),
   batch_status         varchar(2) not null comment '01-新建批次
            02-已结算',
   settle_date          datetime,
   settle_person        int,
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

drop table if exists s_b_layer_house_breed;

/*==============================================================*/
/* Table: s_b_layer_house_breed                                 */
/*==============================================================*/
create table s_b_layer_house_breed
(
   id                   int not null auto_increment,
   farm_breed_id        int not null,
   house_id             int not null,
   farm_id              int not null,
   place_num            int not null,
   bak_int1             int,
   bak_int2             int,
   bak_int3             int,
   bak_num1             numeric(16,2),
   bak_num2             numeric(16,2),
   bak_num3             numeric(16,2),
   bak_var1             varchar(20),
   bak_var2             varchar(20),
   bak_var3             varchar(20),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);


drop table if exists s_b_layer_breed_detail;

/*==============================================================*/
/* Table: s_b_layer_breed_detail                                */
/*==============================================================*/
create table s_b_layer_breed_detail
(
   house_breed_id       int not null,
   day_age              int not null,
   week_age             int not null,
   growth_date          date not null,
   is_history           varchar(1) not null comment 'Y-是;N-否',
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
   cur_water            numeric(16,2),
   acc_water            numeric(16,2),
   cur_lay_num          int,
   acc_lay_num          int,
   cur_lay_weight       numeric(16,2),
   acc_lay_weight       numeric(16,2),
   cur_lay_broken       int,
   acc_lay_broken       int,
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
   primary key (house_breed_id, day_age)
);


drop table if exists s_b_egg_sells;

/*==============================================================*/
/* Table: s_b_egg_sells                                         */
/*==============================================================*/
create table s_b_egg_sells
(
   farm_id              int not null,
   sell_date            date not null,
   is_history           varchar(1) not null comment 'Y-是;N-否',
   day_age              int,
   week_age             int,
   farm_breed_id        int,
   good_box_size        numeric(16,2) comment '单位：公斤/箱',
   good_price_type      varchar(2) comment '01-元/箱
            02-元/公斤',
   good_price_value     numeric(16,2),
   good_salebox_num     numeric(16,2),
   good_sale_money      numeric(16,2),
   good_sale_weight     numeric(16,2),
   broken_box_size      numeric(16,2) comment '单位：公斤/箱',
   broken_price_type    varchar(2) comment '01-元/箱
            02-元/公斤',
   broken_price_value   numeric(16,2),
   broken_salebox_num   numeric(16,2),
   broken_sale_money    numeric(16,2),
   broken_sale_weight   numeric(16,2),
   chicken_manure       numeric(16,2),
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
   primary key (farm_id, sell_date)
);




drop table if exists s_l_jpush_log;

/*==============================================================*/
/* Table: s_l_jpush_log                                         */
/*==============================================================*/
create table s_l_jpush_log
(
   id                   int not null auto_increment,
   content              varchar(500) not null,
   tag_name             varchar(100) not null,
   house_id             int not null,
   alarm_id             numeric(16,2) not null,
   push_date            datetime,
   res_flag             varchar(5) comment 'succ-成功
            fail-失败',
   res_detail           varchar(500),
   make_date            datetime not null,
   modify_date          datetime not null,
   primary key (id)
);

drop table if exists s_b_layer_house_alarm;

/*==============================================================*/
/* Table: s_b_layer_house_alarm                                 */
/*==============================================================*/
create table s_b_layer_house_alarm
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
   target_temp          numeric(16,2),
   min_temp             numeric(16,2),
   max_temp             numeric(16,2),
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


drop table if exists s_b_layer_farm_settle;

/*==============================================================*/
/* Table: s_b_layer_farm_settle                                 */
/*==============================================================*/
create table s_b_layer_farm_settle
(
   id                   int not null auto_increment,
   farm_id              int not null,
   farm_breed_id        int not null,
   house_id             int,
   settle_month         varchar(20),
   fee_type             varchar(2) comment 'I-收入;
            E-支出',
   fee_code             varchar(20),
   fee_name             varchar(50) comment '1001-鸡苗结算;
            1002-青年鸡入舍
            2001-1号饲料;
            2002-2号饲料;
            2003-3号饲料;
            2004-玉米;
            2005-豆粕;
            2006-添加剂;
            2007-预混料;
            2008-产前料;
            2009-产前料1;
            2010-产前料2;
            2011-产前料3;
            2012-产前料4;
            3001-药品疫苗;
            3002-燃料费;
            3003-人工费;
            3004-其他费用;
            3005-折旧费用;
            3006-水电费;
            3007-垫料费;
            3008-抓鸡费;
            3009-包装费用;
            4001-毛鸡结算;
            4002-鸡粪收入;
            4003-其他收入;
            4004-合格蛋销售;
            4005-破损蛋销售;
            4006-淘汰鸡销售;',
   company_code         varchar(20),
   company_name         varchar(50),
   price_pu             numeric(16,2),
   quentity             int,
   weight               numeric(16,2),
   money_sum            numeric(16,2),
   bak_var1             varchar(20),
   bak_var2             varchar(20),
   bak_var3             varchar(20),
   bak_num1             numeric(16,2),
   bak_num2             numeric(16,2),
   bak_num3             numeric(16,2),
   bak_num4             numeric(16,2),
   bak_num5             numeric(16,2),
   bak_num6             numeric(16,2),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);


drop table if exists s_d_layer_chicken_standar;

/*==============================================================*/
/* Table: s_d_layer_chicken_standar                             */
/*==============================================================*/
create table s_d_layer_chicken_standar
(
   id                   int not null auto_increment,
   farm_id              int not null,
   day_age              int not null,
   week_age             int not null,
   type1                varchar(2) not null comment '01-默认',
   day_lay_rate_max     numeric(16,2),
   day_lay_rate_min     numeric(16,2),
   acc_cur_lay_max      numeric(16,2),
   acc_cur_lay_min      numeric(16,2),
   acc_ori_lay_max      numeric(16,2),
   acc_ori_lay_min      numeric(16,2),
   acc_cd_rate          numeric(16,2),
   chicken_weight_max   numeric(16,2),
   chicken_weight_min   numeric(16,2),
   day_feed_max         numeric(16,2),
   day_feed_min         numeric(16,2),
   acc_ori_lay_weight   numeric(16,2),
   lay_weight_max       numeric(16,2),
   lay_weight_min       numeric(16,2),
   num_bak1             numeric(16,2),
   num_bak2             numeric(16,2),
   num_bak3             numeric(16,2),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);


drop table if exists s_d_user_operation;

/*==============================================================*/
/* Table: s_d_user_operation                                    */
/*==============================================================*/
create table s_d_user_operation
(
   id                   int not null auto_increment,
   person_id            int not null,
   menu_code            varchar(10) not null,
   operation_type       varchar(10) not null comment 'select
            update
            add
            delete',
   create_time          datetime not null default CURRENT_TIMESTAMP,
   primary key (id)
);


drop table if exists s_l_jpush_log;

/*==============================================================*/
/* Table: s_l_jpush_log                                         */
/*==============================================================*/
create table s_l_jpush_log
(
   id                   int not null auto_increment,
   content              varchar(500) not null,
   tag_name             varchar(100) not null,
   house_id             int,
   alarm_id             int,
   push_date            datetime,
   res_flag             varchar(5) comment 'succ-成功
            fail-失败',
   res_detail           varchar(500),
   make_date            datetime not null,
   modify_date          datetime not null,
   primary key (id)
);
