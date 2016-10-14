/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mtc.app.service.*;
import com.mtc.entity.app.*;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.biz.RemindReqManager;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;

/**
 * 
 * 农场报警设置类
 * 
 * @author lx
 * 
 */

@Controller
@RequestMapping("/sys/farm/remind")
public class FarmRemindReqController {

	private static Logger mLogger = Logger.getLogger(FarmRemindReqController.class);
	
	@Autowired
	private BaseQueryService tBaseQueryService;
	
	@Autowired
	private SBRemindSettingService remindSettingService;

	@Autowired
	private SBRemindAlarmcodeService remindAlarmcodeService;
	@Autowired
	private SBRemindAlarmerService alarmerService;
	@Autowired
	private SBRemindSwitchService remindSwitchService;
	
	@Autowired
	private RemindReqManager remindReqManager;
	
	@Autowired
	private SDUserOperationService operationService;

	@Autowired
	private SDHouseService tSDHouseService;

	@RequestMapping("/saveSettingData")
	public void saveSettingData(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====New start executing AlarmReqController.saveSettingData");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			int personId = jsonObject.optInt("id_spa");
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject prarObj = jsonObject.getJSONObject("params");
			JSONObject farmAlarmSetting = prarObj.getJSONObject("farmAlarmSetting");
			org.json.JSONArray alarmers = prarObj.getJSONArray("alarmers");
			org.json.JSONArray enableds = prarObj.getJSONArray("enableds");
			
			int FarmId = prarObj.getInt("FarmId");
			int HouseId = prarObj.getInt("HouseId");
			int AlarmType = prarObj.getInt("RemindMethod");

			org.json.JSONArray alarmCodes = prarObj.getJSONArray("alarmCodes");
			
			// 农场设置
			SBRemindSetting alarmSetting = new SBRemindSetting();
			int farmAlarmSettingId = -1;
			try {
				farmAlarmSettingId = farmAlarmSetting.getInt("id");
			} catch (Exception e) {
				farmAlarmSettingId = 0;
			}
			
			Date createTime = new Date(System.currentTimeMillis());
			
			alarmSetting.setRemindMethod(farmAlarmSetting.getInt("remindMethod"));
			alarmSetting.setFarmId(farmAlarmSetting.getInt("farmId"));
			alarmSetting.setPersonReleHouse(farmAlarmSetting.getString("personReleHouse"));
			alarmSetting.setSwitchReleHouse(farmAlarmSetting.getString("switchReleHouse"));
			alarmSetting.setAlarmReleHouse(farmAlarmSetting.getString("alarmReleHouse"));
			alarmSetting.setBak1(farmAlarmSetting.getString("bak1"));
			alarmSetting.setBak2(farmAlarmSetting.getString("bak2"));
			
			// alarm code 
			List<SBRemindAlarmcode> codeSettings = new ArrayList<>();
			for(int i = 0 ; i < alarmCodes.length(); i++){
				JSONObject code = alarmCodes.getJSONObject(i);
				SBRemindAlarmcode codeSetting = new SBRemindAlarmcode();
				codeSetting.setAlarmCode(code.getString("alarmCode"));
				codeSetting.setRemindMethod(AlarmType);
				codeSetting.setCreatePerson(personId);
				codeSetting.setCreateTime(createTime);
				codeSetting.setFarmId(FarmId);
				codeSetting.setHouseId(code.getInt("houseId"));
				
				codeSetting.setModifyPerson(personId);
				codeSetting.setModifyTime(createTime);
				
				codeSettings.add(codeSetting);
				
				
			}
			
			// reminder
			List<SBReminder> alarmerList = new ArrayList<>();
			for(int i = 0 ; i < alarmers.length(); i++){
				JSONObject alarmer = alarmers.getJSONObject(i);
				SBReminder obj = new SBReminder();
				obj.setRemindMethod(alarmer.getInt("remindMethod"));
				obj.setBak1(alarmer.getString("bak1"));
				obj.setBak2(alarmer.getString("bak2"));
				
				obj.setFarmId(alarmer.getInt("farmId"));
				obj.setHouseId(alarmer.getInt("houseId"));
				
				obj.setUserId(alarmer.getInt("userId"));
				obj.setUserOrder(alarmer.getInt("userOrder"));
				obj.setUserType(alarmer.getInt("userType"));
				obj.setCreatePerson(personId);
				obj.setCreateTime(createTime);
				
				obj.setModifyPerson(personId);
				obj.setModifyTime(createTime);
				
				
				
				alarmerList.add(obj);
				
			}
			
			List<SBRemindSwitch> alarmEnableds = new ArrayList<>();
			
			// 报警开关
			for(int i = 0 ; i < enableds.length() ; i++){
				JSONObject enabled = enableds.getJSONObject(i);
				SBRemindSwitch alarmEnabled = new SBRemindSwitch();
				alarmEnabled.setStatus(enabled.getString("status"));
				alarmEnabled.setRemindMethod(enabled.getInt("remindMethod"));
				alarmEnabled.setFarmId(enabled.getInt("farmId"));
				alarmEnabled.setHouseId(enabled.getInt("houseId"));
				
				alarmEnabled.setCreatePerson(personId);
				alarmEnabled.setCreateTime(createTime);
				
				alarmEnabled.setModifyPerson(personId);
				alarmEnabled.setModifyTime(createTime);
				alarmEnableds.add(alarmEnabled);
				
			}
			
			
			if(farmAlarmSettingId == -1) {
				// add 
				operationService.insert(
						SDUserOperationService.MENU_FARM_ALARM_SETTING,
						SDUserOperationService.OPERATION_ADD,
						personId);
				alarmSetting.setCreateTime(createTime);
				
				//remindReqManager.saveFarmRemind(alarmSetting, codeSettings, alarmerList, alarmEnableds);
				
			}else{
				// update 
				operationService.insert(
						SDUserOperationService.MENU_FARM_ALARM_SETTING,
						SDUserOperationService.OPERATION_UPDATE,
						personId);
				
				
			}
			alarmSetting.setCreateTime(createTime);
			
			remindReqManager.updateFarmRemind(alarmSetting,HouseId, codeSettings, alarmerList, alarmEnableds);
			
			
			dealRes = Constants.RESULT_SUCCESS;
			
			resJson = getSBFarmAlarmSetting(FarmId,HouseId, AlarmType);
			
			resJson.put("Result", "Success");
			
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
		mLogger.info("=====Now end executing AlarmReqController.saveSettingData");
	}

	@RequestMapping("/querySettingData")
	public void querySettingData(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====New start executing FarmAlarmReqController.querySettingData");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(
					SDUserOperationService.MENU_FARM_ALARM_SETTING,
					SDUserOperationService.OPERATION_SELECT,
					jsonObject.optInt("id_spa"));

			JSONObject prarObj = jsonObject.getJSONObject("params");
			int FarmId = prarObj.getInt("FarmId");
			int HouseId = prarObj.getInt("HouseId");
			int AlarmType = prarObj.getInt("RemindMethod");

			resJson = getSBFarmAlarmSetting(FarmId,HouseId, AlarmType);

			resJson.put("Result", "Success");
			resJson.put("FarmId", FarmId);
			resJson.put("HouseId", HouseId);
			resJson.put("AlarmType", AlarmType);
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
		mLogger.info("=====Now end executing FarmAlarmReqController.querySettingData");
	}
	
	public JSONObject getSBFarmAlarmSetting(int FarmId,int houseId,int AlarmType) throws JSONException{
		JSONObject resJson = new JSONObject();
		SBRemindSetting farmAlarmSetting = remindSettingService.selectByPrimaryKey(FarmId,AlarmType);
		List<SBRemindSwitch> enableds = new ArrayList<>();
		List<SBReminder> alarmers = new ArrayList<>();
		if(farmAlarmSetting != null){
			String personStatus = farmAlarmSetting.getPersonReleHouse();
			String targetStatus = farmAlarmSetting.getAlarmReleHouse();
			//String targetSetStatus = farmAlarmSetting.getTargetSetStatus();
			if(targetStatus.equals("N")){
				enableds = remindSwitchService.selectByFarmIdAndAlarmType(FarmId, AlarmType);
			}else{
				enableds = remindSwitchService.selectByFarmIdAndAlarmTypeAndHouseId(FarmId,houseId, AlarmType);
			}
			
			if(personStatus.equals("N")){
				alarmers = alarmerService.selectByFarmIdAndAlarmType(FarmId, AlarmType);
			}else{
				alarmers = alarmerService.selectByFarmIdAndAlarmTypeAndHouseId(FarmId,houseId, AlarmType);
			}
		}
		
		resJson.put("farmAlarmSetting", net.sf.json.JSONObject.fromObject(farmAlarmSetting));
		resJson.put("enableds", net.sf.json.JSONArray.fromObject(enableds));
		resJson.put("alarmers", net.sf.json.JSONArray.fromObject(alarmers));
		
		return resJson;
	}

	@RequestMapping("/querySettingData_v2")
	public void querySettingData_v2(HttpServletRequest request, HttpServletResponse response) {
		mLogger.info("=====New start executing FarmAlarmReqController.querySettingData_v2");
		JSONArray results = new JSONArray();
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(
					SDUserOperationService.MENU_FARM_ALARM_SETTING,
					SDUserOperationService.OPERATION_SELECT,
					jsonObject.optInt("id_spa"));

			JSONObject prarObj = jsonObject.getJSONObject("params");
			int FarmId = prarObj.getInt("FarmId");
			int AlarmType = prarObj.getInt("RemindMethod");

			List<SDHouse> houses = tSDHouseService.selectHousesByFarmId(FarmId);
			results = getSBFarmAlarmSettingList(FarmId, houses, AlarmType);

			resJson.put("Result", "Success");
			resJson.put("houseAlarmSetting", results);
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
		mLogger.info("=====Now end executing FarmAlarmReqController.querySettingData_v2");
	}

	public JSONArray getSBFarmAlarmSettingList(int FarmId, List<SDHouse> houses, int AlarmType) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject resJson = new JSONObject();
		JSONObject res = new JSONObject();
		JSONArray resA = new JSONArray();
		SBRemindSetting farmAlarmSetting = new SBRemindSetting();
		for (SDHouse house : houses) {
			resA = new JSONArray();
			resJson = new JSONObject();
			res = new JSONObject();
			List<SBRemindSwitch> enableds = new ArrayList<>();
			List<SBReminder> alarmers = new ArrayList<>();
			farmAlarmSetting = remindSettingService.selectByPrimaryKey(FarmId, AlarmType);
			if (farmAlarmSetting != null) {
				String personStatus = farmAlarmSetting.getPersonReleHouse();
				String targetStatus = farmAlarmSetting.getAlarmReleHouse();
				if (targetStatus.equals("N")) {
					enableds = remindSwitchService.selectByFarmIdAndAlarmType(FarmId, AlarmType);
				} else {
					enableds = remindSwitchService.selectByFarmIdAndAlarmTypeAndHouseId(FarmId, house.getId(), AlarmType);
				}
				if (personStatus.equals("N")) {
					alarmers = alarmerService.selectByFarmIdAndAlarmType(FarmId, AlarmType);
				} else {
					alarmers = alarmerService.selectByFarmIdAndAlarmTypeAndHouseId(FarmId, house.getId(), AlarmType);
				}
				for (SBReminder alarmer : alarmers) {
					res = new JSONObject();
					res.put("userId", alarmer.getUserId());
					res.put("userOrder", alarmer.getUserOrder());
					res.put("userType", alarmer.getUserType());
					resA.put(res);
				}
//				resJson.put("status", enableds.size() == 0 ? "false" : (enableds.get(0).getStatus().equals("N") ? "false" : "true"));
				resJson.put("status", enableds.size() == 0 ? "N" : (enableds.get(0).getStatus().equals("N") ? "N" : "Y"));
			}
			resJson.put("alarmers", resA);
			resJson.put("HouseId", house.getId());
			resJson.put("HouseName", house.getHouseName());
			jsonArray.put(resJson);
		}
		return jsonArray;
	}

}
