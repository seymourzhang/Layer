/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util;

import java.io.PrintStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * @ClassName: StdoutListener
 * @Description: 把在程序中的System.out.print()的信息自动转成日志信息
 * @Date 2015年10月20日 下午4:31:18
 * @Author Yin Guo Xiang
 * 
 */

public class StdoutListener implements ServletContextListener {
	
	private static Logger mLogger =Logger.getLogger(StdoutListener.class);
	
    public void contextDestroyed(ServletContextEvent event) {
    }
    private void log(Object info) {
        try {
        	mLogger.info(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void contextInitialized(ServletContextEvent event) {
        PrintStream printStream = new PrintStream(System.out) {
            public void println(boolean x) {
                log(Boolean.valueOf(x));
            }
            public void println(char x) {
                log(Character.valueOf(x));
            }
            public void println(char[] x) {
                log(x == null ? null : new String(x));
            }
            public void println(double x) {
                log(Double.valueOf(x));
            }
            public void println(float x) {
                log(Float.valueOf(x));
            }
            public void println(int x) {
                log(Integer.valueOf(x));
            }
            public void println(long x) {
                log(x);
            }
            public void println(Object x) {
                log(x);
            }
            public void println(String x) {
                log(x);
            }
        };
        System.setOut(printStream);
        System.setErr(printStream);
    }
}