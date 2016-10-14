/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBUserFarm;
import com.mtc.mapper.app.SBUserFarmMapper;
import com.mtc.mapper.app.SBUserFarmMapperCustom;

/**
 * @ClassName: SBUserFarmService
 * @Description: 
 * @Date 2015年12月1日 上午11:24:30
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SBUserFarmService {
	@Autowired
	private SBUserFarmMapper tSBUserFarmMapper;
	
	@Autowired
	private SBUserFarmMapperCustom tSBUserFarmMapperCustom;
	
	public int insert(SBUserFarm tSBUserFarm){
		return tSBUserFarmMapper.insert(tSBUserFarm);
	}
	public SBUserFarm selectByUserId(Integer userId){
		return tSBUserFarmMapperCustom.selectByUserId(userId);
	}
}
