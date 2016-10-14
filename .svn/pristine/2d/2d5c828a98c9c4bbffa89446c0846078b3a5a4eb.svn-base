/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.mtc.app.biz.LayerBreedBatchReqManager;
import com.mtc.app.biz.LayerBreedSettleReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.MySQLSPService;
import com.mtc.app.service.SBDeviHouseService;
import com.mtc.app.service.SBLayerFarmSettleService;
import com.mtc.app.service.SDHouseService;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBLayerFarmSettle;

/**
 * @ClassName: HouseReqController
 * @Description:
 * @Date 2015年11月24日 下午3:46:30
 * @Author Yin Guo Xiang
 * 
 */
@Controller
@RequestMapping("layer_breedSettle")
public class LayerBreedSettleReqController {

	private static Logger mLogger = Logger.getLogger(LayerBreedSettleReqController.class);
	@Autowired
	private LayerBreedBatchReqManager tLayerBreedBatchReqManager;
	@Autowired
	private SDHouseService mSDHouseService;
	@Autowired
	private SBDeviHouseService tSBDeviHouseService;
	@Autowired
	private BaseQueryService tBaseQueryService;
	@Autowired
	private MySQLSPService tMySQLSPService;
	@Autowired
	private LayerBreedSettleReqManager tLayerBreedSettleReqManager;
	@Autowired
	private  SBLayerFarmSettleService tSBLayerFarmSettleService;
	@Autowired
	private SDUserOperationService sSDUserOperationService;
	@RequestMapping("/monthSettleSave")
	public void monthSettleSave(HttpServletRequest request,
								HttpServletResponse response) {
		mLogger.info("=====Now start executing LayerBreedSettleReqController.monthSettleSave");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			int userId = jsonObject.optInt("id_spa");
			sSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_COST, SDUserOperationService.OPERATION_ADD, userId);
			mLogger.info("======月核算操作信息----增加，导入完毕");
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			List<SBLayerFarmSettle> listSBLayerFarmSettle = new ArrayList<SBLayerFarmSettle>();
			Date tdate = new Date();
			SBLayerFarmSettle tSBLayerFarmSettle = null;
//			int userId = jsonObject.optInt("id_spa");
			JSONObject tHouseJson = jsonObject.optJSONObject("params");
			int BreedBatchId = tHouseJson.optInt("BreedBatchId");
			int FarmId = tHouseJson.optInt("FarmId");
			String SettleMonth = tHouseJson.optString("SettleMonth");
			JSONObject OutputMsg =  tHouseJson.optJSONObject("OutputMsg");
			String   GoodSales =  OutputMsg.optString("GoodSales");
			String   GoodSalesWeight =  OutputMsg.optString("GoodSalesWeight");
		    tSBLayerFarmSettle = new SBLayerFarmSettle();
		    tSBLayerFarmSettle.setFarmId(FarmId);
		    tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
		    tSBLayerFarmSettle.setSettleMonth(SettleMonth);
		    tSBLayerFarmSettle.setWeight(new BigDecimal(GoodSalesWeight));
		    tSBLayerFarmSettle.setFeeType("I");
		    tSBLayerFarmSettle.setFeeCode("4004");
		    tSBLayerFarmSettle.setFeeName("合格蛋销售");
		    tSBLayerFarmSettle.setMoneySum(new BigDecimal(GoodSales));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
			String   BrokenSales =  OutputMsg.optString("BrokenSales");
			String   BrokenSalesWeight =  OutputMsg.optString("BrokenSalesWeight");
			tSBLayerFarmSettle = new SBLayerFarmSettle();
		    tSBLayerFarmSettle.setFarmId(FarmId);
		    tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
		    tSBLayerFarmSettle.setSettleMonth(SettleMonth);
		    tSBLayerFarmSettle.setWeight(new BigDecimal(BrokenSalesWeight));
		    tSBLayerFarmSettle.setFeeType("I");
		    tSBLayerFarmSettle.setFeeCode("4005");
		    tSBLayerFarmSettle.setFeeName("破损蛋销售");
		    tSBLayerFarmSettle.setMoneySum(new BigDecimal(BrokenSales));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
			String   CDChickenSales =  OutputMsg.optString("CDChickenSales");
			tSBLayerFarmSettle = new SBLayerFarmSettle();
		    tSBLayerFarmSettle.setFarmId(FarmId);
		    tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
		    tSBLayerFarmSettle.setSettleMonth(SettleMonth);
		    tSBLayerFarmSettle.setFeeType("I");
		    tSBLayerFarmSettle.setFeeCode("4006");
		    tSBLayerFarmSettle.setFeeName("淘汰鸡销售");
		    tSBLayerFarmSettle.setMoneySum(new BigDecimal(CDChickenSales));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
		    String   ChickenManure =  OutputMsg.optString("ChickenManure");
		    tSBLayerFarmSettle = new SBLayerFarmSettle();
		    tSBLayerFarmSettle.setFarmId(FarmId);
		    tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
		    tSBLayerFarmSettle.setSettleMonth(SettleMonth);
		    tSBLayerFarmSettle.setFeeType("I");
		    tSBLayerFarmSettle.setFeeCode("4002");
		    tSBLayerFarmSettle.setFeeName("鸡粪收入;");
		    tSBLayerFarmSettle.setMoneySum(new BigDecimal(ChickenManure));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
		    String   OtherSales =  OutputMsg.optString("OtherSales");
		    tSBLayerFarmSettle = new SBLayerFarmSettle();
		    tSBLayerFarmSettle.setFarmId(FarmId);
		    tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
		    tSBLayerFarmSettle.setSettleMonth(SettleMonth);
		    tSBLayerFarmSettle.setFeeType("I");
		    tSBLayerFarmSettle.setFeeCode("4003");
		    tSBLayerFarmSettle.setFeeName("其他收入;");
		    tSBLayerFarmSettle.setMoneySum(new BigDecimal(OtherSales));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
			JSONObject nFeedMsg =  tHouseJson.optJSONObject("FeedMsg");
			JSONArray tFeedMsg =  nFeedMsg.optJSONArray("FeedInfo");
			for (int i = 0; i < tFeedMsg.length(); i++) {
				JSONObject	FeedMsg = tFeedMsg.optJSONObject(i);  
				String FeedCode=FeedMsg.optString("FeedCode");
				String FeedName=FeedMsg.optString("FeedName");
				String Weight=FeedMsg.optString("Weight");
				String Price_p=FeedMsg.optString("Price_p");
				String Price_sum=FeedMsg.optString("Price_sum");
				if(FeedCode.substring(0, 1).equals("2")){
					tSBLayerFarmSettle = new SBLayerFarmSettle();
					tSBLayerFarmSettle.setFarmId(FarmId);
					tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
					tSBLayerFarmSettle.setSettleMonth(SettleMonth);
					tSBLayerFarmSettle.setFeeType("E");
					tSBLayerFarmSettle.setFeeCode(FeedCode);
					tSBLayerFarmSettle.setFeeName(FeedName);
					tSBLayerFarmSettle.setMoneySum(new BigDecimal(Price_sum));
				    tSBLayerFarmSettle.setPricePu(new BigDecimal(Price_p));
				    tSBLayerFarmSettle.setWeight(new BigDecimal(Weight));
				    tSBLayerFarmSettle.setModifyDate(tdate);
				    tSBLayerFarmSettle.setModifyPerson(userId);
				    tSBLayerFarmSettle.setModifyTime(tdate);
				    tSBLayerFarmSettle.setCreateDate(tdate);
				    tSBLayerFarmSettle.setCreatePerson(userId);
				    tSBLayerFarmSettle.setCreateTime(tdate);
				    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
				}
			}
			JSONObject OtherMsg =  tHouseJson.optJSONObject("OtherMsg");
			String  PackingFee=OtherMsg.optString("PackingFee");
			tSBLayerFarmSettle = new SBLayerFarmSettle();
		    tSBLayerFarmSettle.setFarmId(FarmId);
		    tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
		    tSBLayerFarmSettle.setSettleMonth(SettleMonth);
		    tSBLayerFarmSettle.setFeeType("E");
		    tSBLayerFarmSettle.setFeeCode("3009");
		    tSBLayerFarmSettle.setFeeName("包装费用");
		    tSBLayerFarmSettle.setMoneySum(new BigDecimal(PackingFee));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
			String VaccineFee=OtherMsg.optString("VaccineFee");
			tSBLayerFarmSettle = new SBLayerFarmSettle();
		    tSBLayerFarmSettle.setFarmId(FarmId);
		    tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
		    tSBLayerFarmSettle.setSettleMonth(SettleMonth);
		    tSBLayerFarmSettle.setFeeType("E");
		    tSBLayerFarmSettle.setFeeCode("3001");
		    tSBLayerFarmSettle.setFeeName("药品疫苗");
		    tSBLayerFarmSettle.setMoneySum(new BigDecimal(VaccineFee));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
			String ManualFee=OtherMsg.optString("ManualFee");
			tSBLayerFarmSettle = new SBLayerFarmSettle();
		    tSBLayerFarmSettle.setFarmId(FarmId);
		    tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
		    tSBLayerFarmSettle.setSettleMonth(SettleMonth);
		    tSBLayerFarmSettle.setFeeType("E");
		    tSBLayerFarmSettle.setFeeCode("3003");
		    tSBLayerFarmSettle.setFeeName("人工费");
		    tSBLayerFarmSettle.setMoneySum(new BigDecimal(ManualFee));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
			String FuelFee=OtherMsg.optString("FuelFee");
			tSBLayerFarmSettle = new SBLayerFarmSettle();
		    tSBLayerFarmSettle.setFarmId(FarmId);
		    tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
		    tSBLayerFarmSettle.setSettleMonth(SettleMonth);
		    tSBLayerFarmSettle.setFeeType("E");
		    tSBLayerFarmSettle.setFeeCode("3002");
		    tSBLayerFarmSettle.setFeeName("燃料费");
		    tSBLayerFarmSettle.setMoneySum(new BigDecimal(FuelFee));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
			String UtilityFee=OtherMsg.optString("UtilityFee");
			tSBLayerFarmSettle = new SBLayerFarmSettle();
		    tSBLayerFarmSettle.setFarmId(FarmId);
		    tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
		    tSBLayerFarmSettle.setSettleMonth(SettleMonth);
		    tSBLayerFarmSettle.setFeeType("E");
		    tSBLayerFarmSettle.setFeeCode("3006");
		    tSBLayerFarmSettle.setFeeName("水电费");
		    tSBLayerFarmSettle.setMoneySum(new BigDecimal(UtilityFee));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
			String LossFee=OtherMsg.optString("LossFee");
			tSBLayerFarmSettle = new SBLayerFarmSettle();
		    tSBLayerFarmSettle.setFarmId(FarmId);
		    tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
		    tSBLayerFarmSettle.setSettleMonth(SettleMonth);
		    tSBLayerFarmSettle.setFeeType("E");
		    tSBLayerFarmSettle.setFeeCode("3005");
		    tSBLayerFarmSettle.setFeeName("折旧费用");
		    tSBLayerFarmSettle.setMoneySum(new BigDecimal(LossFee));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    listSBLayerFarmSettle.add(tSBLayerFarmSettle);
			String OtherFee=OtherMsg.optString("OtherFee");
			tSBLayerFarmSettle = new SBLayerFarmSettle();
			tSBLayerFarmSettle.setFarmId(FarmId);
			tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
			tSBLayerFarmSettle.setSettleMonth(SettleMonth);
			tSBLayerFarmSettle.setFeeType("E");
			tSBLayerFarmSettle.setFeeCode("3004");
			tSBLayerFarmSettle.setFeeName("其他费用");
			tSBLayerFarmSettle.setMoneySum(new BigDecimal(OtherFee));
			tSBLayerFarmSettle.setModifyDate(tdate);
			tSBLayerFarmSettle.setModifyPerson(userId);
			tSBLayerFarmSettle.setModifyTime(tdate);
			tSBLayerFarmSettle.setCreateDate(tdate);
			tSBLayerFarmSettle.setCreatePerson(userId);
			tSBLayerFarmSettle.setCreateTime(tdate);
			listSBLayerFarmSettle.add(tSBLayerFarmSettle);
			HashMap<String, Object> tt = new HashMap<String, Object>();
			tt.put("listSBLayerFarmSettle", listSBLayerFarmSettle);
			tLayerBreedSettleReqManager.monthSettleSave(tt);
			resJson.put("Result", "Success");
			resJson.put("FarmId", FarmId);
			resJson.put("BreedBatchId", BreedBatchId);
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception",  "数据错误");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL;
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now start executing LayerBreedSettleReqController.monthSettleSave");
	}

	@RequestMapping("/monthSettleQuery")
	public void monthSettleQuery(HttpServletRequest request,
		HttpServletResponse response) {
		mLogger.info("=====Now start executing LayerBreedSettleReqController.monthSettleQuery");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			int userId = jsonObject.optInt("id_spa");
			sSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_COST, SDUserOperationService.OPERATION_SELECT, userId);
			mLogger.info("========月核算报告操作信息--查询，导入完毕");
			mLogger.info("jsonObject=" + jsonObject.toString());
			// ** 业务处理开始，查询、增加、修改、或删除 **//
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int BreedBatchId = tHouseJson.optInt("BreedBatchId");
			String SettleMonth = tHouseJson.optString("SettleMonth");
			List<SBLayerFarmSettle> listSBLayerFarmSettle  = tSBLayerFarmSettleService.selectByfarmBreedIdAndsettleMonth(BreedBatchId, SettleMonth);
			JSONObject OutputMsg =new JSONObject();
			JSONObject FeedMsg =new JSONObject();
			JSONObject OtherMsg =new JSONObject();
			JSONArray FeedInfo = new JSONArray();
			OutputMsg.put("GoodSales",0);
       	    OutputMsg.put("GoodSalesWeight",0 );
       	    OutputMsg.put("BrokenSales",0);
       	    OutputMsg.put("BrokenSalesWeight",0 );
	        OutputMsg.put("CDChickenSales",0);
		    OutputMsg.put("ChickenManure", 0);
		    OutputMsg.put("OtherSales", 0);
		    OtherMsg.put("PackingFee", 0);
		    OtherMsg.put("VaccineFee", 0);
		    OtherMsg.put("ManualFee", 0);
		    OtherMsg.put("FuelFee", 0);
		    OtherMsg.put("UtilityFee", 0);
		    OtherMsg.put("ChickenManure",0);
		    OtherMsg.put("LossFee", 0);
		    OtherMsg.put("OtherFee", 0);
	        String SQL ="SELECT IFNULL( SUM(good_sale_money) ,0) AS  GoodSales ,IFNULL( SUM(good_sale_weight) ,0) AS GoodSalesWeight,"
	        		+ "IFNULL(SUM(broken_sale_weight),0) AS  BrokenSalesWeight, IFNULL(SUM(broken_sale_money),0) AS BrokenSales  FROM  s_b_egg_sells "
	        		+" WHERE  farm_breed_id = "+BreedBatchId+" AND  DATE_FORMAT(sell_date,'%Y%m') = "+SettleMonth ;
	    	mLogger.info("=======LayerBreedSettleReqController.monthSettleQuery.SQL" + SQL);
	        List<HashMap<String,Object>> HashMap= tBaseQueryService.selectMapByAny(SQL);
	        if(HashMap.size()!=0){
	        	 OutputMsg.put("GoodSales",HashMap.get(0).get("GoodSales"));
	        	 OutputMsg.put("GoodSalesWeight",HashMap.get(0).get("GoodSalesWeight") );
            	 OutputMsg.put("BrokenSales", HashMap.get(0).get("BrokenSales"));
            	 OutputMsg.put("BrokenSalesWeight",HashMap.get(0).get("BrokenSalesWeight") );
	        }
			if(listSBLayerFarmSettle.size()!=0){
				for (SBLayerFarmSettle sbLayerFarmSettle : listSBLayerFarmSettle) {
				   if(sbLayerFarmSettle.getFeeCode().equals("4006")){
					   OutputMsg.put("CDChickenSales", sbLayerFarmSettle.getMoneySum());
				   }
				   if(sbLayerFarmSettle.getFeeCode().equals("4002")){
					   OutputMsg.put("ChickenManure", sbLayerFarmSettle.getMoneySum());
				   }
				   if(sbLayerFarmSettle.getFeeCode().equals("4003")){
					   OutputMsg.put("OtherSales", sbLayerFarmSettle.getMoneySum());
				   }
				   if(sbLayerFarmSettle.getFeeCode().substring(0,1).equals("2")){
					   JSONObject  tJSONObject = new JSONObject();
					   tJSONObject.put("FeedCode", sbLayerFarmSettle.getFeeCode());
					   tJSONObject.put("FeedName", sbLayerFarmSettle.getFeeName());
					   tJSONObject.put("Weight", sbLayerFarmSettle.getWeight());
					   tJSONObject.put("Price_p", sbLayerFarmSettle.getPricePu());
					   tJSONObject.put("Price_sum", sbLayerFarmSettle.getMoneySum());
					   FeedInfo.put(tJSONObject);
				   }
				   if(sbLayerFarmSettle.getFeeCode().equals("3009")){
					   OtherMsg.put("PackingFee", sbLayerFarmSettle.getMoneySum());
				   }
				   if(sbLayerFarmSettle.getFeeCode().equals("3001")){
					   OtherMsg.put("VaccineFee", sbLayerFarmSettle.getMoneySum());
				   }
				   if(sbLayerFarmSettle.getFeeCode().equals("3003")){
					   OtherMsg.put("ManualFee", sbLayerFarmSettle.getMoneySum());
				   }
				   if(sbLayerFarmSettle.getFeeCode().equals("3006")){
					   OtherMsg.put("UtilityFee", sbLayerFarmSettle.getMoneySum());
				   }
				   if(sbLayerFarmSettle.getFeeCode().equals("3002")){
					   OtherMsg.put("FuelFee", sbLayerFarmSettle.getMoneySum());
				   }
				   if(sbLayerFarmSettle.getFeeCode().equals("3005")){
					   OtherMsg.put("LossFee", sbLayerFarmSettle.getMoneySum());
				   }
				   if(sbLayerFarmSettle.getFeeCode().equals("3004")){
					   OtherMsg.put("OtherFee", sbLayerFarmSettle.getMoneySum());
				   }
				}
			} 
			FeedMsg.put("FeedInfo", FeedInfo);
			resJson.put("OutputMsg", OutputMsg);
			resJson.put("FeedMsg", FeedMsg);
			resJson.put("OtherMsg", OtherMsg);
			resJson.put("Result", "Success");
			resJson.put("BreedBatchId", BreedBatchId);
			resJson.put("SettleMonth", SettleMonth);
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception",  "数据错误");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL;
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now end executing LayerBreedSettleReqController.monthSettleQuery");
	}

	@RequestMapping("/pulletSettleSave")
	public void pulletSettleSave(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====Now start executing LayerBreedBatchReqController.pulletSettleQuery");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			// ** 业务处理开始，查询、增加、修改、或删除 **//
			Date tdate = new Date();
			SBLayerFarmSettle tSBLayerFarmSettle = null;
			int userId = jsonObject.optInt("id_spa");
			sSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_PULLET, SDUserOperationService.OPERATION_ADD, userId);
			mLogger.info("=========青年鸡摊销操作信息：增加，保存完毕");
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int BreedBatchId = tHouseJson.optInt("BreedBatchId");
			int FarmId = tHouseJson.optInt("FarmId");
			JSONObject PulletMsg = tHouseJson.optJSONObject("PulletMsg");
			Integer SumOfAmount = PulletMsg.optInt("SumOfAmount");
			String Price = PulletMsg.optString("Price");
			String SumOfMoney = PulletMsg.optString("SumOfMoney");
			String SumLayAmount= PulletMsg.optString("SumLayAmount");
			String CostPerUnit = PulletMsg.optString("CostPerUnit");
			tSBLayerFarmSettle = new SBLayerFarmSettle();
			tSBLayerFarmSettle.setFarmId(FarmId);
			tSBLayerFarmSettle.setFarmBreedId(BreedBatchId);
			tSBLayerFarmSettle.setFeeType("E");
			tSBLayerFarmSettle.setFeeCode("1002");
			tSBLayerFarmSettle.setFeeName("青年鸡入舍");
			tSBLayerFarmSettle.setMoneySum(new BigDecimal(SumOfMoney));
		    tSBLayerFarmSettle.setPricePu(new BigDecimal(Price));
		    tSBLayerFarmSettle.setQuentity(SumOfAmount);
		    tSBLayerFarmSettle.setBakNum1(new BigDecimal(SumLayAmount));
		    tSBLayerFarmSettle.setBakNum2(new BigDecimal(CostPerUnit));
		    tSBLayerFarmSettle.setModifyDate(tdate);
		    tSBLayerFarmSettle.setModifyPerson(userId);
		    tSBLayerFarmSettle.setModifyTime(tdate);
		    tSBLayerFarmSettle.setCreateDate(tdate);
		    tSBLayerFarmSettle.setCreatePerson(userId);
		    tSBLayerFarmSettle.setCreateTime(tdate);
		    HashMap<String, Object> tt = new HashMap<String, Object>();
			tt.put("SBLayerFarmSettle", tSBLayerFarmSettle);
			tLayerBreedSettleReqManager.pulletSettleSave(tt);
			resJson.put("Result", "Success");
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", "数据错误");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL;
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now end executing RepProSUMReqController.pulletSettleSave");
	}
	@RequestMapping("/pulletSettleQuery")
	public void pulletSettleQuery(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====Now start executing LayerBreedBatchReqController.pulletSettleQuery");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			int userId = jsonObject.optInt("id_spa");
			sSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_PULLET, SDUserOperationService.OPERATION_SELECT, userId);
			mLogger.info("=====青年鸡身价摊销操作信息----查询，导入完毕");
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int BreedBatchId = tHouseJson.optInt("BreedBatchId");
			JSONObject PulletMsg =  new JSONObject();
			SBLayerFarmSettle tSBLayerFarmSettle  = tSBLayerFarmSettleService.selectByfarmBreedIdAndFeeCode(BreedBatchId, "1002");
			String SQL =" SELECT   place_num AS   SumOfAmount ,farm_id    FROM   s_b_layer_farm_breed  WHERE  id = "+BreedBatchId;
		    mLogger.info("=========LayerBreedBatchReqController.pulletSettleQuery.SQL = " + SQL);
		    List<HashMap<String,Object>> HashMap= tBaseQueryService.selectMapByAny(SQL);
			PulletMsg.put("SumOfAmount",HashMap.get(0).get("SumOfAmount"));
			resJson.put("FarmId", HashMap.get(0).get("farm_id"));
			 resJson.put("BreedBatchId", BreedBatchId);
			if(tSBLayerFarmSettle!=null){
				Object SumOfMoney =tSBLayerFarmSettle.getMoneySum();
				Object Price = tSBLayerFarmSettle.getPricePu();
			    Object  SumLayAmount = tSBLayerFarmSettle.getBakNum1();
			    Object CostPerUnit = tSBLayerFarmSettle.getBakNum2();
			    PulletMsg.put("SumOfMoney",SumOfMoney );
			    PulletMsg.put("Price", Price);
			    PulletMsg.put("SumLayAmount",SumLayAmount );
			    PulletMsg.put("CostPerUnit",CostPerUnit );
			    resJson.put("PulletMsg", PulletMsg);
			    resJson.put("Result", "Success");
			}else{
			    PulletMsg.put("SumOfMoney",0 );
			    PulletMsg.put("Price", 0);
			    PulletMsg.put("SumLayAmount",0 );
			    PulletMsg.put("CostPerUnit",0 );
			    resJson.put("PulletMsg", PulletMsg);
			    resJson.put("Result", "Success");
			}
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception",  "数据错误");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL;
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now end executing RepProSUMReqController.pulletSettleQuery");
	}

	@RequestMapping("/monthProfitRep")
    public void monthProfitRep(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing LayerBreedSettleReqController.monthProfitRep");
        JSONObject resJson = new JSONObject();
        String dealRes = null;
            try{
                String paraStr = PubFun.getRequestPara(request);
                JSONObject tFarm = new JSONObject(paraStr);
				int userId = tFarm.optInt("id_spa");
				sSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_PROFIT, SDUserOperationService.OPERATION_SELECT, userId);
                mLogger.info("========月盈利操作信息：查询，导入完毕");
				JSONObject tFarmJson = tFarm.getJSONObject("params");
                int breedBatchId = tFarmJson.getInt("BreedBatchId");
                int farmId = tFarmJson.getInt("FarmId");

				Calendar calendar = Calendar.getInstance();
				int curMonth = calendar.get(Calendar.MONTH);
				int curYear = calendar.get(Calendar.YEAR);
				if(curMonth == 0){
					curMonth = 12;
					--curYear;
				}
				String curStrMonth = curYear + "" + ( curMonth<10 ? "0" + curMonth : curMonth);
				int passIntMonth = calendar.get(Calendar.MONTH) - 1;
				int passIntYear = 0;
				if(passIntMonth > 0){
					passIntYear = calendar.get(Calendar.YEAR);
				} else {
					passIntYear = calendar.get(Calendar.YEAR) - 1;
				}
				String passMonth = passIntYear + "" + ( passIntMonth<10 ? "0" + passIntMonth : passIntMonth);
				mLogger.info("当前年:" + curYear + "当前月:" + curMonth + "上年：" + passIntYear + "上月：" + passIntMonth);
				mLogger.info("当前月份：" + curStrMonth + "上个月份：" + passMonth + "farmId：" + farmId + "breedBatchId:" + breedBatchId);
				String[] rHeaders = {"销售蛋(公斤)", "合格蛋收入", "破损蛋收入", "其他收入", "收入合计", "身价摊销", "饲料成本",
							"包装费", "药品疫苗", "人工费", "燃料费", "水电费", "折旧租金", "其他费用", "成本合计", "盈利/(亏损)"};

				String[] rHeadersCode = {"4004", "4004", "4005", "4003", "合计1", "合计2", "合计3", "3009", "3001", "3003", "3002", "3006", "3005", "3004", "合计4", "合计5"};

				String SQL = "SELECT 'xsdgj' as fee_code,'销售蛋(公斤)' as fee_Name,ifnull(min(CASE when settle_month = '" + curStrMonth + "' then weight else null end),0) as this_money, " +
						"ifnull(min(CASE when settle_month = '" + passMonth + "' then weight else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code = '4004' " +
						"union ALL " +
						"SELECT fee_code as fee_code,'合格蛋收入' as fee_Name,ifnull(min(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(min(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code = '4004'" +
						"union ALL " +
						"SELECT fee_code as fee_code,'破损蛋收入' as fee_Name,ifnull(min(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(min(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code = '4005'" +
						"union ALL " +
						"SELECT 'null' as fee_code,'其它收入' as fee_Name,ifnull(sum(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(sum(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code in ('4002','4003','4006') " +
						"union ALL " +
						"SELECT 'null' as fee_code,'收入合计' as fee_Name,ifnull(sum(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(sum(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code in ('4004','4005', '4002', '4003', '4006') " +
						"union ALL " +
						"SELECT 'null' as fee_code,'身价摊销' as fee_Name,ifnull(sum(bak_num2),0) as this_money, " +
						"ifnull(sum(bak_num2),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and fee_code = '1002' " +
						"union ALL " +
						"SELECT 'null' as fee_code,'饲料成本' as fee_Name,ifnull(sum(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(sum(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code > '2000' and fee_code < '3000' " +
						"union ALL " +
						"SELECT 'null' as fee_code,'包装费' as fee_Name,ifnull(sum(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(sum(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code = '3009' " +
						"union ALL " +
						"SELECT 'null' as fee_code,'药品疫苗' as fee_Name,ifnull(sum(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(sum(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code = '3001' " +
						"union ALL " +
						"SELECT 'null' as fee_code,'人工费' as fee_Name,ifnull(sum(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(sum(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code = '3003' " +
						"union ALL " +
						"SELECT 'null' as fee_code,'燃料费' as fee_Name,ifnull(sum(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(sum(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code = '3002' " +
						"union ALL " +
						"SELECT 'null' as fee_code,'水电费' as fee_Name,ifnull(sum(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(sum(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code = '3006' " +
						"union ALL " +
						"SELECT 'null' as fee_code,'折旧租金' as fee_Name,ifnull(sum(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(sum(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'"+
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code = '3005' " +
						"union ALL " +
						"SELECT 'null' as fee_code,'其他费用' as fee_Name,ifnull(sum(CASE when settle_month = '" + curStrMonth + "' then money_sum else null end),0) as this_money, " +
						"ifnull(sum(CASE when settle_month = '" + passMonth + "' then money_sum else null end),0) as last_money " +
						"from s_b_layer_farm_settle where farm_id='" + farmId + "' and farm_breed_id='" + breedBatchId + "'" +
						"and settle_month in ('" + curStrMonth + "','" + passMonth + "') and fee_code = '3004'" +
						"";
				mLogger.info("============LayerBreedSettleReqController.monthProfitRep.sql:" + SQL);
				List<HashMap<String, Object>> curMonthDatas = tBaseQueryService.selectMapByAny(SQL);
				String header = null,settleMonth = null, headerName = null;
				/*销售蛋(公斤)*/
					String passWeightOBJ = curMonthDatas.get(0).get("last_money").toString();
					BigDecimal passWeight = new BigDecimal(passWeightOBJ);
					String curWeightOBJ = curMonthDatas.get(0).get("this_money").toString();
					BigDecimal curWeight = new BigDecimal(curWeightOBJ);
				/*合格蛋收入*/
					String passInmoneyHgdOBJ = curMonthDatas.get(1).get("last_money").toString();
					BigDecimal passInmoneyHgd = new BigDecimal(passInmoneyHgdOBJ);
					String curInmoneyHgdOBJ = curMonthDatas.get(1).get("this_money").toString();
					BigDecimal curInmoneyHgd = new BigDecimal(curInmoneyHgdOBJ);
				/*破损蛋收入*/
					String passInmoneyPsdOBJ = curMonthDatas.get(2).get("last_money").toString();
					BigDecimal passInmoneyPsd = new BigDecimal(passInmoneyPsdOBJ);
					String curInmoneyPsdOBJ = curMonthDatas.get(2).get("this_money").toString();
					BigDecimal curInmoneyPsd = new BigDecimal(curInmoneyPsdOBJ);
				/*其他收入*/
					String passInmoneyOthersOBJ = curMonthDatas.get(3).get("last_money").toString();
					BigDecimal passInmoneyOthers = new BigDecimal(passInmoneyOthersOBJ);
					String curInmoneyOthersOBJ = curMonthDatas.get(3).get("this_money").toString();
					BigDecimal curInmoneyOthers = new BigDecimal(curInmoneyOthersOBJ);
				/*收入合计*/
					String passInmoneySumOBJ = curMonthDatas.get(4).get("last_money").toString();
					BigDecimal passInmoneySum = new BigDecimal(passInmoneySumOBJ);
					String curInmoneySumOBJ = curMonthDatas.get(4).get("this_money").toString();
					BigDecimal curInmoneySum = new BigDecimal(curInmoneySumOBJ);
				/*身价摊销*/
					String passPricePerOBJ = curMonthDatas.get(5).get("last_money").toString();
					BigDecimal passCostPer = new BigDecimal(passPricePerOBJ);
					String curPricePerOBJ = curMonthDatas.get(5).get("this_money").toString();
					BigDecimal curCostPer = new BigDecimal(curPricePerOBJ);
				/*饲料成本*/
					String passCostGoodsOBJ = curMonthDatas.get(6).get("last_money").toString();
					BigDecimal passCostGoods = new BigDecimal(passCostGoodsOBJ);
					String curCostGoodsOBJ = curMonthDatas.get(6).get("this_money").toString();
					BigDecimal curCostGoods = new BigDecimal(curCostGoodsOBJ);
				/*包装费*/
					String passCostWrapOBJ = curMonthDatas.get(7).get("last_money").toString();
					BigDecimal passCostWrap = new BigDecimal(passCostWrapOBJ);
					String curCostWrapOBJ = curMonthDatas.get(7).get("this_money").toString();
					BigDecimal curCostWrap = new BigDecimal(curCostWrapOBJ);
				/*药品疫苗*/
					String passCostVaccOBJ = curMonthDatas.get(8).get("last_money").toString();
					BigDecimal passCostVacc = new BigDecimal(passCostVaccOBJ);
					String curCostVaccOBJ = curMonthDatas.get(8).get("this_money").toString();
					BigDecimal curCostVacc = new BigDecimal(curCostVaccOBJ);
				/*人工费*/
					String passCostPersonOBJ = curMonthDatas.get(9).get("last_money").toString();
					BigDecimal passCostPerson = new BigDecimal(passCostPersonOBJ);
					String curCostPersonOBJ = curMonthDatas.get(9).get("this_money").toString();
					BigDecimal curCostPerson = new BigDecimal(curCostPersonOBJ);
				/*燃料费*/
					String passCostFuelOBJ = curMonthDatas.get(10).get("last_money").toString();
					BigDecimal passCostFuel = new BigDecimal(passCostFuelOBJ);
					String curCostFuelOBJ = curMonthDatas.get(10).get("this_money").toString();
					BigDecimal curCostFuel = new BigDecimal(curCostFuelOBJ);
				/*水电费*/
					String passCostUtilityOBJ = curMonthDatas.get(11).get("last_money").toString();
					BigDecimal passCostUtility = new BigDecimal(passCostUtilityOBJ);
					String curCostUtilityOBJ = curMonthDatas.get(11).get("this_money").toString();
					BigDecimal curCostUtility = new BigDecimal(curCostUtilityOBJ);
				/*折旧租金*/
					String passCostDeprecOBJ = curMonthDatas.get(12).get("last_money").toString();
					BigDecimal passCostDeprec = new BigDecimal(passCostDeprecOBJ);
					String curCostDeprecOBJ = curMonthDatas.get(12).get("this_money").toString();
					BigDecimal curCostDeprec = new BigDecimal(curCostDeprecOBJ);
				/*其他费用*/
					String passCostOthersOBJ = curMonthDatas.get(13).get("last_money").toString();
					BigDecimal passCostOthers = new BigDecimal(passCostOthersOBJ);
					String curCostOthersOBJ = curMonthDatas.get(13).get("this_money").toString();
					BigDecimal curCostOthers = new BigDecimal(curCostOthersOBJ);
				/*成本合计*/
					BigDecimal passCostSum = passCostOthers.add(passCostDeprec.add(passCostUtility.add(passCostFuel.add(passCostPerson.add(passCostVacc.add(passCostWrap.add(passCostGoods.add(passCostPer.multiply(passWeight)))))))));
					BigDecimal curCostSum = curCostOthers.add(curCostDeprec.add(curCostUtility.add(curCostFuel.add(curCostPerson.add(curCostVacc.add(curCostWrap.add(curCostGoods.add(curCostPer.multiply(curWeight)))))))));
				/*盈利/（亏损）*/
					BigDecimal passProfitOrNot = passInmoneySum.subtract(passCostSum);
					BigDecimal curProfitOrNot = curInmoneySum.subtract(curCostSum);


					DecimalFormat myFormat = new DecimalFormat("#");
					DecimalFormat myFormat1 = new DecimalFormat("#.##");
//					DecimalFormat myFormat2 = new DecimalFormat("#.##");

					JSONArray datasArray = new JSONArray();

//					if(!curWeight.equals(0.00) || !passWeight.equals(0.00)){
					try {
						JSONObject datas = new JSONObject();
						datas.put("ItemName", "销售蛋(公斤)");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curWeight));
						datas.put("PricePKilo_this", "-");
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-": myFormat.format(passWeight));
						datas.put("PricePKilo_last", "-");
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "合格蛋收入");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curInmoneyHgd));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curInmoneyHgd.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passInmoneyHgd));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passInmoneyHgd.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "破损蛋收入");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curInmoneyPsd));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curInmoneyPsd.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passInmoneyPsd));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passInmoneyPsd.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "其他收入");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curInmoneyOthers));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curInmoneyOthers.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passInmoneyOthers));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passInmoneyOthers.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "收入合计");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curInmoneySum));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curInmoneySum.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passInmoneySum));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passInmoneySum.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "身价摊销");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curCostPer.multiply(curWeight)));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curCostPer));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passCostPer.multiply(passWeight)));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passCostPer));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "饲料成本");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curCostGoods));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curCostGoods.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passCostGoods));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passCostGoods.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "包装费");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curCostWrap));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curCostWrap.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passCostWrap));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passCostWrap.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "药品疫苗");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curCostVacc));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curCostVacc.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passCostVacc));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passCostVacc.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "人工费");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curCostPerson));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curCostPerson.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passCostPerson));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passCostPerson.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "燃料费");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curCostFuel));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curCostFuel.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passCostFuel));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passCostFuel.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "水电费");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curCostUtility));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curCostUtility.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passCostUtility));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passCostUtility.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "折旧租金");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curCostDeprec));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curCostDeprec.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passCostDeprec));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passCostDeprec.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "其他费用");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curCostOthers));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curCostOthers.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passCostOthers));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passCostOthers.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "成本合计");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curCostSum));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curCostSum.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passCostSum));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passCostSum.divide(passWeight, 2)));
						datasArray.put(datas);

						datas = new JSONObject();
						datas.put("ItemName", "盈利/(亏损)");
						datas.put("PricSum_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(curProfitOrNot));
						datas.put("PricePKilo_this", curWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(curProfitOrNot.divide(curWeight, 2)));
						datas.put("SaleChicken_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat.format(passProfitOrNot));
						datas.put("PricePKilo_last", passWeight.equals(new BigDecimal("0.00")) ? "-" : myFormat1.format(passProfitOrNot.divide(passWeight, 2)));
						datasArray.put(datas);


						resJson.put("Datas", datasArray);
						resJson.put("Result", "Success");
						dealRes = Constants.RESULT_SUCCESS;
					} catch(ArithmeticException ae){
						ae.printStackTrace();
					}
//					}
					/*else {
						for(int i = 0; i < rHeaders.length; ++i) {
							JSONObject datas = new JSONObject();
							datas.put("ItemName", rHeaders[i]);
							datas.put("PricSum_this", "-");
							datas.put("PricePKilo_this", "-");
							datas.put("SaleChicken_last", "-");
							datas.put("PricePKilo_last", "-");
							datasArray.put(datas);
						}
						mLogger.info("datasArray:" + datasArray);
						resJson.put("Result", "Success");
						resJson.put("Datas", datasArray);
						resJson.put("TipMsg", "请先创建批次！");
						dealRes = Constants.RESULT_SUCCESS;
					}*/

            } catch (Exception e) {
                e.printStackTrace();
                try{
                    resJson.put("ErrorMsg", e.getMessage());
                } catch (JSONException jsonE){
                    jsonE.printStackTrace();
                }
                dealRes = Constants.RESULT_FAIL;
            }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now end executing LayerBreedSettleReqController.monthProfitRep");
    }
	@RequestMapping("/accProfitRep")
	public void accProfitRep(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing LayerBreedSettleReqController.accProfitRep");
		JSONObject resJson = new JSONObject();
		SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		String dealRes = null;
	    String paraStr = PubFun.getRequestPara(request);
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		try {
			JSONObject tFarm = new JSONObject(paraStr);
			JSONObject tFarmJson = tFarm.getJSONObject("params");
			int userId = tFarm.optInt("id_spa");
			sSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_PROFIT_TOTAL, SDUserOperationService.OPERATION_SELECT, userId);
			mLogger.info("=====累计盈利操作信息：查询，导入完毕");
			int FarmId = tFarmJson.getInt("FarmId");
			String viewUnit = tFarmJson.getString("ViewUnit");
			String SQL = "SELECT a.settle_month, ifnull(sum(CASE WHEN fee_code='4004' THEN weight ELSE NULL end), 0) AS total_weight, " +
					"ifnull(sum(CASE WHEN fee_code IN ('4002','4003','4004','4005','4006') THEN money_sum ELSE NULL end), 0) AS total_inmoney, " +
					"(select b.bak_num2 from s_b_layer_farm_settle b where b.farm_id = a.farm_id and b.farm_breed_id = a.farm_breed_id " +
					"and b.fee_code = '1002') * ifnull(sum(CASE WHEN fee_code='4004' THEN weight ELSE NULL end), 0) AS social_price, " +
					"ifnull(sum(CASE WHEN fee_code > '2000' AND fee_code < '3000' THEN money_sum ELSE NULL end), 0) AS feed_cost, " +
					"ifnull(sum(CASE WHEN fee_code='3009' THEN money_sum ELSE NULL end), 0) AS wrap_cost, " +
					"ifnull(sum(CASE WHEN fee_code='3001' THEN money_sum ELSE NULL end), 0) AS vacc_cost, " +
					"ifnull(sum(CASE WHEN fee_code='3002' THEN money_sum ELSE NULL end), 0) AS fuel_cost, " +
					"ifnull(sum(CASE WHEN fee_code='3006' THEN money_sum ELSE NULL end), 0) AS utility_cost, " +
					"ifnull(sum(CASE WHEN fee_code='3003' THEN money_sum ELSE NULL end), 0) AS person_cost, " +
					"ifnull(sum(CASE WHEN fee_code='3005' THEN money_sum ELSE NULL end), 0) AS deprec_cost, " +
					"ifnull(sum(CASE WHEN fee_code='3004' THEN money_sum ELSE NULL end), 0) AS others_cost, " +
					"( " +
					"(select b.bak_num2 from s_b_layer_farm_settle b where b.farm_id = a.farm_id and b.farm_breed_id = a.farm_breed_id " +
					"and b.fee_code = '1002') * ifnull(sum(CASE WHEN fee_code='4004' THEN weight ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code > '2000' AND fee_code < '3000' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3009' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3001' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3002' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3006' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3003' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3005' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3004' THEN money_sum ELSE NULL end), 0)" +
					")AS total_cost, " +
					"( " +
					"ifnull(sum(CASE WHEN fee_code IN ('4002','4003','4004','4005','4006') THEN money_sum ELSE NULL end), 0) - ( " +
					"(select b.bak_num2 from s_b_layer_farm_settle b where b.farm_id = a.farm_id and b.farm_breed_id = a.farm_breed_id " +
					"	and b.fee_code = '1002') * ifnull(sum(CASE WHEN fee_code='4004' THEN weight ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code > '2000' AND fee_code < '3000' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3009' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3001' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3002' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3006' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3003' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3005' THEN money_sum ELSE NULL end), 0) + " +
					"ifnull(sum(CASE WHEN fee_code='3004' THEN money_sum ELSE NULL end), 0) )" +
					")AS profit_result " +
					"FROM s_b_layer_farm_settle a " +
					"WHERE a.farm_id = " + FarmId + " and a.settle_month is not null " +
					"GROUP BY a.settle_month";
				mLogger.info("=======LayerBreedSettleReqController.accProfitRep.SQL = " + SQL);
					List<HashMap<String, Object>> accProfitDatas = tBaseQueryService.selectMapByAny(SQL);
					JSONArray jsonArray = new JSONArray();
					for(int i=0; i < accProfitDatas.size(); ++i){
						JSONObject dataObject = new JSONObject();
						String date = accProfitDatas.get(i).get("settle_month").toString();
						BigDecimal weight = new BigDecimal(accProfitDatas.get(i).get("total_weight").toString());
						dataObject.put("YearMonth", date.substring(2, 6));
						dataObject.put("sale_amount", decimalFormat.format(accProfitDatas.get(i).get("total_weight")));
						dataObject.put("sale_money", decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("total_inmoney").toString()).divide(new BigDecimal(10000))));
						dataObject.put("exp_chick", viewUnit.equals("Money") ? decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("social_price").toString()).divide(new BigDecimal(10000))): decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("social_price").toString()).divide(weight, 2)));
						dataObject.put("exp_feed", viewUnit.equals("Money") ? decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("feed_cost").toString()).divide(new BigDecimal(10000))): decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("feed_cost").toString()).divide(weight,2)));
						dataObject.put("exp_packing", viewUnit.equals("Money") ? decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("wrap_cost").toString()).divide(new BigDecimal(10000))): decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("wrap_cost").toString()).divide(weight,2)));
						dataObject.put("exp_vaccine", viewUnit.equals("Money") ? decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("vacc_cost").toString()).divide(new BigDecimal(10000))): decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("vacc_cost").toString()).divide(weight,2)));
						dataObject.put("exp_fuel", viewUnit.equals("Money") ? decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("fuel_cost").toString()).divide(new BigDecimal(10000))): decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("fuel_cost").toString()).divide(weight,2)));
						dataObject.put("exp_utility", viewUnit.equals("Money") ? decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("utility_cost").toString()).divide(new BigDecimal(10000))): decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("utility_cost").toString()).divide(weight,2)));
						dataObject.put("exp_manual", viewUnit.equals("Money") ? decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("person_cost").toString()).divide(new BigDecimal(10000))): decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("person_cost").toString()).divide(weight,2)));
						dataObject.put("exp_lossFee", viewUnit.equals("Money") ? decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("deprec_cost").toString()).divide(new BigDecimal(10000))): decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("deprec_cost").toString()).divide(weight,2)));
						dataObject.put("exp_other", viewUnit.equals("Money") ? decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("others_cost").toString()).divide(new BigDecimal(10000))): decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("others_cost").toString()).divide(weight,2)));
						dataObject.put("exp_sum", decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("total_cost").toString()).divide(new BigDecimal(10000))));
						dataObject.put("profit", decimalFormat.format(new BigDecimal(accProfitDatas.get(i).get("profit_result").toString()).divide(new BigDecimal(10000))));
						jsonArray.put(dataObject);
					}
					BigDecimal saleAmount = new BigDecimal(0);
					BigDecimal saleMoney = new BigDecimal(0);
					BigDecimal expSum = new BigDecimal(0);
					BigDecimal profit = new BigDecimal(0);
					BigDecimal tempSum = new BigDecimal(0);
					String[] headersCode = {"YearMonth", "sale_amount", "sale_money", "exp_chick", "exp_feed", "exp_packing", "exp_vaccine", "exp_fuel", "exp_utility", "exp_manual", "exp_lossFee", "exp_other", "exp_sum", "profit"};
					String[] headersCode1 = {"exp_chick", "exp_feed", "exp_packing", "exp_vaccine", "exp_fuel", "exp_utility", "exp_manual", "exp_lossFee", "exp_other"};
					String[] headersCode2 = {"social_price", "feed_cost", "wrap_cost", "vacc_cost", "fuel_cost", "utility_cost", "person_cost", "deprec_cost", "others_cost"};
					JSONObject dataObject1 = new JSONObject();
					if(viewUnit.equals("Money")) {
						for (int j = 0; j < headersCode.length; ++j) {
							saleAmount = new BigDecimal(0);
							for (int i = 0; i < jsonArray.length(); ++i) {
									saleAmount = saleAmount.add(new BigDecimal(jsonArray.getJSONObject(i).get(headersCode[j]).toString()));
							}
							dataObject1.put(headersCode[j], j == 0 ? "合计" : decimalFormat.format(saleAmount));
						}
						jsonArray.put(dataObject1);
					}else if(viewUnit.equals("weight")){
						for (int i = 0; i < jsonArray.length(); ++i) {
							saleAmount = saleAmount.add(new BigDecimal(jsonArray.getJSONObject(i).get("sale_amount").toString()));
							saleMoney = saleMoney.add(new BigDecimal(jsonArray.getJSONObject(i).get("sale_money").toString()));
							expSum = expSum.add(new BigDecimal(jsonArray.getJSONObject(i).get("exp_sum").toString()));
							profit = profit.add(new BigDecimal(jsonArray.getJSONObject(i).get("profit").toString()));
						}
						for(int u=0; u < headersCode2.length; ++u){
							tempSum = new BigDecimal(0);
							for(int i=0; i < jsonArray.length(); ++i) {
								tempSum = tempSum.add(new BigDecimal(accProfitDatas.get(i).get(headersCode2[u]).toString()));
							}
							dataObject1.put(headersCode1[u], decimalFormat.format(tempSum.divide(saleAmount, 2)));
						}
						dataObject1.put("YearMonth", "合计");
						dataObject1.put("sale_amount", decimalFormat.format(saleAmount));
						dataObject1.put("sale_money", decimalFormat.format(saleMoney));
						dataObject1.put("exp_sum", decimalFormat.format(expSum));
						dataObject1.put("profit", decimalFormat.format(profit));
						jsonArray.put(dataObject1);
					}
				resJson.put("Datas", jsonArray);
				resJson.put("Result", "Success");
				dealRes = Constants.RESULT_SUCCESS;
		} catch(Exception e) {
			e.printStackTrace();
			try {
				dealRes = Constants.RESULT_FAIL;
				resJson.put("ErrorMsg", e.getMessage());
			} catch (JSONException jsonE) {
				jsonE.printStackTrace();
			}
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now end executing LayerBreedSettleReqController.accProfitRep");
	}
}
