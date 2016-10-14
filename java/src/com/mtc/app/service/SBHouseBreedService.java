/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBHouseBreed;
import com.mtc.mapper.app.SBHouseBreedMapper;
import com.mtc.mapper.app.SBHouseBreedMapperCustom;

/**
 * @ClassName: sbhousebreedService
 * @Description: 
 * @Date 2015-12-4 下午2:17:10
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBHouseBreedService {
   @Autowired
   private  SBHouseBreedMapperCustom tSBHouseBreedMapperCustom;
   @Autowired
   private  SBHouseBreedMapper tSBHouseBreedMapper;
   
   public List<SBHouseBreed> selectBySBFarmBeedId(int id){
	   return tSBHouseBreedMapperCustom.selectBySBFarmBeedId(id);
   }
   public int insert(SBHouseBreed record){
	   return tSBHouseBreedMapper.insert(record);
   }
   public int updateByPrimaryKey(SBHouseBreed record){
	   return tSBHouseBreedMapper.updateByPrimaryKey(record);
   }
   public SBHouseBreed selectByPrimaryKey(int id){
	   return tSBHouseBreedMapper.selectByPrimaryKey(id);
   }

   public SBHouseBreed selectByFarmBreedId(int farmBreedId, int houseId) {
	   
	   return tSBHouseBreedMapperCustom.selectBySBFarmBeedHouseId(farmBreedId,houseId);
   }
}
