package com.lsy.test.user.test;

import org.springframework.format.datetime.DateFormatter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class TimeTest {
    /**
     * Date对象转LocalDateTime对象
     *
     * @param date
     * @return
     */
    public static LocalDateTime parseToLocalDateTime(Date date) {
        ZoneId zid = ZoneId.systemDefault();
        Instant ins = date.toInstant();
        return ins.atZone(zid).toLocalDateTime();
    }
    /**
     * LocalDateTime转Date对象
     *
     * @param localDateTime
     * @return
     */
    private static Date parseToDate(LocalDateTime localDateTime) {
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zid);
        return Date.from(zdt.toInstant());
    }
    /**
     * 日期提前几年
     *
     * @param date  Date对象
     * @param years 年数
     * @return
     */
    public static Date getDateBeforeYears(Date date, long years) {
        LocalDateTime localDateTime = parseToLocalDateTime(date);
        return parseToDate(localDateTime.minusYears(years));
    }
    /**
     * 获取格式化的日期
     *
     * @param date      Date对象
     * @param formatter 日期格式
     * @param charc     分隔符 {@see tech.geeu.commons.consts.CharacterConsts}
     * @return
     *//*
    public static String getDate(Date date, DateFormatter formatter, String charc) {
        LocalDateTime localDateTime = parseToLocalDateTime(date);
        String pattern = patternConvert(formatter, charc);
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }*/

    public static void main(String[] args) {
        Date dateBeforeYears = getDateBeforeYears(new Date(), 1);
        System.out.println();
    }
}
