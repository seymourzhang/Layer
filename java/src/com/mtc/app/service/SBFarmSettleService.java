/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBFarmSettle;
import com.mtc.mapper.app.SBFarmSettleMapper;
import com.mtc.mapper.app.SBFarmSettleMapperCustom;

/**
 * @ClassName: SBFarmSettleService
 * @Description: 
 * @Date 2015-12-4 下午5:05:46
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBFarmSettleService {
	
	@Autowired
	private  SBFarmSettleMapperCustom tSBFarmSettleMapperCustom;
	@Autowired
	private  SBFarmSettleMapper tSBFarmSettleMapper;
	
	public List<SBFarmSettle> selectSettleByFarm(int FarmBreedId) {
		 return tSBFarmSettleMapperCustom.selectFarmSettle(FarmBreedId);
	}
	public int Listinsert(List<SBFarmSettle> tList) {
		 return tSBFarmSettleMapperCustom.Listinsert(tList);
	}
	public int deleteByPrimaryKey(int id) {
		 return tSBFarmSettleMapper.deleteByPrimaryKey(id);
	}
}
