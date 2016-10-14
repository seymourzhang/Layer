package com.mtc.app.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBLayerBreedDetail;
import com.mtc.mapper.app.SBLayerBreedDetailMapper;
import com.mtc.mapper.app.SBLayerBreedDetailMapperCustom;

@Service
public class SBLayerBreedDetailService{
 @Autowired	
 private SBLayerBreedDetailMapperCustom	tSBLayerBreedDetailMapperCustom;
 @Autowired	
 private SBLayerBreedDetailMapper	tSBLayerBreedDetailMapper;
 public  int insertBatch(List<SBLayerBreedDetail> tJSONArray){
	 return tSBLayerBreedDetailMapperCustom.insertBatch(tJSONArray);
 }
 public  List<SBLayerBreedDetail> selectByhouseBreedId(int HouseBreedId,int age1,int age2){
	 return tSBLayerBreedDetailMapperCustom.selectByhouseBreedId(HouseBreedId,age1,age2);
 }
 public  SBLayerBreedDetail selectByPrimaryKey(int houseBreedId,int dayAge){
	 return tSBLayerBreedDetailMapper.selectByPrimaryKey(houseBreedId, dayAge);
 }
 public  int updateByhouseBreedId(List<SBLayerBreedDetail> tSBLayerBreedDetail){
	 return tSBLayerBreedDetailMapperCustom.batchUpdate(tSBLayerBreedDetail);
 }
 public  int updateByPrimaryKey(SBLayerBreedDetail record){
	 return tSBLayerBreedDetailMapper.updateByPrimaryKey(record);
 }
}
