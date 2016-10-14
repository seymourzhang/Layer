/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBUserFarmService;
import com.mtc.app.service.SDFarmService;
import com.mtc.app.service.SDUserService;
import com.mtc.entity.app.SBUserFarm;
import com.mtc.entity.app.SDFarm;

/**
 * @ClassName: FarmReqManager
 * @Description: 
 * @Date 2015年11月24日 下午1:30:43
 * @Author Yin Guo Xiang
 * 
 */
@Component
public class FarmReqManager {

	private static Logger mLogger =Logger.getLogger(FarmReqManager.class);
	
	@Autowired
	private SDFarmService mSDFarmService;
	@Autowired
	private SDUserService mSDUserService;
	@Autowired
	private SBUserFarmService mSBUserFarmService;
	
	public SDFarm dealSave(HashMap<String,Object> tPara) throws Exception{
		SDFarm tSDFarm = (SDFarm)tPara.get("Farm");
		SBUserFarm tSBUserFarm = (SBUserFarm)tPara.get("UserFarm");
		
		mSDFarmService.insertFarm(tSDFarm);
		tSBUserFarm.setFarmId(tSDFarm.getId());
		mSBUserFarmService.insert(tSBUserFarm);
		
		mLogger.info("新增农场id：" + tSDFarm.getId());		
		return tSDFarm;
	}
	public int dealDelete(int id,int userId) throws Exception{
		
		Date curDate = new Date();
		SDFarm tSDFarm = mSDFarmService.selectByPrimaryKey(id);
		tSDFarm.setFreezeStatus("1");
		tSDFarm.setModifyPerson(userId);
		tSDFarm.setModifyDate(curDate);
		tSDFarm.setModifyTime(curDate);
    	int	result = mSDFarmService.updatetFarm(tSDFarm);
		
    	mLogger.info("删除农场id：" + id);	
		return  result;
	}
	
	public SDFarm dealUpdate(HashMap<String,Object> tPara) throws Exception{
		
		SDFarm tSDFarm = (SDFarm)tPara.get("Farm");
		mSDFarmService.updatetFarm(tSDFarm);
		
		mLogger.info("修改农场id：" + tSDFarm.getId());		
		return tSDFarm;
	}

	
	
}
