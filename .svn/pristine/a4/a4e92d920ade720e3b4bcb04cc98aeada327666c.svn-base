package com.mtc.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mtc.entity.app.SBLayerFarmSettle;

public interface SBLayerFarmSettleMapperCustom {
	int insertBatch(List<SBLayerFarmSettle> tJSONArray);
	int deletebatch(@Param("farmBreedId") int farmBreedId ,
			        @Param("settleMonth") String settleMonth);
	List<SBLayerFarmSettle> selectByfarmBreedIdAndsettleMonth(@Param("farmBreedId") int farmBreedId,
	                                                          @Param("settleMonth") String settleMonth);
	SBLayerFarmSettle selectByfarmBreedIdAndFeeCode(@Param("farmBreedId") int farmBreedId,
			                                        @Param("feeCode")     String FeeCode);
	int deleteByFarmBreedIdAndfeeCode(@Param("farmBreedId") Integer farmBreedId,
			                          @Param("feeCode")  String feeCode);
}
