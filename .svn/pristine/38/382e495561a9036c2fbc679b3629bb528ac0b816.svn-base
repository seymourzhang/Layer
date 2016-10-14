package com.mtc.yincomm;
/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */

/**
 * @ClassName: StringHexUtil
 * @Description: 
 * @Date 2015年12月9日 下午1:47:59
 * @Author Yin Guo Xiang
 * 
 */
public class StringHexUtil {
	
	/**
	 * 把字节数组转换成16进制字符串
	 */
	public static final String bytes2HexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		for (int i = 0; i < bArray.length; i++) {
			sb.append(b2HS(bArray[i]));
		}
		return sb.toString();
	}
	
	/**
	 * 把字节转换成16进制字符串
	 */
	public static final String b2HS(byte b) {
		String result ="";
		result = Integer.toHexString(0xFF & b);
		if (result.length() < 2){
			result="0"+result;
		}
		return result.toUpperCase();
	}
    
	/**
	 * 把16进制字符串转换成字节数组
	 */
	public static byte[] hexString2Bytes(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
	
	/**
     * 把字节数组转化成2进制字符串 
     */  
    public static String bytes2BinaryString(byte[] b)
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i)
        {
            buffer.append(b2BS(b[i]));
        }
        return buffer.toString();
    }
	
	/**
     * 把字节转化成2进制字符串 
     */  
    public static String b2BS(byte b){
        String result ="";  
        byte a = b;  
        for (int i = 0; i < 8; i++){
            byte c=a;  
            a=(byte)(a>>1);//每移一位如同将10进制数除以2并去掉余数。  
            a=(byte)(a<<1);
            if(a==c){  
                result="0"+result;  
            }else{  
                result="1"+result;  
            }  
            a=(byte)(a>>1);  
        }  
        return result;  
    }
	
    public static void main(String[] args)
    {
    	byte[] byteArray = hexString2Bytes("4198CCCD");
    	int resInt = ByteNumUtil.bytesToInt(byteArray);
    	Float resFloat = Float.intBitsToFloat(resInt);
    	
    	System.out.println("resInt  :" + resInt);
    	System.out.println("resFloat:" + resFloat);
    	
    }
}