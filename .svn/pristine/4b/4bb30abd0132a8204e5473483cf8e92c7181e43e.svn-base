/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBUserFarmService;
import com.mtc.app.service.SDUserHousesService;
import com.mtc.app.service.SDUserRolesService;
import com.mtc.app.service.SDUserService;
import com.mtc.entity.app.SBUserFarm;
import com.mtc.entity.app.SBUserHouse;
import com.mtc.entity.app.SBUserRole;
import com.mtc.entity.app.SDUser;

/**
 * @ClassName: UserReqManage
 * @Description: 
 * @Date 2015年11月20日 下午4:41:22
 * @Author Yin Guo Xiang
 * 
 */
@Component
public class UserReqManager {
	
	private static Logger mLogger =Logger.getLogger(UserReqManager.class);
	
	@Autowired
	private SDUserService mSDUserService;
	@Autowired
	private SDUserHousesService mSDUserHousesService;
	@Autowired
	private SDUserRolesService mSDUserRolesService;
	@Autowired
	private SBUserFarmService mSBUserFarmService;
	
	public SDUser dealSave(HashMap<String,Object> tPara) throws Exception{
		SDUser tUser = (SDUser)tPara.get("User");
		List<SBUserHouse> tUserHouses = (List<SBUserHouse>)tPara.get("Houses");
		List<SBUserRole>  tUserRoles  = (List<SBUserRole>)tPara.get("Roles");
		SBUserFarm tSBUserFarm = (SBUserFarm)tPara.get("UserFarm");
		
		mSDUserService.insertUser(tUser);
		int newUserid = tUser.getId();
		mLogger.info("新增用户id：" + tUser.getId());		
		for(SBUserHouse temp : tUserHouses){
			temp.setUserId(newUserid);
			temp.setCreatePerson(newUserid);
			temp.setModifyPerson(newUserid);
		}
		if(tUserHouses.size()>0){
			mSDUserHousesService.insertBatch(tUserHouses);
		}
		for(SBUserRole temp : tUserRoles){
			temp.setUserId(newUserid);
			temp.setCreatePerson(newUserid);
			temp.setModifyPerson(newUserid);
		}
		if(tUserRoles.size()>0){
			mSDUserRolesService.insertBatch(tUserRoles);
		}
		if(tSBUserFarm != null){
			tSBUserFarm.setUserId(newUserid);
			tSBUserFarm.setCreatePerson(newUserid);
			tSBUserFarm.setModifyPerson(newUserid);
			mSBUserFarmService.insert(tSBUserFarm);
		}
		return tUser;
	}
	
	public SDUser dealUpdate(HashMap<String,Object> tPara) throws Exception{
		SDUser tSDUser = (SDUser)tPara.get("User");
		List<SBUserHouse> tUserHouses = (List<SBUserHouse>)tPara.get("Houses");
		List<SBUserRole>  tUserRoles  = (List<SBUserRole>)tPara.get("Roles");
		
		//修改用户
		mSDUserService.updateUser(tSDUser);
		//先删除旧的用户栋舍关系，再新增
		mSDUserHousesService.deleteByUserId(tSDUser.getId());
		if(tUserHouses.size()>0){
			mSDUserHousesService.insertBatch(tUserHouses);
		}
		//先删除旧的用户角色关系，再新增		
		mSDUserRolesService.deleteByUserId(tSDUser.getId());
		if(tUserRoles.size()>0){
			mSDUserRolesService.insertBatch(tUserRoles);
		}
		return tSDUser;
	}
	public SDUser dealUpdatePw(HashMap<String,Object> tPara) throws Exception{
		SDUser tSDUser = (SDUser)tPara.get("User");
		//修改用户
		mSDUserService.updateUser(tSDUser);
		return tSDUser;
	}
	public void dealDelete(SDUser delSDUser) throws Exception{
		 mSDUserService.updateUser(delSDUser);
	     mLogger.info("删除用户id：" + delSDUser.getId());
	     
	     mSDUserHousesService.deleteByUserId(delSDUser.getId());
	     mSDUserRolesService.deleteByUserId(delSDUser.getId());
	}
}

















