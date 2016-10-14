/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
* @ClassName: SystemParameterType
* @Description: 
* @Date 2015年9月10日 上午10:49:47
* @Author Yin Guo Xiang
* 
*/ 
public enum SystemParameterType {

    CATEGORY_INFO_LEVEL("类目级别", "CATEGORY_INFO_LEVEL");

    private String typeName;

    private String type;

    /**
     * @param typeName
     * @param type
     */
    private SystemParameterType(String typeName, String type) {
        this.typeName = typeName;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Map<String, String> toMap() {
        Map<String, String> paramTypeMap = new LinkedHashMap<String, String>();
        for (SystemParameterType systemParamType : values()) {
            paramTypeMap.put(systemParamType.type, systemParamType.typeName);
        }
        return paramTypeMap;
    }

    public static String getTypeName(String type) {
        for (SystemParameterType systemParamType : values()) {
            if (systemParamType.type.equals(type)) {
                return systemParamType.typeName;
            }
        }
        return type;
    }
}
