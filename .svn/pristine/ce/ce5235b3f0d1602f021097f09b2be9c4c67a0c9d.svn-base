/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBDeviHouseService;
import com.mtc.app.service.SDHouseService;
import com.mtc.entity.app.SBDeviHouse;
import com.mtc.entity.app.SDHouse;

/**
 * @ClassName: HouseReqManager
 * @Description: 
 * @Date 2015年11月24日 下午3:48:56
 * @Author Yin Guo Xiang
 * 
 */
@Component
public class HouseReqManager {
	private static Logger mLogger =Logger.getLogger(HouseReqManager.class);
	
	@Autowired
	private SDHouseService mSDHouseService;
	@Autowired
	private SBDeviHouseService mSBDeviHouseService;
	
	public SDHouse dealSave(HashMap<String,Object> tPara) throws Exception{
		SDHouse tSDHouse = (SDHouse)tPara.get("House");
		SBDeviHouse tSBDeviHouse = (SBDeviHouse)tPara.get("HouseDevice");
		
		mSDHouseService.insertHouse(tSDHouse);
		mLogger.info("新增栋舍id：" + tSDHouse.getId());
		if(tSBDeviHouse.getDeviceCode() != null && !tSBDeviHouse.getDeviceCode().equals("")){
			tSBDeviHouse.setHouseId(tSDHouse.getId());
			mSBDeviHouseService.insertDeviceHouse(tSBDeviHouse);
		}
		return tSDHouse;
	}
	public SDHouse dealUpdate(HashMap<String,Object> tPara) throws Exception{
		SDHouse tSDHouse = (SDHouse)tPara.get("House");
		SBDeviHouse tSBDeviHouse = (SBDeviHouse)tPara.get("HouseDevice");
		
	    mSDHouseService.updateByPrimaryKey(tSDHouse);
		mLogger.info("修改栋舍id：" + tSDHouse.getId());
		
		mSBDeviHouseService.deleteByHouseid(tSBDeviHouse.getHouseId());
		if(tSBDeviHouse.getDeviceCode() != null && !tSBDeviHouse.getDeviceCode().equals("")){
			mSBDeviHouseService.insertDeviceHouse(tSBDeviHouse);
		}
		return tSDHouse;
	}
	
	public SDHouse dealDelete(HashMap<String,Object> tPara) throws Exception{
		
		SDHouse tSDHouse = (SDHouse)tPara.get("House");
		
		mSDHouseService.updateByPrimaryKey(tSDHouse);
		
		mLogger.info("删除栋舍id：" + tSDHouse.getId());
		
		int Houseid=tSDHouse.getId();
		mSBDeviHouseService.deleteByHouseid(Houseid);
		
		return tSDHouse;
	}
}
