package com.distribution.management.system.common.util;

import com.alibaba.fastjson.JSON;

/**
 * 基于 FastJson 工具类
 *
 * @author admin
 */
public class JsonUtil {
    private JsonUtil() {
    }

    public static boolean isnull(Object object) {
        return object == null;
    }

    /**
     * 对象转JSON字符串
     */
    public static String objToJson(Object object) {
        if (!isnull(object)) {
            return JSON.toJSONString(object);
        }
        return null;
    }

    /**
     * 字符串反序列化为JSON对象
     */
    public static <T> T jsonToObj(String json, Class<T> clazz) {
        if (!isnull(json)) {
            return JSON.parseObject(json, clazz);
        }
        return null;
    }


}
