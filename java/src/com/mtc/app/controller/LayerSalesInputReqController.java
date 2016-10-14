/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.mtc.common.util.DateUtil;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBEggSells;

/**
 * 
 * 销售日报 处理egg销售信息
 * 
 * select操作均在controller中直接进行
 * 
 * update，insert，delete在manager中进行
 * 
 * @author lx
 * 
 */
@Controller
@RequestMapping("/layer_salesInput")
public class LayerSalesInputReqController {
	private static Logger mLogger = Logger
			.getLogger(LayerSalesInputReqController.class);

	@Autowired
	private EggSellsReqManager eggSellsReqManager;

	@Autowired
	private SDUserOperationService operationService;

	//@Autowired
	//private SLAlidayuTTSService ttsService;
	/**
	 * test
	 */
	@RequestMapping("/test")
	public void test(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(request.getHeader("User-Agent"));
		System.out.println(request.getRemoteAddr());
		System.out.println(request.getRequestURL().toString());
		System.out.println(request.getSession().getId());
		//String paraStr = PubFun.getRequestPara(request);
		//mLogger.info("updateFarm.para=" + paraStr);
		
		/*try {
			String res = ttsService.ttsNumSingleCell(123, "18621017090", "{\"name\":\"Sir\",\"time\":\"2016-05-29\"}");
			System.out.println(res);
			//ttsService.tt();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	
	/**
	 * 获取销售日报记录
	 */
	@RequestMapping("/reportTable")
	public void reportTable(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====Now start executing LayerSalesInputReqController.reportTable");
		String paraStr = PubFun.getRequestPara(request);
		mLogger.info("updateFarm.para=" + paraStr);
		JSONObject jsonobject = null;
		try {
			jsonobject = new JSONObject(paraStr);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		mLogger.info("jsonObject=" + jsonobject.toString());
		/** 业务处理开始，查询、增加、修改、或删除 **/
		JSONObject params = jsonobject.optJSONObject("params");
		int userid = jsonobject.optInt("id_spa");

		operationService.insert(SDUserOperationService.MENU_EGGSELL,SDUserOperationService.OPERATION_SELECT,userid);
		
		int farmId = 0;
		int farmFeedId = 0;
		String selectMonth = "";
		try {
			farmId = params.getInt("FarmId");
			farmFeedId = params.getInt("FarmBreedId");
			selectMonth = params.getString("SelectMonth");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		String dealRes = Constants.RESULT_FAIL;
		JSONObject resJson = new JSONObject();

		mLogger.info("get egg sells data,params:farmId:" + farmId
				+ ",farmFeedId:" + farmFeedId + ", selectMonth:" + selectMonth);

		if (farmFeedId > 0 && farmId > 0) {
			Calendar calendar = Calendar.getInstance();

			Date beginDate = null;
			Date endDate = null;

			java.util.Date date = DateUtil.parser(selectMonth + "-01",
					DateUtil.DATE_FORMAT);
			if (date != null) {
				beginDate = new Date(date.getTime());

				calendar.setTimeInMillis(date.getTime());
				calendar.add(Calendar.MONTH, 1);
				endDate = new Date(calendar.getTimeInMillis());

			} else {
				calendar.set(Calendar.DATE, 1);
				calendar.set(Calendar.HOUR, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.SECOND, 0);
				beginDate = new Date(calendar.getTimeInMillis());

				calendar.add(Calendar.MONTH, 1);
				endDate = new Date(calendar.getTimeInMillis());
			}

			mLogger.info("beginDate:" + beginDate + ",endDate:" + endDate);
			// JSONArray
			JSONArray DCDatas = new JSONArray();// new JSONArray();

			List<SBEggSells> list = eggSellsReqManager.selectEggSellsByMonth(
					farmId, farmFeedId, beginDate, endDate);
			try {
				
				if (list != null && !list.isEmpty()) {

					for (SBEggSells sell : list) {
						JSONObject object = new JSONObject();
						object.put("farmId", sell.getFarmId());
						object.put("farmBreedId", sell.getFarmBreedId());
						object.put("good_box_size", sell.getGoodBoxSize());
						double goodPriceValue = sell.getGoodPriceValue().doubleValue();
						double goodSaleWeight = sell.getGoodSaleWeight().doubleValue();
						double goodSaleBoxNum = sell.getGoodSaleboxNum().doubleValue();
						String goodPriceType = sell.getGoodPriceType();
						
						object.put("good_price_type",goodPriceType);
						
						object.put("good_price_value",goodPriceValue);
						object.put("good_salebox_num",goodSaleBoxNum);
						object.put("good_sale_weight",goodSaleWeight);
						object.put("good_sale_money",
								sell.getGoodSaleMoney());
						// 元/公斤
						//object.put("good_price_kil", good_price_kil);
						object.put("dateBak1", sell.getDateBak1());
						object.put("dateBak2", sell.getDateBak2());
						object.put("dateBak3", sell.getDateBak3());
						object.put("broken_box_size",
								sell.getBrokenBoxSize());
						object.put("broken_price_type",
								sell.getBrokenPriceType());
						object.put("broken_price_value",
								sell.getBrokenPriceValue());
						object.put("broken_salebox_num",
								sell.getBrokenSaleboxNum());
						object.put("broken_sale_weight",
								sell.getBrokenSaleWeight());
						object.put("broken_sale_money",
								sell.getBrokenSaleMoney());
						object.put("chicken_manure",
								sell.getChickenManure());
						object.put("day_age", sell.getDayAge());
						object.put("week_age", sell.getWeekAge());
						
						
						object.put("intBak1", sell.getIntBak1());
						object.put("intBak2", sell.getIntBak2());
						object.put("intBak3", sell.getIntBak3());
						object.put("isHistory", sell.getIsHistory());
						object.put("sellDate", DateUtil.toFormatDateString(
								sell.getSellDate(), DateUtil.DATE_FORMAT));
						object.put("sell_date", DateUtil.toFormatDateString(
								sell.getSellDate(), "MM-dd"));

						object.put("numBak1", sell.getNumBak1());
						object.put("numBak2", sell.getNumBak2());
						object.put("numBak3", sell.getNumBak3());
						object.put("varBak1", sell.getVarBak1());
						object.put("varBak2", sell.getVarBak2());
						object.put("varBak3", sell.getVarBak3());
						

						DCDatas.put(object);

					}

				}

				/*List<HashMap<String, Object>> selectEggSells = eggSellsReqManager
						.selectEggSells(farmId, farmFeedId);
				if (selectEggSells != null && !selectEggSells.isEmpty()) {

					resJson.put("accgood_sale_weight", selectEggSells.get(0)
							.get("good_acc_sell_weight") == null ? "0"
							: selectEggSells.get(0).get("good_acc_sell_weight"));
					resJson.put("accgood_sale_money", selectEggSells.get(0)
							.get("good_acc_sell_money") == null ? "0"
							: selectEggSells.get(0).get("good_acc_sell_money"));
					resJson.put("accbroken_sale_weight", selectEggSells.get(0)
							.get("bad_acc_sell_weight") == null ? "0"
							: selectEggSells.get(0).get("bad_acc_sell_weight"));
					resJson.put("accbroken_sale_money", selectEggSells.get(0)
							.get("bad_acc_sell_money") == null ? "0"
							: selectEggSells.get(0).get("bad_acc_sell_money"));
				}*/
				
				dealRes = Constants.RESULT_SUCCESS;
				
				resJson.put("Result", "Success");
				resJson.put("FarmId", farmId);
				resJson.put("FarmBreedId", farmFeedId);
				resJson.put("SelectMonth", selectMonth);
				resJson.put("saleDetails", DCDatas);

			} catch (JSONException e) {
				e.printStackTrace();
				dealRes = Constants.RESULT_FAIL;
				try {
					resJson.put("Result", "Fail");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			DealSuccOrFail.dealApp(request, response, dealRes, resJson);

		} else {
			try {
				resJson.put("Result", "Fail");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		}
		mLogger.info("=====Now end executing LayerSalesInputReqController.reportTable");
	}

	
	/**
	 * 更新銷售记录
	 */
	/**
	@RequestMapping("/saveDR")
	public void saveDR(HttpServletRequest request,
			HttpServletResponse response){
		String paraStr = PubFun.getRequestPara(request);
		mLogger.info("updateFarm.para=" + paraStr);
		String dealRes = Constants.RESULT_FAIL;
		JSONObject resJson = new JSONObject();

		try {
			
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			JSONObject params = jsonobject.optJSONObject("params");
			int userId = jsonobject.optInt("id_spa");
			int farmId = params.getInt("FarmId");
			int farmFeedId = params.getInt("FarmBreedId");
			
			operationService.insert(SDUserOperationService.MENU_EGGSELL,SDUserOperationService.OPERATION_UPDATE,userId);
			
			
			//String month = params.getString("selectMonth");
			JSONArray dataInput = params.getJSONArray("saleDetails");
			// 需要修改的记录
			List<SBEggSells> list = new ArrayList<>();
			
			//Date beginDate = null;// new Date(System.currentTimeMillis());
			//Date endDate = null;//new Date(0);
			
			//HashMap<Long, SBEggSells> eggMap = new HashMap<>();
			
			for(int i = 0; i < dataInput.length(); i++){
				JSONObject temp = dataInput.getJSONObject(i);
				SBEggSells eggsell = new SBEggSells();
				eggsell.setBadAccSellMoney(new BigDecimal(temp.getDouble("badAccSellMoney")));
				eggsell.setBadAccSellWeight(new BigDecimal(temp.getDouble("badAccSellWeight")));
				eggsell.setBadCurSellMoney(new BigDecimal(temp.getDouble("broken_sale_money")));
				eggsell.setBadCurSellPrice(new BigDecimal(temp.getDouble("broken_sale_price")));
				eggsell.setBadCurSellWeight(new BigDecimal(temp.getDouble("broken_sale_weight")));
				eggsell.setFarmBreedId(farmFeedId);
				eggsell.setFarmId(farmId);
				eggsell.setGoodAccSellMoney(new BigDecimal(temp.getDouble("goodAccSellMoney")));
				eggsell.setGoodAccSellWeight(new BigDecimal(temp.getDouble("goodAccSellWeight")));
				eggsell.setGoodCurSellMoney(new BigDecimal(temp.getDouble("good_sale_money")));
				eggsell.setGoodCurSellPrice(new BigDecimal(temp.getDouble("good_sale_price")));
				eggsell.setGoodCurSellWeight(new BigDecimal(temp.getDouble("good_sale_weight")));
				eggsell.setIsHistory(temp.getString("isHistory"));
				java.util.Date date = DateUtil.parser(temp.getString("sellDate"), DateUtil.DATE_FORMAT);
				eggsell.setSellDate(date);

				/////*eggMap.put(date.getTime(), eggsell);
				if (beginDate == null){
					beginDate = new Date(date.getTime());
				}
				
				if(endDate == null){
					endDate = new Date(date.getTime());
				}
				
				if (beginDate.getTime() > date.getTime()){
					beginDate = new Date(date.getTime());
				}
				
				if (endDate.getTime() < date.getTime()){
					endDate = new Date(date.getTime());
				}////
				list.add(eggsell);
			}
			//endDate = new Date(endDate.getTime() + 24*3600*1000);
			
			Collections.sort(list,new  Comparator<SBEggSells>() {
				@Override
				public int compare(SBEggSells arg0, SBEggSells arg1) {
					 return arg0.getSellDate().compareTo(arg1.getSellDate()); 
				}
			});
			
			
			Date beginDate = new Date(list.get(0).getSellDate().getTime());
			Date endDate = new Date(list.get(list.size() - 1).getSellDate().getTime() + 24*3600*1000);
			
			// 获取修改日期之间的记录
			List<SBEggSells> listDB = eggSellsReqManager.selectEggSellsByMonth(
					farmId, farmFeedId, beginDate, endDate);
			
			double accWeight = 0;
			double accMoney = 0;
			double accBadWeight = 0;
			double accBadMoney = 0;
			
			
			// 数据库中的记录
			for(SBEggSells temp : listDB){
				// 需要修改的记录
				//SBEggSells modify = eggMap.get(temp.getSellDate().getTime());
				for(SBEggSells modify : list){
				//if (modify != null){
					if(temp.getSellDate().getTime() == modify.getSellDate().getTime()){
						temp.setGoodCurSellPrice(modify.getGoodCurSellPrice());
						temp.setBadCurSellPrice(modify.getBadCurSellPrice());
						
						// 数据库中旧的金额
						double goodCurSellMoney = Double.parseDouble(temp.getGoodCurSellMoney().toString());
						// 新的金额
						double goodCurSellMoneyNew = Double.parseDouble(modify.getGoodCurSellMoney().toString());
						// 计算金额增量
						accMoney += goodCurSellMoneyNew - goodCurSellMoney;
						
						temp.setGoodCurSellMoney(modify.getGoodCurSellMoney());
						
						// 数据库中旧的重量
						double goodCurSellWeight = Double.parseDouble(temp.getGoodCurSellWeight().toString());
						// 新的重量
						double goodCurSellWeightNew = Double.parseDouble(modify.getGoodCurSellWeight().toString());
						// 计算重量增量
						accWeight += goodCurSellWeightNew - goodCurSellWeight;
						
						temp.setGoodCurSellWeight(modify.getGoodCurSellWeight());
						
						
						// 数据库中旧的金额
						double badCurSellMoney = Double.parseDouble(temp.getBadCurSellMoney().toString());
						// 新的金额
						double badCurSellMoneyNew = Double.parseDouble(modify.getBadCurSellMoney().toString());
						// 计算金额增量
						accBadMoney += badCurSellMoneyNew - badCurSellMoney;
						
						temp.setBadCurSellMoney(modify.getBadCurSellMoney());
						
						// 数据库中旧的重量
						double badCurSellWeight = Double.parseDouble(temp.getBadCurSellWeight().toString());
						// 新的重量
						double badCurSellWeightNew = Double.parseDouble(modify.getBadCurSellWeight().toString());
						// 计算重量增量
						accBadWeight += badCurSellWeightNew - badCurSellWeight;
						
						temp.setBadCurSellWeight(modify.getBadCurSellWeight());
						
						break;
					}
				}
				
				double goodAccSellMoney = Double.parseDouble(temp.getGoodAccSellMoney().toString());
				double goodAccSellWeight = Double.parseDouble(temp.getGoodAccSellWeight().toString());
				double badAccSellMoney = Double.parseDouble(temp.getBadAccSellMoney().toString());
				double badAccSellWeight = Double.parseDouble(temp.getBadAccSellWeight().toString());

				
				temp.setModifyPerson(userId);
				Date modifyDate = new Date(System.currentTimeMillis());
				temp.setModifyDate(modifyDate);
				temp.setModifyTime(modifyDate);
				temp.setBadAccSellMoney(new BigDecimal(badAccSellMoney + accBadMoney));
				temp.setBadAccSellWeight(new BigDecimal(badAccSellWeight + accBadWeight));
				
				temp.setGoodAccSellMoney(new BigDecimal(goodAccSellMoney + accMoney));
				temp.setGoodAccSellWeight(new BigDecimal(goodAccSellWeight + accWeight));
				
			}
			
			
			int res = eggSellsReqManager.updateSellInfoByBatch(listDB);
			mLogger.info("update sell info success,update rows count:" + res);
			
			SBEggSells sell = new SBEggSells();
			Date modifyDate = new Date(System.currentTimeMillis());

			sell.setGoodAccSellMoney(new BigDecimal(accMoney));
			sell.setGoodAccSellWeight(new BigDecimal(accWeight));
			sell.setBadAccSellMoney(new BigDecimal(accBadMoney));
			sell.setBadAccSellWeight(new BigDecimal(accBadWeight));
			sell.setModifyPerson(userId);
			sell.setModifyDate(modifyDate);
			sell.setModifyTime(modifyDate);
			sell.setSellDate(endDate);
			sell.setFarmBreedId(farmFeedId);
			sell.setFarmId(farmId);
			
			res = eggSellsReqManager.updateSellInfoByDate(sell);
			mLogger.info("update sell info by date success,update rows count:" + res);
			
			dealRes = Constants.RESULT_SUCCESS;
			resJson.put("FarmId", farmId);
			resJson.put("FarmBreedId", farmFeedId);
			resJson.put("Result", "Success");
			DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		} catch (JSONException e1) {
			e1.printStackTrace();
			try {
				resJson.put("Result", "Fail");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		}
		
	}
	*/
	
	/**
	 * 获取某一天的销售日报
	 */
	@RequestMapping("/queryDRByDate")
	public void queryDRByDate(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====Now start executing LayerSalesInputReqController.queryDRByDate");
		String paraStr = PubFun.getRequestPara(request);
		mLogger.info("updateFarm.para=" + paraStr);
		JSONObject jsonobject = null;
		try {
			jsonobject = new JSONObject(paraStr);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		mLogger.info("jsonObject=" + jsonobject.toString());
		/** 业务处理开始，查询、增加、修改、或删除 **/
		JSONObject params = jsonobject.optJSONObject("params");
		int userid = jsonobject.optInt("id_spa");

		operationService.insert(SDUserOperationService.MENU_EGGSELL,SDUserOperationService.OPERATION_SELECT,userid);
		
		int farmId = 0;
		int farmFeedId = 0;
		String selectDate = "";
		//int goodSaveType = 0;
		//int brokenSaveType = 0;
		try {
			farmId = params.getInt("FarmId");
			farmFeedId = params.getInt("FarmBreedId");
			selectDate = params.getString("SelectDate");
			//goodSaveType = params.getInt("FarmBreedId");
			//brokenSaveType = params.getInt("FarmBreedId");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		String dealRes = Constants.RESULT_FAIL;
		JSONObject resJson = new JSONObject();

		mLogger.info("get egg sells data,params:farmId:" + farmId
				+ ",farmFeedId:" + farmFeedId + ", selectDate:" + selectDate);

		if (farmFeedId > 0 && farmId > 0) {
			Calendar calendar = Calendar.getInstance();

			Date beginDate = null;
			Date endDate = null;

			java.util.Date date = DateUtil.parser(selectDate,
					DateUtil.DATE_FORMAT);
			if (date == null) {
				date = DateUtil.parser(DateUtil.toDateString(new Date(System.currentTimeMillis())),
						DateUtil.DATE_FORMAT);
			}
			
			beginDate = new Date(date.getTime());

			calendar.setTimeInMillis(date.getTime());
			calendar.add(Calendar.DATE, 1);
			endDate = new Date(calendar.getTimeInMillis());
			

			mLogger.info("beginDate:" + beginDate + ",endDate:" + endDate);
			// JSONArray
			JSONArray DCDatas = new JSONArray();// new JSONArray();

			List<SBEggSells> list = eggSellsReqManager.selectEggSellsByMonth(
					farmId, farmFeedId, beginDate, endDate);
			try {
				
				if (list != null && !list.isEmpty()) {

					for (SBEggSells sell : list) {
						JSONObject object = new JSONObject();
						object.put("farmId", sell.getFarmId());
						object.put("farmBreedId", sell.getFarmBreedId());
						object.put("good_box_size", sell.getGoodBoxSize());
						object.put("good_price_type",
								sell.getGoodPriceType());
						object.put("good_price_value",
								sell.getGoodPriceValue());
						object.put("good_salebox_num",
								sell.getGoodSaleboxNum());
						object.put("good_sale_weight",
								sell.getGoodSaleWeight());
						object.put("good_sale_money",
								sell.getGoodSaleMoney());
						
						object.put("dateBak1", sell.getDateBak1());
						object.put("dateBak2", sell.getDateBak2());
						object.put("dateBak3", sell.getDateBak3());
						object.put("broken_box_size",
								sell.getBrokenBoxSize());
						object.put("broken_price_type",
								sell.getBrokenPriceType());
						object.put("broken_price_value",
								sell.getBrokenPriceValue());
						object.put("broken_salebox_num",
								sell.getBrokenSaleboxNum());
						object.put("broken_sale_weight",
								sell.getBrokenSaleWeight());
						object.put("broken_sale_money",
								sell.getBrokenSaleMoney());
						object.put("chicken_manure",
								sell.getChickenManure());
						object.put("day_age", sell.getDayAge());
						object.put("week_age", sell.getWeekAge());

						object.put("intBak1", sell.getIntBak1());
						object.put("intBak2", sell.getIntBak2());
						object.put("intBak3", sell.getIntBak3());
						object.put("isHistory", sell.getIsHistory());
						object.put("sellDate", DateUtil.toFormatDateString(
								sell.getSellDate(), DateUtil.DATE_FORMAT));
						object.put("sell_date", DateUtil.toFormatDateString(
								sell.getSellDate(), "MM-dd"));

						object.put("numBak1", sell.getNumBak1());
						object.put("numBak2", sell.getNumBak2());
						object.put("numBak3", sell.getNumBak3());
						object.put("varBak1", sell.getVarBak1());
						object.put("varBak2", sell.getVarBak2());
						object.put("varBak3", sell.getVarBak3());

						DCDatas.put(object);

					}

				}

				
				dealRes = Constants.RESULT_SUCCESS;
				
				resJson.put("Result", "Success");
				resJson.put("FarmId", farmId);
				resJson.put("FarmBreedId", farmFeedId);
				resJson.put("SelectDate", selectDate);
				resJson.put("saleDetails", DCDatas);

			} catch (JSONException e) {
				e.printStackTrace();
				dealRes = Constants.RESULT_FAIL;
				try {
					resJson.put("Result", "Fail");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			DealSuccOrFail.dealApp(request, response, dealRes, resJson);

		} else {
			try {
				resJson.put("Result", "Fail");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		}
		mLogger.info("=====Now start executing LayerSalesInputReqController.queryDRByDate");
	}
	
	
	/**
	 * 更新銷售记录
	 */
	@RequestMapping("/saveDRV2")
	public void saveDRV2(HttpServletRequest request,
			HttpServletResponse response){
		mLogger.info("=====Now start executing LayerSalesInputReqController.saveDRV2");
		String paraStr = PubFun.getRequestPara(request);
		mLogger.info("updateFarm.para=" + paraStr);
		String dealRes = Constants.RESULT_FAIL;
		JSONObject resJson = new JSONObject();

		try {
			
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			JSONObject params = jsonobject.optJSONObject("params");
			int userId = jsonobject.optInt("id_spa");
			int farmId = params.getInt("FarmId");
			int farmFeedId = params.getInt("FarmBreedId");
			
			operationService.insert(SDUserOperationService.MENU_EGGSELL,SDUserOperationService.OPERATION_UPDATE,userId);
			
			//String month = params.getString("selectMonth");
			JSONArray dataInput = params.getJSONArray("saleDetails");
			// 需要修改的记录
			List<SBEggSells> list = new ArrayList<>();
			
			
			for(int i = 0; i < dataInput.length(); i++){
				JSONObject temp = dataInput.getJSONObject(i);
				mLogger.info("modify data:" + temp.toString());
				SBEggSells eggsell = new SBEggSells();
				eggsell.setBrokenBoxSize(new BigDecimal(temp.getDouble("broken_box_size")));
				eggsell.setBrokenPriceType(temp.getString("broken_price_type"));
				eggsell.setBrokenPriceValue(new BigDecimal(temp.getDouble("broken_price_value")));
				eggsell.setBrokenSaleboxNum(new BigDecimal(temp.getDouble("broken_salebox_num")));
				eggsell.setBrokenSaleMoney(new BigDecimal(temp.getDouble("broken_sale_money")));
				eggsell.setBrokenSaleWeight(new BigDecimal(temp.getDouble("broken_sale_weight")));
				
				
				eggsell.setFarmBreedId(farmFeedId);
				eggsell.setFarmId(farmId);
				
				eggsell.setGoodBoxSize(new BigDecimal(temp.getDouble("good_box_size")));
				eggsell.setGoodPriceType(temp.getString("good_price_type"));
				eggsell.setGoodPriceValue(new BigDecimal(temp.getDouble("good_price_value")));
				eggsell.setGoodSaleboxNum(new BigDecimal(temp.getDouble("good_salebox_num")));
				eggsell.setGoodSaleMoney(new BigDecimal(temp.getDouble("good_sale_money")));
				eggsell.setGoodSaleWeight(new BigDecimal(temp.getDouble("good_sale_weight")));
				
				eggsell.setChickenManure(new BigDecimal(temp.getDouble("chicken_manure")));
				
				eggsell.setIsHistory(temp.getString("isHistory"));
				java.util.Date date = DateUtil.parser(temp.getString("sellDate"), DateUtil.DATE_FORMAT);
				eggsell.setSellDate(date);
				
				eggsell.setModifyPerson(userId);
				eggsell.setModifyDate(new Date(System.currentTimeMillis()));
				eggsell.setModifyTime(new Date(System.currentTimeMillis()));

				list.add(eggsell);
			}
			
			
			if(!list.isEmpty()){
				int res = eggSellsReqManager.updateSellInfoByDate(list.get(0));
				mLogger.info("update sell info by date success,update rows count:" + res);
			}
			
			dealRes = Constants.RESULT_SUCCESS;
			resJson.put("FarmId", farmId);
			resJson.put("FarmBreedId", farmFeedId);
			resJson.put("Result", "Success");
			DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		} catch (JSONException e1) {
			e1.printStackTrace();
			try {
				resJson.put("Result", "Fail");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		}
		mLogger.info("=====Now end executing LayerSalesInputReqController.saveDRV2");
	}
	
}
