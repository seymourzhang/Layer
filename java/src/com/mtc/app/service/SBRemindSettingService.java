/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBRemindSetting;
import com.mtc.mapper.app.SBRemindSettingMapper;
import com.mtc.mapper.app.SBRemindSettingMapperCustom;

/**
 * 
 * @author lx
 *
 */
@Service
public class SBRemindSettingService {
	
	@Autowired
	private SBRemindSettingMapper remindSettingMapper;
	@Autowired
	private SBRemindSettingMapperCustom remindSettingMapperCustom;
	
	
	public SBRemindSetting selectByPrimaryKey(Integer farmId,Integer alarmType){
		return remindSettingMapper.selectByPrimaryKey(farmId,alarmType);
	}
	
	public int insert(SBRemindSetting record){
		return remindSettingMapperCustom.insert(record);
	}
	
	public int updateByPrimaryKey(SBRemindSetting record){
		return remindSettingMapperCustom.updateByPrimaryKey(record);
	}
}









