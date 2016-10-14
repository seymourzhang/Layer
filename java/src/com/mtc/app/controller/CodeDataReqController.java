/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * @ClassName: CodeDataReqController
 * @Description: 
 * @Date 2015年11月23日 下午2:03:12
 * @Author Yin Guo Xiang
 * 
 */
@Controller
@RequestMapping("/sys/code")
public class CodeDataReqController {
	private static Logger mLogger =Logger.getLogger(CodeDataReqController.class); 
	
	@Autowired
	private BaseQueryService mBaseQueryService;
	
	@Autowired
	private SDUserOperationService operationService;
	
	@RequestMapping("/getData")
	public void getData(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing CodeDataReqController.getData");
		
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
//			paraStr = {"secret":"mtc_secret","params":{"regionId":0},"id_spa":"mtc_spa"} ;
			
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			//int personId = jsonObject.optInt("id_spa");
			//operationService.insert(SDUserOperationService.MENU_FARMINFO, SDUserOperationService.OPERATION_SELECT, personId);
					
			mLogger.info("jsonObject=" + jsonObject.toString());
			
			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject paraObj = jsonObject.getJSONObject("params");
			
			String codeType = paraObj.getString("CodeType");
			
			if(!PubFun.isNull(codeType)){
				List<HashMap<String,Object>> mLists ;
				StringBuffer tSQL = new StringBuffer();

				if("ChinaRegion".equals(codeType)){
					String parentid = paraObj.getString("Para1");
					tSQL.append("SELECT ");
					tSQL.append("DISTINCT a.id AS ccode,");
					tSQL.append("a.short_name AS cctext ");
					tSQL.append("FROM s_d_area_china a WHERE 1=1");
					tSQL.append(" AND a.parent_id = '"+parentid+"'  ORDER BY a.sort" );
				}
				
				if(tSQL != null && !tSQL.equals("")){
					mLists= mBaseQueryService.selectMapByAny(tSQL.toString());
					
					mLogger.info("response:" + ListsToJson(mLists));
					resJson.put("ResultData", new JSONObject(ListsToJson(mLists))) ;
				}
			}
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
		mLogger.info("=====Now end executing CodeDataReqController.getData");
	}
	 @RequestMapping("/getFarmBreedData")
		public void getFarmBreedData(HttpServletRequest request,HttpServletResponse response){
		   mLogger.info("=======Now start executing CodeDataReqController.getFarmBreedData");
		   JSONObject resJson = new JSONObject();
		   String dealRes = null;
		   try {
		     String paraStr = PubFun.getRequestPara(request);
		     mLogger.info("updateFarm.para="+paraStr);
			 JSONObject jsonobject = new JSONObject(paraStr);
			 mLogger.info("jsonObject=" + jsonobject.toString());
			 //int personId = jsonobject.optInt("id_spa");
			//operationService.insert(SDUserOperationService.MENU_FARMINFO, SDUserOperationService.OPERATION_SELECT, personId);
				
			 /** 业务处理开始，查询、增加、修改、或删除 **/
			 JSONObject params = jsonobject.optJSONObject("params");
			 int FarmId = params.optInt("FarmId");
			 if(FarmId == 0){
				 resJson.put("ErrorMsg", "请求参数错误");
			 }else{
				 String sql = " SELECT id AS id,batch_code AS NAME FROM s_b_layer_farm_breed WHERE  farm_id = "+FarmId+" ORDER BY place_date DESC"; 
			     mLogger.info("sql"+sql);
				 List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(sql);
				 JSONObject mJSONObject = new JSONObject();
				 if(toutcome.size()!=0){
					 for (HashMap<String, Object> outcome : toutcome){
						 Object batch_code =  outcome.get("NAME");
						 String id = outcome.get("id").toString();					
						 mJSONObject.put(id,batch_code);
					 }
					 resJson.put("FarmBreedIdArray", mJSONObject);
					 resJson.put("Result", "Success");
					 resJson.put("FarmId", FarmId);
				 }else{
					 resJson.put("ErrorMsg", "该农场还未建立任何批次信息。");
				 }
			 }
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
			mLogger.info("=====Now end executing CodeDataReqController.getFarmBreedData");
		}
	 @RequestMapping("/getLayerDayAgeList")
		public void getLayerDayAgeList(HttpServletRequest request,HttpServletResponse response){
		   mLogger.info("=======Now start executing CodeDataReqController.getLayerDayAgeList");
		   JSONObject resJson = new JSONObject();
		   String dealRes = null;
		   try {
		     String paraStr = PubFun.getRequestPara(request);
		     mLogger.info("updateFarm.para="+paraStr);
			 JSONObject jsonobject = new JSONObject(paraStr);
			 mLogger.info("jsonObject=" + jsonobject.toString());
			 /** 业务处理开始，查询、增加、修改、或删除 **/
			 JSONObject params = jsonobject.optJSONObject("params");
			 int FarmBreedId = params.optInt("FarmBreedId");
			 int HouseId = params.optInt("HouseId");
			 if(FarmBreedId == 0||HouseId ==0){
				 resJson.put("ErrorMsg", "请求参数错误");
			 }else{
				 String sql = "SELECT bd.`day_age` , CASE WHEN bd.`growth_date`= CURDATE() THEN 'Y' ELSE 'N'END AS NY "
				 		+ " FROM s_b_layer_breed_detail AS bd LEFT JOIN s_b_layer_house_breed AS hb ON  bd.`house_breed_id` = hb.`id` "
				 		+ " WHERE  hb.`house_id` = "+HouseId+" AND hb.`farm_breed_id`= "+FarmBreedId; 
			     mLogger.info("========CodeDataReqController.getLayerDayAgeList.sql"+sql);
				 List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(sql);
				 JSONArray mLIST = new JSONArray();
				 if(toutcome.size()!=0){
					 for (HashMap<String, Object> outcome : toutcome){
						 Object age =  outcome.get("day_age");
						 Object NY =  outcome.get("NY");
						 if(NY.equals("Y")){
							 resJson.put("nowAge", age); 
						 }
						 mLIST.put(age);
					 }
					 resJson.put("DayAgeListArray", mLIST);
					 resJson.put("Result", "Success");
					 resJson.put("FarmBreedId", FarmBreedId);
					 resJson.put("HouseId", HouseId);
				 }else{
					 resJson.put("ErrorMsg", "该农场还未建立任何批次信息。");
				 }
			 }
			 /** 业务处理结束 **/
			 dealRes = Constants.RESULT_SUCCESS ;
		   } catch (Exception e) {
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("Exception", "请求参数错误");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
			}
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing CodeDataReqController.getLayerDayAgeList");
		}
	private String ListsToJson(List<HashMap<String,Object>> pLists ){
		String res  = "{";
		if(pLists != null && pLists.size() > 0){
			for (int j = 0; j < pLists.size(); j++) {
				if(j!=0){
					res += ",";
				}
				Map<String, Object> itemData = pLists.get(j);
	            res += "\"" + itemData.get("ccode") + "\":\"" + itemData.get("cctext") + "\"";
	        }
		}
		res +="}";
		return res;
	}
	
	
}
