package com.mtc.app.biz;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBLayerFarmSettleService;
import com.mtc.entity.app.SBLayerFarmSettle;
@Component
public class LayerBreedSettleReqManager {
	private static Logger mLogger =Logger.getLogger(LayerBreedSettleReqManager.class);
	@Autowired
	private  SBLayerFarmSettleService tSBLayerFarmSettleService;
	public void monthSettleSave(HashMap<String,Object> tPara){
		List<SBLayerFarmSettle> tSBLayerFarmSettle = (List<SBLayerFarmSettle>) tPara.get("listSBLayerFarmSettle");
		tSBLayerFarmSettleService.deletebatch(tSBLayerFarmSettle.get(0).getFarmBreedId(), tSBLayerFarmSettle.get(0).getSettleMonth());
		tSBLayerFarmSettleService.insertBatch(tSBLayerFarmSettle);
		mLogger.info("更新批次成功");		
	}
	public void pulletSettleSave(HashMap<String,Object> tPara){
		SBLayerFarmSettle tSBLayerFarmSettle = (SBLayerFarmSettle) tPara.get("SBLayerFarmSettle");
		tSBLayerFarmSettleService.deleteByFarmBreedIdAndfeeCode(tSBLayerFarmSettle.getFarmBreedId(),tSBLayerFarmSettle.getFeeCode()); 
		tSBLayerFarmSettleService.insert(tSBLayerFarmSettle);
		mLogger.info("青年鸡身价摊销保存成功");		
	}
}
