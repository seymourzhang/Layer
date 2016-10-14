/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.BaseQueryService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;

/**
 * @ClassName: UserCheckFilter
 * @Description: 
 * @Date 2015年11月9日 上午11:42:45
 * @Author Yin Guo Xiang
 * 
 */
public class UserCheckFilter implements Filter{
	
	private static Logger mLogger =Logger.getLogger(UserCheckFilter.class);
	@Autowired
	private BaseQueryService mBaseQueryService;
	
	private String[] excudeURLs ; 
	
	public void destroy() {
		mLogger.debug("正在调用 UserCheckFilter.destroy");
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		mLogger.debug("正在调用 UserCheckFilter.doFilter");
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res = (HttpServletResponse) arg1 ;
		String paraStr = PubFun.getRequestPara(req);
		
		boolean needCheck = true;
		String requestURL = req.getRequestURI();
		mLogger.debug("requestURL="+requestURL);
		
		if(requestURL.endsWith("sys/user/save.action")){
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(paraStr);
				if(jsonObject.has("registerFlag") && jsonObject.getString("registerFlag").equals("Y")){
					needCheck = false ;
				}else{
					needCheck = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				needCheck = true;
			}
		}else if(requestURL.endsWith("sys/login/logIn.action")){
			needCheck = false;
		}
		
		int userCount = 0 ;
		if(needCheck){
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(paraStr);
				if(jsonObject.has("id_spa")){
					userCount = mBaseQueryService.selectIntergerByAny(" SELECT COUNT(1) FROM s_d_user a WHERE id = 269 AND a.freeze_status = 0 ");
				}
			} catch (Exception e) {
				e.printStackTrace();
				needCheck = true;
			}
		}else{
			userCount = 1;
		}
		
		if(userCount == 1){
			arg2.doFilter(arg0, arg1);
		}else{
			JSONObject errorJson = null ;
			try {
				errorJson = new JSONObject();
				errorJson.put("ErrorCode", "101") ;
				errorJson.put("ErrorDesc", "该操作用户未授权。") ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			DealSuccOrFail.dealApp(req,res,Constants.RESULT_FAIL,errorJson);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		mLogger.debug("正在调用 UserCheckFilter.init");
		// 获取初始化参数
	      String tempPara = arg0.getInitParameter("excludeURL"); 
	      if(tempPara != null && !tempPara.equals("") ){
	    	  excudeURLs = tempPara.split(",");
	      }
	}
}
