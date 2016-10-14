/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.app.biz.AlidayuLogManager;
import com.mtc.app.biz.AlidayuTTSManager;
import com.mtc.app.biz.RemindReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SDUserService;
import com.mtc.app.service.SLAlidayuTTSService;
import com.mtc.common.util.IPUtil;
import com.mtc.entity.app.SBCallDetail;
import com.mtc.entity.app.SBCallMain;
import com.mtc.entity.app.SDTtsTempParam;
import com.mtc.entity.app.SLDayuTtsLog;
import com.taobao.api.ApiException;

/**
 * 
 * 根据已有的报警信息进行电话拨打
 * 
 * @author lx
 *
 */
@Service
public class CallAlarmerExecuteJob{
	
	private static Logger mLogger =Logger.getLogger(CallAlarmerExecuteJob.class);
	
	@Autowired
	private BaseQueryService baseQueryService;
	
	@Autowired
	private RemindReqManager alarmReqManager;
	
	@Autowired
	private SLAlidayuTTSService ttsService;
	
	@Autowired
	private AlidayuLogManager logManager;
	
	@Autowired
	private AlidayuTTSManager alidayuTTSManager;
	
	@Autowired
	private SDUserService userService;
	
	/**
	 * 多长时间未反馈
	 * 单位分钟
	 */
	private int noResponseTime = 5;
	
	private ResourceBundle conf = null;
	public  String getPropertyValue(String keyName){
		if(conf == null){
			conf= ResourceBundle.getBundle("config/pro/alidayu");
		}
		String value= conf.getString(keyName);
		return value;
	}
	
	public void doCallAlarmers(){
		
		if(!IPUtil.needRunTask()){
			mLogger.info("本机不启用CallAlarmerExecuteJob");
			return ;
		}
		
		try {
			noResponseTime = Integer.parseInt(getPropertyValue("noResponseTime"));
		} catch (Exception e) {
			// TODO: handle exception
			mLogger.error("没有配置有效noResponseTime，noResponseTime设置为默认值5分钟",e);
			noResponseTime = 5;
		}
		
		// 获取需要报警的信息 处于新建，待返回，失败的都需要进行处理 01 02 014 
		List<SBCallMain> callInfo = alarmReqManager.getNeedCallMainInfo();
		if(callInfo != null && !callInfo.isEmpty()){
			for(SBCallMain main : callInfo){
				int mainId = main.getId();
				mLogger.info("开始处理报警信息，mainId:" + mainId + ", call result :" + main.getCallResult());
				mLogger.info("获取上次拨打结果..");
				Date lastCallTime = new Date();
				// 获取最近一次的拨打记录
				List<SBCallDetail> waitRes = alarmReqManager.getSBCallDetail(mainId);
				// 都没有进行拨打
				if(waitRes != null && !waitRes.isEmpty()){
					// 先获取上次拨打结果
					for(SBCallDetail detail : waitRes){
						mLogger.info("上次拨打结果：" + detail.getCallResult());
						// 如果上次处于待返回状态，获取反馈结果
						if(detail.getBizId() != null && detail.getCallResult().equals("02")){
							SLDayuTtsLog log = logManager.getSLDayuTtsLogByBizId(detail.getBizId());
							String callRes = "02";
							Date modify = new Date(System.currentTimeMillis());
							if(log != null){
								String statusCode = log.getStatusCode();
								lastCallTime = new Date();
								mLogger.info("获取bizId: " + detail.getBizId() + " 的拨打返回结果statusCode:" + statusCode);
								if(statusCode == null || statusCode.equals("00")){
									callRes = "02";
									lastCallTime = detail.getCallTime();
								}else if(statusCode.equals("200000")){
									// 通话成功
									callRes = "03";
									main.setSuccTime(log.getCalledTime());
								}else{
									// 通话失败
									callRes = "04";
								}
								
								detail.setCallResult(callRes);
								detail.setDayuResult(statusCode);
								detail.setResultTime(log.getModifyTime());
								detail.setModifyTime(modify);
								
							}else{
								mLogger.info("无法获取上次拨打记录，bizId:" + detail.getBizId());
							}
							main.setCallResult(callRes);
							main.setModifyTime(modify);
							mLogger.info("将拨打电话结果更新到报警信息和具体的报警人");
							alarmReqManager.updateSBCallMainAndDetail(main, detail);
							
						}else{
							mLogger.info("上次拨打结果已经获取到...");
						}
					}
				}else{
					mLogger.info("上次没有拨打记录..");
				}
				
				// 拨打未成功
				if(!main.getCallResult().equals("03")){
					
					long timeDis = System.currentTimeMillis() - lastCallTime.getTime();
					/*
					if(main.getCallResult().equals("02") && timeDis > 10*60*1000){
						mLogger.info("距离上次拨打已经超过10分钟，但仍未接到反馈结果..");
					}*/
					
					if(!main.getCallResult().equals("02") || (main.getCallResult().equals("02") && timeDis > noResponseTime*60*1000)){
						if(main.getCallResult().equals("02") && timeDis > noResponseTime*60*1000){
							mLogger.info("距离上次拨打已经超过"+noResponseTime+"分钟，但仍未接到反馈结果，继续拨打下一人..");
						}
						mLogger.info("报警信息并未处理成功，查找处于新建状态的拨打人..");
						// 获取新建的拨打人
						List<SBCallDetail> newDetail = alarmReqManager.getSBCallDetailByCallResult(mainId, "01");
						if(newDetail != null && !newDetail.isEmpty()){
							// 取第一个拨打人
							SBCallDetail detail = newDetail.get(0);
							String params = detail.getVarBak1();
							if(params == null){
								params = "{}";
							}
							// 获取拨打语音参数值信息
							List<String> paramVals = new ArrayList<>();
							
							org.json.JSONObject jsonParam;
							try {
								jsonParam = new org.json.JSONObject(params);
								Iterator<String> it = jsonParam.keys();
								while(it.hasNext()){
									paramVals.add(it.next());
								}
							} catch (JSONException e1) {
								e1.printStackTrace();
								mLogger.info("赋值参数不是json，不进行拨打~");
								continue;
							}
							
							// 获取模板参数信息
							List<SDTtsTempParam> tempParams = alidayuTTSManager.getTTSTempParams(main.getTempId());
							
							boolean balance = comparisonTempAndVal(paramVals, tempParams);
							mLogger.info(" 比较赋值参数和模板参数是否一致:" + balance);
							
							if(balance){
								
								String excep = getLastAlarmCodeName(mainId);
								
								if(excep.equals("")){
									mLogger.info("报警信息已全被处理，mainId("+mainId+")报警设置为失效..");
									main.setCallResult("05");
									main.setModifyTime(new Date());
									alarmReqManager.updateSBCallMain(main);
									
									continue;
								}
								try {
									jsonParam.put("exceptions", excep);
								} catch (JSONException e1) {
									e1.printStackTrace();
									mLogger.info("设置最新报警信息失败,电话拨打取消...");
									continue;
								}
								
								String callNum = detail.getPhoneNum();
								String tempId = main.getTempId();
								
								jsonParam.remove("name");
								params = jsonParam.toString();
								
								mLogger.info("生产的模板参数params:" + params);
								mLogger.info("开始拨打电话...");
								int count = 1;
								while(count < 4){
									mLogger.info("第" + count + "次拨打电话，号码：" + callNum);
									try {
										
										String bizId = ttsService.ttsNumSingleCell(callNum, params, tempId);
										detail.setBizId(bizId);
										Date callTime = new Date(System.currentTimeMillis());
										detail.setCallResult("02");
										detail.setCallTime(callTime);
										detail.setModifyTime(callTime);
//										detail.setVarBak1(params);
										
										main.setCallResult("02");
										main.setModifyTime(new Date());
										alarmReqManager.updateSBCallMainAndDetail(main, detail);
										mLogger.info("电话拨打成功...");
										break;
									} catch (ApiException e) {
										e.printStackTrace();
										mLogger.error("拨打电话失败",e);
										if(count == 3){
											Date callTime = new Date(System.currentTimeMillis());
											detail.setCallTime(callTime);
											detail.setDayuResult("拨打电话接口调用失败");
											alarmReqManager.updateSBCallDetail(detail);
										}
									}
									count++;
								}
							}else{
								mLogger.info("赋值参数和模板参数不一致，不进行拨打~");
							}
						}else{
							// 未成功的情况下，联系人又用光，将状态设置为失效状态
							main.setCallResult("05");
							main.setModifyTime(new Date());
							alarmReqManager.updateSBCallMain(main);
							
							mLogger.info("没有处于新建状态的报警人,mainId("+mainId+")报警设置为失效..");
						}
					}else{
						mLogger.info("还未接收到上一次的拨打反馈...本次拨打取消");
					}
				}
			}
		}else{
			mLogger.info("没有报警信息,无需拨打电话...");
		}
	}
	
	/**
	 * 获取最新的报警信息
	 * @param mainId
	 * @return
	 */
	private String getLastAlarmCodeName(int mainId){
		String sql = "SELECT co.code_name FROM s_b_call_alarm alarm , s_d_code co "
				+ "WHERE alarm.alarm_code = co.biz_code AND alarm.var_bak1 = '01' AND alarm.main_id = " + mainId;
		
		List<HashMap<String,Object>> codes = baseQueryService.selectMapByAny(sql);
		
		String alarmStr = "";
		
		if(codes != null){
			for(HashMap<String,Object> code : codes){
				alarmStr += code.get("code_name") + ",";
			}
		}
		
		if(alarmStr.length() > 0 )
		{
			if(alarmStr.length() <= 15){
				alarmStr = alarmStr.substring(0, alarmStr.length() - 1);
			}else{
				alarmStr = alarmStr.substring(0, 15);
			}
		}
		mLogger.info("最新的报警信息:" + alarmStr);
	
		return alarmStr;
	}
	
	/**
	 * 比较赋值参数和模板参数是否一致
	 * @param paramVals
	 * @param tempParams
	 * @return
	 */
	private boolean comparisonTempAndVal(List<String> paramVals,List<SDTtsTempParam> tempParams){
		if(paramVals == null && tempParams == null){
			return true;
		}
		
		if(paramVals.size() != tempParams.size()){
			return false;
		}else{
			boolean exist = false;
			for(String param : paramVals){
				boolean flag = false;
				for(SDTtsTempParam temp : tempParams){
					if(param.equals(temp.getParamName())){
						flag = true;
						break;
					}
				}
				if(flag){
					exist = true;
				}else{
					exist = false;
					break;
				}
			}
			return exist;
		}
	}
}









