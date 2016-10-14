/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
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

import com.mtc.app.biz.AlarmReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.MySQLSPService;
import com.mtc.app.service.SBHouseAlarmService;
import com.mtc.app.service.SBHouseProbeService;
import com.mtc.app.service.SBLayerHouseAlarmService;
import com.mtc.app.service.SBTempSettingService;
import com.mtc.app.service.SBTempSettingSubService;
import com.mtc.app.service.SDHouseService;
import com.mtc.app.service.SDUserFarmService;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBHouseProbe;
import com.mtc.entity.app.SBLayerHouseAlarm;
import com.mtc.entity.app.SBUserFarm;
import com.mtc.entity.app.SDHouse;


/**
 * @ClassName: AlarmReqController
 * @Description: 
 * @Date 2015-11-30 上午11:01:19
 * @Author Shao Yao Yu
 * 
 */

@Controller
@RequestMapping("/sys/alarm")
public class AlarmReqController {

	private static Logger mLogger = Logger.getLogger(AlarmReqController.class);	
	@Autowired
	private SBHouseProbeService  tSBHouseProbeService;
	@Autowired
	private BaseQueryService tBaseQueryService;
	@Autowired
	private MySQLSPService tMySQLSPService;
	@Autowired
	private AlarmReqManager tAlarmReqManager;
	@Autowired
	private SDHouseService mSDHouseService;
	@Autowired
	private SDUserFarmService mSDUserFarmService;
	@Autowired
	private SBTempSettingSubService  tSBTempSettingSubService;
	@Autowired
	private SBHouseAlarmService  tSBHouseAlarmService;
	@Autowired
	private SBTempSettingService  tSBTempSettingService;
	/**change by seymour 20160418*/
	@Autowired
	private SBLayerHouseAlarmService lSBLayerHouseAlarmService;
	
	@Autowired
	private SDUserOperationService operationService;

	@RequestMapping("/saveSettingData")
	public void saveSettingData(HttpServletRequest request,HttpServletResponse response){
		mLogger.info("=====New start executing AlarmReqController.saveSettingData");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr="+ paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/

			
			JSONObject prarObj = jsonObject.getJSONObject("params");
			if(PubFun.isNull(prarObj.getInt("FarmId"))||
					PubFun.isNull(prarObj.getInt("HouseId"))||
					PubFun.isNull(jsonObject.getInt("id_spa"))){
				resJson.put("ErrorMsg","插入失败，数据格式错误。");
			}else {
				int FarmId = prarObj.getInt("FarmId");
				int HouseId = prarObj.getInt("HouseId");
				int modifyPerson = jsonObject.getInt("id_spa");
				
				SBLayerHouseAlarm llSBLayerHouseAlarm = lSBLayerHouseAlarmService.selectByPrimaryKey(FarmId, HouseId);

				if (llSBLayerHouseAlarm == null) {
					operationService.insert(SDUserOperationService.MENU_ENVIMONITOR_SETTING, SDUserOperationService.OPERATION_ADD, jsonObject.optInt("id_spa"));
				} else {
					operationService.insert(SDUserOperationService.MENU_ENVIMONITOR_SETTING, SDUserOperationService.OPERATION_UPDATE, jsonObject.optInt("id_spa"));
				}
				
				SDHouse tSDHouse = mSDHouseService.selectHousesIdByFarmId(FarmId, HouseId);
				
				SBUserFarm tSBUserFarm = mSDUserFarmService.selectByUserIdFarmId(modifyPerson, FarmId);
				 if(PubFun.isNull(tSDHouse)||PubFun.isNull(tSBUserFarm))
					{
						resJson.put("ErrorMsg","数据错误。");
					}else{
						JSONObject SBHouseAlarm = prarObj.getJSONObject("AlarmSetting");
						String    SalarmDelay =  SBHouseAlarm.getString("Delay");
						String   tempCpsation =  SBHouseAlarm.getString("tempCpsation");
						String   StempCpsationVal =  SBHouseAlarm.getString("tempCpsationVal");
						String     alarmProbe =  SBHouseAlarm.getString("alarmProbe");
						JSONObject     effAlarmProbe =  SBHouseAlarm.getJSONObject("effAlarmProbe");
						String    SpointAlarm =  SBHouseAlarm.getString("pointAlarm");
					 	String tarTemp = SBHouseAlarm.getString("tarTemp");
						String lowTemp = SBHouseAlarm.getString("minTemp");
					 	String heiTemp = SBHouseAlarm.getString("maxTemp");


						int alarmDelay = Integer.parseInt(SalarmDelay);
						BigDecimal pointAlarm = new BigDecimal(SpointAlarm);
						BigDecimal tempCpsationVal = new BigDecimal(StempCpsationVal);
					 	BigDecimal targetTemp = new BigDecimal(tarTemp);
					 	BigDecimal minTemp = new BigDecimal(lowTemp);
					 	BigDecimal maxTemp = new BigDecimal(heiTemp);

			 			Date newdate = new Date();
						SBLayerHouseAlarm lLayerHouseAlarm= new SBLayerHouseAlarm();
						lLayerHouseAlarm.setFarmId(FarmId);
						lLayerHouseAlarm.setHouseId(HouseId);
						lLayerHouseAlarm.setModifyTime(newdate);
						lLayerHouseAlarm.setModifyPerson(modifyPerson);
						lLayerHouseAlarm.setModifyDate(newdate);
						lLayerHouseAlarm.setCreateDate(newdate);
						lLayerHouseAlarm.setCreatePerson(modifyPerson);
						lLayerHouseAlarm.setCreateTime(newdate);
						lLayerHouseAlarm.setAlarmDelay(alarmDelay);
						lLayerHouseAlarm.setTempCpsation(tempCpsation);
						lLayerHouseAlarm.setAlarmProbe(alarmProbe);
						lLayerHouseAlarm.setPointAlarm(pointAlarm);
						lLayerHouseAlarm.setTempCordon(tempCpsationVal);
					 	lLayerHouseAlarm.setTargetTemp(targetTemp);
					 	lLayerHouseAlarm.setMinTemp(minTemp);
					 	lLayerHouseAlarm.setMaxTemp(maxTemp);

						SBHouseProbe tSBHouseProbe = new SBHouseProbe();
						tSBHouseProbe.setCreateDate(newdate);
						tSBHouseProbe.setCreatePerson(modifyPerson);
						tSBHouseProbe.setCreateTime(newdate);
						tSBHouseProbe.setFarmId(FarmId);
						tSBHouseProbe.setHouseId(HouseId);
						tSBHouseProbe.setModifyDate(newdate);
						tSBHouseProbe.setModifyPerson(modifyPerson);
						tSBHouseProbe.setModifyTime(newdate);

						HashMap<String,Object> mParas = new HashMap<String,Object>();
						mParas.put("SBLayerHouseAlarm", lLayerHouseAlarm);
						mParas.put("effAlarmProbe", effAlarmProbe);
						mParas.put("SBHouseProbe", tSBHouseProbe);
					    tAlarmReqManager.dealSave(mParas);
					    resJson.put("FarmId",FarmId);
					    resJson.put("houseId",HouseId);
					    resJson.put("Result","Success");
					 	resJson.put("ResultFlag","N");
					}
			}
			dealRes = Constants.RESULT_SUCCESS ;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL ;
		}
		DealSuccOrFail.dealApp(request,response,dealRes,resJson);
		mLogger.info("=====Now end executing AlarmReqController.saveSettingData");
	}
	@RequestMapping("/saveSettingBatch")
	public void saveSettingBatch(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====New start executing AlarmReqController.saveSettingBatch");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(SDUserOperationService.MENU_ENVIMONITOR_SETTING, SDUserOperationService.OPERATION_UPDATE, jsonObject.optInt("id_spa"));

			JSONObject prarObj = jsonObject.getJSONObject("params");
			if (PubFun.isNull(prarObj.getInt("FarmId")) ||
//					PubFun.isNull(prarObj.getInt("HouseId")) ||
					PubFun.isNull(jsonObject.getInt("id_spa"))) {
				resJson.put("ErrorMsg", "插入失败，数据格式错误。");
			} else {
				int FarmId = prarObj.getInt("FarmId");
				Date newdate = new Date();
				HashMap<String, Object> mParas = new HashMap<String, Object>();
//				int HouseId = prarObj.getInt("HouseId");
				List<SDHouse> houses = mSDHouseService.selectHousesByFarmId(FarmId);
				for (SDHouse house : houses) {
					int HouseId = house.getId();
					int modifyPerson = jsonObject.getInt("id_spa");
					SDHouse tSDHouse = mSDHouseService.selectHousesIdByFarmId(FarmId, HouseId);

					SBUserFarm tSBUserFarm = mSDUserFarmService.selectByUserIdFarmId(modifyPerson, FarmId);
					if (PubFun.isNull(tSDHouse) || PubFun.isNull(tSBUserFarm)) {
						resJson.put("ErrorMsg", "数据错误。");
					} else {
						JSONObject SBHouseAlarm = prarObj.getJSONObject("AlarmSetting");
						String    SalarmDelay =  SBHouseAlarm.getString("Delay");
						String   tempCpsation =  SBHouseAlarm.getString("tempCpsation");
						String   StempCpsationVal =  SBHouseAlarm.getString("tempCpsationVal");
						String     alarmProbe =  SBHouseAlarm.getString("alarmProbe");
						JSONObject     effAlarmProbe =  SBHouseAlarm.getJSONObject("effAlarmProbe");
						String    SpointAlarm =  SBHouseAlarm.getString("pointAlarm");
					 	String tarTemp = SBHouseAlarm.getString("tarTemp");
						String lowTemp = SBHouseAlarm.getString("minTemp");
					 	String heiTemp = SBHouseAlarm.getString("maxTemp");

						int alarmDelay = Integer.parseInt(SalarmDelay);
						BigDecimal pointAlarm = new BigDecimal(SpointAlarm);
						BigDecimal tempCpsationVal = new BigDecimal(StempCpsationVal);

						SBLayerHouseAlarm tSBLayerHouseAlarm = new SBLayerHouseAlarm();
						tSBLayerHouseAlarm.setFarmId(FarmId);
						tSBLayerHouseAlarm.setHouseId(HouseId);
						tSBLayerHouseAlarm.setModifyTime(newdate);
						tSBLayerHouseAlarm.setModifyPerson(modifyPerson);
						tSBLayerHouseAlarm.setModifyDate(newdate);
						tSBLayerHouseAlarm.setCreateDate(newdate);
						tSBLayerHouseAlarm.setCreatePerson(modifyPerson);
						tSBLayerHouseAlarm.setCreateTime(newdate);
						tSBLayerHouseAlarm.setAlarmDelay(alarmDelay);
						tSBLayerHouseAlarm.setTempCpsation(tempCpsation);
						tSBLayerHouseAlarm.setAlarmProbe(alarmProbe);
						tSBLayerHouseAlarm.setPointAlarm(pointAlarm);
						tSBLayerHouseAlarm.setTempCordon(tempCpsationVal);
						tSBLayerHouseAlarm.setTargetTemp(new BigDecimal(tarTemp));
					 	tSBLayerHouseAlarm.setMinTemp(new BigDecimal(lowTemp));
					 	tSBLayerHouseAlarm.setMaxTemp(new BigDecimal(heiTemp));

						SBHouseProbe tSBHouseProbe = new SBHouseProbe();
						tSBHouseProbe.setCreateDate(newdate);
						tSBHouseProbe.setCreatePerson(modifyPerson);
						tSBHouseProbe.setCreateTime(newdate);
						tSBHouseProbe.setFarmId(FarmId);
						tSBHouseProbe.setHouseId(HouseId);
						tSBHouseProbe.setModifyDate(newdate);
						tSBHouseProbe.setModifyPerson(modifyPerson);
						tSBHouseProbe.setModifyTime(newdate);

						mParas.put("SBLayerHouseAlarm", tSBLayerHouseAlarm);
						mParas.put("effAlarmProbe", effAlarmProbe);
						mParas.put("SBHouseProbe", tSBHouseProbe);
						tAlarmReqManager.dealSave(mParas);
					}
				}
				resJson.put("FarmId", FarmId);
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
		DealSuccOrFail.dealApp(request,response,dealRes,resJson);
		mLogger.info("=====Now end executing AlarmReqController.saveSettingBatch");
	}
	@RequestMapping("/querySettingData")
	public void querySettingData(HttpServletRequest request,HttpServletResponse response){
		mLogger.info("=====New start executing AlarmReqController.querySettingData");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr="+ paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(SDUserOperationService.MENU_ENVIMONITOR_SETTING, SDUserOperationService.OPERATION_SELECT, jsonObject.optInt("id_spa"));

			JSONObject prarObj = jsonObject.getJSONObject("params");
			int FarmId = prarObj.getInt("FarmId");
			int HouseId = prarObj.getInt("HouseId");

			 SBLayerHouseAlarm lSBLayerHouseAlarm= lSBLayerHouseAlarmService.selectByPrimaryKey(FarmId, HouseId);
			if(lSBLayerHouseAlarm==null){
				 lSBLayerHouseAlarm= lSBLayerHouseAlarmService.selectByPrimaryKey(0, 0);
				 	Integer Delay = lSBLayerHouseAlarm.getAlarmDelay();
				    String tempCpsation=lSBLayerHouseAlarm.getTempCpsation();
				    BigDecimal tempCpsationVal = lSBLayerHouseAlarm.getTempCordon();
				    String alarmProbe = lSBLayerHouseAlarm.getAlarmProbe();
				    BigDecimal pointAlarm  = lSBLayerHouseAlarm.getPointAlarm();
					BigDecimal targetTemp = lSBLayerHouseAlarm.getTargetTemp();
					BigDecimal minTemp = lSBLayerHouseAlarm.getMinTemp();
					BigDecimal maxTemp = lSBLayerHouseAlarm.getMaxTemp();


				    JSONObject effAlarmProbe = new JSONObject();
				    JSONObject AlarmSetting = new JSONObject();
					effAlarmProbe.put("tempLeft1", "false");
					effAlarmProbe.put("tempLeft2", "false");
					effAlarmProbe.put("tempMiddle1", "false");
					effAlarmProbe.put("tempMiddle2", "false");
					effAlarmProbe.put("tempRight1", "false");
					effAlarmProbe.put("tempRight2", "false");
				    AlarmSetting.put("effAlarmProbe", effAlarmProbe);
				    
				    AlarmSetting.put("Delay", Delay);
				    AlarmSetting.put("tempCpsation", tempCpsation);
				    AlarmSetting.put("tempCpsationVal", tempCpsationVal);
				    AlarmSetting.put("alarmProbe", alarmProbe);
				    AlarmSetting.put("pointAlarm", pointAlarm);
					/**目标温度、低温报警、高温报警设定*/
					AlarmSetting.put("tarTemp", targetTemp);
					AlarmSetting.put("minTemp", minTemp);
					AlarmSetting.put("maxTemp",maxTemp);
					/**目标温度、低温报警、高温报警设定*/
				resJson.put("Result", "Success");
				resJson.put("ResultFlag","N");
				resJson.put("FarmId",0);
				resJson.put("HouseId", 0);
				resJson.put("AlarmSetting", AlarmSetting);
				dealRes = Constants.RESULT_SUCCESS ;
			}else{
				int Delay = lSBLayerHouseAlarm.getAlarmDelay();
			    String tempCpsation=lSBLayerHouseAlarm.getTempCpsation();
			    BigDecimal tempCpsationVal = lSBLayerHouseAlarm.getTempCordon();
			    String alarmProbe = lSBLayerHouseAlarm.getAlarmProbe();
			    BigDecimal   pointAlarm  = lSBLayerHouseAlarm.getPointAlarm();
				BigDecimal targetTemp = lSBLayerHouseAlarm.getTargetTemp();
				BigDecimal minTemp = lSBLayerHouseAlarm.getMinTemp();
				BigDecimal maxTemp = lSBLayerHouseAlarm.getMaxTemp();
			    JSONObject AlarmSetting = new JSONObject();
			    JSONObject effAlarmProbe = new JSONObject();
			    JSONObject effAlarmProbe1 = new JSONObject();
			    List<SBHouseProbe> lSBHouseProbe=tSBHouseProbeService.selectByFarmIdHouseId(FarmId, HouseId);
			    for (SBHouseProbe sbHouseProbe : lSBHouseProbe) {
			    	effAlarmProbe1.put(sbHouseProbe.getProbeCode(), sbHouseProbe.getProbeCode());
				}
		         String          tempLeft1 = (String) effAlarmProbe1.opt("tempLeft1");
		         String          tempLeft2 = (String) effAlarmProbe1.opt("tempLeft2");
		         String          tempMiddle1 = (String) effAlarmProbe1.opt("tempMiddle1");
		         String          tempMiddle2 = (String) effAlarmProbe1.opt("tempMiddle2");
		         String          tempRight1 = (String) effAlarmProbe1.opt("tempRight1");
		         String          tempRight2 = (String) effAlarmProbe1.opt("tempRight2");
				if(tempLeft1!=null){
					tempLeft1 = "true";
				}else{
					tempLeft1 = "false";
				}
				if(tempLeft2!=null){
					 tempLeft2 = "true";
				}else{
					tempLeft2 = "false";
				} 
				if(tempMiddle1!=null){
					 tempMiddle1 = "true";
				}else{
					 tempMiddle1 = "false";
				} 
				if(tempMiddle2!=null){
					 tempMiddle2 = "true";
				}else{
					 tempMiddle2 = "false";
				}		         
				if(tempRight1!=null){
					 tempRight1 = "true";
				}else{
					 tempRight1 = "false";
				}		         
				if(tempRight2!=null){
					 tempRight2 = "true";
				}else{
					 tempRight2 = "false";
				}		         
				effAlarmProbe.put("tempLeft1", tempLeft1);
				effAlarmProbe.put("tempLeft2", tempLeft2);
				effAlarmProbe.put("tempMiddle1", tempMiddle1);
				effAlarmProbe.put("tempMiddle2", tempMiddle2);
				effAlarmProbe.put("tempRight1", tempRight1);
				effAlarmProbe.put("tempRight2", tempRight2);
			    AlarmSetting.put("effAlarmProbe", effAlarmProbe);
			    AlarmSetting.put("Delay", Delay);
			    AlarmSetting.put("tempCpsation", tempCpsation);
			    AlarmSetting.put("tempCpsationVal", tempCpsationVal);
			    AlarmSetting.put("alarmProbe", alarmProbe);
			    AlarmSetting.put("pointAlarm", pointAlarm);
				/**目标温度、低温报警、高温报警设定*/
				AlarmSetting.put("tarTemp", targetTemp);
				AlarmSetting.put("minTemp", minTemp);
				AlarmSetting.put("maxTemp",maxTemp);
				/**目标温度、低温报警、高温报警设定*/
			resJson.put("Result", "Success");
			resJson.put("ResultFlag","Y");
			resJson.put("FarmId",FarmId);
			resJson.put("HouseId", HouseId);
			resJson.put("AlarmSetting", AlarmSetting);
		    dealRes = Constants.RESULT_SUCCESS ;
		    }
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace  ();
			}
			dealRes = Constants.RESULT_FAIL ;
		}
		DealSuccOrFail.dealApp(request,response,dealRes,resJson);
		mLogger.info("=====Now end executing AlarmReqController.querySettingData");
	}
	@RequestMapping("/queryAlarmData")
	public void queryAlarmData(HttpServletRequest request,HttpServletResponse response){
		mLogger.info("=====New start executing AlarmReqController.queryAlarmData");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr="+ paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(SDUserOperationService.MENU_WARNSTATISTICS, SDUserOperationService.OPERATION_SELECT, jsonObject.optInt("id_spa"));

			JSONObject prarObj = jsonObject.getJSONObject("params");
			int id_spa = jsonObject.getInt("id_spa");
			int FarmId = prarObj.getInt("FarmId");
			JSONArray tJSONArray = new JSONArray();
			String SQL = "SELECT a.house_id,s_f_getHouseName(a.house_id) AS housename,"
					+ "IFNULL(s_f_getDayAgeByHouseId(a.house_id,curdate()), '-') as dayAge,"
					+ "SUM(CASE WHEN ad.alarm_code = 'A001H' or ad.alarm_code = 'A002H' THEN 1 ELSE 0 END) AS temp_in1_alarm_H, "
					+ "SUM(CASE WHEN ad.alarm_code = 'A001L' or ad.alarm_code = 'A002L' THEN 1 ELSE 0 END) AS temp_in1_alarm_L, "
					+ "SUM(CASE WHEN ad.alarm_code = 'A003H' or ad.alarm_code = 'A004H' THEN 1 ELSE 0 END) AS temp_in2_alarm_H, "
					+ "SUM(CASE WHEN ad.alarm_code = 'A003L' or ad.alarm_code = 'A004L' THEN 1 ELSE 0 END) AS temp_in2_alarm_L, "
					+ "SUM(CASE WHEN ad.alarm_code = 'A005H' or ad.alarm_code = 'A006H' THEN 1 ELSE 0 END) AS temp_in3_alarm_H, "
					+ "SUM(CASE WHEN ad.alarm_code = 'A005L' or ad.alarm_code = 'A006L' THEN 1 ELSE 0 END) AS temp_in3_alarm_L, "
					+ "SUM(CASE WHEN ad.alarm_code = 'B001H' THEN 1 ELSE 0 END) AS temp_avg_alarm_H, "
					+ "SUM(CASE WHEN ad.alarm_code = 'B001L' THEN 1 ELSE 0 END) AS temp_avg_alarm_L, "
					+ "SUM(CASE WHEN ad.alarm_code = 'C0001' THEN 1 ELSE 0 END) AS point_temp_alarm, "
					+ "SUM(CASE WHEN ad.alarm_code = 'C0002' THEN 1 ELSE 0 END) AS power_status "
					+ "FROM s_user_house_view a  LEFT JOIN "
						+ "(SELECT sbi.* FROM s_b_alarm_inco sbi WHERE s_f_getHouseBreedId(sbi.house_id) = sbi.house_breed_id  "
							+ "UNION ALL SELECT sbd.* FROM s_b_alarm_done sbd WHERE s_f_getHouseBreedId(sbd.house_id) = sbd.house_breed_id ) ad "
					+ "ON a.house_id = ad.house_id WHERE a.user_id = "+id_spa+" GROUP BY a.house_id ";
			mLogger.info("=========AlarmReqController.queryAlarmData.sql = " + SQL);
			List<HashMap<String,Object>> Loutcome = tBaseQueryService.selectMapByAny(SQL);
			if(Loutcome.size()>0){
				for (HashMap<String, Object> hashMap : Loutcome) {
					JSONObject tJSONObject = new JSONObject();
					JSONObject mJSONObject = new JSONObject();
					Object	 houseID = hashMap.get("houseid");
					tJSONObject.put( "houseID", houseID);
					Object   houseName = hashMap.get("housename");
					tJSONObject.put( "houseName", houseName);
					Object   dayAge = hashMap.get("dayAge");
					tJSONObject.put( "dayAge", dayAge);
					Object   point_temp_alarm = hashMap.get("point_temp_alarm");
					tJSONObject.put( "point_temp_alarm", point_temp_alarm);
					Object   power_status = hashMap.get("power_status");
					tJSONObject.put("power_status", power_status);
					Object   temp_in1_alarm_H =  hashMap.get("temp_in1_alarm_H");
					Object   temp_in1_alarm_L =  hashMap.get("temp_in1_alarm_L");
					mJSONObject.put("temp_in1_alarm_H", temp_in1_alarm_H);
					mJSONObject.put("temp_in1_alarm_L", temp_in1_alarm_L);
					tJSONObject.put( "temp_in1_alarm", mJSONObject);
					Object   temp_in2_alarm_H = hashMap.get("temp_in2_alarm_H");
					Object   temp_in2_alarm_l = hashMap.get("temp_in2_alarm_L");
					mJSONObject = new JSONObject();;
					mJSONObject.put("temp_in2_alarm_H", temp_in2_alarm_H);
					mJSONObject.put("temp_in2_alarm_l", temp_in2_alarm_l);
					tJSONObject.put( "temp_in2_alarm", mJSONObject);
					Object   temp_in3_alarm_H = hashMap.get("temp_in3_alarm_H");
					Object   temp_in3_alarm_L = hashMap.get("temp_in3_alarm_L");
					mJSONObject = new JSONObject();;
					mJSONObject.put("temp_in3_alarm_H", temp_in3_alarm_H);
					mJSONObject.put("temp_in3_alarm_L", temp_in3_alarm_L);
					tJSONObject.put( "temp_in3_alarm", mJSONObject);
					Object   temp_avg_alarm_H = hashMap.get("temp_avg_alarm_H");
					Object   temp_avg_alarm_l = hashMap.get("temp_avg_alarm_L");
					mJSONObject = new JSONObject();;
					mJSONObject.put("temp_avg_alarm_H", temp_avg_alarm_H);
					mJSONObject.put("temp_avg_alarm_l", temp_avg_alarm_l);
					tJSONObject.put( "temp_avg_alarm", mJSONObject);
					tJSONArray.put(tJSONObject);
				}
				resJson.put("Result", "Success");
				resJson.put("FarmId", FarmId);
				resJson.put("AlarmData", tJSONArray);
			}else{
				resJson.put("Result", "Fail");
			}
		    dealRes = Constants.RESULT_SUCCESS ;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL ;
		}
		DealSuccOrFail.dealApp(request,response,dealRes,resJson);
		mLogger.info("=====Now end executing AlarmReqController.queryAlarmData");
	}
	@RequestMapping("/queryAlarmLog")
	public void queryAlarmLog(HttpServletRequest request,HttpServletResponse response) {
		mLogger.info("=====New start executing AlarmReqController.queryAlarmLog");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(SDUserOperationService.MENU_WARNLOG, SDUserOperationService.OPERATION_SELECT, jsonObject.optInt("id_spa"));

			JSONObject prarObj = jsonObject.getJSONObject("params");
			int HouseId = prarObj.optInt("HouseId");
			String AlarmCategory = prarObj.getString("AlarmCategory");
			int AgeBegin = prarObj.getInt("AgeBegin");
			int AgeEnd = prarObj.getInt("AgeEnd");
			
			AgeBegin = (AgeBegin - 1) * 7 ;
			AgeEnd = AgeEnd * 7 ;
			
			/*AgeBegin = (AgeBegin == 0 || AgeBegin == 1)?-10:AgeBegin ;
			AgeEnd = (AgeEnd == 50)?80:AgeEnd ;*/

			JSONArray tJSONArray = new JSONArray();
			List<HashMap<String, Object>> Loutcome = null;
			/*String SQL = "SELECT ad.house_id AS houseid,s_f_getHouseName(ad.house_id) AS housename, "
					+ "s_f_getDayAgeByHouseBreedId(ad.house_breed_id,ad.alarm_time) AS dayAge,  "
					+ "DATE_FORMAT(ad.alarm_time,'%Y-%m-%d') AS alarmdate,  "
					+ "DATE_FORMAT(ad.alarm_time,'%H:%i:%S') AS alarmtime,  "
					+ "ad.id AS alarmid,  ad.alarm_code, ad.alarm_name, "
					+ "round(ad.actual_value,1) as actual_value, round(ad.set_value,1) AS target_value, "
					+ "ad.deal_status FROM "
						+ "( SELECT * FROM s_b_alarm_inco WHERE s_f_getHouseBreedId(house_id) = house_breed_id "
							+ "UNION ALL  SELECT *  FROM s_b_alarm_done WHERE s_f_getHouseBreedId(house_id) = house_breed_id  ) ad "
					+ "WHERE  ad.house_id = "+HouseId+" "
					+ "AND exists(SELECT 1 " +
					"FROM s_b_layer_breed_detail sbl " +
					"WHERE ad.house_breed_id = sbl.house_breed_id AND " +
					"sbl.growth_date = date_format(ad.alarm_time, '%Y-%m-%d') AND " +
					"sbl.week_age BETWEEN " + AgeBegin + " AND " + AgeEnd + ")";*/
			String SQL = "SELECT ad.house_id                                                   AS houseid," +
					" s_f_getHouseName(ad.house_id)                                 AS housename," +
					" s_f_getDayAgeByHouseBreedId(ad.house_breed_id, ad.alarm_time) AS dayAge," +
					" DATE_FORMAT(ad.alarm_time, '%Y-%m-%d')                        AS alarmdate," +
					" DATE_FORMAT(ad.alarm_time, '%H:%i:%S')                        AS alarmtime," +
					" ad.id                                                         AS alarmid," +
					" ad.alarm_code," +
					" ad.alarm_name," +
					" round(ad.actual_value, 1)                                     AS actual_value," +
					" round(ad.set_value, 1)                                        AS target_value," +
					" ad.deal_status," +
					" ad.alarm_time                                                 AS alarm_time," +
					" CASE WHEN ad.deal_delay is NULL THEN '未处理' ELSE concat('延时', ad.deal_delay, '分') END AS process_status," +
					" (select sd.user_real_name from s_d_user sd where sd.id = ad.response_person) AS deal_person," +
					" DATE_FORMAT(ad.deal_time, '%H:%i:%S')                                                  AS deal_time," +
					" CASE WHEN ad.is_remove = 'Y' THEN '是' ELSE '否' END          AS is_remove," +
					" truncate(CASE WHEN ad.is_remove = 'Y' THEN time_to_sec(timediff(ad.remove_time, ad.alarm_time)) / 60 " +
					"when ad.deal_status = '02' then deal_delay else time_to_sec(timediff(curtime(), ad.alarm_time))/60 END, 0) AS deal_delay" +
					" FROM (SELECT *" +
					" FROM s_b_alarm_inco" +
					" WHERE s_f_getHouseBreedId(house_id) = house_breed_id" +
					" UNION ALL SELECT *" +
					" FROM s_b_alarm_done" +
					" WHERE s_f_getHouseBreedId(house_id) = house_breed_id) ad" +
					" WHERE ad.house_id = " + HouseId + " AND s_f_getDayAgeByHouseBreedId(ad.house_breed_id, ad.alarm_time) BETWEEN "+AgeBegin+" AND "+AgeEnd + " " ;
			if (!AlarmCategory.equals("All")) {
				SQL += "AND EXISTS(SELECT * FROM s_d_code sdc WHERE sdc.code_type = 'alarm_code' AND sdc.biz_code = ad.alarm_code AND sdc.bak1 = '" + AlarmCategory + "') ";
			}
			SQL += "order by dayAge desc,alarmdate desc,alarmtime desc ";
			mLogger.info("==========AlarmReqController.queryAlarmLog.sql = " + SQL);
			Loutcome = tBaseQueryService.selectMapByAny(SQL);
			if (Loutcome.size() > 0) {
				for (HashMap<String, Object> hashMap : Loutcome) {
					JSONObject tJSONObject = new JSONObject();
					Object aDayAge = hashMap.get("dayAge");
					Object aDate = hashMap.get("alarmdate");
					Object aTime = hashMap.get("alarmtime");
					Object alarmID = hashMap.get("alarmid");
					Object alarmCode = hashMap.get("alarm_code");
					Object alarmName = hashMap.get("alarm_name");
					Object realValue = hashMap.get("actual_value");
					Object targetValue = hashMap.get("target_value");
					Object dealStatus = hashMap.get("deal_status");
					Object process_status = hashMap.get("process_status");
					Object isNormal = hashMap.get("is_remove");
					Object delayTime = hashMap.get("deal_delay").toString();
					Object dealPerson = new Object();
					Object dealTime = new Object();
					if ("02".equals(dealStatus)) {
						dealPerson = hashMap.get("deal_person");
						dealTime = hashMap.get("deal_time");

						tJSONObject.put("deal_person", dealPerson);
						tJSONObject.put("deal_time", dealTime);
					}

					tJSONObject.put("aDayAge", aDayAge);
					tJSONObject.put("aDate", aDate);
					tJSONObject.put("aTime", aTime);
					tJSONObject.put("alarmID", alarmID);
					tJSONObject.put("alarmCode", alarmCode);
					tJSONObject.put("alarmName", alarmName);
					tJSONObject.put("realValue", realValue);
					tJSONObject.put("targetValue", targetValue);
					tJSONObject.put("values", realValue + "/" + targetValue);
					tJSONObject.put("process_status", process_status);
					tJSONObject.put("is_normal", isNormal);
					tJSONObject.put("last_time", delayTime);

					tJSONArray.put(tJSONObject);
				}
				resJson.put("Result", "Success");
				resJson.put("HouseId", HouseId);
				resJson.put("AlarmData", tJSONArray);
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
		mLogger.info("=====Now end executing AlarmReqController.queryAlarmLog");
	}
}
