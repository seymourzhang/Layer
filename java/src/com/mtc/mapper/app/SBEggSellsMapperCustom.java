package com.mtc.mapper.app;

import com.mtc.entity.app.SBEggSells;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SBEggSellsMapperCustom {
	
	/**
	 * 获取一个月份的销售数据
	 * @return
	 */
	List<SBEggSells> selectEggSellsByMonth(@Param("farmId") Integer farmId,@Param("farmBreedId") Integer farmBreedId,
			@Param("beginDate") Date sellDate,@Param("endDate") Date endDate);


	/**
	 * 获取一个批次所有的销售数据
	 * @return
	 */
	List<HashMap<String,Object>> selectEggSells(@Param("farmId") Integer farmId,@Param("farmBreedId") Integer farmBreedId);

	int updateSellInfoByBatch(@Param("list") List<SBEggSells> list);
	
	int updateSellInfoByDate(SBEggSells sell);
	
	int insertBatch(List<SBEggSells> tJSONArray) ;
	
	/**
	 * 按照日龄获取销售数据
	 * @return
	 */
	List<HashMap<String,Object>> getEggSellsReportByDay(@Param("farmId") Integer farmId,@Param("farmBreedId") Integer farmBreedId, @Param("beginDate")Date beginDate,@Param("endDate")Date endDate);

	/**
	 * 按照周龄获取销售数据
	 * @return
	 */
	List<HashMap<String,Object>> getEggSellsReportByWeek(@Param("farmId") Integer farmId,@Param("farmBreedId") Integer farmBreedId);

	
	List<HashMap<String,Object>>  getChickenEggsByWeek(@Param("farmBreedId") Integer farmBreedId,@Param("farmId") Integer farmId );

    List<HashMap<String,Object>>  getChickenEggsByDay(@Param("farmBreedId") Integer farmBreedId,@Param("farmId") Integer farmId );
    
    List<HashMap<String,Object>>  getWaterFeedByWeek(@Param("farmBreedId") Integer farmBreedId,@Param("farmId") Integer farmId );

    List<HashMap<String,Object>>  getWaterFeedByDay(@Param("farmBreedId") Integer farmBreedId,@Param("farmId") Integer farmId );
    
}