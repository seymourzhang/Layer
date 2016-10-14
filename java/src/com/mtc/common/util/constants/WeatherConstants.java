/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util.constants;

/**
 * @ClassName: WeatherConstants
 * @Description: 
 * @Date 2015年11月4日 下午2:58:16
 * @Author Yin Guo Xiang
 * 
 */
public class WeatherConstants {
	/** 中国天气网接口固定网址 */
	public static final String  WEATHER_SERVER        = "http://open.weather.com.cn/data/";
	
	/** 基础气象数据接口(369个地级市), 返回1天常规3种指数  */
	public static final String  INDEX_F               = "index_f";
	
	/** 常规气象数据接口(2566个城市), 返回1天常规3种指数 */
	public static final String  INDEX_V               = "index_v";
	
	/** 基础气象数据接口(369个地级市),返回3天24小时常规预报数据  */
	public static final String  FORECAST_F            = "forecast_f";
	
	/** 常规气象数据接口(2566个城市),返回3天24小时常规预报数据  */
	public static final String  FORECAST_V            = "forecast_v";
	
	/** 私钥 */
	public static final String PRIVATE_KEY = "fd57c7_SmartWeatherAPI_2e88fa4";
	
	/** 固定分配的型号 */
	public static final String APPID = "05c29dcd66f3a692" ;
}
