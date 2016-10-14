/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.push.api;

import com.google.gson.JsonObject;
import com.mtc.common.util.PubFun;
import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * @ClassName: MiPush
 * @Description: 
 * @Date 2016年9月8日 下午6:21:32
 * @Author Yin Guo Xiang
 * 
 */
public class MiPushUtil {
	private static final String APP_SECRET_KEY = PubFun.getPropertyValue("MiPush.AppSecretKey");
	private static final String MY_PACKAGE_NAME = PubFun.getPropertyValue("MiPush.PackageName");
	
	private static Logger mLogger = Logger.getLogger(MiPushUtil.class);
	
	private static Message buildNormalMessage(String messagePayload ){
		Message message = null; 
		try {
			message = new Message.Builder().payload(messagePayload)
			         .restrictedPackageName(MY_PACKAGE_NAME)
			         .passThrough(1)  //消息使用透传方式
			         .notifyType(1)     // 使用默认提示音提示
			         .build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	public static String pushMessageToAllClient(HashMap<String,String> messageInfo){
		String resFlag = "";
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
			
	        Sender sender = new Sender(APP_SECRET_KEY);
	        Result tResult = sender.broadcastAll(buildNormalMessage(jsonExtra.toString()),0);
	        resFlag = getResultMsg(tResult);
		} catch (Exception e) {
			e.printStackTrace();
			resFlag = e.getMessage();
		}
		return resFlag;
	}
	
	private static String getResultMsg(Result tResult){
		if(tResult == null){
			return "Result is null!";
		}else if(tResult.getErrorCode().equals(ErrorCode.Success)){
			return "Success";
		}else{
			return tResult.getReason();
		}
	}
	
	public static String pushMessageByTagName(HashMap<String,String> messageInfo, List<String> tagNames){
		String resFlag = "";
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
			
	        Sender sender = new Sender(APP_SECRET_KEY);
	        if(tagNames == null || tagNames.size() == 0){
	        	resFlag = "tagNames is null!";
	        }else{
	        	Result tResult = sender.sendToAlias(buildNormalMessage(jsonExtra.toString()), tagNames,0);
	        	resFlag = getResultMsg(tResult);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			resFlag = e.getMessage();
		}
		return resFlag;
	}
	
	public static void main(String[] args) throws Exception {
		HashMap<String,String> messageInfo = new HashMap();
		messageInfo.put("MessageContent", "" + new Date());
		
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		messageInfo.put("messageId", tString);
		messageInfo.put("MessageTitle", "智慧鸡场报警");
		
		List<String> tagNames = new ArrayList<String>();
		
		String imei1 = "868719029712243"; // 华为测试
//		String imei2 = "869336020181952"; // 伟佳机器
		tagNames.add(imei1);
		pushMessageByTagName(messageInfo,tagNames) ;
//		pushMessageToAllClient(messageInfo);
	}
}
