/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SBAbnormalInfoService;
import com.mtc.app.service.SBAlarmIncoService;

/**
 * @ClassName: MonitoringReqManager
 * @Description: 
 * @Date 2015-12-22 上午11:49:50
 * @Author Shao Yao Yu
 * 
 */
@Component
public class MonitoringReqManager {

	private static Logger mLogger =Logger.getLogger(AlarmReqManager.class);
	
	@Autowired
	private SBAlarmIncoService tSBAlarmIncoService;
	@Autowired
	private SBAbnormalInfoService tSBAbnormalInfoService;
	@Autowired
	private BaseQueryService tBaseQueryService;
	public void updateSave(HashMap<String,Object> tPara){
		int userId = (int) tPara.get("modifyUserId");
		int delayTime = (int) tPara.get("delayTime");
		int HouseId = (int) tPara.get("HouseId");
		tSBAlarmIncoService.updateToDealedByHouseId(userId, delayTime, HouseId);
		tSBAbnormalInfoService.updateToDelayedByHouseId(userId, delayTime, HouseId);
	}
	
	public void updateSave_v2(HashMap<String,Object> tPara) throws JSONException{
		JSONArray CurAlarmData = (JSONArray) tPara.get("CurAlarmData");
		int userId = (int) tPara.get("modifyUserId");
		for (int i = 0; i < CurAlarmData.length(); i++) {
		  JSONObject  tJSONObject = (JSONObject) CurAlarmData.get(i);
		  int HouseId = (int) tJSONObject.get("houseId");
		  int delayTime = (int)  tJSONObject.get("delayTime");
		  String SQL1 = "  UPDATE s_b_abnormal_info SET deal_status = '02', deal_delay = "+delayTime+", deal_time = NOW(), modify_time = NOW() "
				  		+ "  WHERE house_id = "+HouseId ;
			mLogger.info("sql"+SQL1);
		  tBaseQueryService.updateIntergerByAny(SQL1);
		  String SQL2 = "  UPDATE s_b_alarm_inco SET deal_status = '02', deal_delay = "+delayTime+", deal_time = NOW(), response_person = "+userId+""
				  		+ " WHERE house_id = "+HouseId+" and deal_status = '01' ";
			mLogger.info("sql"+SQL2);
		  tBaseQueryService.updateIntergerByAny(SQL2); 
		}
	}
}












