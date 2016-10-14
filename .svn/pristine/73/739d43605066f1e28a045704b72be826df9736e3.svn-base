/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBMonitorHist;
import com.mtc.mapper.app.SBMonitorHistMapper;

/**
 * @ClassName: SBMonitorHistSercice
 * @Description: 
 * @Date 2016-1-6 下午3:01:53
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBMonitorHistSercice {

	@Autowired
	public SBMonitorHistMapper   tSBMonitorHistMapper;
	
	public void insertSBMonitorHist(SBMonitorHist tSBMonitorHist) {
		tSBMonitorHistMapper.insert(tSBMonitorHist);
	}
}
