package com.distribution.management.system.common.enums;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * 状态枚举
 *
 * @author admin
 */
@JSONType(serializer = EnumBaseSerializer.class)
public enum StatusEnum {


    /**
     * 未启用
     */
    DISABLE("未启用", 1),


    /**
     * 启用
     */
    ENABLE("启用", 0);

    private String content;

    private Integer value;

    StatusEnum(String content, Integer value) {
        this.content = content;
        this.value = value;
    }

    public static StatusEnum getByValue(Integer value) {
        StatusEnum[] types = StatusEnum.values();
        for (StatusEnum type : types) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

    public String getContent() {
        return this.content;
    }

    public Integer getValue() {
        return this.value;
    }
}
