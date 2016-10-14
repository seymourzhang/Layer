/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SDFarm;
import com.mtc.mapper.app.SDFarmMapper;

/**
 * @ClassName: SDFarmService
 * @Description: 
 * @Date 2015年11月24日 下午1:31:24
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SDFarmService {
	@Autowired
	private SDFarmMapper tSDFarmMapper ;
	
	public int insertFarm(SDFarm tSDFarm){
		return tSDFarmMapper.insert(tSDFarm);
	}
	public int deleteByPrimaryKey(int id){
		return tSDFarmMapper.deleteByPrimaryKey(id);
	}
	public SDFarm selectByPrimaryKey(int id){
		return tSDFarmMapper.selectByPrimaryKey(id);
	}
	public int updatetFarm(SDFarm tSDFarm){
		return tSDFarmMapper.updateByPrimaryKey(tSDFarm);
	}
}

