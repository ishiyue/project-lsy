package com.lsy.test.system.common.enums;


import com.alibaba.fastjson.annotation.JSONType;

/**
 * @author admin
 */
@JSONType(serializer = EnumBaseSerializer.class)
public enum DeleteFlagEnum {
    /**
     * 正常
     */
    NORMAL("存在", 0),

    /**
     * 已删除
     */
    DELETED("已删除", 1);

    private String content;

    private Integer value;

    DeleteFlagEnum(String content, Integer value) {
        this.content = content;
        this.value = value;
    }

    public static DeleteFlagEnum getByValue(Integer value) {
        DeleteFlagEnum[] types = DeleteFlagEnum.values();
        for (DeleteFlagEnum type : types) {
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
