/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SLAlarmRequest;
import com.mtc.mapper.app.SLAlarmRequestMapper;

/**
 * @ClassName: AppDeviceService
 * @Description: 
 * @Date 2016年1月14日 下午4:16:26
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class AppDeviceService {
	
	@Autowired
	private SLAlarmRequestMapper mSLAlarmRequestMapper;
	
	public Integer insertLog(SLAlarmRequest tSLAlarmRequest){
    	return mSLAlarmRequestMapper.insert(tSLAlarmRequest);
    };
}
