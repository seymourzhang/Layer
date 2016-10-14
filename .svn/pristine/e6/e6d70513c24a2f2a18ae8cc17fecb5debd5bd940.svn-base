package com.mtc.app.biz;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBFarmTaskService;
import com.mtc.entity.app.SBFarmTask;

@Component
public class TaskTemplateReqManager {
	private static Logger mLogger =Logger.getLogger(AlarmReqManager.class);
	@Autowired
	private SBFarmTaskService tSBFarmTaskService;

	public int addTsk(HashMap<String,Object> tPara ){
		SBFarmTask tSBFarmTask = (SBFarmTask) tPara.get("SBFarmTask");
		tSBFarmTaskService.addSBFarmTask(tSBFarmTask);
		return  tSBFarmTask.getId();
	}
	public int  updateTsk(HashMap<String,Object> tPara ){
		SBFarmTask tSBFarmTask = (SBFarmTask) tPara.get("SBFarmTask");
		tSBFarmTaskService.updateByPrimaryKey(tSBFarmTask);
		return  tSBFarmTask.getId();
	}
}
