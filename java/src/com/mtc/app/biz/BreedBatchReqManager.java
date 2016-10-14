/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SBFarmBreedService;
import com.mtc.app.service.SBFarmSettleService;
import com.mtc.entity.app.SBFarmBreed;
import com.mtc.entity.app.SBFarmSettle;

/**
 * @ClassName: BreedBatchReqManager
 * @Description: 
 * @Date 2015-12-4 下午2:29:24
 * @Author Shao Yao Yu
 * 
 */
@Component
public class BreedBatchReqManager {
	
	private static Logger mLogger =Logger.getLogger(BreedBatchReqManager.class);
	
	@Autowired
	private SBFarmBreedService tSBFarmBreedService;
	
	@Autowired
	private SBFarmSettleService tSBFarmSettleService;
	
	@Autowired
	private BaseQueryService tBaseQueryService;
	
	
	public SBFarmBreed dealSave(HashMap<String,Object> tPara){
		SBFarmBreed tSBFarmBreed = (SBFarmBreed)tPara.get("SBFarmBreed");

		tSBFarmBreedService.insertSBFarmBreed(tSBFarmBreed);

		// 修改待完成任务为当前批次  
		String SQL = "UPDATE s_b_house_breed_tsk d set farm_breed_id = "+tSBFarmBreed.getId()+" where 1=1 and exists( "
						+ " SELECT 1 from s_d_house c where d.house_id = c.id and c.farm_id = " + tSBFarmBreed.getFarmId()
						+ ") and farm_breed_id = 0 ";
		tBaseQueryService.updateIntergerByAny(SQL);
		
		mLogger.info("新增批次：" + tSBFarmBreed.getId());
		return tSBFarmBreed;
	}
	public SBFarmBreed dealUpdate(HashMap<String,Object> tPara){
		SBFarmBreed tSBFarmBreed = (SBFarmBreed)tPara.get("SBFarmBreed");

		tSBFarmBreedService.updateByPrimaryKey(tSBFarmBreed);
		
		mLogger.info("更新批次：" + tSBFarmBreed.getId());		
		return tSBFarmBreed;
	}
	public SBFarmBreed settleBatch(HashMap<String,Object> tPara){
		SBFarmBreed tSBFarmBreed = (SBFarmBreed)tPara.get("SBFarmBreed");
		tSBFarmBreedService.updateByPrimaryKey(tSBFarmBreed);
		
		String sql = "UPDATE s_b_farm_task a set a.task_status = 'N',a.bak1 = '"+tSBFarmBreed.getId()+"' where a.task_type = '05' and a.task_status = 'Y' and a.farm_id = " + tSBFarmBreed.getFarmId() ;	
		tBaseQueryService.updateIntergerByAny(sql);
		
		mLogger.info("保存批次：" + tSBFarmBreed.getId());		
		return tSBFarmBreed;
	}
	public int intterSBFarmSettle(HashMap<String,Object> tPara){
		List<SBFarmSettle> tList = (List<SBFarmSettle>) tPara.get("tList");
		int FarmBreedId = tList.get(0).getFarmBreedId();
		String SQL ="DELETE  FROM  s_b_farm_settle  WHERE farm_breed_id ="+FarmBreedId;
		tBaseQueryService.deleteByAny(SQL);
		mLogger.info("保存批次：" + tList.get(0).getId());
		return tSBFarmSettleService.Listinsert(tList);
	}
	
}
