/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBEggSells;
import com.mtc.mapper.app.SBEggSellsMapper;
import com.mtc.mapper.app.SBEggSellsMapperCustom;

/**
 * @ClassName: SBEggSellsService
 * @Description: 蛋鸡销售日报操作
 * 
 */
@Service
public class SBEggSellsService {
	
	@Autowired
	private  SBEggSellsMapper eggSellsMapper;

	@Autowired
	private SBEggSellsMapperCustom custom;
	
	
	/**
	 * 获取一个月份的销售数据
	 * @return
	 */
	public List<SBEggSells> selectEggSellsByMonth( Integer farmId, Integer farmBreedId, Date beginDate,Date endDate){
		return custom.selectEggSellsByMonth(farmId, farmBreedId, beginDate,endDate);
	}



	
	public int updateSellInfoByDate(SBEggSells sell){
		return custom.updateSellInfoByDate(sell);
	}
	
	
	public int insertBatch(List<SBEggSells> tJSONArray){
		return custom.insertBatch(tJSONArray);
	}

	
	/**
	 * 按照日龄获取销售数据
	 * @return
	 */
	public List<HashMap<String,Object>> getEggSellsReportByDay( Integer farmId, Integer farmBreedId, Date beginDate,Date endDate){
		return custom.getEggSellsReportByDay(farmId, farmBreedId,beginDate,endDate);
	}

	/**
	 * 按照周龄获取销售数据
	 * @return
	 */
	public List<HashMap<String,Object>> getEggSellsReportByWeek( Integer farmId, Integer farmBreedId){
		return custom.getEggSellsReportByWeek(farmId, farmBreedId);
	}


	public List<HashMap<String,Object>>  getChickenEggsByWeek(Integer farmBreedId,Integer farmId ){
		return custom.getChickenEggsByWeek(farmBreedId, farmId);
	}

	public List<HashMap<String,Object>>  getChickenEggsByDay(Integer farmBreedId, Integer farmId ){
    	return custom.getChickenEggsByDay(farmBreedId, farmId);
    }

	public List<HashMap<String,Object>>  getWaterFeedByWeek(Integer farmBreedId,Integer farmId ){
		return custom.getWaterFeedByWeek(farmBreedId, farmId);
	}

	public List<HashMap<String,Object>>  getWaterFeedByDay(Integer farmBreedId, Integer farmId ){
    	return custom.getWaterFeedByDay(farmBreedId, farmId);
    }
	
}
