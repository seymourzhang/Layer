/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.mtc.app.service.BaseQueryService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;

/**
 * @ClassName: RepTempReqController
 * @Description:
 * @Date 2016-1-6 下午5:40:42
 * @Author Shao Yao Yu
 * 
 */
@Controller
@RequestMapping("/rep/TempCurve")
public class RepTempReqController {

	private static Logger mLogger = Logger.getLogger(RepTempReqController.class);

	@Autowired
	private BaseQueryService mBaseQueryService;

	@RequestMapping("/TempCurveReq")
	public void TempCurveReq(HttpServletRequest request,HttpServletResponse response) {
		mLogger.info("=======Now start executing RepTempReqController.TempCurveReq");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("updateFarm.para=" + paraStr);
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());

			String tErrorContent = "Null";

			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject params = jsonobject.optJSONObject("params");
			int FarmBreedId = params.optInt("FarmBreedId");
			int HouseId = params.optInt("HouseId");
			String DataType = params.optString("DataType");
			String ReqFlag = params.optString("ReqFlag");
			String DataRange = params.optString("DataRange");
			String data_date = "null" ;
			String data_age = "null";
			JSONArray TempDatas = new JSONArray();
			List<HashMap<String, Object>> listMap = null;
			String tSQL = "";
			
			if (DataType.equals("01")) {
				tSQL = "SELECT (CASE when a.growth_date > curdate() then 'N' else 'Y' end) as dataflag,'Null'as data_age,'Null'as data_date,b.house_id,concat(date_format(a.growth_date,'%m-%d'),'(',a.age,')') as x_axis,tData2.avgtempLeft1,tData2.avgtempLeft2,tData2.avgtempMiddle1,tData2.avgtempMiddle2,tData2.avgtempRight1,tData2.avgtempRight2 ,tData2.avgoutsidetemp "
					 + "FROM s_b_breed_detail a "
					 + "left join s_b_house_breed b on b.id = a.house_breed_id "
					 + "left join( SELECT tData.timeId,round(avg(tData.inside_temp1),1) as avgtempLeft1,round(avg(tData.inside_temp2),1) as avgtempLeft2,round(avg(tData.inside_temp3),1) as avgtempMiddle1 ,"
								   + "round(avg(tData.inside_temp4),1) as avgtempMiddle2,round(avg(tData.inside_temp5),1) as avgtempRight1,round(avg(tData.inside_temp6),1) as avgtempRight2 ,round(AVG(tData.outside_temp),1) AS avgoutsidetemp "
								   + "from ( SELECT date_format(collect_datetime, '%Y-%m-%d') AS timeId, a.* "
								   			+ "FROM s_b_monitor_hist a WHERE 1 = 1 "
								   				+ "AND a.house_id = " + HouseId + " "
								   				+ "AND exists(SELECT 1 FROM s_b_house_breed sbb "
								   							+ "WHERE sbb.farm_breed_id = " + FarmBreedId + " "
								   							+ "AND sbb.house_id = a.house_id "
								   							+ "AND a.collect_datetime BETWEEN sbb.place_date AND date_add(sbb.place_date, INTERVAL 60 DAY)"
								   				+ ")"
								   + ") tData group by tData.timeId order by tData.timeId "
						+ ") as tData2 on tData2.timeId = date_format(a.growth_date,'%Y-%m-%d') "
						+ "where 1=1 "
						+ "and a.age <= 45 "
						+ "and b.house_id = " + HouseId + " "
						+ "and b.farm_breed_id =" + FarmBreedId + " "
						+ "and exists(SELECT 1 from s_b_house_breed sbh where sbh.id = a.house_breed_id and a.growth_date <= ifnull(b.market_date,now())) ";
			}else if (DataType.equals("02")) {
				if (ReqFlag.equals("N")) {
					DataRange = "NULL";
					String tDateSql = "SELECT s_f_getRecentAgeDateByHouseId("+HouseId+", '"+DataRange+"',"+FarmBreedId+") ";
					DataRange = mBaseQueryService.selectStringByAny(tDateSql);
				}else{
					if (DataRange.length() != 10) {
						tErrorContent = "请传入正确的日期参数（YYYY-MM-DD）。";
					}
				}
				tSQL = "SELECT (CASE WHEN concat(tData3.data_date,' ',CODE) > date_format(adddate(now(), INTERVAL 30 MINUTE), '%Y-%m-%d %H:%i') THEN 'N' ELSE 'Y' END) AS dataflag, "
							+ "CODE AS x_axis," + HouseId + " AS house_id,"
							+ "tData3.data_date as data_date,"
							+ "concat('(日龄：',s_f_getDayAgeByHouseId(" + HouseId+ ",tData3.data_date),')')  AS data_age,"
							+ "tData2.avgtempLeft1,tData2.avgtempLeft2,tData2.avgtempMiddle1,tData2.avgtempMiddle2,tData2.avgtempRight1,tData2.avgtempRight2,tData2.avgoutsidetemp"
						+ " FROM s_b_constants sc "
						+ "	LEFT JOIN("
								+ " SELECT case when tData.timeId = '00:00' then '24:00' else tData.timeId end as timeId,tData.house_id, tData.date_age,round(AVG(tData.inside_temp1),1) AS avgtempLeft1,round(AVG(tData.inside_temp2),1) AS avgtempLeft2,round(AVG(tData.inside_temp3),1) AS avgtempMiddle1 ,round(AVG(tData.inside_temp4),1) AS avgtempMiddle2,round(AVG(tData.inside_temp5),1) AS avgtempRight1,round(AVG(tData.inside_temp6),1) AS avgtempRight2 ,round(AVG(tData.outside_temp),1) AS avgoutsidetemp "
								+ " FROM ( SELECT  (CASE WHEN DATE_FORMAT(collect_datetime, '%i') BETWEEN '00' AND '30' THEN CONCAT(DATE_FORMAT(collect_datetime, '%H'),':30') ELSE CONCAT(DATE_FORMAT(adddate(collect_datetime,INTERVAL 1 HOUR), '%H'),':00') END) AS timeId,a.* "
									+ " FROM s_b_monitor_hist a "
									+ " WHERE  a.house_id = "+ HouseId
									+ " and DATE_FORMAT(a.collect_datetime, '%Y-%m-%d') = '"+ DataRange + "' "
									+ ") tData GROUP BY tData.timeId ORDER BY tData.timeId"
						+ ") AS tData2 ON tData2.timeId = sc.code "
						+ "LEFT JOIN (select '"+ DataRange + "' as data_date) AS tData3 on 1=1"
						+ " WHERE codetype = 'HalfHour' ";
			} else if (DataType.equals("03")) {
				String DataRangeStart = "";
				String DataRangeEnd = "";
				if (ReqFlag.equals("N")) {
					String tarTime = "";
					if(DataRange.equals(PubFun.getCurrentDate())){
						String tCurTime = PubFun.getCurrentTime();
						if(tCurTime.substring(3, 5).compareTo("30")>0){
							tarTime = tCurTime.substring(0,2) + ":30";
						}else{
							tarTime = tCurTime.substring(0,2) + ":00";
						}
					}else{
						tarTime = "00:00";
					}
					DataRangeStart = DataRange + " " + tarTime ;
					
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					Date date = formatter.parse(DataRangeStart);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.MINUTE, 30);
					DataRangeEnd = formatter.format(calendar.getTime());
					
				}else{
					DataRangeEnd = DataRange ;
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					Date date = formatter.parse(DataRangeEnd);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.MINUTE, -30);
					DataRangeStart = formatter.format(calendar.getTime());
					
					date = formatter.parse(DataRangeEnd);
					DataRangeEnd = formatter.format(date);
				}
				if (DataRangeStart.length() != 16 || DataRangeEnd.length() != 16) {
					tErrorContent = "DataRange日期参数有误";
				}
				
				data_date = DataRangeStart.substring(0,10);
				String tHourValue = DataRangeStart.substring(11, 13);
				String codeType = "";
				if (DataRangeStart.endsWith("00")) {
					codeType = "PerMinute1";
				} else {
					codeType = "PerMinute2";
				}
				
				tSQL = "SELECT 'Y' as dataflag ,CONCAT('"+ tHourValue+ ":', CASE when tData.timeId = '00' then '60' else tData.timeId end) AS x_axis,'Null'as data_date,concat('(日龄：',tData.date_age,')')  AS data_age,tData.house_id,round(AVG(tData.inside_temp1),1) AS avgtempLeft1,round(AVG(tData.inside_temp2),1) AS avgtempLeft2,round(AVG(tData.inside_temp3),1) AS avgtempMiddle1 ,"
							+ "round(AVG(tData.inside_temp4),1) AS avgtempMiddle2,round(AVG(tData.inside_temp5),1) AS avgtempRight1,round(AVG(tData.inside_temp6),1) AS avgtempRight2 ,round(AVG(tData.outside_temp),1) AS avgoutsidetemp "
						+ "FROM (SELECT DATE_FORMAT(adddate(a.collect_datetime,INTERVAL 1 MINUTE), '%i') AS timeId,a.* "
							+ "FROM s_b_monitor_hist a WHERE 1=1 "
								+ "and a.house_id = " + HouseId + " "
								+ "AND a.collect_datetime >= STR_TO_DATE('"+ DataRangeStart+ "', '%Y-%m-%d %H:%i' ) "
								+ "AND a.collect_datetime  < STR_TO_DATE( '"+ DataRangeEnd+ "',  '%Y-%m-%d %H:%i' ) "
							 + ") tData GROUP BY tData.timeId "
						+ "UNION ALL "
							+ "SELECT  'N' as dataflag , CONCAT('"+ tHourValue+ ":', sc.code) AS x_axis,NULL,NULL, NULL,  NULL,  NULL,  NULL,  NULL,  NULL,  NULL,NULL FROM s_b_constants sc  WHERE 1=1 "
								+ "AND sc.codetype = '"+ codeType+ "' "
								+ "AND CODE > (SELECT right(concat('0', ifnull(DATE_FORMAT(MAX(sbh.collect_datetime), '%i') + 1,0)), 2) FROM s_b_monitor_hist sbh  WHERE 1=1 "
								+ "AND sbh.house_id = " + HouseId + " "
								+ "AND sbh.collect_datetime BETWEEN "
											+ "STR_TO_DATE('"+ DataRangeStart+ "', '%Y-%m-%d %H:%i' ) AND "
											+ "STR_TO_DATE('"+ DataRangeEnd + "','%Y-%m-%d %H:%i' )) "
						+ " ORDER BY x_axis ";
			}else{
				tErrorContent = "DataType参数有误";
			}
			mLogger.info("DataType=" + DataType + " DataRange="+ DataRange);
			mLogger.info("=======RepTempReqController.TempCurveReq.sql=" + tSQL);
			
			if(tErrorContent.equals("Null")){
				listMap = mBaseQueryService.selectMapByAny(tSQL);
				if (listMap.size() > 0) {
					JSONArray avgtempLeft1 = new JSONArray();
					JSONArray avgtempLeft2 = new JSONArray();
					JSONArray avgtempMiddle1 = new JSONArray();
					JSONArray avgtempMiddle2 = new JSONArray();
					JSONArray avgtempRight1 = new JSONArray();
					JSONArray avgtempRight2 = new JSONArray();
					JSONArray avgoutsideTemp = new JSONArray();
					JSONArray xAxis = new JSONArray();
					for (HashMap<String, Object> hashMap : listMap) {
						Object x_axis = hashMap.get("x_axis");
						if (x_axis == null) {
							x_axis = 0;
						}
						
						if(x_axis.toString().endsWith("60")){
							int tHor = Integer.parseInt(x_axis.toString().substring(0, 2)) + 1;
							x_axis = PubFun.fillLeftChar(tHor, '0', 2) + ":00";
						}
						
						Object avgtempLeft11 = hashMap.get("avgtempLeft1");
						if (avgtempLeft11 == null) {
							avgtempLeft11 = 0;
						}
						Object avgtempLeft22 = hashMap.get("avgtempLeft2");
						if (avgtempLeft22 == null) {
							avgtempLeft22 = 0;
						}
						Object avgtempMiddle11 = hashMap.get("avgtempMiddle1");
						if (avgtempMiddle11 == null) {
							avgtempMiddle11 = 0;
						}
						Object avgtempMiddle22 = hashMap.get("avgtempMiddle2");
						if (avgtempMiddle22 == null) {
							avgtempMiddle22 = 0;
						}
						Object avgtempRight11 = hashMap.get("avgtempRight1");
						if (avgtempRight11 == null) {
							avgtempRight11 = 0;
						}
						Object avgtempRight22 = hashMap.get("avgtempRight2");
						if (avgtempRight22 == null) {
							avgtempRight22 = 0;
						}
						Object avgoutsideTemp1 = hashMap.get("avgoutsidetemp");
						if (avgoutsideTemp1 == null) {
							avgoutsideTemp1 = 0;
						}
						xAxis.put(x_axis);
						if(hashMap.get("dataflag").equals("N")){
							continue;
						}
						avgtempLeft1.put(avgtempLeft11);
						avgtempLeft2.put(avgtempLeft22);
						avgtempMiddle1.put(avgtempMiddle11);
						avgtempMiddle2.put(avgtempMiddle22);
						avgtempRight1.put(avgtempRight11);
						avgtempRight2.put(avgtempRight22);
						avgoutsideTemp.put(avgoutsideTemp1);
						if(DataType.equals("02")){
							data_date = hashMap.get("data_date")!=null?hashMap.get("data_date").toString():"Null";
						}
						if(hashMap.get("data_age") != null){
							data_age = hashMap.get("data_age").toString();
						}
					}

					resJson.put("xAxis", xAxis);
					JSONObject tJSONObject = new JSONObject();
					tJSONObject.put("TempAreaCode", "tempLeft1");
					tJSONObject.put("TempAreaName", "前区温度1");
					tJSONObject.put("TempCurve", avgtempLeft1);
					TempDatas.put(tJSONObject);
					tJSONObject = new JSONObject();
					tJSONObject.put("TempAreaCode", "tempLeft2");
					tJSONObject.put("TempAreaName", "前区温度2");
					tJSONObject.put("TempCurve", avgtempLeft2);
					TempDatas.put(tJSONObject);
					tJSONObject = new JSONObject();
					tJSONObject.put("TempAreaCode", "tempMiddle1");
					tJSONObject.put("TempAreaName", "中区温度");
					tJSONObject.put("TempCurve", avgtempMiddle1);
					TempDatas.put(tJSONObject);
					// tJSONObject = new JSONObject();
					// tJSONObject.put("TempAreaCode", "tempMiddle2");
					// tJSONObject.put("TempAreaName", "中区温度2");
					// tJSONObject.put("TempCurve", avgtempMiddle2);
					// TempDatas.put(tJSONObject);
					tJSONObject = new JSONObject();
					tJSONObject.put("TempAreaCode", "tempRight1");
					tJSONObject.put("TempAreaName", "后区温度1");
					tJSONObject.put("TempCurve", avgtempRight1);
					TempDatas.put(tJSONObject);
					tJSONObject = new JSONObject();
					tJSONObject.put("TempAreaCode", "tempRight2");
					tJSONObject.put("TempAreaName", "后区温度2");
					tJSONObject.put("TempCurve", avgtempRight2);
					TempDatas.put(tJSONObject);
					tJSONObject = new JSONObject();
					tJSONObject.put("TempAreaCode", "out_temp");
					tJSONObject.put("TempAreaName", "室外温度");
					tJSONObject.put("TempCurve", avgoutsideTemp);
					TempDatas.put(tJSONObject);
					resJson.put("TempDatas", TempDatas);
					resJson.put("HouseId", HouseId);
					resJson.put("DataDate", data_date);
					resJson.put("data_age", data_age);
					resJson.put("FarmBreedId", FarmBreedId);
					resJson.put("Result", "Success");
					dealRes = Constants.RESULT_SUCCESS;
				} else {
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", "请求参数错误");
					dealRes = Constants.RESULT_SUCCESS;
				}
			}else{
				resJson.put("Result", "Fail");
				resJson.put("ErrorMsg", tErrorContent);
				dealRes = Constants.RESULT_SUCCESS;
			}
			/** 业务处理结束 **/
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
		mLogger.info("=====Now end executing RepTempReqController.TempCurveReq");
	}
}
