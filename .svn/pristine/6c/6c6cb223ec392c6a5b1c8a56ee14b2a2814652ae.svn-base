/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBAlarmInco;
import com.mtc.mapper.app.SBAlarmIncoMapper;
import com.mtc.mapper.app.SBAlarmIncoMapperCustom;

/**
 * @ClassName: SBAlarmIncoService
 * @Description: 
 * @Date 2015-12-22 上午10:09:54
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBAlarmIncoService {

	@Autowired
	private SBAlarmIncoMapper tSBAlarmIncoMapper;
	@Autowired
	private SBAlarmIncoMapperCustom tSBAlarmIncoMapperCustom;
	
	public SBAlarmInco selectByPrimaryKey(int id){
		return tSBAlarmIncoMapper.selectByPrimaryKey(id);
	}
	public int updateByPrimaryKey(SBAlarmInco record){
		return tSBAlarmIncoMapper.updateByPrimaryKey(record);
	}
	
	public int updateToDealedByHouseId(int modifyUserId,int delayTime,int houseId){
		return tSBAlarmIncoMapperCustom.updateToDealedByHouseId(modifyUserId, delayTime,houseId);
	}
}
