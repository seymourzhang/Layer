package com.mtc.mapper.app;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mtc.entity.app.SBDeviHouse;

public interface SBDeviHouseMapperCustom {
   
	public SBDeviHouse selectByHouseId(Integer houseId);
	
    int deleteByHouseId(int UserId);
    
    SBDeviHouse selectByDeviandHouseId(@Param("houseId") Integer houseId,
    		                           @Param("device_code")  String  device_code);
    
    List<SBDeviHouse> selectByDeviceCode(String  device_code);
    
}