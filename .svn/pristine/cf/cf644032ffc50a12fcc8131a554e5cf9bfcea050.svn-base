/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SDTTSTemplateParamService;
import com.mtc.app.service.SDTTSTemplateService;
import com.mtc.entity.app.SDTtsTempParam;
import com.mtc.entity.app.SDTtsTemplate;


@Component
public class AlidayuTTSManager {
	
	private static Logger mLogger =Logger.getLogger(AlidayuTTSManager.class);
	
	@Autowired
	private SDTTSTemplateService templateService;
	
	@Autowired
	private SDTTSTemplateParamService paramService;

	
	
	public SDTtsTemplate getSDTtsTemplate(String tempId){
		return templateService.getSDTtsTemplate(tempId);
	}
	
	public List<SDTtsTempParam> getTTSTempParams(String tempId){
		List<SDTtsTempParam> temps = paramService.getTTSTempParams(tempId);
		if(temps == null){
			return new ArrayList<>();
		}
		return temps;
	}
}

















