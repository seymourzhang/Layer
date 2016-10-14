package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mtc.entity.app.SBLayerHouseBreed;
import com.mtc.mapper.app.SBLayerHouseBreedMapper;

@Service
public class SBLayerHouseBreedService {
@Autowired
private SBLayerHouseBreedMapper tSBLayerHouseBreedMapper;
 public void insert(SBLayerHouseBreed  tSBLayerHouseBreed){
	 tSBLayerHouseBreedMapper.insert(  tSBLayerHouseBreed);
 }
}
