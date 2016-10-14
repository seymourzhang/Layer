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

import net.sf.json.test.JSONAssert;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mtc.common.util.MAPIHttpServletRequestWrapper;
import com.mtc.common.util.PubFun;

/**
 * @ClassName: ReportDelayFilter
 * @Description: 
 * @Date 2016年6月6日 下午1:57:58
 * @Author Yin Guo Xiang
 * 
 */
public class ReportDelayFilter implements Filter{
	
	private static Logger mLogger =Logger.getLogger(ReportDelayFilter.class);
	private static final String REPORTDELAY_STR = PubFun.getPropertyValue("Pub.ReportDelay");
	private static int REPORTDELAY_INT = 1500;
	
	FilterConfig config;
	
	@Override
	public void destroy() {
		this.config = null;  
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		try {
			HttpServletRequest tHttpServletRequest = (HttpServletRequest) arg0;
			String requestURL = tHttpServletRequest.getRequestURI();
			mLogger.debug("requestURL="+requestURL);

			// 设备上传数据不需要检测
			if(requestURL.indexOf("dataUploadBBF")>=0){
				arg2.doFilter(arg0, arg1);
			}else{
				MAPIHttpServletRequestWrapper requestWrapper = null;    
				if(arg0 instanceof HttpServletRequest) {
				     requestWrapper = new MAPIHttpServletRequestWrapper((HttpServletRequest) arg0);    
				}
				String paraStr = PubFun.getRequestPara(requestWrapper);
				JSONObject params;
				JSONObject jsonobject;
				String isNeedDelay = "";
				try {
					jsonobject = new JSONObject(paraStr);
					params = jsonobject.optJSONObject("params");
					isNeedDelay = params.optString("IsNeedDelay");
				} catch (Exception e1) {
//					e1.printStackTrace();
				}
				if("Y".equals(isNeedDelay)){
					long startReqTime = System.currentTimeMillis();
					arg2.doFilter(requestWrapper, arg1);
					long endReqTime = System.currentTimeMillis();
					if(endReqTime - startReqTime < 1500){
						try {
							Thread.sleep(1500 - endReqTime + startReqTime);
						}catch(InterruptedException e) {
							e.printStackTrace();
						}
					}
				}else{
					arg2.doFilter(requestWrapper, arg1);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		mLogger.info("begin do ReportDelayFilter filter!");
		this.config = config;
		try{
			REPORTDELAY_INT = Integer.parseInt(REPORTDELAY_STR);
		}catch (Exception e){
			REPORTDELAY_INT = 1500 ;
		}
		mLogger.info("ReportDelayFilter.REPORTDELAY_INT：" + REPORTDELAY_INT);
	}
}
