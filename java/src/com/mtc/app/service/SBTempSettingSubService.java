/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBTempSettingSub;
import com.mtc.mapper.app.SBTempSettingSubMapper;
import com.mtc.mapper.app.SBTempSettingSubMapperCustom;

/**
 * @ClassName: SBTempSettingSubService
 * @Description: 
 * @Date 2015-11-30 下午2:38:36
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBTempSettingSubService {

	@Autowired
	private SBTempSettingSubMapper tSBTempSettingSubMapper;	
	@Autowired
	private SBTempSettingSubMapperCustom tSBTempSettingSubMapperCustom;	
	public int deleteByPrimaryKey(Integer id,Integer  dayAge){
	  return	tSBTempSettingSubMapper.deleteByPrimaryKey(id, dayAge);
	}
	
	public int insert(SBTempSettingSub record){
	  return	tSBTempSettingSubMapper.insert(record);
	}
	public List<SBTempSettingSub> selectByOnePrimaryKey(int uidNum){
		  return	tSBTempSettingSubMapperCustom.selectByOnePrimaryKey(uidNum);
	}
}
