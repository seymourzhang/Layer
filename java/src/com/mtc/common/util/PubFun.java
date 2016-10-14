/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


/**
* @ClassName: PubFun
* @Description: 
* @Date 2015年9月10日 上午10:48:41
* @Author Yin Guo Xiang
* 
*/ 
public class PubFun {
	private static Logger logger = Logger.getLogger(PubFun.class);

	/**
	 * 得到当前系统日期 author: GX
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统时间 author: GX
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	public static String getCurrentTime() {
		String pattern = "HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}
	
	/**
	 * 得到当前系统日期 author: GX
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyyMMdd"
	 */
	public static String getCurrentDate2() {
		String pattern = "yyyyMMdd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统时间 author: GX
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HHmmss"
	 */
	public static String getCurrentTime2() {
		String pattern = "HHmmss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}
	
	/**
	 * 得到指定日期的Date author: GX
	 * 
	 * @return 指定日期的Date ,参数格式：YYYY-MM-DD
	 * @throws Exception 
	 */
	public static Date getDate(String tDate) throws Exception {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date returnDate = df.parse(tDate);
		return returnDate;
	}
	
	/**
	 * 得到WEB-INF的路径
	 * 
	 * @return 指定日期的Date ,参数格式：YYYY-MM-DD
	 * @throws Exception 
	 */
	public static String getWEBINFpath() throws Exception {
		String path = Thread.currentThread().getContextClassLoader()
						.getResource("").toString();  
        path=path.replace("file:/", ""); //去掉file:  
        path=path.replace("classes/", ""); //去掉class\  
        return path;  
	}

	/**
	 * 
	 * @return 指定日期的Date ,参数格式：YYYY-MM-DD
	 * @throws ParseException 
	 * @throws java.text.ParseException 
	 * @throws java.text.ParseException 
	 * @throws 返回Int 
	 *   			类型的日期差
	 */
	
	public static int daysBetween(Date smdate,Date bdate) throws ParseException   
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
	
	/*
	 * 日期加天数得到的日期
	 */
	 public static Date addDate(Date date,int day) throws ParseException {

		     Calendar   calendar   =   new   GregorianCalendar(); 
		     calendar.setTime(date); 
		     calendar.add(Calendar.DATE, day);//把日期往后增加一天.整数往后推,负数往前移动 
		     date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
		     return date;
		  } 
	
	public static String addDate(String date, int day) throws Exception {
		Date tDate = PubFun.getDate(date);
		Date resDate = PubFun.addDate(tDate, day);
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(resDate) ;
	}
	
	public static Object [][] convertResult2Array(
				List<LinkedHashMap<String,Object>> sourceRes){
		Object [][] targetRes = null;
		if(sourceRes != null && sourceRes.size()>0){
			targetRes = new Object[sourceRes.size()][] ;
			int itemIndex = 0;
			for(HashMap<String, Object> item:sourceRes){
				Object [] array2 = new Object[item.keySet().size()] ;
				int keyIndex = 0;
				for(String key:item.keySet()){
					array2[keyIndex] = item.get(key);
					keyIndex ++;
				}
				targetRes[itemIndex] = array2;
				itemIndex++;
			}
		}
		return targetRes;
	}
	
	public static String getRequestPara(HttpServletRequest request){
		StringBuilder sb = new StringBuilder ();
        try {
			InputStream is = request.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			 byte [] buffer = new byte[1024];
			 int read = 0;
			 while ((read=bis.read(buffer)) != -1){
			      sb.append( new String(buffer, 0, read, "UTF-8" ));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return sb.toString();
	}
	
	public static String getRequestPara(MAPIHttpServletRequestWrapper request){
		StringBuilder sb = new StringBuilder ();
        try {
			InputStream is = request.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			 byte [] buffer = new byte[1024];
			 int read = 0;
			 while ((read=bis.read(buffer)) != -1){
			      sb.append( new String(buffer, 0, read, "UTF-8" ));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return sb.toString();
	}
	
	public static boolean isNull(Object o){
		if(o == null){
			return true;
		}else if("".equals(o)){
			return true;
		}else{
			return false;
		}
	}
	
	public static BigDecimal getBigDecimalData(String val){
		BigDecimal tVal = null ;
		try {
			if(val == null || val.equals("")){
				return null;
			}else{
				tVal = BigDecimal.valueOf(Double.valueOf(val));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return tVal;
	}
	public static Double getDoubleData(String val){
		Double tVal = null ;
		try {
			tVal = Double.valueOf(val);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return tVal;
	}
	public static Integer getIntegerData(String val){
		Integer tVal = null ;
		try {
			tVal = Integer.valueOf(val);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return tVal;
	}
	
	/**
	 * double 保留小数位数
	 * @param num
	 * @param length
	 * @return
	 */
	public static String formatDoubleNum(double num,int length){
        String numStr = Double.toString(num);
        if(numStr.equals("NaN")){
        	return "-";
        }
        if((numStr.length()-numStr.indexOf("."))<length)
            return numStr;
        StringBuffer strAfterDot = new StringBuffer();
        int i = 0;
        while(i<length){
            strAfterDot.append("0");
            i++;
        }
        String formatStr = "0."+strAfterDot.toString();
        if(length==0){
        	formatStr = "0";
        }
        DecimalFormat df = new DecimalFormat( formatStr);
        String res = df.format(num);
        if(res.indexOf("∞")>=0){
        	res = "-";
        }
        return res;
    }
	
	/**
	 * 补0方法
	 * 
	 * @param num
	 * @param length
	 * @return
	 */
	public static String fillLeftChar(int tInt,char tChar,int tLength){
		String tempStr = tInt+"";
		int needLength = tLength - tempStr.length();
		if(needLength > 0){
			for(int i = 1; i<= needLength; i++){
				tempStr = "" + tChar + tInt ;
			}
		}
		return tempStr;
    }
	
	/**
	 * 根据生长日龄计算周龄
	 * 
	 * @param calType     01-生长日龄/7的算法     02-参考自然周算法
	 * @param dayOfAge    生长日龄
	 * @param dayOfDate   该日龄对应的日期
	 * @return
	 */
	public static int getWeekAge(String calType,int dayOfAge,Date dayOfDate){
		int weekAge = 0;
		try {
			if("01".equals(calType)){
				weekAge = (dayOfAge+6) / 7 ;
			}else if("02".equals(calType)){
				Calendar cal = Calendar.getInstance();  
				Date placeDate = PubFun.addDate(dayOfDate,-dayOfAge+1);
				cal.setTime(placeDate);  
				int placeDateOfweek = cal.get(Calendar.DAY_OF_WEEK);
				if(placeDateOfweek <= 4){
					dayOfAge = dayOfAge + (placeDateOfweek-1);
				}else{
					dayOfAge = dayOfAge - (8- placeDateOfweek);
				}
				weekAge = (dayOfAge + 6) / 7; 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weekAge;
	}

	public static String getPropertyValue(String keyName){
		ResourceBundle conf= ResourceBundle.getBundle("config/pro/constants");
		String value= conf.getString(keyName);
		return value;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(PubFun.getPropertyValue("JPush.AppKey"));
	}	
}
