/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

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

import com.mtc.app.biz.UserReqManager;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.app.service.SDUserService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBUserFarm;
import com.mtc.entity.app.SBUserHouse;
import com.mtc.entity.app.SBUserRole;
import com.mtc.entity.app.SDUser;

/**
* @ClassName: UserReqController
* @Description: 
* @Date 2015年11月18日 下午2:55:46
* @Author Yin Guo Xiang
* 
*/
@Controller
@RequestMapping("/sys/user")
public class UserReqController {
	
	private static Logger mLogger =Logger.getLogger(UserReqController.class);  
	
	@Autowired
	private SDUserService mSDUserService;
	
	@Autowired
	private UserReqManager mUserReqManager ;
	
	@Autowired
	private SDUserOperationService operationService;
	
	public static void main(String[] args) throws Exception{
	}
	
	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing UserReqController.save");
		
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("saveUser.para=" + paraStr);
	        
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(SDUserOperationService.MENU_MEMBERINFO, SDUserOperationService.OPERATION_ADD, jsonObject.optInt("id_spa"));

			Date curDate = new Date();
			
			JSONObject tUserJson = jsonObject.getJSONObject("params");
			int id_spa = jsonObject.optInt("id_spa");
			if(PubFun.isNull(tUserJson.optString("name")) 
					  || PubFun.isNull(tUserJson.optString("tele")) 
					  || PubFun.isNull(tUserJson.optString("pw"))){
				resJson.put("ErrorMsg","插入失败，数据格式错误。");
			}else if(mSDUserService.selectByUserCode(tUserJson.optString("tele")) != null){
				resJson.put("ErrorMsg","保存失败，该手机号已经注册，请联系管理员。");
			}else{
				// 用户信息
				SDUser tSDUser = new SDUser();
				tSDUser.setUserRealName(tUserJson.optString("name"));
				tSDUser.setUserCode(tUserJson.optString("tele"));
				tSDUser.setUserPassword(tUserJson.optString("pw"));
				tSDUser.setUserMobile1(tUserJson.optString("tele"));
				
				tSDUser.setCreatePerson(id_spa);
				tSDUser.setModifyPerson(id_spa);
				tSDUser.setCreateDate(curDate);
				tSDUser.setModifyDate(curDate);
				tSDUser.setUserStatus("1");
				tSDUser.setFreezeStatus("0");
				List<SBUserRole> mUserRoles = new	ArrayList<SBUserRole>();
				SBUserRole tSBUserRole = new SBUserRole();
				tSBUserRole.setRoleId(tUserJson.optInt("role"));
				tSBUserRole.setUserCode(tSDUser.getUserCode());
				tSBUserRole.setCreateDate(curDate);
				tSBUserRole.setModifyDate(curDate);
				mUserRoles.add(tSBUserRole);
				
				List<SBUserHouse> mUserHouses = new	ArrayList<SBUserHouse>();
				if(!tUserJson.isNull("houses")){
					SBUserHouse tSBUserHouse = null;
					JSONArray houseArrays = tUserJson.getJSONArray("houses") ;
					for(int i=0;i<houseArrays.length();i++){
						System.out.println("tempId=" + houseArrays.optInt(i));
						tSBUserHouse = new SBUserHouse();
						tSBUserHouse.setFarmId(tUserJson.optInt("farmid"));
						tSBUserHouse.setHouseId(houseArrays.optInt(i));
						tSBUserHouse.setUserCode(tSDUser.getUserCode());
						tSBUserHouse.setCreateDate(curDate);
						tSBUserHouse.setModifyDate(curDate);
						mUserHouses.add(tSBUserHouse);
					}
				}
				
				SBUserFarm tSBUserFarm = null;
				if(tUserJson.has("farmid") && !tUserJson.isNull("farmid")){
					tSBUserFarm = new SBUserFarm();
					tSBUserFarm.setUserCode(tSDUser.getUserCode());
					tSBUserFarm.setFarmId(tUserJson.optInt("farmid"));
					tSBUserFarm.setCreateDate(curDate);
					tSBUserFarm.setModifyDate(curDate);
				}
				
				HashMap<String,Object> mParas = new HashMap<String,Object>(); 
				mParas.put("User", tSDUser);
				mParas.put("Houses", mUserHouses);
				mParas.put("Roles", mUserRoles);
				mParas.put("UserFarm", tSBUserFarm);
				
				SDUser newUser = mUserReqManager.dealSave(mParas);
				
				resJson.put("userId",newUser.getId());
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
		mLogger.info("=====Now end executing UserReqController.save");
	}
	
	@RequestMapping("/update")
    public void  update(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing UserReqController.update");
		response.setCharacterEncoding("UTF-8");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try{
			String paraStr = PubFun.getRequestPara(request);
		    mLogger.info("saveUser.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/

			Date curDate = new Date();
			int id_spa = jsonObject.optInt("id_spa");
			JSONObject mUserJson = jsonObject.getJSONObject("params");
			JSONObject tUserJson = mUserJson.getJSONObject("userInfo");
			
			String operate=mUserJson.optString("operate");
			if(operate.equals("DELETE")){
				operationService.insert(SDUserOperationService.MENU_MEMBERINFO, SDUserOperationService.OPERATION_DELETE, jsonObject.optInt("id_spa"));

				if(PubFun.isNull(tUserJson.optString("id"))){
					resJson.put("ErrorMsg","插入失败，数据格式错误。");
				}else{
					
				     int id = tUserJson.optInt("id");
				     SDUser tSDUser = mSDUserService.selectValidByPrimaryKey(id);
					 tSDUser.setFreezeStatus("1");
					 tSDUser.setModifyPerson(id_spa);
				     
					 mUserReqManager.dealDelete(tSDUser);
					 
					 resJson.put("userId",id);
					 /** 业务处理结束 **/
					 dealRes = Constants.RESULT_SUCCESS ;
				}
			}else if(operate.equals("UPDATE")){
				operationService.insert(SDUserOperationService.MENU_MEMBERINFO, SDUserOperationService.OPERATION_UPDATE, jsonObject.optInt("id_spa"));

				boolean commitFlag = true ;
				if(PubFun.isNull(tUserJson.optString("id"))
						|| PubFun.isNull(tUserJson.optString("name"))
						|| PubFun.isNull(tUserJson.optString("tele"))){
					commitFlag = false;
					resJson.put("ErrorMsg","插入失败，数据格式错误。");	
			    }else{
			    	int id = tUserJson.optInt("id");
			    	SDUser tSDUser = mSDUserService.selectValidByPrimaryKey(id);
					tSDUser.setUserRealName(tUserJson.optString("name"));
					tSDUser.setUserMobile1(tUserJson.optString("tele"));
					tSDUser.setModifyDate(curDate);
					tSDUser.setModifyPerson(id_spa);
					
					List<SBUserRole> mUserRoles = new	ArrayList<SBUserRole>();
					SBUserRole tSBUserRole = new SBUserRole();
					tSBUserRole.setRoleId(tUserJson.optInt("role"));
					tSBUserRole.setUserCode(tSDUser.getUserCode());
					tSBUserRole.setCreateDate(curDate);
					tSBUserRole.setModifyDate(curDate);
					tSBUserRole.setUserId(tSDUser.getId());
					tSBUserRole.setCreatePerson(id_spa);
					tSBUserRole.setModifyPerson(id_spa);
					mUserRoles.add(tSBUserRole);
					
					List<SBUserHouse> mUserHouses = new	ArrayList<SBUserHouse>();
					SBUserHouse tSBUserHouse = null;
					if(!tUserJson.isNull("houses")&&tSBUserRole.getRoleId()==4){
						JSONArray houseArrays = tUserJson.getJSONArray("houses") ;
						for(int i=0;i<houseArrays.length();i++){
							System.out.println("tempId=" + houseArrays.optInt(i));
							tSBUserHouse = new SBUserHouse();
							if(PubFun.isNull(tUserJson.optInt("farmid"))){
								commitFlag = false;
								resJson.put("ErrorMsg","farmid 为空");	
								break;
							}
							tSBUserHouse.setFarmId(tUserJson.optInt("farmid"));
							tSBUserHouse.setHouseId(houseArrays.optInt(i));
							tSBUserHouse.setUserCode(tSDUser.getUserCode());
							tSBUserHouse.setUserId(tSDUser.getId());
							tSBUserHouse.setCreatePerson(id_spa);
							tSBUserHouse.setModifyPerson(id_spa);
							tSBUserHouse.setCreateDate(curDate);
							tSBUserHouse.setModifyDate(curDate);
							mUserHouses.add(tSBUserHouse);
						}
					}
					if(commitFlag){
						HashMap<String,Object> mParas = new HashMap<String,Object>(); 
						mParas.put("User", tSDUser);
						mParas.put("Houses", mUserHouses);
						mParas.put("Roles", mUserRoles);
						
						SDUser newUser = mUserReqManager.dealUpdate(mParas);
						resJson.put("userId",newUser.getId());
					}
				}
				/** 业务处理结束 **/
				dealRes = Constants.RESULT_SUCCESS ;
			  }
		   }catch(Exception e){
			    dealRes = Constants.RESULT_FAIL ;
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("Exception", e.getMessage()==null ? "操作的对象为空":e.getMessage());
				}catch(JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
		   }
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing UserReqController.update");
	  }
	@RequestMapping("/upPassword")
    public void  upPassword(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing UserReqController.upPassword");
		response.setCharacterEncoding("UTF-8");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try{
			String paraStr = PubFun.getRequestPara(request);
		    mLogger.info("saveUser.para=" + paraStr);
			JSONObject jsonObject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			operationService.insert(SDUserOperationService.MENU_PASSWORD, SDUserOperationService.OPERATION_UPDATE, jsonObject.optInt("id_spa"));

			Date curDate = new Date();
			int id_spa = jsonObject.optInt("id_spa");
			JSONObject tUserJson = jsonObject.getJSONObject("params");
			
			 if(PubFun.isNull(jsonObject.optInt("id_spa"))
						|| PubFun.isNull(tUserJson.optString("user_id"))
						|| PubFun.isNull(tUserJson.optString("old_pw"))
						|| PubFun.isNull(tUserJson.optString("new_pw"))){
				  resJson.put("ErrorMsg","插入失败，数据格式错误。");
			 }else{
    	        int     id     = tUserJson.optInt("user_id");
		    	SDUser tSDUser = mSDUserService.selectValidByPrimaryKey(id);
		    	String oldpw   = tSDUser.getUserPassword();
		    	String old_pw  =  tUserJson.optString("old_pw");
		    	boolean commitFlag = true ;
		    	if(oldpw.equals(old_pw)){
		    		tSDUser.setUserPassword(tUserJson.optString("new_pw"));
					tSDUser.setModifyTime(curDate);
					tSDUser.setModifyDate(curDate);
					tSDUser.setModifyPerson(id_spa);
		    	}else{
		    		commitFlag = false;
					resJson.put("ErrorMsg","输入旧密码错误");	
		    	 }
					if(commitFlag){
						HashMap<String,Object> mParas = new HashMap<String,Object>(); 
						mParas.put("User", tSDUser);
						SDUser newUser = mUserReqManager.dealUpdatePw(mParas);
						resJson.put("userId",newUser.getId());
					    }
					/** 业务处理结束 **/
					dealRes = Constants.RESULT_SUCCESS ;
				 }
		   }catch(Exception e){
			    dealRes = Constants.RESULT_FAIL ;
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("Exception", e.getMessage());
				}catch(JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
		   }
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing UserReqController.upPassword");
	  }
}
