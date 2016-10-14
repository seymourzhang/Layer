/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.mtc.app.biz.MonitoringReqManager;
import com.mtc.app.service.AppDeviceService;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SBAlarmIncoService;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBAlarmInco;
import com.mtc.entity.app.SLAlarmRequest;

/**
 * @ClassName: MonitoringReqController
 * @Description:
 * @Date 2015-12-1 下午12:03:53
 * @Author Shao Yao Yu
 * 
 */
@Controller
@RequestMapping("/envCtrl")
public class MonitoringReqController {

	private static Logger mLogger = Logger
			.getLogger(MonitoringReqController.class);

	@Autowired
	private BaseQueryService tBaseQueryService;
	@Autowired
	private SBAlarmIncoService tSBAlarmIncoService;
	@Autowired
	private MonitoringReqManager tMonitoringReqManager;
	@Autowired
	private AppDeviceService tAppDeviceService;
	@Autowired
	private SDUserOperationService operationService;

	@RequestMapping("/monitoring")
	public void monitoring(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=======Now start executing MonitoringReqController.monitoring");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("updateFarm.para=" + paraStr);
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			operationService.insert(SDUserOperationService.MENU_ENVIMONITOR, SDUserOperationService.OPERATION_SELECT, jsonobject.optInt("id_spa"));

			/** 业务处理开始，查询、增加、修改、或删除 **/
			int id_spa = jsonobject.optInt("id_spa");
			String sql = "SELECT a.id,a.house_name AS houseName, "
									+ "IFNULL(s_f_getDayAgeByHouseId(a.id,curdate()),'-') AS dayAge, "
									+ "IFNULL(ROUND(dc.inside_avg_temp,1) ,'-')  AS avg_temp, "
									+ "IFNULL(ROUND(dc.inside_humidity,1),'-')  AS humi, "
									+ "IFNULL(ROUND(dc.inside_set_temp,1),'-')  AS tar_temp , "
									+ "IFNULL(CONCAT(ROUND(dc.inside_temp1,1),'℃') ,'-')  AS tempLeft1, "
									+ "IFNULL(CONCAT(ROUND(dc.inside_temp2,1),'℃') ,'-')  AS tempLeft2,  "
									+ "IFNULL(CONCAT(ROUND(dc.inside_temp3,1),'℃') ,'-')  AS tempMiddle1,  "
									+ "IFNULL(CONCAT(ROUND(dc.inside_temp4,1),'℃') ,'-')  AS tempMiddle2,  "
									+ "IFNULL(CONCAT(ROUND(dc.inside_temp5,1),'℃') ,'-')  AS tempRight1,  "
									+ "IFNULL(CONCAT(ROUND(dc.inside_temp6,1),'℃') ,'-')  AS tempRight2, "
									+ "IFNULL(CONCAT(ROUND(dc.outside_temp,1),'℃') ,'-')  AS out_temp , "
									+ "IFNULL(ROUND(dc.high_alarm_temp,1) ,'-')  AS high_alarm_temp , "
									+ "IFNULL(ROUND(dc.low_alarm_temp,1) ,'-')  AS low_alarm_temp , "
									+ "IFNULL(ROUND(dc.point_temp_diff,1),'-')  AS point_temp , "
									+ "IFNULL((SELECT (CASE WHEN alarm_code like '%H' THEN 'H' WHEN alarm_code like '%L' THEN 'L' ELSE 'N' END) FROM s_b_alarm_inco al WHERE al.house_id = a.id AND al.alarm_code IN ('A001H','A001L','A002H','A002L') LIMIT 1),'N') AS temp_in1_alarm, "
									+ "IFNULL((SELECT (CASE WHEN alarm_code like '%H' THEN 'H' WHEN alarm_code like '%L' THEN 'L' ELSE 'N' END) FROM s_b_alarm_inco al WHERE al.house_id = a.id AND al.alarm_code IN ('A003H','A003L','A004H','A004L') LIMIT 1),'N') AS temp_in2_alarm, "
									+ "IFNULL((SELECT (CASE WHEN alarm_code like '%H' THEN 'H' WHEN alarm_code like '%L' THEN 'L' ELSE 'N' END) FROM s_b_alarm_inco al WHERE al.house_id = a.id AND al.alarm_code IN ('A005H','A005L','A006H','A006L') LIMIT 1),'N') AS temp_in3_alarm, "
									+ "IFNULL((SELECT (CASE WHEN alarm_code like '%H' THEN 'H' WHEN alarm_code like '%L' THEN 'L' ELSE 'N' END) FROM s_b_alarm_inco al WHERE al.house_id = a.id AND al.alarm_code IN ('B001H','B001L') LIMIT 1),'N') AS temp_avg_alarm, "
									+ "(SELECT COUNT(1) FROM s_b_alarm_inco al WHERE al.house_id = a.id AND al.deal_status = '01' AND al.alarm_code = 'C0001' LIMIT 1) AS point_temp_alarm, "
									+ "(SELECT COUNT(1) FROM s_b_alarm_inco al WHERE al.house_id = a.id AND al.deal_status = '01' AND al.alarm_code = 'C0002' LIMIT 1) AS power_status_alarm,"
									+ "if(dc.power_status is not null,if(dc.power_status = '1','正常','断电'),'-') as  power_status, "
									+ "DATE_FORMAT(dc.collect_datetime,'%H时%i分') AS data_time "
									+ "FROM s_d_house a LEFT JOIN s_b_monitor_curr dc ON dc.house_id = a.id and date_add(dc.collect_datetime,INTERVAL 30 MINUTE) > now() "
									+ "WHERE a.freeze_status = 0 "
									+ "AND EXISTS(SELECT 1 FROM s_user_house_view sv "
													+ "WHERE a.id = sv.house_id AND sv.user_id = " + id_spa + " ) "
//									+ "and exists(SELECT 1 from s_b_devi_house sbd where a.id = sbd.house_id) "
									+ "ORDER BY a.id ";
			mLogger.info("========MonitoringReqController.monitoring.SQL = "+sql);
			List<HashMap<String, Object>> toutcome = tBaseQueryService
					.selectMapByAny(sql);
			JSONArray MonitorData = new JSONArray();
			if (toutcome.size() != 0) {
				for (HashMap<String, Object> outcome : toutcome) {
					JSONObject mJSONObject = new JSONObject();
					Object houseName = outcome.get("houseName");
					Object dayAge = outcome.get("dayAge");
					Object out_temp = outcome.get("out_temp");
					Object tempLeft1 = outcome.get("tempLeft1");
					Object tempLeft2 = outcome.get("tempLeft2");
					Object tempMiddle1 = outcome.get("tempMiddle1");
					Object tempMiddle2 = outcome.get("tempMiddle2");
					Object tempRight1 = outcome.get("tempRight1");
					Object tempRight2 = outcome.get("tempRight2");
					Object tar_temp = outcome.get("tar_temp");
					Object avg_temp = outcome.get("avg_temp");
					Object high_alarm_temp = outcome.get("high_alarm_temp");
					Object low_alarm_temp = outcome.get("low_alarm_temp");
					Object point_temp = outcome.get("point_temp");
					Object humi = outcome.get("humi");
					Object temp_in1_alarm = outcome.get("temp_in1_alarm");
					Object temp_in2_alarm = outcome.get("temp_in2_alarm");
					Object temp_in3_alarm = outcome.get("temp_in3_alarm");
					Object temp_avg_alarm = outcome.get("temp_avg_alarm");
					Object point_temp_alarm = outcome.get("point_temp_alarm");
					Object power_status = outcome.get("power_status");
					Object power_status_alarm = outcome.get("power_status_alarm");
					Object data_time = outcome.get("data_time");

					mJSONObject.put("tempLeft1", tempLeft1);
					mJSONObject.put("tempLeft2", tempLeft2);
					mJSONObject.put("tempMiddle1", tempMiddle1);
					mJSONObject.put("tempMiddle2", tempMiddle2);
					mJSONObject.put("tempRight1", tempRight1);
					mJSONObject.put("tempRight2", tempRight2);
					mJSONObject.put("houseName", houseName);
					mJSONObject.put("dayAge", dayAge);
					mJSONObject.put("out_temp", out_temp);
					mJSONObject.put("tar_temp", tar_temp);
					mJSONObject.put("avg_temp", avg_temp);
					mJSONObject.put("H_temp", high_alarm_temp);
					mJSONObject.put("L_temp", low_alarm_temp);
					mJSONObject.put("point_temp", point_temp);
					mJSONObject.put("humi", humi);
					mJSONObject.put("temp_in1_alarm", temp_in1_alarm);
					mJSONObject.put("temp_in2_alarm", temp_in2_alarm);
					mJSONObject.put("temp_in3_alarm", temp_in3_alarm);
					mJSONObject.put("house_id", outcome.get("id"));
					mJSONObject.put("temp_avg_alarm", temp_avg_alarm);
					mJSONObject.put("point_temp_alarm", point_temp_alarm);
					mJSONObject.put("power_status", power_status);
					mJSONObject.put("power_status_alarm", power_status_alarm);
					mJSONObject.put("data_time", data_time);
					MonitorData.put(mJSONObject);
				}
				resJson.put("MonitorData", MonitorData);
			}
			resJson.put("Result", "Success");
			/** 业务处理结束 **/
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
		mLogger.info("=====Now end executing MonitoringReqController.monitoring");
	}
	@RequestMapping("AlarmDealDelay_v2")
	public void AlarmDealDelay_v2(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=======Now start executing MonitoringReqController.AlarmDealDelay_v2");
		String dealRes = null;
		boolean outcome = true;
		JSONObject resJson = new JSONObject();
		String paraStr = PubFun.getRequestPara(request);
		mLogger.info("updateFarm.para=" + paraStr);
		try {
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(SDUserOperationService.MENU_WARNTREATMENT, SDUserOperationService.OPERATION_UPDATE, jsonobject.optInt("id_spa"));
			int id_spa = jsonobject.optInt("id_spa");
			if (id_spa == 0) {
				resJson.put("ErrorMsg", "Fail");
			}else{
				JSONObject params = jsonobject.getJSONObject("params");
				JSONArray CurAlarmData = params.getJSONArray("CurAlarmData");
				if( CurAlarmData.length() > 0){
					HashMap<String, Object> mParas = new HashMap<String, Object>();
					mParas.put("modifyUserId", id_spa);
					mParas.put("CurAlarmData", CurAlarmData);
					tMonitoringReqManager.updateSave_v2(mParas);
				}
				resJson.put("Result", "Success");
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
		mLogger.info("=====Now end executing MonitoringReqController.AlarmDealDelay_v2");
	}
	@RequestMapping("AlarmDealQuery_v2")
	public void AlarmDealQuery_v2(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=======Now start executing MonitoringReqController.AlarmDealQuery_v2");
		String dealRes = null;
		JSONObject resJson = new JSONObject();
		String paraStr = PubFun.getRequestPara(request);
		mLogger.info("updateFarm.para=" + paraStr);
		try {
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(SDUserOperationService.MENU_WARNTREATMENT, SDUserOperationService.OPERATION_SELECT, jsonobject.optInt("id_spa"));

			JSONObject params = jsonobject.getJSONObject("params");
			int FarmId = params.getInt("FarmId");
			JSONArray CurAlarmData = new JSONArray();
			String SQL = "SELECT  s_f_getHouseName(house_id) AS HouseName, house_id  AS  house_id, s_f_getDayAgeByHouseBreedId(ad.house_breed_id,ad.alarm_time) AS dayAge,"
					+ " DATE_FORMAT(ad.alarm_time,'%Y-%m-%d') AS adate, DATE_FORMAT(ad.alarm_time,'%H:%i:%S') AS atime, ad.id AS alarmid, ad.alarm_code, ad.alarm_name, ad.actual_value, ad.set_value AS target_value, ad.deal_status "
					+ " FROM s_b_alarm_inco ad WHERE  ad.farm_id = "+ FarmId+" order by house_id";
			mLogger.info("=========MonitoringReqController.AlarmDealQuery_v2.sql"+SQL);
			List<HashMap<String, Object>> toutcome = tBaseQueryService
					.selectMapByAny(SQL);
			if (toutcome.size() != 0) {
				resJson.put("FarmId", FarmId);
				JSONArray alarmMessage = new JSONArray();
				int i = 0;
				for (HashMap<String, Object> hashMap : toutcome) {
					boolean dd = true;
					JSONObject tJSONObject = new JSONObject();
					Object HouseId = hashMap.get("house_id");
					Object HouseName = hashMap.get("HouseName");
					Object aDayAge = hashMap.get("dayAge");
					Object aDate = hashMap.get("adate");
					Object aTime = hashMap.get("atime");
					Object alarmID = hashMap.get("alarmid");
					Object alarmCode = hashMap.get("alarm_code");
					Object alarmName = hashMap.get("alarm_name");
					Object realValue = hashMap.get("actual_value");
					Object targetValue = hashMap.get("target_value");
					Object process_status = hashMap.get("deal_status");
					try {
						dd = HouseId.equals(toutcome.get(i+1).get("house_id"));
					} catch (Exception e) {
						dd = false;
					}
					i++;
					tJSONObject.accumulate("aDayAge", aDayAge);
					tJSONObject.accumulate("aDate", aDate);
					tJSONObject.accumulate("aTime", aTime);
					tJSONObject.accumulate("alarmID", alarmID);
					tJSONObject.accumulate("alarmCode", alarmCode);
					tJSONObject.accumulate("alarmName", alarmName);
					tJSONObject.accumulate("realValue", realValue);
					tJSONObject.accumulate("targetValue", targetValue);
					tJSONObject.accumulate("process_status", process_status);
					CurAlarmData.put(tJSONObject);
					if(!dd){
						JSONObject yJSONObject = new JSONObject();
						yJSONObject.put("HouseId", HouseId);
						yJSONObject.put("HouseName", HouseName);
						yJSONObject.put("CurAlarmData", CurAlarmData);
						CurAlarmData = new JSONArray();
						alarmMessage.put(yJSONObject);
					}
				}
				resJson.put("Result", "Success");
				resJson.put("alarmMessage", alarmMessage);
			} else {
				resJson.put("Result", "Fail");
			}
			/** 业务处理结束 **/
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
		mLogger.info("=====Now end executing MonitoringReqController.AlarmDealQuery_v2");
	}
	/*@RequestMapping("AlarmDealQuery")
	public void AlarmDealQuery(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=======Now start executing MonitoringReqController.AlarmDealQuery");
		String dealRes = null;
		JSONObject resJson = new JSONObject();
		String paraStr = PubFun.getRequestPara(request);
		mLogger.info("updateFarm.para=" + paraStr);
		try {
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			*//** 业务处理开始，查询、增加、修改、或删除 **//*
			operationService.insert(SDUserOperationService.MENU_WARNTREATMENT, SDUserOperationService.OPERATION_SELECT, jsonobject.optInt("id_spa"));

			JSONObject params = jsonobject.getJSONObject("params");
			int HouseId = params.getInt("HouseId");
			JSONArray CurAlarmData = new JSONArray();
			String SQL = "SELECT s_f_getDayAgeByHouseBreedId(ad.house_breed_id,ad.alarm_time) AS dayAge, DATE_FORMAT(ad.alarm_time,'%Y-%m-%d') AS adate, DATE_FORMAT(ad.alarm_time,'%H:%i:%S') AS atime, ad.id AS alarmid, ad.alarm_code, ad.alarm_name, ad.actual_value, ad.set_value AS target_value, ad.deal_status  FROM s_b_alarm_inco ad WHERE  ad.house_id = "
					+ HouseId;
			mLogger.info("sql"+SQL);
			List<HashMap<String, Object>> toutcome = tBaseQueryService
					.selectMapByAny(SQL);
			if (toutcome.size() != 0) {
				resJson.put("HouseId", HouseId);
				resJson.put("Result", "Success");
				for (HashMap<String, Object> hashMap : toutcome) {
					JSONObject tJSONObject = new JSONObject();
					Object aDayAge = hashMap.get("dayAge");
					Object aDate = hashMap.get("adate");
					Object aTime = hashMap.get("atime");
					Object alarmID = hashMap.get("alarmid");
					Object alarmCode = hashMap.get("alarm_code");
					Object alarmName = hashMap.get("alarm_name");
					Object realValue = hashMap.get("actual_value");
					Object targetValue = hashMap.get("target_value");
					Object process_status = hashMap.get("deal_status");
					tJSONObject.accumulate("aDayAge", aDayAge);
					tJSONObject.accumulate("aDate", aDate);
					tJSONObject.accumulate("aTime", aTime);
					tJSONObject.accumulate("alarmID", alarmID);
					tJSONObject.accumulate("alarmCode", alarmCode);
					tJSONObject.accumulate("alarmName", alarmName);
					tJSONObject.accumulate("realValue", realValue);
					tJSONObject.accumulate("targetValue", targetValue);
					tJSONObject.accumulate("process_status", process_status);
					CurAlarmData.put(tJSONObject);
				}
				resJson.put("CurAlarmData", CurAlarmData);
			} else {
				resJson.put("Result", "Fail");
			}
			*//** 业务处理结束 **//*
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
		mLogger.info("=====Now end executing MonitoringReqController.AlarmDealQuery");
	}

	@RequestMapping("AlarmDealDelay")
	public void AlarmDealDelay(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=======Now start executing MonitoringReqController.AlarmDealDelay");
		JSONObject JSONObject = new JSONObject();
		String dealRes = null;
		boolean outcome = true;
		JSONObject resJson = new JSONObject();
		String paraStr = PubFun.getRequestPara(request);
		mLogger.info("updateFarm.para=" + paraStr);
		try {
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			*//** 业务处理开始，查询、增加、修改、或删除 **//*
			operationService.insert(SDUserOperationService.MENU_WARNTREATMENT, SDUserOperationService.OPERATION_UPDATE, jsonobject.optInt("id_spa"));

			int id_spa = jsonobject.optInt("id_spa");
			if (id_spa == 0) {
				resJson.put("ErrorMsg", "Fail");
			} else {
				JSONObject params = jsonobject.getJSONObject("params");
				int houseId = params.optInt("HouseId");
				int delayTime = 0;
				if(houseId != 0){
					JSONArray CurAlarmData = params.getJSONArray("CurAlarmData");
					if( CurAlarmData.length() > 0){
						delayTime = CurAlarmData.getJSONObject(0).optInt("delayTime");
					}else{
						outcome = false;
					}
				}else{
					outcome = false;
				}
				if (outcome) {
					HashMap<String, Object> mParas = new HashMap<String, Object>();
					mParas.put("modifyUserId", id_spa);
					mParas.put("delayTime", delayTime);
					mParas.put("HouseId", houseId);
					tMonitoringReqManager.updateSave(mParas);
					resJson.put("Result", "Success");
				} else {
					resJson.put("Result", "Success");
					dealRes = Constants.RESULT_SUCCESS;
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
		mLogger.info("=====Now end executing MonitoringReqController.AlarmDealDelay");
	}
*/
	@RequestMapping("needAlarm")
	public void needAlarm(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=======Now start executing MonitoringReqController.needAlarm");
		String dealRes = null;
		JSONObject resJson = new JSONObject();
		String paraStr = PubFun.getRequestPara(request);
		mLogger.info("needAlarm.para=" + paraStr);
		try {
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			int id_spa = jsonobject.optInt("id_spa");
			if (id_spa == 0) {
				resJson.put("AlarmStatus", "N");
			} else {
				JSONObject params = jsonobject.getJSONObject("params");
				
				String uuid = params.optString("uuid");
				String model = params.optString("model");
				String version = params.optString("version");
				String platform = params.optString("platform");
				
				mLogger.info("userId=" + id_spa);
				mLogger.info("uuid=" + uuid);
				mLogger.info("model=" + model);
				mLogger.info("version=" + version);
				mLogger.info("platform=" + platform);
				
				SLAlarmRequest ttSLAlarmRequest = new SLAlarmRequest();
				ttSLAlarmRequest.setUserid(id_spa);
				ttSLAlarmRequest.setModel(model);
				ttSLAlarmRequest.setUuid(uuid);
				ttSLAlarmRequest.setVersion(version);
				ttSLAlarmRequest.setPlatform(platform);
				
				String tSQL = "SELECT case when count(1) > 0 then 'Y' else 'N' END FROM s_b_alarm_inco a where 1=1 " +
							" and a.deal_status = '01' " +
							" and exists(SELECT 1 from s_user_house_view b where b.user_id = " +id_spa +" and a.house_id = b.house_id)" ;
				mLogger.info("sql"+tSQL);
				String res = tBaseQueryService.selectStringByAny(tSQL);
				
				ttSLAlarmRequest.setCdate(new Date());
				ttSLAlarmRequest.setResult(res+"_new");
				
				if(model != null && !model.equals("") && !model.equals("null")){
					tAppDeviceService.insertLog(ttSLAlarmRequest);
				}
				
				resJson.put("AlarmStatus", res);
			}
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("AlarmStatus", "N");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL;
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now end executing MonitoringReqController.needAlarm");
	}
	
	@RequestMapping("needAlarm2")
	public void needAlarm2(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {
		String tRes = "";
		mLogger.info("=====Now start executing MonitoringReqController.needAlarm2");
		try {
			int userId = Integer.parseInt(request.getParameter("userID"));
			String uuid = request.getParameter("uuid");
			String model = request.getParameter("model");
			String version = request.getParameter("version");
			String platform = request.getParameter("platform");
			
			mLogger.info("userId=" + userId);
			mLogger.info("uuid=" + uuid);
			mLogger.info("model=" + model);
			mLogger.info("version=" + version);
			mLogger.info("platform=" + platform);
			
			SLAlarmRequest ttSLAlarmRequest = new SLAlarmRequest();
			ttSLAlarmRequest.setUserid(userId);
			ttSLAlarmRequest.setModel(model);
			ttSLAlarmRequest.setUuid(uuid);
			ttSLAlarmRequest.setVersion(version);
			ttSLAlarmRequest.setPlatform(platform);
			
			if (userId == 0) {
				tRes = "N";
			} else {
				String tSQL = "SELECT case when count(1) > 0 then 'Y' else 'N' END FROM s_b_alarm_inco a where 1=1 " +
							" and a.deal_status = '01' " +
							" and exists(SELECT 1 from s_user_house_view b where b.user_id = " +userId +" and a.house_id = b.house_id)" ;
				mLogger.info("SQL=" + tSQL);
				tRes = tBaseQueryService.selectStringByAny(tSQL);
			}
//			tRes = "N";
			ttSLAlarmRequest.setCdate(new Date());
			ttSLAlarmRequest.setResult(tRes+"_old");
			if(model != null && !model.equals("") && !model.equals("null")){
				tAppDeviceService.insertLog(ttSLAlarmRequest);
			}
			/** 业务处理结束 **/
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		try {
			mLogger.info("Result:" + tRes);
			response.getWriter().write(tRes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mLogger.info("=====Now end executing MonitoringReqController.needAlarm2");
	}
}
