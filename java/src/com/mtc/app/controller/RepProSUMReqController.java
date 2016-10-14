/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mtc.app.service.SDUserOperationService;
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
 * @ClassName: RepFCRReqController
 * @Description:
 * @Date 2016年4月8日 下午12:06:09
 * @Author 邵耀雨
 * 
 */
@Controller
@RequestMapping("/rep/ProSUM")
public class RepProSUMReqController {
	private static Logger mLogger = Logger.getLogger(RepProSUMReqController.class);

	@Autowired
	private BaseQueryService mBaseQueryService;

	@Autowired
	private SDUserOperationService sSDUserOperationService;

	@RequestMapping("/SUMReq")
	public void SUMReq(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=======Now start executing RepProSUMReqController.SUMReq");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("updateFarm.para=" + paraStr);
			JSONObject jsonobject = new JSONObject(paraStr);
            int userId = jsonobject.optInt("id_spa");
			mLogger.info("jsonObject=" + jsonobject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			 JSONObject params = jsonobject.optJSONObject("params");
			 String CompareType = params.optString("CompareType");
			 if(CompareType == null || CompareType.equals("")){
				 CompareType = "01";
			 }
				JSONArray Headers = new JSONArray();
				JSONArray TableDatas = new JSONArray();
				JSONObject TableDatas1 = new JSONObject();
				JSONObject TableDatas2 = new JSONObject();
				JSONObject TableDatas3 = new JSONObject();
				JSONObject TableDatas4 = new JSONObject();
				JSONObject TableDatas5 = new JSONObject();
				JSONObject TableDatas6 = new JSONObject();
				JSONObject TableDatas7 = new JSONObject();
				Headers.put("");
				TableDatas1.put("Index1", "饲养天数");
				TableDatas2.put("Index1", "毛鸡数量");
				TableDatas3.put("Index1", "总重量");
				TableDatas4.put("Index1", "只均重");
				TableDatas5.put("Index1", "成活率");
				TableDatas6.put("Index1", "料肉比");
				TableDatas7.put("Index1", "欧洲指数");
				int j = 2 ;
			 // 栋舍对比
			 if(CompareType.equals("01")){
				 int FarmBreedId = params.optInt("FarmBreedId");
				 String sql = "SELECT house_id, place_num,acc_feed,sd.age,cur_amount, "
				         +"cur_weight*cur_amount AS con_weight,cur_weight,  ROUND(cur_amount/(acc_cd+cur_amount),2) AS surRate  , "
						 +"IFNULL(ROUND(acc_feed * 1000/(cur_amount*cur_weight),2),0) AS fcr  "
						 +"FROM s_b_breed_detail AS sd LEFT JOIN s_b_house_breed AS hb ON sd.house_breed_id =  hb.id "
				         +"WHERE hb.farm_breed_id = "+FarmBreedId+"  AND  DATE_FORMAT(hb.market_date,'%Y%m%d') = DATE_FORMAT( sd.growth_date,'%Y%m%d')  "
				         +"UNION ALL SELECT null, SUM( place_num), SUM(acc_feed), ROUND( AVG(sd.age),0) as age,  SUM(cur_amount),SUM(cur_weight*cur_amount ) AS con_weight,ROUND (AVG(cur_weight),2),"
				         +"ROUND(AVG(cur_amount/(acc_cd+cur_amount)),2) AS surRate, ROUND(AVG(IFNULL(acc_feed * 1000/(cur_amount*cur_weight),0)),2)AS fcr  "
				         +"FROM s_b_breed_detail AS sd LEFT JOIN s_b_house_breed AS hb ON sd.house_breed_id =  hb.id "
				         +"WHERE hb.farm_breed_id = "+FarmBreedId+"  AND DATE_FORMAT(hb.market_date,'%Y%m%d') = DATE_FORMAT( sd.growth_date,'%Y%m%d') "; 
				 mLogger.info("========RepProSUMReqController.SUMReq.SQL=" + sql);
			     List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(sql);
				 if(toutcome.size()>1){
					for (int i = 0; i < toutcome.size(); i++) {
						String Index = "Index"+j;
						Object house_id =   toutcome.get(i).get("house_id");
						Headers.put(house_id);
						Object age1 =   toutcome.get(i).get("age");
						Integer age = Integer.parseInt(age1.toString());
						TableDatas1.put(Index,age1);
						Object place_num1 =	 toutcome.get(i).get("place_num");
						int place_num = Integer.parseInt(String.valueOf(place_num1));
						TableDatas2.put(Index,place_num1);
						String con_weight1 = toutcome.get(i).get("con_weight").toString();
						Double con_weight = Double.parseDouble(con_weight1);
						TableDatas3.put(Index,con_weight1);
						String cur_amount1 =  toutcome.get(i).get("cur_amount").toString();
						int cur_amount = Integer.parseInt(String.valueOf(cur_amount1));
						String cur_weight1 =  toutcome.get(i).get("cur_weight").toString();
						Double cur_weight = Double.parseDouble(String.valueOf(cur_weight1));
						TableDatas4.put(Index,cur_weight);
						String surRate = toutcome.get(i).get("surRate").toString();
						TableDatas5.put(Index,surRate);
						String fcr1 = toutcome.get(i).get("fcr").toString();
						TableDatas6.put(Index,fcr1);
						String acc_feed1 = toutcome.get(i).get("acc_feed").toString();
						Double acc_feed = Double.parseDouble(acc_feed1);
						String  EuropIndexValue= BreedBatchReqController.getEuropIndexValue(con_weight, cur_amount, place_num, acc_feed, age);
						TableDatas7.put(Index,EuropIndexValue);
						j++;
					}
					Headers.remove(Headers.length()-1);
					Headers.put("全场平均");
					TableDatas.put(TableDatas1);
					TableDatas.put(TableDatas2);
					TableDatas.put(TableDatas3);
					TableDatas.put(TableDatas4);
					TableDatas.put(TableDatas5);
					TableDatas.put(TableDatas6);
					TableDatas.put(TableDatas7);
				 }
				 resJson.put("Result", "Success");
				 resJson.put("Headers", Headers);
				 resJson.put("FarmBreedId", FarmBreedId);			
				 resJson.put("TableDatas", TableDatas);	
			 // 批次对比	 
			 }else if(CompareType.equals("02")){
				 int HouseId = params.optInt("HouseId");
				 String sql = "SELECT  farm_breed_id, place_num,acc_feed,hb.house_id,sd.age,sd.house_breed_id,cur_amount, "
				         +"cur_weight*cur_amount AS con_weight,cur_weight, cur_amount/(acc_cd+cur_amount) AS surRate , "
						 +"IFNULL(ROUND(acc_feed * 1000/(cur_amount*cur_weight),2),0) AS fcr  "
						 +"FROM s_b_breed_detail AS sd LEFT JOIN s_b_house_breed AS hb ON sd.house_breed_id =  hb.id "
				         +"WHERE hb.house_id = "+HouseId+"  AND DATE_FORMAT(hb.market_date,'%Y%m%d') = DATE_FORMAT( sd.growth_date,'%Y%m%d') ";  
				 mLogger.info("=======RepProSUMReqController.SUMReq.SQL=" + sql);
				 List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(sql);
				 if(toutcome.size()>0){
					for (int i = 0; i < toutcome.size(); i++) {
						String Index = "Index"+j;
						Object farm_breed_id =   toutcome.get(i).get("farm_breed_id");
						Headers.put(farm_breed_id);
						Object age1 =   toutcome.get(i).get("age");
						Integer age = Integer.parseInt(age1.toString());
						TableDatas1.put(Index,age1);
						Object place_num1 =	 toutcome.get(i).get("place_num");
						int place_num = Integer.parseInt(String.valueOf(place_num1));
						TableDatas2.put(Index,place_num1);
						String con_weight1 = toutcome.get(i).get("con_weight").toString();
						Double con_weight = Double.parseDouble(con_weight1);
						TableDatas3.put(Index,con_weight1);
						String cur_amount1 =  toutcome.get(i).get("cur_amount").toString();
						int cur_amount = Integer.parseInt(String.valueOf(cur_amount1));
						String cur_weight1 =  toutcome.get(i).get("cur_weight").toString();
						Double cur_weight = Double.parseDouble(String.valueOf(cur_weight1));
						TableDatas4.put(Index,cur_weight);
						String surRate = toutcome.get(i).get("surRate").toString();
						TableDatas5.put(Index,surRate);
						String fcr1 = toutcome.get(i).get("fcr").toString();
						TableDatas6.put(Index,fcr1);
						String acc_feed1 = toutcome.get(i).get("acc_feed").toString();
						Double acc_feed = Double.parseDouble(acc_feed1);
						String  EuropIndexValue= BreedBatchReqController.getEuropIndexValue(con_weight, cur_amount, place_num, acc_feed, age);
						TableDatas7.put(Index,EuropIndexValue);
						j++;
					}
					TableDatas.put(TableDatas1);
					TableDatas.put(TableDatas2);
					TableDatas.put(TableDatas3);
					TableDatas.put(TableDatas4);
					TableDatas.put(TableDatas5);
					TableDatas.put(TableDatas6);
					TableDatas.put(TableDatas7);
				 }
				 resJson.put("Result", "Success");
				 resJson.put("Headers", Headers);
				 resJson.put("HouseId", HouseId);			
				 resJson.put("TableDatas", TableDatas);				
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
		mLogger.info("=====Now end executing RepProSUMReqController.SUMReq");
	}
}
