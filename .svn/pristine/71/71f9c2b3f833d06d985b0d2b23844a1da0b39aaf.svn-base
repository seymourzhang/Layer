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

import com.mtc.app.biz.FarmReqManager;
import com.mtc.app.biz.HouseReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.MySQLSPService;
import com.mtc.app.service.SBDeviHouseService;
import com.mtc.app.service.SDFarmService;
import com.mtc.app.service.SDHouseService;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.app.service.SDUserService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBDeviHouse;
import com.mtc.entity.app.SBUserFarm;
import com.mtc.entity.app.SDFarm;
import com.mtc.entity.app.SDHouse;

/**
 * @ClassName: FarmReqController
 * @Description: 
 * @Date 2015年11月23日 上午11:13:48
 * @Author Yin Guo Xiang
 * 
 */
@Controller
@RequestMapping("/sys/farm")
public class FarmReqController {
	private static Logger mLogger =Logger.getLogger(FarmReqController.class);
	
	@Autowired
	private FarmReqManager mFarmReqManager ;
	
	@Autowired
	private SDFarmService mSDFarmService;
	
	@Autowired
	private  BaseQueryService mBaseQueryService;
	
	@Autowired
	private SDUserService mSDUserService;
	
	@Autowired
	private MySQLSPService tMySQLSPService;
	
	@Autowired
	private SDUserOperationService operationService;
	
	@Autowired
	private HouseReqManager mHouseReqManager ;
	
	@Autowired
	private SBDeviHouseService mSBDeviHouseService ;
	
	@Autowired
	private SDHouseService mSDHouseService ;
	
	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing FarmReqController.save");
		
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveFarm.para=" + paraStr);
	        
			JSONObject jsonObject = new JSONObject(paraStr);
			
			operationService.insert(SDUserOperationService.MENU_FARMINFO, SDUserOperationService.OPERATION_ADD, jsonObject.optInt("id_spa"));
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			
			Date curDate = new Date();
			
			JSONObject tFarmJson = jsonObject.getJSONObject("params");
			int userId = jsonObject.optInt("id_spa");
			
			if(userId == 0){
				resJson.put("ErrorMsg","插入失败，数据格式错误。");
			}else{
				
				String tSQL = "SELECT count(1) from s_b_user_farm where user_id = " + userId;
				mLogger.info("======FarmReqController.save.sql = " + tSQL);
				int existsFarm = mBaseQueryService.selectIntergerByAny(tSQL);
				if(tFarmJson.optString("name") == null || tFarmJson.optString("name").equals("")){
					dealRes = Constants.RESULT_FAIL ;
					resJson.put("ErrorMsg", "请输入农场名称。。");
				}else{
					if(existsFarm == 0){
						// 农场信息
						SDFarm tSDFarm = new SDFarm();
						
						tSDFarm.setFarmNameChs(tFarmJson.optString("name"));
						tSDFarm.setFarmAdd1(tFarmJson.optString("address1"));
						tSDFarm.setFarmAdd2(tFarmJson.optString("address2"));
						tSDFarm.setFarmAdd3(tFarmJson.optString("address3"));
						tSDFarm.setFarmAdd4(tFarmJson.optString("address4"));
						tSDFarm.setFarmAdd5(tFarmJson.optString("address5"));
						// 饲养方式
						tSDFarm.setFeedMethod(tFarmJson.optString("feedtype"));
						// 养殖面积
						tSDFarm.setFeedDensity(PubFun.getBigDecimalData(tFarmJson.optString("feedarea")));
						// 栋舍尺寸
						tSDFarm.sethHeight(PubFun.getBigDecimalData(tFarmJson.optString("house_height")));
						tSDFarm.sethLength(PubFun.getBigDecimalData(tFarmJson.optString("house_length")));
						tSDFarm.sethWidth(PubFun.getBigDecimalData(tFarmJson.optString("house_width")));
						// 笼养 层 排
						tSDFarm.setLayers(tFarmJson.optInt("cageInfo_layer"));
						tSDFarm.setRows(tFarmJson.optInt("cageInfo_row"));
						// 合作公司				
						tSDFarm.setCorporation(tFarmJson.optString("corporation"));
						
						// 
						tSDFarm.setMemo1(tFarmJson.optString("buildDate"));
						int houseNumOffsize = tFarmJson.optInt("houseNumOffsize");
						int houseNum = tFarmJson.optInt("houseNum");
						// 
						tSDFarm.setMemo2(houseNum + houseNumOffsize + "");
						
						tSDFarm.setFreezeStatus("0");
						tSDFarm.setCreatePerson(userId);
						tSDFarm.setCreateDate(curDate);
						tSDFarm.setModifyPerson(userId);
						tSDFarm.setModifyDate(curDate);
						
						SBUserFarm tSBUserFarm = new SBUserFarm();
						tSBUserFarm.setUserId(userId);
						tSBUserFarm.setUserCode(mSDUserService.selectValidByPrimaryKey(userId).getUserCode());
						tSBUserFarm.setCreateDate(curDate);
						tSBUserFarm.setModifyDate(curDate);
						tSBUserFarm.setCreatePerson(userId);
						tSBUserFarm.setModifyPerson(userId);
						
						HashMap<String,Object> mParas = new HashMap<String,Object>(); 
						mParas.put("Farm", tSDFarm);
						mParas.put("UserFarm", tSBUserFarm);
						
						SDFarm newFarm = mFarmReqManager.dealSave(mParas);
						saveHouseInfo(houseNum, houseNumOffsize, newFarm.getId(), userId);
						resJson.put("farmId",newFarm.getId());
						
						//resJson.put("houseinfos", getHousesInfo(newFarm.getId()));
						
						dealRes = Constants.RESULT_SUCCESS ;
					}else{
						dealRes = Constants.RESULT_FAIL ;
						resJson.put("ErrorMsg", "该农场已经存在，请勿再次插入");
					}
				}
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
		mLogger.info("=====Now end executing FarmReqController.save");
	}
	
	private void saveHouseInfo(int houseNum,int houseNumOffsize,int farmId,int userId) throws Exception{
		Date curDate = new Date(System.currentTimeMillis());
		for(int i = 1; i<= houseNumOffsize; i++){
			int j = houseNum + i;
			String houseName = j > 9 ? j + "" : "0" + j;
			// 栋舍信息
			SDHouse tSDHouse = new SDHouse();
			tSDHouse.setHouseCode(houseName);
			tSDHouse.setHouseName(houseName);
			tSDHouse.setFarmId(farmId);
			tSDHouse.setFreezeStatus("0");
			tSDHouse.setCreatePerson(userId);
			tSDHouse.setCreateDate(curDate);
			tSDHouse.setModifyPerson(userId);
			tSDHouse.setModifyDate(curDate);
			// 栋舍设备信息
			SBDeviHouse tSBDeviHouse = new SBDeviHouse();
			tSBDeviHouse.setFarmId(farmId);
			//tSBDeviHouse.setDeviceCode(DeviceCode);
			tSBDeviHouse.setCreatePerson(userId);
			tSBDeviHouse.setCreateDate(curDate);
			tSBDeviHouse.setModifyPerson(userId);
			tSBDeviHouse.setModifyDate(curDate);
			
			HashMap<String,Object> hParas = new HashMap<String,Object>(); 
			hParas.put("House", tSDHouse);
			hParas.put("HouseDevice", tSBDeviHouse);
			
			SDHouse newHouse = mHouseReqManager.dealSave(hParas);
			mLogger.info("add new house id :" + newHouse.getId());
		}
	}
	
	private JSONArray getHousesInfo(int farmId) throws JSONException{
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
		
		return houseJsons;
	}
	
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing FarmReqController.update");
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("updateFarm.para=" + paraStr);
	        
//			paraStr = "{\"secret\":\"mtc_secret\",\"params\":{\"tele\":\"13456789\",\"houses\":[3,2,1],\"farmid\":9,\"name\":\"张张\",\"role\":2,\"pw\":\"sdfljlsjfl\"},\"id_spa\":\"mtc_spa\"}" ;
			
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(SDUserOperationService.MENU_FARMINFO, SDUserOperationService.OPERATION_UPDATE, jsonObject.optInt("id_spa"));

			JSONObject mjsonObject = jsonObject.getJSONObject("params");
		    String	operate = mjsonObject.optString("operate");
		    JSONObject tfarmInfo = mjsonObject.getJSONObject("farmInfo");
		    
		    Date curDate = new Date();
		    int userId = jsonObject.optInt("id_spa");
		    int farmId = tfarmInfo.optInt("id");
			if(operate.equals("DELETE")){
				if(PubFun.isNull(tfarmInfo.optInt("id")) 
				   || userId == 0){
					resJson.put("ErrorMsg","插入失败，数据格式错误。");
				}else{
					mFarmReqManager.dealDelete(farmId,userId);
					resJson.put("farmId",farmId);
				}
			}else if(operate.equals("UPDATE")){
				if(PubFun.isNull(tfarmInfo.optString("address1")) 
						|| PubFun.isNull(tfarmInfo.optString("address2")) 
						|| PubFun.isNull(tfarmInfo.optString("address3")) 
						|| PubFun.isNull(tfarmInfo.optInt("id")) 
						|| userId == 0){
					resJson.put("ErrorMsg","插入失败，数据格式错误。");
				}else{
					SDFarm tSDFarm = mSDFarmService.selectByPrimaryKey(farmId);
					tSDFarm.setFarmNameChs(tfarmInfo.optString("name"));
					tSDFarm.setFarmAdd1(tfarmInfo.optString("address1"));
					tSDFarm.setFarmAdd2(tfarmInfo.optString("address2"));
					tSDFarm.setFarmAdd3(tfarmInfo.optString("address3"));
					tSDFarm.setFarmAdd4(tfarmInfo.optString("address4"));
					tSDFarm.setFarmAdd5(tfarmInfo.optString("address5"));
					// 饲养方式
					tSDFarm.setFeedMethod(tfarmInfo.optString("feedtype"));
					// 养殖面积
					tSDFarm.setFeedDensity(PubFun.getBigDecimalData(tfarmInfo.optString("feedarea")));
					// 栋舍尺寸
					tSDFarm.sethHeight(PubFun.getBigDecimalData(tfarmInfo.optString("house_height")));
					tSDFarm.sethLength(PubFun.getBigDecimalData(tfarmInfo.optString("house_length")));
					tSDFarm.sethWidth(PubFun.getBigDecimalData(tfarmInfo.optString("house_width")));
					// 笼养 层 排
					tSDFarm.setLayers(tfarmInfo.optInt("cageInfo_layer"));
					tSDFarm.setRows(tfarmInfo.optInt("cageInfo_row"));
					// 合作公司				
					tSDFarm.setCorporation(tfarmInfo.optString("corporation"));
					// 
					tSDFarm.setMemo1(tfarmInfo.optString("buildDate"));
					int houseNumOffsize = tfarmInfo.optInt("houseNumOffsize");
					int houseNum = tfarmInfo.optInt("houseNum");
					// 
					tSDFarm.setMemo2(houseNum + houseNumOffsize + "");
					
					
					
					tSDFarm.setModifyPerson(userId);
					tSDFarm.setModifyDate(curDate);
					tSDFarm.setModifyTime(curDate);
					
					
					HashMap<String,Object> mParas = new HashMap<String,Object>(); 
					mParas.put("Farm", tSDFarm);
					
					SDFarm newFarm = mFarmReqManager.dealUpdate(mParas);
					resJson.put("farmId",newFarm.getId());
					//resJson.put("houseinfos", getHousesInfo(newFarm.getId()));
					
					saveHouseInfo(houseNum, houseNumOffsize, newFarm.getId(), userId);
					
					
				}
				dealRes = Constants.RESULT_SUCCESS ;
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
		mLogger.info("=====Now end executing FarmReqController.update");
	}
}
