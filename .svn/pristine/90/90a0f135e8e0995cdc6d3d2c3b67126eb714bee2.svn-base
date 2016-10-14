/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SDUser;
import com.mtc.mapper.app.SDUserMapper;
import com.mtc.mapper.app.SDUserMapperCustom;

/**
 * @ClassName: SDUserService
 * @Description: 
 * @Date 2015年11月20日 下午4:47:09
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SDUserService {
	
	@Autowired
	private SDUserMapper tSDUserMapper ;
	
	@Autowired
	private SDUserMapperCustom tSDUserMapperCustom ;
	
	public int insertUser(SDUser tSDUser){
		return tSDUserMapper.insert(tSDUser);
	}
	public SDUser selectValidByPrimaryKey(int id){
		return tSDUserMapperCustom.selectValidByPrimaryKey(id);
	}
	public SDUser selectByUserCode(String userCode){
		return tSDUserMapperCustom.selectByUserCode(userCode);
	}
	public List<SDUser> selectByFarmer(int farmerId){
		return tSDUserMapperCustom.selectByFarmer(farmerId);
	}
	public int updateUser(SDUser tSDUser){
		return tSDUserMapper.updateByPrimaryKey(tSDUser);
	}
}









