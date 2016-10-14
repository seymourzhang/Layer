/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBBreedDetail;
import com.mtc.mapper.app.SBBreedDetailMapper;
import com.mtc.mapper.app.SBBreedDetailMapperCustom;

/**
 * @ClassName: SBBreedDetailService
 * @Description: 
 * @Date 2015-12-8 下午8:19:09
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBBreedDetailService {
	@Autowired
	private SBBreedDetailMapper tSBBreedDetailMapper;
	@Autowired
	private SBBreedDetailMapperCustom tSBBreedDetailMapperCustom;
	
	public int insert(SBBreedDetail record){
		return tSBBreedDetailMapper.insert(record);
	}
	public List<SBBreedDetail> selectByhouseBreedId(int houseBreedId){
		return tSBBreedDetailMapperCustom.selectByhouseBreedId(houseBreedId);
	}
	public int updateByPrimaryKey(SBBreedDetail record){
		return tSBBreedDetailMapper.updateByPrimaryKey(record);
	}
	public SBBreedDetail selectByPrimaryKey(int houseBreedId,int age){
		return tSBBreedDetailMapper.selectByPrimaryKey(houseBreedId, age);
	}
	public SBBreedDetail selectByhouseIdDate(int houseBreedId,Date date){
		return tSBBreedDetailMapperCustom.selectByhouseIdDate(houseBreedId, date);
	}
	
	public int insertBatch(List<SBBreedDetail> item){
		return tSBBreedDetailMapperCustom.insertBatch(item);
	}
}
