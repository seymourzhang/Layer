/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.RSAService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.RequestWeather;
import com.mtc.common.util.constants.Constants;

/**
 * @ClassName: HomeReqController
 * @Description:
 * @Date 2015-11-26 下午2:44:04
 * @Author Shao Yao Yu
 * 
 */
@Controller
@RequestMapping("/sys/checkrs")
public class RSAReqController {

	private static Logger mLogger = Logger.getLogger(RSAReqController.class);

	@Autowired
	private RSAService rsaService;

	@RequestMapping("/reqrskey")
	public void reqrskey(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=======Now start executing RSAReqController.reqrskey");
		response.setCharacterEncoding("UTF-8");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			/*
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("updateFarm.para=" + paraStr);
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			*/
			// 返回公钥 用于前台参数加密
			resJson.put("publicKeyExponent", rsaService.getPublicKeyExponent());
			resJson.put("publicKeyModulus", rsaService.getPublicKeyModulus());
			
			mLogger.info("reutrnStr:" + resJson);
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			dealRes = Constants.RESULT_FAIL;
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL;
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
	}
}
