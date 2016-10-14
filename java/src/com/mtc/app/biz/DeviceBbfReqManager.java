/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBBbfarSercice;
import com.mtc.app.service.SBMonitorCurrSercice;
import com.mtc.app.service.SBMonitorHistSercice;
import com.mtc.entity.app.SBBbfar;
import com.mtc.entity.app.SBMonitorCurr;
import com.mtc.entity.app.SBMonitorHist;
import com.mtc.entity.app.SDFarm;

/**
 * @ClassName: DeviceBbfReqController
 * @Description: 
 * @Date 2016-1-5 上午11:43:11
 * @Author Shao Yao Yu
 * 
 */
@Component
public class DeviceBbfReqManager {

	@Autowired
	private   SBBbfarSercice tSBBbfarSercice;
	@Autowired
	private   SBMonitorHistSercice tSBMonitorHistSercice;
	@Autowired
	private   SBMonitorCurrSercice tSBMonitorCurrSercice;
	
	public int dealSaveSbbbfar(HashMap<String, Object> mParas) {
		SBBbfar tSBBbfar = (SBBbfar) mParas.get("SBBbfar");
		return tSBBbfarSercice.insertSbbbfar(tSBBbfar);
	}
	public void dealSave(HashMap<String, Object> mParas) {
		SBMonitorCurr tSBMonitorCurr = (SBMonitorCurr) mParas.get("SBMonitorCurr");
		SBMonitorHist tSBMonitorHist = (SBMonitorHist) mParas.get("SBMonitorHist");
		int HouseId = tSBMonitorCurr.getHouseId();
	    tSBMonitorCurrSercice.deleteByHouId(HouseId);
		tSBMonitorCurrSercice.insertSBMonitorCurr(tSBMonitorCurr);
		tSBMonitorHist.setId(tSBMonitorCurr.getId());
		tSBMonitorHistSercice.insertSBMonitorHist(tSBMonitorHist);
	}
}
