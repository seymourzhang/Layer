package com.mtc.app.controller;

import static java.lang.Math.round;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.biz.LayerBreedBatchReqManager;
import com.mtc.app.biz.LayerDataInputReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.MySQLSPService;
import com.mtc.app.service.SBDeviHouseService;
import com.mtc.app.service.SBLayerBreedDetailService;
import com.mtc.app.service.SDHouseService;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBLayerBreedDetail;
import com.thoughtworks.xstream.converters.basic.BigDecimalConverter;

@Controller
@RequestMapping("layer_dataInput/")
public class LayerDataInputReqController {
	private static Logger mLogger =Logger.getLogger(LayerDataInputReqController.class);
	@Autowired
	private LayerBreedBatchReqManager tLayerBreedBatchReqManager ;
	@Autowired
	private SDHouseService mSDHouseService;
	@Autowired
	private SBDeviHouseService tSBDeviHouseService ;
	@Autowired
	private BaseQueryService tBaseQueryService;
	@Autowired
	private MySQLSPService tMySQLSPService;
	@Autowired
	private SBLayerBreedDetailService tSBLayerBreedDetailService;
	@Autowired
	private LayerDataInputReqManager tLayerDataInputReqManager;

	@Autowired
	private SDUserOperationService operationService;

	@RequestMapping("queryDR_v2")
	public void queryDR_v2(HttpServletResponse response,HttpServletRequest request){
		mLogger.info("=====Now start executing LayerDataInputReqController.queryDR_v2");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			operationService.insert(SDUserOperationService.MENU_PRODUCTION_DAY, 
            SDUserOperationService.OPERATION_SELECT, jsonObject.optInt("id_spa"));
			mLogger.info("=====日报查询操作信息：查询，导入完毕");
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int FarmBreedId = tHouseJson.getInt("FarmBreedId");
			int HouseId = tHouseJson.getInt("HouseId");
			int DayAge = tHouseJson.getInt("DayAge");
			String	SQL2 = "SELECT id AS HouseBreedId  FROM s_b_layer_house_breed where house_id = "+HouseId+"  AND farm_breed_id = "+FarmBreedId;
			Integer  HouseBreedId =  tBaseQueryService.selectIntergerByAny(SQL2);
			if(HouseBreedId!=null){
				if(DayAge == -1){
					String	SQL1 = " SELECT day_age FROM s_b_layer_breed_detail  WHERE growth_date = CURDATE() AND  house_breed_id ="+HouseBreedId;
					  DayAge =  tBaseQueryService.selectIntergerByAny(SQL1);
					}
					String SQL ="SELECT house_breed_id AS HouseBreedId, ifnull(num_bak1,0) as egg_box_size , "
							+ "week_age AS GrowthWeekAge,day_age AS CurDayAge,"
							   + "  s_f_getHouseName ("+HouseId+") AS HouseName,  CURDATE() AS Cur_Date,"
							   + "  cur_lay_num AS curLayNum, cur_lay_weight  AS curLayWeight ,  cur_cd AS culling_cd ,"
							   + "   cur_lay_num AS curLayNum, cur_lay_weight AS curLayWeight,  cur_lay_broken AS curBrokenNum,"
							   + "   cur_feed AS daily_feed, cur_weight AS daily_weight, cur_water AS daily_water"
							   + " FROM s_b_layer_breed_detail  "
							   + " WHERE   house_breed_id ="+HouseBreedId+"   AND is_history = 'N'   AND day_age = "+DayAge;
				mLogger.info("========LayerDataInputReqController.queryDR_v2.SQL=" + SQL);
				List<HashMap<String,Object>> hashMap = tBaseQueryService.selectMapByAny(SQL);
			    if(hashMap.size()==1){
		    	    Calendar cal = Calendar.getInstance();
		    	    int day = cal.get(Calendar.DATE);
		    	    int month = cal.get(Calendar.MONTH) + 1;
	        	    String date = month+"月"+day+"日";
		    		resJson.put("CurDate",date);	
			    	resJson.put("Result","Success");	
		    		resJson.put("HouseBreedId",HouseBreedId);	
		    		resJson.put("HouseId",HouseId);	
		    		resJson.put("egg_box_size",hashMap.get(0).get("egg_box_size"));	
		    		resJson.put("HouseName",hashMap.get(0).get("HouseName"));
		    		resJson.put("CurDate",hashMap.get(0).get("Cur_Date"));	
		    		resJson.put("CurDayAge",hashMap.get(0).get("CurDayAge"));	
		    		resJson.put("GrowthWeekAge", hashMap.get(0).get("GrowthWeekAge"));
		    		resJson.put("culling_all", hashMap.get(0).get("culling_cd"));	
		    		resJson.put("curLayNum",hashMap.get(0).get("curLayNum"));	
		    		resJson.put("eggAvgWeight",hashMap.get(0).get("curLayWeight"));	
		    		resJson.put("curBrokenNum", hashMap.get(0).get("curBrokenNum"));	
		    		resJson.put("daily_feed",hashMap.get(0).get("daily_feed"));	
		    		resJson.put("daily_weight",hashMap.get(0).get("daily_weight"));	
		    		resJson.put("daily_water",hashMap.get(0).get("daily_water"));	
		    		Integer LayerWeekAge = 0;
		    		String SQL1 =" SELECT(SELECT week_age FROM s_b_layer_breed_detail WHERE growth_date=CURDATE() AND house_breed_id = "+HouseBreedId+")"
		    				+ "  - (SELECT MIN(week_age) FROM s_b_layer_breed_detail WHERE cur_lay_num>0 AND house_breed_id =  "+HouseBreedId+") AS LayerWeekAge";
		    		mLogger.info("========LayerDataInputReqController.queryDR_v2.SQL=" + SQL1);
		    		List<HashMap<String,Object>> hashMap1 = tBaseQueryService.selectMapByAny(SQL1);
		    		if(hashMap1.get(0)!=null&&hashMap1.get(0).get("LayerWeekAge")!=null){
		    		 LayerWeekAge =	Integer.parseInt(hashMap1.get(0).get("LayerWeekAge").toString())+1;
		    		}
		    		resJson.put("LayerWeekAge",LayerWeekAge);	
		    }else{
		    	resJson.put("Result","Fail");
		    	resJson.put("ErrorMsg","“该日期无日报数据。”");
		    }
	    }else{
	    	resJson.put("Result","Fail");
	    	resJson.put("ErrorMsg","所选栋舍没有进鸡，无日报数据。");
	    }
			dealRes = Constants.RESULT_SUCCESS ;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", "提交请求参数有误");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL ;
		}
		DealSuccOrFail.dealApp(request,response,dealRes,resJson);
		mLogger.info("=====Now end executing LayerDataInputReqController.queryDR_v2");
	}
	@RequestMapping("queryDR")
	public void queryDR(HttpServletResponse response,HttpServletRequest request){
		mLogger.info("=====Now start executing LayerDataInputReqController.queryDR");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			//** 业务处理开始，查询、增加、修改、或删除 **//
			operationService.insert(SDUserOperationService.MENU_PRODUCTION_DAY, SDUserOperationService.OPERATION_SELECT, jsonObject.optInt("id_spa"));
			mLogger.info("=====日报查询操作信息：查询，导入完毕");
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int FarmBreedId = tHouseJson.getInt("FarmBreedId");
			int HouseId = tHouseJson.getInt("HouseId");
			String SQL ="SELECT  dayofweek(growth_date) as weekFlag ,week_age, "
					+ " s_f_getHouseName(hb.house_id) as house_name ,"
					+ "cur_lay_num, cur_lay_weight,house_breed_id,growth_date,day_age ,"
					+ "cur_amount,acc_cd,cur_cd , acc_lay_num,acc_lay_weight,cur_lay_broken,"
					+ "cur_feed ,cur_weight ,cur_water ,"
					+ "CONCAT(ROUND(acc_cd/(acc_cd+cur_amount)*100,1),'%') AS acc_cd_rate "
					+ "FROM s_b_layer_breed_detail AS bd LEFT JOIN s_b_layer_house_breed AS hb ON bd.`house_breed_id` = hb.`id`"
					+ "WHERE hb.`house_id` = "+HouseId+" "
					+ "AND hb.`farm_breed_id`="+FarmBreedId+" "
							+ "AND bd.`is_history`='N' "
							+ "AND growth_date BETWEEN DATE_ADD(curdate(), INTERVAL -60 DAY) and curdate() "
							+ "ORDER BY growth_date ";
			mLogger.info("=========LayerDataInputReqController.queryDR.SQL=" + SQL);
			List<HashMap<String,Object>> ff = tBaseQueryService.selectMapByAny(SQL);
		    if(ff.size()>0){
		    	JSONArray dataInput =new JSONArray();
		    	Object house_breed_id = ff.get(0).get("house_breed_id");
	    		Object HouseName = ff.get(0).get("house_name");
	    		int i = 0;
		    	for (HashMap<String, Object> hashMap : ff) {
		    		i ++;
		    		JSONObject tJSONObject =new JSONObject();
		    		Object day_age = hashMap.get("day_age");
		    		Object week_age = hashMap.get("week_age");
		    		Object weekFlag = hashMap.get("weekFlag");
		    		Object culling_all = hashMap.get("cur_cd");
		   		    Object curLayNum = hashMap.get("cur_lay_num");
		    		Object curLayWeight = hashMap.get("cur_lay_weight");
		    		Object curBrokenNum = hashMap.get("cur_lay_broken");
		    		Object daily_feed = hashMap.get("cur_feed");
		    		Object daily_weight = hashMap.get("cur_weight");
		    		Object daily_water = hashMap.get("cur_water");
		    		tJSONObject.put("day_age", day_age);
		    		tJSONObject.put("weekFlag", weekFlag);
		    		tJSONObject.put("week_age", week_age);
		    		tJSONObject.put("row_id", i);
		    		tJSONObject.put("culling_all", culling_all);
		    		tJSONObject.put("curLayNum", curLayNum);
		    		tJSONObject.put("curLayWeight", curLayWeight);
		    		tJSONObject.put("curBrokenNum", curBrokenNum);
		    		tJSONObject.put("daily_feed", daily_feed);
		    		tJSONObject.put("daily_weight", daily_weight);
		    		tJSONObject.put("daily_water", daily_water);
		    		dataInput.put(tJSONObject);
		    		
		    		if(i == ff.size()){
			    		resJson.put("CurDayAge",day_age);	
				    	resJson.put("cur_amount",hashMap.get("cur_amount"));	
				    	resJson.put("culling_acc",hashMap.get("acc_cd"));	
				    	resJson.put("acc_cd_rate",hashMap.get("acc_cd_rate"));	
				    	resJson.put("acc_layNum",hashMap.get("acc_lay_num"));	
				    	resJson.put("acc_layWeight",hashMap.get("acc_lay_weight"));	
		    		}
		    	}
		    	resJson.put("Result","Success");	
		    	resJson.put("HouseBreedId",house_breed_id);	
		    	resJson.put("HouseId",HouseId);	
		    	resJson.put("HouseName",HouseName);	
		    	resJson.put("dataInput",dataInput);	
		    }else{
		    	resJson.put("Result","Fail");
		    }
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
		mLogger.info("=====Now end executing LayerDataInputReqController.queryDR");
	}
	@RequestMapping("saveDR")
    public void saveDR(HttpServletResponse response,HttpServletRequest request){
		mLogger.info("=====Now start executing LayerDataInputReqController.saveDR");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			Date date  = new Date();
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(SDUserOperationService.MENU_PRODUCTION_DAY, SDUserOperationService.OPERATION_UPDATE, jsonObject.optInt("id_spa"));
			mLogger.info("========数据导入操作信息：新增，导入完毕");
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int userId = jsonObject.optInt("id_spa");
			int HouseBreedId =  tHouseJson.optInt("HouseBreedId");
			int HouseId =  tHouseJson.optInt("HouseId");
			JSONArray dataInput = (JSONArray) tHouseJson.opt("dataInput");
				if(dataInput.length()!=0){
					List<SBLayerBreedDetail> lSBLayerBreedDetail = new  ArrayList<SBLayerBreedDetail>();
					for (int i = 0; i < dataInput.length(); i++) {
						SBLayerBreedDetail tSBLayerBreedDetail = new SBLayerBreedDetail();
						int	day_age =  dataInput.getJSONObject(i).getInt("day_age");
						int	culling_all = dataInput.getJSONObject(i).getInt("culling_all");
						int	curLayNum = dataInput.getJSONObject(i).getInt("curLayNum");
						String	curLayWeight = dataInput.getJSONObject(i).getString("curLayWeight");
						int	curBrokenNum = dataInput.getJSONObject(i).getInt("curBrokenNum");
						String	daily_feed = dataInput.getJSONObject(i).getString("daily_feed");
						String	daily_weight = dataInput.getJSONObject(i).getString("daily_weight");
						String	daily_water = dataInput.getJSONObject(i).getString("daily_water");
						tSBLayerBreedDetail.setHouseBreedId(HouseBreedId);
						tSBLayerBreedDetail.setDayAge(day_age);
						tSBLayerBreedDetail.setCurCd(culling_all);
						tSBLayerBreedDetail.setCullingPm(culling_all);
						tSBLayerBreedDetail.setCurLayNum(curLayNum);
						tSBLayerBreedDetail.setCurLayWeight(new BigDecimal(curLayWeight) );
						tSBLayerBreedDetail.setCurLayBroken(curBrokenNum);
						tSBLayerBreedDetail.setCurWeight(new BigDecimal(daily_weight));
						tSBLayerBreedDetail.setCurWater(new BigDecimal(daily_water));
						tSBLayerBreedDetail.setCurFeed(new BigDecimal(daily_feed));
						lSBLayerBreedDetail.add(tSBLayerBreedDetail);
					}
					Collections.sort(lSBLayerBreedDetail,new  Comparator<SBLayerBreedDetail>() {
						@Override
						public int compare(SBLayerBreedDetail arg0, SBLayerBreedDetail arg1) {
							 return arg0.getDayAge().compareTo(arg1.getDayAge()); 
						}
						});
				    List<SBLayerBreedDetail> ySBLayerBreedDetail = tSBLayerBreedDetailService.selectByhouseBreedId(HouseBreedId,lSBLayerBreedDetail.get(0).getDayAge(),lSBLayerBreedDetail.get(lSBLayerBreedDetail.size()-1).getDayAge());
				    if(ySBLayerBreedDetail.size()!=0){
				    	 Collections.sort(ySBLayerBreedDetail,new  Comparator<SBLayerBreedDetail>() {
								@Override
								public int compare(SBLayerBreedDetail arg0, SBLayerBreedDetail arg1) {
									 return arg0.getDayAge().compareTo(arg1.getDayAge()); 
								}
						 });
				    	 int diff_acc_cd = 0;
				    	 BigDecimal diff_acc_feed =new BigDecimal(0) ;
				    	 BigDecimal diff_acc_water = new BigDecimal(0);
				    	 int diff_acc_lay_num = 0;
				    	 BigDecimal diff_acc_lay_weight =new BigDecimal(0);
				    	 int diff_acc_lay_broken = 0;
				    	 boolean addLayerBreedDetail =true;
					    for (int i = 0; i < ySBLayerBreedDetail.size(); i++) {
					    	addLayerBreedDetail =true;
							for (int j = 0; j < lSBLayerBreedDetail.size(); j++) {
								if(ySBLayerBreedDetail.get(i).getDayAge().equals(lSBLayerBreedDetail.get(j).getDayAge())){
									ySBLayerBreedDetail.get(i).setYtdAmount(ySBLayerBreedDetail.get(i).getYtdAmount()-diff_acc_cd);
									// 累计死淘
						    		diff_acc_cd = diff_acc_cd + lSBLayerBreedDetail.get(j).getCurCd() - ySBLayerBreedDetail.get(i).getCurCd();
						    		// 累计饲料
							    	diff_acc_feed =diff_acc_feed.add(lSBLayerBreedDetail.get(j).getCurFeed().subtract(ySBLayerBreedDetail.get(i).getCurFeed()));
							    	// 累计用水
							    	diff_acc_water = diff_acc_water.add(lSBLayerBreedDetail.get(j).getCurWater().subtract(ySBLayerBreedDetail.get(i).getCurWater()));
							    	// 累计产蛋
							    	diff_acc_lay_num =  diff_acc_lay_num + lSBLayerBreedDetail.get(j).getCurLayNum() - ySBLayerBreedDetail.get(i).getCurLayNum();
							    	// 累计产蛋重量
							    	diff_acc_lay_weight =diff_acc_lay_weight.add(lSBLayerBreedDetail.get(j).getCurLayWeight().subtract(ySBLayerBreedDetail.get(i).getCurLayWeight()));
							    	// 累计产蛋碎
							    	diff_acc_lay_broken = diff_acc_lay_broken + lSBLayerBreedDetail.get(j).getCurLayBroken() - ySBLayerBreedDetail.get(i).getCurLayBroken();
							    	
							    	
									 ySBLayerBreedDetail.get(i).setCullingPm(lSBLayerBreedDetail.get(j).getCullingPm());
									 ySBLayerBreedDetail.get(i).setCurCd(lSBLayerBreedDetail.get(j).getCurCd());
									 ySBLayerBreedDetail.get(i).setAccCd( ySBLayerBreedDetail.get(i).getAccCd()+diff_acc_cd);
									 ySBLayerBreedDetail.get(i).setCurFeed(lSBLayerBreedDetail.get(j).getCurFeed());
									 ySBLayerBreedDetail.get(i).setAccFeed(ySBLayerBreedDetail.get(i).getAccFeed().add(diff_acc_feed));
									 ySBLayerBreedDetail.get(i).setCurWeight(lSBLayerBreedDetail.get(j).getCurWeight());
									 ySBLayerBreedDetail.get(i).setCurAmount(ySBLayerBreedDetail.get(i).getCurAmount()-diff_acc_cd);
									 ySBLayerBreedDetail.get(i).setCurWater(lSBLayerBreedDetail.get(j).getCurWater());
									 ySBLayerBreedDetail.get(i).setCurLayNum(lSBLayerBreedDetail.get(j).getCurLayNum());
									 ySBLayerBreedDetail.get(i).setAccLayNum(ySBLayerBreedDetail.get(i).getAccLayNum()+diff_acc_lay_num);
									 ySBLayerBreedDetail.get(i).setCurLayBroken(lSBLayerBreedDetail.get(j).getCurLayBroken());
									 ySBLayerBreedDetail.get(i).setAccLayBroken(ySBLayerBreedDetail.get(i).getAccLayBroken()+diff_acc_lay_broken);
									 ySBLayerBreedDetail.get(i).setAccWater(ySBLayerBreedDetail.get(i).getAccWater().add(diff_acc_water));
									 ySBLayerBreedDetail.get(i).setCurLayWeight(lSBLayerBreedDetail.get(j).getCurLayWeight());
									 ySBLayerBreedDetail.get(i).setAccLayWeight(ySBLayerBreedDetail.get(i).getAccLayWeight().add(diff_acc_lay_weight));
									 ySBLayerBreedDetail.get(i).setModifyDate(date);
									 ySBLayerBreedDetail.get(i).setModifyTime(date);
									 ySBLayerBreedDetail.get(i).setModifyPerson(userId);
									 addLayerBreedDetail=false;
								}
							}
							if(addLayerBreedDetail){
								 ySBLayerBreedDetail.get(i).setYtdAmount(ySBLayerBreedDetail.get(i).getYtdAmount()-diff_acc_cd);
								 ySBLayerBreedDetail.get(i).setAccCd( ySBLayerBreedDetail.get(i).getAccCd()+diff_acc_cd);
								 ySBLayerBreedDetail.get(i).setAccFeed(ySBLayerBreedDetail.get(i).getAccFeed().add(diff_acc_feed));
								 ySBLayerBreedDetail.get(i).setCurAmount(ySBLayerBreedDetail.get(i).getCurAmount()-diff_acc_cd);
								 ySBLayerBreedDetail.get(i).setAccLayNum(ySBLayerBreedDetail.get(i).getAccLayNum()+diff_acc_lay_num);
								 ySBLayerBreedDetail.get(i).setAccLayBroken(ySBLayerBreedDetail.get(i).getAccLayBroken()+diff_acc_lay_broken);
								 ySBLayerBreedDetail.get(i).setAccWater(ySBLayerBreedDetail.get(i).getAccLayWeight().add(diff_acc_lay_weight));
								 ySBLayerBreedDetail.get(i).setAccLayWeight(ySBLayerBreedDetail.get(i).getAccLayWeight().add(diff_acc_lay_weight));
								 ySBLayerBreedDetail.get(i).setModifyDate(date);
								 ySBLayerBreedDetail.get(i).setModifyTime(date);
								 ySBLayerBreedDetail.get(i).setModifyPerson(userId);
							}
							ySBLayerBreedDetail.get(i).setModifyPerson(userId);
							ySBLayerBreedDetail.get(i).setModifyDate(new Date());
							ySBLayerBreedDetail.get(i).setModifyTime(new Date());
							mLogger.info("ySBLayerBreedDetail.dayAge=" + ySBLayerBreedDetail.get(i).getDayAge());
							mLogger.info("ySBLayerBreedDetail.growthDate=" + ySBLayerBreedDetail.get(i).getGrowthDate());
						} 
					String SQL ="UPDATE s_b_layer_breed_detail SET acc_cd = acc_cd+ "+diff_acc_cd
				    										  +" , acc_feed = acc_feed+ "+diff_acc_feed
				    										  +" ,cur_amount = cur_amount-"+diff_acc_cd
				    										  +" , ytd_amount = ytd_amount-"+diff_acc_cd
				    										  +" , acc_water = acc_water+"+diff_acc_water
				    										  +",acc_lay_num = acc_lay_num+"+diff_acc_lay_num
				    										  +",acc_lay_weight = acc_lay_weight+"+diff_acc_lay_weight
				    										  +",acc_lay_broken=acc_lay_broken+"+diff_acc_lay_broken
				    										  +",modify_date=curdate() "
				    										  + ",modify_time=curdate() "
				    										  + ",modify_person="+userId+" "
				    										  + "WHERE house_breed_id = "+HouseBreedId
				    										  +" AND day_age>"+ySBLayerBreedDetail.get(ySBLayerBreedDetail.size()-1).getDayAge(); 
					mLogger.info("===========LayerDataInputReqController.saveDR.SQL=" + SQL);
				    HashMap< String, Object> tPara = new HashMap< String, Object>();
					tPara.put("SBLayerBreedDetail", ySBLayerBreedDetail);
					tPara.put("SQL", SQL);
				   	tLayerDataInputReqManager.SaveDR(tPara);
				    resJson.put("LoginResult", "Success");
					resJson.put("HouseId", HouseId);
			    }else{
			     resJson.put("ErrorMsg", "该批次尚未数据不存在");
			    }
			}else{
				 resJson.put("Result", "Fail");
			}
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
		mLogger.info("=====Now end executing LayerDataInputReqController.saveDR");
	}
	@RequestMapping("saveDR_v2")
    public void saveDR_v2(HttpServletResponse response,HttpServletRequest request){
		mLogger.info("=====Now start executing LayerDataInputReqController.saveDR_v2");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			Date date  = new Date();
			//** 业务处理开始，查询、增加、修改、或删除 **//*
			operationService.insert(SDUserOperationService.MENU_PRODUCTION_DAY, SDUserOperationService.OPERATION_UPDATE, jsonObject.optInt("id_spa"));
			mLogger.info("========数据导入操作信息：新增，导入完毕");
			int culling_all = 0;
			int curLayNum = 0;
			int curBrokenNum = 0;
			String egg_box_size = null;
			String daily_feed = null;
			String daily_water = null;
			String daily_weight = null;
			Integer CurDayAge = null;
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int userId = jsonObject.optInt("id_spa");
			int HouseBreedId =  tHouseJson.optInt("HouseBreedId");
			int HouseId =  tHouseJson.optInt("HouseId");
			JSONArray jsonArray = tHouseJson.optJSONArray("dataInput");
			for (int i = 0; i < jsonArray.length(); ++i){
				CurDayAge = jsonArray.getJSONObject(i).optInt("day_age");
				culling_all = jsonArray.getJSONObject(i).optInt("culling_all");
				curLayNum = jsonArray.getJSONObject(i).optInt("curLayNum");
				curBrokenNum = jsonArray.getJSONObject(i).optInt("curBrokenNum");
				egg_box_size = jsonArray.getJSONObject(i).optString("egg_box_size");
				daily_feed = jsonArray.getJSONObject(i).optString("daily_feed");
				daily_water = jsonArray.getJSONObject(i).optString("daily_water");
				daily_weight = jsonArray.getJSONObject(i).optString("daily_weight");
//				JSONObject jsonObject1 = tHouseJson.optJSONObject("dataInput");
				BigDecimal eggBoxSize = new BigDecimal(egg_box_size);
				BigDecimal curLayNum1 = new BigDecimal(curLayNum);
				BigDecimal curLayWeight = eggBoxSize.multiply(curLayNum1.divide(new BigDecimal(360),5,BigDecimal.ROUND_HALF_UP));
				if(CurDayAge!=null){
					if(CurDayAge == -1) {
						String sql = "select day_age as age from s_b_layer_breed_detail where growth_date =curdate() and house_breed_id=" + HouseBreedId;
						mLogger.info("=====LayerDataInputReqController.saveDR_v2.sql" + sql);
						CurDayAge = tBaseQueryService.selectIntergerByAny(sql);
					}
			    	SBLayerBreedDetail ySBLayerBreedDetail = tSBLayerBreedDetailService.selectByPrimaryKey(HouseBreedId, CurDayAge);
				   if(ySBLayerBreedDetail!=null){
						 int diff_acc_cd = 0;
						 BigDecimal diff_acc_feed =new BigDecimal(0) ;
						 BigDecimal diff_acc_water = new BigDecimal(0);
						 int diff_acc_lay_num = 0;
						 BigDecimal diff_acc_lay_weight =new BigDecimal(0);
						 int diff_acc_lay_broken = 0;
						 // 累计死淘
						 diff_acc_cd = diff_acc_cd + culling_all - ySBLayerBreedDetail.getCurCd();
						 // 累计饲料
						 diff_acc_feed =diff_acc_feed.add(new BigDecimal(daily_feed).subtract(ySBLayerBreedDetail.getCurFeed()));
						 // 累计用水
						 diff_acc_water = diff_acc_water.add(new BigDecimal(daily_water).subtract(ySBLayerBreedDetail.getCurWater()));
						 // 累计产蛋
						 diff_acc_lay_num = diff_acc_lay_num + curLayNum - ySBLayerBreedDetail.getCurLayNum();
						 // 累计产蛋重量
						 diff_acc_lay_weight = diff_acc_lay_weight.add(curLayWeight).subtract(ySBLayerBreedDetail.getCurLayWeight());
						 // 累计产蛋碎
						 diff_acc_lay_broken = diff_acc_lay_broken + curBrokenNum - ySBLayerBreedDetail.getCurLayBroken();
						 ySBLayerBreedDetail.setCullingPm(culling_all);
						 ySBLayerBreedDetail.setCurCd(culling_all);
						 ySBLayerBreedDetail.setAccCd( ySBLayerBreedDetail.getAccCd()+diff_acc_cd);
						 ySBLayerBreedDetail.setCurFeed(new BigDecimal(daily_feed));
						 ySBLayerBreedDetail.setAccFeed(ySBLayerBreedDetail.getAccFeed().add(diff_acc_feed));
						 ySBLayerBreedDetail.setCurWeight(new BigDecimal(daily_weight));
						 ySBLayerBreedDetail.setCurAmount(ySBLayerBreedDetail.getCurAmount()-diff_acc_cd);
						 ySBLayerBreedDetail.setCurWater(new BigDecimal(daily_water));
						 ySBLayerBreedDetail.setCurLayNum(curLayNum);
						 ySBLayerBreedDetail.setNumBak1(eggBoxSize);
						 ySBLayerBreedDetail.setAccLayNum(ySBLayerBreedDetail.getAccLayNum()+diff_acc_lay_num);
						 ySBLayerBreedDetail.setCurLayBroken(curBrokenNum);
						 ySBLayerBreedDetail.setAccLayBroken(ySBLayerBreedDetail.getAccLayBroken()+diff_acc_lay_broken);
						 ySBLayerBreedDetail.setAccWater(ySBLayerBreedDetail.getAccWater().add(diff_acc_water));
						 ySBLayerBreedDetail.setCurLayWeight(curLayWeight);
						 ySBLayerBreedDetail.setAccLayWeight(ySBLayerBreedDetail.getAccLayWeight().add(diff_acc_lay_weight));
						 ySBLayerBreedDetail.setModifyDate(date);
						 ySBLayerBreedDetail.setModifyTime(date);
						 ySBLayerBreedDetail.setModifyPerson(userId);
						 mLogger.info("ySBLayerBreedDetail.dayAge=" + ySBLayerBreedDetail.getDayAge());
						 mLogger.info("ySBLayerBreedDetail.growthDate=" + ySBLayerBreedDetail.getGrowthDate());
						 String SQL ="UPDATE s_b_layer_breed_detail SET acc_cd = acc_cd+ "+diff_acc_cd+" , acc_feed = acc_feed+ "+diff_acc_feed
					    										  +" ,cur_amount = cur_amount-"+diff_acc_cd
					    										  +" , ytd_amount = ytd_amount-"+diff_acc_cd
					    										  +" , acc_water = acc_water+"+diff_acc_water
					    										  +",acc_lay_num = acc_lay_num+"+diff_acc_lay_num
					    										  +",acc_lay_weight = acc_lay_weight+"+diff_acc_lay_weight
					    										  +",acc_lay_broken=acc_lay_broken+"+diff_acc_lay_broken
					    										  +",modify_date=curdate() "
					    										  + ",modify_time=curdate() "
					    										  + ",modify_person="+userId+" "
					    										  + "WHERE house_breed_id = "+HouseBreedId
					    										  +" AND day_age>"+CurDayAge; 
						mLogger.info("==========LayerDataInputReqController.saveDR_v2.SQL=" + SQL);
					    HashMap< String, Object> tPara = new HashMap< String, Object>();
						tPara.put("SBLayerBreedDetail", ySBLayerBreedDetail);
						tPara.put("SQL", SQL);
					   	tLayerDataInputReqManager.SaveDR_v2(tPara);
					    resJson.put("LoginResult", "Success");
						resJson.put("HouseId", HouseId);
						} else {
							resJson.put("ErrorMsg", "该批次尚未数据不存在。");
						}
					} else {
						resJson.put("ErrorMsg", "该日期无日报数据。");
					}
				}
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
		mLogger.info("=====Now end executing LayerDataInputReqController.saveDR_v2");
	}
	@RequestMapping("queryWR")
	public void queryWR(HttpServletResponse response,HttpServletRequest request){
		mLogger.info("=====Now start executing LayerDataInputReqController.queryWR");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		DecimalFormat df = new DecimalFormat("#.#");
		try {
			String paraStr = PubFun.getRequestPara(request);
			JSONObject jsonObject = new JSONObject(paraStr);
			//** 业务处理开始，查询、增加、修改、或删除 **//
			operationService.insert(SDUserOperationService.MENU_PRODUCTION_WEEK, SDUserOperationService.OPERATION_SELECT, jsonObject.optInt("id_spa"));
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			Calendar calendar = Calendar.getInstance();
			int numDate = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			if (numDate < 0){
				numDate = 0;
			}
			int curMonth = calendar.get(Calendar.MONTH) + 1;
			int curDay = calendar.get(Calendar.DAY_OF_MONTH);
			String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
			String curDate = curMonth + "月" + curDay + "日" + weekDays[numDate];

			int FarmBreedId = tHouseJson.getInt("FarmBreedId");
			int HouseId = tHouseJson.getInt("HouseId");
			String ViewType = tHouseJson.getString("ViewType");
			String SQL1 ="SELECT  bd.house_breed_id AS HouseBreedId,"
				+ " s_f_getHouseName(hb.house_id) AS HouseName,"
				+ "bd.week_age AS  CurWeekAge ,"
				+ "week_age ,acc_cd AS culling_acc ,"
				+ "CONCAT(ROUND(bd.acc_cd/((bd.ytd_amount+bd.cur_amount)/2)*100,1),'%') AS acc_cd_rate ,"
				+ "acc_lay_num AS acc_layNum ,"
				+ "acc_lay_weight AS acc_layWeight "
				+ "FROM  s_b_layer_breed_detail AS bd LEFT JOIN  s_b_layer_house_breed AS hb ON bd.`house_breed_id` = hb.`id`"
				+ "WHERE  hb.farm_breed_id ="+FarmBreedId+" AND hb.`house_id`="+HouseId+" AND  growth_date = CURDATE()  " ;
			mLogger.info("==========LayerDataInputReqController.queryWR.SQL1=" + SQL1);
			List<HashMap<String,Object>> ff = tBaseQueryService.selectMapByAny(SQL1);
	  	    if(ff.size()==1) {
				JSONArray dataInput = new JSONArray();
				Object HouseBreedId = ff.get(0).get("HouseBreedId");

				/**生长周龄和产蛋周龄*/
				String SQL2 = "select " +
						"a.week_age as growthWeekAge,ifnull(a.week_age - s_f_getLayAge(a.house_breed_id,'WeekAge') + 1,0) as layWeekAge " +
						"from s_b_layer_breed_detail a " +
						"where a.house_breed_id = " + HouseBreedId + " and a.growth_date = curdate()";
				List<HashMap<String, Object>> tAccData1 = tBaseQueryService.selectMapByAny(SQL2);
				mLogger.info("========LayerDataInputReqController.queryWR.SQL2 = " + SQL2);
				Object growthWeekAge = tAccData1.get(0).get("growthWeekAge");
				Object layWeekAge = tAccData1.get(0).get("layWeekAge");
				
				long beginedLayerAge = 0;				
				if(growthWeekAge.equals(layWeekAge)){
					layWeekAge = 0 ;
					beginedLayerAge = 0;
				}else{
					beginedLayerAge = (int)growthWeekAge - (long)layWeekAge + 1;
				}
				
				/**入舍鸡数和产蛋周初存栏数 sql查询*/
				String SQL3 = "select " +
						"a.acc_cd + a.cur_amount as placeNum ,a.ytd_amount as LayedBeginAmount " +
						"from s_b_layer_breed_detail a LEFT JOIN (select min(day_age) as tarDayAge from s_b_layer_breed_detail b " +
						"where 1=1 " +
						"and b.house_breed_id = " + HouseBreedId + " " +
						"and b.week_age = s_f_getLayAge(" + HouseBreedId + ",'WeekAge')) as temp on 1=1 " +
						"where a.house_breed_id = " + HouseBreedId + " and a.day_age = ifnull(temp.tarDayAge,200)";
				List<HashMap<String, Object>> tAccData2 = tBaseQueryService.selectMapByAny(SQL3);
				mLogger.info("=========LayerDataInputReqController.queryWR.SQL3 = " + SQL3);
				Object PlaceNum = tAccData2.get(0).get("placeNum");
				Object AmountFirshLayer = tAccData2.get(0).get("LayedBeginAmount");

				if(ViewType.equals("01")) {
                    /**当前数据信息*/
					String SQL4 = "SELECT " +
							"week_age AS week_age, " +
							"SUM(cur_cd) AS culling_all, " +
							"truncate(SUM(cur_cd)/(sum(b.cur_cd) + min(b.cur_amount)) * 1000, 1) AS culling_rate, " +
							"SUM(cur_lay_num) AS curLayNum, " +
							"CASE WHEN is_history = 'Y' THEN truncate((avg(cur_lay_num) / 7) / (sum(b.cur_cd) + min(b.cur_amount)) * 100, 1) " +
							"	ELSE truncate(avg(cur_lay_num) / (sum(b.cur_cd) + min(b.cur_amount)) * 100, 1) END AS curLayRate, " +
							"truncate(SUM(cur_lay_weight),0) AS curLaySumWeight, " +
							"truncate(SUM(cur_lay_weight) * 1000 / SUM(cur_lay_num), 1) AS curLayWeight, " +
							"SUM(cur_lay_broken) AS curBrokenNum, " +
							"truncate(SUM(cur_feed),0) AS weekly_feed, " +
							"truncate(SUM(cur_feed) / (sum(b.cur_cd) + min(b.cur_amount)) / 7 * 1000, 0) AS avg_feed," +
							"truncate(SUM(cur_water),1) AS weekly_water, " +
							"truncate(SUM(cur_water) / (sum(b.cur_cd) + min(b.cur_amount)) / 7 * 1000000,0) AS avg_water," +
                            "ifnull(truncate(SUM(cur_water) / SUM(cur_feed) *1000, 2),'-') AS rOfWM," +
							"truncate(AVG(CASE WHEN cur_weight = 0 THEN NULL ELSE cur_weight END), 2) AS weekly_weight " +
							"FROM s_b_layer_breed_detail b " +
							"WHERE house_breed_id = " + HouseBreedId + " AND growth_date <= CURDATE() " +
							"GROUP BY week_age";
					List<HashMap<String, Object>> TT = tBaseQueryService.selectMapByAny(SQL4);
					mLogger.info("=========LayerDataInputReqController.queryWR.SQL4 = " + SQL4);
					for (HashMap<String, Object> hashMap : TT) {
						JSONObject tJSONObject = new JSONObject();
						Object weekAge = hashMap.get("week_age");
						Object curLayNum = hashMap.get("curLayNum");
						Object culling_all = hashMap.get("culling_all");
						Object culling_rate = hashMap.get("culling_rate");
						Object curLayRate = hashMap.get("curLayRate");
						Object curLaySumWeight = hashMap.get("curLaySumWeight");
						Object curLayAvgWeight = hashMap.get("curLayWeight");
						Object curBrokenNum = hashMap.get("curBrokenNum");
						Object weekly_feed = hashMap.get("weekly_feed");
						Object avg_feed = hashMap.get("avg_feed");
						Object weekly_water = hashMap.get("weekly_water");
						Object avg_water = hashMap.get("avg_water");
						Object chicken_weight = hashMap.get("weekly_weight");
                        Object rOfWM = hashMap.get("rOfWM");
						if (chicken_weight == null) {
							chicken_weight = 0;
						}
						if (curLayAvgWeight == null) {
							curLayAvgWeight = 0;
						}
						tJSONObject.put("growth_weekage", weekAge);
						tJSONObject.put("layer_weekage", layWeekAge);
						tJSONObject.put("curLayNum", curLayNum);
						tJSONObject.put("culling_all", culling_all);
						tJSONObject.put("curLayRate", curLayRate.toString());
						tJSONObject.put("curLaySumWeight", curLaySumWeight.toString());
						tJSONObject.put("cull_rate", culling_rate.toString());
						tJSONObject.put("curLayAvgWeight", curLayAvgWeight.toString());
						tJSONObject.put("curBrokenNum", curBrokenNum);
						tJSONObject.put("weekly_feed", weekly_feed.toString());
						tJSONObject.put("avg_feed", avg_feed.toString());
						tJSONObject.put("chickenWeight", chicken_weight.toString());
						tJSONObject.put("weekly_water", weekly_water.toString());
						tJSONObject.put("avg_water", avg_water.toString());
						tJSONObject.put("rOfWM", rOfWM.toString());
						dataInput.put(tJSONObject);
					}
				} else if(ViewType.equals("02")){
					String SQL5 = "SELECT " +
							"week_age AS week_age, " +
							"MAX(acc_cd) AS culling_all, " +
							"truncate(MAX(acc_cd)/(case WHEN week_age >= "+beginedLayerAge+" then "+AmountFirshLayer+" else "+PlaceNum+" end) * 1000 ,1) AS culling_rate," +
							"MAX(acc_lay_num) AS acc_lay_num, " +
							"truncate(MAX(acc_lay_weight),0) AS acc_lay_weight, " +
							"truncate(MAX(acc_feed),1) AS acc_feed_weight, " +
							"truncate(MAX(acc_feed)/MAX(acc_lay_weight),1) AS rOfFE, " +
							"truncate(MAX(acc_water),1) AS acc_water " +
							"FROM s_b_layer_breed_detail " +
							"WHERE house_breed_id = " + HouseBreedId + " AND growth_date <= CURDATE() " +
							"GROUP BY week_age";
					List<HashMap<String, Object>> tAccData3 = tBaseQueryService.selectMapByAny(SQL5);
					mLogger.info("==========LayerDataInputReqController.queryWR.SQL5 = " + SQL5);
					for (HashMap<String, Object> T : tAccData3){
						JSONObject jJsonObject = new JSONObject();
						Object weekAge = T.get("week_age");
						Object cullingAll = T.get("culling_all");
						Object cullingRate = T.get("culling_rate");
						Object accLayNum = T.get("acc_lay_num");
						
						BigDecimal accLayNumPer = new BigDecimal(accLayNum.toString()).divide(new BigDecimal(AmountFirshLayer.toString()),1,BigDecimal.ROUND_HALF_UP);
						Object accLayWeight = T.get("acc_lay_weight");
						BigDecimal accLayWeightPer = new BigDecimal(accLayWeight.toString()).divide(new BigDecimal(AmountFirshLayer.toString()),2,BigDecimal.ROUND_HALF_UP);
						Object accFeedWeight = T.get("acc_feed_weight");
						BigDecimal accFeedWeightPer = new BigDecimal(accFeedWeight.toString()).divide(new BigDecimal(AmountFirshLayer.toString()),1,BigDecimal.ROUND_HALF_UP);
						
						Object rOfFE = T.get("rOfFE");
						Object accWater = T.get("acc_water");

						jJsonObject.put("growth_weekage", weekAge);
						jJsonObject.put("layer_weekage", layWeekAge);
                        jJsonObject.put("accCD", cullingAll);
						jJsonObject.put("accCDRate", cullingRate.toString());
						jJsonObject.put("accLayNum",new BigDecimal(accLayNum.toString()).divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP).toString());
						jJsonObject.put("accLayWeight", accLayWeight);
						jJsonObject.put("accLayNumPer", accLayNumPer.toString());
						jJsonObject.put("accLayWeightPer", accLayWeightPer.toString());
						jJsonObject.put("accFeedWeight", accFeedWeight.toString());
						jJsonObject.put("accFeedWeightPer", accFeedWeightPer.toString());
						jJsonObject.put("rOfFE", rOfFE != null?rOfFE.toString():"-");
						jJsonObject.put("accWater", accWater.toString());
						dataInput.put(jJsonObject);
					}
				}
				resJson.put("weekData", dataInput);
				resJson.put("Result", "Success");
				resJson.put("HouseBreedId", HouseBreedId);
				resJson.put("HouseId", HouseId);
				resJson.put("DataInfos", curDate);
				resJson.put("CurGrowthWeekAge", growthWeekAge);
				resJson.put("CurLayerWeekAge", layWeekAge);
				resJson.put("PlaceNum", PlaceNum);
				resJson.put("AmountFirstLayer", AmountFirshLayer);
			}else{
	    	resJson.put("Result","Fail");
		    }
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
		mLogger.info("=====Now end executing LayerDataInputReqController.queryWR");
	}
	

	@RequestMapping("DailyReport")
	public void DailyReport(HttpServletResponse response,HttpServletRequest request){
		mLogger.info("=====Now start executing LayerDataInputReqController.DailyReport");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			//** 业务处理开始，查询、增加、修改、或删除 **//
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int FarmBreedId = tHouseJson.getInt("FarmBreedId");
			int HouseId = tHouseJson.getInt("HouseId");
			int WeekAgeBegin = tHouseJson.optInt("WeekAgeBegin");
			int WeekAgeEnd = tHouseJson.optInt("WeekAgeEnd");
			String SQL1 ="SELECT hb.id AS HouseBreedId, hb.place_num ,DATE_FORMAT(fb.place_date ,'%Y-%m-%d') as PlaceDate "
					+ "FROM s_b_layer_house_breed AS hb LEFT JOIN  s_b_layer_farm_breed  AS fb ON  hb.farm_breed_id = fb.id "
                        +"where hb.house_id ="+HouseId+" and hb.farm_breed_id="+FarmBreedId;
			mLogger.info("====LayerDataInputReqController.DailyReport.SQL1=" + SQL1);
			List<HashMap<String,Object>> ff = tBaseQueryService.selectMapByAny(SQL1);
		    if(ff.size()==1){
		    	JSONArray weekData =new JSONArray();
		    	Object  HouseBreedId=ff.get(0).get("HouseBreedId");
		    	Object  PlaceDate=ff.get(0).get("PlaceDate");
		    	Integer  PlaceNum=Integer.parseInt(ff.get(0).get("place_num").toString());
		    	String SQL2 ="select ifnull(min(day_age),0) as layerage , (SELECT day_age FROM s_b_layer_breed_detail WHERE house_breed_id = " + HouseBreedId + " AND growth_date=CURDATE() ) AS CurGrowthAge from s_b_layer_breed_detail where acc_lay_num>0 and house_breed_id="+HouseBreedId;
				mLogger.info("SQL2=" + SQL2);
				List<HashMap<String,Object>> gg = tBaseQueryService.selectMapByAny(SQL2);
				Integer  CurGrowthAge = Integer.parseInt( gg.get(0).get("CurGrowthAge").toString());
				int tt =  Integer.parseInt( gg.get(0).get("layerage").toString());
				int  CurLayerAge = CurGrowthAge - tt + 1;
				if(CurLayerAge < 0){
					CurLayerAge = 0;
				}
				String SQL3 =null;
				if(WeekAgeEnd == 0&&WeekAgeBegin==0 ){
					 SQL3 = "SELECT day_age AS growth_age, CASE WHEN day_age < "+tt+"  THEN  0 ELSE day_age-"+tt+" + 1 END AS layer_age,"
				    			+ "  cur_cd AS  culling_all, cur_amount AS curAmount, cur_lay_num AS curLayNum,"
				    			+ "  truncate(ROUND(cur_lay_num/cur_amount*100,1),1)  AS curLayRate, truncate(ROUND(cur_lay_weight,0),0) AS curLaySumWeight,"
				    			+ "  truncate(ROUND(cur_lay_weight*1000/cur_lay_num,1),1) AS curLayAvgWeight, cur_lay_broken AS curBrokenNum, "
							    + "  truncate(ROUND(cur_feed,0),0) AS daily_feed,"
				    			+ "  truncate(round(cur_water),1) AS daily_water , "
							    + "  truncate(ifnull(ROUND(cur_water / cur_feed * 1000, 2),'-'),2) AS rOfWM, if(cur_weight=0,'-',cur_weight) AS chickenWeight"
				    			+ " FROM s_b_layer_breed_detail  where 1=1 "
				    			+ "and house_breed_id="+HouseBreedId+" "
				    			+ "AND growth_date <= curDate()  "
				    			+ "AND growth_date>date_sub(CURDATE(),interval 30 day)";
				}else{
					 SQL3 = "SELECT day_age AS growth_age, CASE WHEN day_age < "+tt+"  THEN  0 ELSE day_age-"+tt+" + 1 END AS layer_age,"
				    			+ "  cur_cd AS  culling_all, cur_amount AS curAmount, cur_lay_num AS curLayNum,"
				    			+ "  truncate(ROUND(cur_lay_num/cur_amount*100,1),1)  AS curLayRate, truncate(ROUND(cur_lay_weight,0),0) AS curLaySumWeight,"
				    			+ "  truncate(ROUND(cur_lay_weight*1000/cur_lay_num,1),1) AS curLayAvgWeight, cur_lay_broken AS curBrokenNum, "
							    + "  truncate(ROUND(cur_feed,0),0) AS daily_feed,"
				    			+ "  truncate(round(cur_water,1),1) AS daily_water , "
							    + "  truncate(ifnull(ROUND(cur_water / cur_feed * 1000, 2),'-'),2) AS rOfWM, if(cur_weight=0,'-',cur_weight) AS chickenWeight"
                                + " FROM s_b_layer_breed_detail where 1=1 "
                                + "and house_breed_id="+HouseBreedId+" "
                                + "and week_age BETWEEN "+WeekAgeBegin+"  AND "+WeekAgeEnd;
				}
		    	mLogger.info("=========LayerDataInputReqController.DailyReport.SQL3=" + SQL3);
		    	List<HashMap<String,Object>> TT = tBaseQueryService.selectMapByAny(SQL3);
		    	for (HashMap<String, Object> hashMap : TT) {
		    		JSONObject tJSONObject =new JSONObject();
		    		Object growth_age = hashMap.get("growth_age");
		    		Object layer_age = hashMap.get("layer_age");
		    		if(layer_age==null){
		    			layer_age = 0;
		    		}
		    		Object culling_all = hashMap.get("culling_all");
		    		if(culling_all==null){
		    			culling_all = 0;
		    		}
		    		Object curAmount = hashMap.get("curAmount");
		    		if(curAmount==null){
		    			curAmount = 0;
		    		}
		    		Object curLayNum = hashMap.get("curLayNum");
		    		if(curLayNum==null){
		    			curLayNum = 0;
		    		}
		    		Object curLayRate = hashMap.get("curLayRate");
		    		if(curLayRate==null){
		    			curLayRate = 0;
		    		}
		    		Object curLaySumWeight = hashMap.get("curLaySumWeight");
		    		if(curLaySumWeight==null){
		    			curLaySumWeight = 0;
		    		}
		    		Object curLayAvgWeight = hashMap.get("curLayAvgWeight");
		    		if(curLayAvgWeight==null){
		    			curLayAvgWeight = 0;
		    		}
		    		Object curBrokenNum = hashMap.get("curBrokenNum");
		    		if(curBrokenNum==null){
		    			curBrokenNum = 0;
		    		}
		    		Object daily_feed = hashMap.get("daily_feed");
		    		if(daily_feed==null){
		    			daily_feed = 0;
		    		}
		    		Object daily_water = hashMap.get("daily_water");
		    		if(daily_water==null){
		    			daily_water = 0;
		    		}
		    		Object rOfWM = hashMap.get("rOfWM");
		    		if(rOfWM==null){
		    			rOfWM = 0;
		    		}
		    		Object chickenWeight = hashMap.get("chickenWeight");
		    		if(chickenWeight==null){
		    			chickenWeight = 0;
		    		}
		    		tJSONObject.put("growth_age", growth_age);
		    		tJSONObject.put("layer_age", layer_age);
		    		tJSONObject.put("culling_all", culling_all);
		    		tJSONObject.put("curAmount", curAmount);
		    		tJSONObject.put("curLayNum", curLayNum);
		    		tJSONObject.put("curLayRate", curLayRate.toString());
		    		tJSONObject.put("curLaySumWeight", curLaySumWeight.toString());
		    		tJSONObject.put("curLayAvgWeight", curLayAvgWeight.toString());
		    		tJSONObject.put("curBrokenNum", curBrokenNum);
		    		tJSONObject.put("daily_feed", daily_feed.toString());
		    		tJSONObject.put("daily_water", daily_water.toString());
		    		tJSONObject.put("rOfWM", rOfWM.toString());
		    		tJSONObject.put("chickenWeight", chickenWeight);
		    		weekData.put(tJSONObject);
		    	}
		    	resJson.put("weekData",weekData);	
		    	resJson.put("Result","Success");	
		    	resJson.put("HouseBreedId",HouseBreedId);	
		    	resJson.put("HouseId",HouseId);	
		    	resJson.put("CurLayerAge",tt == 0 ? 0 : CurLayerAge);
		    	resJson.put("CurGrowthAge",CurGrowthAge);	
		    	resJson.put("PlaceDate",PlaceDate);	
		    	resJson.put("PlaceNum",PlaceNum);	
		    }else{
		    	resJson.put("Result","Fail");
		    }
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
		mLogger.info("=====Now end executing LayerDataInputReqController.DailyReport");
	}	
}
