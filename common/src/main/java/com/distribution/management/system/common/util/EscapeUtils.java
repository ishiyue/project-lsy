package com.distribution.management.system.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 模糊查询时转义工具类
 *
 * @author admin
 */
public class EscapeUtils {

    /**
     * mysql的模糊查询时特殊字符转义
     */
    public static String escapeChar(String before) {
        if (StringUtils.isNotBlank(before)) {
            before = before.replaceAll("\\\\", "\\\\\\\\");
            before = before.replaceAll("_", "\\\\_");
            before = before.replaceAll("%", "\\\\%");
            return before.trim();
        }
        return "";
    }
}