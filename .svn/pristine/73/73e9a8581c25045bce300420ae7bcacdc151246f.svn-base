/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
* @ClassName: DateUtil
* @Description: 
* @Date 2015年9月10日 上午10:48:08
* @Author Yin Guo Xiang
* 
*/ 
public class DateUtil {

    public static final String LONG_DATE_GBK_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String LONG_DATE_FORMAT     = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT          = "yyyy-MM-dd";
    public static final String DATE_FORMAT_NUMBER   = "yyyyMMdd";

    /**
     * 将一个日期型转换为指定格式字串
     * @param date
     * @param formatStr
     * @return
     */
    public static final String toFormatDateString(Date date, String formatStr) {
        if (date == null)
            return StringUtils.EMPTY;
        return new SimpleDateFormat(formatStr).format(date);

    }

    /**
     * 将一个日期型转换为'yyyy年MM月dd日 HH时mm分ss秒'格式字串
     * @param date
     * @return
     */
    public static final String toLongDateGBKString(Date date) {
        return toFormatDateString(date, LONG_DATE_GBK_FORMAT);
    }

    public static final String toLongDateString(Date date) {
        return toFormatDateString(date, LONG_DATE_FORMAT);
    }

    public static final String toDateString(Date date) {
        return toFormatDateString(date, DATE_FORMAT);
    }

    public static final String toDateNumber(Date date) {
        return toFormatDateString(date, DATE_FORMAT_NUMBER);
    }

    public static final Date parser(String dateStr, String formatter) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);

        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static final long getInterval(Date begin, Date end) {
        return end.getTime() - begin.getTime();
    }
}
