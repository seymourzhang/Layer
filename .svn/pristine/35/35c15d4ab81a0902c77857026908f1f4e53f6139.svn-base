package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBLayerFarmSettle;
import com.mtc.mapper.app.SBLayerFarmSettleMapper;
import com.mtc.mapper.app.SBLayerFarmSettleMapperCustom;
@Service
public class SBLayerFarmSettleService {
@Autowired
private SBLayerFarmSettleMapperCustom tSBLayerFarmSettleMapperCustom;
@Autowired
private SBLayerFarmSettleMapper tSBLayerFarmSettleMapper;
	public void insertBatch(List<SBLayerFarmSettle> tJSONArray) {
		tSBLayerFarmSettleMapperCustom.insertBatch(tJSONArray);
	}
	public void deletebatch( int farmBreedId ,String settleMonth) {
		tSBLayerFarmSettleMapperCustom.deletebatch(  farmBreedId , settleMonth);
	}
	public List<SBLayerFarmSettle>  selectByfarmBreedIdAndsettleMonth( int farmBreedId ,String settleMonth) {
		return  tSBLayerFarmSettleMapperCustom.selectByfarmBreedIdAndsettleMonth(  farmBreedId , settleMonth);
	}
	public int  insert(SBLayerFarmSettle record) {
		return  tSBLayerFarmSettleMapper.insert(record);
	}
	public SBLayerFarmSettle  selectByfarmBreedIdAndFeeCode( int farmBreedId ,String FeeCode) {
		return  tSBLayerFarmSettleMapperCustom.selectByfarmBreedIdAndFeeCode(  farmBreedId , FeeCode);
	}
	public int deleteByFarmBreedIdAndfeeCode(Integer farmBreedId,String feeCode) {
		return  tSBLayerFarmSettleMapperCustom.deleteByFarmBreedIdAndfeeCode(  farmBreedId , feeCode);
	}
}
