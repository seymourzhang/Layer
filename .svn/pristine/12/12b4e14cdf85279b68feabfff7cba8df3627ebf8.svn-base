/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.push.api;


import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.google.gson.JsonObject;
import com.mtc.common.util.PubFun;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: JPushUtil
 * @Description: 
 * @Date 2016年5月13日 下午2:29:04
 * @Author Yin Guo Xiang
 * 
 */
public class JPushUtil {
	private static final String appKey = PubFun.getPropertyValue("JPush.AppKey");
	private static final String masterSecret = PubFun.getPropertyValue("JPush.MasterSecret");
	
	private static Logger mLogger = Logger.getLogger(JPushUtil.class);
	
	private static PushPayload buildPushObject_all_tag_alert(String tagName, String alertMessage) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tagName))
                .setNotification(Notification.alert(alertMessage))
                .build();
    }
	
	/**
	 * @param alertMessage   发送通知内容
	 * @param tags  客户端指定TagName
	 * @return   向指定的tagname发送通知
	 */
	public static String sendPushWithTags(String alertMessage,String tags []) {
		ClientConfig config = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, config);
        for(String tag:tags){
        	PushPayload payload = buildPushObject_all_tag_alert(tag,alertMessage);
            try {
                PushResult result = jpushClient.sendPush(payload);
                mLogger.info("JPushUtil.sendPushAll-result - " + result);
            } catch (APIConnectionException e) {
                mLogger.error("JPushUtil.sendPushAll-Connection error. Should retry later. ", e);
                return e.getMessage();
            } catch (APIRequestException e) {
                mLogger.error("JPushUtil.sendPushAll-Error response from JPush server. Should review and fix it. ", e);
                mLogger.info("JPushUtil.sendPushAll-HTTP Status: " + e.getStatus());
                mLogger.info("JPushUtil.sendPushAll-Error Code: " + e.getErrorCode());
                mLogger.info("JPushUtil.sendPushAll-Error Message: " + e.getErrorMessage());
                mLogger.info("JPushUtil.sendPushAll-Msg ID: " + e.getMsgId());
                return e.getErrorMessage();
            }
        }
        return "succ";
	}
	
	/**
	 * 	生成 PushPayload
	 * 
	 * @param content   自定义消息内容
	 * @param tagNames  标签集合
	 * @return   PushPayload
	 */
	private static PushPayload buildNormalPushPayloadByTag(String content, List<String> tagNames){
		PushPayload jPushPayload = null;
		try {
			jPushPayload = PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.tag_and(tagNames))
	                .setMessage(Message.content(content))
	                .build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jPushPayload;
	}
	
	/**
	 * 	生成 PushPayload
	 * 
	 * @param content   内容
	 * @param aliasNames  别名集合
	 * @return   PushPayload
	 */
	private static PushPayload buildNormalPushPayloadByAlias(String content, List<String> aliasNames){
		PushPayload jPushPayload = null;
		try {
			jPushPayload = PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.alias(aliasNames))
	                .setMessage(Message.content(content))
	                .build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jPushPayload;
	}
	
	/**
	 *  根据别名发送自定义消息
	 * 
	 * @param messageInfo  消息参数
	 * @param aliasNames  别名集合
	 * @return
	 */
	public static String pushMessageByAliasName(HashMap<String,String> messageInfo, List<String> aliasNames){
		String resFlag = "Success";
		try {
			long timestamp = System.currentTimeMillis();
			String alertTitle = messageInfo.get("MessageTitle");
			String alertContent = messageInfo.get("MessageContent");
			String messageId = messageInfo.get("messageId");
			
			JsonObject jsonExtra = new JsonObject();
	        jsonExtra.addProperty("timestamp", timestamp);
	        jsonExtra.addProperty("title", alertTitle);
	        jsonExtra.addProperty("content", alertContent);
	        jsonExtra.addProperty("id", messageId);
	        jsonExtra.addProperty("extra", "");
			
			ClientConfig clientConfig = ClientConfig.getInstance();
	        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
	        if(aliasNames == null || aliasNames.size() == 0 ){
	        	resFlag = "aliasNames is null!";
	        }else{
	        	jpushClient.sendPush(buildNormalPushPayloadByAlias(jsonExtra.toString(),aliasNames));
	        }
        } catch (APIConnectionException e) {
            mLogger.error("JPushUtil.sendPushAll-Connection error. Should retry later. ", e);
            resFlag = e.getMessage();
        } catch (APIRequestException e) {
            resFlag = e.getErrorMessage();
        }
		return resFlag;
	}
	
	public static void main(String args[]){
		
		HashMap<String,String> messageInfo = new HashMap();
		messageInfo.put("MessageContent", "" + new Date());
		
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		messageInfo.put("messageId", tString);
		messageInfo.put("MessageTitle", "智慧鸡场报警");
		
		List<String> aliasNames = new ArrayList<String>();
		
//		String imei1 = "868719029712243"; // 华为测试
		String imei2 = "869336020181952"; // 伟佳机器
		aliasNames.add(imei2);
		
		System.out.print(pushMessageByAliasName(messageInfo,aliasNames));
	}

}
