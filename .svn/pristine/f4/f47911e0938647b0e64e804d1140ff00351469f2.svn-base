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

import com.mtc.app.biz.BreedBatchReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.MySQLSPService;
import com.mtc.app.service.SBFarmBreedService;
import com.mtc.app.service.SBFarmSettleService;
import com.mtc.app.service.SBHouseBreedService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBFarmBreed;
import com.mtc.entity.app.SBFarmSettle;
import com.mtc.entity.app.SBHouseBreed;

/**
 * @ClassName: BreedBatchReqController
 * @Description: 
 * @Date 2015-12-4 下午12:22:26
 * @Author Shao Yao Yu
 * 
 */

@Controller
@RequestMapping("/breedBatch")
public class BreedBatchReqController{
	private static Logger mLogger =Logger.getLogger(BreedBatchReqController.class); 
	
	@Autowired
	private BreedBatchReqManager tBreedBatchReqManager;
	
	@Autowired
	private BaseQueryService tBaseQueryService;
	
	@Autowired
	private	SBFarmSettleService tSBFarmSettleService;
	
	@Autowired
	private SBHouseBreedService tSBHouseBreedService;
	
	@Autowired
	private SBFarmBreedService tSBFarmBreedService;
	
	@Autowired
	private MySQLSPService tMySQLSPService;
	
	@RequestMapping("/createBatch")
	public void createBatch(HttpServletRequest request,HttpServletResponse response){
    mLogger.info("=====Now start executing BreedBatchReqController.createBatch");
		
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		boolean tboolean = true;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("paraStr=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			
			JSONObject  tJSONObject = jsonObject.getJSONObject("params");
			
			int FarmId = tJSONObject.getInt("FarmId");
			String BatchCode = tJSONObject.getString("BatchCode");
			String place_date = tJSONObject.getString("place_date");
			int place_num = tJSONObject.getInt("place_num");
			int breed_days = tJSONObject.getInt("breed_days");
			String market_date = tJSONObject.getString("market_date");
			String doc_vendors = tJSONObject.getString("doc_vendors");
			String breed = tJSONObject.getString("breed");
			int modifyPerson = jsonObject.getInt("id_spa");
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date placeDate=sdf.parse(place_date);
			Date marketDate=sdf.parse(market_date);
			Date date = new Date();
			SBFarmBreed tSBFarmBreed = new SBFarmBreed();
			tSBFarmBreed.setFarmId(FarmId);
			tSBFarmBreed.setBatchCode(BatchCode);
			tSBFarmBreed.setBatchStatus("01");
			tSBFarmBreed.setPlaceDate(placeDate);
			tSBFarmBreed.setPlaceNum(place_num);
			tSBFarmBreed.setBreedDays(breed_days);
			tSBFarmBreed.setMarketDate(marketDate);
			tSBFarmBreed.setModifyDate(date);
			tSBFarmBreed.setModifyPerson(modifyPerson);
			tSBFarmBreed.setModifyTime(date);
			tSBFarmBreed.setCreateDate(date);
			tSBFarmBreed.setCreatePerson(modifyPerson);
			tSBFarmBreed.setCreateTime(date);
			tSBFarmBreed.setDocVendors(doc_vendors);
			tSBFarmBreed.setBreed(breed);
			
			HashMap<String,Object> mParas = new HashMap<String,Object>();
			
			mParas.put("SBFarmBreed", tSBFarmBreed);
			
			String sql = "SELECT COUNT(1) FROM s_b_farm_breed a WHERE 1=1 AND a.farm_id = "+FarmId+" AND date(ifnull(a.settle_date,a.market_date)) >= '"+place_date+"'";
			 mLogger.info(sql);
			int dd = tBaseQueryService.selectIntergerByAny(sql);  
			 if(dd!=0){
				 tboolean=false;
				 resJson.put("Result","Fail");
				 resJson.put("ErrorMsg","该日期已经有饲养批次，请勿重复生成。");
			 }
			 if(marketDate.before(placeDate)){
				 tboolean=false;
				 resJson.put("Result","Fail");
			     resJson.put("ErrorMsg","出栏日期小于入雏日期，请修改。");
			 }
            if(tboolean){
				 SBFarmBreed ySBFarmBreed = tBreedBatchReqManager.dealSave(mParas);
				 resJson = new JSONObject();
				 resJson.put("FarmId",FarmId);
				 resJson.put("BreedBatchId",ySBFarmBreed.getId());
				 resJson.put("Result","Success");
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
		mLogger.info("=====Now end executing CodeDataReqController.createBatch");
	}
	@RequestMapping("/modifyBatch")
	public void modifyBatch(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing BreedBatchReqController.modifyBatch");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			Boolean tboolean = true;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				
				/** 业务处理开始，查询、增加、修改、或删除 **/
				JSONObject  tJSONObject = jsonObject.getJSONObject("params");
				int BreedBatchId = tJSONObject.getInt("BreedBatchId");
				String BatchCode = tJSONObject.getString("BatchCode");
				String place_date = tJSONObject.getString("place_date");
				int place_num = tJSONObject.getInt("place_num");
				int breed_days = tJSONObject.getInt("breed_days");
				String market_date = tJSONObject.getString("market_date");
				String doc_vendors = tJSONObject.getString("doc_vendors");
				String breed = tJSONObject.getString("breed");
				
				int modifyPerson = jsonObject.getInt("id_spa");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date placeDate=sdf.parse(place_date);
				Date marketDate=sdf.parse(market_date);
				Date date = new Date();
				
				SBFarmBreed tSBFarmBreed = tSBFarmBreedService.selectByPrimaryKey(BreedBatchId);
			  
				List<SBHouseBreed> tSBHouseBreed = tSBHouseBreedService.selectBySBFarmBeedId(BreedBatchId);
				 if(tSBHouseBreed.size()!=0){
					 tboolean=false;
					 resJson.put("Result","Fail");
					 resJson.put("ErrorMsg","入雏之后不允许修改农场饲养批次。");
				 }
				 if(marketDate.before(placeDate)){
					 tboolean=false;
					 resJson.put("Result","Fail");
				     resJson.put("ErrorMsg","出栏日期小于入雏日期，请修改。");
				 }
				 if(tSBFarmBreed==null){
					  tboolean=false;
					  resJson.put("Result","Fail");
					  resJson.put("ErrorMsg","其它错误，请联系管理员。");
				 }
				 if(tboolean){
					    tSBFarmBreed.setBatchCode(BatchCode);
						tSBFarmBreed.setPlaceDate(placeDate);
						tSBFarmBreed.setPlaceNum(place_num);
						tSBFarmBreed.setBreedDays(breed_days);
						tSBFarmBreed.setMarketDate(marketDate);
						tSBFarmBreed.setModifyDate(date);
						tSBFarmBreed.setModifyPerson(modifyPerson);
						tSBFarmBreed.setModifyTime(date);
						tSBFarmBreed.setDocVendors(doc_vendors);
						tSBFarmBreed.setBreed(breed);
						HashMap<String,Object> mParas = new HashMap<String,Object>();
						mParas.put("SBFarmBreed", tSBFarmBreed);
						SBFarmBreed mSBFarmBreed = tBreedBatchReqManager.dealUpdate(mParas);
						resJson.put("BreedBatchId", mSBFarmBreed.getId());
						resJson.put("Result","Success");
						
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
			mLogger.info("=====Now end executing CodeDataReqController.modifyBatch");
		
	}
	@RequestMapping("/queryBatch")
	public void queryBatch(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing BreedBatchReqController.queryBatch");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject  tJSONObject = jsonObject.getJSONObject("params");
				int BreedBatchId = tJSONObject.getInt("BreedBatchId");
			
				SBFarmBreed tSBFarmBreed = tSBFarmBreedService.selectByPrimaryKey(BreedBatchId);
			 if(tSBFarmBreed==null){
				 resJson.put("Result", "Fail");
				 dealRes = Constants.RESULT_FAIL;
			 }else{
					String BatchCode = tSBFarmBreed.getBatchCode();
					Date place_date = tSBFarmBreed.getPlaceDate();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					String placedate=sdf.format(place_date);
					int place_num = tSBFarmBreed.getPlaceNum();
					int breed_days = tSBFarmBreed.getBreedDays();
					Date market_date = tSBFarmBreed.getMarketDate();
					String marketdate = sdf.format(market_date);
					String doc_vendors = tSBFarmBreed.getDocVendors();
					String  breed = tSBFarmBreed.getBreed();
					
					JSONObject mJSONObject = new JSONObject();
					mJSONObject.put("BreedBatchId", BreedBatchId);
					mJSONObject.put("BatchCode", BatchCode);
					mJSONObject.put("place_date", placedate);
					mJSONObject.put("place_num", place_num);
					mJSONObject.put("breed_days", breed_days);
					mJSONObject.put("market_date", marketdate);
					mJSONObject.put("doc_vendors", doc_vendors);
					mJSONObject.put("breed", breed);
					
					resJson.put("Result", "Success");
					resJson.put("BreedBatch", mJSONObject);
					dealRes = Constants.RESULT_SUCCESS;
			 }
				//** 业务处理结束 **//*
			} catch (Exception e) {
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("ErrorMsg", e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
			}
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing CodeDataReqController.queryBatch");
		
	}
	/*@RequestMapping("/settleBatch")
	public void settleBatch(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing BreedBatchReqController.settleBatch");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject  tJSONObject = jsonObject.getJSONObject("params");
				int  id_spa = jsonObject.getInt("id_spa");
				int BreedBatchId = tJSONObject.getInt("BreedBatchId");
				String tSQL = "SELECT count(1) from s_b_house_breed where farm_breed_id = "+BreedBatchId+" and batch_status = '01'";
				int dd = tBaseQueryService.selectIntergerByAny(tSQL);  
				if(dd != 0){
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", "请确保所有栋舍已经出栏，再进行批次结算。");
				}else{
					SBFarmBreed tSBFarmBreed = tSBFarmBreedService.selectByPrimaryKey(BreedBatchId);
					if(tSBFarmBreed!=null){
						if(tSBFarmBreed.getBatchStatus().equals("02")){
							resJson.put("Result", "Fail");
							resJson.put("ErrorMsg", "该批次已经被结算，请勿重复操作。");
						}else{
							tSBFarmBreed.setModifyPerson(id_spa);
							tSBFarmBreed.setBatchStatus("02");
							Date tdate = new Date();
							tSBFarmBreed.setSettleDate(tdate);
							HashMap<String, Object> mpate = new HashMap<String, Object>();
							mpate.put("SBFarmBreed", tSBFarmBreed);
							tBreedBatchReqManager.settleBatch(mpate);
							resJson.put("Result", "Success");
						}
					}else{
						resJson.put("Result", "Fail");
					    resJson.put("ErrorMsg", "系统中不存在该饲养批次。");
					}
				}
				dealRes = Constants.RESULT_SUCCESS;
				//** 业务处理结束 **//*
			} catch (Exception e) {
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("ErrorMsg", e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
			}
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing CodeDataReqController.settleBatch");
	}*/
	
	@RequestMapping("/settleBatchQuery")
	public void settleBatchQuery(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing BreedBatchReqController.settleBatchQuery");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject  tJSONObject = jsonObject.getJSONObject("params");
				int  id_spa = jsonObject.getInt("id_spa");
				int BreedBatchId = tJSONObject.getInt("BreedBatchId");
				
				int place_num = 0;
				String tSQL1 = "SELECT ifnull(sum(a.place_num),0) as place_num from s_b_house_breed a where a.farm_breed_id = "+BreedBatchId ;
				place_num = tBaseQueryService.selectIntergerByAny(tSQL1);
				
				JSONObject ChickMsg = new JSONObject();
				ChickMsg.put("VenderName", "");// 鸡苗结算--厂家
				ChickMsg.put("amount", 0);// 鸡苗结算--购雏数
				ChickMsg.put("price_p", 0);// 鸡苗结算--单价
				ChickMsg.put("price_sum", 0);// 鸡苗结算--总金额
				ChickMsg.put("sys_amount", place_num);// 鸡苗结算--生产日报数据
				
				String tSQL2 = " SELECT ifnull(sum(b.cur_feed),0) as acc_feed,ifnull(sum(case when b.age = 0 then a.place_num else 0 end) - sum(b.cur_cd), 0) as acc_alive,(select c.batch_status from s_b_farm_breed c where c.id = a.farm_breed_id) AS status from s_b_house_breed a left JOIN s_b_breed_detail b on a.id = b.house_breed_id where farm_breed_id = " + BreedBatchId ;
				List<HashMap<String,Object>> list2 = tBaseQueryService.selectMapByAny(tSQL2);
				double acc_feed = 0;
				double acc_alive = 0;
				String batchStatus = "00";
				if(list2 != null && list2.size()>0){
					acc_feed = ((BigDecimal)list2.get(0).get("acc_feed")).doubleValue();
					acc_alive = ((BigDecimal)list2.get(0).get("acc_alive")).doubleValue();
					batchStatus = (String)list2.get(0).get("status");
				}
				
				JSONObject tFeedMsg = new JSONObject();
				tFeedMsg.put("VenderName", "");// 饲料结算--厂家
				tFeedMsg.put("sys_amount", acc_feed );// 饲料结算--生产日报耗料
				
				JSONObject tFeedInfo_1 = new JSONObject();
				tFeedInfo_1.put("FeedCode", "2001");
				tFeedInfo_1.put("FeedName", "1号料");
				tFeedInfo_1.put("Weight", 0);// 1号料--重量
				tFeedInfo_1.put("Price_p", 0);// 1号料--单价
				tFeedInfo_1.put("Price_sum", 0);// 1号料--总金额
				
				JSONObject tFeedInfo_2 = new JSONObject();
				tFeedInfo_2.put("FeedCode", "2002");
				tFeedInfo_2.put("FeedName", "2号料");
				tFeedInfo_2.put("Weight", 0);// 2号料--重量
				tFeedInfo_2.put("Price_p", 0);// 2号料--单价
				tFeedInfo_2.put("Price_sum", 0);// 2号料--总金额
				
				JSONObject tFeedInfo_3 = new JSONObject();
				tFeedInfo_3.put("FeedCode", "2003");
				tFeedInfo_3.put("FeedName", "3号料");
				tFeedInfo_3.put("Weight", 0);// 3号料--重量
				tFeedInfo_3.put("Price_p", 0);// 3号料--单价
				tFeedInfo_3.put("Price_sum", 0);// 3号料--总金额
				
				JSONObject tOutputMsg = new JSONObject();
				tOutputMsg.put("VenderName", "");// 毛鸡结算--厂家
				tOutputMsg.put("Price_p", 0);// 毛鸡结算--单价
				tOutputMsg.put("sys_amount", acc_alive);// 毛鸡结算--生产日报出栏数
				
				String tSQL = "SELECT a.house_id,s_f_getHouseName(a.house_id) as house_name,a.place_num as place_num,sum(b.cur_feed) as acc_feed,a.place_num-sum(b.cur_cd) as acc_alive,a.id as houseBreedId from s_b_house_breed a left JOIN s_b_breed_detail b on a.id = b.house_breed_id where farm_breed_id = "+BreedBatchId+" group by a.house_id,a.place_num" ;
				List<HashMap<String,Object>> tHouseInfo = tBaseQueryService.selectMapByAny(tSQL);
				JSONArray houseDetail = new JSONArray();
				JSONObject tH = null;
				for(HashMap<String,Object> tHouse:tHouseInfo){
					tH = new JSONObject();
					tH.put("HouseId", tHouse.get("house_id"));
					tH.put("HouseName", tHouse.get("house_name"));
					tH.put("houseBreedId", tHouse.get("houseBreedId"));
					tH.put("SettleAmount", 0);// 毛鸡结算--结算数量
					tH.put("SettleWeight", 0);// 毛鸡结算--结算重量
					tH.put("Weight_Avg", 0);// 毛鸡结算--结算均重
					tH.put("Price_sum", 0);// 毛鸡结算--结算总金额
					houseDetail.put(tH);
				}
				
				JSONObject tOtherMsg = new JSONObject();
				tOtherMsg.put("ChickenManure", 0);// 农场费用--鸡粪收入
				tOtherMsg.put("OtherFee_IC", 0);// 农场费用--其他收入
				tOtherMsg.put("VaccineFee", 0);// 农场费用--药品疫苗
				tOtherMsg.put("ManualFee", 0);// 农场费用--人工费用
				tOtherMsg.put("FuelFee", 0);// 农场费用--燃料费用
				tOtherMsg.put("UtilityFee", 0);// 农场费用--水电费
				tOtherMsg.put("PaddingFee", 0);// 农场费用--垫料费
				tOtherMsg.put("CatcherFee", 0);// 农场费用--抓鸡费
				tOtherMsg.put("LossFee", 0);// 农场费用--折旧租金
				tOtherMsg.put("OtherFee", 0);// 农场费用--其它费用
				
				
				List<SBFarmSettle> tSBFarmSettles = tSBFarmSettleService.selectSettleByFarm(BreedBatchId);
				if(tSBFarmSettles != null && tSBFarmSettles.size()>0){
					for(SBFarmSettle mSBFarmSettle:tSBFarmSettles){
						/*  编码参考
							1001-鸡苗结算;
							2001-1号饲料;
							2002-2号饲料;
							2003-3号饲料;
							3001-药品疫苗;
							3002-燃料费;
							3003-人工费;
							3004-其他费用;
							3005-折旧费用;
							3006-水电费;
							3007-垫料费;
							3008-抓鸡费;
							4001-毛鸡结算;
							4002-鸡粪收入;
							4003-其他收入;
						 */
						if(mSBFarmSettle.getFeeCode().equals("1001")){
							ChickMsg.put("VenderName", mSBFarmSettle.getCompanyName());
							ChickMsg.put("amount", mSBFarmSettle.getQuentity());
							ChickMsg.put("price_p", getDoubleValue(mSBFarmSettle.getPricePu()));
							ChickMsg.put("price_sum", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("2001")){
							tFeedMsg.put("VenderName", mSBFarmSettle.getCompanyName());
							tFeedInfo_1.put("Weight", getDoubleValue(mSBFarmSettle.getWeight()));
							tFeedInfo_1.put("Price_p", getDoubleValue(mSBFarmSettle.getPricePu()));
							tFeedInfo_1.put("Price_sum", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("2002")){
							tFeedMsg.put("VenderName", mSBFarmSettle.getCompanyName());
							tFeedInfo_2.put("Weight", getDoubleValue(mSBFarmSettle.getWeight()));
							tFeedInfo_2.put("Price_p", getDoubleValue(mSBFarmSettle.getPricePu()));
							tFeedInfo_2.put("Price_sum", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("2003")){
							tFeedMsg.put("VenderName", mSBFarmSettle.getCompanyName());
							tFeedInfo_3.put("Weight", getDoubleValue(mSBFarmSettle.getWeight()));
							tFeedInfo_3.put("Price_p", getDoubleValue(mSBFarmSettle.getPricePu()));
							tFeedInfo_3.put("Price_sum", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("4001")){
							tOutputMsg.put("VenderName", mSBFarmSettle.getCompanyName());
							tOutputMsg.put("Price_p", getDoubleValue(mSBFarmSettle.getPricePu()));
							for(int i=0;i<houseDetail.length();i++){
								if(((JSONObject)houseDetail.get(i)).get("HouseId").equals(mSBFarmSettle.getHouseId())){
									((JSONObject)houseDetail.get(i)).put("SettleAmount",mSBFarmSettle.getQuentity());
									((JSONObject)houseDetail.get(i)).put("SettleWeight", getDoubleValue(mSBFarmSettle.getWeight()));
									((JSONObject)houseDetail.get(i)).put("Weight_Avg", getDoubleValue(mSBFarmSettle.getBak4()));
									((JSONObject)houseDetail.get(i)).put("Price_sum", getDoubleValue(mSBFarmSettle.getMoneySum()));
								};
							}
						}
						if(mSBFarmSettle.getFeeCode().equals("3001")){
							tOtherMsg.put("VaccineFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("3002")){
							tOtherMsg.put("FuelFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("3003")){
							tOtherMsg.put("ManualFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("3005")){
							tOtherMsg.put("LossFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("3006")){
							tOtherMsg.put("UtilityFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("3007")){
							tOtherMsg.put("PaddingFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("3008")){
							tOtherMsg.put("CatcherFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("3004")){
							tOtherMsg.put("OtherFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("4003")){
							tOtherMsg.put("OtherFee_IC", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
						if(mSBFarmSettle.getFeeCode().equals("4002")){
							tOtherMsg.put("ChickenManure", getDoubleValue(mSBFarmSettle.getMoneySum()));
						}
					}
				}
				
				resJson.put("ChickMsg", ChickMsg);
				JSONArray tJSONArray = new JSONArray();
				tJSONArray.put(tFeedInfo_1);
				tJSONArray.put(tFeedInfo_2);
				tJSONArray.put(tFeedInfo_3);
				tFeedMsg.put("FeedInfo", tJSONArray);
				resJson.put("FeedMsg", tFeedMsg);
				tOutputMsg.put("Detail", houseDetail);
				resJson.put("OutputMsg", tOutputMsg);
				resJson.put("OtherMsg", tOtherMsg);
				resJson.put("BatchStatus", batchStatus);
				resJson.put("Result", "Success");
				
				dealRes = Constants.RESULT_SUCCESS;
				//** 业务处理结束 **//*
			} catch (Exception e) {
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("ErrorMsg", e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
			}
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing BreedBatchReqController.settleBatchQuery");
	}
	
	@RequestMapping("/settleBatchSave")
	public void settleBatchSave(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing BreedBatchReqController.settleBatchSave");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject  tJSONObject = jsonObject.getJSONObject("params");
				int  id_spa = jsonObject.getInt("id_spa");
				int BreedBatchId = tJSONObject.getInt("BreedBatchId");
				int FarmId = tJSONObject.getInt("FarmId");
				Date curDate = new Date();
				String batchStatus = "";
				String tSQL1 = "SELECT batch_status from s_b_farm_breed where id = "+BreedBatchId ;
				batchStatus = tBaseQueryService.selectStringByAny(tSQL1);
				if(batchStatus.equals("01")){
					List<SBFarmSettle> tList = new ArrayList<SBFarmSettle>();
					JSONObject  ChickMsgJson = tJSONObject.getJSONObject("ChickMsg");
					// 鸡苗结算
					SBFarmSettle tChickSBFarmSettle = new SBFarmSettle();
					tChickSBFarmSettle.setFarmId(FarmId);
					tChickSBFarmSettle.setFarmBreedId(BreedBatchId);
					tChickSBFarmSettle.setFeeType("E");
					tChickSBFarmSettle.setFeeCode("1001");
					tChickSBFarmSettle.setFeeName("鸡苗结算");
					tChickSBFarmSettle.setCompanyCode("");
					tChickSBFarmSettle.setCompanyName(ChickMsgJson.optString("VenderName"));
					tChickSBFarmSettle.setPricePu(PubFun.getBigDecimalData(ChickMsgJson.optString("price_p")));
					tChickSBFarmSettle.setQuentity(ChickMsgJson.optInt("amount"));
					tChickSBFarmSettle.setMoneySum(PubFun.getBigDecimalData(ChickMsgJson.optString("price_sum")));
					tChickSBFarmSettle.setCreatePerson(id_spa);
					tChickSBFarmSettle.setCreateDate(curDate);
					tChickSBFarmSettle.setCreateTime(curDate);
					tChickSBFarmSettle.setModifyPerson(id_spa);
					tChickSBFarmSettle.setModifyDate(curDate);
					tChickSBFarmSettle.setModifyTime(curDate);
					tList.add(tChickSBFarmSettle);
					// 饲料结算
					JSONObject  FeedMsgJson = tJSONObject.getJSONObject("FeedMsg");
					JSONArray tJSONArray =FeedMsgJson.getJSONArray("FeedInfo");
					for (int i = 0; i < tJSONArray.length(); i++) {
					JSONObject	tObject = (JSONObject) tJSONArray.get(i);
					SBFarmSettle tFeedSBFarmSettle = new SBFarmSettle();
					tFeedSBFarmSettle.setFarmId(FarmId);
					tFeedSBFarmSettle.setFarmBreedId(BreedBatchId);
					tFeedSBFarmSettle.setFeeType("E");
					tFeedSBFarmSettle.setCompanyCode("");
					tFeedSBFarmSettle.setCompanyName(FeedMsgJson.optString("VenderName"));
					tFeedSBFarmSettle.setCreatePerson(id_spa);
					tFeedSBFarmSettle.setCreateDate(curDate);
					tFeedSBFarmSettle.setCreateTime(curDate);
					tFeedSBFarmSettle.setModifyPerson(id_spa);
					tFeedSBFarmSettle.setModifyDate(curDate);
					tFeedSBFarmSettle.setModifyTime(curDate);
					tFeedSBFarmSettle.setFeeCode(tObject.getString("FeedCode"));
					tFeedSBFarmSettle.setFeeName(tObject.getString("FeedName"));
					tFeedSBFarmSettle.setPricePu(PubFun.getBigDecimalData(tObject.optString("Price_p")));
					tFeedSBFarmSettle.setWeight(PubFun.getBigDecimalData(tObject.optString("Weight")));
					tFeedSBFarmSettle.setMoneySum(PubFun.getBigDecimalData(tObject.optString("Price_sum")));
					tList.add(tFeedSBFarmSettle);
					}
					// 毛鸡结算
					JSONObject  OutputMsgJson = tJSONObject.getJSONObject("OutputMsg");
					JSONArray tOutputMsgJSONArray = OutputMsgJson.getJSONArray("Detail");
					for (int i = 0;i<tOutputMsgJSONArray.length();i++) {
						SBFarmSettle tOutputMsgJSON = new SBFarmSettle();
						JSONObject  dJSONObject = (JSONObject) tOutputMsgJSONArray.get(i);
						tOutputMsgJSON.setFarmId(FarmId);
						tOutputMsgJSON.setFarmBreedId(BreedBatchId);
						tOutputMsgJSON.setHouseId(dJSONObject.optInt("HouseId"));
						tOutputMsgJSON.setHouseBreedId(dJSONObject.optInt("houseBreedId"));
						tOutputMsgJSON.setFeeType("I");
						tOutputMsgJSON.setFeeCode("4001");
						tOutputMsgJSON.setFeeName("毛鸡结算");
						tOutputMsgJSON.setCompanyCode("");
						tOutputMsgJSON.setCompanyName(OutputMsgJson.optString("VenderName"));
						tOutputMsgJSON.setPricePu(PubFun.getBigDecimalData(OutputMsgJson.optString("Price_p")));
						tOutputMsgJSON.setQuentity(dJSONObject.optInt("SettleAmount"));
						tOutputMsgJSON.setBak4(PubFun.getBigDecimalData(dJSONObject.optString("Weight_Avg")));
						tOutputMsgJSON.setWeight(PubFun.getBigDecimalData((dJSONObject.optString("SettleWeight"))));   
						tOutputMsgJSON.setMoneySum(PubFun.getBigDecimalData(dJSONObject.optString("Price_sum")));
						tOutputMsgJSON.setCreatePerson(id_spa);
						tOutputMsgJSON.setCreateDate(curDate);
						tOutputMsgJSON.setCreateTime(curDate);
						tOutputMsgJSON.setModifyPerson(id_spa);
						tOutputMsgJSON.setModifyDate(curDate);
						tOutputMsgJSON.setModifyTime(curDate);
						tList.add(tOutputMsgJSON);
					}
					// 农场费用登记
					JSONObject  OtherMsgJson = tJSONObject.getJSONObject("OtherMsg");
					if(OtherMsgJson.optString("ChickenManure")!=null){
						SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
						tOtherMsgSettle.setFarmId(FarmId);
						tOtherMsgSettle.setFarmBreedId(BreedBatchId);
						tOtherMsgSettle.setFeeType("I");
						tOtherMsgSettle.setCreatePerson(id_spa);
						tOtherMsgSettle.setCreateDate(curDate);
						tOtherMsgSettle.setCreateTime(curDate);
						tOtherMsgSettle.setModifyPerson(id_spa);
						tOtherMsgSettle.setModifyDate(curDate);
						tOtherMsgSettle.setModifyTime(curDate);
						tOtherMsgSettle.setFeeCode("4002");
						tOtherMsgSettle.setFeeName("鸡粪收入");
						tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsgJson.optString("ChickenManure")));
						tList.add(tOtherMsgSettle);
					}
					if(OtherMsgJson.optString("OtherFee_IC")!=null){
						SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
						tOtherMsgSettle.setFarmId(FarmId);
						tOtherMsgSettle.setFarmBreedId(BreedBatchId);
						tOtherMsgSettle.setFeeType("I");
						tOtherMsgSettle.setCreatePerson(id_spa);
						tOtherMsgSettle.setCreateDate(curDate);
						tOtherMsgSettle.setCreateTime(curDate);
						tOtherMsgSettle.setModifyPerson(id_spa);
						tOtherMsgSettle.setModifyDate(curDate);
						tOtherMsgSettle.setModifyTime(curDate);
						tOtherMsgSettle.setFeeCode("4003");
						tOtherMsgSettle.setFeeName("其他收入");
						tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsgJson.optString("OtherFee_IC")));
					    tList.add(tOtherMsgSettle);
					}
					if(OtherMsgJson.optString("VaccineFee")!=null){
						SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
						tOtherMsgSettle.setFarmId(FarmId);
						tOtherMsgSettle.setFarmBreedId(BreedBatchId);
						tOtherMsgSettle.setFeeType("E");
						tOtherMsgSettle.setCreatePerson(id_spa);
						tOtherMsgSettle.setCreateDate(curDate);
						tOtherMsgSettle.setCreateTime(curDate);
						tOtherMsgSettle.setModifyPerson(id_spa);
						tOtherMsgSettle.setModifyDate(curDate);
						tOtherMsgSettle.setModifyTime(curDate);
						tOtherMsgSettle.setFeeCode("3001");
						tOtherMsgSettle.setFeeName("药品疫苗费");
						tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsgJson.optString("VaccineFee")));
						tList.add(tOtherMsgSettle);
					}
					if(OtherMsgJson.optString("FuelFee")!=null){
						SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
						tOtherMsgSettle.setFarmId(FarmId);
						tOtherMsgSettle.setFarmBreedId(BreedBatchId);
						tOtherMsgSettle.setFeeType("E");
						tOtherMsgSettle.setCreatePerson(id_spa);
						tOtherMsgSettle.setCreateDate(curDate);
						tOtherMsgSettle.setCreateTime(curDate);
						tOtherMsgSettle.setModifyPerson(id_spa);
						tOtherMsgSettle.setModifyDate(curDate);
						tOtherMsgSettle.setModifyTime(curDate);
						tOtherMsgSettle.setFeeCode("3002");
						tOtherMsgSettle.setFeeName("燃料费");
						tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsgJson.optString("FuelFee")));
						tList.add(tOtherMsgSettle);	
					}
					if(OtherMsgJson.optString("ManualFee")!=null){
						SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
						tOtherMsgSettle.setFarmId(FarmId);
						tOtherMsgSettle.setFarmBreedId(BreedBatchId);
						tOtherMsgSettle.setFeeType("E");
						tOtherMsgSettle.setCreatePerson(id_spa);
						tOtherMsgSettle.setCreateDate(curDate);
						tOtherMsgSettle.setCreateTime(curDate);
						tOtherMsgSettle.setModifyPerson(id_spa);
						tOtherMsgSettle.setModifyDate(curDate);
						tOtherMsgSettle.setModifyTime(curDate);
						tOtherMsgSettle.setFeeCode("3003");
						tOtherMsgSettle.setFeeName("人工费用");
						tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsgJson.optString("ManualFee")));
					    tList.add(tOtherMsgSettle);						
					}
					if(OtherMsgJson.optString("UtilityFee")!=null){
						SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
						tOtherMsgSettle.setFarmId(FarmId);
						tOtherMsgSettle.setFarmBreedId(BreedBatchId);
						tOtherMsgSettle.setFeeType("E");
						tOtherMsgSettle.setCreatePerson(id_spa);
						tOtherMsgSettle.setCreateDate(curDate);
						tOtherMsgSettle.setCreateTime(curDate);
						tOtherMsgSettle.setModifyPerson(id_spa);
						tOtherMsgSettle.setModifyDate(curDate);
						tOtherMsgSettle.setModifyTime(curDate);
						tOtherMsgSettle.setFeeCode("3006");
						tOtherMsgSettle.setFeeName("水电费");
						tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsgJson.optString("UtilityFee")));
					    tList.add(tOtherMsgSettle);						
					}
					if(OtherMsgJson.optString("PaddingFee")!=null){
						SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
						tOtherMsgSettle.setFarmId(FarmId);
						tOtherMsgSettle.setFarmBreedId(BreedBatchId);
						tOtherMsgSettle.setFeeType("E");
						tOtherMsgSettle.setCreatePerson(id_spa);
						tOtherMsgSettle.setCreateDate(curDate);
						tOtherMsgSettle.setCreateTime(curDate);
						tOtherMsgSettle.setModifyPerson(id_spa);
						tOtherMsgSettle.setModifyDate(curDate);
						tOtherMsgSettle.setModifyTime(curDate);
						tOtherMsgSettle.setFeeCode("3007");
						tOtherMsgSettle.setFeeName("垫料费");
						tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsgJson.optString("PaddingFee")));
					    tList.add(tOtherMsgSettle);						
					}
					if(OtherMsgJson.optString("CatcherFee")!=null){
						SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
						tOtherMsgSettle.setFarmId(FarmId);
						tOtherMsgSettle.setFarmBreedId(BreedBatchId);
						tOtherMsgSettle.setFeeType("E");
						tOtherMsgSettle.setCreatePerson(id_spa);
						tOtherMsgSettle.setCreateDate(curDate);
						tOtherMsgSettle.setCreateTime(curDate);
						tOtherMsgSettle.setModifyPerson(id_spa);
						tOtherMsgSettle.setModifyDate(curDate);
						tOtherMsgSettle.setModifyTime(curDate);
						tOtherMsgSettle.setFeeCode("3008");
						tOtherMsgSettle.setFeeName("抓鸡费");
						tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsgJson.optString("CatcherFee")));
					    tList.add(tOtherMsgSettle);						
					}
					if(OtherMsgJson.optString("LossFee")!=null){
						SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
						tOtherMsgSettle.setFarmId(FarmId);
						tOtherMsgSettle.setFarmBreedId(BreedBatchId);
						tOtherMsgSettle.setFeeType("E");
						tOtherMsgSettle.setCreatePerson(id_spa);
						tOtherMsgSettle.setCreateDate(curDate);
						tOtherMsgSettle.setCreateTime(curDate);
						tOtherMsgSettle.setModifyPerson(id_spa);
						tOtherMsgSettle.setModifyDate(curDate);
						tOtherMsgSettle.setModifyTime(curDate);
						tOtherMsgSettle.setFeeCode("3005");
						tOtherMsgSettle.setFeeName("折旧租金");
						tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsgJson.optString("LossFee")));
					    tList.add(tOtherMsgSettle);						
					}
					if(OtherMsgJson.optString("OtherFee")!=null){
						SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
						tOtherMsgSettle.setFarmId(FarmId);
						tOtherMsgSettle.setFarmBreedId(BreedBatchId);
						tOtherMsgSettle.setFeeType("E");
						tOtherMsgSettle.setCreatePerson(id_spa);
						tOtherMsgSettle.setCreateDate(curDate);
						tOtherMsgSettle.setCreateTime(curDate);
						tOtherMsgSettle.setModifyPerson(id_spa);
						tOtherMsgSettle.setModifyDate(curDate);
						tOtherMsgSettle.setModifyTime(curDate);
						tOtherMsgSettle.setFeeCode("3004");
						tOtherMsgSettle.setFeeName("其它费用");
						tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsgJson.optString("OtherFee")));
					    tList.add(tOtherMsgSettle);
					}
					HashMap<String,Object> tPara =  new HashMap<String, Object>();
					tPara.put("tList", tList);
					tBreedBatchReqManager.intterSBFarmSettle(tPara);
				}else{
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", "该农场批次已经结算确认，不允许修改。");
				}
				resJson.put("Result", "Success");
				dealRes = Constants.RESULT_SUCCESS;
				//** 业务处理结束 **//*
			} catch (Exception e){
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("ErrorMsg", e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
			}
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing BreedBatchReqController.settleBatchSave");
	}
	
	
	@RequestMapping("/settleBatchConfirm")
	public void settleBatchConfirm(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing BreedBatchReqController.settleBatchConfirm");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject  tJSONObject = jsonObject.getJSONObject("params");
				int  id_spa = jsonObject.getInt("id_spa");
				int BreedBatchId = tJSONObject.getInt("BreedBatchId");
				String tSQL = "SELECT count(1) from s_b_house_breed where farm_breed_id = "+BreedBatchId+" and batch_status = '01'";
				int dd = tBaseQueryService.selectIntergerByAny(tSQL);  
				if(dd != 0){
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", "请确保所有栋舍已经出栏，再进行批次结算。");
				}else{
					SBFarmBreed tSBFarmBreed = tSBFarmBreedService.selectByPrimaryKey(BreedBatchId);
					if(tSBFarmBreed!=null){
						if(tSBFarmBreed.getBatchStatus().equals("02")){
							resJson.put("Result", "Fail");
							resJson.put("ErrorMsg", "该批次已经被结算，请勿重复操作。");
						}else{
							tSBFarmBreed.setModifyPerson(id_spa);
							tSBFarmBreed.setBatchStatus("02");
							Date tdate = new Date();
							tSBFarmBreed.setSettleDate(tdate);
							HashMap<String, Object> mpate = new HashMap<String, Object>();
							mpate.put("SBFarmBreed", tSBFarmBreed);
							tBreedBatchReqManager.settleBatch(mpate);
							
							try{
								 // 生成栋舍任务提醒 
								 HashMap tHashMap = new HashMap();
					             tHashMap.put("in_farm_id", tSBFarmBreed.getFarmId());
					             tHashMap.put("in_apply_flag", "All");
					             tHashMap.put("in_temp_id", 0);
					             tMySQLSPService.exec_s_p_createHouseTask(tHashMap);
					             
							 }catch(Exception e){
								 
							 }
							resJson.put("Result", "Success");
						}
					}else{
						resJson.put("Result", "Fail");
					    resJson.put("ErrorMsg", "系统中不存在该饲养批次。");
					}
				}
				dealRes = Constants.RESULT_SUCCESS;
				//** 业务处理结束 **//*
			} catch (Exception e) {
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("ErrorMsg", e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
			}
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing CodeDataReqController.settleBatchConfirm");
	
	}

	@RequestMapping("/getProfitRep")
	public void getProfitRep(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing BreedBatchReqController.getProfitRep");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject  tJSONObject = jsonObject.getJSONObject("params");
				int  id_spa = jsonObject.getInt("id_spa");
				int BreedBatchId = tJSONObject.getInt("BreedBatchId");
				int FarmId = tJSONObject.getInt("FarmId");
				
				String tSQL = "SELECT ifnull(b.fee_code,'null') as fee_code,b.fee_name,"
						+ "ifnull(sum(case when a.tFlag = 'this' then b.quentity else 0 end),0) as this_amount,"
						+ "ifnull(sum(case when a.tFlag = 'last' then b.quentity else 0 end),0) as last_amount,"
   						+ "ifnull(sum(case when a.tFlag = 'this' then b.weight else 0 end),0) as this_weight,"
   						+ "ifnull(sum(case when a.tFlag = 'last' then b.weight else 0 end),0) as last_weight,"
						+ "ifnull(sum(case when a.tFlag = 'this' then b.money_sum else 0 end),0) as this_money,"
						+ "ifnull(sum(case when a.tFlag = 'last' then b.money_sum else 0 end),0) as last_money from ( "
								+ "select id as farmBreedId,if(id="+BreedBatchId+",'this','last') as tFlag "
										+ "from s_b_farm_breed where farm_id = "+FarmId+" ORDER BY batch_code desc LIMIT 2 ) a "
								+ "left join s_b_farm_settle b on b.farm_breed_id = a.farmBreedId where fee_code is not null "
						+ "GROUP BY b.fee_code,b.fee_name order by b.fee_code " ;
				mLogger.info("getProfitRep.SQL=" + tSQL);
				List<HashMap<String, Object>> tDetail = tBaseQueryService.selectMapByAny(tSQL);
				if(tDetail != null && tDetail.size()>1){
					int this_amount = 0;
					int last_amount = 0;
					double this_weight = 0;
					double last_weight = 0;
					// 先计算毛鸡结算的数量以及重量
					for(HashMap<String,Object> tRow:tDetail){
						if(tRow.get("fee_code").equals("4001")){
							this_amount = ((BigDecimal)tRow.get("this_amount")).intValue();
							last_amount = ((BigDecimal)tRow.get("last_amount")).intValue();
							this_weight = ((BigDecimal)tRow.get("this_weight")).doubleValue();
							last_weight = ((BigDecimal)tRow.get("last_weight")).doubleValue();
							break;
						}
					}
					
					JSONArray tInComeArray = new JSONArray();
					JSONArray tExpenseArray = new JSONArray();
					JSONObject tProfitJson = new JSONObject();
					
					JSONObject tChickenJson = new JSONObject();
					JSONObject tOtherIncomeJson = new JSONObject();
					JSONObject tIncomAllJson = new JSONObject();
					
					JSONObject tChickJson = new JSONObject();
					JSONObject tFeedJson = new JSONObject();
					JSONObject tMedicineJson = new JSONObject();
					JSONObject tManualJson = new JSONObject();
					JSONObject tUtilityJson = new JSONObject();
					JSONObject tPaddingJson = new JSONObject();
					JSONObject tCatcherJson = new JSONObject();
					JSONObject tDepreciationJson = new JSONObject();
					JSONObject tFuelJson = new JSONObject();
					JSONObject tOtherExpenseJson = new JSONObject();
					JSONObject tExpenAllJson = new JSONObject();
					// 其它收入合计					
					double money_other_i_sum_this = 0 ;
					double money_other_i_sum_last = 0 ;
					// 饲料合计
					double money_feed_sum_this = 0 ;
					double money_feed_sum_last = 0 ;
					// 其它支出合计					
					double money_other_e_sum_this = 0 ;
					double money_other_e_sum_last = 0 ;
					// 支出合计					
					double money_all_e_sum_this = 0 ;
					double money_all_e_sum_last = 0 ;
					// 收入合计
					double money_all_i_sum_this = 0 ;
					double money_all_i_sum_last = 0 ;
					// 依次计算各指标值
					for(HashMap<String,Object> tRow:tDetail){
						if(tRow.get("fee_code").equals("4001")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_all_i_sum_this += money_sum_this;
							money_all_i_sum_last += money_sum_last;
							tChickenJson.put("ItemName", "毛鸡");
							tChickenJson.put("PricSum_this", PubFun.formatDoubleNum(money_sum_this/10000,2));
							tChickenJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this/this_weight,2));
							tChickenJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this/this_amount,2));
							tChickenJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_sum_last/10000,2));
							tChickenJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last/last_weight,2));
							tChickenJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last/last_amount,2));
						}
						if(tRow.get("fee_code").equals("4002")
								|| tRow.get("fee_code").equals("4003")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_other_i_sum_this += money_sum_this;
							money_other_i_sum_last += money_sum_last;
							money_all_i_sum_this += money_sum_this;
							money_all_i_sum_last += money_sum_last;
						}
						if(tRow.get("fee_code").equals("1001")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_all_e_sum_this += money_sum_this;
							money_all_e_sum_last += money_sum_last;
							tChickJson.put("ItemName", "鸡苗");
							tChickJson.put("PricSum_this", PubFun.formatDoubleNum(money_sum_this/10000,2));
							tChickJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this/this_weight,2));
							tChickJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this/this_amount,2));
							tChickJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_sum_last/10000,2));
							tChickJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last/last_weight,2));
							tChickJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last/last_amount,2));
						}
						if(tRow.get("fee_code").equals("2001")
								|| tRow.get("fee_code").equals("2002")
								|| tRow.get("fee_code").equals("2003")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_feed_sum_this += money_sum_this;
							money_feed_sum_last += money_sum_last;
							money_all_e_sum_this += money_sum_this;
							money_all_e_sum_last += money_sum_last;
						}
						if(tRow.get("fee_code").equals("3001")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_all_e_sum_this += money_sum_this;
							money_all_e_sum_last += money_sum_last;
							tMedicineJson.put("ItemName", "药费");
							tMedicineJson.put("PricSum_this", PubFun.formatDoubleNum(money_sum_this/10000,2));
							tMedicineJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this/this_weight,2));
							tMedicineJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this/this_amount,2));
							tMedicineJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_sum_last/10000,2));
							tMedicineJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last/last_weight,2));
							tMedicineJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last/last_amount,2));
						}
						if(tRow.get("fee_code").equals("3002")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_all_e_sum_this += money_sum_this;
							money_all_e_sum_last += money_sum_last;
							tFuelJson.put("ItemName", "燃料");
							tFuelJson.put("PricSum_this", PubFun.formatDoubleNum(money_sum_this/10000,2));
							tFuelJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this/this_weight,2));
							tFuelJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this/this_amount,2));
							tFuelJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_sum_last/10000,2));
							tFuelJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last/last_weight,2));
							tFuelJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last/last_amount,2));
						}
						if(tRow.get("fee_code").equals("3003")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_all_e_sum_this += money_sum_this;
							money_all_e_sum_last += money_sum_last;
							tManualJson.put("ItemName", "人工");
							tManualJson.put("PricSum_this", PubFun.formatDoubleNum(money_sum_this/10000,2));
							tManualJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this/this_weight,2));
							tManualJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this/this_amount,2));
							tManualJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_sum_last/10000,2));
							tManualJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last/last_weight,2));
							tManualJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last/last_amount,2));
						}
						if(tRow.get("fee_code").equals("3004")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_other_e_sum_this += money_sum_this;
							money_other_e_sum_last += money_sum_last;
							money_all_e_sum_this += money_sum_this;
							money_all_e_sum_last += money_sum_last;
						}
						if(tRow.get("fee_code").equals("3006")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_all_e_sum_this += money_sum_this;
							money_all_e_sum_last += money_sum_last;
							tUtilityJson.put("ItemName", "水电");
							tUtilityJson.put("PricSum_this", PubFun.formatDoubleNum(money_sum_this/10000,2));
							tUtilityJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this/this_weight,2));
							tUtilityJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this/this_amount,2));
							tUtilityJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_sum_last/10000,2));
							tUtilityJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last/last_weight,2));
							tUtilityJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last/last_amount,2));
						}
						if(tRow.get("fee_code").equals("3007")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_all_e_sum_this += money_sum_this;
							money_all_e_sum_last += money_sum_last;
							tPaddingJson.put("ItemName", "垫料");
							tPaddingJson.put("PricSum_this", PubFun.formatDoubleNum(money_sum_this/10000,2));
							tPaddingJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this/this_weight,2));
							tPaddingJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this/this_amount,2));
							tPaddingJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_sum_last/10000,2));
							tPaddingJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last/last_weight,2));
							tPaddingJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last/last_amount,2));
						}
						if(tRow.get("fee_code").equals("3008")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_all_e_sum_this += money_sum_this;
							money_all_e_sum_last += money_sum_last;
							tCatcherJson.put("ItemName", "抓鸡");
							tCatcherJson.put("PricSum_this", PubFun.formatDoubleNum(money_sum_this/10000,2));
							tCatcherJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this/this_weight,2));
							tCatcherJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this/this_amount,2));
							tCatcherJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_sum_last/10000,2));
							tCatcherJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last/last_weight,2));
							tCatcherJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last/last_amount,2));
						}
						if(tRow.get("fee_code").equals("3005")){
							double money_sum_this = ((BigDecimal)tRow.get("this_money")).doubleValue();
							double money_sum_last = ((BigDecimal)tRow.get("last_money")).doubleValue();
							money_all_e_sum_this += money_sum_this;
							money_all_e_sum_last += money_sum_last;
							tDepreciationJson.put("ItemName", "折旧");
							tDepreciationJson.put("PricSum_this", PubFun.formatDoubleNum(money_sum_this/10000,2));
							tDepreciationJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this/this_weight,2));
							tDepreciationJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this/this_amount,2));
							tDepreciationJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_sum_last/10000,2));
							tDepreciationJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last/last_weight,2));
							tDepreciationJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last/last_amount,2));
						}
					}
					
					tInComeArray.put(tChickenJson);// 毛鸡

					tOtherIncomeJson.put("ItemName", "其它");
					tOtherIncomeJson.put("PricSum_this", PubFun.formatDoubleNum(money_other_i_sum_this/10000,2));
					tOtherIncomeJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_other_i_sum_this/this_weight,2));
					tOtherIncomeJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_other_i_sum_this/this_amount,2));
					tOtherIncomeJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_other_i_sum_last/10000,2));
					tOtherIncomeJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_other_i_sum_last/last_weight,2));
					tOtherIncomeJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_other_i_sum_last/last_amount,2));
					tInComeArray.put(tOtherIncomeJson);  // 其它收入
					
					tIncomAllJson.put("ItemName", "收入");
					tIncomAllJson.put("PricSum_this", PubFun.formatDoubleNum(money_all_i_sum_this/10000,2));
					tIncomAllJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_all_i_sum_this/this_weight,2));
					tIncomAllJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_all_i_sum_this/this_amount,2));
					tIncomAllJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_all_i_sum_last/10000,2));
					tIncomAllJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_all_i_sum_last/last_weight,2));
					tIncomAllJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_all_i_sum_last/last_amount,2));
					tInComeArray.put(tIncomAllJson);  // 总收入
					
					resJson.put("InCome", tInComeArray);
					
					tExpenseArray.put(tChickJson); // 鸡苗
					tFeedJson.put("ItemName", "饲料");
					tFeedJson.put("PricSum_this", PubFun.formatDoubleNum(money_feed_sum_this/10000,2));
					tFeedJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_feed_sum_this/this_weight,2));
					tFeedJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_feed_sum_this/this_amount,2));
					tFeedJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_feed_sum_last/10000,2));
					tFeedJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_feed_sum_last/last_weight,2));
					tFeedJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_feed_sum_last/last_amount,2));
					tExpenseArray.put(tFeedJson); // 饲料

					if(tMedicineJson.length() != 0 &&
							(!tMedicineJson.optString("PricSum_this").equals("0.00")
							|| !tMedicineJson.optString("SaleChicken_last").equals("0.00"))){
						tExpenseArray.put(tMedicineJson);// 疫苗
					}
					if(tManualJson.length() != 0 &&
							(!tManualJson.optString("PricSum_this").equals("0.00")
							|| !tManualJson.optString("SaleChicken_last").equals("0.00"))){
						tExpenseArray.put(tManualJson);// 人工
					}
					if(tFuelJson.length() != 0 &&
							(!tFuelJson.optString("PricSum_this").equals("0.00")
							|| !tFuelJson.optString("SaleChicken_last").equals("0.00"))){
						tExpenseArray.put(tFuelJson);// 燃料
					}
					if(tUtilityJson.length() != 0 &&
							(!tUtilityJson.optString("PricSum_this").equals("0.00")
							|| !tUtilityJson.optString("SaleChicken_last").equals("0.00"))){
						tExpenseArray.put(tUtilityJson);// 水电
					}
					if(tPaddingJson.length() != 0 &&
							(!tPaddingJson.optString("PricSum_this").equals("0.00")
							|| !tPaddingJson.optString("SaleChicken_last").equals("0.00"))){
						tExpenseArray.put(tPaddingJson);// 垫料
					}
					if(tCatcherJson.length() != 0 &&
							(!tCatcherJson.optString("PricSum_this").equals("0.00")
							|| !tCatcherJson.optString("SaleChicken_last").equals("0.00"))){
						tExpenseArray.put(tCatcherJson);// 抓鸡
					}
					if(tDepreciationJson.length() != 0 &&
							(!tDepreciationJson.optString("PricSum_this").equals("0.00")
							|| !tDepreciationJson.optString("SaleChicken_last").equals("0.00"))){
						tExpenseArray.put(tDepreciationJson);  // 折旧
					}
					
					tOtherExpenseJson.put("ItemName", "其它");
					tOtherExpenseJson.put("PricSum_this", PubFun.formatDoubleNum(money_other_e_sum_this/10000,2));
					tOtherExpenseJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_other_e_sum_this/this_weight,2));
					tOtherExpenseJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_other_e_sum_this/this_amount,2));
					tOtherExpenseJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_other_e_sum_last/10000,2));
					tOtherExpenseJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_other_e_sum_last/last_weight,2));
					tOtherExpenseJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_other_e_sum_last/last_amount,2));
					
					if(!tOtherExpenseJson.optString("PricSum_this").equals("0.00")
							|| !tOtherExpenseJson.optString("SaleChicken_last").equals("0.00")){
						tExpenseArray.put(tOtherExpenseJson);// 其它支出
					}
					
					tExpenAllJson.put("ItemName", "成本");  
					tExpenAllJson.put("PricSum_this", PubFun.formatDoubleNum(money_all_e_sum_this/10000,2));
					tExpenAllJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_all_e_sum_this/this_weight,2));
					tExpenAllJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_all_e_sum_this/this_amount,2));
					tExpenAllJson.put("SaleChicken_last", PubFun.formatDoubleNum(money_all_e_sum_last/10000,2));
					tExpenAllJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_all_e_sum_last/last_weight,2));
					tExpenAllJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_all_e_sum_last/last_amount,2));
					tExpenseArray.put(tExpenAllJson);  // 总成本
					resJson.put("Expense", tExpenseArray);
					
					double profit_this = money_all_i_sum_this - money_all_e_sum_this;
					double profit_last = money_all_i_sum_last - money_all_e_sum_last;
					
					this.mLogger.info("盈利报告：本批次盈利=" + profit_this + ",数量=" + this_amount + ",重量=" + this_weight );
					this.mLogger.info("盈利报告：上批次盈利=" + profit_last + ",数量=" + last_amount + ",重量=" + last_weight );
					
					tProfitJson.put("ItemName", "盈(亏)");
					tProfitJson.put("PricSum_this", PubFun.formatDoubleNum(profit_this/10000,2));
					tProfitJson.put("PricePKilo_this", PubFun.formatDoubleNum(profit_this/this_weight,2));
					tProfitJson.put("PricePUnit_this", PubFun.formatDoubleNum(profit_this/this_amount,2));
					tProfitJson.put("SaleChicken", PubFun.formatDoubleNum(profit_last/10000,2));
					tProfitJson.put("PricePKilo", PubFun.formatDoubleNum(profit_last/last_weight,2));
					tProfitJson.put("PricePUnit", PubFun.formatDoubleNum(profit_last/last_amount,2));
					resJson.put("Profits", tProfitJson);
					
					resJson.put("Result", "Success");
					
				}else{
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", "该批次下没有结算数据。");
				}
				dealRes = Constants.RESULT_SUCCESS ;
				/** 业务处理结束 **/
			} catch (Exception e) {
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
			}
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing BreedBatchReqController.getProfitRep");
	}
	
	@RequestMapping("/batchComparation")
	public void batchComparation(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing BreedBatchReqController.batchComparation");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject  tJSONObject = jsonObject.getJSONObject("params");
				int  id_spa = jsonObject.getInt("id_spa");
				int FarmId = tJSONObject.optInt("FarmId");
				String queryUnit = tJSONObject.optString("ViewUnit");
				
				if(queryUnit == null || queryUnit.equals("") || FarmId == 0 ){
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", "请求参数有误，请确认。");
					dealRes = Constants.RESULT_SUCCESS ;
				}else{
					JSONArray tOverViewArray = new JSONArray();
					JSONObject tBatchCodeJson = new JSONObject();
					tBatchCodeJson.put("ItemName","批次");
					JSONObject tEuropIndexJson = new JSONObject();
					tEuropIndexJson.put("ItemName", "欧指");
					JSONObject tChickenOutJson = new JSONObject();
					tChickenOutJson.put("ItemName", "毛鸡");
					JSONObject tOtherIncomJson = new JSONObject();
					tOtherIncomJson.put("ItemName", "其它");
					JSONObject tIncomAllJson = new JSONObject();
					tIncomAllJson.put("ItemName", "收入");
					
					JSONArray tDetailArray = new JSONArray();
					JSONObject tChickJson = new JSONObject();
					tChickJson.put("ItemName", "鸡苗");
					JSONObject tFeedJson = new JSONObject();
					tFeedJson.put("ItemName", "饲料");
					JSONObject tMedicineJson = new JSONObject();
					tMedicineJson.put("ItemName", "药费");
					JSONObject tManualJson = new JSONObject();
					tManualJson.put("ItemName", "人工");
					JSONObject tFuelJson = new JSONObject();
					tFuelJson.put("ItemName", "燃料");
					JSONObject tUtilityJson = new JSONObject();
					tUtilityJson.put("ItemName", "水电");
					JSONObject tPaddingJson = new JSONObject();
					tPaddingJson.put("ItemName", "垫料");
					JSONObject tCatcherJson = new JSONObject();
					tCatcherJson.put("ItemName", "抓鸡");
					JSONObject tDepreciationJson = new JSONObject();
					tDepreciationJson.put("ItemName", "折旧");
					JSONObject tOtherExpenseJson = new JSONObject();
					tOtherExpenseJson.put("ItemName", "其它");
					JSONObject tExpenAllJson = new JSONObject();
					tExpenAllJson.put("ItemName", "成本");
					
					JSONArray tProfitArray = new JSONArray();
					JSONObject tProfitJson = new JSONObject();
					tProfitJson.put("ItemName", "盈(亏)");
					
					/*
					JSONArray tDealPriceArray = new JSONArray();
					JSONObject tChickenPriceJson = new JSONObject();
					tChickenPriceJson.put("ItemName", "毛鸡价");
					JSONObject tChickPriceJson = new JSONObject();
					tChickPriceJson.put("ItemName", "鸡苗价");
					JSONObject tFeedPriceJson = new JSONObject();
					tFeedPriceJson.put("ItemName", "饲料价");
					*/
					// 饲养天数
					int feedAges_index1 = 0;
					int feedAges_index2 = 0;
					int feedAges_index3 = 0;
					int feedAges_index4 = 0;
					int feedAges_index5 = 0;
					
					String tSQL1 = "select id as farmBreedId,batch_code,datediff(ifnull(settle_date,curdate()),place_date) as feedDays from s_b_farm_breed where farm_id = "+FarmId+" and batch_status = '02' ORDER BY batch_code desc LIMIT 5 ";
					mLogger.info("batchComparation.SQL111=" + tSQL1);
					List<HashMap<String, Object>> tBatchCodes = tBaseQueryService.selectMapByAny(tSQL1);
					
					for(int i = 1; i <= 5; i++){
						tBatchCodeJson.put("index"+i, "-");
						tChickenOutJson.put("index"+i, "-");
						tIncomAllJson.put("index"+i, "-");
						tChickJson.put("index"+i, "-");
						tFeedJson.put("index"+i, "-");
						tMedicineJson.put("index"+i, "-");
						tManualJson.put("index"+i, "-");
						tFuelJson.put("index"+i, "-");
						tUtilityJson.put("index"+i, "-");
						tPaddingJson.put("index"+i, "-");
						tCatcherJson.put("index"+i, "-");
						tDepreciationJson.put("index"+i, "-");
						tOtherExpenseJson.put("index"+i, "-");
						tExpenAllJson.put("index"+i, "-");
						tProfitJson.put("index"+i, "-");
					}
					
					int m = 1 ;
					for(m = 1; m <= tBatchCodes.size(); m++){
						tBatchCodeJson.put("index"+m, tBatchCodes.get(m-1).get("batch_code"));
						if(m == 1){
							feedAges_index1 = ((Long)(tBatchCodes.get(m-1).get("feedDays"))).intValue();
						}else if(m == 2){
							feedAges_index2 = ((Long)(tBatchCodes.get(m-1).get("feedDays"))).intValue();
						}else if(m == 3){
							feedAges_index3 = ((Long)(tBatchCodes.get(m-1).get("feedDays"))).intValue();
						}else if(m == 4){
							feedAges_index4 = ((Long)(tBatchCodes.get(m-1).get("feedDays"))).intValue();
						}else if(m == 5){
							feedAges_index5 = ((Long)(tBatchCodes.get(m-1).get("feedDays"))).intValue();
						}
					}
					
					String tSQL = "SELECT ifnull(b.fee_code,'null') as fee_code,b.fee_name,a.farmBreedId,a.batch_code,b.quentity,b.weight,b.money_sum,b.price_pu "
							+"from (select id as farmBreedId,batch_code from s_b_farm_breed where farm_id = "+FarmId+" and batch_status = '02' ORDER BY batch_code desc LIMIT 5 "
							+ " ) a left join s_b_farm_settle b on a.farmBreedId = b.farm_breed_id "
							+ " order by a.batch_code desc,b.fee_code" ;
					mLogger.info("batchComparation.SQL222=" + tSQL);
					List<HashMap<String, Object>> tDetail = tBaseQueryService.selectMapByAny(tSQL);
					// 各批次毛鸡数量合计
					int amount_index1 = 0;
					int amount_index2 = 0;
					int amount_index3 = 0;
					int amount_index4 = 0;
					int amount_index5 = 0;
					// 各批次毛鸡重量合计
					double weight_index1 = 0;
					double weight_index2 = 0;
					double weight_index3 = 0;
					double weight_index4 = 0;
					double weight_index5 = 0;
					// 先计算各批次 毛鸡结算的数量以及重量
					for(HashMap<String,Object> tRow:tDetail){
						String feeCode = (String)tRow.get("fee_code");
						String batch_code = (String)tRow.get("batch_code");
						if(feeCode.equals("4001")){
							if(batch_code.equals(tBatchCodeJson.opt("index1"))){
								amount_index1 += (int)tRow.get("quentity");
								weight_index1 += getDoubleValue((BigDecimal)tRow.get("weight"));
							}else if(batch_code.equals(tBatchCodeJson.opt("index2"))){
								amount_index2 += (int)tRow.get("quentity");
								weight_index2 += getDoubleValue((BigDecimal)tRow.get("weight"));
							}else if(batch_code.equals(tBatchCodeJson.opt("index3"))){
								amount_index3 += (int)tRow.get("quentity");
								weight_index3 += getDoubleValue((BigDecimal)tRow.get("weight"));
							}else if(batch_code.equals(tBatchCodeJson.opt("index4"))){
								amount_index4 += (int)tRow.get("quentity");
								weight_index4 += getDoubleValue((BigDecimal)tRow.get("weight"));
							}else if(batch_code.equals(tBatchCodeJson.opt("index5"))){
								amount_index5 += (int)tRow.get("quentity");
								weight_index5 += getDoubleValue((BigDecimal)tRow.get("weight"));
							}
						}
					}
					/*
					// 各批次毛鸡单价(元/公斤)
					double price_index1 = 0;
					double price_index2 = 0;
					double price_index3 = 0;
					double price_index4 = 0;
					double price_index5 = 0;
					// 各批次鸡苗单价(元/只)
					double price_chick_index1 = 0;
					double price_chick_index2 = 0;
					double price_chick_index3 = 0;
					double price_chick_index4 = 0;
					double price_chick_index5 = 0;
					// 各批次饲料单价(元/公斤)
					double price_feed_index1 = 0;
					double price_feed_index2 = 0;
					double price_feed_index3 = 0;
					double price_feed_index4 = 0;
					double price_feed_index5 = 0;
					*/
					// 各批次鸡苗数量					
					int amount_chick_index1 = 0;
					int amount_chick_index2 = 0;
					int amount_chick_index3 = 0;
					int amount_chick_index4 = 0;
					int amount_chick_index5 = 0;
					// 各批次毛鸡收入
					double money_chicken_i_sum_index1 = 0 ;
					double money_chicken_i_sum_index2 = 0 ;
					double money_chicken_i_sum_index3 = 0 ;
					double money_chicken_i_sum_index4 = 0 ;
					double money_chicken_i_sum_index5 = 0 ;
					// 各批次其它收入
					double money_other_i_sum_index1 = 0 ;
					double money_other_i_sum_index2 = 0 ;
					double money_other_i_sum_index3 = 0 ;
					double money_other_i_sum_index4 = 0 ;
					double money_other_i_sum_index5 = 0 ;
					// 各批次总收入合计
					double money_all_i_sum_index1 = 0 ;
					double money_all_i_sum_index2 = 0 ;
					double money_all_i_sum_index3 = 0 ;
					double money_all_i_sum_index4 = 0 ;
					double money_all_i_sum_index5 = 0 ;
					
					// 各批次饲料总金额合计
					double money_feed_sum_index1 = 0 ;
					double money_feed_sum_index2 = 0 ;
					double money_feed_sum_index3 = 0 ;
					double money_feed_sum_index4 = 0 ;
					double money_feed_sum_index5 = 0 ;
					// 各批次饲料重量合计
					double weight_feed_sum_index1 = 0 ;
					double weight_feed_sum_index2 = 0 ;
					double weight_feed_sum_index3 = 0 ;
					double weight_feed_sum_index4 = 0 ;
					double weight_feed_sum_index5 = 0 ;
					// 各批次其它支出
					double money_other_e_sum_index1 = 0 ;
					double money_other_e_sum_index2 = 0 ;
					double money_other_e_sum_index3 = 0 ;
					double money_other_e_sum_index4 = 0 ;
					double money_other_e_sum_index5 = 0 ;
					
					// 各批次总支出合计
					double money_all_e_sum_index1 = 0 ;
					double money_all_e_sum_index2 = 0 ;
					double money_all_e_sum_index3 = 0 ;
					double money_all_e_sum_index4 = 0 ;
					double money_all_e_sum_index5 = 0 ;
					
					// 再计算其它指标
					for(HashMap<String,Object> tRow:tDetail){
						String feeCode = (String)tRow.get("fee_code");
						String batch_code = (String)tRow.get("batch_code");
						if(batch_code.equals(tBatchCodeJson.opt("index1"))){
							if(feeCode.equals("4001")){
								// 毛鸡结算
								money_chicken_i_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_i_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
//								price_index1 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								
							}else if(feeCode.equals("1001")){
								// 鸡苗结算
								if(queryUnit.equals("Money")){
									tChickJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tChickJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tChickJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								amount_chick_index1 = (int)tRow.get("quentity");
								money_all_e_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
//								price_chick_index1 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								
							}else if(feeCode.equals("2001") || feeCode.equals("2002") || feeCode.equals("2003")){
								// 饲料结算
								money_feed_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_e_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								weight_feed_sum_index1 += ((BigDecimal)tRow.get("weight")).doubleValue() ;
								
							}else if(feeCode.equals("3001")){
								// 药品
								if(queryUnit.equals("Money")){
									tMedicineJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tMedicineJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tMedicineJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								money_all_e_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3002")){
								// 燃料
								if(queryUnit.equals("Money")){
									tFuelJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tFuelJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tFuelJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								money_all_e_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3003")){
								// 人工
								if(queryUnit.equals("Money")){
									tManualJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tManualJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tManualJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								money_all_e_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3006")){
								// 水电
								if(queryUnit.equals("Money")){
									tUtilityJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tUtilityJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tUtilityJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								money_all_e_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3007")){
								// 垫料
								if(queryUnit.equals("Money")){
									tPaddingJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tPaddingJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tPaddingJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								money_all_e_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3008")){
								// 抓鸡
								if(queryUnit.equals("Money")){
									tCatcherJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tCatcherJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tCatcherJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								money_all_e_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3004")){
								// 其它费用
								money_other_e_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_e_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3005")){
								// 折旧费用
								if(queryUnit.equals("Money")){
									tDepreciationJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tDepreciationJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tDepreciationJson.put("index1", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								money_all_e_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("4002") || feeCode.equals("4003")){
								// 其它收入
								money_other_i_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_i_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
							}
						}else if(batch_code.equals(tBatchCodeJson.opt("index2"))){
							
							if(feeCode.equals("4001")){
								// 毛鸡结算
								money_chicken_i_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_i_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
//								price_index2 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								
							}else if(feeCode.equals("1001")){
								// 鸡苗结算
								if(queryUnit.equals("Money")){
									tChickJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tChickJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index2,2));
								}else if(queryUnit.equals("weight")){
									tChickJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index2,2));
								}
								amount_chick_index2 = (int)tRow.get("quentity");
								money_all_e_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
//								price_chick_index2 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								
							}else if(feeCode.equals("2001") || feeCode.equals("2002") || feeCode.equals("2003")){
								// 饲料结算
								money_feed_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_e_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								weight_feed_sum_index2 += ((BigDecimal)tRow.get("weight")).doubleValue() ;
								
							}else if(feeCode.equals("3001")){
								// 药品
								if(queryUnit.equals("Money")){
									tMedicineJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tMedicineJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index2,2));
								}else if(queryUnit.equals("weight")){
									tMedicineJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index2,2));
								}
								money_all_e_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3002")){
								// 燃料
								if(queryUnit.equals("Money")){
									tFuelJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tFuelJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index2,2));
								}else if(queryUnit.equals("weight")){
									tFuelJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index2,2));
								}
								money_all_e_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3003")){
								// 人工
								if(queryUnit.equals("Money")){
									tManualJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tManualJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index2,2));
								}else if(queryUnit.equals("weight")){
									tManualJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index2,2));
								}
								money_all_e_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3006")){
								// 水电
								if(queryUnit.equals("Money")){
									tUtilityJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tUtilityJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index2,2));
								}else if(queryUnit.equals("weight")){
									tUtilityJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index2,2));
								}
								money_all_e_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3007")){
								// 垫料
								if(queryUnit.equals("Money")){
									tPaddingJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tPaddingJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index2,2));
								}else if(queryUnit.equals("weight")){
									tPaddingJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index2,2));
								}
								money_all_e_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3008")){
								// 抓鸡
								if(queryUnit.equals("Money")){
									tCatcherJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tCatcherJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index2,2));
								}else if(queryUnit.equals("weight")){
									tCatcherJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index2,2));
								}
								money_all_e_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3004")){
								// 其它费用
								money_other_e_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_e_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3005")){
								// 折旧费用
								if(queryUnit.equals("Money")){
									tDepreciationJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tDepreciationJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tDepreciationJson.put("index2", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								money_all_e_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("4002") || feeCode.equals("4003")){
								// 其它收入
								money_other_i_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_i_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
							}
						}else if(batch_code.equals(tBatchCodeJson.opt("index3"))){
							if(feeCode.equals("4001")){
								// 毛鸡结算
								money_chicken_i_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_i_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
//								price_index3 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								
							}else if(feeCode.equals("1001")){
								// 鸡苗结算
								if(queryUnit.equals("Money")){
									tChickJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tChickJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index3,2));
								}else if(queryUnit.equals("weight")){
									tChickJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index3,2));
								}
								amount_chick_index3 = (int)tRow.get("quentity");
								money_all_e_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
//								price_chick_index3 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								
							}else if(feeCode.equals("2001") || feeCode.equals("2002") || feeCode.equals("2003")){
								// 饲料结算
								money_feed_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_e_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								weight_feed_sum_index3 += ((BigDecimal)tRow.get("weight")).doubleValue() ;
								
							}else if(feeCode.equals("3001")){
								// 药品
								if(queryUnit.equals("Money")){
									tMedicineJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tMedicineJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index3,2));
								}else if(queryUnit.equals("weight")){
									tMedicineJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index3,2));
								}
								money_all_e_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3002")){
								// 燃料
								if(queryUnit.equals("Money")){
									tFuelJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tFuelJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index3,2));
								}else if(queryUnit.equals("weight")){
									tFuelJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index3,2));
								}
								money_all_e_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3003")){
								// 人工
								if(queryUnit.equals("Money")){
									tManualJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tManualJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index3,2));
								}else if(queryUnit.equals("weight")){
									tManualJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index3,2));
								}
								money_all_e_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3006")){
								// 水电
								if(queryUnit.equals("Money")){
									tUtilityJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tUtilityJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index3,2));
								}else if(queryUnit.equals("weight")){
									tUtilityJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index3,2));
								}
								money_all_e_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3007")){
								// 垫料
								if(queryUnit.equals("Money")){
									tPaddingJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tPaddingJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index3,2));
								}else if(queryUnit.equals("weight")){
									tPaddingJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index3,2));
								}
								money_all_e_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3008")){
								// 抓鸡
								if(queryUnit.equals("Money")){
									tCatcherJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tCatcherJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index3,2));
								}else if(queryUnit.equals("weight")){
									tCatcherJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index3,2));
								}
								money_all_e_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3004")){
								// 其它费用
								money_other_e_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_e_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3005")){
								// 折旧费用
								if(queryUnit.equals("Money")){
									tDepreciationJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tDepreciationJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tDepreciationJson.put("index3", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								money_all_e_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("4002") || feeCode.equals("4003")){
								// 其它收入
								money_other_i_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_i_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
							}
						}else if(batch_code.equals(tBatchCodeJson.opt("index4"))){
							
							if(feeCode.equals("4001")){
								// 毛鸡结算
								money_chicken_i_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_i_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
//								price_index4 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								
							}else if(feeCode.equals("1001")){
								// 鸡苗结算
								if(queryUnit.equals("Money")){
									tChickJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tChickJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index4,2));
								}else if(queryUnit.equals("weight")){
									tChickJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index4,2));
								}
								amount_chick_index4 = (int)tRow.get("quentity");
								money_all_e_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
//								price_chick_index4 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								
							}else if(feeCode.equals("2001") || feeCode.equals("2002") || feeCode.equals("2003")){
								// 饲料结算
								money_feed_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_e_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								weight_feed_sum_index4 += ((BigDecimal)tRow.get("weight")).doubleValue() ;
								
							}else if(feeCode.equals("3001")){
								// 药品
								if(queryUnit.equals("Money")){
									tMedicineJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tMedicineJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index4,2));
								}else if(queryUnit.equals("weight")){
									tMedicineJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index4,2));
								}
								money_all_e_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3002")){
								// 燃料
								if(queryUnit.equals("Money")){
									tFuelJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tFuelJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index4,2));
								}else if(queryUnit.equals("weight")){
									tFuelJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index4,2));
								}
								money_all_e_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3003")){
								// 人工
								if(queryUnit.equals("Money")){
									tManualJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tManualJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index4,2));
								}else if(queryUnit.equals("weight")){
									tManualJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index4,2));
								}
								money_all_e_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3006")){
								// 水电
								if(queryUnit.equals("Money")){
									tUtilityJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tUtilityJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index4,2));
								}else if(queryUnit.equals("weight")){
									tUtilityJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index4,2));
								}
								money_all_e_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3007")){
								// 垫料
								if(queryUnit.equals("Money")){
									tPaddingJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tPaddingJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index4,2));
								}else if(queryUnit.equals("weight")){
									tPaddingJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index4,2));
								}
								money_all_e_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3008")){
								// 抓鸡
								if(queryUnit.equals("Money")){
									tCatcherJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tCatcherJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index4,2));
								}else if(queryUnit.equals("weight")){
									tCatcherJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index4,2));
								}
								money_all_e_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3004")){
								// 其它费用
								money_other_e_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_e_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3005")){
								// 折旧费用
								if(queryUnit.equals("Money")){
									tDepreciationJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tDepreciationJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tDepreciationJson.put("index4", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								money_all_e_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("4002") || feeCode.equals("4003")){
								// 其它收入
								money_other_i_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_i_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
							}
						}else if(batch_code.equals(tBatchCodeJson.opt("index5"))){
							
							if(feeCode.equals("4001")){
								// 毛鸡结算
								money_chicken_i_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_i_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
//								price_index5 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								
							}else if(feeCode.equals("1001")){
								// 鸡苗结算
								if(queryUnit.equals("Money")){
									tChickJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tChickJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index5,2));
								}else if(queryUnit.equals("weight")){
									tChickJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index5,2));
								}
								amount_chick_index5 = (int)tRow.get("quentity");
								money_all_e_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
//								price_chick_index5 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								
							}else if(feeCode.equals("2001") || feeCode.equals("2002") || feeCode.equals("2003")){
								// 饲料结算
								money_feed_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_e_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								weight_feed_sum_index5 += ((BigDecimal)tRow.get("weight")).doubleValue() ;
								
							}else if(feeCode.equals("3001")){
								// 药品
								if(queryUnit.equals("Money")){
									tMedicineJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tMedicineJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index5,2));
								}else if(queryUnit.equals("weight")){
									tMedicineJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index5,2));
								}
								money_all_e_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3002")){
								// 燃料
								if(queryUnit.equals("Money")){
									tFuelJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tFuelJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index5,2));
								}else if(queryUnit.equals("weight")){
									tFuelJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index5,2));
								}
								money_all_e_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3003")){
								// 人工
								if(queryUnit.equals("Money")){
									tManualJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tManualJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index5,2));
								}else if(queryUnit.equals("weight")){
									tManualJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index5,2));
								}
								money_all_e_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3006")){
								// 水电
								if(queryUnit.equals("Money")){
									tUtilityJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tUtilityJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index5,2));
								}else if(queryUnit.equals("weight")){
									tUtilityJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index5,2));
								}
								money_all_e_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3007")){
								// 垫料
								if(queryUnit.equals("Money")){
									tPaddingJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tPaddingJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index5,2));
								}else if(queryUnit.equals("weight")){
									tPaddingJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index5,2));
								}
								money_all_e_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3008")){
								// 抓鸡
								if(queryUnit.equals("Money")){
									tCatcherJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tCatcherJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index5,2));
								}else if(queryUnit.equals("weight")){
									tCatcherJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index5,2));
								}
								money_all_e_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3004")){
								// 其它费用
								money_other_e_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_e_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("3005")){
								// 折旧费用
								if(queryUnit.equals("Money")){
									tDepreciationJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/10000,2));
								}else if(queryUnit.equals("quentity")){
									tDepreciationJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/amount_index1,2));
								}else if(queryUnit.equals("weight")){
									tDepreciationJson.put("index5", PubFun.formatDoubleNum(((BigDecimal)tRow.get("money_sum")).doubleValue()/weight_index1,2));
								}
								money_all_e_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								
							}else if(feeCode.equals("4002") || feeCode.equals("4003")){
								// 其它收入
								money_other_i_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								money_all_i_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
							}
						}
					}
					
					if(queryUnit.equals("Money")){
						tChickenOutJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_chicken_i_sum_index1/10000,2): "-");
						tChickenOutJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_chicken_i_sum_index2/10000,2): "-");
						tChickenOutJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_chicken_i_sum_index3/10000,2): "-");
						tChickenOutJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_chicken_i_sum_index4/10000,2): "-");
						tChickenOutJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_chicken_i_sum_index5/10000,2): "-");
							
						tOtherIncomJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_other_i_sum_index1/10000,2): "-");
						tOtherIncomJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_other_i_sum_index2/10000,2): "-");
						tOtherIncomJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_other_i_sum_index3/10000,2): "-");
						tOtherIncomJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_other_i_sum_index4/10000,2): "-");
						tOtherIncomJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_other_i_sum_index5/10000,2): "-");
						
						tIncomAllJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_all_i_sum_index1/10000,2): "-");
						tIncomAllJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_all_i_sum_index2/10000,2): "-");
						tIncomAllJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_all_i_sum_index3/10000,2): "-");
						tIncomAllJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_all_i_sum_index4/10000,2): "-");
						tIncomAllJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_all_i_sum_index5/10000,2): "-");
						
						tFeedJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_feed_sum_index1/10000,2) : "-");
						tFeedJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_feed_sum_index2/10000,2) : "-");
						tFeedJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_feed_sum_index3/10000,2) : "-");
						tFeedJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_feed_sum_index4/10000,2) : "-");
						tFeedJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_feed_sum_index5/10000,2) : "-");
						
						tOtherExpenseJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_other_e_sum_index1/10000,2) : "-");
						tOtherExpenseJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_other_e_sum_index2/10000,2) : "-");
						tOtherExpenseJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_other_e_sum_index3/10000,2) : "-");
						tOtherExpenseJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_other_e_sum_index4/10000,2) : "-");
						tOtherExpenseJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_other_e_sum_index5/10000,2) : "-");
						
						tExpenAllJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_all_e_sum_index1/10000,2) : "-");
						tExpenAllJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_all_e_sum_index2/10000,2) : "-");
						tExpenAllJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_all_e_sum_index3/10000,2) : "-");
						tExpenAllJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_all_e_sum_index4/10000,2) : "-");
						tExpenAllJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_all_e_sum_index5/10000,2) : "-");
						
						tProfitJson.put("index1", m >= 2? PubFun.formatDoubleNum((money_all_i_sum_index1 - money_all_e_sum_index1)/10000,2 ): "-");
						tProfitJson.put("index2", m >= 3? PubFun.formatDoubleNum((money_all_i_sum_index2 - money_all_e_sum_index2)/10000,2 ): "-");
						tProfitJson.put("index3", m >= 4? PubFun.formatDoubleNum((money_all_i_sum_index3 - money_all_e_sum_index3)/10000,2 ): "-");
						tProfitJson.put("index4", m >= 5? PubFun.formatDoubleNum((money_all_i_sum_index4 - money_all_e_sum_index4)/10000,2 ): "-");
						tProfitJson.put("index5", m >= 6? PubFun.formatDoubleNum((money_all_i_sum_index5 - money_all_e_sum_index5)/10000,2 ): "-");
						
					}else if(queryUnit.equals("quentity")){
						tChickenOutJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_chicken_i_sum_index1/amount_index1,2): "-");
						tChickenOutJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_chicken_i_sum_index2/amount_index2,2): "-");
						tChickenOutJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_chicken_i_sum_index3/amount_index3,2): "-");
						tChickenOutJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_chicken_i_sum_index4/amount_index4,2): "-");
						tChickenOutJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_chicken_i_sum_index5/amount_index5,2): "-");
						
						tOtherIncomJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_other_i_sum_index1/amount_index1,2): "-");
						tOtherIncomJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_other_i_sum_index2/amount_index2,2): "-");
						tOtherIncomJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_other_i_sum_index3/amount_index3,2): "-");
						tOtherIncomJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_other_i_sum_index4/amount_index4,2): "-");
						tOtherIncomJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_other_i_sum_index5/amount_index5,2): "-");
						
						tIncomAllJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_all_i_sum_index1/amount_index1,2): "-");
						tIncomAllJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_all_i_sum_index2/amount_index2,2): "-");
						tIncomAllJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_all_i_sum_index3/amount_index3,2): "-");
						tIncomAllJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_all_i_sum_index4/amount_index4,2): "-");
						tIncomAllJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_all_i_sum_index5/amount_index5,2): "-");
						
						tFeedJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_feed_sum_index1/amount_index1,2): "-");
						tFeedJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_feed_sum_index2/amount_index2,2): "-");
						tFeedJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_feed_sum_index3/amount_index3,2): "-");
						tFeedJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_feed_sum_index4/amount_index4,2): "-");
						tFeedJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_feed_sum_index5/amount_index5,2): "-");
						
						tOtherExpenseJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_other_e_sum_index1/amount_index1,2) : "-");
						tOtherExpenseJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_other_e_sum_index2/amount_index2,2) : "-");
						tOtherExpenseJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_other_e_sum_index3/amount_index3,2) : "-");
						tOtherExpenseJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_other_e_sum_index4/amount_index4,2) : "-");
						tOtherExpenseJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_other_e_sum_index5/amount_index5,2) : "-");
						
						tExpenAllJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_all_e_sum_index1/amount_index1,2): "-");
						tExpenAllJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_all_e_sum_index2/amount_index2,2): "-");
						tExpenAllJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_all_e_sum_index3/amount_index3,2): "-");
						tExpenAllJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_all_e_sum_index4/amount_index4,2): "-");
						tExpenAllJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_all_e_sum_index5/amount_index5,2): "-");
						
						tProfitJson.put("index1", m >= 2? PubFun.formatDoubleNum((money_all_i_sum_index1 - money_all_e_sum_index1)/amount_index1,2 ): "-");
						tProfitJson.put("index2", m >= 3? PubFun.formatDoubleNum((money_all_i_sum_index2 - money_all_e_sum_index2)/amount_index2,2 ): "-");
						tProfitJson.put("index3", m >= 4? PubFun.formatDoubleNum((money_all_i_sum_index3 - money_all_e_sum_index3)/amount_index3,2 ): "-");
						tProfitJson.put("index4", m >= 5? PubFun.formatDoubleNum((money_all_i_sum_index4 - money_all_e_sum_index4)/amount_index4,2 ): "-");
						tProfitJson.put("index5", m >= 6? PubFun.formatDoubleNum((money_all_i_sum_index5 - money_all_e_sum_index5)/amount_index5,2 ): "-");
					}else if(queryUnit.equals("weight")){
						tChickenOutJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_chicken_i_sum_index1/weight_index1,2): "-");
						tChickenOutJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_chicken_i_sum_index2/weight_index2,2): "-");
						tChickenOutJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_chicken_i_sum_index3/weight_index3,2): "-");
						tChickenOutJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_chicken_i_sum_index4/weight_index4,2): "-");
						tChickenOutJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_chicken_i_sum_index5/weight_index5,2): "-");
						
						tOtherIncomJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_other_i_sum_index1/weight_index1,2): "-");
						tOtherIncomJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_other_i_sum_index2/weight_index2,2): "-");
						tOtherIncomJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_other_i_sum_index3/weight_index3,2): "-");
						tOtherIncomJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_other_i_sum_index4/weight_index4,2): "-");
						tOtherIncomJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_other_i_sum_index5/weight_index5,2): "-");
						
						tIncomAllJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_all_i_sum_index1/weight_index1,2): "-");
						tIncomAllJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_all_i_sum_index2/weight_index2,2): "-");
						tIncomAllJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_all_i_sum_index3/weight_index3,2): "-");
						tIncomAllJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_all_i_sum_index4/weight_index4,2): "-");
						tIncomAllJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_all_i_sum_index5/weight_index5,2): "-");
						
						tFeedJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_feed_sum_index1/weight_index1,2): "-");
						tFeedJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_feed_sum_index1/weight_index2,2): "-");
						tFeedJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_feed_sum_index1/weight_index3,2): "-");
						tFeedJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_feed_sum_index1/weight_index4,2): "-");
						tFeedJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_feed_sum_index1/weight_index5,2): "-");
						
						tOtherExpenseJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_other_e_sum_index1/weight_index1,2) : "-");
						tOtherExpenseJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_other_e_sum_index2/weight_index2,2) : "-");
						tOtherExpenseJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_other_e_sum_index3/weight_index3,2) : "-");
						tOtherExpenseJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_other_e_sum_index4/weight_index4,2) : "-");
						tOtherExpenseJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_other_e_sum_index5/weight_index5,2) : "-");
						
						tExpenAllJson.put("index1", m >= 2? PubFun.formatDoubleNum(money_all_e_sum_index1/weight_index1,2): "-");
						tExpenAllJson.put("index2", m >= 3? PubFun.formatDoubleNum(money_all_e_sum_index2/weight_index2,2): "-");
						tExpenAllJson.put("index3", m >= 4? PubFun.formatDoubleNum(money_all_e_sum_index3/weight_index3,2): "-");
						tExpenAllJson.put("index4", m >= 5? PubFun.formatDoubleNum(money_all_e_sum_index4/weight_index4,2): "-");
						tExpenAllJson.put("index5", m >= 6? PubFun.formatDoubleNum(money_all_e_sum_index5/weight_index5,2): "-");
						
						tProfitJson.put("index1", m >= 2? PubFun.formatDoubleNum((money_all_i_sum_index1 - money_all_e_sum_index1)/weight_index1,2 ): "-");
						tProfitJson.put("index2", m >= 3? PubFun.formatDoubleNum((money_all_i_sum_index2 - money_all_e_sum_index2)/weight_index2,2 ): "-");
						tProfitJson.put("index3", m >= 4? PubFun.formatDoubleNum((money_all_i_sum_index3 - money_all_e_sum_index3)/weight_index3,2 ): "-");
						tProfitJson.put("index4", m >= 5? PubFun.formatDoubleNum((money_all_i_sum_index4 - money_all_e_sum_index4)/weight_index4,2 ): "-");
						tProfitJson.put("index5", m >= 6? PubFun.formatDoubleNum((money_all_i_sum_index5 - money_all_e_sum_index5)/weight_index5,2 ): "-");
					}
					
					// 计算公式：((体重x成活率)/(料肉比x出栏日龄)) x 10000。
					//weight_index1-毛鸡重量   amount_index1-毛鸡数量   amount_chick_index1-鸡苗数量   weight_feed_sum_index1-饲料总量    feedAges_index1-养殖天数
					tEuropIndexJson.put("index1",m >= 2? getEuropIndexValue(weight_index1,amount_index1,amount_chick_index1,weight_feed_sum_index1,feedAges_index1): "-");
					tEuropIndexJson.put("index2",m >= 3? getEuropIndexValue(weight_index2,amount_index2,amount_chick_index2,weight_feed_sum_index2,feedAges_index2): "-");
					tEuropIndexJson.put("index3",m >= 4? getEuropIndexValue(weight_index3,amount_index3,amount_chick_index3,weight_feed_sum_index3,feedAges_index3): "-");
					tEuropIndexJson.put("index4",m >= 5? getEuropIndexValue(weight_index4,amount_index4,amount_chick_index4,weight_feed_sum_index4,feedAges_index4): "-");
					tEuropIndexJson.put("index5",m >= 6? getEuropIndexValue(weight_index5,amount_index5,amount_chick_index5,weight_feed_sum_index5,feedAges_index5): "-");
					
					tOverViewArray.put(tBatchCodeJson);
					tOverViewArray.put(tEuropIndexJson);
					tOverViewArray.put(tChickenOutJson);
					tOverViewArray.put(tOtherIncomJson);
					tOverViewArray.put(tIncomAllJson);
					
					tDetailArray.put(tChickJson);
					tDetailArray.put(tFeedJson);
					
					tDetailArray.put(tMedicineJson);
					tDetailArray.put(tManualJson);
					tDetailArray.put(tFuelJson);
					tDetailArray.put(tUtilityJson);
					tDetailArray.put(tPaddingJson);
					tDetailArray.put(tCatcherJson);
					tDetailArray.put(tDepreciationJson);
					tDetailArray.put(tOtherExpenseJson);
					tDetailArray.put(tExpenAllJson);
					
					tProfitArray.put(tProfitJson);
					
					resJson.put("ViewUnit", queryUnit);
					resJson.put("OverView", tOverViewArray);
					resJson.put("Detail", tDetailArray);
					resJson.put("Profit", tProfitArray);
					
					/*  
					tChickenPriceJson.put("index1", m >= 2? PubFun.formatDoubleNum(price_index1,2): "-");
					tChickenPriceJson.put("index2", m >= 3? PubFun.formatDoubleNum(price_index2,2): "-");
					tChickenPriceJson.put("index3", m >= 4? PubFun.formatDoubleNum(price_index3,2): "-");
					tChickenPriceJson.put("index4", m >= 5? PubFun.formatDoubleNum(price_index4,2): "-");
					tChickenPriceJson.put("index5", m >= 6? PubFun.formatDoubleNum(price_index5,2): "-");
					
					tChickPriceJson.put("index1", m >= 2? PubFun.formatDoubleNum(price_chick_index1,2): "-");
					tChickPriceJson.put("index2", m >= 3? PubFun.formatDoubleNum(price_chick_index2,2): "-");
					tChickPriceJson.put("index3", m >= 4? PubFun.formatDoubleNum(price_chick_index3,2): "-");
					tChickPriceJson.put("index4", m >= 5? PubFun.formatDoubleNum(price_chick_index4,2): "-");
					tChickPriceJson.put("index5", m >= 6? PubFun.formatDoubleNum(price_chick_index5,2): "-");
					
					price_feed_index1 = money_feed_sum_index1/weight_feed_sum_index1 ;
					price_feed_index2 = money_feed_sum_index2/weight_feed_sum_index2 ;
					price_feed_index3 = money_feed_sum_index3/weight_feed_sum_index3 ;
					price_feed_index4 = money_feed_sum_index4/weight_feed_sum_index4 ;
					price_feed_index5 = money_feed_sum_index5/weight_feed_sum_index5 ;
					
					tFeedPriceJson.put("index1", m >= 2? PubFun.formatDoubleNum(price_feed_index1,2): "-");
					tFeedPriceJson.put("index2", m >= 3? PubFun.formatDoubleNum(price_feed_index2,2): "-");
					tFeedPriceJson.put("index3", m >= 4? PubFun.formatDoubleNum(price_feed_index3,2): "-");
					tFeedPriceJson.put("index4", m >= 5? PubFun.formatDoubleNum(price_feed_index4,2): "-");
					tFeedPriceJson.put("index5", m >= 6? PubFun.formatDoubleNum(price_feed_index5,2): "-");
					
					tDealPriceArray.put(tChickenPriceJson);
					tDealPriceArray.put(tChickPriceJson);
					tDealPriceArray.put(tFeedPriceJson);
					
					resJson.put("DealPrice", tDealPriceArray);
					*/
					
					resJson.put("Result", "Success");
					dealRes = Constants.RESULT_SUCCESS ;
				}
				//** 业务处理结束 **//*
			} catch (Exception e) {
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
			}
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing BreedBatchReqController.batchComparation");
	}
	
	@RequestMapping("/batchSettleComparation")
	public void batchSettleComparation(HttpServletRequest request,HttpServletResponse response){
		 	mLogger.info("=====Now start executing BreedBatchReqController.batchSettleComparation");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject  tJSONObject = jsonObject.getJSONObject("params");
				int  id_spa = jsonObject.getInt("id_spa");
				int FarmId = tJSONObject.optInt("FarmId");
				
				if(FarmId == 0){
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", "请求参数有误，请确认。");
					dealRes = Constants.RESULT_SUCCESS ;
				}else{
					JSONArray tDealPriceArray = new JSONArray();
					JSONObject tBatchCodeJson = new JSONObject();
					tBatchCodeJson.put("ItemName","批次");
					JSONObject tChickenFirmJson = new JSONObject();
					tChickenFirmJson.put("ItemName", "厂家");
					JSONObject tChickenPriceJson = new JSONObject();
					tChickenPriceJson.put("ItemName", "毛鸡价");
					JSONObject tChickFirmJson = new JSONObject();
					tChickFirmJson.put("ItemName", "厂家");
					JSONObject tChickPriceJson = new JSONObject();
					tChickPriceJson.put("ItemName", "鸡苗价");
					JSONObject tFeedFirmJson = new JSONObject();
					tFeedFirmJson.put("ItemName", "厂家");
					JSONObject tFeedPriceJson = new JSONObject();
					tFeedPriceJson.put("ItemName", "饲料价");
					
					String tSQL1 = "select id as farmBreedId,batch_code,datediff(ifnull(settle_date,curdate()),place_date) as feedDays from s_b_farm_breed where farm_id = "+FarmId+" and batch_status = '02' ORDER BY batch_code desc LIMIT 5 ";
					mLogger.info("======breedBatchReqController.batchSettleComparation.SQL111=" + tSQL1);
					List<HashMap<String, Object>> tBatchCodes = tBaseQueryService.selectMapByAny(tSQL1);
					
					int m = 1 ;
					for(m = 1; m <= tBatchCodes.size(); m++){
						tBatchCodeJson.put("index"+m, tBatchCodes.get(m-1).get("batch_code"));
					}
					
					String tSQL = "SELECT ifnull(b.fee_code,'null') as fee_code,b.fee_name,a.farmBreedId,a.batch_code,b.weight,b.money_sum,b.price_pu,b.company_name "
							+"from (select id as farmBreedId,batch_code from s_b_farm_breed where farm_id = "+FarmId+" and batch_status = '02' ORDER BY batch_code desc LIMIT 5 "
							+ " ) a left join s_b_farm_settle b on a.farmBreedId = b.farm_breed_id "
							+ " order by a.batch_code desc,b.fee_code" ;
					mLogger.info("==========breedBatchReqController.batchSettleComparation.SQL222=" + tSQL);
					List<HashMap<String, Object>> tDetail = tBaseQueryService.selectMapByAny(tSQL);
					
					// 各批次毛鸡单价(元/公斤)
					double price_index1 = 0;
					double price_index2 = 0;
					double price_index3 = 0;
					double price_index4 = 0;
					double price_index5 = 0;
					// 各批次鸡苗单价(元/只)
					double price_chick_index1 = 0;
					double price_chick_index2 = 0;
					double price_chick_index3 = 0;
					double price_chick_index4 = 0;
					double price_chick_index5 = 0;
					// 各批次饲料单价(元/公斤)
					double price_feed_index1 = 0;
					double price_feed_index2 = 0;
					double price_feed_index3 = 0;
					double price_feed_index4 = 0;
					double price_feed_index5 = 0;
					
					// 各批次饲料总金额合计
					double money_feed_sum_index1 = 0 ;
					double money_feed_sum_index2 = 0 ;
					double money_feed_sum_index3 = 0 ;
					double money_feed_sum_index4 = 0 ;
					double money_feed_sum_index5 = 0 ;
					// 各批次饲料重量合计
					double weight_feed_sum_index1 = 0 ;
					double weight_feed_sum_index2 = 0 ;
					double weight_feed_sum_index3 = 0 ;
					double weight_feed_sum_index4 = 0 ;
					double weight_feed_sum_index5 = 0 ;
					
					// 再计算其它指标
					for(HashMap<String,Object> tRow:tDetail){
						String feeCode = (String)tRow.get("fee_code");
						String batch_code = (String)tRow.get("batch_code");
						if(batch_code.equals(tBatchCodeJson.opt("index1"))){
							if(feeCode.equals("4001")){
								// 毛鸡结算
								price_index1 = ((BigDecimal)tRow.get("price_pu")).doubleValue();
								tChickenFirmJson.put("index1", tRow.get("company_name"));
							}else if(feeCode.equals("1001")){
								// 鸡苗结算
								price_chick_index1 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								tChickFirmJson.put("index1", tRow.get("company_name"));
							}else if(feeCode.equals("2001") || feeCode.equals("2002") || feeCode.equals("2003")){
								// 饲料结算
								money_feed_sum_index1 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								weight_feed_sum_index1 += ((BigDecimal)tRow.get("weight")).doubleValue() ;
								tFeedFirmJson.put("index1", tRow.get("company_name"));
							}
						}else if(batch_code.equals(tBatchCodeJson.opt("index2"))){
							if(feeCode.equals("4001")){
								// 毛鸡结算
								price_index2 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								tChickenFirmJson.put("index2", tRow.get("company_name"));
							}else if(feeCode.equals("1001")){
								// 鸡苗结算
								price_chick_index2 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								tChickFirmJson.put("index2", tRow.get("company_name"));
							}else if(feeCode.equals("2001") || feeCode.equals("2002") || feeCode.equals("2003")){
								// 饲料结算
								money_feed_sum_index2 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								weight_feed_sum_index2 += ((BigDecimal)tRow.get("weight")).doubleValue() ;
								tFeedFirmJson.put("index2", tRow.get("company_name"));
							}
						}else if(batch_code.equals(tBatchCodeJson.opt("index3"))){
							if(feeCode.equals("4001")){
								// 毛鸡结算
								price_index3 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								tChickenFirmJson.put("index3", tRow.get("company_name"));
							}else if(feeCode.equals("1001")){
								// 鸡苗结算
								price_chick_index3 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								tChickFirmJson.put("index3", tRow.get("company_name"));
							}else if(feeCode.equals("2001") || feeCode.equals("2002") || feeCode.equals("2003")){
								// 饲料结算
								money_feed_sum_index3 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								weight_feed_sum_index3 += ((BigDecimal)tRow.get("weight")).doubleValue() ;
								tFeedFirmJson.put("index3", tRow.get("company_name"));
							}
						}else if(batch_code.equals(tBatchCodeJson.opt("index4"))){
							if(feeCode.equals("4001")){
								// 毛鸡结算
								price_index4 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								tChickenFirmJson.put("index4", tRow.get("company_name"));
							}else if(feeCode.equals("1001")){
								// 鸡苗结算
								price_chick_index4 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								tChickFirmJson.put("index4", tRow.get("company_name"));
							}else if(feeCode.equals("2001") || feeCode.equals("2002") || feeCode.equals("2003")){
								// 饲料结算
								money_feed_sum_index4 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								weight_feed_sum_index4 += ((BigDecimal)tRow.get("weight")).doubleValue() ;
								tFeedFirmJson.put("index4", tRow.get("company_name"));
							}
						}else if(batch_code.equals(tBatchCodeJson.opt("index5"))){
							if(feeCode.equals("4001")){
								// 毛鸡结算
								price_index5 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								tChickenFirmJson.put("index5", tRow.get("company_name"));
							}else if(feeCode.equals("1001")){
								// 鸡苗结算
								price_chick_index5 = ((BigDecimal)tRow.get("price_pu")).doubleValue() ;
								tChickFirmJson.put("index5", tRow.get("company_name"));
							}else if(feeCode.equals("2001") || feeCode.equals("2002") || feeCode.equals("2003")){
								// 饲料结算
								money_feed_sum_index5 += ((BigDecimal)tRow.get("money_sum")).doubleValue() ;
								weight_feed_sum_index5 += ((BigDecimal)tRow.get("weight")).doubleValue() ;
								tFeedFirmJson.put("index5", tRow.get("company_name"));
							}
						}
					}
					if(m < 6){
						for(int i = 1; i <= 6-m; i++){
							tBatchCodeJson.put("index"+(m-1+i), "-");
							tChickenFirmJson.put("index"+(m-1+i), "-");
							tChickFirmJson.put("index"+(m-1+i), "-");
							tFeedFirmJson.put("index"+(m-1+i), "-");
						}
					}
					tDealPriceArray.put(tBatchCodeJson);
					
					tChickenPriceJson.put("index1", m >= 2? PubFun.formatDoubleNum(price_index1,2): "-");
					tChickenPriceJson.put("index2", m >= 3? PubFun.formatDoubleNum(price_index2,2): "-");
					tChickenPriceJson.put("index3", m >= 4? PubFun.formatDoubleNum(price_index3,2): "-");
					tChickenPriceJson.put("index4", m >= 5? PubFun.formatDoubleNum(price_index4,2): "-");
					tChickenPriceJson.put("index5", m >= 6? PubFun.formatDoubleNum(price_index5,2): "-");
					
					tChickPriceJson.put("index1", m >= 2? PubFun.formatDoubleNum(price_chick_index1,2): "-");
					tChickPriceJson.put("index2", m >= 3? PubFun.formatDoubleNum(price_chick_index2,2): "-");
					tChickPriceJson.put("index3", m >= 4? PubFun.formatDoubleNum(price_chick_index3,2): "-");
					tChickPriceJson.put("index4", m >= 5? PubFun.formatDoubleNum(price_chick_index4,2): "-");
					tChickPriceJson.put("index5", m >= 6? PubFun.formatDoubleNum(price_chick_index5,2): "-");
					
					price_feed_index1 = money_feed_sum_index1/weight_feed_sum_index1 ;
					price_feed_index2 = money_feed_sum_index2/weight_feed_sum_index2 ;
					price_feed_index3 = money_feed_sum_index3/weight_feed_sum_index3 ;
					price_feed_index4 = money_feed_sum_index4/weight_feed_sum_index4 ;
					price_feed_index5 = money_feed_sum_index5/weight_feed_sum_index5 ;
					
					tFeedPriceJson.put("index1", m >= 2? PubFun.formatDoubleNum(price_feed_index1,2): "-");
					tFeedPriceJson.put("index2", m >= 3? PubFun.formatDoubleNum(price_feed_index2,2): "-");
					tFeedPriceJson.put("index3", m >= 4? PubFun.formatDoubleNum(price_feed_index3,2): "-");
					tFeedPriceJson.put("index4", m >= 5? PubFun.formatDoubleNum(price_feed_index4,2): "-");
					tFeedPriceJson.put("index5", m >= 6? PubFun.formatDoubleNum(price_feed_index5,2): "-");
					
					tDealPriceArray.put(tChickenFirmJson);
					tDealPriceArray.put(tChickenPriceJson);
					tDealPriceArray.put(tChickFirmJson);
					tDealPriceArray.put(tChickPriceJson);
					tDealPriceArray.put(tFeedFirmJson);
					tDealPriceArray.put(tFeedPriceJson);
					
					resJson.put("DealPrice", tDealPriceArray);
					
					resJson.put("Result", "Success");
					dealRes = Constants.RESULT_SUCCESS ;
				}
				/** 业务处理结束 **/
			} catch (Exception e) {
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
			}
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing BreedBatchReqController.batchSettleComparation");
	}
	
	private double getDoubleValue(BigDecimal tBigDecimal){
		if(tBigDecimal == null){
			return 0;
		}else{
			return tBigDecimal.doubleValue();
		}
		
	}
	
	/**
	 * ((体重x成活率)/(料肉比x出栏日龄))
	 * @param chicken_weight   毛鸡重量
	 * @param chicken_amount   毛鸡数量
	 * @param chick_amount     鸡苗数量
	 * @param feed_weight      饲料重量
	 * @param feedAges         饲养天数
	 * @return
	 */
	public static String getEuropIndexValue(double chicken_weight,int chicken_amount,int chick_amount,double feed_weight,int feedAges){
		String res = "";
		if(chicken_amount == 0 || chick_amount == 0 || chicken_weight == 0 || feed_weight == 0 || feedAges <= 0){
			res = "-";
		}else{
			BigDecimal chicken_weight_B = new BigDecimal(chicken_weight);
//			BigDecimal chicken_amount_B = new BigDecimal(chicken_amount);
			BigDecimal chick_amount_B = new BigDecimal(chick_amount);
			BigDecimal feed_weight_B = new BigDecimal(feed_weight);
			BigDecimal feedAges_B = new BigDecimal(feedAges);
			
			BigDecimal resFenZi_B = chicken_weight_B.divide(chick_amount_B,5,BigDecimal.ROUND_HALF_UP);
			BigDecimal resFenMu_B = feed_weight_B.divide(chicken_weight_B,5,BigDecimal.ROUND_HALF_UP);
			resFenMu_B = resFenMu_B.multiply(feedAges_B);
			BigDecimal resFinall = resFenZi_B.multiply(new BigDecimal(10000)).divide(resFenMu_B,0,BigDecimal.ROUND_HALF_UP);
			
			res = resFinall.toString();
		}
		return res;
	}
}