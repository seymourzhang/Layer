/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.service.BaseQueryService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.RequestWeather;
import com.mtc.common.util.constants.Constants;

/**
 * @ClassName: HomeReqController
 * @Description:
 * @Date 2015-11-26 下午2:44:04
 * @Author Shao Yao Yu
 * 
 */
@Component
@RequestMapping("/sys/home")
public class HomeReqController {

	private static Logger mLogger = Logger.getLogger(HomeReqController.class);

	@Autowired
	private BaseQueryService mBaseQueryService;

	@RequestMapping("/reqWeather")
	public void reqWeather(HttpServletRequest request,
			HttpServletResponse response) {
			mLogger.info("=======Now start executing HomeReqController.reqWeather");
			response.setCharacterEncoding("UTF-8");
			JSONObject resJson = new JSONObject();
			String dealRes = null;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("updateFarm.para=" + paraStr);
				JSONObject jsonobject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonobject.toString());
				/** 业务处理开始，查询、增加、修改、或删除 **/
				JSONObject params = jsonobject.getJSONObject("params");
				String add1code1 = params.getString("add1code");
				String add2code1 = params.getString("add2code");
				String add3code1 = params.getString("add3code");
				boolean correct = true;
				if(PubFun.isNull(add1code1) || PubFun.isNull(add2code1) || PubFun.isNull(add3code1) ){
					correct = false;
					resJson.put("ErrorMsg", "POST参数有误。");
				}
				if(correct){
					String tSQL = "SELECT a.name1,a.name2,a.name3,a.bak2 FROM s_d_weather_code a WHERE 1=1 "
							+ "AND a.name3 LIKE CONCAT('%',(SELECT b.short_name FROM s_d_area_china b WHERE b.id = "
							+ add3code1
							+ "),'%')AND a.name2 LIKE CONCAT('%',(SELECT b.short_name FROM s_d_area_china b WHERE b.id = "
							+ add2code1 + "),'%')";
					
					mLogger.info("========HomeReqController.reqWeather.sql:" + tSQL);
					List<HashMap<String, Object>> tTempInfo = mBaseQueryService.selectMapByAny(tSQL);
					
					if(tTempInfo != null && tTempInfo.size()==0){
						tSQL = "SELECT a.name1,a.name2,a.name3,a.bak2 FROM s_d_weather_code a WHERE 1=1 "
								+ "AND a.name3 LIKE CONCAT('%',(SELECT b.short_name FROM s_d_area_china b WHERE b.id = "
								+ add2code1
								+ "),'%')AND a.name2 LIKE CONCAT('%',(SELECT b.short_name FROM s_d_area_china b WHERE b.id = "
								+ add2code1 + "),'%')";
						mLogger.info("========HomeReqController.reqWeather.sql_again:" + tSQL);
						tTempInfo = mBaseQueryService.selectMapByAny(tSQL);
					}
					
					JSONObject tjsonObject = new JSONObject();
					JSONArray JSONArry = new JSONArray();
					if(tTempInfo != null && tTempInfo.size() > 0){
						String weatherResult = ""; 
						weatherResult = RequestWeather.requestWeather(tTempInfo.get(0).get("bak2").toString(),null, null);
						if (weatherResult != null && !"".equals(weatherResult)) {
							JSONObject jsonObject = new JSONObject(weatherResult);
							if (PubFun.getCurrentTime2().compareTo("180000") > 0) {
								JSONObject yjsonObject = new JSONObject();
								yjsonObject.put("day_temp", jsonObject.getJSONObject("f")
										.getJSONArray("f1").getJSONObject(0)
										.optString("fd"));
								yjsonObject.put("night_temp", jsonObject.getJSONObject("f")
										.getJSONArray("f1").getJSONObject(0)
										.optString("fd"));
								yjsonObject.put("day_speed",RequestWeather.paseWeatherDesc(
										"wind_speed",  jsonObject.getJSONObject("f")
										.getJSONArray("f1").getJSONObject(0)
										.getString("fh")));
								yjsonObject.put("day_wind",RequestWeather.paseWeatherDesc(
										"wind_direction", jsonObject.getJSONObject("f")
										.getJSONArray("f1").getJSONObject(0)
										.getString("ff")));
								yjsonObject.put("day_desc", RequestWeather.paseWeatherDesc(
										"weather_desc", jsonObject.getJSONObject("f")
												.getJSONArray("f1").getJSONObject(0)
												.getString("fb")));
								yjsonObject.put("night_desc", RequestWeather
										.paseWeatherDesc("weather_desc", jsonObject
												.getJSONObject("f").getJSONArray("f1")
												.getJSONObject(0).getString("fb")));
								yjsonObject.put("day_desc_png",
										jsonObject.getJSONObject("f").getJSONArray("f1")
												.getJSONObject(0).getString("fb"));
								JSONArry.put(yjsonObject);
							} else {
								JSONObject yjsonObject = new JSONObject();
								yjsonObject.put("day_temp", jsonObject.getJSONObject("f")
										.getJSONArray("f1").getJSONObject(0)
										.optString("fc"));
								yjsonObject.put("night_temp", jsonObject.getJSONObject("f")
										.getJSONArray("f1").getJSONObject(0)
										.optString("fd"));
								yjsonObject.put("day_speed",RequestWeather.paseWeatherDesc(
										"wind_speed",  jsonObject.getJSONObject("f")
										.getJSONArray("f1").getJSONObject(0)
										.getString("fg")));
								yjsonObject.put("night_speed",RequestWeather.paseWeatherDesc(
										"wind_speed",  jsonObject.getJSONObject("f")
										.getJSONArray("f1").getJSONObject(0)
										.getString("fh")));
								yjsonObject.put("day_wind",RequestWeather.paseWeatherDesc(
										"wind_direction", jsonObject.getJSONObject("f")
										.getJSONArray("f1").getJSONObject(0)
										.getString("fe")));
								yjsonObject.put("night_wind",RequestWeather.paseWeatherDesc(
										"wind_direction", jsonObject.getJSONObject("f")
										.getJSONArray("f1").getJSONObject(0)
										.getString("ff")));
								yjsonObject.put("day_desc", RequestWeather.paseWeatherDesc(
										"weather_desc", jsonObject.getJSONObject("f")
												.getJSONArray("f1").getJSONObject(0)
												.getString("fa")));
								yjsonObject.put("night_desc", RequestWeather
										.paseWeatherDesc("weather_desc", jsonObject
												.getJSONObject("f").getJSONArray("f1")
												.getJSONObject(0).getString("fb")));
								yjsonObject.put("day_desc_png",
										jsonObject.getJSONObject("f").getJSONArray("f1")
												.getJSONObject(0).getString("fb"));
								JSONArry.put(yjsonObject);
							}
							JSONObject ujsonObject = new JSONObject();
							ujsonObject.put("day_temp", jsonObject.getJSONObject("f")
									.getJSONArray("f1").getJSONObject(1).getInt("fc"));
							ujsonObject.put("night_temp", jsonObject.getJSONObject("f")
									.getJSONArray("f1").getJSONObject(1).getInt("fd"));
							ujsonObject.put("day_speed",RequestWeather.paseWeatherDesc(
									"wind_speed",  jsonObject.getJSONObject("f")
									.getJSONArray("f1").getJSONObject(1)
									.getString("fg")));
							ujsonObject.put("night_speed",RequestWeather.paseWeatherDesc(
									"wind_speed",  jsonObject.getJSONObject("f")
									.getJSONArray("f1").getJSONObject(1)
									.getString("fh")));
							ujsonObject.put("day_wind",RequestWeather.paseWeatherDesc(
									"wind_direction", jsonObject.getJSONObject("f")
									.getJSONArray("f1").getJSONObject(1)
									.getString("fe")));
							ujsonObject.put("night_wind",RequestWeather.paseWeatherDesc(
									"wind_direction", jsonObject.getJSONObject("f")
									.getJSONArray("f1").getJSONObject(1)
									.getString("ff")));
							ujsonObject.put("day_desc", RequestWeather.paseWeatherDesc(
									"weather_desc",
									jsonObject.getJSONObject("f").getJSONArray("f1")
											.getJSONObject(1).getString("fa")));
							ujsonObject.put("night_desc", RequestWeather.paseWeatherDesc(
									"weather_desc",
									jsonObject.getJSONObject("f").getJSONArray("f1")
											.getJSONObject(1).getString("fb")));
							ujsonObject.put("day_desc_png", jsonObject.getJSONObject("f")
									.getJSONArray("f1").getJSONObject(1).getString("fa"));
							JSONArry.put(ujsonObject);
							tjsonObject.put("weathercode", jsonObject.getJSONObject("c")
									.getString("c1"));
							tjsonObject.put("date", jsonObject.getJSONObject("f")
									.getString("f0"));
							resJson.put("weatherinfo", JSONArry);
					}
				}
				String tSQL2 = "SELECT (SELECT short_name from s_d_area_china where id = "+add1code1+") as name1,"
						+ " (SELECT short_name from s_d_area_china where id = "+add2code1+") as name2,"
						+ " (SELECT short_name from s_d_area_china where id = "+add3code1+") as name3" ;
				List<HashMap<String, Object>> nameInfo = mBaseQueryService.selectMapByAny(tSQL2);
				if(nameInfo != null && nameInfo.size()>0){
					tjsonObject.put("cityname1", nameInfo.get(0).get("name1"));
					tjsonObject.put("cityname2", nameInfo.get(0).get("name2"));
					tjsonObject.put("cityname3", nameInfo.get(0).get("name3"));
				}
				resJson.put("cityinfo", tjsonObject);
				mLogger.info("reutrnStr:" + resJson);
			}
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			dealRes = Constants.RESULT_FAIL;
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
		mLogger.info("=====Now end executing HomeReqController.reqWeather");
	}
}
