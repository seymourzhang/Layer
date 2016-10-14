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
import com.mtc.mapper.app.SBDeviHouseMapper;
import com.mtc.mapper.app.SBDeviHouseMapperCustom;

/**
 * @ClassName: SBDeviHouseService
 * @Description: 
 * @Date 2015年12月1日 上午11:18:17
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SBDeviHouseService {

	@Autowired
	private SBDeviHouseMapper tSBDeviHouseMapper ;
	
	@Autowired
	private SBDeviHouseMapperCustom tSBDeviHouseMapperCustom ;
	
	public int insertDeviceHouse(SBDeviHouse tSBDeviHouse){
		return tSBDeviHouseMapper.insert(tSBDeviHouse);
	}
	public SBDeviHouse selectByPrimaryKey(int id){
		return 	tSBDeviHouseMapper.selectByPrimaryKey(id);
	}
	public int deleteByHouseid(int Houseid){
		return 	tSBDeviHouseMapperCustom.deleteByHouseId(Houseid);
	}
	public SBDeviHouse selectByHouseId(int houseId){
		return tSBDeviHouseMapperCustom.selectByHouseId(houseId);
	}
	public SBDeviHouse selectByDeviandHouseId(int houseId,String  device_code){
		return tSBDeviHouseMapperCustom.selectByDeviandHouseId(houseId,device_code);
	}
	public List<SBDeviHouse> selectByDeviceCode(String  device_code){
		return tSBDeviHouseMapperCustom.selectByDeviceCode(device_code);
	}
}
