package com.mtc.mapper.app;

import com.mtc.entity.app.SDHouse;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SDHouseMapperCustom {
    
	public List<SDHouse> selectByFarmId(Integer farmId);
	
	public SDHouse selectHousesIdByFarmId(@Param("farmId")int farmId,@Param("houseId")int houseId);

}