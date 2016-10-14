/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBDataInput;
import com.mtc.mapper.app.SBDataInputMapper;
import com.mtc.mapper.app.SBDataInputMapperCustom;

/**
 * @ClassName: SBDataInputService
 * @Description: 
 * @Date 2015-12-8 下午7:34:53
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBDataInputService {

	@Autowired
	private SBDataInputMapper  tSBDataInputMapper;
	@Autowired
	private SBDataInputMapperCustom  tSBDataInputMapperCustom;
	
	public int insert(SBDataInput record){
		return tSBDataInputMapper.insert(record);
	}
	
	public SBDataInput selectBySBHouseBreedId(Integer id,String datatype,int age){
		return tSBDataInputMapperCustom.selectBySBHouseBreedId(id,datatype,age);
	}
	public int updateByPrimaryKey(SBDataInput record){
		return tSBDataInputMapper.updateByPrimaryKey(record);
	}
}
