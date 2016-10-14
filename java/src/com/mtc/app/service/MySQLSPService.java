/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.mapper.app.MySQLSPMapper;

/**
 * @ClassName: SQLServerSPService
 * @Description: 
 * @Date 2015年9月14日 上午10:13:08
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class MySQLSPService {

	@Autowired
	private MySQLSPMapper tMySQLSPMapper;
	
	public void exec_s_p_createTargetMonitor(HashMap<String,Object> map){
		tMySQLSPMapper.exec_s_p_createTargetMonitor(map);
	};
	
	public void exec_s_b_task_test(HashMap<String,Object> map){
		tMySQLSPMapper.exec_s_b_task_test(map);
	};
	
	public String exec_s_p_dealMonitorAlarm(){
		return tMySQLSPMapper.exec_s_p_dealMonitorAlarm();
	};
	
	public void exec_s_p_createHouseTask(HashMap<String,Object> map){
		tMySQLSPMapper.exec_s_p_createHouseTask(map);
	};
	
	public void exec_s_p_createFarmTask(HashMap<String,Object> map){
		tMySQLSPMapper.exec_s_p_createFarmTask(map);
	};
}
