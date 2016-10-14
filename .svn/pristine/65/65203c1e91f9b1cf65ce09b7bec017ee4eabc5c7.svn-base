/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util;

import java.net.URLEncoder;
import java.security.InvalidKeyException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.mtc.common.util.constants.WeatherConstants;
/**
 * @ClassName: RequestWeather
 * @Description: 
 * @Date 2015年10月30日 下午2:31:07
 * @Author Yin Guo Xiang
 * 
 */

public class RequestWeather {
	private static final char last2byte = (char) Integer.parseInt("00000011", 2);
	private static final char last4byte = (char) Integer.parseInt("00001111", 2);
	private static final char last6byte = (char) Integer.parseInt("00111111", 2);
	private static final char lead6byte = (char) Integer.parseInt("11111100", 2);
	private static final char lead4byte = (char) Integer.parseInt("11110000", 2);
	private static final char lead2byte = (char) Integer.parseInt("11000000", 2);
	
	private static final char[] encodeTable = new char[] { 'A', 'B', 'C', 'D',
		'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
		'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
		'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
		'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
		'4', '5', '6', '7', '8', '9', '+', '/'
	};
	
	private static String standardURLEncoder(String data, String key) {
		byte[] byteHMAC = null;
		String urlEncoder = "";
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			mac.init(spec);
			byteHMAC = mac.doFinal(data.getBytes());
			if (byteHMAC != null) {
				String oauth = encode(byteHMAC);
				if (oauth != null) {
					urlEncoder = URLEncoder.encode(oauth, "utf8");
				}
			}
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return urlEncoder;
	}
	
	private static String encode(byte[] from) {
		StringBuffer to = new StringBuffer((int) (from.length * 1.34) + 3);
		int num = 0;
		char currentByte = 0;
		for (int i = 0; i < from.length; i++) {
			num = num % 8;
			while (num < 8) {
				switch (num) {
					case 0:
						currentByte = (char) (from[i] & lead6byte);
						currentByte = (char) (currentByte >>> 2);
						break;
					case 2:
						currentByte = (char) (from[i] & last6byte);
					break;
					case 4:
						currentByte = (char) (from[i] & last4byte);
						currentByte = (char) (currentByte << 2);
						if ((i + 1) < from.length) {
						currentByte |= (from[i + 1] & lead2byte) >>> 6;
						}
						break;
					case 6:
						currentByte = (char) (from[i] & last2byte);
						currentByte = (char) (currentByte << 4);
						if ((i + 1) < from.length) {
						currentByte |= (from[i + 1] & lead4byte) >>> 4;
						}
						break;
				}
				to.append(encodeTable[currentByte]);
				num += 6;
			}
		}
		if (to.length() % 4 != 0) {
			for (int i = 4 - to.length() % 4; i > 0; i--) {
				to.append("=");
			}
		}
		return to.toString();
	}
	
	public static String requestWeather(String tAreaID,String tDate,String tType){
		
		if(tType == null){
			tType = WeatherConstants.FORECAST_V;
		}
		if(tDate == null){
			tDate = PubFun.getCurrentDate2() + PubFun.getCurrentTime2() ;
		}
		
		String public_key = WeatherConstants.WEATHER_SERVER + "?areaid="+tAreaID+"&type="+tType+"&date="+tDate+"&appid="+WeatherConstants.APPID; 
		
		String keyValue = standardURLEncoder(public_key, WeatherConstants.PRIVATE_KEY);
		
		String requestURL = WeatherConstants.WEATHER_SERVER + "?areaid="+tAreaID+"&type="+tType+"&date="+tDate+"&appid="+WeatherConstants.APPID.substring(0, 6)+"&key=" + keyValue;
		
		return HttpRequestUtil.HttpGetRequest(requestURL);
	}
	
	public static String paseWeatherDesc(String tCodeType,String tCode){
		String returnStr = "";
		if(tCodeType.equals("weather_desc")){
			if(tCode.equals("00")) returnStr = "晴";
			if(tCode.equals("01")) returnStr = "多云";
			if(tCode.equals("02")) returnStr = "阴";
			if(tCode.equals("03")) returnStr = "阵雨";
			if(tCode.equals("04")) returnStr = "雷阵雨";
			if(tCode.equals("05")) returnStr = "雷阵雨伴有冰雹";
			if(tCode.equals("06")) returnStr = "雨夹雪";
			if(tCode.equals("07")) returnStr = "小雨";
			if(tCode.equals("08")) returnStr = "中雨";
			if(tCode.equals("09")) returnStr = "大雨";
			if(tCode.equals("10")) returnStr = "暴雨";
			if(tCode.equals("11")) returnStr = "大暴雨";
			if(tCode.equals("12")) returnStr = "特大暴雨";
			if(tCode.equals("13")) returnStr = "阵雪";
			if(tCode.equals("14")) returnStr = "小雪";
			if(tCode.equals("15")) returnStr = "中雪";
			if(tCode.equals("16")) returnStr = "大雪";
			if(tCode.equals("17")) returnStr = "暴雪";
			if(tCode.equals("18")) returnStr = "雾";
			if(tCode.equals("19")) returnStr = "冻雨";
			if(tCode.equals("20")) returnStr = "沙尘暴";
			if(tCode.equals("21")) returnStr = "小到中雨";
			if(tCode.equals("22")) returnStr = "中到大雨";
			if(tCode.equals("23")) returnStr = "大到暴雨";
			if(tCode.equals("24")) returnStr = "暴雨到大暴雨";
			if(tCode.equals("25")) returnStr = "大暴雨到特大暴雨";
			if(tCode.equals("26")) returnStr = "小到中雪";
			if(tCode.equals("27")) returnStr = "中到大雪";
			if(tCode.equals("28")) returnStr = "大到暴雪";
			if(tCode.equals("29")) returnStr = "浮尘";
			if(tCode.equals("30")) returnStr = "扬沙";
			if(tCode.equals("31")) returnStr = "强沙尘暴";
			if(tCode.equals("53")) returnStr = "霾";
		}else if(tCodeType.equals("wind_direction")){
			if(tCode.equals("0")) returnStr = "无持续风向";
			if(tCode.equals("1")) returnStr = "东北风";
			if(tCode.equals("2")) returnStr = "东风";
			if(tCode.equals("3")) returnStr = "东南风";
			if(tCode.equals("4")) returnStr = "南风";
			if(tCode.equals("5")) returnStr = "西南风";
			if(tCode.equals("6")) returnStr = "西风";
			if(tCode.equals("7")) returnStr = "西北风";
			if(tCode.equals("8")) returnStr = "北风";
			if(tCode.equals("9")) returnStr = "旋转风";
		}else if(tCodeType.equals("wind_speed")){
			if(tCode.equals("0")) returnStr = "微风";
			if(tCode.equals("1")) returnStr = "3-4级";
			if(tCode.equals("2")) returnStr = "4-5级";
			if(tCode.equals("3")) returnStr = "5-6级";
			if(tCode.equals("4")) returnStr = "6-7级";
			if(tCode.equals("5")) returnStr = "7-8级";
			if(tCode.equals("6")) returnStr = "8-9级";
			if(tCode.equals("7")) returnStr = "9-10 级";
			if(tCode.equals("8")) returnStr = "10 -11 级";
			if(tCode.equals("9")) returnStr = "11 -12 级";
		}
		return returnStr ;
	}
	
	
	public static void main(String[] args) {
			String areaID = "101010100";
			String dd =RequestWeather.requestWeather(areaID,null,null);
			System.out.println(dd);
	}
}
