/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mtc.app.service.SDUserService;
import com.mtc.common.util.PubFun;
import com.mtc.entity.app.SDUser;
import com.mtc.push.api.MiPushUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.MySQLSPService;
import com.mtc.app.service.SLJpushLogService;
import com.mtc.common.util.IPUtil;
import com.mtc.entity.app.SLJpushLog;
import com.mtc.push.api.JPushUtil;

/**
 * @ClassName: AlarmDealExecuteJob
 * @Description: 
 * @Date 2015年11月30日 上午11:24:32
 * @Author Yin Guo Xiang
 * 
 */

public class JPushDealExecuteJob {
	private static Logger mLogger =Logger.getLogger(JPushDealExecuteJob.class);
	
	@Autowired
	private MySQLSPService tMySQLSPService;
	@Autowired
	private BaseQueryService tBaseQueryService;
	@Autowired
	private SLJpushLogService sSLJpushLogService;
	@Autowired
	private SDUserService sSDUserService;

	private static int counter = 0;

	public void Service() {
		if (!IPUtil.needRunTask()) {
			return;
		}
		counter++;
		mLogger.info("=====Now 开始  第 " + counter + " 次 JPushDealExecuteJob.Service");
		try {
			Object alarm_time = new Object();
			String content;
			Object houseId;
			Object alarmId;
			Object alarmName;
			Object houseName;
			List alias = new ArrayList();
			HashMap<String,String> contents = null;
			String SQL = "SELECT * FROM s_b_alarm_inco a " +
					"LEFT JOIN s_d_house b ON a.house_id = b.id " +
					"WHERE deal_status = '01' AND alarm_code <> 'null' ";
			mLogger.info("JPushDealExecuteJob.Service.SQL = " + SQL);
			JPushUtil jPushUtil = new JPushUtil();
			MiPushUtil miPushUtil = new MiPushUtil();
			List<HashMap<String, Object>> historyDatas = tBaseQueryService.selectMapByAny(SQL);
			SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			if (historyDatas.size() != 0) {
				Date newDate = new Date();
				String result = null;
				for (int i = 0; i < historyDatas.size(); ++i) {
				    alias = new ArrayList();
					houseId = historyDatas.get(i).get("house_id");
					alarmName = historyDatas.get(i).get("alarm_name");
					alarmId = historyDatas.get(i).get("id");
					houseName = historyDatas.get(i).get("house_name").toString();
					alarm_time = historyDatas.get(i).get("alarm_time");
					content = houseName + "栋，在" + sdf1.format(alarm_time).toString() + "发生" + alarmName;
					contents = new HashMap<String, String>();
					contents.put("MessageTitle", "智慧鸡场报警");
					contents.put("messageId", sdf2.format(newDate));
					contents.put("MessageContent", content);

				    String ImeiInfoSQL = "SELECT a.imei_no as imei FROM s_user_house_view b,s_l_user_imei a WHERE 1=1 and a.user_id = b.user_id and b.house_id = " + houseId ;
					List<HashMap<String, Object>> ImeiInfos = tBaseQueryService.selectMapByAny(ImeiInfoSQL);
					for (HashMap<String, Object> ImeiInfo : ImeiInfos) {
						if (ImeiInfo != null && ImeiInfo.get("imei") != null && !ImeiInfo.get("imei").equals("")) {
							alias.add(ImeiInfo.get("imei"));
						}
					}
                    
                    SLJpushLog sLJpushlog = new SLJpushLog();
                    sLJpushlog.setHouseId(Integer.parseInt(houseId.toString()));
                    sLJpushlog.setContent(content);
                    sLJpushlog.setAlarmId(Integer.parseInt(alarmId.toString()));
                    sLJpushlog.setTagName(alias.toString().length() > 99 ? alias.toString().substring(0, 100) : alias.toString());
                    if (alias.size() != 0) {
                        sLJpushlog.setJgPushTime(new Date());
                        result = jPushUtil.pushMessageByAliasName(contents, alias);
                        if (!"Success".equals(result)) {
                            sLJpushlog.setJgRes("fail");
                            if (result.length() > 490) {
                                sLJpushlog.setJgMessage(result.substring(0, 490));
                            } else {
                                sLJpushlog.setJgMessage(result);
                            }
                        } else {
                            sLJpushlog.setJgRes("Success");
                        }
                        result = miPushUtil.pushMessageByTagName(contents, alias);
                        sLJpushlog.setMiPushTime(new Date());
                        if (!"Success".equals(result)) {
                            sLJpushlog.setMiRes("fail");
                            if (result.length() > 490) {
                                sLJpushlog.setMiMessage(result.substring(0, 490));
                            } else {
                                sLJpushlog.setMiMessage(result);
                            }
                        } else {
                            sLJpushlog.setMiRes("Success");
                        }
                    }else{
                    	sLJpushlog.setJgMessage("imei is null");
						sLJpushlog.setMiMessage("imei is null");
                    }
                    sLJpushlog.setMakeDate(newDate);
                    sLJpushlog.setModifyDate(newDate);
                    sSLJpushLogService.insert(sLJpushlog);
				}
			} else {
				mLogger.info("=====No AlarmNotification to push =====");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mLogger.info("=====Now 结束  第 " + counter + " 次 JPushDealExecuteJob.Service");
	}

	public void Service2() {
		if(!IPUtil.needRunTask()){
			return ;
		}
		counter ++ ;
		mLogger.info("=====Now 开始  第 " + counter +" 次 JPushDealExecuteJob.Service");
		try {
			Object alarm_time = new Object();
			String content;
			String tag ="mtc_tag_" ;
			Object houseId;
			Object alarmId;
			Object alarmName;
			Object houseName;
			String[] tags = {""};
			String SQL = "SELECT * FROM s_b_alarm_inco a " +
							"LEFT JOIN s_d_house b ON a.house_id = b.id " +
						 "WHERE deal_status = '01' AND alarm_code <> 'null' " +
						 "AND not exists(SELECT 1 FROM s_l_jpush_log c WHERE a.id = c.alarm_id"
//						 + " and c.res_flag = 'succ' "
						 + ") ";
			mLogger.info("JPushDealExecuteJob.Service.SQL = " + SQL);
			JPushUtil jPushUtil = new JPushUtil();
			List<HashMap<String, Object>> historyDatas = tBaseQueryService.selectMapByAny(SQL);
			SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
			if(historyDatas.size() != 0){
				for (int i = 0; i < historyDatas.size(); ++i) {
					houseId = historyDatas.get(i).get("house_id");
					alarmName = historyDatas.get(i).get("alarm_name");
					alarmId = historyDatas.get(i).get("id");
					houseName = historyDatas.get(i).get("house_name").toString();
					alarm_time = historyDatas.get(i).get("alarm_time");
					tag = "mtc_tag_" + houseId;
					tags[0] = tag;
					content = houseName + "栋，在" + sdf1.format(alarm_time).toString() + "发生" + alarmName;
					
					SLJpushLog sLJpushlog = new SLJpushLog();
					sLJpushlog.setHouseId(Integer.parseInt(houseId.toString()));
					sLJpushlog.setContent(content);
					sLJpushlog.setAlarmId(Integer.parseInt(alarmId.toString()));
					sLJpushlog.setTagName(tag);
					sLJpushlog.setJgPushTime(new Date());
					String result = null;
					try {
						result = jPushUtil.sendPushWithTags(content, tags);
					} catch (Exception e) {
						e.printStackTrace();
						result = e.getMessage();
					}
					sLJpushlog.setMakeDate(new Date());
					sLJpushlog.setModifyDate(new Date());
					if(result.equals("succ")) {
						sLJpushlog.setJgRes(result);
					} else {
						sLJpushlog.setJgRes("fail");
						if(result.length()>490){
							sLJpushlog.setJgMessage(result.substring(0, 490));
						}else{
							sLJpushlog.setJgMessage(result);
						}
					}
					sSLJpushLogService.insert(sLJpushlog);
				}
			}else{
				mLogger.info("=====No AlarmNotification to push =====");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mLogger.info("=====Now 结束  第 " + counter +" 次 JPushDealExecuteJob.Service");
	}
}
