package com.mtc.mapper.app;

import java.util.List;

import com.mtc.entity.app.SDUser;

public interface SDUserMapperCustom {
   
	SDUser selectByUserCode(String userCode);
	
	List<SDUser> selectByFarmer(int farmerId);
	
	SDUser selectValidByPrimaryKey(Integer id);
}