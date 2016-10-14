/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.mtc.common.util.constants.Constants;

/**
* @ClassName: URLUtil
* @Description: 
* @Date 2015年9月10日 上午10:49:00
* @Author Yin Guo Xiang
* 
*/ 
public class URLUtil {

    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, Constants.ENCODING_UTF_8);
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }

    public static String decode(String url) {
        try {
            return URLDecoder.decode(url, Constants.ENCODING_UTF_8);
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }

}
