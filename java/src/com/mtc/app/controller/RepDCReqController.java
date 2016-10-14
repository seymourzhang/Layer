/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.util.ArrayList;
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
 * @ClassName: RepDCReqController
 * @Description: 
 * @Date 2016-1-4 下午6:00:55
 * @Author Shao Yao Yu
 * 成活率曲线
 */
@Controller
@RequestMapping("/rep/DCRate")
public class RepDCReqController {
		
	private static Logger mLogger =Logger.getLogger(RepDCReqController.class); 
		
	@Autowired
	private BaseQueryService mBaseQueryService;
	 
	 @RequestMapping("/DCRateReq")
		public void DCRateReq(HttpServletRequest request,HttpServletResponse response){
		   mLogger.info("=======Now start executing RepDCReqController.DCRateReq");
		   JSONObject resJson = new JSONObject();
		   String dealRes = null;
		   try {
		     String paraStr = PubFun.getRequestPara(request);
		     mLogger.info("updateFarm.para="+paraStr);
			 JSONObject jsonobject = new JSONObject(paraStr);
			 mLogger.info("jsonObject=" + jsonobject.toString());
			 /** 业务处理开始，查询、增加、修改、或删除 **/
			 JSONObject params = jsonobject.optJSONObject("params");
			 int userid = jsonobject.optInt("id_spa");
			 String CompareType = params.optString("CompareType");
			 if(CompareType == null || CompareType.equals("")){
				 CompareType = "01";
			 }
			 // 栋舍对比
			 if(CompareType.equals("01")){
				 int FarmBreedId = params.optInt("FarmBreedId");
				 
				 String sql = " SELECT (case when bd.growth_date > curdate() then 'N' else 'Y' end) as showFlag, hb.house_id,s_f_getHouseName(hb.house_id) AS housename,"
				 		+ "ROUND(bd.cur_cd/((bd.ytd_amount+bd.cur_amount)/2)*1000,1)  AS atu_cd_rate  "
				 		+ "FROM s_b_house_breed hb "
				 		+ "LEFT JOIN s_b_breed_detail bd ON bd.house_breed_id = hb.id "
				 		+ "WHERE hb.farm_breed_id = "+FarmBreedId+" GROUP BY bd.age,hb.house_id ORDER BY hb.house_id,bd.age"; 
			     
				 mLogger.info("========RepDCReqController.DCRateReq.SQL=" + sql);
				 
			     List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(sql);
				 JSONArray DCDatas = new JSONArray();
				 if(toutcome.size()!=0){
					 Object house_id = toutcome.get(0).get("house_id");
					 int i=0;
					 Object housename = null;
					 ArrayList<Object> HouseDa =new ArrayList<Object>();
					 for (HashMap<String, Object> outcome : toutcome){
						 if(!house_id.equals(toutcome.get(i).get("house_id"))||i+1==toutcome.size()){
							 JSONObject tJSONObject = new JSONObject();
							 tJSONObject.put("HouseId", house_id);
							 tJSONObject.put("housename", housename);
							 tJSONObject.put("HouseDatas", HouseDa);
							 DCDatas.put(tJSONObject);
							 HouseDa = new ArrayList<Object>();
						 }
						 house_id =  outcome.get("house_id");					
						 housename =  outcome.get("housename");
						 Object atu_cd_rate =  outcome.get("atu_cd_rate");	
						 String showFlag =  outcome.get("showFlag").toString();	
						 if(showFlag.equals("Y")){
							 HouseDa.add(atu_cd_rate);
						 }
						 i++;
					 }
				 }
				 resJson.put("DCDatas", DCDatas);
				 resJson.put("FarmBreedId", FarmBreedId);			
			 // 批次对比	 
			 }else if(CompareType.equals("02")){
				 int HouseId = params.optInt("HouseId");
				 
				 String sql = "SELECT (case when bd.growth_date > curdate() then 'N' else 'Y' end) as showFlag, hb.farm_breed_id,(SELECT batch_code from s_b_farm_breed where id = hb.farm_breed_id) AS batch_code,"
				 				+ " ROUND(bd.cur_cd / ((bd.ytd_amount+bd.cur_amount)/2) * 1000, 1) AS atu_cd_rate  "
					 		+ "FROM s_b_house_breed hb "
					 		+ "LEFT JOIN s_b_breed_detail bd ON bd.house_breed_id = hb.id "
					 		+ "WHERE hb.house_id = "+HouseId+" GROUP BY bd.age,hb.farm_breed_id ORDER BY hb.farm_breed_id,bd.age"; 
				 mLogger.info("========RepDCReqController.DCRateReq.SQL=" + sql);
				 
			     List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(sql);
				 JSONArray DCDatas = new JSONArray();
				 if(toutcome.size()!=0){
					 Object farm_breed_id = toutcome.get(0).get("farm_breed_id");
					 int i=0;
					 Object batch_code = null;
					 ArrayList<Object> HouseDa =new ArrayList<Object>();
					 for (HashMap<String, Object> outcome : toutcome){
						 if(!farm_breed_id.equals(toutcome.get(i).get("farm_breed_id")) || i+1==toutcome.size()){
							 JSONObject tJSONObject = new JSONObject();
							 tJSONObject.put("FarmBreedId", farm_breed_id);
							 tJSONObject.put("FBBatchCode", batch_code);
							 tJSONObject.put("HouseDatas", HouseDa);
							 DCDatas.put(tJSONObject);
							 HouseDa = new ArrayList<Object>();
						 }
						 farm_breed_id =  outcome.get("farm_breed_id");					
						 batch_code =  outcome.get("batch_code");
						 Object atu_cd_rate =  outcome.get("atu_cd_rate");	
						 
						 String showFlag =  outcome.get("showFlag").toString();	
						 if(showFlag.equals("Y")){
							 HouseDa.add(atu_cd_rate);
						 }
						 i++;
					 }
				 }
				 resJson.put("DCDatas", DCDatas);
				 resJson.put("HouseId", HouseId);	
			 }
			 resJson.put("Result", "Success");
			 /** 业务处理结束 **/
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
			mLogger.info("=====Now end executing RepDCReqController.DCRateReq");
		}
}
