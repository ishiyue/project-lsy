package com.distribution.management.system.common.enums;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * @author admin
 */

@JSONType(serializer = EnumBaseSerializer.class)
public enum DataItemsEnum {
    /**
     * 终端抄表时间
     */
    ENDPOINT_READING_TIME("终端抄表时间"),

    /**
     * 正向有功电能数据
     */
    FORWARD_ACTIVE_ELECTRIC_ENERGY_DATA("正向有功电能数据"),

    /**
     * 正向无功电能数据
     */
    FORWARD_REACTIVE_ELECTRIC_ENERGY_DATA("正向无功电能数据"),


    /**
     * 反向有功电能数据
     */
    REVERSE_ACTIVE_ELECTRIC_ENERGY_DATA("反向有功电能数据"),


    /**
     * 反向无功电能数据
     */
    REVERSE_REACTIVE_ELECTRIC_ENERGY_DATA("反向无功电能数据"),

    /**
     * 总有功功率
     */
    ACTIVE_POWER_TOTAL("总有功功率"),

    /**
     * A相有功功率
     */
    ACTIVE_POWER_A("A相有功功率"),


    /**
     * B相有功功率
     */
    ACTIVE_POWER_B("B相有功功率"),


    /**
     * C相有功功率
     */
    ACTIVE_POWER_C("C相有功功率"),

    /**
     * 总无功功率
     */
    REACTIVE_POWER_TOTAL("总无功功率"),


    /**
     * A相无功功率
     */
    REACTIVE_POWER_A("A相无功功率"),


    /**
     * B相无功功率
     */
    REACTIVE_POWER_B("B相无功功率"),


    /**
     * C相无功功率
     */
    REACTIVE_POWER_C("C相无功功率"),


    /**
     * 总功率因数
     */
    POWER_FACTOR_TOTAL("总功率因数"),


    /**
     * A相功率因数
     */
    POWER_FACTOR_A("A相功率因数"),


    /**
     * B相功率因数
     */
    POWER_FACTOR_B("B相功率因数"),


    /**
     * C相功率因数
     */
    POWER_FACTOR_C("C相功率因数"),


    /**
     * A相电压
     */
    VOLTAGE_A("A相电压"),

    /**
     * B相电压
     */
    VOLTAGE_B("B相电压"),


    /**
     * C相电压
     */
    VOLTAGE_C("C相电压"),


    /**
     * A相电流
     */
    CURRENT_A("A相电流"),


    /**
     * B相电流
     */
    CURRENT_B("B相电流"),


    /**
     * C相电流
     */
    CURRENT_C("C相电流"),


    /**
     * 零序电流
     */
    CURRENT_ZERO_SEQUENCE("零序电流"),


    /**
     * 总视在功率
     */
    TOTAL_APPARENT_POWER("总视在功率"),


    /**
     * A相视在功率
     */
    APPARENT_POWER_A("A相视在功率"),


    /**
     * B相视在功率
     */
    APPARENT_POWER_B("B相视在功率"),


    /**
     * C相视在功率
     */
    APPARENT_POWER_C("C相视在功率");


    private String content;


    DataItemsEnum(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public String getCode() {
        return this.name();
    }
}
