package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBHouseBreedTsk;
import com.mtc.mapper.app.SBHouseBreedTskMapper;
@Service
public class SBHouseBreedTskServier {

	@Autowired
	private SBHouseBreedTskMapper tSBHouseBreedTskMapper;

	public SBHouseBreedTsk selectByPrimaryKey(int id){
		return tSBHouseBreedTskMapper.selectByPrimaryKey(id);
	}
	public int updateByPrimaryKey(SBHouseBreedTsk record){
		return tSBHouseBreedTskMapper.updateByPrimaryKey(record);
	}
}
