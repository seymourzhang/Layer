/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBEggSellsService;
import com.mtc.entity.app.SBEggSells;

/**
 * 
 * 封装update，delete，insert操作
 * 
 * @author lx
 *
 */
@Component
public class EggSellsReqManager {
	private static Logger mLogger =Logger.getLogger(EggSellsReqManager.class);
	
	@Autowired
	private SBEggSellsService eggSellsService;
	
	/**
	 * 获取一个月份的销售数据
	 * @return
	 */
	public List<SBEggSells> selectEggSellsByMonth( Integer farmId, Integer farmBreedId, Date beginDate,Date endDate){
		return eggSellsService.selectEggSellsByMonth(farmId, farmBreedId, beginDate,endDate);
	}

	
	public int updateSellInfoByDate(SBEggSells sell){
		return eggSellsService.updateSellInfoByDate(sell);
	}

	/**
	 * 按照日龄获取销售数据
	 * @return
	 */
	public List<HashMap<String,Object>> getEggSellsReportByDay( Integer farmId, Integer farmBreedId, Date beginDate,Date endDate){
		return eggSellsService.getEggSellsReportByDay(farmId, farmBreedId,beginDate,endDate);
	}

	/**
	 * 按照周龄获取销售数据
	 * @return
	 */
	public List<HashMap<String,Object>> getEggSellsReportByWeek( Integer farmId, Integer farmBreedId){
		return eggSellsService.getEggSellsReportByWeek(farmId, farmBreedId);
	}
	
	
	public List<HashMap<String,Object>>  getChickenEggsByWeek(Integer farmBreedId,Integer farmId ){
		return eggSellsService.getChickenEggsByWeek(farmBreedId, farmId);
	}

	public List<HashMap<String,Object>>  getChickenEggsByDay(Integer farmBreedId, Integer farmId ){
    	return eggSellsService.getChickenEggsByDay(farmBreedId, farmId);
    }
	
	
	public List<HashMap<String,Object>>  getWaterFeedByWeek(Integer farmBreedId,Integer farmId ){
		return eggSellsService.getWaterFeedByWeek(farmBreedId, farmId);
	}

	public List<HashMap<String,Object>>  getWaterFeedByDay(Integer farmBreedId, Integer farmId ){
    	return eggSellsService.getWaterFeedByDay(farmBreedId, farmId);
    }
	
}
