package com.mtc.app.biz;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SBLayerBreedDetailService;
import com.mtc.entity.app.SBLayerBreedDetail;
@Component
public class LayerDataInputReqManager {
	private static Logger mLogger =Logger.getLogger(LayerDataInputReqManager.class);
	@Autowired
	private  SBLayerBreedDetailService tSBLayerBreedDetailService;
	@Autowired
	private  BaseQueryService tQueryService;
	public void SaveDR(HashMap<String,Object> tPara){
		List<SBLayerBreedDetail> tSBLayerBreedDetail = (List<SBLayerBreedDetail>) tPara.get("SBLayerBreedDetail");
		String SQL =  tPara.get("SQL").toString();
		tSBLayerBreedDetailService.updateByhouseBreedId(tSBLayerBreedDetail);
		tQueryService.updateIntergerByAny(SQL);
		mLogger.info("更新批次成功");		
	}
	public void SaveDR_v2(HashMap<String,Object> tPara){
		SBLayerBreedDetail tSBLayerBreedDetail = (SBLayerBreedDetail) tPara.get("SBLayerBreedDetail");
		String SQL =  tPara.get("SQL").toString();
		tSBLayerBreedDetailService.updateByPrimaryKey(tSBLayerBreedDetail);
		tQueryService.updateIntergerByAny(SQL);
		mLogger.info("更新批次成功");		
	}
}
