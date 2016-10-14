/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.mapper.app;

import java.util.HashMap;

/**
 * @ClassName: MySQLSPMapper
 * @Description: 
 * @Date 2015年9月11日 上午10:19:56
 * @Author Yin Guo Xiang
 * 
 */
public interface MySQLSPMapper {
	
	public String exec_s_p_createTargetMonitor(HashMap<String,Object> map);
	
	public String exec_s_b_task_test(HashMap<String,Object> map);
	
	public String exec_s_p_dealMonitorAlarm();
	
	public String exec_s_p_createHouseTask(HashMap<String,Object> map);
	
	public String exec_s_p_createFarmTask(HashMap<String,Object> map);
	
}
