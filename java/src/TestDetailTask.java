/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


/**
 * @ClassName: TtestTask
 * @Description: 
 * @Date 2016年1月18日 下午3:34:16
 * @Author Yin Guo Xiang
 * 
 */
public class TestDetailTask implements Runnable{
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public static void main(String[] args) {
		Random random = new Random();
		int min = 2511;
		int max = 2900;
		int res = random.nextInt(max)%(max-min+1)+min;
		double iValue = (double)res/100;
		System.out.println(iValue);
	}
	
	@Override
	public void run() {
		System.out.println(id+" begin");
		Random random = new Random();
		int min = 2011;
		int max = 2900;
		int res ;
		double iValue1,iValue2,iValue3,iValue4,iValue5,iValue6;
		
		res = random.nextInt(max)%(max-min+1)+min;
		iValue1 = (double)res/100;
		res = random.nextInt(max)%(max-min+1)+min;
		iValue2 = (double)res/100;
		res = random.nextInt(max)%(max-min+1)+min;
		iValue3 = (double)res/100;
		res = random.nextInt(max)%(max-min+1)+min;
		iValue4 = (double)res/100;
		res = random.nextInt(max)%(max-min+1)+min;
		iValue5 = (double)res/100;
		res = random.nextInt(max)%(max-min+1)+min;
		iValue6 = (double)res/100;
		
		String tPath = "http://127.0.0.1/sweb/";
		
		String ressss = reqGet(tPath + "sys/device/dataUploadBBF.action?"
				+ "keyid=mtcdevice_"+id
				+"&t1="+iValue1
				+"&t2="+iValue2
				+"&t3="+iValue3
				+"&t4="+iValue4
				+"&t5="+iValue5
				+"&t6="+iValue6
				+"&h1="+15.2
				+"&h2="+25.5
				+"&p=1" );
		System.out.println(id+" end:" + ressss);
	}
	
	public static String reqGet(String tURL){
		URL url = null;
		HttpURLConnection conn = null;
		String resSTR = "";
		BufferedReader in = null;
		try {
			url = new URL(tURL);
			conn = (HttpURLConnection) url.openConnection();
		    conn.setReadTimeout(40000);
		    conn.setConnectTimeout(10000);
		    conn.setRequestMethod("GET");
		    conn.setDoInput(true);
		    conn.setDoOutput(true);
		    conn.setRequestProperty("Content-Type", "application/json");
		    
		    in = new BufferedReader(new InputStreamReader(
		    		conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
            	resSTR += line;
            }
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			conn.disconnect();
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

		return resSTR;
	}
}
