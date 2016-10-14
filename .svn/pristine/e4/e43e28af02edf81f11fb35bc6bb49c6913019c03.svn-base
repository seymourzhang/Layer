/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.filter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mtc.app.service.RSAService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;

/**
 * 
 * 安全性过过滤器，包括非法请求过滤和密文解密
 * 
 * @author lx
 * 
 */
public class SecurityCheckFilter implements Filter {

	private static Logger mLogger = Logger.getLogger(SecurityCheckFilter.class);

	private RSAService rsaService;

	/**
	 * 过滤非手机请求
	 */
	private boolean filterUserAgentFlag = false;
	/**
	 * 验证时间戳
	 */
	private boolean filterTimeFlag = true;
	/**
	 * 验证token
	 */
	private boolean filterTokenFlag = true;
	/**
	 * IP限制
	 */
	private boolean filterIPFlag = true;

	/**
	 * 是否需要参数解密
	 */
	private boolean decryptFlag = true;

	public boolean isDecryptFlag() {
		return decryptFlag;
	}

	public void setDecryptFlag(boolean decryptFlag) {
		this.decryptFlag = decryptFlag;
	}

	public boolean isFilterTimeFlag() {
		return filterTimeFlag;
	}

	public void setFilterTimeFlag(boolean filterTimeFlag) {
		this.filterTimeFlag = filterTimeFlag;
	}

	public boolean isFilterUserAgentFlag() {
		return filterUserAgentFlag;
	}

	public void setFilterUserAgentFlag(boolean filterUserAgentFlag) {
		this.filterUserAgentFlag = filterUserAgentFlag;
	}

	public boolean isFilterTokenFlag() {
		return filterTokenFlag;
	}

	public void setFilterTokenFlag(boolean filterTokenFlag) {
		this.filterTokenFlag = filterTokenFlag;
	}

	public boolean isFilterIPFlag() {
		return filterIPFlag;
	}

	public void setFilterIPFlag(boolean filterIPFlag) {
		this.filterIPFlag = filterIPFlag;
	}

	public void destroy() {
		mLogger.debug("正在调用 SecurityCheckFilter.destroy");
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		mLogger.debug("正在调用 SecurityCheckFilter.doFilter");
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res = (HttpServletResponse) arg1;
		String paraStr = "";//PubFun.getRequestPara(req);
		//req.setCharacterEncoding("utf-8");
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = req.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = bis.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, read, "iso-8859-1"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		paraStr = sb.toString();
		mLogger.info("paraStr:" + paraStr);
		JSONObject errorJson = new JSONObject();
		String url = req.getRequestURL().toString();
		mLogger.info("url:" + url);

		if (filterUserAgentFlag) {
			String agent = req.getHeader("User-Agent");
			if (!agent.contains("Android") && !agent.contains("iPhone")) {
				try {
					errorJson.put("ErrorCode", "2000");
					errorJson.put("ErrorDesc", "非法请求。");
				} catch (Exception e) {
					e.printStackTrace();
				}
				DealSuccOrFail.dealApp(req, res, Constants.RESULT_FAIL,
						errorJson);
				return;
			}
		}

		if (filterTimeFlag) {

		}

		if (filterTokenFlag) {

		}

		if (filterIPFlag) {

		}

		if (decryptFlag) {
			// 请求公钥的请求不需要解密
			if (!url.contains("reqrskey")) {
				String code = "";
				try {
					if (rsaService != null) {
						String[] codes = paraStr.split(",");
						for(String codeStr : codes){
							String tmp = rsaService.decryptByPrivateKey(codeStr);
							mLogger.info("tmp res :" + tmp);
							tmp = new StringBuilder(tmp).reverse().toString();
							code += unescape(tmp);
							//mLogger.info("tmp res :" + tmp);
						}
						mLogger.info("code res :" + code);
						arg0.setAttribute("paramsVal", code);
					} else {
						mLogger.info("rsaService is null");
					}

				} catch (Exception e) {
					e.printStackTrace();
					try {
						errorJson.put("ErrorCode", "2001");
						errorJson.put("ErrorDesc", "非法参数。");
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					DealSuccOrFail.dealApp(req, res, Constants.RESULT_FAIL,
							errorJson);
					return;
				}
			}
		}

		arg2.doFilter(arg0, arg1);

	}

	public void init(FilterConfig arg0) throws ServletException {
		mLogger.debug("正在调用 SecurityCheckFilter.init");
		// 获取初始化参数
		String tempPara = arg0.getInitParameter("excludeURL");
		if (tempPara != null && !tempPara.equals("")) {
			// excudeURLs = tempPara.split(",");
		}

		ServletContext context = arg0.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(context);
		rsaService = ctx.getBean(RSAService.class);
		// rsaService = (RSAService)ctx.getBean("RSAService");
	}
	
	public String escape(String src) {
		  int i;
		  char j;
		  StringBuffer tmp = new StringBuffer();
		  tmp.ensureCapacity(src.length() * 6);
		  for (i = 0; i < src.length(); i++) {
		   j = src.charAt(i);
		   if (Character.isDigit(j) || Character.isLowerCase(j)
		     || Character.isUpperCase(j))
		    tmp.append(j);
		   else if (j < 256) {
		    tmp.append("%");
		    if (j < 16)
		     tmp.append("0");
		    tmp.append(Integer.toString(j, 16));
		   } else {
		    tmp.append("%u");
		    tmp.append(Integer.toString(j, 16));
		   }
		  }
		  return tmp.toString();
		 }
	
	

	  
	 public String unescape(String src) {
	  StringBuffer tmp = new StringBuffer();
	  tmp.ensureCapacity(src.length());
	  int lastPos = 0, pos = 0;
	  char ch;
	  while (lastPos < src.length()) {
	   pos = src.indexOf("%", lastPos);
	   if (pos == lastPos) {
	    if (src.charAt(pos + 1) == 'u') {
	     ch = (char) Integer.parseInt(src
	       .substring(pos + 2, pos + 6), 16);
	     tmp.append(ch);
	     lastPos = pos + 6;
	    } else {
	     ch = (char) Integer.parseInt(src
	       .substring(pos + 1, pos + 3), 16);
	     tmp.append(ch);
	     lastPos = pos + 3;
	    }
	   } else {
	    if (pos == -1) {
	     tmp.append(src.substring(lastPos));
	     lastPos = src.length();
	    } else {
	     tmp.append(src.substring(lastPos, pos));
	     lastPos = pos;
	    }
	   }
	  }
	  return tmp.toString();
	 }
}
