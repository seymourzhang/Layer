/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBChickenStandar;
import com.mtc.mapper.app.SBChickenStandarMapperCustom;

/**
 * @ClassName: SBChickenStandarService
 * @Description: 
 * @Date 2016年2月19日 下午1:10:39
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SBChickenStandarService {
	@Autowired
	private SBChickenStandarMapperCustom tSBChickenStandarMapperCustom ;
	
	public List<SBChickenStandar> selectStandarInfoByFeedType(String feedType){
		return tSBChickenStandarMapperCustom.selectStandarInfoByFeedType(feedType);
	}
}
