package com.distribution.management.system.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 字符串转义工具类
 * @ClassName StringEscapeUtils
 * @Author admin
 * @Date 2020/12/10 10:33
 */
public class StringEscapeUtils {

    private static final String REG_EX = "[`~!@#$%^&*()+=|{}‘:;‘,\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";


    /**
     * @Description 转义字符串中的特殊字符
     * @Date 2020/12/10 10:37
     * @Param
     * @Return
     */
    public static String escapeStr(String str) {
        Pattern p = Pattern.compile(REG_EX);
        Matcher m1 = p.matcher(str);
        if (m1.find()) {
            CharSequence cs = str;
            int j = 0;
            for (int i = 0; i < cs.length(); i++) {
                String temp = String.valueOf(cs.charAt(i));
                Matcher m2 = p.matcher(temp);
                if (m2.find()) {
                    StringBuilder sb = new StringBuilder(str);
                    str = sb.insert(j, "\\").toString();
                    j++;
                }
                j++;
            }
        }
        return str;
    }
}
