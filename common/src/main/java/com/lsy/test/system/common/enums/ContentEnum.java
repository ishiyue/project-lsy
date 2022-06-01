package com.lsy.test.system.common.enums;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * @author admin
 */
@JSONType(serializer = EnumBaseSerializer.class)
public interface ContentEnum {

    /**
     * 获取枚举内容
     *
     * @return
     */
    String getContent();

    /**
     * 获取枚举值
     *
     * @return
     */
    Integer getValue();

    /**
     * 判断是否相等
     *
     * @param value
     * @return
     */
    default boolean equalsValue(Integer value) {
        return value != null && value.equals(this.getValue());
    }


}
