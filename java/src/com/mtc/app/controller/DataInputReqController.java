/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.mtc.app.biz.DataInputReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.MySQLSPService;
import com.mtc.app.service.SBBreedDetailService;
import com.mtc.app.service.SBChickenStandarService;
import com.mtc.app.service.SBDataInputService;
import com.mtc.app.service.SBFarmBreedService;
import com.mtc.app.service.SBHouseBreedService;
import com.mtc.app.service.SDHouseService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBBreedDetail;
import com.mtc.entity.app.SBChickenStandar;
import com.mtc.entity.app.SBDataInput;
import com.mtc.entity.app.SBFarmBreed;
import com.mtc.entity.app.SBHouseBreed;

/**
 * @ClassName: DataInputReqController
 * @Description: 
 * @Date 2015-12-7 下午8:34:36
 * @Author Shao Yao Yu
 * 
 */
@Controller
@RequestMapping("/dataInput")
public class DataInputReqController {
	private static Logger mLogger =Logger.getLogger(DataInputReqController.class);
	@Autowired
	private BaseQueryService tBaseQueryService;
	@Autowired
	private  SBFarmBreedService tSBFarmBreedService;
	@Autowired
	private  SDHouseService tSDHouseService;
	@Autowired
	private  SBBreedDetailService tSBBreedDetailService;
	@Autowired
    private	SBDataInputService tSBDataInputService;
	@Autowired
	private SBChickenStandarService tSBChickenStandarService;
	@Autowired
	private MySQLSPService tMySQLSPService;
	@Autowired
	private  SBHouseBreedService tSBHouseBreedService;
	@Autowired
	private DataInputReqManager tDataInputReqManager;
	@RequestMapping("/saveDOC")
	public void saveDOC(HttpServletRequest request,HttpServletResponse  response){
        mLogger.info("=====Now start executing DataInputReqController.saveDOC");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveUser.para=" + paraStr);
	        
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			int id_spa = jsonObject.getInt("id_spa");
			JSONObject tJSONObject = jsonObject.getJSONObject("params");
			
			int HouseId = tJSONObject.getInt("HouseId");
			int PlaceNum = tJSONObject.getInt("PlaceNum");
			String PlaceDate = tJSONObject.getString("PlaceDate");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date placeDate=sdf.parse(PlaceDate);
			int farmId = tSDHouseService.selectByPrimaryKey(HouseId).getFarmId();
			String sql = "SELECT a.id FROM s_b_farm_breed  a WHERE a.farm_id ='"+farmId+"' AND DATE(a.place_date) <= '"+PlaceDate+"' AND DATE(a.market_date)>='"+PlaceDate+"'"
					+ " and a.batch_status= '01' ";
			mLogger.info("======DataInputReqController.saveDOC.SQL=" + sql);
			Integer farmBreedId = tBaseQueryService.selectIntergerByAny(sql);
			String sql1 = "SELECT COUNT(id) FROM s_b_house_breed WHERE  farm_breed_id = '"+farmBreedId+"' AND house_id = " +HouseId;
			mLogger.info("=====DataInputReqController.saveDOC.SQL=" + sql1);
			Integer udged = tBaseQueryService.selectIntergerByAny(sql1);
			if(farmBreedId!=null){
				if(udged!=0){
					resJson.put("Result", "Fail");
					resJson.put("Exception", "入雏失败，该日期的栋舍批次已经出栏。");
				}else if(udged==0){
					SBFarmBreed tSBFarmBreed= tSBFarmBreedService.selectByPrimaryKey(farmBreedId);
					
					SBHouseBreed tSBHouseBreed = new SBHouseBreed();
					tSBHouseBreed.setHouseId(HouseId);
					tSBHouseBreed.setFarmId(farmId);
					tSBHouseBreed.setFarmBreedId(farmBreedId);
					Date date = new Date();
					tSBHouseBreed.setPlaceNum(PlaceNum);
					tSBHouseBreed.setPlaceDate(placeDate);
					tSBHouseBreed.setCreateDate(date);
					tSBHouseBreed.setCreatePerson(id_spa);
					tSBHouseBreed.setCreateTime(date);
					tSBHouseBreed.setBatchStatus("01");
					tSBHouseBreed.setModifyDate(date);
					tSBHouseBreed.setModifyPerson(id_spa);
					tSBHouseBreed.setModifyTime(date);
					HashMap<String,Object> mParas = new HashMap<String,Object>(); 
					mParas.put("SBHouseBreed", tSBHouseBreed);
					
					Date marketDate = tSBFarmBreed.getMarketDate();
					// 10天的容差
					marketDate = PubFun.addDate(marketDate,10);
					Date tempDate = placeDate;
					SBBreedDetail  tSBBreedDetail = null;
					List<SBBreedDetail> tSBBreedDetails = new ArrayList<SBBreedDetail>();
					for(int i = 0; tempDate.before(marketDate);i++){
						tempDate = PubFun.addDate(placeDate, i);
						tSBBreedDetail = new SBBreedDetail();
						tSBBreedDetail.setAge(i);
						tSBBreedDetail.setDeathAm(0);
						tSBBreedDetail.setDeathPm(0);
						tSBBreedDetail.setCullingAm(0);
						tSBBreedDetail.setCullingPm(0);
						tSBBreedDetail.setCurCd(0);
						tSBBreedDetail.setAccCd(0);
						tSBBreedDetail.setAccCd(0);
						tSBBreedDetail.setCurWeight(PubFun.getBigDecimalData("0"));
						tSBBreedDetail.setCurAmount(PlaceNum);
						tSBBreedDetail.setYtdAmount(PlaceNum);
						tSBBreedDetail.setCurFeed(PubFun.getBigDecimalData("0"));
						tSBBreedDetail.setAccFeed(PubFun.getBigDecimalData("0"));
						tSBBreedDetail.setCurWeight(PubFun.getBigDecimalData("0"));
						tSBBreedDetail.setCreatePerson(id_spa);
						tSBBreedDetail.setCreateDate(date);
						tSBBreedDetail.setCreateTime(date);
						tSBBreedDetail.setModifyDate(date);
						tSBBreedDetail.setModifyTime(date);
						tSBBreedDetail.setModifyPerson(id_spa);
						tSBBreedDetail.setGrowthDate(tempDate);
						tSBBreedDetails.add(tSBBreedDetail);
					}
					mParas.put("SBBreedDetails",tSBBreedDetails);
					
					SBDataInput tSBDataInput = new SBDataInput();
					tSBDataInput.setFarmBreedId(farmBreedId);
					tSBDataInput.setFarmId(farmId);
					tSBDataInput.setHouseId(HouseId);
					tSBDataInput.setDataType("D0007");
					tSBDataInput.setValueType("01");
					tSBDataInput.setValInt(PlaceNum);
					tSBDataInput.setValDate(date);
					tSBDataInput.setAge(0);
					tSBDataInput.setFreezeStatus("0");
					tSBDataInput.setCreateDate(date);
					tSBDataInput.setCreateTime(date);
					tSBDataInput.setCreatePerson(id_spa);
					tSBDataInput.setModifyDate(date);
					tSBDataInput.setModifyTime(date);
					tSBDataInput.setModifyPerson(id_spa);
					mParas.put("SBDataInput",tSBDataInput);
					
					SBHouseBreed mSBHouseBreed = tDataInputReqManager.saveSBHouseBreed(mParas);
					
					HashMap<String, Object> tHashMap = new HashMap<String, Object>();
		            tHashMap.put("in_buzType", "0");
		            tHashMap.put("in_houseBreedId", mSBHouseBreed.getId());
		            tMySQLSPService.exec_s_p_createTargetMonitor(tHashMap);
					
					resJson.put("Result", "Success");
					resJson.put("HouseBreedId", mSBHouseBreed.getId());
					resJson.put("HouseId", HouseId);
				}
			}else{
				resJson.put("Result", "Fail");
				resJson.put("Exception", "入雏失败，请先进行农场批次设定。");
			}
			dealRes = Constants.RESULT_SUCCESS ;
			/** 业务处理结束 **/
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
		mLogger.info("=====Now end executing DataInputReqController.saveDOC");
	}
	@RequestMapping("/queryDOC")
	public void queryDOC(HttpServletRequest request,HttpServletResponse  response){
		 mLogger.info("=====Now start executing DataInputReqController.queryDOC");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("saveUser.para=" + paraStr);
		        
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject tJSONObject = jsonObject.getJSONObject("params");
				int HouseBreedId = tJSONObject.getInt("HouseBreedId");
				int HouseId = tJSONObject.getInt("HouseId");
				SBHouseBreed tSBHouseBreed = tSBHouseBreedService.selectByPrimaryKey(HouseBreedId);
				if(tSBHouseBreed!=null){
					resJson.put("Result", "Success");
					resJson.put("HouseId", HouseId);
					resJson.put("PlaceNum", tSBHouseBreed.getPlaceNum());
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					String PlaceDate=sdf.format(tSBHouseBreed.getPlaceDate());
					resJson.put("PlaceDate",PlaceDate );
					dealRes = Constants.RESULT_SUCCESS ;
				}else{
					resJson.put("Result", "Fail");
					dealRes = Constants.RESULT_FAIL ;
				}
				//** 业务处理结束 **//*
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
			mLogger.info("=====Now end executing DataInputReqController.queryDOC");
	}
	@RequestMapping("/ModifyDOC")
	public void ModifyDOC(HttpServletRequest request,HttpServletResponse  response){
		 mLogger.info("=====Now start executing DataInputReqController.ModifyDOC");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("saveUser.para=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				Boolean result = true ;
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				int id_spa = jsonObject.getInt("id_spa");
				JSONObject tJSONObject = jsonObject.getJSONObject("params");
				Date date = new Date();
				int HouseBreedId = tJSONObject.getInt("HouseBreedId");
				String PlaceDate = tJSONObject.getString("PlaceDate");
				SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
				Date placeDate = sdf.parse(PlaceDate);
				int HouseId = tJSONObject.getInt("HouseId");
				int PlaceNum = tJSONObject.getInt("PlaceNum");
				
				SBHouseBreed tSBHouseBreed = tSBHouseBreedService.selectByPrimaryKey(HouseBreedId);
				
				Integer oldPlaceNum = tSBHouseBreed.getPlaceNum();
				
				if(HouseId!=tSBHouseBreed.getHouseId()){
					result = false ;
					resJson.put("Result","Fail");
					resJson.put("Exception","栋舍养殖批次号与栋舍号不匹配。");
				}else if(!placeDate.equals(tSBHouseBreed.getPlaceDate())){
					result = false ;
					resJson.put("Result","Fail");
					resJson.put("Exception","入雏日期不允许修改");
				}else if("02".equals(tSBHouseBreed.getBatchStatus())){
					result = false ;
					resJson.put("Result","Fail");
					resJson.put("Exception","该栋舍已经出栏，不允许进行修改。");
				}else{
    				tSBHouseBreed.setPlaceNum(PlaceNum);
    				tSBHouseBreed.setModifyDate(date);
    				tSBHouseBreed.setModifyPerson(id_spa);
    				tSBHouseBreed.setModifyTime(date);
    				HashMap<String,Object> mParas = new HashMap<String,Object>(); 
    				mParas.put("SBHouseBreed", tSBHouseBreed);
    				SBDataInput tSBDataInput = tSBDataInputService.selectBySBHouseBreedId(HouseBreedId,"D0007",0);
    				if(tSBDataInput==null){
					  SBDataInput mSBDataInput = new  SBDataInput();
					  mSBDataInput.setAge(0);
					  mSBDataInput.setDataType("D0007");
					  mSBDataInput.setFarmBreedId(tSBHouseBreed.getFarmBreedId());
					  mSBDataInput.setHouseBreedId(HouseBreedId);
					  mSBDataInput.setFarmId(tSBHouseBreed.getFarmId());
					  mSBDataInput.setHouseId(tSBHouseBreed.getHouseId());
					  mSBDataInput.setValueType("01");
					  mSBDataInput.setValInt(PlaceNum);
					  mSBDataInput.setAge(0);
					  mSBDataInput.setFreezeStatus("0");
					  mSBDataInput.setCreateDate(date);
					  mSBDataInput.setCreatePerson(id_spa);
					  mSBDataInput.setCreateTime(date);
					  mSBDataInput.setModifyDate(date);
					  mSBDataInput.setModifyTime(date);
					  mSBDataInput.setModifyPerson(id_spa);
					  mParas.put("SBDataInput", mSBDataInput);
    				}else{
					  tSBDataInput.setModifyDate(date);
					  tSBDataInput.setModifyTime(date);
					  tSBDataInput.setModifyPerson(id_spa);
    				  mParas.put("SBDataInput", tSBDataInput);
    				}
    				List<SBBreedDetail> tSBBreedDetail = tSBBreedDetailService.selectByhouseBreedId(HouseBreedId);
    				for (SBBreedDetail sbBreedDetail : tSBBreedDetail) {
    					int dd1 = sbBreedDetail.getCurAmount() + (PlaceNum-oldPlaceNum);
    					int dd2 = sbBreedDetail.getYtdAmount() + (PlaceNum-oldPlaceNum);
    					sbBreedDetail.setCurAmount(dd1);
    					sbBreedDetail.setYtdAmount(dd2);
    					sbBreedDetail.setModifyDate(date);
    					sbBreedDetail.setModifyPerson(id_spa);
    					sbBreedDetail.setModifyTime(date);
					}
    				mParas.put("List<SBBreedDetail>", tSBBreedDetail);
    			    tDataInputReqManager.updateSBHouseBreed(mParas);
    				resJson.put("Result","Success");
    				resJson.put("HouseBreedId",HouseBreedId);
    				resJson.put("HouseId",HouseId);
				}
				dealRes = Constants.RESULT_SUCCESS ;
				//** 业务处理结束 **//*
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
			mLogger.info("=====Now end executing DataInputReqController.ModifyDOC");
	}
	@RequestMapping("/queryDR")
	public void queryDR(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing DataInputReqController.queryDR");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("saveUser.para=" + paraStr);
		        
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//
				JSONObject tJSONObject = jsonObject.getJSONObject("params");
				int FarmBreedId = tJSONObject.getInt("FarmBreedId");
				int HouseId = tJSONObject.getInt("HouseId");
				SBHouseBreed tSBHouseBreed = tSBHouseBreedService.selectByFarmBreedId(FarmBreedId,HouseId);
				int HouseBreedId = tSBHouseBreed.getId();
				String sql = "SELECT c.house_name,b.cur_amount,a.place_num,"
						+ "b.acc_cd,"
						+ "round(b.num_bak2) as acc_water,"
						+ "round(b.acc_feed,2) as acc_feed,"
						+ "concat(round(b.acc_cd/a.place_num*100, 2),'%') as acc_cd_rate,"
						+ "b.age "
						+ "FROM s_b_house_breed  a "
						+ "LEFT JOIN s_b_breed_detail  b ON a.id = b.house_breed_id "
						+ "INNER JOIN s_d_house  c ON  a.house_id = c.id  "
						+ "WHERE  a.farm_breed_id = "+FarmBreedId+" AND a.house_id = "+HouseId+" AND b.growth_date = ifnull(a.market_date,curdate()) ";
				mLogger.info("=======DataInputReqController.queryDR.SQL=" + sql);
				List<HashMap<String, Object>>  atu_cd_rate = tBaseQueryService.selectMapByAny(sql);
			    if(atu_cd_rate.size()!=0){
		    	    Object  place_num =  atu_cd_rate.get(0).get("place_num");
				    Object  cur_amount = atu_cd_rate.get(0).get("cur_amount");
				    Object  house_name = atu_cd_rate.get(0).get("house_name");
				    Object  acc_cd_rate = atu_cd_rate.get(0).get("acc_cd_rate");
				    Object  acc_cd = atu_cd_rate.get(0).get("acc_cd");
				    Object  acc_water = atu_cd_rate.get(0).get("acc_water");
				    Object  acc_feed = atu_cd_rate.get(0).get("acc_feed");
				    Object  CurDayAge = atu_cd_rate.get(0).get("age");
				    
					resJson.put("HouseBreedId",HouseBreedId);
					resJson.put("HouseId",HouseId);
					resJson.put("HouseName",house_name);
					resJson.put("CurDayAge",CurDayAge);
					resJson.put("cur_amount",cur_amount);
					resJson.put("culling_acc",acc_cd);
					resJson.put("acc_water",acc_water);
					resJson.put("acc_feed",acc_feed);
					resJson.put("acc_cd_rate",acc_cd_rate);
					resJson.put("original_amount",place_num);
					
					JSONArray dataInput = new JSONArray();
				    List<SBBreedDetail>	mSBBreedDetail = tSBBreedDetailService.selectByhouseBreedId(HouseBreedId);
					for (SBBreedDetail sbBreedDetail : mSBBreedDetail) {
						JSONObject mJSONObject = new JSONObject();
						mJSONObject.put("day_age", sbBreedDetail.getAge());
						// 当日死淘数
						mJSONObject.put("culling_all", sbBreedDetail.getCurCd());
						// 累计死淘数
						mJSONObject.put("culling_acc", sbBreedDetail.getAccCd());						
						// 累计死淘率
						BigDecimal accCd = new BigDecimal(sbBreedDetail.getAccCd()*100);
						BigDecimal ori_amount = new BigDecimal((int)place_num);
						double acc_atu_cd_rate= accCd.divide(ori_amount,3,BigDecimal.ROUND_HALF_UP).doubleValue();
						String acc_atu_cd_rate_str = PubFun.formatDoubleNum(acc_atu_cd_rate,2);
						mJSONObject.put("acc_cd_rate", acc_atu_cd_rate_str);
						// 当日采食量
						mJSONObject.put("daily_feed", sbBreedDetail.getCurFeed());
						// 当日耗水
						mJSONObject.put("daily_water", sbBreedDetail.getNumBak1());
						// 累计采食量 
						mJSONObject.put("acc_feed", sbBreedDetail.getAccFeed());
						
						mJSONObject.put("death_am", sbBreedDetail.getDeathAm());
						mJSONObject.put("death_pm", sbBreedDetail.getDeathPm());
						mJSONObject.put("culling_am", sbBreedDetail.getCullingAm());
						mJSONObject.put("culling_pm", sbBreedDetail.getCullingPm());
						// 当日均重
						mJSONObject.put("daily_weight", sbBreedDetail.getCurWeight());
						dataInput.put(mJSONObject);
					}
				   resJson.put("dataInput",dataInput);
			    }else{
			    	   resJson.put("Result","Fail");
			    }
				//** 业务处理结束 **//
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
			mLogger.info("=====Now end executing DataInputReqController.queryDR");
	}
	@RequestMapping("/saveDR")
	public void saveDR(HttpServletRequest request,HttpServletResponse  response){
		 mLogger.info("=====Now start executing DataInputReqController.saveDR");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("saveUser.para=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				int modifyPerson = jsonObject.getInt("id_spa");
				JSONObject tJSONObject = jsonObject.getJSONObject("params");
			    int HouseBreedId =	 tJSONObject.getInt("HouseBreedId");
			    int HouseId =	 tJSONObject.getInt("HouseId");
			    JSONArray mJSONArray = tJSONObject.getJSONArray("dataInput");
			    ArrayList<SBBreedDetail> listSBBreedDetail = new ArrayList<SBBreedDetail>();
		        for(int i=0; i<mJSONArray.length();i++){
			    	 int age = mJSONArray.getJSONObject(i).getInt("day_age");  
		    	     int death_am = mJSONArray.getJSONObject(i).getInt("death_am");  
			    	 int death_pm = mJSONArray.getJSONObject(i).getInt("death_pm");  
			    	 int culling_am = mJSONArray.getJSONObject(i).getInt("culling_am");  
			    	 int culling_pm = mJSONArray.getJSONObject(i).getInt("culling_pm");  
			    	 int culling_all = mJSONArray.getJSONObject(i).getInt("culling_all"); 
			    	 String daily_feed = mJSONArray.getJSONObject(i).getString("daily_feed");  
			    	 String daily_weight = mJSONArray.getJSONObject(i).getString("daily_weight");  
			    	 String daily_water = mJSONArray.getJSONObject(i).getString("daily_water");  
			    	 
			    	 SBBreedDetail tSBBreedDetail = tSBBreedDetailService.selectByPrimaryKey(HouseBreedId,age);
				   	 tSBBreedDetail.setDeathPm(death_pm);
					 tSBBreedDetail.setCullingPm(culling_pm);
					 tSBBreedDetail.setCurFeed(PubFun.getBigDecimalData(daily_feed));
					 tSBBreedDetail.setCurWeight(PubFun.getBigDecimalData(daily_weight));
					 tSBBreedDetail.setNumBak1(PubFun.getBigDecimalData(daily_water));
					 listSBBreedDetail.add(tSBBreedDetail);
		        }
		        HashMap<String,Object> mParas = new HashMap<String,Object>(); 
		        List<SBBreedDetail> tSBBreedDetail = tSBBreedDetailService.selectByhouseBreedId(HouseBreedId);
		        if(tSBBreedDetail.size()==listSBBreedDetail.size()){
		        	mParas.put("listSBBreedDetail", listSBBreedDetail);
			        mParas.put("modifyPerson", modifyPerson);
			        mParas.put("HouseBreedId", HouseBreedId);
					tDataInputReqManager.saveDR(mParas);
					resJson.put("LoginResult", "Success");
					resJson.put("HouseId", HouseId);
		        }else{
		        	resJson.put("Exception","参数异常,日龄数不对");
		        }
				//** 业务处理结束 **//*
				dealRes = Constants.RESULT_SUCCESS ;
			}catch (Exception e){
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
			mLogger.info("=====Now end executing DataInputReqController.saveDR");
	}

	@RequestMapping("/DRShow")
	public void DRShow(HttpServletRequest request,HttpServletResponse  response){
		 mLogger.info("=====Now start executing DataInputReqController.DRShow");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("saveUser.para=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject tJSONObject = jsonObject.getJSONObject("params"); 
			    int FarmBreedId = tJSONObject.getInt("FarmBreedId");
			    int HouseId = tJSONObject.getInt("HouseId");
			    Date date1 = new Date();
    			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    			String date = sdf.format(date1);
			    String SQL="SELECT b.age ,a.id,a.Place_date,a.place_num ,b.acc_feed,b.acc_cd,b.cur_amount,c.house_name,b.acc_cd/a.place_num  as atu_acc_cd_rate FROM s_b_house_breed a  INNER JOIN s_b_breed_detail b ON  a.id = b.house_breed_id INNER JOIN  s_d_house c ON a.house_id = c.id where a.farm_breed_id = "+FarmBreedId+" AND c.id = "+HouseId+" and greatest(datediff(curdate(),a.place_date),0) = b.age ";
			    mLogger.info("SQL=" + SQL);
			    List<HashMap<String,Object>>  listMap = tBaseQueryService.selectMapByAny(SQL);
				if(listMap.size()!=0){
					int  age = (int) listMap.get(0).get("age");
					int      HouseBreedId = (int) listMap.get(0).get("id");
					Object		HouseName = listMap.get(0).get("house_name");
					Object		CurDate = listMap.get(0).get("Place_date");
					Object      CurDayAge = age;
					Object      acc_feed = listMap.get(0).get("acc_feed");
					Object		cur_amount = listMap.get(0).get("cur_amount");
					Object		original_amount = listMap.get(0).get("place_num");
					Object		acc_cd = listMap.get(0).get("acc_cd");
					Object		std_acc_cd_rate = "2.23%";
					String		atu_acc_cd_rate = listMap.get(0).get("atu_acc_cd_rate").toString();
				    Double atu_acc_cd_rate1 = Double.valueOf(atu_acc_cd_rate);
				    Double atu_acc_cd_rate2 = atu_acc_cd_rate1*100;
				    String  atu_acc_cd_rate3 = PubFun.formatDoubleNum(atu_acc_cd_rate2,2);
				    String atu_acc_cd_rate4 = atu_acc_cd_rate3+"%";
					resJson.put("Result", "Success");
					resJson.put("HouseBreedId", HouseBreedId);
					resJson.put("HouseId", HouseId);
					resJson.put("HouseName", HouseName);
					resJson.put("acc_feed",acc_feed);
					resJson.put("CurDate",date);
					resJson.put("std_acc_cd", 100);
					resJson.put("atu_acc_cd", acc_cd);
					resJson.put("CurDayAge", CurDayAge);
					resJson.put("cur_amount",cur_amount);
					resJson.put("cur_amount",cur_amount);
					resJson.put("original_amount", original_amount);
					resJson.put("std_acc_cd_rate",std_acc_cd_rate);
					resJson.put("atu_acc_cd_rate", atu_acc_cd_rate4);
					List<SBBreedDetail> tSBBreedDetail = tSBBreedDetailService.selectByhouseBreedId(HouseBreedId);
					
					List<SBChickenStandar> tChickenInfo = tSBChickenStandarService.selectStandarInfoByFeedType("mixed");
					JSONArray dataInput = new JSONArray();
					for (SBBreedDetail sbBreedDetail : tSBBreedDetail) {
						JSONObject mJSONObject = new JSONObject();
						// 日龄
						mJSONObject.put("day_age", sbBreedDetail.getAge());
						// 日死淘数
						mJSONObject.put("atu_cd_num", sbBreedDetail.getCurCd());
						// 当日存栏数
						mJSONObject.put("daily_amount",sbBreedDetail.getCurAmount());
						// 当日死淘率
						BigDecimal curCd = new BigDecimal(sbBreedDetail.getCurCd()*1000);
						BigDecimal curAmount = new BigDecimal(sbBreedDetail.getCurAmount()+sbBreedDetail.getYtdAmount())
									.divide(new BigDecimal(2));
						double atu_cd_rate= curCd.divide(curAmount,3,BigDecimal.ROUND_HALF_UP).doubleValue();
						String atu_cd_rate_str = PubFun.formatDoubleNum(atu_cd_rate,1) + "‰" ;
						mJSONObject.put("atu_cd_rate",atu_cd_rate_str);
						// 累计死淘数
						mJSONObject.put("acc_atu_cd_num",sbBreedDetail.getAccCd());
						// 累计死淘率
						BigDecimal accCd = new BigDecimal(sbBreedDetail.getAccCd()*100);
						BigDecimal ori_amount = new BigDecimal((int)original_amount);
						double acc_atu_cd_rate= accCd.divide(ori_amount,3,BigDecimal.ROUND_HALF_UP).doubleValue();
						
						String acc_atu_cd_rate_str = PubFun.formatDoubleNum(acc_atu_cd_rate,1) + "%";
						mJSONObject.put("acc_atu_cd_rate",acc_atu_cd_rate_str);
						// 日均重
						mJSONObject.put("daily_weight",sbBreedDetail.getCurWeight());
						// 日采食量
						mJSONObject.put("daily_feed", sbBreedDetail.getCurFeed());
						// 累计采食量
						mJSONObject.put("acc_daily_feed", sbBreedDetail.getAccFeed());
						// 累计单只采食量
						BigDecimal accFeed = sbBreedDetail.getAccFeed().multiply(new BigDecimal(1000));
						BigDecimal c_amount = new BigDecimal(sbBreedDetail.getYtdAmount()+sbBreedDetail.getCurAmount())
												.divide(new BigDecimal(2));
						double acc_daily_feed_per = accFeed.divide(c_amount,3,BigDecimal.ROUND_HALF_UP).doubleValue();
						String acc_daily_feed_per_str = PubFun.formatDoubleNum(acc_daily_feed_per,0);
						mJSONObject.put("acc_daily_feed_per", acc_daily_feed_per_str);
						
						// 饲料转换率
						double CurWeight = sbBreedDetail.getCurWeight().doubleValue();
						if(CurWeight !=0 && acc_daily_feed_per !=0 ){
							double feed_conversion = acc_daily_feed_per/CurWeight;
					        mJSONObject.put("feed_conversion",PubFun.formatDoubleNum(feed_conversion,2));
							dataInput.put(mJSONObject);
						}else{
							mJSONObject.put("feed_conversion","-");
							dataInput.put(mJSONObject);
						}
						String std_feed_per = "-";
						String std_daily_weight = "-";
						String std_feed_conversion = "-";
						if(tChickenInfo!=null && tChickenInfo.size()>0){
							for(SBChickenStandar tSBChickenStandar : tChickenInfo){
								if(tSBChickenStandar.getAge() == sbBreedDetail.getAge()){
									if(tSBChickenStandar.getAccFeed() != null){
										std_feed_per = PubFun.formatDoubleNum(tSBChickenStandar.getAccFeed().doubleValue(),0);
									}
									if(tSBChickenStandar.getWeightPer() != null){
										std_daily_weight = PubFun.formatDoubleNum(tSBChickenStandar.getWeightPer().doubleValue(),0);
									}
									if(tSBChickenStandar.getFeedConversion() != null){
										std_feed_conversion = PubFun.formatDoubleNum(tSBChickenStandar.getFeedConversion().doubleValue(),2);
									}
								}
							}
						}
						// 标准采食量
						mJSONObject.put("std_feed_per", std_feed_per);
						// 标准日均重
						mJSONObject.put("std_daily_weight",std_daily_weight);
						// 标准饲料转换率
						mJSONObject.put("std_feed_conversion", std_feed_conversion);
					 }
					resJson.put("dataInput", dataInput);
			    }else{
			    	resJson.put("Result", "Fail"); 
			    }
				//** 业务处理结束 **//*
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
			mLogger.info("=====Now end executing DataInputReqController.DRShow");
	}
	@RequestMapping("/ChickSettle")
	public void ChickSettle(HttpServletRequest request,HttpServletResponse  response){
		 mLogger.info("=====Now start executing DataInputReqController.ChickSettle");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("saveUser.para=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject tJSONObject = jsonObject.getJSONObject("params"); 
				int id_spa = jsonObject.getInt("id_spa"); 
			    Integer HouseBreedId = tJSONObject.getInt("HouseBreedId");
			    Integer HouseId = tJSONObject.optInt("HouseId");
			    Integer moveoutNum = tJSONObject.optInt("moveoutNum");
			    String moveoutWeight = tJSONObject.optString("moveoutWeight");
			    String marketDate = tJSONObject.optString("marketDate");
			    BigDecimal   tmoveoutWeight = PubFun.getBigDecimalData(moveoutWeight);
			    Date date1 = new Date();
			    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			    Date date2=sdf.parse(marketDate); 
				if(HouseId!=null&&HouseBreedId!=null&&HouseId!=null&&moveoutNum!=null&&marketDate!=null){
					SBHouseBreed SBHouseBreed = tSBHouseBreedService.selectByPrimaryKey(HouseBreedId);
					if(SBHouseBreed.getBatchStatus().equals("02")){
						resJson.put("Exception", "该批次已经出栏。"); 
				    	dealRes = Constants.RESULT_FAIL ;
					}else{
						SBHouseBreed.setMoveoutNum(moveoutNum);
						SBHouseBreed.setMoveoutWeight(tmoveoutWeight);
						SBHouseBreed.setMarketDate(date2);
						SBHouseBreed.setBatchStatus("02");
						SBHouseBreed.setModifyTime(date1);
						SBHouseBreed.setModifyPerson(id_spa);
						SBHouseBreed.setModifyDate(date1);
						HashMap<String,Object> mParas = new HashMap<String,Object>(); 
						mParas.put("SBHouseBreed",SBHouseBreed);
						SBDataInput tSBDataInput = new SBDataInput();
						tSBDataInput.setAge(0);
						tSBDataInput.setCreateDate(date1);
						tSBDataInput.setCreatePerson(id_spa);
						tSBDataInput.setCreateTime(date1);
						tSBDataInput.setFarmBreedId(SBHouseBreed.getFarmBreedId());
						tSBDataInput.setFarmId(SBHouseBreed.getFarmId());
						tSBDataInput.setHouseId(HouseId);
						tSBDataInput.setFreezeStatus("0");
						tSBDataInput.setDataType("D0008");
						tSBDataInput.setHouseBreedId(HouseBreedId);
						tSBDataInput.setModifyDate(date1);
						tSBDataInput.setModifyPerson(id_spa);
						tSBDataInput.setModifyTime(date1);
						mParas.put("SBDataInput",tSBDataInput);
						mParas.put("moveoutNum",moveoutNum);
						tDataInputReqManager.updateChickSettle(mParas);
						resJson.put("Result", "Success"); 
						resJson.put("HouseBreedId", HouseBreedId); 
						resJson.put("HouseId", HouseId); 
						dealRes = Constants.RESULT_SUCCESS ;
					}
				}else{
			    	resJson.put("Result", "Fail"); 
			    	dealRes = Constants.RESULT_FAIL ;
			    }
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
			mLogger.info("=====Now end executing DataInputReqController.ChickSettle");
	}
}
