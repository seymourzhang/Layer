drop table if exists s_b_abnormal_info;

CREATE TABLE s_b_abnormal_info
(
    farm_id INT(11) NOT NULL,
    house_id INT(11) NOT NULL,
    alarm_code VARCHAR(40) NOT NULL,
    status VARCHAR(1) NOT NULL COMMENT 'Y-报警；N-正常',
    start_time DATETIME,
    standar_delay INT(11) NOT NULL,
    deal_status VARCHAR(2) COMMENT '01-待处理；02-处理中',
    deal_delay INT(11),
    deal_time DATETIME,
    modify_time DATETIME,
    CONSTRAINT `PRIMARY` PRIMARY KEY (farm_id, house_id, alarm_code)
);

drop table if exists s_b_alarm_should;

CREATE TABLE s_b_alarm_should
(
    farm_id INT(11) NOT NULL,
    house_id INT(11) NOT NULL,
    alarm_code VARCHAR(40) NOT NULL,
    status VARCHAR(1) NOT NULL COMMENT 'Y-报警；N-正常',
    start_time DATETIME,
    standar_delay INT(11) NOT NULL,
    deal_status VARCHAR(2) COMMENT '01-待处理；02-处理中',
    deal_delay INT(11),
    deal_time DATETIME,
    modify_time DATETIME
);

drop table if exists s_b_monitor_temp_alert;

CREATE TABLE s_b_monitor_temp_alert
(
    farmid INT(11),
    houseid INT(11),
    farmbreedid INT(11),
    housebreedid INT(11),
    monitor_id INT(11),
    collect_datetime DATETIME,
    alert_type VARCHAR(10),
    soure_values DECIMAL(19,2),
    target_values DECIMAL(19,2)
);

drop table if exists s_l_alarm_request;

CREATE TABLE s_l_alarm_request
(
    id INT(11) PRIMARY KEY NOT NULL auto_increment,
    uuid VARCHAR(100),
    model VARCHAR(50),
    version VARCHAR(50),
    platform VARCHAR(50),
    cdate DATETIME DEFAULT CURRENT_TIMESTAMP,
    result VARCHAR(5),
    userid INT(11)
);

drop table if exists s_b_yincomm_data;

CREATE TABLE s_b_yincomm_data
(
    id INT(11) PRIMARY KEY NOT NULL,
    data_date DATETIME NOT NULL,
    data_type VARCHAR(20),
    MM_sn VARCHAR(50) NOT NULL,
    data_sn INT(11) NOT NULL,
    sensor_sn_hex VARCHAR(20),
    sensor_sn BIGINT(20),
    para_id INT(11),
    para_value DECIMAL(20,2),
    receive_data VARCHAR(1000),
    confirm_data VARCHAR(500),
    create_date DATETIME
);

drop table if exists s_b_farm_settle;

/*==============================================================*/
/* Table: s_b_farm_settle                                       */
/*==============================================================*/
create table s_b_farm_settle
(
   id                   int not null auto_increment,
   farm_id              int not null,
   farm_breed_id        int not null,
   house_id             int,
   house_breed_id       int,
   fee_type             varchar(2) comment 'I-收入;
            E-支出',
   fee_code             varchar(20),
   fee_name             varchar(50) comment '1001-鸡苗结算;
            2001-1号饲料;
            2002-2号饲料;
            2003-3号饲料;
            3001-药品疫苗;
            3002-燃料费;
            3003-人工费;
            3004-其他费用;
            4001-毛鸡结算;
            4002-鸡粪收入;
            4003-其他收入;',
   company_code         varchar(20),
   company_name         varchar(50),
   price_pu             numeric(16,2),
   quentity             int,
   weight               numeric(16,2),
   money_sum            numeric(16,2),
   bak1                 varchar(20),
   bak2                 varchar(20),
   bak3                 varchar(20),
   bak4                 numeric(16,2),
   bak5                 numeric(16,2),
   bak6                 numeric(16,2),
   create_person        int not null,
   create_date          datetime not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   modify_person        int not null,
   modify_date          datetime not null,
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (id)
);

drop table if exists s_b_temp1;

drop table if exists s_b_temp2;

CREATE TABLE s_b_temp1(KeyInfo int,id int,houseid int,dayage int,
                          targettemp NUMERIC(19,2),hightemp NUMERIC(19,2),lowtemp NUMERIC(19,2),
                          humidity NUMERIC(19,2),place_date datetime,
                          nextdayage int,
                          nexthightemp numeric(19,2),nextlowtemp numeric(19,2),nexttargettemp numeric(19,2),totalrow int);

Create TABLE s_b_temp2(KeyInfo int,id int,dayage int,record_datetime datetime,targettemp NUMERIC(19,2),hightemp NUMERIC(19,2),lowtemp NUMERIC(19,2),humidity numeric(19,2));
