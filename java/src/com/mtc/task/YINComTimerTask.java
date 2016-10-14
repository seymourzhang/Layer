/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.biz.SBYincommManager;
import com.mtc.yincomm.SocketServer;

/**
 * @ClassName: YINComTimerTask
 * @Description: 
 * @Date 2015年12月22日 下午5:19:37
 * @Author Yin Guo Xiang
 * 
 */
@Component
public class YINComTimerTask implements Runnable {
	
	private static Logger mLogger =Logger.getLogger(YINComTimerTask.class);
	
	@Autowired
    private SBYincommManager tSBYincommManager;
	
	public void run() {  
    	SocketServer mSocketServer = SocketServer.getInstance();
    	mSocketServer.settSBYincommManager(tSBYincommManager);
    	mSocketServer.startServer();
    }
}
