/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util.constants;

/**
* @ClassName: ValidateInfoConstants
* @Description: 
* @Date 2015年9月10日 上午10:50:18
* @Author Yin Guo Xiang
* 
*/ 
public class ValidateInfoConstants {

    public static final String VALIDATE_TYPE_VALIDATE_EMAIL               = "VALIDATE_EMAIL";
    public static final int    VALIDATE_EXPIRE_TIME_VALIDATE_EMAIL        = 24 * 3600 * 1000;

    public static final String VALIDATE_TYPE_VALIDATE_MOBILE_PHONE        = "VALIDATE_MOBILE_PHONE";
    public static final int    VALIDATE_EXPIRE_TIME_VALIDATE_MOBILE_PHONE = 1800 * 1000;

    public static final String VALIDATE_TYPE_FIND_PASSWORD                = "FIND_PASSWORD";
    public static final int    VALIDATE_EXPIRE_TIME_FIND_PASSWORD         = VALIDATE_EXPIRE_TIME_VALIDATE_EMAIL;

}
