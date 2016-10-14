/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.util.HashMap;
import java.util.List;

import com.mtc.entity.app.*;
import com.sun.net.httpserver.Authenticator;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBHouseAlarmService;
import com.mtc.app.service.SBHouseProbeService;
import com.mtc.app.service.SBTempSettingSubService;
import com.mtc.app.service.SBTempSettingService;
import com.mtc.app.service.SBLayerHouseAlarmService;

/**
 * @ClassName: AlarmReqManager
 * @Description: 
 * @Date 2015-11-30 下午2:35:39
 * @Author Shao Yao Yu
 * 
 */
@Component
public class AlarmReqManager {
	
	
	private static Logger mLogger =Logger.getLogger(AlarmReqManager.class);
	@Autowired
	private SBTempSettingService  mSBTempSettingService;
	@Autowired
	private SBTempSettingSubService  tSBTempSettingSubService;
	@Autowired
	private SBHouseAlarmService  tSBHouseAlarmService;
	@Autowired
	private SBHouseProbeService  tSBHouseProbeService;
	@Autowired
	private SBLayerHouseAlarmService lSBLayerHouseAlarmService;

	public boolean dealSave(HashMap<String,Object> tPara) throws Exception {
		SBLayerHouseAlarm lSBLayerHouseAlarm = (SBLayerHouseAlarm) tPara.get("SBLayerHouseAlarm");
		SBHouseProbe tSBHouseProbe = (SBHouseProbe) tPara.get("SBHouseProbe");
		JSONObject effAlarmProbe = (JSONObject) tPara.get("effAlarmProbe");
		String tempLeft1 = effAlarmProbe.getString("tempLeft1");
		String tempLeft2 = effAlarmProbe.getString("tempLeft2");
		String tempMiddle1 = effAlarmProbe.getString("tempMiddle1");
		String tempMiddle2 = effAlarmProbe.getString("tempMiddle2");
		String tempRight1 = effAlarmProbe.getString("tempRight1");
		String tempRight2 = effAlarmProbe.getString("tempRight2");

		if (tempLeft1.equals("true")) {
			tSBHouseProbe.setProbeCode("tempLeft1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		} else {
			tSBHouseProbe.setProbeCode("tempLeft1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}
		if (tempLeft2.equals("true")) {
			tSBHouseProbe.setProbeCode("tempLeft2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		} else {
			tSBHouseProbe.setProbeCode("tempLeft2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}
		if (tempMiddle1.equals("true")) {
			tSBHouseProbe.setProbeCode("tempMiddle1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		} else {
			tSBHouseProbe.setProbeCode("tempMiddle1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}
		if (tempMiddle2.equals("true")) {
			tSBHouseProbe.setProbeCode("tempMiddle2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		} else {
			tSBHouseProbe.setProbeCode("tempMiddle2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}
		if (tempRight1.equals("true")) {
			tSBHouseProbe.setProbeCode("tempRight1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		} else {
			tSBHouseProbe.setProbeCode("tempRight1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}
		if (tempRight2.equals("true")) {
			tSBHouseProbe.setProbeCode("tempRight2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		} else {
			tSBHouseProbe.setProbeCode("tempRight2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}

		int FarmId = lSBLayerHouseAlarm.getFarmId();
		int HouseId = lSBLayerHouseAlarm.getHouseId();
		SBLayerHouseAlarm llSBLayerHouseAlarm = lSBLayerHouseAlarmService.selectByPrimaryKey(FarmId, HouseId);

		if (llSBLayerHouseAlarm == null) {
			lSBLayerHouseAlarmService.insert(lSBLayerHouseAlarm);
		} else {
			lSBLayerHouseAlarm.setAlarmDelay(lSBLayerHouseAlarm.getAlarmDelay());
			lSBLayerHouseAlarm.setTempCpsation(lSBLayerHouseAlarm.getTempCpsation());
			lSBLayerHouseAlarm.setTempCordon(lSBLayerHouseAlarm.getTempCordon());
			lSBLayerHouseAlarm.setAlarmWay(lSBLayerHouseAlarm.getAlarmWay());
			lSBLayerHouseAlarm.setAlarmProbe(lSBLayerHouseAlarm.getAlarmProbe());
			lSBLayerHouseAlarm.setPointAlarm(lSBLayerHouseAlarm.getPointAlarm());
			lSBLayerHouseAlarm.setModifyDate(lSBLayerHouseAlarm.getModifyDate());
			lSBLayerHouseAlarm.setModifyPerson(lSBLayerHouseAlarm.getModifyPerson());
			lSBLayerHouseAlarm.setModifyTime(lSBLayerHouseAlarm.getModifyTime());
			lSBLayerHouseAlarmService.updateByPrimaryKey(lSBLayerHouseAlarm);
		}
		return true;
	}
}
