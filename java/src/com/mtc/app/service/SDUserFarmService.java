/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBUserFarm;
import com.mtc.mapper.app.SBUserFarmMapper;
import com.mtc.mapper.app.SBUserFarmMapperCustom;


/**
 * @ClassName: SDUserHousesService
 * @Description: 
 * @Date 2015年11月25日 下午1:34:39
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SDUserFarmService {
	@Autowired
	private SBUserFarmMapper tSBUserFarmMapper ;
	@Autowired
	private SBUserFarmMapperCustom tSBUserFarmMapperCustom ;
	
	public SBUserFarm selectByUserId(int userId){
		return  tSBUserFarmMapperCustom.selectByUserId(userId);
	}
	public SBUserFarm selectByPrimaryKey(int id){
		return tSBUserFarmMapper.selectByPrimaryKey(id);
	}
	public SBUserFarm  selectByUserIdFarmId(int userId,int houseId){
		return tSBUserFarmMapperCustom.selectByUserIdFarmId(userId, houseId);
	}
}
