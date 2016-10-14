/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
* @ClassName: IPUtil
* @Description: 
* @Date 2015年9月10日 上午10:48:33
* @Author Yin Guo Xiang
* 
*/ 
public class IPUtil {
	private static Logger mLogger =Logger.getLogger(IPUtil.class);

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
    
    /**
     * 获取本机Ip
     * @return
     */
    public static String getCurIp(){
    	String curIp = null;
    	InetAddress addr;
    	try {
	    	Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
	    	InetAddress ip = null;
	    	while (allNetInterfaces.hasMoreElements())
	    	{
		    	NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
		    	mLogger.debug("netInterface.Name==" + netInterface.getName());
		    	Enumeration<InetAddress> address = netInterface.getInetAddresses();  
		    	while (address.hasMoreElements())
		    	{
		    		ip = address.nextElement(); 
//		    		mLogger.debug("ip.isSiteLocalAddress==" + ip.isSiteLocalAddress());
//		    		mLogger.debug("ip.isLoopbackAddress==" + ip.isLoopbackAddress());
//		    		mLogger.debug("ip.isAnyLocalAddress==" + ip.isAnyLocalAddress());
			    	if (ip != null 
			    			&& ip instanceof Inet4Address
			    			&& !ip.isLoopbackAddress()
			    			&& ip.isSiteLocalAddress()
			    			&& ip.getHostAddress().indexOf(":") == -1
			    			)
			    	{
//			    		mLogger.debug("InetAddress.HostName==" + ip.getHostName());
			    		curIp =  ip.getHostAddress() ;
			    	} 
		    	}
	    	}
			mLogger.debug("LocalIp==" + curIp);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return curIp;
    }
    
    /**
     * 判断是否需要运行批处理任务
     * @return
     */
    public static boolean needRunTask(){
    	String proIp = ""; 
    	try {
			proIp = PubFun.getPropertyValue("Pub.RunTaskIp");
			mLogger.debug("proIp==" + proIp);
		} catch (Exception e) {
			e.printStackTrace();
			proIp = "";
		}
    	if(proIp.equals(IPUtil.getCurIp())){
    		return true;
    	}else{
    		mLogger.info("该服务器不需要跑批处理任务。。。。");
    		return false;
    	}
    }
    
    public static void main(String[] args) {
		IPUtil.getCurIp();
	}
}
