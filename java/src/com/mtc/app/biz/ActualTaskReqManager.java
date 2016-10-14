package com.mtc.app.biz;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBHouseBreedTskServier;
import com.mtc.entity.app.SBHouseBreedTsk;
import com.mtc.mapper.app.SBHouseBreedTskMapper;

@Component
public class ActualTaskReqManager {
	private static Logger mLogger =Logger.getLogger(BreedBatchReqManager.class);
	@Autowired
	private SBHouseBreedTskServier tSBHouseBreedTskServier;
	
	public int updateByPrimaryKey(HashMap<String,Object> tPara){
		SBHouseBreedTsk tSBHouseBreedTsk = (SBHouseBreedTsk) tPara.get("SBHouseBreedTsk");
		mLogger.info("更新任务提醒明细：" + tSBHouseBreedTsk.getId());		
		return tSBHouseBreedTskServier.updateByPrimaryKey(tSBHouseBreedTsk);
	}
}
