/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.biz.DeviceBbfReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBBbfar;
import com.mtc.entity.app.SBMonitorCurr;
import com.mtc.entity.app.SBMonitorHist;

/**
 * @ClassName: DeviceReqController
 * @Description:
 * @Date 2016-1-4 下午5:45:57
 * @Author Shao Yao Yu
 * 
 */
@Controller
@RequestMapping("/sys/device")
public class DeviceReqController {

	private static Logger mLogger = Logger.getLogger(DeviceReqController.class);
	@Autowired
	private DeviceBbfReqManager mDeviceBbfReqManager;
	@Autowired
	private BaseQueryService mBaseQueryService;

	@RequestMapping("/dataUploadBBF")
	public void dataUploadBBF(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====Now start executing DeviceReqController.dataUploadBBF");
		
		String dealRes = null;
		Date curDate = new Date();
		try {
			SBBbfar tSBBbfar = new SBBbfar();
			String keyid = request.getParameter("keyid");
			String t1 = request.getParameter("t1");
			String t2 = request.getParameter("t2");
			String t3 = request.getParameter("t3");
			String t4 = request.getParameter("t4");
			String t5 = request.getParameter("t5");
			String t6 = request.getParameter("t6");
			String h1 = request.getParameter("h1");
			String h2 = request.getParameter("h2");
			String p = request.getParameter("p");
			String station = request.getParameter("station");
			String gps = request.getParameter("gps");
			String help = request.getParameter("help");
			String dateTime = request.getParameter("dateTime");
			String IMEI = request.getParameter("IMEI");
			String ICCID = request.getParameter("ICCID");
			if (keyid != null) {
				tSBBbfar.setKeyid(keyid);
				if (t1 != null) {
					tSBBbfar.setT1(t1);
				}
				if (t2 != null) {
					tSBBbfar.setT2(t2);
				}
				if (t3 != null) {
					tSBBbfar.setT3(t3);
				}
				if (t4 != null) {
					tSBBbfar.setT4(t4);
				}
				if (t5 != null) {
					tSBBbfar.setT5(t5);
				}
				if (t6 != null) {
					tSBBbfar.setT6(t6);
				}
				if (h1 != null) {
					tSBBbfar.setH1(h1);
				}
				if (h2 != null) {
					tSBBbfar.setH2(h2);
				}
				if (p != null) {
					tSBBbfar.setP(p);
				}
				if (station != null) {
					tSBBbfar.setStation(station);
				}
				if (gps != null) {
					tSBBbfar.setGps(gps);
				}
				if (help != null) {
					tSBBbfar.setHelp(help);
				}
				if (dateTime != null) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					java.util.Date date = sdf.parse(dateTime);
					tSBBbfar.setDateTime(date);
				} else {
					tSBBbfar.setDateTime(curDate);
				}
				if(!PubFun.isNull(IMEI)){
					tSBBbfar.setBak1(IMEI);
				}
				if(!PubFun.isNull(ICCID)){
					tSBBbfar.setBak2(ICCID);
				}
				
				String houseDeviceRela = tSBBbfar.getKeyid();
				//  Bak2 存 SIM卡 iccid
				if(!PubFun.isNull(tSBBbfar.getBak2())){
					houseDeviceRela = tSBBbfar.getBak2();
				}else{
					//  Bak1 存设备的 IMEI号
					if(!PubFun.isNull(tSBBbfar.getBak1())){
						houseDeviceRela = tSBBbfar.getBak1();
					}
				}
				
				HashMap<String, Object> mParas = new HashMap<String, Object>();
				mParas.put("SBBbfar", tSBBbfar);
				mDeviceBbfReqManager.dealSaveSbbbfar(mParas);
				
				String SQL1 = "SELECT c.house_id ,c.farm_id, s_f_getDayAgeByHouseId(c.house_id,CURDATE()) AS age,"
								+ "d.target_temp as set_temp,d.max_temp as high_alarm_temp,d.min_temp as low_alarm_temp,0 as set_humidity,d.temp_cpsation,d.temp_cordon,"
								+ "(select group_concat(DISTINCT e.probe_code) from s_b_house_probe e where e.house_id = c.house_id ) as probe_codes "
								+ "FROM s_b_devi_house c "
								+ "LEFT JOIN s_b_layer_house_alarm d on d.house_id = c.house_id "
								+ "WHERE  c.device_code = '" + houseDeviceRela + "' ";
				mLogger.info("=======DeviceReqController.dataUploadBBF.sql"+SQL1);
				List<HashMap<String, Object>> listMap1 = mBaseQueryService
						.selectMapByAny(SQL1);
				if (listMap1.size() > 0) {
					Object houseId = listMap1.get(0).get("house_id");
					Object farmId = listMap1.get(0).get("farm_id");
					Object age = listMap1.get(0).get("age");
					Object set_temp = listMap1.get(0).get("set_temp");
					Object high_alarm_temp = listMap1.get(0).get("high_alarm_temp");
					Object low_alarm_temp = listMap1.get(0).get("low_alarm_temp");
					Object set_humidity = listMap1.get(0).get("set_humidity");
					Object probe_codes = listMap1.get(0).get("probe_codes");
					String temp_cpsation = null;
					if(listMap1.get(0).get("temp_cpsation") != null){
						temp_cpsation = listMap1.get(0).get("temp_cpsation").toString();
					}else{
						temp_cpsation = "0";
					}
					
					BigDecimal temp_cordon = new BigDecimal(0);
					if(temp_cpsation.equals("1")){
						temp_cordon = PubFun.getBigDecimalData(listMap1.get(0).get("temp_cordon").toString());
					}
					
					int HouseId = (int) houseId;		
					BigDecimal T1 = PubFun.getBigDecimalData(t1 == null?null:t1);
					BigDecimal T2 = PubFun.getBigDecimalData(t2 == null?null:t2);
					BigDecimal T3 = PubFun.getBigDecimalData(t3 == null?null:t3);
					BigDecimal T4 = PubFun.getBigDecimalData(t4 == null?null:t4);
					BigDecimal T5 = PubFun.getBigDecimalData(t5 == null?null:t5);
					BigDecimal T6 = PubFun.getBigDecimalData(t6 == null?null:t6);
					
					SBMonitorCurr tSBMonitorCurr = new SBMonitorCurr();
					tSBMonitorCurr.setHouseId(HouseId);
					tSBMonitorCurr.setFarmId((int)farmId);
					tSBMonitorCurr.setCollectDatetime(tSBBbfar.getDateTime());
					tSBMonitorCurr.setDealStatus(0);
					tSBMonitorCurr.setDateAge(age==null?0:(int)age);
					
					
					// 探头温度如果是 85度，则说明接触不良，需要取上一次温度
					if((T1 != null && new BigDecimal(85).compareTo(T1)== 0)
						|| (T2 != null && new BigDecimal(85).compareTo(T2)== 0)
						|| (T3 != null && new BigDecimal(85).compareTo(T3) == 0)
						|| (T4 != null && new BigDecimal(85).compareTo(T4)== 0)
						|| (T5 != null && new BigDecimal(85).compareTo(T5)== 0)
						|| (T6 != null && new BigDecimal(85).compareTo(T6)== 0)
							){
						String tempSql = "SELECT inside_temp1,inside_temp2,inside_temp3,inside_temp4,inside_temp5,inside_temp6,outside_temp from s_b_monitor_curr where house_id = " + HouseId;
						List<HashMap<String, Object>> tempData = mBaseQueryService.selectMapByAny(tempSql);
						if(tempData.size() == 1){
							if(T1 != null && new BigDecimal(85).compareTo(T1)== 0){
								T1 = (BigDecimal)tempData.get(0).get("inside_temp1");
							}
							if(T2 != null && new BigDecimal(85).compareTo(T2)== 0){
								T2 = (BigDecimal)tempData.get(0).get("inside_temp2");
							}
							if(T3 != null && new BigDecimal(85).compareTo(T3)== 0){
								T3 = (BigDecimal)tempData.get(0).get("inside_temp3");
							}
							if(T4 != null && new BigDecimal(85).compareTo(T4)== 0){
								T4 = (BigDecimal)tempData.get(0).get("inside_temp5");
							}
							if(T5 != null && new BigDecimal(85).compareTo(T5)== 0){
								T5 = (BigDecimal)tempData.get(0).get("inside_temp6");
							}
							if(T6 != null && new BigDecimal(85).compareTo(T6)== 0){
								T6 = (BigDecimal)tempData.get(0).get("outside_temp");
							}
						}
					}
					
					tSBMonitorCurr.setInsideTemp1(T1);
					tSBMonitorCurr.setInsideTemp2(T2);
					tSBMonitorCurr.setInsideTemp3(T3);
					tSBMonitorCurr.setInsideTemp5(T4);
					tSBMonitorCurr.setInsideTemp6(T5);
					tSBMonitorCurr.setOutsideTemp(T6);
					
					BigDecimal insideAvgTemp = null;
					BigDecimal pointTempDiff = new BigDecimal(0);
					if(probe_codes != null && !probe_codes.equals("")){
						String[] probe_cd = probe_codes.toString().split(",");
						BigDecimal insideAvgTemp1 = new BigDecimal(0);
						BigDecimal Tmin = new BigDecimal(999);
						BigDecimal Tmax = new BigDecimal(0);
						int m = 0;
						for(m = 0;m<probe_cd.length;m++){
							if(probe_cd[m].equals("tempLeft1")){
								insideAvgTemp1 = insideAvgTemp1.add(tSBMonitorCurr.getInsideTemp1());
								Tmin = Tmin.min(tSBMonitorCurr.getInsideTemp1());
								Tmax = Tmax.max(tSBMonitorCurr.getInsideTemp1());
							}else if(probe_cd[m].equals("tempLeft2")){
								insideAvgTemp1 = insideAvgTemp1.add(tSBMonitorCurr.getInsideTemp2());
								Tmin = Tmin.min(tSBMonitorCurr.getInsideTemp2());
								Tmax = Tmax.max(tSBMonitorCurr.getInsideTemp2());
							}else if(probe_cd[m].equals("tempMiddle1")){
								insideAvgTemp1 = insideAvgTemp1.add(tSBMonitorCurr.getInsideTemp3());
								Tmin = Tmin.min(tSBMonitorCurr.getInsideTemp3());
								Tmax = Tmax.max(tSBMonitorCurr.getInsideTemp3());
							}else if(probe_cd[m].equals("tempMiddle2")){
								insideAvgTemp1 = insideAvgTemp1.add(tSBMonitorCurr.getInsideTemp4());
								Tmin = Tmin.min(tSBMonitorCurr.getInsideTemp4());
								Tmax = Tmax.max(tSBMonitorCurr.getInsideTemp4());
							}else if(probe_cd[m].equals("tempRight1")){
								insideAvgTemp1 = insideAvgTemp1.add(tSBMonitorCurr.getInsideTemp5());
								Tmin = Tmin.min(tSBMonitorCurr.getInsideTemp5());
								Tmax = Tmax.max(tSBMonitorCurr.getInsideTemp5());
							}else if(probe_cd[m].equals("tempRight2")){
								insideAvgTemp1 = insideAvgTemp1.add(tSBMonitorCurr.getInsideTemp6());
								Tmin = Tmin.min(tSBMonitorCurr.getInsideTemp6());
								Tmax = Tmax.max(tSBMonitorCurr.getInsideTemp6());
							}
						}
						mLogger.info("tempPlus="+insideAvgTemp1.toString()+" probe_count=" +m + " probe_max=" + Tmax + " probe_min=" + Tmin);
						if(m > 0){
							insideAvgTemp = insideAvgTemp1.divide(new BigDecimal(m),2);
						}
						pointTempDiff = Tmax.subtract(Tmin);
					}
					tSBMonitorCurr.setPointTempDiff(pointTempDiff);
					tSBMonitorCurr.setInsideAvgTemp(insideAvgTemp);
					if (set_temp != null) {
						tSBMonitorCurr.setInsideSetTemp(PubFun
								.getBigDecimalData(set_temp.toString()));
					}
					if (high_alarm_temp != null) {
						if(temp_cpsation.equals("1")){
							temp_cordon = temp_cordon.add(T6) ;
							tSBMonitorCurr.setHighAlarmTemp(
									temp_cordon.max(PubFun.getBigDecimalData(high_alarm_temp.toString()))
									);
						}else{
							tSBMonitorCurr.setHighAlarmTemp(PubFun.getBigDecimalData(high_alarm_temp.toString()));
						}
					}
					if (low_alarm_temp != null) {
						tSBMonitorCurr.setLowAlarmTemp(PubFun
								.getBigDecimalData(low_alarm_temp.toString()));
					}
					if (set_humidity != null) {
						tSBMonitorCurr.setTargetHumidity(PubFun
								.getBigDecimalData(set_humidity.toString()));
					}
					
					if(h1 != null && !"".equals(h1)){
						tSBMonitorCurr.setInsideHumidity(PubFun
								.getBigDecimalData(h1.toString()));
					}
					
					tSBMonitorCurr.setPowerStatus(Integer.parseInt(p));
					tSBMonitorCurr.setUpdateDatetime(curDate);
					SBMonitorHist tSBMonitorHist = new SBMonitorHist();
					tSBMonitorHist.setHouseId(tSBMonitorCurr.getHouseId());
					tSBMonitorHist.setFarmId(tSBMonitorCurr.getFarmId());
					tSBMonitorHist.setCollectDatetime(tSBBbfar.getDateTime());
					tSBMonitorHist.setDealStatus(0);
					tSBMonitorHist.setDateAge(tSBMonitorCurr.getDateAge());
					tSBMonitorHist.setInsideTemp1(tSBMonitorCurr.getInsideTemp1());
					tSBMonitorHist.setInsideTemp2(tSBMonitorCurr.getInsideTemp2());
					tSBMonitorHist.setInsideTemp3(tSBMonitorCurr.getInsideTemp3());
					tSBMonitorHist.setInsideTemp4(tSBMonitorCurr.getInsideTemp4());	
					tSBMonitorHist.setInsideTemp5(tSBMonitorCurr.getInsideTemp5());
					tSBMonitorHist.setInsideTemp6(tSBMonitorCurr.getInsideTemp6());
					tSBMonitorHist.setOutsideTemp(tSBMonitorCurr.getOutsideTemp());
					tSBMonitorHist.setInsideAvgTemp(insideAvgTemp);
					tSBMonitorHist.setUpdateDatetime(curDate);
					tSBMonitorHist.setInsideSetTemp(tSBMonitorCurr.getInsideSetTemp());
					tSBMonitorHist.setHighAlarmTemp(tSBMonitorCurr.getHighAlarmTemp());
					tSBMonitorHist.setLowAlarmTemp(tSBMonitorCurr.getLowAlarmTemp());
					tSBMonitorHist.setTargetHumidity(tSBMonitorCurr.getTargetHumidity());
					tSBMonitorHist.setInsideHumidity(tSBMonitorCurr.getInsideHumidity());
					tSBMonitorHist.setPointTempDiff(pointTempDiff);
					tSBMonitorHist.setPowerStatus(Integer.parseInt(p));
					HashMap<String, Object> mParas2 = new HashMap<String, Object>();
					mParas2.put("SBMonitorCurr", tSBMonitorCurr);
					mParas2.put("SBMonitorHist", tSBMonitorHist);
					mDeviceBbfReqManager.dealSave(mParas2);
					dealRes = "MTC response: upLoad Data Sucess!";
				}else{
					mLogger.info("该设备(" + keyid + ")没有取到相关的栋舍、日龄、目标温度等数据。");
					dealRes = "MTC response: upLoad Data Sucess!";
				}
			} else {
				mLogger.info("设备ID为空");
				dealRes = "设备数据为空，请输入正确数据。";
			}
		} catch (Throwable e) {
			e.printStackTrace();
			dealRes = "服务程序错误，请联系管理员。";
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		try {
			mLogger.info("Result:" + dealRes);
			response.getWriter().write(dealRes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mLogger.info("=====Now end executing DeviceReqController.dataUploadBBF");
	}

	@RequestMapping("/showData")
	public void showData(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String tRes = "";
		mLogger.info("=====Now start executing DeviceReqController.showData");
		try {
			String tSQL = "SELECT keyid,date_format(date_time,'%Y-%m-%d %H:%i:%s') as date_time,t1,t2,t3,t4,t5,t6,h1,h2,p,station,gps,bak1,bak2 from s_b_bbfar order by id desc  LIMIT 100 ";
			mLogger.info("DeviceReqController.showData.sql"+tSQL);
			List aList = mBaseQueryService.selectMapByAny(tSQL);
			ObjectMapper mapper = new ObjectMapper();
			tRes = mapper.writeValueAsString(aList);
			System.out.println("tRes=" + tRes);
			/** 业务处理结束 **/
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(tRes);
		mLogger.info("=====Now end executing DeviceReqController.showData");
	}
}
