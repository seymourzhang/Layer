/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.common.util;

import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

/**
* @ClassName: ValidateCodeGenerater
* @Description: 
* @Date 2015年9月10日 上午10:49:06
* @Author Yin Guo Xiang
* 
*/ 
public class ValidateCodeGenerater {

    public synchronized static String generate(int userId, String target, String type) {
        StringBuilder sb = new StringBuilder();
        sb.append(userId);
        sb.append(target);
        sb.append(type);
        sb.append(System.nanoTime());
        sb.append(getRandomSalt(10));

        return DigestUtils.md5Hex(sb.toString());
    }

    public synchronized static String generate() {
        return getRandomSalt(6);
    }

    public synchronized static String generateSid() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.nanoTime());
        sb.append(getRandomSalt(10));

        return DigestUtils.md5Hex(sb.toString());
    }

    /**
     * Generates a string of random chars from the B64T set.
     *
     * @param num
     *            Number of chars to generate.
     */
    static String getRandomSalt(final int num) {
        final StringBuilder saltString = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            saltString.append(new Random().nextInt(10));
        }
        return saltString.toString();
    }

}
