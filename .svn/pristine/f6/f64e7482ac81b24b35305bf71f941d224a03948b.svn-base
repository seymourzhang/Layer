package com.mtc.mapper.app;

import com.mtc.entity.app.SBHouseProbe;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SBHouseProbeMapperCustom {
   
	List<SBHouseProbe> selectByfarmIdandhouseId(@Param("farmId") Integer farmId, @Param("houseId") Integer houseId);

	int deleteByFarmIdHouseId(Integer farmId, Integer houseId);
   
}