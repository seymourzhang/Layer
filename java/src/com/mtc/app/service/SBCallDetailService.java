/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBCallAlarm;
import com.mtc.entity.app.SBCallDetail;
import com.mtc.mapper.app.SBCallAlarmMapper;
import com.mtc.mapper.app.SBCallAlarmMapperCustom;
import com.mtc.mapper.app.SBCallDetailMapper;
import com.mtc.mapper.app.SBCallDetailMapperCustom;

/**
 * 
 * @author lx
 *
 */
@Service
public class SBCallDetailService {
	
	@Autowired
	private SBCallDetailMapper detailMapper;
	@Autowired
	private SBCallDetailMapperCustom detailMapperCustom;
	

	public int insertCallDetails(List<SBCallDetail> details) {
		// TODO Auto-generated method stub
		return detailMapperCustom.insertCallDetails(details);
	}


	public List<SBCallDetail> getSBCallDetail(int mainId) {
		// TODO Auto-generated method stub
		return detailMapperCustom.getSBCallDetail(mainId);
	}


	public int updateSBCallDetail(SBCallDetail detail) {
		// TODO Auto-generated method stub
		return detailMapperCustom.updateByPrimaryKey(detail);
	}


	public List<SBCallDetail> getSBCallDetailByCallResult(int mainId,
			String callResult) {
		// TODO Auto-generated method stub
		return detailMapperCustom.getSBCallDetailByCallResult(mainId,callResult);
	}
}









