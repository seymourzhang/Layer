/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SDTtsTemplate;
import com.mtc.mapper.app.SDTtsTemplateMapper;
import com.mtc.mapper.app.SDTtsTemplateMapperCustom;

/**
 * 
 * @author lx
 *
 */
@Service
public class SDTTSTemplateService {
	
	@Autowired
	private SDTtsTemplateMapper ttsTemplateMapper;
	@Autowired
	private SDTtsTemplateMapperCustom ttsTemplateMapperCustom;
	
	public SDTtsTemplate getSDTtsTemplate(String tempId) {
		// TODO Auto-generated method stub
		return ttsTemplateMapperCustom.getSDTtsTemplate(tempId);
	}
	
	
}









