package com.mtc.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.biz.TaskTemplateReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.MySQLSPService;
import com.mtc.app.service.SBFarmTaskService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBFarmTask;

@Controller
@RequestMapping("/tsk/TaskTemplate")
public class TaskTemplateReqController {
	private static Logger mLogger = Logger
			.getLogger(TaskTemplateReqController.class);
	@Autowired
	private TaskTemplateReqManager tTaskTemplateReqManager;
	@Autowired
	private SBFarmTaskService tSBFarmTaskService;
	@Autowired
	private BaseQueryService tBaseQueryService;

	@Autowired
	private MySQLSPService tMySQLSPService;

	@RequestMapping("/addTsk")
	public void addTsk(HttpServletRequest request, HttpServletResponse response) {
		mLogger.info("=====New start executing TaskTemplateReqController.addTsk");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			int id_spa = jsonObject.getInt("id_spa");
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject prarObj = jsonObject.getJSONObject("params");
			int FarmId = prarObj.getInt("FarmId");
			String TaskName = prarObj.getString("TaskName");
			String TaskType = prarObj.getString("TaskType");
			String AgeInfos = prarObj.getString("AgeInfos");
			String TaskStatus = prarObj.getString("TaskStatus");
			
			if(TaskTemplateReqController.checkAgeInfos(AgeInfos)){
				SBFarmTask tSBFarmTask = new SBFarmTask();
				tSBFarmTask.setFarmId(FarmId);
				tSBFarmTask.setTaskType(TaskType);
				tSBFarmTask.setTaskName(TaskName);
				tSBFarmTask.setTaskCode("0");
				tSBFarmTask.setTaskSource("02");
				tSBFarmTask.setAgeInfos(AgeInfos);
				tSBFarmTask.setTaskStatus(TaskStatus);
				Date newdate = new Date();
				tSBFarmTask.setCreateDate(newdate);
				tSBFarmTask.setCreatePerson(id_spa);
				tSBFarmTask.setCreateTime(newdate);
				tSBFarmTask.setModifyDate(newdate);
				tSBFarmTask.setModifyTime(newdate);
				tSBFarmTask.setModifyPerson(id_spa);
				HashMap<String, Object> tPara = new HashMap<String, Object>();
				tPara.put("SBFarmTask", tSBFarmTask);
				int TskId = tTaskTemplateReqManager.addTsk(tPara);
	
				if (TaskType.equals("05")) {
					try {
						// 生成栋舍任务提醒
						HashMap tHashMap = new HashMap();
						tHashMap.put("in_farm_id", FarmId);
						tHashMap.put("in_apply_flag", "TempTask");
						tHashMap.put("in_temp_id", TskId);
						tMySQLSPService.exec_s_p_createHouseTask(tHashMap);
	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				resJson.put("TaskName", TaskName);
				resJson.put("TskId", TskId);
				resJson.put("Result", "Success");
			}else{
				resJson.put("Result", "Fail");
				resJson.put("ErrorMsg", "日龄格式错误。");
			}
			resJson.put("FarmId", FarmId);
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
		mLogger.info("=====Now end executing TaskTemplateReqController.addTsk");
	}

	@RequestMapping("/updateTsk")
	public void updateTsk(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====New start executing TaskTemplateReqController.updateTsk");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			int id_spa = jsonObject.getInt("id_spa");
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject prarObj = jsonObject.getJSONObject("params");
			int TskId = prarObj.getInt("TskId");
			int FarmId = prarObj.getInt("FarmId");
			String TaskName = prarObj.getString("TaskName");
			String TaskType = prarObj.getString("TaskType");
			String AgeInfos = prarObj.getString("AgeInfos");
			String TaskStatus = prarObj.getString("TaskStatus");
			if(TaskTemplateReqController.checkAgeInfos(AgeInfos)){
				SBFarmTask tSBFarmTask = tSBFarmTaskService
						.selectByPrimaryKey(TskId);
				tSBFarmTask.setFarmId(FarmId);
				tSBFarmTask.setTaskType(TaskType);
				tSBFarmTask.setTaskName(TaskName);
				tSBFarmTask.setAgeInfos(AgeInfos);
				tSBFarmTask.setTaskStatus(TaskStatus);
				Date newdate = new Date();
				tSBFarmTask.setModifyDate(newdate);
				tSBFarmTask.setModifyTime(newdate);
				tSBFarmTask.setModifyPerson(id_spa);
				HashMap<String, Object> tPara = new HashMap<String, Object>();
				tPara.put("SBFarmTask", tSBFarmTask);
				tTaskTemplateReqManager.updateTsk(tPara);
				
				if (TaskType.equals("05")) {
					try {
						// 生成栋舍任务提醒
						HashMap tHashMap = new HashMap();
						tHashMap.put("in_farm_id", FarmId);
						tHashMap.put("in_apply_flag", "TempTask");
						tHashMap.put("in_temp_id", TskId);
						tMySQLSPService.exec_s_p_createHouseTask(tHashMap);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				resJson.put("Result", "Success");
			}else{
				resJson.put("Result", "Fail");
				resJson.put("ErrorMsg", "日龄格式错误。");
			}
			resJson.put("FarmId", FarmId);
			resJson.put("TskId", TskId);
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
		mLogger.info("=====Now end executing TaskTemplateReqController.updateTsk");
	}
	
	private static boolean checkAgeInfos(String ageInfos){
		if(ageInfos == null || ageInfos.trim().equals("") 
				|| ageInfos.trim().endsWith("#")|| ageInfos.trim().startsWith("#")
					){
			return false;
		}else{
			ageInfos = ageInfos.trim();
			String temps[] = ageInfos.split("#");
			Pattern pattern = Pattern.compile("[0-9]*"); 
			for(int i = 0; i<temps.length; i++){
				Matcher isNum = pattern.matcher(temps[i]);
				if(!isNum.matches() || temps[i].equals("")){
			       return false; 
				}
			}
			return true;
		}
	}
	
	public static void main(String[] args) {
		System.out.print(TaskTemplateReqController.checkAgeInfos("1##78"));
	}
	
	@RequestMapping("/queryTskDetail")
	public void queryTskDetail(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====New start executing TaskTemplateReqController.queryTskDetail");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject prarObj = jsonObject.getJSONObject("params");
			int TskId = prarObj.getInt("TskId");
			SBFarmTask tSBFarmTask = tSBFarmTaskService
					.selectByPrimaryKey(TskId);
			if (tSBFarmTask != null) {
				String TaskName = tSBFarmTask.getTaskName();
				String TaskType = tSBFarmTask.getTaskType();
				String AgeInfos = tSBFarmTask.getAgeInfos();
				String TaskStatus = tSBFarmTask.getTaskStatus();
				resJson.put("TaskName", TaskName);
				resJson.put("TaskType", TaskType);
				resJson.put("AgeInfos", AgeInfos);
				resJson.put("TaskStatus", TaskStatus);
				resJson.put("TskId", TskId);
				resJson.put("Result", "Success");
				dealRes = Constants.RESULT_SUCCESS;
			} else {
				resJson.put("Result", "Fail");
				dealRes = Constants.RESULT_FAIL;
			}
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
		mLogger.info("=====Now end executing TaskTemplateReqController.queryTskDetail");
	}

	@RequestMapping("/queryTskList")
	public void queryTskList(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====New start executing TaskTemplateReqController.queryTskList");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject prarObj = jsonObject.getJSONObject("params");
			int FarmId = prarObj.getInt("FarmId");
			String TaskType = prarObj.optString("TaskType");
			String SQL = "SELECT id,task_name,age_infos,task_type,task_status FROM s_b_farm_task WHERE task_type = '"
					+ TaskType + "' AND farm_id = " + FarmId ;
			if(TaskType.equals("05")){
				SQL += " and bak1 is null " ;
			}
			
			mLogger.info("=========TaskTemplateReqController.queryTskList.SQL=" + SQL);
			List<HashMap<String, Object>> outcome = tBaseQueryService
					.selectMapByAny(SQL);
			if (outcome.size() != 0) {
				JSONArray TaskDetail = new JSONArray();
				for (HashMap<String, Object> hashMap : outcome) {
					JSONObject tjsonObject = new JSONObject();
					Object TskID = hashMap.get("id");
					Object ageInfos = hashMap.get("age_infos");
					Object TaskStatus = hashMap.get("task_status");
					Object TaskName = hashMap.get("task_name");
					Object TaskType1 = hashMap.get("task_type");
					tjsonObject.put("TskID", TskID);
					tjsonObject.put("ageInfos", ageInfos);
					tjsonObject.put("TaskStatus", TaskStatus);
					tjsonObject.put("TaskName", TaskName);
					tjsonObject.put("TaskType", TaskType1);
					TaskDetail.put(tjsonObject);
				}
				resJson.put("TaskDetail", TaskDetail);
				resJson.put("Result", "Success");
				resJson.put("FarmId", FarmId);

			} else {
				resJson.put("Result", "Fail");
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
		mLogger.info("=====Now end executing TaskTemplateReqController.queryTskList");
	}
}