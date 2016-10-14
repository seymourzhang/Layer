/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.scheduler;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mtc.app.service.MySQLSPService;
import com.mtc.common.util.IPUtil;

/**
 * @ClassName: AlarmDealExecuteJob
 * @Description: 
 * @Date 2015年11月30日 上午11:24:32
 * @Author Yin Guo Xiang
 * 
 */

public class AlarmDealExecuteJob {
	private static Logger mLogger =Logger.getLogger(AlarmDealExecuteJob.class);
	
	@Autowired
	private MySQLSPService tMySQLSPService;
	
	private static int counter = 0;
	
	public void Service() {
		if(!IPUtil.needRunTask()){
			return ;
		}
		counter ++ ;
		mLogger.info("=====Now 开始  第 " + counter +" 次 AlarmDealExecuteJob.Service");
		try {
//			HashMap tHashMap = new HashMap();
//            tHashMap.put("in_name", "sys" + counter);

            String resl = tMySQLSPService.exec_s_p_dealMonitorAlarm();
            mLogger.info("=====执行结果：" + resl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mLogger.info("=====Now 结束  第 " + counter +" 次 AlarmDealExecuteJob.Service");
	}
}
