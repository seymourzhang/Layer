/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName: tttt
 * @Description: 
 * @Date 2016年1月14日 下午12:21:34
 * @Author Yin Guo Xiang
 * 
 */
public class tttt {

	private final Timer AlarmTimer = new Timer();
	
	public String tURL = "";
	public boolean bAlarm=false;
	
	public boolean execute(String action ){
		String tUserid = "9";
		if ("startTsk".equals(action)) {
			try {
				tURL="http://192.168.10.103:8080/sweb/envCtrl/needAlarm2.action?userID=" + tUserid;
				AlarmTimer.schedule(AlarmTask, 1000, 10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }else{
	    }
    	return true;
	}
	
	public static void main(String[] args) {
		tttt t = new tttt();
		t.execute("startTsk") ;
	}
	
	private TimerTask AlarmTask=new TimerTask(){
		@Override
		public void run() {
			if(tURL.equals("") || tURL == null){
				return;
			}
	    	String str=getJsonContent(tURL);
	    	System.out.println("AlarmTask====" + str);
	    	if (str.toUpperCase().equals("H")){
	    		if (!bAlarm){ 
	        		bAlarm=true;
	        	}
	    	}else{
	        	if (bAlarm){
	        		bAlarm=false;
	        	}
	        }
	        	
		}
	};
	
	public static String getJsonContent(String path)
	{
		try 
		{
			URL url=new URL(path);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			int code=connection.getResponseCode();
			if(code==200){
				return changeInputString(connection.getInputStream());
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	private static String changeInputString(InputStream inputStream)
	{
		
		String jsonString="";
		ByteArrayOutputStream outPutStream=new ByteArrayOutputStream();
		byte[] data=new byte[1024];
		int len=0;
		try {
			while((len=inputStream.read(data))!=-1){
				outPutStream.write(data, 0, len);
			}
			jsonString=new String(outPutStream.toByteArray());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString.trim();
	}
}
