/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBMonitorCurr;
import com.mtc.mapper.app.SBMonitorCurrMapper;
import com.mtc.mapper.app.SBMonitorCurrMapperCustom;

/**
 * @ClassName: SBMonitorCurrSercice
 * @Description: 
 * @Date 2016-1-6 下午3:02:08
 * @Author Shao Yao Yu
 * 
 */
@Service
public class SBMonitorCurrSercice {

	@Autowired
	public SBMonitorCurrMapper   tSBMonitorCurrMapper;
	@Autowired
	public SBMonitorCurrMapperCustom   tSBMonitorCurrMapperCustom;

	public void deleteByHouId(int id) {
		tSBMonitorCurrMapperCustom.deleteByHouId(id);
	}
	public void insertSBMonitorCurr(SBMonitorCurr tSBMonitorCurr) {
		tSBMonitorCurrMapper.insert(tSBMonitorCurr);
	}
}
