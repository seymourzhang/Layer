/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mtc.common.util.constants.Constants;

/**
* @ClassName: DealSuccOrFail
* @Description: 
* @Date 2015年9月10日 上午10:37:11
* @Author Yin Guo Xiang
* 
*/ 
public class DealSuccOrFail {
	public static void deal(HttpServletResponse response, String SuccOrFail){
		try {
			String resStr = Constants.RESULT_SUCCESS;
			if(!SuccOrFail.equals(Constants.RESULT_SUCCESS)){
				resStr = Constants.RESULT_FAIL;
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/text;charset=utf-8");
			response.getWriter().write(resStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void dealApp(HttpServletRequest request,HttpServletResponse response, String SuccOrFail , JSONObject jsonResult){
		JSONObject resJson = new JSONObject();
		try {
			if(SuccOrFail.equals(Constants.RESULT_SUCCESS)){
				resJson.put("ResponseStatus",1) ;
			}else{
				resJson.put("ResponseStatus",2) ;
			}
			resJson.put("ResponseDetail", jsonResult) ;
			
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/text;charset=utf-8");
			response.getWriter().write(resJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
