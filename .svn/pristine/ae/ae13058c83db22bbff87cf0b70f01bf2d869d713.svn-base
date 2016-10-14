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
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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

/**
 * 
 * 销售曲线
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
public class LayerRepSaleEggController {
	private static Logger mLogger = Logger
			.getLogger(LayerRepSaleEggController.class);

	@Autowired
	private EggSellsReqManager eggSellsReqManager;
	
	@Autowired
	private SDUserOperationService operationService;

	
	/**
	 * 获取销售日报分析数据
	 */
	@RequestMapping("/querySaleEgg")
	public void querySaleEgg(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=====Now start executing LayerRepSaleEggController.querySaleEgg");
		String paraStr = PubFun.getRequestPara(request);
		mLogger.info("updateFarm.para=" + paraStr);
		JSONObject jsonobject = null;
		String dealRes = Constants.RESULT_FAIL;
		JSONObject resJson = new JSONObject();
		JSONObject saleDatas = new JSONObject();
		// x轴
		List<String> xAxis = new ArrayList<>();
		// 销量
		List<String> weights = new ArrayList<>();
		// 价格
		List<String> prices = new ArrayList<>();
		
		
		
		
		//operationService.insert(SDUserOperationService.MENU_DAYREPORT,SDUserOperationService.OPERATION_ADD,111);
		
		
		
		try {
			jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject params = jsonobject.optJSONObject("params");
			int userid = jsonobject.optInt("id_spa");

			int farmId = 0;
			int farmFeedId = 0;
			String viewType = "01";// 01 周显示 02 日显示
			farmId = params.getInt("FarmId");
			farmFeedId = params.getInt("FarmBreedId");
			viewType = params.getString("ViewType");
			
			operationService.insert(SDUserOperationService.MENU_DATAANALYSIS_SALE,SDUserOperationService.OPERATION_SELECT,userid);
			
			
			
			Calendar calendar = Calendar.getInstance();
			List<HashMap<String, Object>> sellData = new ArrayList<>();
			if(viewType.equals("01")){
				sellData = eggSellsReqManager.getEggSellsReportByWeek(farmId, farmFeedId);
			}else{
				Date beginDate = new Date(System.currentTimeMillis());
				calendar.add(Calendar.DATE, -60);
				Date endDate = new Date(calendar.getTimeInMillis());
				sellData = eggSellsReqManager.getEggSellsReportByDay(farmId, farmFeedId,beginDate,endDate);
			}
			 java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00"); 
			String priceType = "01";
			if(!sellData.isEmpty()){
				
				if(viewType.equals("01")){
					int weekNum = 0;
					for(HashMap<String, Object> map : sellData){
						weekNum = (int) map.get("weekNum");
						Date date = (Date) map.get("sell_date");
						xAxis.add(map.get("weekNum").toString());
						
						if(date.getTime() > System.currentTimeMillis()){
							weights.add("-");
							prices.add("-");
						}else{
							BigDecimal weight = (BigDecimal)map.get("weekWeight");
							BigDecimal weekPrice = (BigDecimal)map.get("weekPrice");
							priceType = map.get("good_price_type").toString();
							if(weight.intValue() == 0){
								weights.add("0");
							}else{
								weights.add(weight.toString());
							}
							if(weekPrice.intValue() == 0){
								prices.add("0.00");
							}else{
								prices.add(df.format(weekPrice.doubleValue()));
							}
						}
					}
					
					if(sellData.size() < 60){
						int n = 60 - sellData.size();
						for(int i = 0 ;i < n ; i++){
							++weekNum;
							xAxis.add(weekNum + "");
							weights.add("-");
							prices.add("-");
						}
					}
					
				}else{// 日龄
					Date lastDate = new Date(System.currentTimeMillis());
					for(HashMap<String, Object> map : sellData){
						lastDate = (Date)map.get("sell_date");
						
						xAxis.add(DateUtil.toFormatDateString((Date)map.get("sell_date"), "MM-dd"));
						
						if(lastDate.getTime() > System.currentTimeMillis()){
							weights.add("-");
							prices.add("-");
						}else{
							BigDecimal weight = (BigDecimal)map.get("good_sale_weight");
							BigDecimal weekPrice = (BigDecimal)map.get("good_price_value");
							priceType = map.get("good_price_type").toString();
							
							if(weight.intValue() == 0){
								weights.add("0");
							}else{
								weights.add(weight.toString());
							}
							if(weekPrice.intValue() == 0){
								prices.add("0.00");
							}else{
								prices.add(df.format(weekPrice.doubleValue()));
							}
						}
						
						
					}
					// 不够60个，进行数据补全
					if(sellData.size() < 60){
						int n = 60 - sellData.size();
						
						calendar.setTimeInMillis(lastDate.getTime());
						for(int i = 0 ;i < n ; i++){
							calendar.add(Calendar.DATE, 1);
							xAxis.add(DateUtil.toFormatDateString(calendar.getTime(), "MM-dd"));
							weights.add("-");
							prices.add("-");
						}
					}
				}
			}
			
			
			
			saleDatas.put("SaleAmount", weights);
			saleDatas.put("SalePrice", prices);
			
			dealRes = Constants.RESULT_SUCCESS;
			
			resJson.put("FarmId", farmId);
			resJson.put("FarmBreedId", farmFeedId);
			resJson.put("ViewType", viewType);
			resJson.put("xAxis", xAxis);
			resJson.put("priceType", priceType);
			resJson.put("SaleDatas", saleDatas);
			resJson.put("Result", "Success");
			
			DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		} catch (JSONException e1) {
			e1.printStackTrace();
			mLogger.error("parse param err!",e1);
			try {
				resJson.put("Result", "Fail");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		}
		
		
	mLogger.info("=====Now end executing LayerRepSaleEggController.querySaleEgg");
	}

}
