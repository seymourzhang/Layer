/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import com.mtc.entity.app.SLJpushLog;
import com.mtc.mapper.app.SLJpushLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SLJpushLogService
 * @Description: 
 * @Date 2016-5-20 下午18:14:02
 * @Author seymour
 * 
 */
@Service
public class SLJpushLogService {

	@Autowired
	private SLJpushLogMapper sSLJpushLogMapper;

	public int insert(SLJpushLog record){
		return sSLJpushLogMapper.insert(record);
	}
	
}
