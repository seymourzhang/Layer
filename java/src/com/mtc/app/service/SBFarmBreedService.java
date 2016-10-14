/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBFarmBreed;
import com.mtc.mapper.app.SBFarmBreedMapper;

/**
 * @ClassName: Service
 * @Description: 
 * @Date 2015-12-4 下午5:05:46
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBFarmBreedService {
	
	@Autowired
	private  SBFarmBreedMapper tSBFarmBreedMapper;

	public int updateByPrimaryKey(SBFarmBreed tSBFarmBreed) {
		
		 return tSBFarmBreedMapper.updateByPrimaryKey(tSBFarmBreed);
	}

	public int insertSBFarmBreed(SBFarmBreed tSBFarmBreed) {
		 return tSBFarmBreedMapper.insert(tSBFarmBreed);
		
	}
	public SBFarmBreed selectByPrimaryKey(int id) {
		
		 return tSBFarmBreedMapper.selectByPrimaryKey(id);
	}
}
