/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBReminder;
import com.mtc.mapper.app.SBReminderMapper;
import com.mtc.mapper.app.SBReminderMapperCustom;

/**
 * 
 * @author lx
 *
 */
@Service
public class SBRemindAlarmerService {
	@Autowired
	private SBReminderMapper reminderMapper;
	@Autowired
	private SBReminderMapperCustom reminderMapperCustom;
	
	public List<SBReminder> selectByFarmIdAndAlarmType(Integer farmId,  Integer alarmType){
		return reminderMapperCustom.selectByFarmIdAndAlarmType(farmId, alarmType);
	}
	
	public List<SBReminder> selectByFarmIdAndAlarmTypeAndHouseId(Integer farmId,int houseId,  Integer alarmType){
		return reminderMapperCustom.selectByFarmIdAndAlarmTypeAndHouseId(farmId,houseId, alarmType);
	}
	
	public int insertBatch(List<SBReminder> list){
		return reminderMapperCustom.insertBatch(list);
	}
	
	public int updateByPrimaryKey(SBReminder record){
		return reminderMapperCustom.updateByPrimaryKey(record);
	}

	public int deleteByFarmHouseType(int farmId,int houseId,
			Integer remindMethod) {
		// TODO Auto-generated method stub
		return reminderMapperCustom.deleteByFarmHouseType(farmId,houseId, remindMethod);
	}
}









