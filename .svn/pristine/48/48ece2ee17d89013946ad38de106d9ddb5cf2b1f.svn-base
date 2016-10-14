/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SDTtsTempParam;
import com.mtc.mapper.app.SDTtsTempParamMapper;
import com.mtc.mapper.app.SDTtsTempParamMapperCustom;

/**
 * 
 * @author lx
 *
 */
@Service
public class SDTTSTemplateParamService {
	
	@Autowired
	private SDTtsTempParamMapper tempParamMapper;
	@Autowired
	private SDTtsTempParamMapperCustom paramMapperCustom;
	
	public List<SDTtsTempParam> getTTSTempParams(String tempId) {
		// TODO Auto-generated method stub
		return paramMapperCustom.getTTSTempParams(tempId);
	}
	
}









