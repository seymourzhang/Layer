package com.mtc.app.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource("classpath:config/pro/alidayu.properties")
@Component("configProperty")
public class ConfigProperty implements InitializingBean {

	@Autowired
	private Environment env;

	private String appKey = "";
	private String appSecret = "";
	private String callNumUrl = "ws://mc.api.taobao.com";
	private String taobaoRest = "ws://mc.api.taobao.com";
	private String showNum = "4001008052";
	private String ttsCode = "TTS_9605610";
	private String extend = "mtccall";

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getCallNumUrl() {
		return callNumUrl;
	}

	public void setCallNumUrl(String callNumUrl) {
		this.callNumUrl = callNumUrl;
	}

	public String getTaobaoRest() {
		return taobaoRest;
	}

	public void setTaobaoRest(String taobaoRest) {
		this.taobaoRest = taobaoRest;
	}

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public String getShowNum() {
		return showNum;
	}

	public void setShowNum(String showNum) {
		this.showNum = showNum;
	}

	public String getTtsCode() {
		return ttsCode;
	}

	public void setTtsCode(String ttsCode) {
		this.ttsCode = ttsCode;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		appKey = env.getProperty("appKey");
		appSecret = env.getProperty("appSecret");
		callNumUrl = env.getProperty("callNumUrl");
		taobaoRest = env.getProperty("taobaoRest");
		showNum = env.getProperty("showNum");
		ttsCode = env.getProperty("ttsCode");
		extend = env.getProperty("extend");
	}

}
