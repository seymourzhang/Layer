/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SBBreedDetailService;
import com.mtc.app.service.SBDataInputService;
import com.mtc.app.service.SBHouseBreedService;
import com.mtc.common.util.PubFun;
import com.mtc.entity.app.SBBreedDetail;
import com.mtc.entity.app.SBDataInput;
import com.mtc.entity.app.SBHouseBreed;

/**
 * @ClassName: DataInputReqManager
 * @Description: 
 * @Date 2015-12-8 下午3:59:08
 * @Author Shao Yao Yu
 * 
 */
@Component
public class DataInputReqManager{
	
	private static Logger mLogger =Logger.getLogger(DataInputReqManager.class);
	@Autowired
	private SBBreedDetailService tSBBreedDetailService;
	@Autowired
	private SBHouseBreedService tSBHouseBreedService;
	@Autowired
	private SBDataInputService tSBDataInputService;
	@Autowired
	private BaseQueryService tBaseQueryService;
	
	public SBHouseBreed saveSBHouseBreed(HashMap<String,Object> tPara) throws ParseException{
		SBHouseBreed tSBHouseBreed = (SBHouseBreed) tPara.get("SBHouseBreed");
		List<SBBreedDetail> tSBBreedDetails = (List<SBBreedDetail>) tPara.get("SBBreedDetails");
		SBDataInput   tSBDataInput = (SBDataInput) tPara.get("SBDataInput");
		tSBHouseBreedService.insert(tSBHouseBreed);
		if(tSBBreedDetails != null && tSBBreedDetails.size()>0){
			for(int i = 0; i<tSBBreedDetails.size();i++){
				tSBBreedDetails.get(i).setHouseBreedId(tSBHouseBreed.getId());
			}
			tSBBreedDetailService.insertBatch(tSBBreedDetails);
		}
		tSBDataInput.setHouseBreedId(tSBHouseBreed.getId());
		tSBDataInputService.insert(tSBDataInput);
		return tSBHouseBreed;
	}
	public SBHouseBreed updateSBHouseBreed(HashMap<String,Object> tPara){
		Date date = new Date();
		SBHouseBreed tSBHouseBreed = (SBHouseBreed) tPara.get("SBHouseBreed");
		tSBHouseBreedService.updateByPrimaryKey(tSBHouseBreed);
		SBDataInput   tSBDataInput = (SBDataInput) tPara.get("SBDataInput");
		if(tSBDataInput.getId()==null){
			tSBDataInputService.insert(tSBDataInput);
		}else{
			tSBDataInput.setModifyDate(date);
			tSBDataInput.setModifyTime(date);
			tSBDataInput.setFreezeStatus("1");
			tSBDataInputService.updateByPrimaryKey(tSBDataInput);
			tSBDataInput.setId(null);
			tSBDataInput.setValInt(tSBHouseBreed.getPlaceNum());
			tSBDataInput.setCreateDate(date);
			tSBDataInput.setCreatePerson(tSBDataInput.getModifyPerson());
			tSBDataInput.setCreateTime(date);
			tSBDataInput.setFreezeStatus("0");
			tSBDataInputService.insert(tSBDataInput);
		}
		List<SBBreedDetail> tSBBreedDetail = (List<SBBreedDetail>)tPara.get("List<SBBreedDetail>");
		for (SBBreedDetail sbBreedDetail : tSBBreedDetail) {
			tSBBreedDetailService.updateByPrimaryKey(sbBreedDetail);
		}
		return tSBHouseBreed;
	}
	public void saveDR(HashMap<String,Object> tPara){
		 ArrayList<SBBreedDetail>  listSBBreedDetail = ( ArrayList<SBBreedDetail> ) tPara.get("listSBBreedDetail");
		 int  HouseBreedId = (int) tPara.get("HouseBreedId");
		 int  modifyPerson = (int)tPara.get("modifyPerson");
		 Date date = new Date(); 
		 SBHouseBreed tSBHouseBreed = tSBHouseBreedService.selectByPrimaryKey(HouseBreedId);
		 int farmBreedId = tSBHouseBreed.getFarmBreedId();
		 int farmId = tSBHouseBreed.getFarmId();
		 int houseId = tSBHouseBreed.getHouseId();
		 List<SBBreedDetail> tSBBreedDetail = tSBBreedDetailService.selectByhouseBreedId(HouseBreedId);
		 int i = 0; 
		 Collections.sort(tSBBreedDetail,new  Comparator<SBBreedDetail>() {
				@Override
				public int compare(SBBreedDetail arg0, SBBreedDetail arg1) {
					 return arg0.getAge().compareTo(arg1.getAge()); 
				}
				});
		 Collections.sort(listSBBreedDetail,new  Comparator<SBBreedDetail>() {
			@Override
			public int compare(SBBreedDetail arg0, SBBreedDetail arg1) {
				 return arg0.getAge().compareTo(arg1.getAge()); 
			}
			});
		 int yest_acc_cd = 0;
		 BigDecimal yest_acc_feed = new BigDecimal(0);
		 BigDecimal yest_acc_water = new BigDecimal(0);
		 for (SBBreedDetail sbBreedDetail : listSBBreedDetail) {
			 int Age = sbBreedDetail.getAge();
			 int death_am = sbBreedDetail.getDeathAm();
			 if(death_am!=tSBBreedDetail.get(i).getDeathAm()){
				 SBDataInput tSBDataInput = tSBDataInputService.selectBySBHouseBreedId(HouseBreedId, "D0001", Age);
				 if(tSBDataInput==null){
				  SBDataInput mSBDataInput = new  SBDataInput();
				  mSBDataInput.setAge(Age);
				  mSBDataInput.setDataType("D0001");
				  mSBDataInput.setFarmBreedId(farmBreedId);
				  mSBDataInput.setHouseBreedId(HouseBreedId);
				  mSBDataInput.setFarmId(farmId);
				  mSBDataInput.setHouseId(houseId);
				  mSBDataInput.setValueType("01");
				  mSBDataInput.setValInt(death_am);
				  mSBDataInput.setFreezeStatus("0");
				  mSBDataInput.setCreateDate(date);
				  mSBDataInput.setCreatePerson(modifyPerson);
				  mSBDataInput.setCreateTime(date);
				  mSBDataInput.setModifyDate(date);
				  mSBDataInput.setModifyTime(date);
				  mSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInputService.insert(mSBDataInput);
				 }else{
				  tSBDataInput.setModifyDate(date);
				  tSBDataInput.setModifyTime(date);	 
				  tSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInput.setFreezeStatus("1"); 
				  tSBDataInputService.updateByPrimaryKey(tSBDataInput);
				  tSBDataInput.setId(null);
				  tSBDataInput.setCreatePerson(modifyPerson);
				  tSBDataInput.setCreateTime(date);
				  tSBDataInput.setCreateDate(date);
				  tSBDataInput.setValInt(death_am);
				  tSBDataInput.setFreezeStatus("0");
				  tSBDataInputService.insert(tSBDataInput);
				 }
			 }
			 int death_pm = sbBreedDetail.getDeathPm();
			 if(death_pm!=tSBBreedDetail.get(i).getDeathPm()){
				 SBDataInput tSBDataInput = tSBDataInputService.selectBySBHouseBreedId(HouseBreedId, "D0002", Age);
				 if(tSBDataInput==null){
				  SBDataInput mSBDataInput = new  SBDataInput();
				  mSBDataInput.setAge(Age);
				  mSBDataInput.setDataType("D0002");
				  mSBDataInput.setFarmBreedId(farmBreedId);
				  mSBDataInput.setHouseBreedId(HouseBreedId);
				  mSBDataInput.setFarmId(farmId);
				  mSBDataInput.setHouseId(houseId);
				  mSBDataInput.setValueType("01");
				  mSBDataInput.setValInt(death_pm);
				  mSBDataInput.setFreezeStatus("0");
				  mSBDataInput.setCreateDate(date);
				  mSBDataInput.setCreatePerson(modifyPerson);
				  mSBDataInput.setCreateTime(date);
				  mSBDataInput.setModifyDate(date);
				  mSBDataInput.setModifyTime(date);
				  mSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInputService.insert(mSBDataInput);
				 }else{
				  tSBDataInput.setModifyDate(date);
				  tSBDataInput.setModifyTime(date);	 
				  tSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInput.setFreezeStatus("1"); 
				  tSBDataInputService.updateByPrimaryKey(tSBDataInput);
				  tSBDataInput.setId(null);
				  tSBDataInput.setCreatePerson(modifyPerson);
				  tSBDataInput.setCreateTime(date);
				  tSBDataInput.setCreateDate(date);
				  tSBDataInput.setValInt(death_pm);
				  tSBDataInput.setFreezeStatus("0");
				  tSBDataInputService.insert(tSBDataInput);
				 }
			 }
			 int culling_am = sbBreedDetail.getCullingAm();
			 if(culling_am!=tSBBreedDetail.get(i).getCullingAm()){
				 SBDataInput tSBDataInput = tSBDataInputService.selectBySBHouseBreedId(HouseBreedId, "D0003", Age);
				 if(tSBDataInput==null){
				  SBDataInput mSBDataInput = new  SBDataInput();
				  mSBDataInput.setAge(Age);
				  mSBDataInput.setDataType("D0003");
				  mSBDataInput.setFarmBreedId(farmBreedId);
				  mSBDataInput.setHouseBreedId(HouseBreedId);
				  mSBDataInput.setFarmId(farmId);
				  mSBDataInput.setHouseId(houseId);
				  mSBDataInput.setValueType("01");
				  mSBDataInput.setValInt(culling_am);
				  mSBDataInput.setFreezeStatus("0");
				  mSBDataInput.setCreateDate(date);
				  mSBDataInput.setCreatePerson(modifyPerson);
				  mSBDataInput.setCreateTime(date);
				  mSBDataInput.setModifyDate(date);
				  mSBDataInput.setModifyTime(date);
				  mSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInputService.insert(mSBDataInput);
				 }else{
				  tSBDataInput.setModifyDate(date);
				  tSBDataInput.setModifyTime(date);	 
				  tSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInput.setFreezeStatus("1");
				  tSBDataInputService.updateByPrimaryKey(tSBDataInput);
				  tSBDataInput.setId(null);
				  tSBDataInput.setCreatePerson(modifyPerson);
				  tSBDataInput.setCreateTime(date);
				  tSBDataInput.setCreateDate(date);
				  tSBDataInput.setValInt(culling_am);
				  tSBDataInput.setFreezeStatus("0");
				  tSBDataInputService.insert(tSBDataInput);
				 } 
			 }
			 int culling_pm = sbBreedDetail.getCullingPm();
			 if(culling_pm!=tSBBreedDetail.get(i).getCullingPm()){
				 SBDataInput tSBDataInput = tSBDataInputService.selectBySBHouseBreedId(HouseBreedId, "D0004", Age);
				 if(tSBDataInput==null){
				  SBDataInput mSBDataInput = new  SBDataInput();
				  mSBDataInput.setAge(Age);
				  mSBDataInput.setDataType("D0004");
				  mSBDataInput.setFarmBreedId(farmBreedId);
				  mSBDataInput.setHouseBreedId(HouseBreedId);
				  mSBDataInput.setFarmId(farmId);
				  mSBDataInput.setHouseId(houseId);
				  mSBDataInput.setValueType("01");
				  mSBDataInput.setValInt(culling_pm);
				  mSBDataInput.setFreezeStatus("0");
				  mSBDataInput.setCreateDate(date);
				  mSBDataInput.setCreatePerson(modifyPerson);
				  mSBDataInput.setCreateTime(date);
				  mSBDataInput.setModifyDate(date);
				  mSBDataInput.setModifyTime(date);
				  mSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInputService.insert(mSBDataInput);
				 }else{
				  tSBDataInput.setModifyDate(date);
				  tSBDataInput.setModifyTime(date);	 
				  tSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInput.setFreezeStatus("1");
				  tSBDataInputService.updateByPrimaryKey(tSBDataInput);
				  tSBDataInput.setId(null);
				  tSBDataInput.setCreatePerson(modifyPerson);
				  tSBDataInput.setCreateTime(date);
				  tSBDataInput.setCreateDate(date);
				  tSBDataInput.setValInt(culling_pm);
				  tSBDataInput.setFreezeStatus("0");
				  tSBDataInputService.insert(tSBDataInput);
			     }
			 }
			 BigDecimal daily_weight = sbBreedDetail.getCurWeight();
			 BigDecimal daily_weight2  = PubFun.getBigDecimalData(tSBBreedDetail.get(i).getCurWeight().toString());
			 if(!daily_weight.equals(daily_weight2)){
				 SBDataInput tSBDataInput = tSBDataInputService.selectBySBHouseBreedId(HouseBreedId, "D0005", Age);
				 if(tSBDataInput==null){
				  SBDataInput mSBDataInput = new  SBDataInput();
				  mSBDataInput.setAge(Age);
				  mSBDataInput.setDataType("D0005");
				  mSBDataInput.setFarmBreedId(farmBreedId);
				  mSBDataInput.setHouseBreedId(HouseBreedId);
				  mSBDataInput.setFarmId(farmId);
				  mSBDataInput.setHouseId(houseId);
				  mSBDataInput.setValueType("02");
				  mSBDataInput.setValNum(daily_weight);
				  mSBDataInput.setFreezeStatus("0");
				  mSBDataInput.setCreateDate(date);
				  mSBDataInput.setCreatePerson(modifyPerson);
				  mSBDataInput.setCreateTime(date);
				  mSBDataInput.setModifyDate(date);
				  mSBDataInput.setModifyTime(date);
				  mSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInputService.insert(mSBDataInput);
				 }else{
				  tSBDataInput.setModifyDate(date);
				  tSBDataInput.setModifyTime(date);	 
				  tSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInput.setFreezeStatus("1");
				  tSBDataInputService.updateByPrimaryKey(tSBDataInput);
				  tSBDataInput.setId(null);
				  tSBDataInput.setCreatePerson(modifyPerson);
				  tSBDataInput.setCreateTime(date);
				  tSBDataInput.setCreateDate(date);
				  tSBDataInput.setValNum(daily_weight);
				  tSBDataInput.setFreezeStatus("0");
				  tSBDataInputService.insert(tSBDataInput); 
			     }
			 }
			 BigDecimal daily_feed  = sbBreedDetail.getCurFeed();
			 BigDecimal daily_feed2  = PubFun.getBigDecimalData(tSBBreedDetail.get(i).getCurFeed().toString());
			 if(!daily_feed.equals(daily_feed2)){
				 SBDataInput tSBDataInput = tSBDataInputService.selectBySBHouseBreedId(HouseBreedId, "D0006", Age);
				 if(tSBDataInput==null){
				  SBDataInput mSBDataInput = new  SBDataInput();
				  mSBDataInput.setAge(Age);
				  mSBDataInput.setDataType("D0006");
				  mSBDataInput.setFarmBreedId(farmBreedId);
				  mSBDataInput.setHouseBreedId(HouseBreedId);
				  mSBDataInput.setFarmId(farmId);
				  mSBDataInput.setHouseId(houseId);
				  mSBDataInput.setValueType("02");
				  mSBDataInput.setValNum(daily_feed);
				  mSBDataInput.setFreezeStatus("0");
				  mSBDataInput.setCreateDate(date);
				  mSBDataInput.setCreatePerson(modifyPerson);
				  mSBDataInput.setCreateTime(date);
				  mSBDataInput.setModifyDate(date);
				  mSBDataInput.setModifyTime(date);
				  mSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInputService.insert(mSBDataInput);
				 }else{
				  tSBDataInput.setModifyDate(date);
				  tSBDataInput.setModifyTime(date);	 
				  tSBDataInput.setModifyPerson(modifyPerson);
				  tSBDataInput.setFreezeStatus("1");
				  tSBDataInputService.updateByPrimaryKey(tSBDataInput);
				  tSBDataInput.setId(null);
				  tSBDataInput.setCreatePerson(modifyPerson);
				  tSBDataInput.setCreateTime(date);
				  tSBDataInput.setCreateDate(date);
				  tSBDataInput.setValNum(daily_feed);
				  tSBDataInput.setFreezeStatus("0");
				  tSBDataInputService.insert(tSBDataInput); 
			     }
			 }
			 i++;
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			 String str=sdf.format(date);  
			 String SQL = "update s_b_breed_detail a set a.modify_date='"+str+"',a.modify_time='"+str+"',a.modify_person="+modifyPerson
					 		+",a.death_am = "+death_am+",a.death_pm = "+death_pm+
					 		",a.culling_am = "+culling_am+",a.culling_pm = "+culling_pm
					 		+" ,a.cur_cd = (a.death_am+a.death_pm+a.culling_am+a.culling_pm),a.acc_cd = a.cur_cd + "+yest_acc_cd
					 		+", a.cur_feed =" +daily_feed+",a.acc_feed = a.cur_feed + "+yest_acc_feed
					 		+", a.num_bak1 =" +sbBreedDetail.getNumBak1()+",a.num_bak2 = a.num_bak1 + "+yest_acc_water
					 		+", a.cur_weight = "+daily_weight
					 		+", a.ytd_amount = (select b.place_num - "+yest_acc_cd+" from s_b_house_breed b where a.house_breed_id = b.id)"
					 		+", a.cur_amount = (a.ytd_amount - a.cur_cd) "
					 		+ "where a.house_breed_id ="+HouseBreedId+" and a.age = "+Age+";";
			 tBaseQueryService.updateIntergerByAny(SQL);
			 yest_acc_cd = (yest_acc_cd+death_am + death_pm + culling_am + culling_pm);
			 yest_acc_feed = yest_acc_feed.add(daily_feed);
			 yest_acc_water = yest_acc_water.add(sbBreedDetail.getNumBak1());
		 }
	}
	public void updateChickSettle(HashMap<String,Object> tPara){
		
		SBHouseBreed tSBHouseBreed = (SBHouseBreed) tPara.get("SBHouseBreed");
		SBDataInput tSBDataInput = (SBDataInput) tPara.get("SBDataInput");
		
		int moveoutNum = (int) tPara.get("moveoutNum");
		tSBHouseBreedService.updateByPrimaryKey(tSBHouseBreed);
		
		tSBDataInput.setValInt(moveoutNum);
		tSBDataInput.setValNum(null);
		tSBDataInput.setValDate(null);
		tSBDataInput.setValueType("01");
		tSBDataInputService.insert(tSBDataInput);
	}
}
   

