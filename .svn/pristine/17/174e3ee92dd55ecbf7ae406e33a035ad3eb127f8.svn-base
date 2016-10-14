/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBYincommData;
import com.mtc.mapper.app.SBYincommDataMapper;

/**
 * @ClassName: YincommDataService
 * @Description: 
 * @Date 2015年12月22日 下午2:40:53
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SBYincommDataService {
	@Autowired
	private SBYincommDataMapper tSBYincommDataMapper ;
	
	public int insert(SBYincommData tSBYincommData){
		return tSBYincommDataMapper.insert(tSBYincommData);
	}
}
