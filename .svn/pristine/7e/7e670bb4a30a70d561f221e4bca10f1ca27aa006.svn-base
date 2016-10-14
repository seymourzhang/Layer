/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util.constants;

/**
 * 
 */
/**
* @ClassName: UserGender
* @Description: 
* @Date 2015年9月10日 上午10:50:07
* @Author Yin Guo Xiang
* 
*/ 
public enum UserGender {

    SECRET("S", "保密"),

    MALE("M", "男"),

    FEMALE("F", "女");

    private String code;

    private String name;

    /**
     * @param code
     * @param name
     */
    private UserGender(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public static String getName(String code) {
        for (UserGender userGender : values()) {
            if (userGender.code.equals(code)) {
                return userGender.name;
            }
        }
        return code;
    }

}
