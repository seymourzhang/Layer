/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBHouseAlarm;
import com.mtc.mapper.app.SBHouseAlarmMapper;

/**
 * @ClassName: SBHouseAlarmMapper
 * @Description: 
 * @Date 2015-11-30 下午2:43:54
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBHouseAlarmService {
	
	@Autowired
	private SBHouseAlarmMapper tSBHouseAlarmMapper;	
	
	public SBHouseAlarm selectByPrimaryKey(int farmId,int houseId){
		return tSBHouseAlarmMapper.selectByPrimaryKey(farmId, houseId);
	}

	public int insert(SBHouseAlarm record){
		return tSBHouseAlarmMapper.insert(record);
	}
	
	public int updateByPrimaryKey(SBHouseAlarm record){
		return tSBHouseAlarmMapper.updateByPrimaryKey(record);
	}
	
}
