package com.mtc.mapper.app;

import com.mtc.entity.app.SBTempSetting;

import org.apache.ibatis.annotations.Param;

public interface SBTempSettingMapperCustom {
	SBTempSetting selectByFarmIdKeyandHouseId(@Param("farm_id")  int farm_id,
		                                   	  @Param("house_id") int house_id);
}