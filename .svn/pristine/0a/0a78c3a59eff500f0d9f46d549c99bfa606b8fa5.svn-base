/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

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

import com.mtc.app.biz.HouseReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.MySQLSPService;
import com.mtc.app.service.SBDeviHouseService;
import com.mtc.app.service.SDHouseService;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBDeviHouse;
import com.mtc.entity.app.SDHouse;

/**
 * @ClassName: HouseReqController
 * @Description: 
 * @Date 2015年11月24日 下午3:46:30
 * @Author Yin Guo Xiang
 * 
 */
@Controller
@RequestMapping("/sys/house")
public class HouseReqController {

	private static Logger mLogger =Logger.getLogger(HouseReqController.class);
	@Autowired
	private HouseReqManager mHouseReqManager ;
	@Autowired
	private SDHouseService mSDHouseService;
	@Autowired
	private SBDeviHouseService tSBDeviHouseService ;
	@Autowired
	private BaseQueryService tBaseQueryService;
	@Autowired
	private MySQLSPService tMySQLSPService;
	
	@Autowired
	private SDUserOperationService operationService;
	
	@Autowired
	private SBDeviHouseService mSBDeviHouseService ;
	
	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing HouseReqController.save");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
           	        
			JSONObject jsonObject = new JSONObject(paraStr);
			operationService.insert(SDUserOperationService.MENU_HOUSEINFO, SDUserOperationService.OPERATION_ADD, jsonObject.optInt("id_spa"));

			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			
			Date curDate = new Date();
			JSONObject tHouseJson = jsonObject.getJSONObject("params");
			int userId = jsonObject.optInt("id_spa");
			String DeviceCode = tHouseJson.optString("mtc_device_id");
			int farmId = tHouseJson.optInt("farmId");
			String houseName = tHouseJson.optString("houseName");
			int countFlag = 0;
			if(!PubFun.isNull(DeviceCode)){
				List<SBDeviHouse> mSBDeviHouse = tSBDeviHouseService.selectByDeviceCode(DeviceCode);
				countFlag = mSBDeviHouse==null ? 0:mSBDeviHouse.size();
			}
			
			if(PubFun.isNull(tHouseJson.optString("houseName"))
					|| userId == 0){
				resJson.put("ErrorMsg","插入失败，数据格式错误。");
			}else if(countFlag != 0){
				resJson.put("ErrorMsg","该设备已经存在。");
			}else{
				// 栋舍信息
				SDHouse tSDHouse = new SDHouse();
				tSDHouse.setHouseCode(houseName);
				tSDHouse.setHouseName(houseName);
				tSDHouse.setFarmId(farmId);
				tSDHouse.setHouseHeight(PubFun.getBigDecimalData(tHouseJson.optString("h_height")));
				tSDHouse.setHouseLength(PubFun.getBigDecimalData(tHouseJson.optString("h_length")));
				tSDHouse.setHouseWidth(PubFun.getBigDecimalData(tHouseJson.optString("h_width")));
				tSDHouse.setFeedDensity(PubFun.getBigDecimalData(tHouseJson.optString("feedarea")));
				tSDHouse.setFreezeStatus("0");
				tSDHouse.setCreatePerson(userId);
				tSDHouse.setCreateDate(curDate);
				tSDHouse.setModifyPerson(userId);
				tSDHouse.setModifyDate(curDate);
				// 栋舍设备信息
				SBDeviHouse tSBDeviHouse = new SBDeviHouse();
				tSBDeviHouse.setFarmId(farmId);
				tSBDeviHouse.setDeviceCode(DeviceCode);
				tSBDeviHouse.setCreatePerson(userId);
				tSBDeviHouse.setCreateDate(curDate);
				tSBDeviHouse.setModifyPerson(userId);
				tSBDeviHouse.setModifyDate(curDate);
				
				HashMap<String,Object> mParas = new HashMap<String,Object>(); 
				mParas.put("House", tSDHouse);
				mParas.put("HouseDevice", tSBDeviHouse);
				
				SDHouse newHouse = mHouseReqManager.dealSave(mParas);
				resJson.put("houseId",newHouse.getId());
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
		mLogger.info("=====Now end executing HouseReqController.save");
	}
	
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing HouseReqController.update");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveHouse.para=" + paraStr);
	        
			JSONObject jsonObject = new JSONObject(paraStr);

			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			
			Date curDate = new Date();
			
			JSONObject tParaJson = jsonObject.getJSONObject("params");
			int userId = jsonObject.optInt("id_spa");
			
			JSONObject tHouseJson = tParaJson.getJSONObject("houseInfo");
			String operate = tParaJson.optString("operate");
			String DeviceCode=tHouseJson.optString("mtc_device_id");
			int houseId=tHouseJson.optInt("houseId");
			int countFlag = 0;
			if(!PubFun.isNull(DeviceCode)){
				String SQL="SELECT count(1) from s_b_devi_house where device_code = '"+DeviceCode+"' and house_id <>" +houseId;
				countFlag = tBaseQueryService.selectIntergerByAny(SQL);
			}
            
			if(operate.equals("UPDATE")){
				operationService.insert(SDUserOperationService.MENU_HOUSEINFO, SDUserOperationService.OPERATION_UPDATE, jsonObject.optInt("id_spa"));

				if(PubFun.isNull(tHouseJson.optString("houseId"))
					|| userId == 0){
					resJson.put("ErrorMsg","保存失败，请求参数错误。");
				}else if(countFlag != 0){
					resJson.put("ErrorMsg","保存失败，该设备ID已经被使用。");
				}else{
					SDHouse tSDHouse = mSDHouseService.selectByPrimaryKey(houseId);
					tSDHouse.setHouseCode(tHouseJson.optString("houseName"));
					tSDHouse.setHouseName(tHouseJson.optString("houseName"));
					tSDHouse.setHouseHeight(PubFun.getBigDecimalData(tHouseJson.optString("h_height")));
					tSDHouse.setHouseLength(PubFun.getBigDecimalData(tHouseJson.optString("h_length")));
					tSDHouse.setHouseWidth(PubFun.getBigDecimalData(tHouseJson.optString("h_width")));
					tSDHouse.setFeedDensity(PubFun.getBigDecimalData(tHouseJson.optString("feedarea")));
					tSDHouse.setFarmId(tHouseJson.optInt("farmId"));
					tSDHouse.setModifyPerson(userId);
					tSDHouse.setModifyDate(curDate);
					tSDHouse.setModifyTime(curDate);
					// 栋舍设备信息
					
					SBDeviHouse tSBDeviHouse = new SBDeviHouse();
					tSBDeviHouse.setFarmId(tHouseJson.optInt("farmId"));
					tSBDeviHouse.setHouseId(tHouseJson.optInt("houseId"));
					tSBDeviHouse.setModifyPerson(userId);
					tSBDeviHouse.setModifyDate(curDate);
					tSBDeviHouse.setModifyTime(curDate);
					tSBDeviHouse.setCreateDate(curDate);
					tSBDeviHouse.setCreatePerson(userId);
					tSBDeviHouse.setCreateTime(curDate);
					tSBDeviHouse.setDeviceCode(DeviceCode);
					
					HashMap<String,Object> mParas = new HashMap<String,Object>(); 
					mParas.put("House", tSDHouse);
					mParas.put("HouseDevice", tSBDeviHouse);
					
					SDHouse newHouse = mHouseReqManager.dealUpdate(mParas);
					resJson.put("houseId",newHouse.getId());
				}
			}else if(operate.equals("DELETE")){
				operationService.insert(SDUserOperationService.MENU_HOUSEINFO, SDUserOperationService.OPERATION_DELETE, jsonObject.optInt("id_spa"));

				SDHouse tSDHouse = mSDHouseService.selectByPrimaryKey(houseId);
				tSDHouse.setFreezeStatus("1");
				tSDHouse.setModifyDate(curDate);
				tSDHouse.setModifyTime(curDate);
				tSDHouse.setModifyPerson(userId);
				HashMap<String,Object> mParas = new HashMap<String,Object>(); 
				mParas.put("House", tSDHouse);
				
				SDHouse newHouse = mHouseReqManager.dealDelete(mParas);
				resJson.put("houseId",newHouse.getId());
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
		mLogger.info("=====Now end executing HouseReqController.update");
	}
	
	@RequestMapping("/queryHouses")
	public void queryHouses(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing HouseReqController.queryHouses");
		JSONObject resJson = new JSONObject();
		String dealRes = Constants.RESULT_FAIL ; ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("queryHouses.para=" + paraStr);
	        
			JSONObject jsonObject = new JSONObject(paraStr);

			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			
			JSONObject tParaJson = jsonObject.getJSONObject("params");
			int farmId = tParaJson.optInt("FarmId");
			
			
			// 返回 栋舍信息
			JSONArray houseJsons = new JSONArray();
			
			List<SDHouse> mSDHouses = mSDHouseService.selectHousesByFarmId(farmId);
			
			if(mSDHouses != null && mSDHouses.size() > 0){
				JSONObject houseJson = null;
				for(int i = 0;i<mSDHouses.size();i++){
					if(mSDHouses.get(i).getFreezeStatus().equals("1")){
						continue;
					}
					houseJson = new JSONObject();
					houseJson.put("id", mSDHouses.get(i).getId());
					houseJson.put("houseName", mSDHouses.get(i).getHouseName());
					houseJson.put("h_length", mSDHouses.get(i).getHouseLength());
					houseJson.put("h_width", mSDHouses.get(i).getHouseWidth());
					houseJson.put("h_height", mSDHouses.get(i).getHouseHeight());
					houseJson.put("feedarea", mSDHouses.get(i).getFeedDensity());
					
					SBDeviHouse tSBDeviHouse = mSBDeviHouseService.selectByHouseId(mSDHouses.get(i).getId());  
					String tempDeviceId = "";
					if(tSBDeviHouse!=null){
						tempDeviceId = tSBDeviHouse.getDeviceCode();
					}
					houseJson.put("mtc_device_id", tempDeviceId);
					houseJsons.put(houseJson) ;
				}
			}
			dealRes = Constants.RESULT_SUCCESS ;
			resJson.put("houseinfos", houseJsons);

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
		mLogger.info("=====Now end executing HouseReqController.queryHouses");
		
	}
}
