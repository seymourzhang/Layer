package com.mtc.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mtc.entity.app.SBLayerBreedDetail;

public interface SBLayerBreedDetailMapperCustom {

    int insertBatch(List<SBLayerBreedDetail> tJSONArray);

	List<SBLayerBreedDetail> selectByhouseBreedId(@Param("houseBreedId")Integer houseBreedId,
												  @Param("dayAge1") Integer dayAge1, 
												  @Param("dayAge2") Integer dayAge2);

	int batchUpdate(List<SBLayerBreedDetail> tSBLayerBreedDetail);

}