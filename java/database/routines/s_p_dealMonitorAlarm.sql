DROP PROCEDURE IF EXISTS sparrow_lc.s_p_dealMonitorAlarm;
CREATE DEFINER=`phoelc`@`%` PROCEDURE `s_p_dealMonitorAlarm`()
label_at_start:BEGIN
    DECLARE p_cur_time DATETIME;
    DECLARE p_deal_flag VARCHAR(10);
    DECLARE p_standard_delay_time int;
    /*声明变量，用于异常情况的捕捉*/
    DECLARE p_res VARCHAR(5) DEFAULT 'Succ';
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION ,SQLWARNING ,NOT FOUND SET p_res = 'Fail'; -- 异常捕获
    START TRANSACTION ;
    
    SELECT biz_code INTO p_deal_flag from s_d_code where code_type = 'alarm_flag' ;
    if p_deal_flag = 'false' THEN 
        select 'DealFlag:false' ;
        COMMIT;
        LEAVE label_at_start;
    END IF;
    
    /* 1、生成异常数据 */
        /* 1.2 只处理数据采集时间是近5分钟之内的栋舍 */
        if NOT exists(select 1 from s_b_monitor_curr where collect_datetime >= date_add(now(),INTERVAL -5 MINUTE)) then
            update s_b_abnormal_info a set a.status = 'N',a.start_time = null,a.deal_status = null,a.deal_delay = null,a.deal_time = null,a.modify_time = now();
            INSERT INTO s_b_alarm_done
                        (id, alarm_code, alarm_Name, farm_id, house_id, farm_breed_id, house_breed_id, monitor_id, actual_value, set_value,
                        value_unit, alarm_time, deal_status, deal_delay, deal_time, response_person,is_remove,remove_time)
            SELECT a.id,a.alarm_code,a.alarm_name,a.farm_id,a.house_id,a.farm_breed_id,a.house_breed_id,a.monitor_id,a.actual_value,a.set_value,
                         a.value_unit,a.alarm_time,a.deal_status,a.deal_delay,a.deal_time,a.response_person,
                        'Y', -- 已经消除
                        now() -- 消除时间
            from s_b_alarm_inco a where 1=1 and a.farm_id <> 0 ;
            -- 删除数据
            DELETE from s_b_alarm_inco where 1=1 and farm_id <> 0 ;
          
            select 'No-Data' ;
            COMMIT;
            LEAVE label_at_start;
        END IF;
    
        /* 1.3 生成所有的异常数据，放至 临时表 s_b_monitor_temp_alert*/
        -- 清空工作表
        TRUNCATE table s_b_monitor_temp_alert;
        -- 插入点温差、断电、前区1、前区2、中区1、中区2、后区1、后区2、平均温度 的异常数据
        insert INTO s_b_monitor_temp_alert(farmid, houseid,monitor_id, 
                                    collect_datetime, alert_type, soure_values, 
                                    target_values)
            -- 点温差异常
            SELECT t0.farm_id,t0.house_id,t0.id,t0.collect_datetime,'C0001',t0.point_temp_diff,t1.point_alarm
            from s_b_monitor_curr  t0
            inner join s_b_layer_house_alarm t1 on t0.house_id = t1.house_id
             where t0.point_temp_diff >= t1.point_alarm
            -- 断电异常
            union all
            SELECT t0.farm_id,t0.house_id,t0.id,t0.collect_datetime,'C0002',0,1
            from s_b_monitor_curr  t0
              where t0.power_status =0
            -- 前区1温度异常
            union all
            SELECT t0.farm_id,t0.house_id,t0.id,t0.collect_datetime,
                   case when ifnull(t0.inside_temp1,-99) >= t0.high_alarm_temp then 'A001H' else 'A001L' end,
                   ifnull(t0.inside_temp1,-99),
                   case when ifnull(t0.inside_temp1,-99) >= t0.high_alarm_temp then high_alarm_temp else t0.low_alarm_temp end
            from s_b_monitor_curr  t0
              join s_b_layer_house_alarm t1 on t0.house_id = t1.house_id
              where 1=1
              and t1.alarm_probe = '1'
              and (ifnull(t0.inside_temp1,-99) >= t0.high_alarm_temp or ifnull(t0.inside_temp1,+99) <= t0.low_alarm_temp)
            -- 前区2温度异常
            union all
            SELECT t0.farm_id,t0.house_id,t0.id,t0.collect_datetime,
                   case when ifnull(t0.inside_temp2,-99) >= t0.high_alarm_temp then 'A002H' else 'A002L' end,
                   ifnull(t0.inside_temp2,-99),
                   case when ifnull(t0.inside_temp2,-99) >= t0.high_alarm_temp then high_alarm_temp else t0.low_alarm_temp end
            from s_b_monitor_curr  t0
              join s_b_layer_house_alarm t1 on t0.house_id = t1.house_id
              where 1=1
              and t1.alarm_probe = '1'
              and (ifnull(t0.inside_temp2,-99) >= t0.high_alarm_temp or ifnull(t0.inside_temp2,+99) <= t0.low_alarm_temp)
            -- 中区1温度异常
            union all
            SELECT t0.farm_id,t0.house_id,t0.id,t0.collect_datetime,
                   case when ifnull(t0.inside_temp3,-99) >= t0.high_alarm_temp then 'A003H' else 'A003L' end,
                   ifnull(t0.inside_temp3,-99),
                   case when ifnull(t0.inside_temp3,-99) >= t0.high_alarm_temp then high_alarm_temp else t0.low_alarm_temp end
            from s_b_monitor_curr  t0
              join s_b_layer_house_alarm t1 on t0.house_id = t1.house_id
              where 1=1
              and t1.alarm_probe = '1'
              and (ifnull(t0.inside_temp3,-99) >= t0.high_alarm_temp or ifnull(t0.inside_temp3,+99) <= t0.low_alarm_temp)
            -- 中区2温度异常
            union all
            SELECT t0.farm_id,t0.house_id,t0.id,t0.collect_datetime,
                   case when ifnull(t0.inside_temp4,-99) >= t0.high_alarm_temp then 'A004H' else 'A004L' end,
                   ifnull(t0.inside_temp4,-99),
                   case when ifnull(t0.inside_temp4,-99) >= t0.high_alarm_temp then high_alarm_temp else t0.low_alarm_temp end
            from s_b_monitor_curr  t0
              join s_b_layer_house_alarm t1 on t0.house_id = t1.house_id
              where 1=1
              and t1.alarm_probe = '1'
              and (ifnull(t0.inside_temp4,-99) >= t0.high_alarm_temp or ifnull(t0.inside_temp4,+99) <= t0.low_alarm_temp)
            -- 后区1温度异常
            union all
            SELECT t0.farm_id,t0.house_id,t0.id,t0.collect_datetime,
                   case when ifnull(t0.inside_temp5,-99) >= t0.high_alarm_temp then 'A005H' else 'A005L' end,
                   ifnull(t0.inside_temp5,-99),
                   case when ifnull(t0.inside_temp5,-99) >= t0.high_alarm_temp then high_alarm_temp else t0.low_alarm_temp end
            from s_b_monitor_curr  t0
              join s_b_layer_house_alarm t1 on t0.house_id = t1.house_id
              where 1=1
              and t1.alarm_probe = '1'
              and (ifnull(t0.inside_temp5,-99) >= t0.high_alarm_temp or ifnull(t0.inside_temp5,+99) <= t0.low_alarm_temp)
            -- 后区2温度异常
            union all
            SELECT t0.farm_id,t0.house_id,t0.id,t0.collect_datetime,
                   case when ifnull(t0.inside_temp6,-99) >= t0.high_alarm_temp then 'A006H' else 'A006L' end,
                   t0.inside_temp6,
                   case when ifnull(t0.inside_temp6,-99) >= t0.high_alarm_temp then high_alarm_temp else t0.low_alarm_temp end
            from s_b_monitor_curr  t0
              join s_b_layer_house_alarm t1 on t0.house_id = t1.house_id
              where 1=1
              and t1.alarm_probe = '1'
              and (ifnull(t0.inside_temp6,-99) >= t0.high_alarm_temp or ifnull(t0.inside_temp6,+99) <= t0.low_alarm_temp)
            -- 平均温度
            union all
            SELECT t0.farm_id,t0.house_id,t0.id,t0.collect_datetime,
                   case when ifnull(t0.inside_avg_temp,-99) >= t0.high_alarm_temp then 'B001H' else 'B001L'end,
                   t0.inside_avg_temp,
                   case when t0.inside_avg_temp >= t0.high_alarm_temp then high_alarm_temp else t0.low_alarm_temp end
            from s_b_monitor_curr t0
              join s_b_layer_house_alarm t1 on t0.house_id = t1.house_id
              where 1=1
              and t1.alarm_probe = '2'
              and (ifnull(t0.inside_avg_temp,-99) >= t0.high_alarm_temp or ifnull(t0.inside_avg_temp,+99) <= t0.low_alarm_temp)
            ;
            -- 更新农场饲养批次以及栋舍饲养批次
            UPDATE s_b_monitor_temp_alert set farmbreedid = s_f_getFarmBreedId(farmid),housebreedid = s_f_getHouseBreedId(houseid);
            -- 删除 30分钟之前的无效数据
            DELETE from s_b_monitor_temp_alert where date_add(collect_datetime,INTERVAL 5 MINUTE) < now();
            -- 删除 空舍的报警
            DELETE from s_b_monitor_temp_alert where not exists(
                SELECT 1 from s_b_layer_house_breed b where b.house_id = houseid and b.farm_breed_id = farmbreedid
            );
            -- 删除 不需要报警的探头
            DELETE FROM s_b_monitor_temp_alert where 1=1 
                     and not exists(
                            SELECT 1 from s_b_house_probe b,s_d_code c where 1=1
                                          and c.code_type = 'alarm_code' and b.probe_code = c.bak2
                                          and b.house_id = houseid and c.biz_code = alert_type )
                     and alert_type like 'A%';
            COMMIT ;-- 提交事务
    /* 2、正常数据的处理 */
        /* 2.1 栋舍异常表更新至正常*/
        update s_b_abnormal_info a set a.status = 'N',a.start_time = null,a.deal_status = null,a.deal_delay = null,a.deal_time = null,a.modify_time = now()
                where 1=1 
                and not exists( SELECT 1 from s_b_monitor_temp_alert b where a.house_id = b.houseid and a.alarm_code = b.alert_type);
        COMMIT ;-- 提交事务
        /* 2.2 处理已经报警的提醒*/
            -- 备份数据
            INSERT INTO s_b_alarm_done
                        (id, alarm_code, alarm_Name, farm_id, house_id, farm_breed_id, house_breed_id, monitor_id, actual_value, set_value,
                        value_unit, alarm_time, deal_status, deal_delay, deal_time, response_person,is_remove,remove_time)
            SELECT a.id,a.alarm_code,a.alarm_name,a.farm_id,a.house_id,a.farm_breed_id,a.house_breed_id,a.monitor_id,a.actual_value,a.set_value,
                         a.value_unit,a.alarm_time,a.deal_status,a.deal_delay,a.deal_time,a.response_person,
                        'Y', -- 已经消除
                        now() -- 消除时间
            from s_b_alarm_inco a where 1=1
            and not exists(SELECT 1 from s_b_monitor_temp_alert b where a.house_id = b.houseid and a.alarm_code = b.alert_type)
            and a.farm_id <> 0 ;
            -- 删除数据
            DELETE from s_b_alarm_inco where 1=1
                  and not exists(SELECT 1 from s_b_monitor_temp_alert b where house_id = b.houseid and alarm_code = b.alert_type)
            and farm_id <> 0 ;
            COMMIT ;-- 提交事务
    /* 3、异常数据的处理*/
        /* 3.1 上次正常，本次异常的数据处理*/
            set p_standard_delay_time = 3;          
            -- 第一次需要先插入数据
            INSERT INTO s_b_abnormal_info (farm_id, house_id, alarm_code, status, start_time, standar_delay, deal_status, deal_delay, modify_time)
            SELECT a.farmid,a.houseid,a.alert_type,'Y',now(),
                    (CASE when a.alert_type= 'C0002' then 0 else 
                    ifnull((SELECT bha.alarm_delay from s_b_layer_house_alarm bha where bha.house_id = a.houseid and bha.farm_id = a.farmid),0)
                      end), -- 除断电报警外，其他报警均延迟3分钟
                    '01',0,now()
            from s_b_monitor_temp_alert a
            where 1=1
            and NOT exists(SELECT 1 from s_b_abnormal_info b where a.houseid = b.house_id and a.alert_type = b.alarm_code);
            -- 已经存在的数据更新相应字段
            update s_b_abnormal_info a set a.status = 'Y',a.start_time = now(),
                        a.standar_delay = (CASE when a.alarm_code= 'C0002' then 0 else 
                        ifnull((SELECT bha.alarm_delay from s_b_layer_house_alarm bha where bha.house_id = a.house_id and bha.farm_id = a.farm_id),0)    
                          end), -- 除断电报警外，其他报警均延迟3分钟
                        a.deal_status = '01',a.deal_delay = 0,a.deal_time = null,a.modify_time = now()
            where 1=1
            and exists(SELECT 1 from s_b_monitor_temp_alert b where a.house_id = b.houseid and a.alarm_code = b.alert_type)
            and a.status = 'N' ;
            COMMIT ;-- 提交事务
        /* 3.2 上次异常，本次异常的数据s_b_abnormal_info表不需要处理*/
        /* 3.3 根据相应状态，判断是否需要生成 报警提醒表*/
            set p_cur_time = now();
            /* 3.3.1 根据s_b_abnormal_info表 查询理论上应该报警的数据，生成至表 s_b_alarm_should */
                    /*此处理论上应该报警的数据包括两部分：1、超过标准延时时间，且没有经过用户延时处理，应该报警的数据；2、超过用户处理时间，再次报警的数据 */
            TRUNCATE table s_b_alarm_should;
            INSERT into s_b_alarm_should(farm_id, house_id, alarm_code, status, start_time, standar_delay,
                                        deal_status, deal_delay, deal_time, modify_time) 
            SELECT * from s_b_abnormal_info b where 1=1
            and (case when b.deal_status = '01'  -- 没有经过延时处理
                      then date_add(b.start_time,INTERVAL b.standar_delay MINUTE)
                      when b.deal_status = '02'  -- 已经延时处理
                      then date_add(b.deal_time,INTERVAL b.deal_delay MINUTE)
                      end ) <= p_cur_time
            and not exists(SELECT 1 from s_b_alarm_inco c 
                        where b.house_id = c.house_id and b.alarm_code = c.alarm_code and c.deal_status = '01') -- 之前已经生成过的报警，且用户没有处理，则不在继续生成
            and b.status = 'Y';
            /* 3.3.2 删除已经过期的报警提醒(此处指超过用户处理时间，依旧需要报警的栋舍)*/
                -- 备份数据
                INSERT INTO s_b_alarm_done
                            (id, alarm_code, alarm_Name, farm_id, house_id, farm_breed_id, house_breed_id, monitor_id, actual_value, set_value,
                            value_unit, alarm_time, deal_status, deal_delay, deal_time, response_person,is_remove,remove_time)
                SELECT a.id,a.alarm_code,a.alarm_name,a.farm_id,a.house_id,a.farm_breed_id,a.house_breed_id,a.monitor_id,a.actual_value,a.set_value,
                             a.value_unit,a.alarm_time,a.deal_status,a.deal_delay,a.deal_time,a.response_person,
                            'N', -- 已经消除
                            null -- 消除时间
                from s_b_alarm_inco a where 1=1
                and exists(SELECT 1 from s_b_alarm_should b where a.house_id = b.house_id and a.alarm_code = b.alarm_code)
                and a.deal_status = '02'
                and date_add(a.alarm_time,INTERVAL a.deal_delay MINUTE) <= p_cur_time;
                -- 删除数据
                DELETE from s_b_alarm_inco where 1=1
                       and exists(SELECT 1 from s_b_alarm_should b where house_id = b.house_id and alarm_code = b.alarm_code)
                       and deal_status = '02'
                       and date_add(alarm_time,INTERVAL deal_delay MINUTE) <= p_cur_time ;
            /* 3.3.3 生成新的报警提醒 */
                -- (此处生成了第一次需要报警的栋舍、以及超过用户处理时间，新生成的报警)
                INSERT into s_b_alarm_inco(id, alarm_code, alarm_name, farm_id, house_id, farm_breed_id, house_breed_id, monitor_id, actual_value, set_value, 
                                           value_unit, alarm_time, deal_status, deal_delay, deal_time, response_person, is_remove, remove_time)
                SELECT null,a.alert_type,
                  (SELECT code_name from s_d_code where code_type = 'alarm_code' and biz_code = a.alert_type),
                  a.farmid,a.houseid,a.farmbreedid,a.housebreedid,a.monitor_id,a.soure_values,a.target_values,
                  null,now(),'01',null,null,null,'N',null
                from s_b_monitor_temp_alert a
                where 1=1
                and exists(SELECT 1 from s_b_alarm_should b where 1=1 
                              and a.houseid = b.house_id and a.alert_type = b.alarm_code)
                ;
                -- (超过用户处理时间的报警已经重新生成，所以延时处理状态需要还原)
                update s_b_abnormal_info b set b.deal_status = '01',b.deal_delay = 0,b.deal_time = null 
                      where 1=1
                      and b.deal_status = '02'
                      and date_add(b.deal_time,INTERVAL b.deal_delay MINUTE) <= p_cur_time
                      and b.status = 'Y';
    
    IF p_res = 'Fail' THEN
        ROLLBACK;
    ELSE
        COMMIT;
    END IF;
    
    select p_res;
END;