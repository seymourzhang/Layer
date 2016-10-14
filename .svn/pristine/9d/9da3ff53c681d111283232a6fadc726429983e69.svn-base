/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBTempSetting;
import com.mtc.mapper.app.SBTempSettingMapper;
import com.mtc.mapper.app.SBTempSettingMapperCustom;

/**
 * @ClassName: SBTempSettingService
 * @Description: 
 * @Date 2015-11-30 下午2:37:44
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBTempSettingService {

	@Autowired
	private SBTempSettingMapper tSBTempSettingMapper;
	
	@Autowired
	private SBTempSettingMapperCustom tSBTempSettingMapperCustom;
	
	public SBTempSetting selectByPrimaryKey(int id){
		return tSBTempSettingMapper.selectByPrimaryKey(id);
	}
	
	public SBTempSetting selectByFarmIdKeyandHouseId(int farm_id,int house_id){
		return tSBTempSettingMapperCustom.selectByFarmIdKeyandHouseId(farm_id, house_id);
	}
	
	public int insert(SBTempSetting record){
		return tSBTempSettingMapper.insert(record);
	}
	
	public int deleteByPrimaryKey(int id){
		return tSBTempSettingMapper.deleteByPrimaryKey(id);
	}
	
	public int updateByPrimaryKey(SBTempSetting record){
		return tSBTempSettingMapper.updateByPrimaryKey(record);
	}
}
