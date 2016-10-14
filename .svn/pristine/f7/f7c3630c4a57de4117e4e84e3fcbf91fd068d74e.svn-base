package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mtc.entity.app.SBFarmTask;
import com.mtc.mapper.app.SBFarmTaskMapper;

@Service
public class SBFarmTaskService {
	@Autowired
	private  SBFarmTaskMapper tSBFarmTaskMapper;
	
 public int addSBFarmTask(SBFarmTask tSBFarmTask){
	 return tSBFarmTaskMapper.insert(tSBFarmTask);
 }
 public SBFarmTask selectByPrimaryKey(int id){
	 return tSBFarmTaskMapper.selectByPrimaryKey(id);
 }
 public int updateByPrimaryKey(SBFarmTask record){
	 return tSBFarmTaskMapper.updateByPrimaryKey(record);
 }
}
