/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
* @ClassName: MyExceptionHandler
* @Description: 
* @Date 2015年9月10日 上午10:04:30
* @Author Yin Guo Xiang
* 
*/ 
public class MyExceptionHandler implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);
		
		// 根据不同错误转向不同页面
		if(ex instanceof BusinessException) {
			return new ModelAndView("error-business", model);
		}else if(ex instanceof ParameterException) {
			return new ModelAndView("error-parameter", model);
		} else {
			return new ModelAndView("error", model);
		}
	}
}