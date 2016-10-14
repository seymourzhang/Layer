/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util;

import java.lang.reflect.Method;
import java.util.Date;

/**
* @ClassName: FillCommonField
* @Description: 
* @Date 2015年8月18日 下午5:34:31
* @Author Yin Guo Xiang
* 
*/
public class EntityCommonOperate {
	
	public static void fillCommonField(Object obj,String userName) throws Exception{
		
		Class objClass = obj.getClass();
		Date curDate = new Date();
		
        Method setCreatePerson = objClass.getDeclaredMethod("setCreatePerson",String.class);
        Method setModifyPerson = objClass.getDeclaredMethod("setModifyPerson",String.class);
        Method setCreateDate = objClass.getDeclaredMethod("setCreateDate",Date.class);
        Method setModifyDate = objClass.getDeclaredMethod("setModifyDate",Date.class);
        
        setCreatePerson.invoke(obj,userName);
        setModifyPerson.invoke(obj,userName);
        setCreateDate.invoke(obj,curDate);
        setModifyDate.invoke(obj,curDate);
	}
	
	/*public static void main(String [] ad) throws Exception{
		LogUserLogon tLogUserLogon = new LogUserLogon();
		EntityCommonOperate.fillCommonField(tLogUserLogon,"002");
		System.out.println("99999");
	}*/
}
