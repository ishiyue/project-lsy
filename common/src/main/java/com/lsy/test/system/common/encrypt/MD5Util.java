package com.lsy.test.system.common.encrypt;

import static io.jsonwebtoken.impl.TextCodec.BASE64;

import java.security.MessageDigest;

/**
 * @author lsy
 * @since 2022/6/2 09:54:50
 */
public class MD5Util {
    //盐，用于混交md5
    private static final String SLAT = "&%5123***&&%%$$#@";
    private static final String MD5 = "MD5";
    public static String sign(String dataStr) {
        try {
            dataStr = dataStr + SLAT;
            dataStr =  BASE64.encode(dataStr.getBytes());
            MessageDigest m = MessageDigest.getInstance(MD5);
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
