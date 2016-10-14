/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mtc.task.YINComTimerTask;

/**
 * @ClassName: StartupListener
 * @Description: 
 * @Date 2015年12月22日 下午1:56:45
 * @Author Yin Guo Xiang
 * 
 */

@Component
public class StartupListener implements ServletContextListener{
	
	private static Logger mLogger =Logger.getLogger(StartupListener.class);
	
	private ApplicationContext app;  
	
    private YINComTimerTask tYINComTimerTask;
	
	private Thread tYINCommThread = null; 
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		app = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()); //获取spring上下文！  
		tYINComTimerTask = (YINComTimerTask)app.getBean("YINComTimerTask");   
		mLogger.info("获取设备数据服务启动。。。");
		
		tYINCommThread = new Thread(tYINComTimerTask);
		
		tYINCommThread.start();
	}
}
