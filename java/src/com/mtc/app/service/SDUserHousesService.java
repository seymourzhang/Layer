/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBUserHouse;
import com.mtc.mapper.app.SBUserHouseMapper;
import com.mtc.mapper.app.SBUserHouseMapperCustom;

/**
 * @ClassName: SDUserHousesService
 * @Description: 
 * @Date 2015年11月25日 下午1:34:39
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SDUserHousesService {
	@Autowired
	private SBUserHouseMapper tSBUserHouseMapper ;
	@Autowired
	private SBUserHouseMapperCustom tSBUserHouseMapperCustom ;
	
	public List<SBUserHouse> selectByUserId(int userId){
		return tSBUserHouseMapperCustom.selectByUserId(userId);
	}

	public SBUserHouse selectByPrimaryKey(int id){
		return tSBUserHouseMapper.selectByPrimaryKey(id);
	}
	public List<SBUserHouse>  selectByUserIdHouseId(int userId,int houseId){
		return tSBUserHouseMapperCustom.selectByUserIdHouseId(userId, houseId);
	}

	public int insertBatch(List<SBUserHouse> items){
		return tSBUserHouseMapperCustom.insertBatch(items);
	}
	public int deleteByUserId(int userId){
		return tSBUserHouseMapperCustom.deleteByUserId(userId);
	}

}
