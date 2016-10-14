package com.mtc.app.controller;

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

@Controller
@RequestMapping("layer_report/")
public class LayerDeathCullReqController {
	private static Logger mLogger =Logger.getLogger(LayerDeathCullReqController.class);
	@Autowired
	private BaseQueryService tBaseQueryService;
	@Autowired
	private SDUserOperationService sSDUserOperationService;
	//死淘率查询
	@RequestMapping("queryDeathCull")
	public void queryDeathCull(HttpServletResponse response,HttpServletRequest request){
		mLogger.info("=====Now start executing LayerDeathCullReqController.queryDeathCull");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			int userId = jsonObject.optInt("id_spa");
			sSDUserOperationService.insert(SDUserOperationService.MENU_DATAANALYSIS_DIE, SDUserOperationService.OPERATION_SELECT, userId);
			mLogger.info("========死淘率操作信息：查询，导入完毕");
			mLogger.info("jsonObject=" + jsonObject.toString());
			//** 业务处理开始，查询、增加、修改、或删除 **//
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int FarmBreedId = tHouseJson.getInt("FarmBreedId");
			int FarmId = tHouseJson.getInt("FarmId");
			String ViewType = tHouseJson.getString("ViewType");
			//01周显示    02 日显示
			List<HashMap<String,Object>>  ff  =null;
			String SQL = null;
			if(ViewType.equals("02")){
				 SQL =" SELECT day_age as agw , house_id ,s_f_getHouseName(house_id) AS HouseName , ROUND((cur_cd/(ytd_amount))*1000, 1) as layrate"
						+ " FROM s_b_layer_breed_detail AS bd LEFT JOIN s_b_layer_house_breed AS hb ON bd.`house_breed_id` = hb.`id` "
						+ " WHERE  hb.`farm_breed_id`="+FarmBreedId+" AND  growth_date > DATE_SUB(CURDATE(),INTERVAL 60 DAY) AND  growth_date <= CURDATE() "
						+ " UNION ALL SELECT day_age as agw , IFNULL(null, '01')   AS  house_id, "
						+ " IFNULL(null,'全场平均') AS HouseName , "
						+ " ROUND((SUM(cur_cd)/SUM(ytd_amount))*1000, 1) as layrate "
						+ " FROM s_b_layer_breed_detail AS bd LEFT JOIN s_b_layer_house_breed AS hb ON bd.`house_breed_id` = hb.`id` "
						+ " WHERE  hb.`farm_breed_id`="+FarmBreedId+" AND  growth_date > DATE_SUB(CURDATE(),INTERVAL 60 DAY) AND  growth_date <= CURDATE() GROUP BY growth_date";
			}else if(ViewType.equals("01")){
				 SQL =" SELECT week_age as agw , house_id ,  s_f_getHouseName(house_id) AS HouseName ,"
						+ " ROUND((cur_cd/(ytd_amount))*1000, 1) AS layrate"
						+ " FROM s_b_layer_breed_detail AS bd LEFT JOIN s_b_layer_house_breed AS hb ON bd.`house_breed_id` = hb.`id` "
						+ " WHERE  hb.`farm_breed_id`="+FarmBreedId
						+ " AND   growth_date <= CURDATE()"
						+ " GROUP BY week_age , house_id  UNION ALL SELECT week_age as agw , IFNULL(NULL, '01') AS house_id,  "
						+ " IFNULL(NULL,'全场平均') AS HouseName ,ROUND((SUM(cur_cd)/SUM(ytd_amount)/2)*1000, 1) AS layrate  "
						+ " FROM s_b_layer_breed_detail AS bd LEFT JOIN s_b_layer_house_breed AS hb ON bd.`house_breed_id` = hb.`id` "
						+ " WHERE  hb.`farm_breed_id`="+FarmBreedId
						+ " AND   growth_date <= CURDATE()"
						+ " GROUP BY week_age  ORDER BY house_id , agw ";
				 SQL = " SELECT  week_age AS agw, house_id, s_f_getHouseName (house_id) AS HouseName,"
				 		+ " truncate(SUM(cur_cd)/(SUM(cur_cd) + MIN(cur_amount)) * 1000, 1) AS layrate"
				 		+ " FROM s_b_layer_breed_detail AS bd  LEFT JOIN s_b_layer_house_breed AS hb "
				 		+ "  ON bd.`house_breed_id` = hb.`id` WHERE hb.`farm_breed_id` = "+FarmBreedId+" "
				 		+ "  AND growth_date <= CURDATE() GROUP BY week_age, house_id "
				 		+ " UNION ALL SELECT  agw, IFNULL(NULL, '01') AS house_id,"
				 		+ " IFNULL(NULL, '全场平均') AS HouseName, truncate(AVG(layrate),1) AS layrate "
				 		+ " FROM (SELECT  week_age AS agw, house_id, s_f_getHouseName (house_id) "
				 		+ " AS HouseName, truncate(SUM(cur_cd)/(SUM(cur_cd) + MIN(cur_amount)) * 1000, 1) AS layrate "
				 		+ " FROM s_b_layer_breed_detail AS bd  LEFT JOIN s_b_layer_house_breed AS hb "
				 		+ "  ON bd.`house_breed_id` = hb.`id` WHERE hb.`farm_breed_id` = "+FarmBreedId+" "
				 		+ "  AND growth_date <= CURDATE() GROUP BY week_age, house_id ) "
				 		+ " a GROUP BY agw ORDER BY house_id, agw ";
			}else if(ViewType.equals("03")){
				
				/**入舍鸡数和产蛋周初存栏数 sql查询
				String SQL3 = "select " +
						"a.acc_cd + a.cur_amount as placeNum ,a.ytd_amount as LayedBeginAmount " +
						"from s_b_layer_breed_detail a LEFT JOIN (select min(day_age) as tarDayAge from s_b_layer_breed_detail b " +
						"where 1=1 " +
						"and b.house_breed_id = " + HouseBreedId + " " +
						"and b.week_age = s_f_getLayAge(" + HouseBreedId + ",'WeekAge')) as temp on 1=1 " +
						"where a.house_breed_id = " + HouseBreedId + " and a.day_age = ifnull(temp.tarDayAge,200)";
				List<HashMap<String, Object>> tAccData2 = tBaseQueryService.selectMapByAny(SQL3);
				mLogger.info("queryWR.SQL3 = " + SQL3);
				Object PlaceNum = tAccData2.get(0).get("placeNum");
				Object AmountFirshLayer = tAccData2.get(0).get("LayedBeginAmount");
				*/
				
				 SQL =" SELECT  week_age AS agw, house_id, s_f_getHouseName (house_id) AS HouseName," +
						"  ROUND((MAX(acc_cd) / (MAX(acc_cd) + MIN(cur_amount))) * 100, 2) AS layrate  FROM  s_b_layer_breed_detail AS bd " +
						"  LEFT JOIN s_b_layer_house_breed AS hb     ON bd.`house_breed_id` = hb.`id` " +
						" WHERE hb.`farm_breed_id` = "+FarmBreedId
						+ " AND growth_date <= CURDATE() "
						+ " GROUP BY week_age,  house_id UNION ALL "
						+ " SELECT agw, '00' AS house_id , '全场平均' AS HouseName ,ROUND(AVG(layrate),2) AS layrate"
						+ " FROM (SELECT  week_age AS agw, house_id,s_f_getHouseName (house_id) AS HouseName,"
						+ "ROUND((MAX(acc_cd) / (MAX(acc_cd) + MIN(cur_amount))) * 100, 2) AS layrate FROM s_b_layer_breed_detail"
						+ " AS bd  LEFT JOIN s_b_layer_house_breed AS hb   ON bd.`house_breed_id` = hb.`id`"
						+ " WHERE hb.`farm_breed_id` = "+FarmBreedId
						+ " AND growth_date <= CURDATE()"
						+ " GROUP BY week_age, house_id )"
						+ " a GROUP BY  agw ORDER BY house_id ,agw ";
				}
			 mLogger.info("========LayerDeathCullReqController.queryDeathCull.SQL=" + SQL);
			 ff = tBaseQueryService.selectMapByAny(SQL);
		     if(ff.size()>0){
		    	JSONArray  LayerRate = new JSONArray();
		    	JSONArray  HouseDatas = new JSONArray();
		    	JSONObject tJSONObject = new JSONObject();
		    	JSONArray  xAxis = new JSONArray();
		    	int i=0;
		    	boolean xboolean =true;
		    	for (HashMap<String, Object> hashMap : ff) {
		    		Object agw = hashMap.get("agw");
		   		    Object layrate = hashMap.get("layrate");
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
			   		     tJSONObject.put("house_id", house_id);
			   		     tJSONObject.put("HouseName", HouseName);
			   		     tJSONObject.put("HouseDatas", HouseDatas);
			   		     LayerRate.put(tJSONObject);
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
		    	resJson.put("LayerRate",LayerRate);	
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
		mLogger.info("=====Now end executing LayerDeathCullReqController.queryDeathCull");
	}
}
