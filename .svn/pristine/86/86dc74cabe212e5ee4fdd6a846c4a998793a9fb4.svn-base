/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBRemindSwitch;
import com.mtc.mapper.app.SBRemindSwitchMapper;
import com.mtc.mapper.app.SBRemindSwitchMapperCustom;

/**
 * 
 * @author lx
 *
 */
@Service
public class SBRemindSwitchService {
	
	@Autowired
	private SBRemindSwitchMapper remindSwitchMapper;
	@Autowired
	private SBRemindSwitchMapperCustom remindSwitchMapperCustom;
	
	public SBRemindSwitch selectByPrimaryKey(Integer farmId,Integer houseId, Integer alarmType){
		return remindSwitchMapper.selectByPrimaryKey(farmId, houseId, alarmType);
	}
	
	public List<SBRemindSwitch> selectByFarmIdAndAlarmType(Integer farmId, Integer alarmType){
		return remindSwitchMapperCustom.selectByFarmIdAndAlarmType(farmId, alarmType);
	}
	
	
	public List<SBRemindSwitch> selectByFarmIdAndAlarmTypeAndHouseId(Integer farmId,int houseId, Integer alarmType){
		return remindSwitchMapperCustom.selectByFarmIdAndAlarmTypeAndHouseId(farmId,houseId, alarmType);
	}
	
	
	public int insert(SBRemindSwitch record){
		return remindSwitchMapperCustom.insert(record);
	}
	

	public int updateByPrimaryKey(SBRemindSwitch record){
		return remindSwitchMapperCustom.updateByPrimaryKey(record);
	}

	public int insertBatch(List<SBRemindSwitch> list) {
		// TODO Auto-generated method stub
		return remindSwitchMapperCustom.insertBatch(list);
	}

	public int deleteByFarmHouseType(int farmId,int houseId, Integer remindMethod) {
		// TODO Auto-generated method stub
		return remindSwitchMapperCustom.deleteByFarmHouseType(farmId,houseId, remindMethod);
	}
}









