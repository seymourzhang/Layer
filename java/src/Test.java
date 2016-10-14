/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */



/**
 * @ClassName: TtestTask
 * @Description: 
 * @Date 2016年1月18日 下午3:34:16
 * @Author Yin Guo Xiang
 * 
 */
public class Test{
	public static void main(String[] args) {
		for(int j = 0; j<10000;j++){
			System.out.println("开始第"+j+"次压测。。");
			for(int i=0;i<400;i++){
				TestDetailTask tdt = new TestDetailTask();
				tdt.setId(i);
				Thread thread = new Thread(tdt);
				thread.start();
			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
