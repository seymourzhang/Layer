/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
* @ClassName: CollectionUtil
* @Description: 
* @Date 2015年9月10日 上午10:47:42
* @Author Yin Guo Xiang
* 
*/ 
public class CollectionUtil {

    public static List<Integer> stringToIntegerList(String str, String segmentation) {
        return str == null ? null : stringArrayToIntegerList(str.split(segmentation));
    }

    public static List<Integer> stringArrayToIntegerList(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();
        for (String str : array) {
            list.add(Integer.parseInt(str));
        }
        return list;
    }

    public static List<String> stringToList(String str, String segmentation) {
        return str == null ? null : stringArrayToList(str.split(segmentation));
    }

    public static List<String> stringArrayToList(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        for (String str : array) {
            list.add(str);
        }
        return list;
    }

    public static String integerListToString(List<Integer> list, String segmentation) {
        if (isEmpty(list)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i : list) {
            sb.append(segmentation);
            sb.append(i);
        }

        return sb.substring(segmentation.length());
    }

    private static boolean isEmpty(Collection<?> coll) {
        return (coll == null || coll.isEmpty());
    }

}
