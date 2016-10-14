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
import com.mtc.app.service.SLAlidayuLogService;
import com.mtc.entity.app.SDTtsTempParam;
import com.mtc.entity.app.SDTtsTemplate;
import com.mtc.entity.app.SLDayuTtsLog;


@Component
public class AlidayuLogManager {
	
	private static Logger mLogger =Logger.getLogger(AlidayuLogManager.class);
	
	@Autowired
	private SLAlidayuLogService logService;

	public int updateByBizId(SLDayuTtsLog tts) {
		// TODO Auto-generated method stub
		return logService.updateByBizId(tts);
	}

	public int insert(SLDayuTtsLog tts) {
		// TODO Auto-generated method stub
		return logService.insert(tts);
	}

	public SLDayuTtsLog getSLDayuTtsLogByBizId(String bizId) {
		// TODO Auto-generated method stub
		return logService.getSLDayuTtsLogByBizId(bizId);
	}
	
}

















