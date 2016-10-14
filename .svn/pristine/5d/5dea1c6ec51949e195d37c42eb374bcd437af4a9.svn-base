/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBHouseProbe;
import com.mtc.mapper.app.SBHouseProbeMapper;
import com.mtc.mapper.app.SBHouseProbeMapperCustom;

/**
 * @ClassName: SBHouseProbeService
 * @Description: 
 * @Date 2016-1-22 下午2:45:51
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBHouseProbeService {
	@Autowired
	public SBHouseProbeMapper   tSBHouseProbeMapper;
	@Autowired
	public SBHouseProbeMapperCustom   tSBHouseProbeMapperCustom;
	public int insertSBHouseProbe(SBHouseProbe tSBHouseProbe){
	   return tSBHouseProbeMapper.insert(tSBHouseProbe);
	}
	public int deleteByPrimaryKeySBHouseProbe(SBHouseProbe tSBHouseProbe){
		   return tSBHouseProbeMapper.deleteByPrimaryKey(tSBHouseProbe.getFarmId(),tSBHouseProbe.getHouseId(), tSBHouseProbe.getProbeCode());
		}
	public List<SBHouseProbe> selectByFarmIdHouseId(int FarmId,int HouseId){
		   return tSBHouseProbeMapperCustom.selectByfarmIdandhouseId(FarmId,HouseId);
		}
}
