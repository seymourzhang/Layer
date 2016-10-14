/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.sql.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.app.biz.AlidayuLogManager;
import com.mtc.common.util.DateUtil;
import com.mtc.common.util.IPUtil;
import com.mtc.entity.app.SLDayuTtsLog;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.internal.toplink.LinkException;

/**
 * response demo:
 * {"status_code":"200000","duration":"10","biz_id":"101679577699^100221309814","end_time":"2016-05-23 10:18:34","status_msg":"","extend":"12345","start_time":"2016-05-23 10:18:24"}

 * @ClassName: SLAlidayuTMCService
 * @Description: 阿里大鱼语音通知发出后，用户是否应答等反馈，由此服务接收。服务解析信息反馈并更新拨打记录表。
 *  * 
 */
@Service
public class SLAlidayuTMCService implements InitializingBean{
	
	private static Logger mLogger =Logger.getLogger(SLAlidayuTMCService.class);
	
	private String appKey = "";
	private String appSecret = "";
	private String url = "ws://mc.api.taobao.com";
	@Autowired
	private AlidayuLogManager logManager;
	
	@Resource(name = "configProperty")
	private ConfigProperty configProperty;
	/**
	 * 使用多线程处理反馈结果
	 */
	private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();  
	
	/**
	 * 初始化接收服务
	 */
	public void init(){
		if(!IPUtil.needRunTask()){
			mLogger.info("本机不启用AlidayuTMCService");
			return ;
		}
		mLogger.info("connect to taobao server...");
		mLogger.info("appKey:" + appKey + ", appSecret:" + appSecret + ",url:" + url);
		TmcClient client = new TmcClient(appKey, appSecret, "default"); // 关于default参考消息分组说明
		client.setMessageHandler(new MessageHandler() {
			public void onMessage(Message message, MessageStatus status) {
				try {
					final String content = message.getContent();
					mLogger.info("message content:" + content);
					
					cachedThreadPool.execute(new Runnable() {
						
						@Override
						public void run() {
							try {
								parseContent(content);
							} catch (JSONException e) {
								e.printStackTrace();
								mLogger.error("消息处理失败",e);
							}
						}
					});
					
				} catch (Exception e) {
					e.printStackTrace();
					mLogger.error("消息处理失败回滚，服务端需要重发",e);
					status.fail(); // 消息处理失败回滚，服务端需要重发
				}
			}
		});
		
		try {
			
			client.connect(url);
			mLogger.info("connect success~");
		} catch (LinkException e) {
			e.printStackTrace();
			mLogger.error("connect failed!", e);
		}
	}
	
	/**
	 * {"status_code":"200000","duration":"10","biz_id":"101679577699^100221309814","end_time":"2016-05-23 10:18:34","status_msg":"","extend":"12345","start_time":"2016-05-23 10:18:24"}
	 * @param content
	 * @throws Exception 
	 */
	private void parseContent(String content) throws JSONException{
		JSONObject jsonObject = new JSONObject(content);
		String status_code = jsonObject.getString("status_code");
		int duration = jsonObject.getInt("duration");
		String biz_id = jsonObject.getString("biz_id");
		
		Date startTime = null;
		Date endTime = null;
		
		try {
			String start_time = jsonObject.getString("start_time");
			
			java.util.Date d1 = DateUtil.parser(start_time, DateUtil.LONG_DATE_FORMAT);
			if(d1 != null){
				startTime = new Date(d1.getTime());
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		try {
			String end_time = jsonObject.getString("end_time");
			
			java.util.Date d2 = DateUtil.parser(end_time, DateUtil.LONG_DATE_FORMAT);
			if(d2 != null){
				endTime = new Date(d2.getTime());
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		
		String status_msg = jsonObject.getString("status_msg");
		
		SLDayuTtsLog tts = new SLDayuTtsLog();
		tts.setDuration(duration);
		tts.setStartTime(startTime);
		tts.setEndTime(endTime);
		tts.setModifyTime(new Date(System.currentTimeMillis()));
		tts.setStatusCode(status_code);
		tts.setBizId(biz_id);
		tts.setStatusMsg(status_msg);
		
		mLogger.info("start update call info ");
		int n = logManager.updateByBizId(tts);
		mLogger.info("update call info result:" + n);
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		appKey = configProperty.getAppKey();
		appSecret = configProperty.getAppSecret();
		url = configProperty.getTaobaoRest();
		
		init();
	}
	
	
}









