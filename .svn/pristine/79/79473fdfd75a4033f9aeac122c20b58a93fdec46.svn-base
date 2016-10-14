package com.mtc.app.biz;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBLayerFarmBreedService;
import com.mtc.app.service.SBLayerBreedDetailService;
import com.mtc.app.service.SBLayerHouseBreedService;
import com.mtc.app.service.SBEggSellsService;
import com.mtc.common.util.PubFun;
import com.mtc.entity.app.SBEggSells;
import com.mtc.entity.app.SBLayerBreedDetail;
import com.mtc.entity.app.SBLayerFarmBreed;
import com.mtc.entity.app.SBLayerHouseBreed;

@Component
public class LayerBreedBatchReqManager {
	private static Logger mLogger =Logger.getLogger(LayerBreedBatchReqManager.class);
	@Autowired
	private SBLayerFarmBreedService tSBFarmBreedService;
	@Autowired
	private SBLayerHouseBreedService tSBLayerHouseBreedService;
	@Autowired
	private SBLayerBreedDetailService tSBLayerBreedDetailService;
	@Autowired
	private SBEggSellsService tSBEggSellsService;
	
	private String tCalType = "02";

	public int createBatch(HashMap<String, Object> tt) throws JSONException,
			ParseException {
		JSONArray lSBLayerHouseBreed = (JSONArray) tt.get("SBLayerHouseBreed");
		SBLayerFarmBreed tSBLayerFarmBreed = (SBLayerFarmBreed) tt
				.get("SBLayerFarmBreed");
		tSBFarmBreedService.insert(tSBLayerFarmBreed);
		List<SBLayerBreedDetail> tJSONArray = new ArrayList<SBLayerBreedDetail>();
		List<SBEggSells> tSBEggSellsJSONArray = new ArrayList<SBEggSells>();
		SBLayerBreedDetail ySBLayerBreedDetail = (SBLayerBreedDetail) tt
				.get("SBLayerBreedDetail");
		BigDecimal tBigDecimal = new BigDecimal(0);
		for (int i = 0; i < lSBLayerHouseBreed.length(); i++) {
			SBLayerHouseBreed lsbhousebreed = (SBLayerHouseBreed) lSBLayerHouseBreed
					.get(i);
			lsbhousebreed.setFarmBreedId(tSBLayerFarmBreed.getId());
			tSBLayerHouseBreedService.insert(lsbhousebreed);

			// 当前日期、当前日龄以及当前周龄
			Date nowDate = new Date();
			int nowAge = PubFun.daysBetween(tSBLayerFarmBreed.getPlaceDate(),nowDate) + ySBLayerBreedDetail.getDayAge();
			int nowWeekAge = PubFun.getWeekAge(tCalType, nowAge, nowDate);
			SBLayerBreedDetail tSBLayerBreedDetail = null;
			int m = 0;
			for (int j = 0; j < 800; j++) {
				m++;
				// 每次循环所对应的日期、日龄以及周龄
				Date dealdate = PubFun.addDate(
						tSBLayerFarmBreed.getPlaceDate(), j);
				int dealAge = ySBLayerBreedDetail.getDayAge() + j;
				int dealWeekAge = PubFun.getWeekAge(tCalType, dealAge, dealdate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(dealdate);
				int dealDayOFWeek = cal.get(Calendar.DAY_OF_WEEK);
				// 1、当前周龄 大于 处理的周龄时，需要插入一条数据
				// 2、当前周龄 小于等于 处理的周龄时，需要每天插入一条数据
				if ((nowWeekAge > dealWeekAge && m >= 4 && dealDayOFWeek == 7)
						|| nowWeekAge <= dealWeekAge) {
					tSBLayerBreedDetail = new SBLayerBreedDetail();
					tSBLayerBreedDetail.setGrowthDate(dealdate);
					tSBLayerBreedDetail.setHouseBreedId(lsbhousebreed.getId());
					tSBLayerBreedDetail.setDayAge(dealAge);
					tSBLayerBreedDetail.setWeekAge(dealWeekAge);
					tSBLayerBreedDetail
							.setIsHistory(nowWeekAge > dealWeekAge ? "Y" : "N");
					tSBLayerBreedDetail.setCreateDate(ySBLayerBreedDetail
							.getCreateDate());
					tSBLayerBreedDetail.setCreatePerson(ySBLayerBreedDetail
							.getCreatePerson());
					tSBLayerBreedDetail.setCreateTime(ySBLayerBreedDetail
							.getCreateDate());
					tSBLayerBreedDetail.setDeathAm(0);
					tSBLayerBreedDetail.setDeathPm(0);
					tSBLayerBreedDetail.setCullingAm(0);
					tSBLayerBreedDetail.setCullingPm(0);
					tSBLayerBreedDetail.setCurCd(0);
					tSBLayerBreedDetail.setAccCd(0);
					tSBLayerBreedDetail.setCurFeed(tBigDecimal);
					tSBLayerBreedDetail.setAccFeed(tBigDecimal);
					tSBLayerBreedDetail.setCurWeight(tBigDecimal);
					tSBLayerBreedDetail.setCurAmount(lsbhousebreed
							.getPlaceNum());
					tSBLayerBreedDetail.setYtdAmount(lsbhousebreed
							.getPlaceNum());
					tSBLayerBreedDetail.setCurWater(tBigDecimal);
					tSBLayerBreedDetail.setAccWater(tBigDecimal);
					tSBLayerBreedDetail.setCurLayNum(0);
					tSBLayerBreedDetail.setAccLayNum(0);
					tSBLayerBreedDetail.setCurLayWeight(tBigDecimal);
					tSBLayerBreedDetail.setAccLayWeight(tBigDecimal);
					tSBLayerBreedDetail.setCurLayBroken(0);
					tSBLayerBreedDetail.setAccLayBroken(0);
					tSBLayerBreedDetail.setCreateDate(ySBLayerBreedDetail
							.getCreateDate());
					tSBLayerBreedDetail.setCreatePerson(ySBLayerBreedDetail
							.getCreatePerson());
					tSBLayerBreedDetail.setCreateTime(ySBLayerBreedDetail
							.getCreateDate());
					tSBLayerBreedDetail.setModifyDate(ySBLayerBreedDetail
							.getCreateDate());
					tSBLayerBreedDetail.setModifyTime(ySBLayerBreedDetail
							.getCreateDate());
					tSBLayerBreedDetail.setModifyPerson(ySBLayerBreedDetail
							.getCreatePerson());
					tJSONArray.add(tSBLayerBreedDetail);
				}
				if(i == 0){
					SBEggSells NSBEggSells = new SBEggSells();
					NSBEggSells.setFarmId(tSBLayerFarmBreed.getFarmId());
					NSBEggSells.setFarmBreedId(tSBLayerFarmBreed.getId());
					NSBEggSells.setSellDate(dealdate);
					NSBEggSells.setIsHistory("N");
					
					NSBEggSells.setGoodBoxSize(tBigDecimal);
					NSBEggSells.setGoodPriceType("01");
					NSBEggSells.setGoodPriceValue(tBigDecimal);
					NSBEggSells.setGoodSaleboxNum(tBigDecimal);
					NSBEggSells.setGoodSaleWeight(tBigDecimal);
					NSBEggSells.setBrokenBoxSize(tBigDecimal);
					NSBEggSells.setBrokenPriceType("01");
					NSBEggSells.setBrokenPriceValue(tBigDecimal);
					NSBEggSells.setBrokenSaleboxNum(tBigDecimal);
					NSBEggSells.setBrokenSaleWeight(tBigDecimal);
					
					NSBEggSells.setChickenManure(tBigDecimal);
					
					NSBEggSells.setWeekAge(dealWeekAge);
					NSBEggSells.setDayAge(dealAge);
					NSBEggSells.setModifyDate(ySBLayerBreedDetail
							.getModifyDate());
					NSBEggSells.setModifyPerson(ySBLayerBreedDetail
							.getModifyPerson());
					NSBEggSells.setModifyTime(ySBLayerBreedDetail
							.getModifyTime());
					NSBEggSells.setCreateDate(ySBLayerBreedDetail
							.getCreateDate());
					NSBEggSells.setCreatePerson(ySBLayerBreedDetail
							.getCreatePerson());
					NSBEggSells.setCreateTime(ySBLayerBreedDetail
							.getCreateTime());
					tSBEggSellsJSONArray.add(NSBEggSells);
				}
			}
		}
		tSBEggSellsService.insertBatch(tSBEggSellsJSONArray);
		tSBLayerBreedDetailService.insertBatch(tJSONArray);
		mLogger.info("新建批次成功");
		return tSBLayerFarmBreed.getId();
	}
}
