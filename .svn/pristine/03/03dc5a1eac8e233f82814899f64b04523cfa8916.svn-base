/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.scheduler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.app.biz.AlidayuTTSManager;
import com.mtc.app.biz.RemindReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SDUserService;
import com.mtc.common.util.DateUtil;
import com.mtc.common.util.IPUtil;
import com.mtc.entity.app.SBAlarmInfoTemp;
import com.mtc.entity.app.SBCallAlarm;
import com.mtc.entity.app.SBCallDetail;
import com.mtc.entity.app.SBCallMain;
import com.mtc.entity.app.SBFarmAlarmerTemp;
import com.mtc.entity.app.SDTtsTempParam;
import com.mtc.entity.app.SDTtsTemplate;
import com.mtc.entity.app.SDUser;

/**
 * 
 * 根据语音配置表，获取报警信息
 * 
 * @author lx
 *
 */
@Service
public class RemindInfoFiltrateExecuteJob{
	
	private static Logger mLogger =Logger.getLogger(RemindInfoFiltrateExecuteJob.class);
	
	@Autowired
	private BaseQueryService baseQueryService;
	
	@Autowired
	private RemindReqManager alarmReqManager;
	
	@Autowired
	private AlidayuTTSManager alidayuTTSManager;
	
	@Autowired
	private SDUserService userService;
	
	private String tempId = "";
	
	
	
	public String getTempId() {
		return tempId;
	}


	public void setTempId(String tempId) {
		this.tempId = tempId;
	}


	private ResourceBundle conf = null;
	public  String getPropertyValue(String keyName){
		if(conf == null){
			conf= ResourceBundle.getBundle("config/pro/alidayu");
		}
		String value= conf.getString(keyName);
		return value;
	}
	
	/**
	 * 
	 */
	private double minDistance = -1;
	private double maxDistance = 1;
	
	
	public double getMinDistance() {
		return minDistance;
	}


	public void setMinDistance(double minDistance) {
		this.minDistance = minDistance;
	}


	public double getMaxDistance() {
		return maxDistance;
	}


	public void setMaxDistance(double maxDistance) {
		this.maxDistance = maxDistance;
	}


	/**
	 * 获取报警信息
	 */
	public void doGetRemindAlarms(){
		
		if(!IPUtil.needRunTask()){
			mLogger.info("本机不启用RemindInfoFiltrateExecuteJob");
			return ;
		}
		
		int alarmType = 0;// 报警类型
		String dealStatus = "01";// 报警处理状态
		int interval = 5;// 5分钟之前
		int maxTimes = 6;// 最多拨打6次
		
		try {
			tempId = getPropertyValue("tempId");
		} catch (Exception e) {
			e.printStackTrace();
			mLogger.error("未配置tempId",e);
			return;
		}
		
		mLogger.info("default tempId:" + tempId);
		// 查询需要报警的记录
		List<HashMap<String,Object>> alarms = getAlarmInfo(alarmType, dealStatus, interval);
		
		
		SDTtsTemplate template = alidayuTTSManager.getSDTtsTemplate(tempId);
		if(template == null){
			mLogger.error("can not find template info!");
			return;
		}
		// 模板ID
		String tempId = template.getTempId();
		
		
		if(alarms != null && !alarms.isEmpty()){
			// 保存每一个栋舍的报警信息  key:farmId_houseId
			HashMap<String, List<SBAlarmInfoTemp>> alarmInfoTemps = new HashMap<>();
			// 保存农场的报警设置
			HashMap<Integer, SBAlarmInfoTemp> farmAlarmSettings = new HashMap<>();
			
			tidyAlarmInfo(alarmType, dealStatus, alarms, alarmInfoTemps, farmAlarmSettings);
			
			// 遍历栋舍报警信息
			if(!alarmInfoTemps.isEmpty()){
				Set<String> keys = alarmInfoTemps.keySet();
				Iterator<String> it = keys.iterator();
				while(it.hasNext()){
					String key = it.next();
					// 一个农场栋舍的报警信息
					List<SBAlarmInfoTemp> temps = alarmInfoTemps.get(key);
					int farmId = temps.get(0).getFarmId();
					int houseId = temps.get(0).getHouseId();
					String farmName = temps.get(0).getFarmName();
					String houseName = temps.get(0).getHouseName();
					mLogger.info("开始处理农场的报警信息：farmId:" + farmId + ",houseId:" + houseId);
					// 农场的报警设置信息
					SBAlarmInfoTemp alarmSet = farmAlarmSettings.get(farmId);
					mLogger.info("农场配置信息:" + JSONObject.fromObject(alarmSet));
					// 查询农场饲养员
					List<SBFarmAlarmerTemp> alarmers1 = getFarmSiAlarmers(farmId, alarmSet.getPersonStatus(),1, alarmType,houseId);
					
					// 查询农场报警人
					List<SBFarmAlarmerTemp> alarmers2 = getFarmNormalAlarmer(farmId, alarmSet.getPersonStatus(), 0, alarmType,houseId);
					if(alarmers1 != null && !alarmers1.isEmpty()){
						if(alarmers1.size() == 1){
							alarmers2.addAll(alarmers1);
						}else{
							int roundIndex = (int) (Math.random()*alarmers1.size());
							alarmers2.add(alarmers1.get(roundIndex));
						}
					}
					
					Collections.sort(alarmers2, new Comparator<SBFarmAlarmerTemp>() {

						@Override
						public int compare(SBFarmAlarmerTemp o1,
								SBFarmAlarmerTemp o2) {
							if(o1.getUserLevel() > o2.getUserLevel()){
								return 1;
							}
							return -1;
						}
					});
					
					
					// 拨打人信息记录表
					List<SBCallDetail> details = new ArrayList<>();
					// 拨打提醒记录表
					List<SBCallAlarm> callAlarms = new ArrayList<>();
					
					// 查询拨打信息主表，查看是否有最近的拨打记录
					SBCallMain callMain = alarmReqManager.selectCallMainByParams(farmId, houseId, tempId);
					
					int mainId = 0;
					if(callMain == null){
						mLogger.info("在拨打信息主表中没有发现此记录，添加新的报警拨打相关信息");
						
						createAlarms(mainId, callAlarms, temps);
						
					}else{
						mainId = callMain.getId();
						
						// 查看拨打提醒记录表，如果已经存在，在对其时间进行比较
						// 01：新建 02：待返回 03：成功 04：失败
						
						// 数据库中已有的报警信息
						mLogger.info("查询数据库中已报警的提醒信息");
						List<SBCallAlarm> alarmsDB = alarmReqManager.getSBCallAlarmByMainId(mainId);
						
						// 将新的报警添加到拨打提醒记录表中
						comparisonDBAndNewAlarmInfo(callMain, callAlarms, temps, alarmsDB);
					}
					
					if(!callAlarms.isEmpty()){
						
						JSONObject json = createParamJson(tempId, farmName, houseName, callAlarms);
						mLogger.info("参数值：" + json.toString());
						
						createAlarmer(mainId, maxTimes, alarmers2, details,json);
						
						SBCallMain main = createCallMain(farmId, houseId, tempId);
						
						mainId = alarmReqManager.saveCallMainAndOthers(main, callAlarms, details);
						
						mLogger.info("生产新的报警，new main id :" + mainId);
					}else{
						mLogger.info("没有发现新的报警信息。。。");
					}
					
				}
			}
		}else{
			mLogger.info("没有查到符合要求的报警信息！");
		}
		
	}
	
	
	private JSONObject createParamJson(String tempId ,String farmName,String houseName,List<SBCallAlarm> callAlarms){
		
		mLogger.info("生成报警参数值。。");
		List<SDTtsTempParam> params = alidayuTTSManager.getTTSTempParams(tempId);
		
		JSONObject json = new JSONObject();
		String codeStr = "(";
		for(int i = 0 ; i < callAlarms.size(); i++){
			if(i == callAlarms.size() - 1){
				codeStr += "'" + callAlarms.get(i).getAlarmCode() + "')";
			}else{
				codeStr += "'" + callAlarms.get(i).getAlarmCode() + "',";
			}
		}
		
		String sql = "select code_name from s_d_code where biz_code in  " + codeStr;
		mLogger.info("query codename sql:" + sql);
		List<HashMap<String,Object>> codes = baseQueryService.selectMapByAny(sql);
		
		String alarmStr = "";
		
		if(codes != null){
			for(HashMap<String,Object> code : codes){
				alarmStr += code.get("code_name") + ",";
			}
		}
		
		if(alarmStr.length() > 0 )
		{
			if(alarmStr.length() <= 15){
				alarmStr = alarmStr.substring(0, alarmStr.length() - 1);
			}else{
				alarmStr = alarmStr.substring(0, 15);
			}
		}
		mLogger.info("报警信息:" + alarmStr);
		
		if(params != null)
		{
			mLogger.info("template params size :" + params.size());
			for(SDTtsTempParam param :params){
				String name = param.getParamName();
				if(name.equals("name")){
					json.put("name", "");
				}else if(name.equals("farmName")){
					json.put("farmName", farmName);
				}else if(name.equals("exceptions")){
					json.put("exceptions", alarmStr);
				}else if(name.equals("houseName")){
					json.put("houseName", houseName);
				}
			}
		}else{
			mLogger.info("can not find template params");
		}
		
		return json;
	}
	
	
	/**
	 * 
	 * 将查询出的报警信息进行整理
	 * 
	 * 按照农场栋舍整理，并且整理出农场的报警设置
	 * 
	 * @param alarmType
	 * @param dealStatus
	 * @param alarms
	 * @param alarmInfoTemps
	 * @param farmAlarmSettings
	 */
	private void tidyAlarmInfo(int alarmType,String dealStatus,List<HashMap<String,Object>> alarms,HashMap<String, List<SBAlarmInfoTemp>> alarmInfoTemps,HashMap<Integer, SBAlarmInfoTemp> farmAlarmSettings){
		for(HashMap<String,Object> map : alarms){
			String farmName = map.get("farm_name_chs").toString();
			String houseName = map.get("house_name").toString();
			int id = Integer.parseInt(map.get("id").toString());
			String alarmCode = map.get("alarm_code").toString();
			String alarmName = "";//map.get("alarm_Name").toString();
			int farmId = Integer.parseInt(map.get("farm_id").toString());
			int houseId = Integer.parseInt(map.get("house_id").toString());
			int farmBreedId = Integer.parseInt(map.get("farm_breed_id").toString());
			int houseBreedId = Integer.parseInt(map.get("house_breed_id").toString());
			int monitorId = Integer.parseInt(map.get("monitor_id").toString());
			BigDecimal actualValue = new BigDecimal(0);
			if(map.get("actual_value") != null){
				actualValue = new BigDecimal(map.get("actual_value").toString());
			}
			BigDecimal setValue = new BigDecimal(0);
			if(map.get("set_value") != null){
				setValue = new BigDecimal(map.get("set_value").toString());
			}
			String valueUnit = map.get("value_unit") == null ? "" : map.get("value_unit").toString();
			Date alarmTime = DateUtil.parser(map.get("alarm_time").toString(),DateUtil.LONG_DATE_FORMAT);
			int dealDelay = map.get("deal_delay") == null ? 0 :(int) map.get("deal_delay");
			Date dealTime = map.get("deal_time") == null ? null : DateUtil.parser(map.get("deal_time").toString(),DateUtil.LONG_DATE_FORMAT);
			int responsePerson = map.get("response_person") == null ? 0 : (int)map.get("response_person");
			String personStatus = map.get("person_rele_house").toString();
			String targetStatus = map.get("switch_rele_house").toString();
			String targetSetStatus = map.get("alarm_rele_house").toString();
			
			if(farmAlarmSettings.get(farmId) == null){
				SBAlarmInfoTemp alarmSet = new SBAlarmInfoTemp();
				alarmSet.setPersonStatus(personStatus);
				alarmSet.setTargetSetStatus(targetSetStatus);
				alarmSet.setTargetStatus(targetStatus);
				farmAlarmSettings.put(farmId, alarmSet);
			}
			
			SBAlarmInfoTemp temp = new SBAlarmInfoTemp();
			temp.setActualValue(actualValue);
			temp.setAlarmCode(alarmCode);
			temp.setAlarmName(alarmName);
			temp.setAlarmTime(alarmTime);
			temp.setAlarmType(alarmType);
			temp.setDealDelay(dealDelay);
			temp.setDealStatus(dealStatus);
			temp.setDealTime(dealTime);
			temp.setFarmBreedId(farmBreedId);
			temp.setFarmId(farmId);
			temp.setFarmName(farmName);
			temp.setHouseBreedId(houseBreedId);
			temp.setHouseId(houseId);
			temp.setHouseName(houseName);
			temp.setId(id);
			temp.setMonitorId(monitorId);
			temp.setPersonStatus(personStatus);
			temp.setResponsePerson(responsePerson);
			temp.setSetValue(setValue);
			temp.setTargetSetStatus(targetSetStatus);
			temp.setTargetStatus(targetStatus);
			temp.setValueUnit(valueUnit);
			
			String key = farmId + "_" + houseId;
			List<SBAlarmInfoTemp> temps = alarmInfoTemps.get(key);
			if(temps == null){
				temps = new ArrayList<>();
				temps.add(temp);
				alarmInfoTemps.put(key, temps);
			}else{
				temps.add(temp);
			}
			
		}
	}
	
	/**
	 * 保存拨打主表信息，返回最新ID
	 * @param farmId
	 * @param houseId
	 * @param tempId
	 * @return
	 */
	private SBCallMain createCallMain(int farmId,int houseId,String tempId){
		Date createTime = new Date(System.currentTimeMillis());
		SBCallMain callMain = new SBCallMain();
		callMain.setCreateTime(createTime);
		callMain.setCallResult("01");
		callMain.setFarmId(farmId);
		callMain.setHouseId(houseId);
		callMain.setTempId(tempId);
		return callMain;
	}
	
	/**
	 * 保存拨打主表信息，返回最新ID
	 * @param farmId
	 * @param houseId
	 * @param tempId
	 * @return
	 */
	private int saveCallMain(int farmId,int houseId,String tempId){
		Date createTime = new Date(System.currentTimeMillis());
		SBCallMain callMain = new SBCallMain();
		callMain.setCreateTime(createTime);
		callMain.setCallResult("01");
		callMain.setFarmId(farmId);
		callMain.setHouseId(houseId);
		callMain.setTempId(tempId);
		mLogger.info("拨打信息主表信息入库：" + JSONObject.fromObject(callMain));
		// 插入数据库
		alarmReqManager.insert(callMain);
		mLogger.info("new call mainId :" + callMain.getId());
		return callMain.getId();
	}
	/**
	 * 将查询出来的报警信息写入到拨打提醒记录表中
	 * @param mainId
	 * @param callAlarms
	 * @param temps
	 */
	private void createAlarms(int mainId,List<SBCallAlarm> callAlarms,List<SBAlarmInfoTemp> temps){
		java.sql.Date createTime = new java.sql.Date(System.currentTimeMillis());
		for(SBAlarmInfoTemp temp : temps){
			SBCallAlarm callAlarm = new SBCallAlarm();
			callAlarm.setMainId(mainId);
			callAlarm.setAlarmId(temp.getId());
			callAlarm.setAlarmCode(temp.getAlarmCode());
			callAlarm.setCreateTime(createTime);
			callAlarm.setVarBak1("01");
			callAlarms.add(callAlarm);
		}
		mLogger.info("将查询出来的报警信息写入到拨打提醒记录表中");
	}
	
	/**
	 * 对比数据库中已有的报警信息和最新曝出的报警信息，将数据库中不存在的报警信息再次入库
	 * @param mainId
	 * @param alarmsDB
	 * @param callAlarms
	 * @param temps
	 */
	private void comparisonDBAndNewAlarmInfo(SBCallMain callMain,List<SBCallAlarm> callAlarms,List<SBAlarmInfoTemp> temps,List<SBCallAlarm> alarmsDB){
		mLogger.info("对比数据库中已有的报警信息和最新曝出的报警信息，筛选出需要报警的信息");
		java.sql.Date createTime = new java.sql.Date(System.currentTimeMillis());
		int mainId = callMain.getId();
		for(SBAlarmInfoTemp temp : temps){
			boolean exist = false;
			for(SBCallAlarm tempDB : alarmsDB){
				if(temp.getId().intValue() == tempDB.getAlarmId().intValue()){
					exist = true;
					break;
				}else{
					// 同一栋舍，同一报警，距离上次拨打成功30分钟内，不当做新的报警
					if(callMain.getCallResult().equals("03") && tempDB.getAlarmCode().equals(temp.getAlarmCode())){
						if(System.currentTimeMillis() - callMain.getSuccTime().getTime() <= 30*60*1000){
							exist = true;
							break;
						}
					}
				}
			}
			// 数据库中不存在
			if(exist == false){
				SBCallAlarm callAlarm = new SBCallAlarm();
				
				callAlarm.setMainId(mainId);
				callAlarm.setAlarmId(temp.getId());
				callAlarm.setAlarmCode(temp.getAlarmCode());
				callAlarm.setCreateTime(createTime);
				callAlarm.setVarBak1("01");
				callAlarms.add(callAlarm);
			}
		}
		
		if(callAlarms.isEmpty()){
			mLogger.info("没有发现新的报警信息...");
		}
		
	}
	
	/**
	 * 分配报警人，报警人顺序从登记高到低
	 * 
	 * @param mainId
	 * @param alarmers
	 * @param details
	 */
	private void createAlarmer(int mainId,int maxTimes,List<SBFarmAlarmerTemp> alarmers,List<SBCallDetail> details,JSONObject json){
		mLogger.info("分配报警人.." + alarmers.size());
		java.sql.Date createTime = new java.sql.Date(System.currentTimeMillis());
		int alarmTimes = 0;
		for(int i = 0; i < alarmers.size(); i++){
			alarmTimes ++ ;
			SDUser user = userService.selectValidByPrimaryKey(alarmers.get(i).getUserId());
			json.put("name", user.getUserRealName());
			SBCallDetail detail = new SBCallDetail();
			detail.setCallOrder(alarmTimes);
			detail.setMainId(mainId);
			detail.setPhoneNum(alarmers.get(i).getPhoneNum());
			detail.setUserId(alarmers.get(i).getUserId());
			detail.setCreateTime(createTime);
			detail.setCallResult("01");
			detail.setVarBak1(json.toString());
			details.add(detail);
			
			if(alarmTimes >= maxTimes){
				break;
			}
			if(i == alarmers.size()-1){
				i = -1;
			}
		}
	}
	
	/**
	 * 获取农场普通报警人
	 * @param farmId
	 * @param personStatus
	 * @param userType
	 * @param alarmType
	 * @return
	 */
	private List<SBFarmAlarmerTemp> getFarmNormalAlarmer(int farmId,String personStatus,int userType,int alarmType,int houseId){
		/**查询普通联系人*/
		String queryNromalAlarmer = " SELECT usr.id,usr.user_code,usr.user_mobile_1,usr.user_real_name,alarmer.user_order,"
				+ " alarmer.user_type ,alarmer.farm_id,alarmer.house_id ,alarmer.remind_method"
				+ " FROM s_b_reminder alarmer , s_d_user usr ,s_b_remind_setting alarmSet ,s_b_remind_switch alarmEnabled "
				+ " WHERE  alarmSet.remind_method = alarmer.remind_method AND alarmEnabled.remind_method = alarmer.remind_method"
				+ " AND alarmer.remind_method = " + alarmType + " AND alarmEnabled.status = 'Y' AND  alarmer.farm_Id = alarmSet.farm_Id "
				+ " AND alarmSet.farm_Id = alarmEnabled.farm_Id AND CASE WHEN alarmSet.person_rele_house = 'Y' THEN "
				+ " alarmer.house_Id = alarmEnabled.house_Id AND alarmEnabled.house_Id > 0 ELSE alarmEnabled.house_Id = 0 END"
				+ " AND alarmer.house_Id = alarmEnabled.house_Id AND alarmer.user_id = usr.id AND alarmer.farm_Id =  " + farmId 
				+ " and alarmer.house_id = " + houseId + " "
				+ " AND alarmSet.person_rele_house = '" + personStatus + "' AND alarmer.user_type = " + userType;
		mLogger.info("queryNromalAlarmer:" + queryNromalAlarmer);
		// 查询到的报警信息
		List<HashMap<String,Object>> normalAlarmers = baseQueryService.selectMapByAny(queryNromalAlarmer);
		
		List<SBFarmAlarmerTemp> alarmers = resToAlarmer(normalAlarmers);
		
		return alarmers;
		
	}
	
	/**
	 * 获取农场饲养员
	 * @param farmId
	 * @param personStatus
	 * @param userType
	 * @param alarmType
	 * @return
	 */
	private List<SBFarmAlarmerTemp> getFarmSiAlarmers(int farmId,String personStatus,int userType,int alarmType,int houseId){
		/**查询饲养员*/
		String querySiAlarmer = " SELECT  usr.id,usr.user_code,usr.user_mobile_1,usr.user_real_name , alarmer.user_order ,"
				+ " alarmer.user_type , f.farm_id, f.house_id ,alarmEnabled.remind_method "
				+ " FROM s_d_user usr , s_b_user_role role , s_b_user_house f ,s_b_remind_setting alarmSet ,"
				+ " s_b_remind_switch alarmEnabled,s_b_reminder alarmer "
				+ " WHERE f.farm_id = alarmSet.farm_Id AND alarmSet.farm_Id = alarmEnabled.farm_Id "
				+ " AND alarmer.farm_id = f.farm_id AND alarmer.user_type = " + userType + " AND CASE WHEN alarmSet.person_rele_house = 'Y' "
				+ " THEN alarmEnabled.house_Id = f.house_id AND alarmer.house_id = f.house_id ELSE alarmEnabled.house_Id = 0 "
				+ " AND alarmer.house_id = 0 END AND alarmSet.remind_method = alarmEnabled.remind_method "
				+ " AND alarmEnabled.remind_method = alarmer.remind_method AND alarmEnabled.remind_method =  " + alarmType 
				+ " AND alarmSet.person_rele_house = '" + personStatus + "' AND  f.farm_id = " + farmId + " AND f.user_id = usr.id "
				+ " and alarmer.house_id = " + houseId + " "
				+ " AND usr.id = role.user_id AND role.role_id = 4 "
				+ " GROUP BY usr.id,usr.user_code,usr.user_mobile_1,usr.user_real_name,alarmer.user_order " ;
		
		mLogger.info("querySiAlarmer:" + querySiAlarmer);
		List<HashMap<String,Object>> siAlarmers = baseQueryService.selectMapByAny(querySiAlarmer);
		
		List<SBFarmAlarmerTemp> alarmers = resToAlarmer(siAlarmers);
		
		return alarmers;
	}
	
	/**
	 * 获取一个农场所有的联系人
	 * @param farmId
	 * @param personStatus
	 * @param alarmType
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<SBFarmAlarmerTemp> getAllAlarmers(int farmId,String personStatus,int alarmType){
		String queryNromalAlarmer = " SELECT usr.id,usr.user_code,usr.user_mobile_1,usr.user_real_name,alarmer.user_order,"
				+ " alarmer.user_type ,alarmer.farm_id,alarmer.house_id ,alarmer.remind_method"
				+ " FROM s_b_reminder alarmer , s_d_user usr ,s_b_remind_setting alarmSet ,s_b_remind_switch alarmEnabled "
				+ " WHERE  alarmSet.remind_method = alarmer.remind_method AND alarmEnabled.remind_method = alarmer.remind_method"
				+ " AND alarmer.remind_method = " + alarmType + " AND alarmEnabled.status = 'Y' AND  alarmer.farm_Id = alarmSet.farm_Id "
				+ " AND alarmSet.farm_Id = alarmEnabled.farm_Id AND CASE WHEN alarmSet.person_rele_house = 'Y' THEN "
				+ " alarmer.house_Id = alarmEnabled.house_Id AND alarmEnabled.house_Id > 0 ELSE alarmEnabled.house_Id = 0 END"
				+ " AND alarmer.house_Id = alarmEnabled.house_Id AND alarmer.user_id = usr.id AND alarmer.farm_Id =  " + farmId 
				+ " AND alarmSet.person_rele_house = '" + personStatus + "' AND alarmer.user_type = 0 ";
		
		String querySiAlarmer = " SELECT  usr.id,usr.user_code,usr.user_mobile_1,usr.user_real_name , alarmer.user_order ,"
				+ " alarmer.user_type , f.farm_id, f.house_id ,alarmEnabled.remind_method "
				+ " FROM s_d_user usr , s_b_user_role role , s_b_user_house f ,s_b_remind_setting alarmSet ,"
				+ " s_b_remind_switch alarmEnabled,s_b_reminder alarmer "
				+ " WHERE f.farm_id = alarmSet.farm_Id AND alarmSet.farm_Id = alarmEnabled.farm_Id "
				+ " AND alarmer.farm_id = f.farm_id AND alarmer.user_type = 1 AND CASE WHEN alarmSet.person_rele_house = 'Y' "
				+ " THEN alarmEnabled.house_Id = f.house_id AND alarmer.house_id = f.house_id ELSE alarmEnabled.house_Id = 0 "
				+ " AND alarmer.house_id = 0 END AND alarmSet.remind_method = alarmEnabled.remind_method "
				+ " AND alarmEnabled.remind_method = alarmer.remind_method AND alarmEnabled.remind_method =  " + alarmType 
				+ " AND alarmSet.person_rele_house = '" + personStatus + "' AND  f.farm_id = " + farmId + " AND f.user_id = usr.id "
				+ " AND usr.id = role.user_id AND role.role_id = 4 "
				+ " GROUP BY usr.id,usr.user_code,usr.user_mobile_1,usr.user_real_name,alarmer.user_order  " ;
		
		String unionSQL = queryNromalAlarmer + " UNION ALL " + querySiAlarmer;
		mLogger.info("unionSQL:" + unionSQL);
		List<HashMap<String,Object>> siAlarmers = baseQueryService.selectMapByAny(unionSQL);
		
		List<SBFarmAlarmerTemp> alarmers = resToAlarmer(siAlarmers);
		
		return alarmers;
	}
	
	/**
	 * 将查出来的联系人进行二次封装
	 * @param values
	 * @return
	 */
	private List<SBFarmAlarmerTemp> resToAlarmer(List<HashMap<String,Object>> values){
		List<SBFarmAlarmerTemp> alarmers = new ArrayList<>();
		if(values != null && !values.isEmpty()){
			for(HashMap<String,Object> map : values){
				int userId = (int) map.get("id");
				String phoneNum = map.get("user_mobile_1").toString();
				String userName = map.get("user_real_name").toString();
				int userLevel = (int)map.get("user_order");
				int farmId = (int)map.get("farm_id");
				int houseId = (int)map.get("house_id");
				int userType = (int)map.get("user_type");
				int alarmType = (int)map.get("remind_method");
				
				SBFarmAlarmerTemp temp = new SBFarmAlarmerTemp();
				temp.setAlarmType(alarmType);
				temp.setFarmId(farmId);
				temp.setHouseId(houseId);
				temp.setUserId(userId);
				temp.setPhoneNum(phoneNum);
				temp.setUserLevel(userLevel);
				temp.setUserName(userName);
				temp.setUserType(userType);
				
				alarmers.add(temp);
			}
		}
		
		return alarmers;
		
	}
	
	/**
	 * // 查询需要报警的记录  
	 * @param alarmType
	 * @param dealStatus
	 * @param interval
	 * @param bigVal
	 * @param smallVal
	 * @return
	 */
	private List<HashMap<String,Object>> getAlarmInfo(int alarmType,String dealStatus,int interval){
		// 查询需要报警的记录
		String queryAlarmSql = " SELECT DISTINCT f.farm_name_chs, h.house_name, alarmInfo.* ,(alarmInfo.actual_value - alarmInfo.set_value) AS val , " + 
				" alarmSet.person_rele_house,alarmSet.switch_rele_house,alarmSet.alarm_rele_house " + 
				" FROM s_b_alarm_inco alarmInfo  ,s_d_house h , s_d_farm f ,s_b_remind_switch alarmEnabled , " +
				" s_b_remind_alarmcode alarmCodeSet,s_b_remind_setting alarmSet   " + 
				" WHERE alarmInfo.farm_id = f.id AND alarmInfo.house_id = h.id  " + 
				" AND alarmInfo.farm_id = h.farm_id AND alarmInfo.farm_id = alarmEnabled.farm_Id  " + 
				" AND alarmSet.farm_Id = alarmInfo.farm_id AND alarmEnabled.remind_method = alarmCodeSet.remind_method " + 
				" AND alarmEnabled.remind_method = alarmSet.remind_method AND alarmEnabled.remind_method =  " + alarmType  + 
				" AND CASE WHEN alarmSet.alarm_rele_house = 'Y' THEN alarmInfo.house_id = alarmEnabled.house_Id AND alarmEnabled.house_Id > 0  " + 
				" ELSE alarmEnabled.house_Id = 0 END " + 
				" AND CASE WHEN alarmSet.switch_rele_house = 'Y' THEN alarmEnabled.house_id = alarmCodeSet.house_Id AND alarmCodeSet.house_Id > 0  " + 
				" ELSE alarmCodeSet.house_Id = 0 END  " + 
				" AND alarmEnabled.status = 'Y' AND alarmCodeSet.farm_Id = alarmInfo.farm_id  " + 
				" AND alarmInfo.alarm_code = alarmCodeSet.alarm_code AND alarmInfo.deal_status =  '" + dealStatus + "'" + 
				" AND alarmInfo.alarm_time < DATE_SUB(NOW(),INTERVAL " + interval + " MINUTE) AND alarmInfo.alarm_time >= DATE_FORMAT(NOW(),'%Y-%m-%d') " +
				" AND (alarmInfo.actual_value - alarmInfo.set_value >= " + maxDistance + " OR alarmInfo.actual_value - alarmInfo.set_value <= " + minDistance + ") " + 
				" AND alarmInfo.id NOT IN (SELECT callalarm.alarm_id FROM s_b_call_alarm callalarm); " ;
		
		mLogger.info("queryAlarmSql:" + queryAlarmSql);
		// 查询到的报警信息
		List<HashMap<String,Object>> alarms = baseQueryService.selectMapByAny(queryAlarmSql);
		
		return alarms;
	}
	
}
