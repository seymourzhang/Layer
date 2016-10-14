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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.biz.EggSellsReqManager;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;

/**
 * 
 * 只鸡累计产蛋比
 * 
 * 
 * @author lx
 * 
 */
@Controller
@RequestMapping("/layer_report")
public class LayerRepChickenEggsController {
	private static Logger mLogger = Logger
			.getLogger(LayerRepChickenEggsController.class);

	@Autowired
	private EggSellsReqManager eggSellsReqManager;
	
	@Autowired
	private SDUserOperationService operationService;
	
	/**
	 * 获取只鸡产蛋比
	 */
	@RequestMapping("/queryChickenEggs")
	public void queryChickenEggs(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====Now start executing LayerRepChickenEggsController.queryChickenEggs");
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
			
			operationService.insert(SDUserOperationService.MENU_DATAANALYSIS_CHICKEN_EGGS, SDUserOperationService.OPERATION_SELECT, userid);
			
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int FarmBreedId = tHouseJson.getInt("FarmBreedId");
			int FarmId = tHouseJson.getInt("FarmId");
			String ViewType = tHouseJson.getString("ViewType");
			//01周显示    02 日显示
			List<HashMap<String,Object>>  ff  =null;
			//String SQL = null;
			if(ViewType.equals("02")){
				ff = eggSellsReqManager.getChickenEggsByDay(FarmBreedId, FarmId);
			}else if(ViewType.equals("01")){
				ff = eggSellsReqManager.getChickenEggsByWeek(FarmBreedId, FarmId);
			}
			
			// mLogger.info("SQL=" + SQL);
			 
		     if(ff.size()>0){
		    	JSONArray  yDatas = new JSONArray();
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
		    		Object layrate = hashMap.get("layrate");
		    		/*Date growth_date = (Date)hashMap.get("growth_date");
		    		
		    		long growthTime = growth_date.getTime();
		    		
		   		    
		   		    // 时间还未到
		   		    if(growthTime > System.currentTimeMillis()){
		   		    	if(layrate==null){
			   		    	layrate = "-";
			   		    }
		   		    }*/
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
			   		     yDatas.put(tJSONObject);
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
		    	mLogger.info("yDatas:" + yDatas);
		    	resJson.put("yDatas",yDatas);	
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
		mLogger.info("=====Now end executing LayerRepChickenEggsController.queryChickenEggs");
	}

}
