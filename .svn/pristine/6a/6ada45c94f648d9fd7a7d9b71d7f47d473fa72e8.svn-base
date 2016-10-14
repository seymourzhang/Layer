package com.mtc.yincomm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.mtc.app.biz.SBYincommManager;
import com.mtc.listener.StartupListener;

/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */

/**
 * @ClassName: YINCommServer
 * @Description: 
 * @Date 2015年12月22日 上午11:00:28
 * @Author Yin Guo Xiang
 * 
 */
public class SocketServer {
	private static Logger mLogger =Logger.getLogger(SocketServer.class);
	
	private static SocketServer mSocketServer = null;
	
	private static ServerSocket SocketServer = null;  
	
	private SBYincommManager tSBYincommManager;
	
	public SBYincommManager gettSBYincommManager() {
		return tSBYincommManager;
	}

	public void settSBYincommManager(SBYincommManager tSBYincommManager) {
		this.tSBYincommManager = tSBYincommManager;
	}

	private Socket Socket = null;  
	/**
	 * serverState:00-未启动  01-启动中  02-正常停止  05-异常停止
	 */
	private String serverState = "00";
	
	public String getServerState() {
		return serverState;
	}

	public void setServerState(String serverState) {
		this.serverState = serverState;
	}

	public void startServer() {
		
		try {
			if(SocketServer == null || SocketServer.isClosed()){
				SocketServer = new ServerSocket(9081);
			}
			
			if(SocketServer == null){
				mLogger.info("启动服务失败。");
				return;
			}
			mLogger.info("启动服务 Success");
			serverState = "01";
			while (true)  
			{
			    try  
			    {
					Socket = SocketServer.accept();
			    	
			    	YINCommSocketTask tSocketTask = new YINCommSocketTask(Socket);  
			    	tSocketTask.settSBYincommManager(tSBYincommManager);
			    	Thread tThread = new Thread(tSocketTask);
			    	tThread.start();  
			        Thread.sleep(2000);  
			    }
			    catch (Exception e)  
			    {
			        e.printStackTrace();  
			    }
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(Socket != null){
				try {
					Socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(SocketServer != null){
				try {
					SocketServer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			serverState = "02";
		}
	}
	
	public static SocketServer getInstance(){
		if(mSocketServer == null){
			mSocketServer = new SocketServer();
		}
		return mSocketServer ;
	}
	
	private SocketServer()  
    {
    }
}
