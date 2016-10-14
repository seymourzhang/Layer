package com.mtc.app.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.biz.ActualTaskReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.MySQLSPService;
import com.mtc.app.service.SBHouseBreedTskServier;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBHouseBreedTsk;

@Controller
@RequestMapping("tsk/ActualTask")
public class ActualTaskReqController {

	private static Logger mLogger = Logger.getLogger(ActualTaskReqController.class);
	@Autowired
	private BaseQueryService tBaseQueryService;
	@Autowired
	private ActualTaskReqManager tActualTaskReqManager;
	@Autowired
	private SBHouseBreedTskServier tSBHouseBreedTskServier;
	@Autowired
	private MySQLSPService tMySQLSPService;
	
	@RequestMapping("/query")
	public void query(HttpServletRequest request, HttpServletResponse response) {	
		mLogger.info("=====New start executing ActualTaskReqController.query");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject prarObj = jsonObject.getJSONObject("params");
			int FarmBreedId = prarObj.getInt("FarmBreedId");
			int HouseId = prarObj.getInt("HouseId");
			int AgeBegin = prarObj.getInt("AgeBegin");
			int AgeEnd = prarObj.getInt("AgeEnd");
			String tSQL1 = "SELECT ifnull(sum(CASE WHEN agedata.curAge IS NULL " +
								"THEN if(a.age < 0 and a.deal_status ='00',1,0) " +
								"WHEN agedata.curAge < 0 " +
								"THEN if(a.age < 0  and a.deal_status ='00',1,0) " +
								"ELSE if(a.age < agedata.curAge  and a.deal_status ='00',1,0) END),0) AS unCompleteCount," +
								"ifnull(sum(CASE WHEN agedata.curAge IS NULL " +
								"THEN if(a.age < 0 and a.deal_status ='02',1,0) " +
								"WHEN agedata.curAge < 0 " +
								"THEN if(a.age < 0  and a.deal_status ='02',1,0) " +
								"ELSE if(a.age < agedata.curAge  and a.deal_status ='02',1,0) END),0) AS delayCount," +
								"ifnull(sum(CASE WHEN agedata.curAge IS NULL " +
								"THEN if(a.age < 0 and a.deal_status ='03',1,0) " +
								"WHEN agedata.curAge < 0 " +
								"THEN if(a.age < 0  and a.deal_status ='03',1,0) " +
								"ELSE if(a.age < agedata.curAge  and a.deal_status ='03',1,0) END),0) AS cancleCount " +
						"FROM s_b_house_breed_tsk a " +
						"INNER JOIN (SELECT s_f_getDayAgeByHouseId("+HouseId+",CURDATE()) AS curAge ) agedata " +
						"where a.house_id = "+HouseId+" and a.farm_breed_id = " +  FarmBreedId;
			
			mLogger.info("=========ActualTaskReqController.query.tSQL1=" + tSQL1);
			int tUnCompleteTask = 0;
			int delayCount = 0;
			int cancleCount = 0;
			List<HashMap<String, Object>> tCounts = tBaseQueryService.selectMapByAny(tSQL1);
			if(tCounts != null && tCounts.size() > 0){
				tUnCompleteTask = ((BigDecimal)tCounts.get(0).get("unCompleteCount")).intValue();
				delayCount = ((BigDecimal)tCounts.get(0).get("delayCount")).intValue();
				cancleCount = ((BigDecimal)tCounts.get(0).get("cancleCount")).intValue();
			}
			
			String tSQL2 = "SELECT a.id ,"
					+ "(CASE WHEN agedata.curAge IS NULL THEN (IF(a.age < 0, 'Y', 'N')) "
						  + "WHEN agedata.curAge < 0 THEN (IF(a.age<0,'Y','N')) "
					      + "ELSE IF(a.age = agedata.curage,'Y','N') END) AS  curAgeFlag, "
					+ "(CASE WHEN agedata.curAge IS NULL THEN (IF(a.age < 0, 'Y', 'N')) "
						  + "ELSE IF(a.age <= agedata.curage, 'Y', 'N') END) AS updateFlag,"
					+ "a.task_type,a.age,a.deal_status,a.task_name FROM s_b_house_breed_tsk a "
					+ "INNER JOIN (SELECT s_f_getDayAgeByHouseId(" + HouseId+ ",CURDATE()) AS curAge ) agedata "
					+ "WHERE  a.farm_breed_id = " + FarmBreedId
					+ " AND a.house_id = " + HouseId ;
			if (AgeEnd == 0 && AgeBegin == 0) {
				tSQL2 += " AND (CASE WHEN agedata.curAge IS NULL OR agedata.curAge < 0 THEN a.age <= 0 ELSE a.age BETWEEN (agedata.curAge-1) AND (agedata.curAge +1) END)" ;
			} else if (AgeEnd == -1 && AgeBegin == -1) {
				tSQL2 += " AND  a.age <= " + AgeEnd ;
			} else {
				tSQL2 += " AND  a.age BETWEEN " + AgeBegin + " AND " + AgeEnd ;
			}
			tSQL2 += " ORDER BY a.age,a.task_code";
			mLogger.info("=======ActualTaskReqController.query.tSQL2=" + tSQL2);
			List<HashMap<String, Object>> outcome = tBaseQueryService
					.selectMapByAny(tSQL2);
			JSONArray tTaskGrpArray = new JSONArray();
			if (outcome.size() > 0) {
				JSONArray tTaskArray = new JSONArray();
				JSONObject tTaskJson = null;
				String curAgeFlag= "N";
				for (HashMap<String, Object> hashMap : outcome) {
					int age = (int) hashMap.get("age");
					if (age < 0) {
						curAgeFlag = hashMap.get("curAgeFlag").toString();
						Object TaskName = hashMap.get("task_name");
						Object dealStatus = hashMap.get("deal_status");
						Object updateFlag = hashMap.get("updateFlag");
						Object id = hashMap.get("id");
						tTaskJson = new JSONObject();
						tTaskJson.put("dealStatus", dealStatus);
						tTaskJson.put("TaskName", TaskName);
						tTaskJson.put("TskSN", id);
						tTaskJson.put("UpdateFlag", updateFlag);
						tTaskArray.put(tTaskJson);
					}
				}
				JSONObject tJSONObject = new JSONObject();
				tJSONObject.put("TaskDetail", tTaskArray);
				tJSONObject.put("TskGrpName", "入雏前准备");
				tJSONObject.put("curAgeFlag", curAgeFlag);
				if (tTaskArray.length() > 0) {
					tTaskGrpArray.put(tJSONObject);
				}
				
				tTaskArray = new JSONArray();
				curAgeFlag= "N";
				for (HashMap<String, Object> hashMap : outcome) {
					int age = (int) hashMap.get("age");
					if (age == 0) {
						curAgeFlag = hashMap.get("curAgeFlag").toString();
						Object TaskName = hashMap.get("task_name");
						Object dealStatus = hashMap.get("deal_status");
						Object updateFlag = hashMap.get("updateFlag");
						Object id = hashMap.get("id");
						tTaskJson = new JSONObject();
						tTaskJson.put("dealStatus", dealStatus);
						tTaskJson.put("TaskName", TaskName);
						tTaskJson.put("TskSN", id);
						tTaskJson.put("UpdateFlag", updateFlag);
						tTaskArray.put(tTaskJson);
					}
				}
				tJSONObject = new JSONObject();
				tJSONObject.put("TaskDetail", tTaskArray);
				tJSONObject.put("TskGrpName", "入雏日");
				tJSONObject.put("curAgeFlag", curAgeFlag);
				if (tTaskArray.length() > 0) {
					tTaskGrpArray.put(tJSONObject);
				}
				
				tTaskArray = new JSONArray();
				Integer lastTaskAge = 0;
				curAgeFlag= "N";
				for (HashMap<String, Object> hashMap : outcome) {
					Integer age = (int) hashMap.get("age");
					if (age <= 0) {
						continue;
					}
					if(lastTaskAge != age ){
						if(tTaskArray.length() > 0){
							tJSONObject = new JSONObject();
							tJSONObject.put("TaskDetail", tTaskArray);
							tJSONObject.put("TskGrpName", lastTaskAge + "日龄");
							tJSONObject.put("curAgeFlag", curAgeFlag);
							tTaskGrpArray.put(tJSONObject);
						}
						tTaskArray = new JSONArray();
					}
					lastTaskAge = age;
					Object TaskName = hashMap.get("task_name");
					Object dealStatus = hashMap.get("deal_status");
					Object id = hashMap.get("id");
					Object updateFlag = hashMap.get("updateFlag");
					curAgeFlag = hashMap.get("curAgeFlag").toString();
					tTaskJson = new JSONObject();
					tTaskJson.put("dealStatus", dealStatus);
					tTaskJson.put("TaskName", TaskName);
					tTaskJson.put("UpdateFlag", updateFlag);
					tTaskJson.put("TskSN", id);
					tTaskArray.put(tTaskJson);
				}
				if(tTaskArray.length() > 0){
					tJSONObject = new JSONObject();
					tJSONObject.put("TaskDetail", tTaskArray);
					tJSONObject.put("TskGrpName", lastTaskAge + "日龄");
					tJSONObject.put("curAgeFlag", curAgeFlag);
					tTaskGrpArray.put(tJSONObject);
				}
			}
			resJson.put("FarmBreedId", FarmBreedId);
			resJson.put("HouseId", HouseId);
			resJson.put("Result", "Success");
			resJson.put("UnCompleteTaskNum", tUnCompleteTask);
			resJson.put("delayCount", delayCount);
			resJson.put("cancleCount", cancleCount);
			resJson.put("TskInfo", tTaskGrpArray);
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL;
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now end executing ActualTaskReqController.query");
	}

	@RequestMapping("/deal")
	public void deal(HttpServletRequest request, HttpServletResponse response) {
		mLogger.info("=====New start executing ActualTaskReqController.deal");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			int id_spa = jsonObject.getInt("id_spa");
			JSONObject prarObj = jsonObject.getJSONObject("params");
			int TskSN = prarObj.getInt("TskSN");
			int FarmBreedId = prarObj.getInt("FarmBreedId");
			int HouseId = prarObj.getInt("HouseId");
			String TaskName = prarObj.getString("TaskName");
			String dealStatus = prarObj.getString("dealStatus");
			Date curDate = new Date();
			if(!dealStatus.equals("0")
					&& !dealStatus.equals("01")
					&& !dealStatus.equals("02")
					&& !dealStatus.equals("03")){
				resJson.put("Result", "Fail");
				resJson.put("ErrorMsg", "任务处理状态异常。");
			}else{
				SBHouseBreedTsk tSBHouseBreedTsk = tSBHouseBreedTskServier
						.selectByPrimaryKey(TskSN);
				String originStatus = tSBHouseBreedTsk.getDealStatus();
				tSBHouseBreedTsk.setDealStatus(dealStatus);
				tSBHouseBreedTsk.setDealTime(curDate);
				tSBHouseBreedTsk.setDealLog(tSBHouseBreedTsk.getDealLog()+";"+originStatus);
				tSBHouseBreedTsk.setModifyDate(curDate);
				tSBHouseBreedTsk.setModifyTime(curDate);
				tSBHouseBreedTsk.setModifyPerson(id_spa);
				HashMap<String, Object> tPara = new HashMap<String, Object>();
				tPara.put("SBHouseBreedTsk", tSBHouseBreedTsk);
				int outcome = tActualTaskReqManager.updateByPrimaryKey(tPara);
				if (outcome != 0) {
					resJson.put("FarmBreedId", FarmBreedId);
					resJson.put("HouseId", HouseId);
					resJson.put("Result", "Success");
					resJson.put("TskSN", TskSN);
					resJson.put("TaskName", TaskName);
					resJson.put("dealStatus", dealStatus);
				}else{
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", "提交数据失败，请联系管理员。");
				}
			}
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL;
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now end executing ActualTaskReqController.deal");
	}
}
