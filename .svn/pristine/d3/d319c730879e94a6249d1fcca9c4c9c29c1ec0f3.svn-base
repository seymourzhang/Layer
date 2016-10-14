/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.sql.Date;
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
import com.mtc.app.service.SDUserOperationService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;

/**
 * 
 * 获取小鸡体重信息
 * 
 * select操作均在controller中直接进行
 * 
 * update，insert，delete在manager中进行
 * 
 * @author lx
 * 
 */
@Controller
@RequestMapping("/layer_report")
public class LayerRepChicWeightController {
	private static Logger mLogger = Logger
			.getLogger(LayerRepChicWeightController.class);

	@Autowired
	private BaseQueryService baseQueryService;
	
	@Autowired
	private SDUserOperationService operationService;
	
	/**
	 * 获取小鸡重量
	 */
	@RequestMapping("/queryChickenWeight")
	public void queryChickenWeight(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====Now start executing LayerRepChicWeightController.queryChickenWeight");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("==========体重操作信息：查询，导入完毕");
			mLogger.info("jsonObject=" + jsonObject.toString());
			//** 业务处理开始，查询、增加、修改、或删除 **//
			int userid = jsonObject.optInt("id_spa");
			
			operationService.insert(SDUserOperationService.MENU_DATAANALYSIS_WEIGHT, SDUserOperationService.OPERATION_SELECT, userid);
			
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int FarmBreedId = tHouseJson.getInt("FarmBreedId");
			int FarmId = tHouseJson.getInt("FarmId");
			String ViewType = tHouseJson.getString("ViewType");
			//01周显示    02 日显示
			List<HashMap<String,Object>>  ff  =null;
			String SQL = null;
			if(ViewType.equals("02")){
				 SQL =" ";
			}else if(ViewType.equals("01")){
				 
			}
			SQL =" SELECT growth_date, week_age AS agw , house_id ,  s_f_getHouseName(house_id) AS HouseName ,  ROUND(AVG(CASE  WHEN cur_weight = 0.00 THEN NULL ELSE cur_weight END), 1) AS layrate "
			 		+ " FROM s_b_layer_breed_detail AS bd LEFT JOIN s_b_layer_house_breed AS hb"
			 		+ " ON bd.`house_breed_id` = hb.`id`  WHERE  hb.`farm_breed_id`= "+ FarmBreedId +"  "
			 				+ "  GROUP BY week_age , house_id  "
			 				+ "UNION ALL SELECT growth_date, week_age AS agw , IFNULL(NULL, '01') AS house_id, "
			 				+ "  IFNULL(NULL,'全场平均') AS HouseName ,ROUND((AVG(CASE  WHEN cur_weight = 0.00 THEN NULL ELSE cur_weight END)), 1) AS layrate  "
			 				+ " FROM s_b_layer_breed_detail AS bd LEFT JOIN s_b_layer_house_breed AS hb"
			 				+ " ON bd.`house_breed_id` = hb.`id`  WHERE  hb.`farm_breed_id`="+ FarmBreedId +"   "
			 						+ "  GROUP BY week_age  ORDER BY house_id , agw ;";
		// AND growth_date <= CURDATE() 
			 mLogger.info("=========LayerRepChicWeightController.queryChickenWeight.SQL=" + SQL);
			 ff = baseQueryService.selectMapByAny(SQL);
		     if(ff.size()>0){
		    	JSONArray  AvgWeight = new JSONArray();
		    	JSONArray  HouseDatas = new JSONArray();
		    	JSONObject tJSONObject = new JSONObject();
		    	JSONArray  xAxis = new JSONArray();
		    	int i=0;
		    	boolean xboolean =true;
		    	// 今天
	    		//String today = DateUtil.toDateString(new Date(System.currentTimeMillis()));
	    		//long curTime = DateUtil.parser(today, DateUtil.DATE_FORMAT).getTime();
	    		
		    	for (HashMap<String, Object> hashMap : ff) {
		    		Object agw = hashMap.get("agw");
		    		Date growth_date = (Date)hashMap.get("growth_date");
		    		
		    		long growthTime = growth_date.getTime();
		    		
		   		    Object layrate = hashMap.get("layrate");
		   		    // 时间还未到
		   		    if(growthTime > System.currentTimeMillis()){
		   		    	if(layrate==null){
			   		    	layrate = "-";
			   		    }
		   		    }
		   		    if(layrate==null){
		   		    	layrate = 0;
		   		    }
		   		    Object house_id = hashMap.get("house_id");
		    		Object HouseName = hashMap.get("HouseName");
		    		xAxis.put(agw);
		    		HouseDatas.put(layrate);
		    		boolean outcome =false;
		    		try {
		    			 outcome = !house_id.equals(ff.get(i+1).get("house_id"));
					} catch (Exception e) {
						 outcome = true;
					}
		   		    if(outcome){
			   		     tJSONObject =new JSONObject();  
			   		     tJSONObject.put("HouseId", house_id);
			   		     tJSONObject.put("HouseName", HouseName);
			   		     tJSONObject.put("HouseDatas", HouseDatas);
			   		     AvgWeight.put(tJSONObject);
			   		     HouseDatas = new JSONArray();
		   		    }
		   		    if(outcome&&xboolean){
		    			 resJson.put("xAxis",xAxis);
		    			 xAxis = new JSONArray();
		    			 xboolean =false;
			   		  }
		   		    i++;
		    	}
		    	resJson.put("Result","Success");	
		    	resJson.put("FarmBreedId",FarmBreedId);	
		    	resJson.put("FarmId",FarmId);	
		    	resJson.put("ViewType",ViewType);	
		    	resJson.put("AvgWeight",AvgWeight);	
		    }else{
		    	resJson.put("Result","Fail");
		    }
			dealRes = Constants.RESULT_SUCCESS ;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", "数据异常");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL ;
		}
		DealSuccOrFail.dealApp(request,response,dealRes,resJson);
		mLogger.info("=====Now end executing LayerRepChicWeightController.queryChickenWeight");
	}

}
