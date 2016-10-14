/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.task;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.mtc.app.controller.HouseReqController;
import com.mtc.app.service.MySQLSPService;
import com.mtc.common.util.PubFun;

/**
 * @ClassName: TempOtherTask
 * @Description: 
 * @Date 2016年4月13日 下午3:01:23
 * @Author Yin Guo Xiang
 * 
 */
public class TempOtherTask implements Runnable {
	
	private static Logger mLogger =Logger.getLogger(TempOtherTask.class);
	
	private MySQLSPService tMySQLSPService;
	
	private HashMap tHashMap; 
	
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	private String proName ;
	
	public MySQLSPService gettMySQLSPService() {
		return tMySQLSPService;
	}

	public void settMySQLSPService(MySQLSPService tMySQLSPService) {
		this.tMySQLSPService = tMySQLSPService;
	}
	
	public HashMap gettHashMap() {
		return tHashMap;
	}

	public void settHashMap(HashMap tHashMap) {
		this.tHashMap = tHashMap;
	}

	@Override
	public void run() {
		try{
			mLogger.info("正在执行存储过程。。。");
			if(PubFun.isNull(proName) 
					|| tMySQLSPService == null
					|| tHashMap == null){
				return;
			}
			if(proName.equals("createHouseTask")){
				mLogger.info("TempOtherTask.name==s_p_createHouseTask, 参数为" + tHashMap.toString());
				tMySQLSPService.exec_s_p_createHouseTask(tHashMap);
			}else if(proName.equals("createFarmTask")){
				mLogger.info("TempOtherTask.name==s_p_createFarmTask, 参数为" + tHashMap.toString());
				tMySQLSPService.exec_s_p_createFarmTask(tHashMap);
			}
		 }catch(Exception e){
			e.printStackTrace();
		 }
		mLogger.info("执行存储过程成功");
	}
}
