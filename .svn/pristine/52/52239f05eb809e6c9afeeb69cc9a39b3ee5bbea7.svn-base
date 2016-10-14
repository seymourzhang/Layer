/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBDeviHouse;
import com.mtc.entity.app.SDHouse;
import com.mtc.mapper.app.SBDeviHouseMapperCustom;
import com.mtc.mapper.app.SDHouseMapper;
import com.mtc.mapper.app.SDHouseMapperCustom;

/**
 * @ClassName: SDHouseService
 * @Description: 
 * @Date 2015年11月24日 下午3:47:32
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SDHouseService {
	@Autowired
	private SDHouseMapper tSDHouseMapper ;
	
	@Autowired
	private SDHouseMapperCustom tSDHouseMapperCustom ;
	@Autowired
	private SBDeviHouseMapperCustom tSBDeviHouseMapperCustom ;
	
	public int insertHouse(SDHouse tSDHouse){
		return tSDHouseMapper.insert(tSDHouse);
	}
	public SDHouse selectByPrimaryKey(int Houseid){
		return 	tSDHouseMapper.selectByPrimaryKey(Houseid);
	}
    public int updateByPrimaryKey(SDHouse record){
		return 	tSDHouseMapper.updateByPrimaryKey(record);
	}
	public List<SDHouse> selectHousesByFarmId(int farmId){
		return tSDHouseMapperCustom.selectByFarmId(farmId);
	}

	
	public SDHouse selectHousesIdByFarmId(int farmId,int houseId){
		return tSDHouseMapperCustom.selectHousesIdByFarmId( farmId, houseId);
	}
	
	public SBDeviHouse selectHouseDevice(int houseId){
		return tSBDeviHouseMapperCustom.selectByHouseId(houseId);
	}

}
