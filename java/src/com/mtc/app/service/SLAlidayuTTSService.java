/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.sql.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.app.biz.AlidayuLogManager;
import com.mtc.common.util.IPUtil;
import com.mtc.entity.app.SLDayuTtsLog;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;

/**
 * @ClassName: SLAlidayuTTSService
 * @Description: 语音通知拨打服务。包含拨打方法，同时将拨打记录和拨打内容入库。拨打方法返回每次拨打的所分配的唯一ID。
 * 
 */
@Service
public class SLAlidayuTTSService implements InitializingBean {
	
	private static Logger mLogger =Logger.getLogger(SLAlidayuTTSService.class);
	@Autowired
	private AlidayuLogManager logManager;
	
	
	private String appKey = "";
	private String appSecret = "";	
	private String url = "";
	
	@Resource(name = "configProperty")
	private ConfigProperty configProperty;
	
	/**
	 * 被叫号显，传入的显示号码必须是阿里大鱼“管理中心-号码管理”中申请或购买的号码 
	 */
	private String showNum = "4001008052";
	private String ttsCode = "TTS_9605610";
	private String extend = "mtccall";
	
	
	private TaobaoClient client;
	private AlibabaAliqinFcTtsNumSinglecallRequest req;
	

	
	public void test() throws ApiException{
		if(client == null){
			mLogger.info("appKey:" + appKey + ", appSecret:" + appSecret + ",url:" + url);
			client = new DefaultTaobaoClient(url, appKey, appSecret);
		}
		if(req == null){
			req = new AlibabaAliqinFcTtsNumSinglecallRequest();
		}
		/** 
		 * 公共回传参数，在“消息返回”中会透传回该参数；
		 * 举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，
		 * 用户可以根据该会员ID识别是哪位会员使用了你的应用
		*/
		req.setExtend("12345");
		/***
		 * 文本转语音（TTS）模板变量，传参规则{"key"："value"}，
		 * key的名字须和TTS模板中的变量名一致，多个变量之间以逗号隔开，
		 * 示例：{"name":"xiaoming","code":"1234"} 
		 */
		req.setTtsParamString("{\"name\":\"Sir\",\"time\":\"2016-05-20\"}");
		/**
		 * 被叫号码，支持国内手机号与固话号码,格式如下057188773344,13911112222,4001112222,95500
		 */
		req.setCalledNum("18621017090");
		/**
		 * 被叫号显，传入的显示号码必须是阿里大鱼“管理中心-号码管理”中申请或购买的号码 
		 */
		req.setCalledShowNum("4001008052");
		/**
		 *  TTS模板ID，传入的模板必须是在阿里大鱼“管理中心-语音TTS模板管理”中的可用模板 
		 */
		req.setTtsCode("TTS_9605610");
		
		AlibabaAliqinFcTtsNumSinglecallResponse rsp = client.execute(req);
		mLogger.info(rsp.getBody());
		
	}
	
	/**
	 *  拨打语音电话
	 * @param callNum
	 * @param ttsParams
	 * @return
	 * @throws ApiException
	 */
	public String ttsNumSingleCell(String callNum,String ttsParams) throws ApiException{
		
		return ttsNumSingleCell(callNum, ttsParams, ttsCode, showNum, extend);
		
	}
	
	/**
	 *  拨打语音电话
	 * @param callNum
	 * @param ttsParams
	 * @param ttsCode
	 * @return
	 * @throws ApiException
	 */
	public String ttsNumSingleCell(String callNum,String ttsParams,String ttsCode) throws ApiException{
		
		return ttsNumSingleCell(callNum, ttsParams, ttsCode, showNum, extend);
		
	}
	
	/**
	 *  拨打语音电话
	 *  
	 * @param callNum
	 * @param ttsParams
	 * @param ttsCode
	 * @param showNum
	 * @return
	 * @throws ApiException
	 */
	public String ttsNumSingleCell(String callNum,String ttsParams,String ttsCode,String showNum) throws ApiException{
		
		return ttsNumSingleCell(callNum, ttsParams, ttsCode, showNum, extend);
		
	}
	
	
	/**
	 *  拨打语音电话
	 * @param callNum 要拨打的电话号码
	 * @param ttsParams 拨打的参数json串
	 * @param ttsCode 模板ID
	 * @param showNum 拨打显示的号码
	 * @param extend 扩展字段
	 * @return bizId 每次拨打电话，返回一个bizId,此ID可用于查询拨打反馈结果
	 * @throws ApiException
	 */
	public String ttsNumSingleCell(String callNum,String ttsParams,String ttsCode,String showNum,String extend) throws ApiException{
		
		mLogger.info("appKey:" + appKey + ", appSecret:" + appSecret + ",url:" + url);
		if(client == null){
			client = new DefaultTaobaoClient(url, appKey, appSecret);
		}
		if(req == null){
			req = new AlibabaAliqinFcTtsNumSinglecallRequest();
		}
		/** 
		 * 公共回传参数，在“消息返回”中会透传回该参数；
		 * 举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，
		 * 用户可以根据该会员ID识别是哪位会员使用了你的应用
		*/
		req.setExtend(extend);
		/***
		 * 文本转语音（TTS）模板变量，传参规则{"key"："value"}，
		 * key的名字须和TTS模板中的变量名一致，多个变量之间以逗号隔开，
		 * 示例：{"name":"xiaoming","code":"1234"} 
		 */
		req.setTtsParamString(ttsParams);
		/**
		 * 被叫号码，支持国内手机号与固话号码,格式如下057188773344,13911112222,4001112222,95500
		 */
		req.setCalledNum(callNum);
		/**
		 * 被叫号显，传入的显示号码必须是阿里大鱼“管理中心-号码管理”中申请或购买的号码 
		 */
		req.setCalledShowNum(showNum);
		/**
		 *  TTS模板ID，传入的模板必须是在阿里大鱼“管理中心-语音TTS模板管理”中的可用模板 
		 */
		req.setTtsCode(ttsCode);
		
		// 拨打记录
		SLDayuTtsLog tts = new SLDayuTtsLog();
		tts.setCalledNum(callNum);
		tts.setCalledTime(new Date(System.currentTimeMillis()));
		tts.setExtend(extend);
		tts.setTempParam(ttsParams);
		tts.setTempId(ttsCode);
		tts.setStatusCode("00");
		
		AlibabaAliqinFcTtsNumSinglecallResponse rsp = client.execute(req);
		/**
		 *success: {"alibaba_aliqin_fc_tts_num_singlecall_response":{"result":{"err_code":"0","model":"101679577699^100221309814","success":true},"request_id":"qm2xt3au7037"}}
		 *fail:{"error_response":{"code":15,"msg":"Remote service error","sub_code":"isv.TTS_TEMPLATE_ILLEGAL","sub_msg":"未找到审核通过的文本转语音模板,ttsCode=TTS_96056210,partnerId=99109010036","request_id":"15q04lulip32i"}}
		 */
		mLogger.info("alidayu response body:" + rsp.getBody());
		
		String bizId = null;
		String key = "alibaba_aliqin_fc_tts_num_singlecall_response";
		JSONObject response = null;
		
		try {
			JSONObject jsonObject = new JSONObject(rsp.getBody());
			if(rsp.getBody().contains("error_response")){
				response = jsonObject.getJSONObject("error_response");
				tts.setSubCode(response.getString("sub_code"));
			}else{
				response = jsonObject.getJSONObject(key);
				JSONObject result = response.getJSONObject("result");
				tts.setCreateTime(new Date(System.currentTimeMillis()));
				bizId = result.getString("model");
				tts.setBizId(bizId);
				tts.setRequestId(response.getString("request_id"));
				tts.setSubCode("success");
			}
			
			tts.setBizId(bizId);
			
			return bizId;
			
		} catch (JSONException e) {
			e.printStackTrace();
			throw new ApiException("can not parse body to json,the body is :" + rsp.getBody());
		}finally{
			if(bizId != null){
				int m = logManager.insert(tts);
				mLogger.info("add data to s_l_dayu_tts_log result:" + m);
			}
			
		}
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		
		appKey = configProperty.getAppKey();
		appSecret = configProperty.getAppSecret();
		url = configProperty.getCallNumUrl();
		showNum = configProperty.getShowNum();
		extend = configProperty.getExtend();
		client = new DefaultTaobaoClient(url, appKey, appSecret);
		req = new AlibabaAliqinFcTtsNumSinglecallRequest();
	}

	public ConfigProperty getConfigProperty() {
		return configProperty;
	}

	public void setConfigProperty(ConfigProperty configProperty) {
		this.configProperty = configProperty;
	}

	
}









