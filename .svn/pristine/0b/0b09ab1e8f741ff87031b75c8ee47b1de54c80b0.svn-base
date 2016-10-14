package com.mtc.mapper.app;

import com.mtc.entity.app.SBHouseBreed;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SBHouseBreedMapperCustom {
    List<SBHouseBreed>  selectBySBFarmBeedId(int id);

	SBHouseBreed selectBySBFarmBeedHouseId(@Param("farmBreedId") int farmBreedId,
			                               @Param("house_id") int houseId);
}