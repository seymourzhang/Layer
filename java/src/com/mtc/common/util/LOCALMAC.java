package com.mtc.common.util;
import java.net.InetAddress;
import java.net.NetworkInterface;

import javax.servlet.http.HttpServletRequest;
/*
 * 物理地址是48位，别和ipv6搞错了
 */
public class LOCALMAC {
	/**
	 * @param args
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 */
	public String getRemortIP(HttpServletRequest request) {
		
		if (request.getHeader("x-forwarded-for") == null)
		{    
		return request.getRemoteAddr(); 
			}  
		return request.getHeader("x-forwarded-for");
		}
	
	
	
	

	public  String getLocalMac()throws Exception {
		// TODO Auto-generated method stub
		//获取网卡，获取地址
		InetAddress ia=InetAddress.getLocalHost();
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		return sb.toString().toUpperCase();
	}
}