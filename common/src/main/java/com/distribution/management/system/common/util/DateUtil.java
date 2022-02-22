package com.distribution.management.system.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * @author admin
 */
@Slf4j
public class DateUtil {

    private DateUtil() {
    }

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * ISO8601格林威治天文台的标准时间,阿里云要求
     */

    public static final String DATE_FORMAT_MS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String DATE_FORMAT_11 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String DATE_FORMAT_01 = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_02 = "yyyy-MM-dd";

    public static final String DATE_FORMAT_03 = "yyyy-MM";

    public static final String DATE_FORMAT_04 = "yyyyMMdd";

    public static final String DAY_BEGIN = " 00:00:00";

    public static final String DAY_OVER = " 23:59:59";

    public static final String MONTH_FIRST_DAY = "-01";

    public static final String HOUR_BEGIN_MS = ":00:00.000";

    public static final String HOUR_END_MS = ":59:59.999";
    public final static String yyyyMMdd = "yyyyMMdd";

    public final static String HH_mm_ss = "HH:mm:ss";


    public static Date getNow() {
        return new Date();
    }


    /**
     * 指定格式获取时间
     */
    public static String formatDateMonth(String date, Integer i) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date startTime = ft.parse(date);
            calendar.setTime(startTime);
            calendar.add(Calendar.YEAR, i);
            String overTime = DateUtil.formatDate(calendar.getTime(), DateUtil.DATE_FORMAT_02);
            return overTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算俩个时间段相差多少天
     */
    public static Integer calute(String time) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date startTime = ft.parse(time);
            calendar.setTime(startTime);
            Date overTime = calendar.getTime();
            Date startDate = new Date();
            int a = (int) ((overTime.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
            if (a <= 0) {
                return 0;
            } else {
                return a;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 指定格式获取时间
     */
    public static String date(String date, Integer i) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date startTime = ft.parse(date);
            calendar.setTime(startTime);
            calendar.add(Calendar.MONTH, i);
            String overTime = DateUtil.formatDate(calendar.getTime(), DateUtil.DATE_FORMAT_02);
            return overTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 计算俩个时间段相差多少天
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 俩个时间段相差的天数
     * 如果结果大于0，则后者时间段大于前者时间段；</br>
     * 如果结果等于0，则后者时间段与前者时间段相等；</br>
     * 如果结果小于0，则后者时间段小于前者时间段；
     */
    public static int calculateDifferenceTwoDate(Date startTime, Date endTime) {
        return (int) ((endTime.getTime() - startTime.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 计算两个日期间隔的月份数
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 间隔的月份数
     */
    public static int getMonthSpace(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int result = 0;
        Calendar cal1 = null;
        Calendar cal2 = null;
        try {
            cal1 = new GregorianCalendar();
            cal1.setTime(sdf.parse(date1));
            cal2 = new GregorianCalendar();
            cal2.setTime(sdf.parse(date2));
            result =
                    (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
        } catch (ParseException e) {
            log.error("解析失败。", e);
        }
        return result == 0 ? 0 : Math.abs(result);
    }

    public static Date getDayStart(String data) {
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(DATE_FORMAT_02);
        LocalDate localDate = LocalDate.parse(data, formatter);
        LocalDateTime time = localDate.atStartOfDay();
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getDayStart(Date date) {
        LocalDateTime localDateTime = changeLocalDateTime(date);
        LocalDate localDate = localDateTime.toLocalDate();
        LocalDateTime time = localDate.atStartOfDay();
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getDayStartV2(Date date) {
        LocalDateTime localDateTime = changeLocalDateTime(date);
        LocalDateTime dateEnd = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
        return Date.from(dateEnd.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String getDayStartStr(Date date) {
        date = getDayStartV2(date);
        return formatDate(date, DateUtil.DATE_FORMAT_01);
    }

    public static String getDayEndStr(Date date) {
        date = getDayEnd(date);
        return formatDate(date, DateUtil.DATE_FORMAT_01);
    }

    /**
     * 获取前一个月
     */
    public static String getBeforeMonth(String date) {
        DateTime dateTime = new DateTime(date);
        DateTime beforeMonth = dateTime.minusMonths(1);
        return beforeMonth.toString(DATE_FORMAT_02);
    }

    public static String getNowDayStart() {
        LocalDate localDate = LocalDate.now();
        LocalDateTime time = localDate.atStartOfDay();
        return formatDate(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()), DATE_FORMAT_01);
    }

    public static Date getMonthStart(String data) {
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(DATE_FORMAT_02);
        LocalDate localDate = LocalDate.parse(data + "-01", formatter);
        LocalDateTime time = localDate.atStartOfDay();
        LocalDateTime first = time.with(TemporalAdjusters.firstDayOfMonth());
        return Date.from(first.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @Description String 时间按照指定格式转 Date 类型
     * @Date 2021/4/12 15:16
     */
    public static Date formatToDate(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date parse = null;
        try {
            parse = dateFormat.parse(date);
        } catch (ParseException e) {
            log.error("转换时间异常：[{}]:", e.getMessage());
        }
        return parse;
    }

    /***
     * 获取前一个月时间
     * @param time1
     * @param time2
     * @param num
     * @return
     */
    public static boolean interval(Date time1, Date time2, long num) {
        LocalDateTime localDateTime1 = changeLocalDateTime(time1);
        LocalDateTime localDateTime2 = changeLocalDateTime(time2);
        return localDateTime1.plusDays(num).isAfter(localDateTime2);
    }

    /***
     * 获取前一个月时间
     * @param time1
     * @param time2
     * @return
     */
    public static boolean interval(Date time1, Date time2) {
        LocalDateTime localDateTime1 = changeLocalDateTime(time1);
        LocalDateTime localDateTime2 = changeLocalDateTime(time2);
        return localDateTime1.isAfter(localDateTime2);
    }

    public static Date getMonthEnd(LocalDateTime time) {
        LocalDateTime lastDay = time.with(TemporalAdjusters.lastDayOfMonth());
        return Date.from(lastDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getMonthEnd(String data) {
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(DATE_FORMAT_02);
        LocalDate localDate = LocalDate.parse(data + "-01", formatter);
        LocalDateTime time = LocalDateTime.of(localDate, LocalTime.MAX);
        LocalDateTime lastDay = time.with(TemporalAdjusters.lastDayOfMonth());
        return Date.from(lastDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getMonthEnd(Date date) {
        LocalDateTime time = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime lastDay = time.with(TemporalAdjusters.lastDayOfMonth());
        return Date.from(lastDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @Description <按照月维度增加时间>
     * @Date 2021/3/15 10:27
     * @Param date
     * @Param month
     * @Return Date
     */
    public static Date dateAddMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * @Description <按照年维度增加时间>
     * @Date 2021/3/15 10:34
     * @Param date
     * @Param year
     * @Return Date
     */
    public static Date dateAddYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 计算俩个时间段相差多少天
     */
    public static Integer caluteDateValue(Date startTime, Date endTime) {
        int a = (int) ((endTime.getTime() - startTime.getTime()) / (1000 * 3600 * 24));
        if (a <= 0) {
            return 0;
        }
        return a;
    }


    /**
     * @Description 判断日期是否是月末
     * @Date 2021/3/12 10:08
     * @Param date
     * @Return boolean
     */
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static Date getDayEnd(String data) {
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(DATE_FORMAT_02);
        LocalDate localDate = LocalDate.parse(data, formatter);
        LocalDateTime time = LocalDateTime.of(localDate, LocalTime.MAX);
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getDayEnd(Date date) {
        LocalDateTime localDateTime = changeLocalDateTime(date);
        LocalDateTime dateEnd = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
        return Date.from(dateEnd.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getPreMonthStart(LocalDateTime time) {
        time = time.plusMonths(-1);
        LocalDateTime firstday = time.with(TemporalAdjusters.firstDayOfMonth());
        return Date.from(firstday.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 当前传入时间与当前时间差（年月日，时分秒）
     *
     * @param time
     * @return
     */
    public static String formatRemainingTime(Date time) {
        LocalDateTime localDateTime = changeLocalDateTime(time);
        LocalDateTime nowTime = LocalDateTime.now();
        int year = localDateTime.getYear() - nowTime.getYear();
        int month = localDateTime.getMonthValue() - nowTime.getMonthValue();
        int day = localDateTime.getDayOfMonth() - nowTime.getDayOfMonth();
        int hour = localDateTime.getHour() - nowTime.getHour();
        int minute = localDateTime.getMinute() - nowTime.getMinute();
        int second = localDateTime.getSecond() - nowTime.getSecond();

        String str = (year > 0 ? year + "年" : "")
                + (month > 0 ? month + "月" : "")
                + (day > 0 ? day + "天" : "")
                + (hour > 0 ? hour + "时" : "")
                + (minute > 0 ? minute + "分" : "")
                + (second > 0 ? second + "秒" : "");

        return str;
    }

    /**
     * @author jiamo
     * @description <按照天维度增加时间>
     * @since 2021/2/25 11:31
     */
    public static Date dateDayAdd(Date time, long num) {
        LocalDateTime localDateTime = changeLocalDateTime(time);
        localDateTime = localDateTime.plusDays(num);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private static LocalDateTime changeLocalDateTime(Date time1) {
        Instant instant = time1.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 指定格式获取时间
     */
    public static String formatDate(Date date, String dateFormat) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(dateFormat);
    }

    public static String formatTime(Date date, String dateFormat) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(dateFormat);
    }

    /**
     * 获取前一天
     */
    public static String getBeforeDay(String date) {
        DateTime dateTime = new DateTime(date);
        DateTime beforeDay = dateTime.minusDays(1);
        return beforeDay.toString(DATE_FORMAT_02);
    }


    /**
     * 获取前一天
     */
    public static Date getBefore(String date) {
        DateTime dateTime = new DateTime(date);
        DateTime beforeDay = dateTime.minusDays(1);
        return beforeDay.toDate();
    }

    /**
     * 时间往前推几分钟
     */
    public static Date getBeforeMinute(Date date, int minute) {
        DateTime dateTime = new DateTime(date);
        DateTime beforeDay = dateTime.minusMinutes(minute);
        return beforeDay.toDate();
    }

    /**
     * 获取月份的最后一天
     */
    public static String getMonthLastDay(String date) {
        return getBeforeDay(getAfterMonth(date + MONTH_FIRST_DAY));
    }

    /**
     * 获取后一个月
     */
    public static String getAfterMonth(String date) {
        DateTime dateTime = new DateTime(date);
        DateTime beforeMonth = dateTime.plusMonths(1);
        return beforeMonth.toString(DATE_FORMAT_03);
    }

    /**
     * 获取指定的前几天
     */
    public static String getBeforeDays(String date, int days) {
        DateTime dateTime = new DateTime(date);
        DateTime beforeDay = dateTime.minusDays(days);
        return beforeDay.toString(DATE_FORMAT_02);
    }

    /**
     * 获取指定日期的后几天
     */
    public static Date getAfterDate(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        DateTime afterDate = dateTime.plusDays(days);
        return afterDate.toDate();
    }

    /**
     * 获取hou一天
     */
    public static String getAfterDay(String date) {
        DateTime dateTime = new DateTime(date);
        DateTime beforeDay = dateTime.plusDays(1);
        return beforeDay.toString(DATE_FORMAT_02);
    }

    /**
     * 获取后一天
     */
    public static String getAfterDay(Date date) {
        LocalDateTime localDateTime = changeLocalDateTime(date);
        Date afterDay = Date.from(localDateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return formatDate(afterDay, DATE_FORMAT_02);
    }


    /**
     * 获取指定天的开始时间
     */
    public static String getDayBegin(String day) {
        return day + DAY_BEGIN;
    }

    /**
     * 获取指定天的结束时间
     */
    public static String getDayOver(String day) {
        return day + DAY_OVER;
    }

    /**
     * 获取指定日期的上一个月
     */
    public static String getPreMonth(String date) {
        DateTime dateTime = new DateTime(date);
        DateTime preMonth = dateTime.minusMonths(1);
        return preMonth.toString(DATE_FORMAT_03);
    }

    public static String formatStrDate(String date, String dateFormat) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(dateFormat);
    }

    public static int getMonthEndDay(String date) {
        DateTime dateTime = new DateTime(date);
        int lastDay = dateTime.dayOfMonth().getMaximumValue();
        return lastDay;
    }

    public static int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);
        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }


    public static boolean overtimeCode(Date firstTime, Date lastTime, long overtime) {
        boolean flag = false;
        long time1 = firstTime.getTime();
        long time2 = lastTime.getTime();
        long diff = time2 - time1;
        if (diff / 1000 > overtime) {
            flag = true;
        }
        return flag;
    }

    /**
     * 将Date类型时间转换为字符串
     *
     * @param date
     * @return
     */
    public static String toString(Date date) {
        String time;
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("yyyy-MM-dd HH");
        time = formatter.format(date);
        return time;
    }

    /**
     * 将Date类型时间转换为字符串
     *
     * @param date
     * @return
     */
    public static String toString(Date date, String dateFormat) {
        String time;
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern(dateFormat);
        time = formatter.format(date);
        return time;
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }


    /**
     * @Description 获得当前时间的前一个小时
     * @Date 2020/12/25 16:36
     * @Param date
     * @Return String
     */
    public static String getTimeHourBefore(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
        return df.format(calendar.getTime());
    }

    /**
     * @Description 获得当前时间的前一个小时
     * @Date 2020/12/25 16:36
     * @Param date
     * @Return String
     */
    public static Date getHourBefore(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -1);
        return calendar.getTime();
    }

    /**
     * @Description 获得当前时间的后一个小时
     * @Date 2020/12/25 16:36
     * @Param date
     * @Return String
     */
    public static Date getHourAfter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 1);
        return calendar.getTime();
    }

    /**
     * @Description 获取小时账单的当前月份
     * @Date 2021/1/9 14:06
     * @Param [date]
     * @Return java.lang.String
     */
    public static String getCurrentMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -1);
        return getString(calendar);
    }

    /**
     * @Description 获取小时账单上个月份
     * @Date 2021/1/9 14:12
     * @Param [date]
     * @Return java.lang.String
     */
    public static String getLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -1);
        // 再减去一个月
        calendar.add(Calendar.MONTH, -1);
        return getString(calendar);

    }

    private static String getString(Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_04);
        String format = df.format(calendar.getTime());
        if (Objects.nonNull(format)) {
            return format.substring(0, 6);
        }
        return null;
    }

/*    public static void main(String[] args) {
        String date = "2021-01-01 00:00:00";
        Date toDate = strToDate(date);
        System.out.println(getCurrentMonth(toDate));
        System.out.println(getLastMonth(toDate));
    }*/


    /**
     * @Description yyyy-MM-dd 和yyyy-MM 字符串 -> yyyyMM 数字
     * @Date 2020/12/29 11:31
     * @Param [date]
     * @Return java.lang.Integer
     */
    public static Integer formatMonth(String date) {
        if (Objects.nonNull(date)) {
            String substring = date.replaceAll("-", "").substring(0, 6);
            return Integer.parseInt(substring);
        }
        return null;
    }

    /**
     * @Description yyyy-MM-dd 字符串 -> dd 数字
     * @Date 2020/12/29 11:31
     * @Param [date]
     * @Return java.lang.Integer
     */
    public static Integer formatDay(String date) {
        if (Objects.nonNull(date)) {
            String substring = date.replaceAll("-", "").substring(6);
            return Integer.parseInt(substring);
        }
        return null;
    }

    /**
     * @Description 时间加上一小时
     * @Date 2020/12/29 15:00
     * @Param [start]
     * @Return java.util.Date
     */
    public static Date addHour(Date start) {
        if (start == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.HOUR, 1);
        return calendar.getTime();
    }

    public static String getHourBeginMs(String dateStr) {
        return dateStr + HOUR_BEGIN_MS;
    }

    public static String getHourEndMs(String dateStr) {
        return dateStr + HOUR_END_MS;
    }


    /**
     * 获取后三天
     */
    public static Date getDay(Date date) {
        DateTime dateTime = new DateTime(date);
        DateTime beforeDay = dateTime.plusDays(3);
        return beforeDay.toDate();
    }

    /**
     * 比较日期大小
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return true：date1大于date2(date1在date2之后或相等)；false：date1小于等于date2(date1在date2之前)
     */
    public static boolean dateCompare(Date date1, Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_04);
        //当前的是2021-03-26
        String dateFirst = dateFormat.format(date1);
        //2021-03-31
        String dateLast = dateFormat.format(date2);
        int dateFirstIntVal = Integer.parseInt(dateFirst);
        int dateLastIntVal = Integer.parseInt(dateLast);
        if (dateFirstIntVal > dateLastIntVal) {
            return true;
        }
        return false;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 获取指定时间的整点时间
     *
     * @return 整点时间
     */
    public static Date getCurrHourDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前时间的零分零秒   类型String   只设置分钟 与 秒
     *
     * @return
     */
    public static String getCurrHourTime(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        date = ca.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 转换时区
     *
     * @param date
     * @return
     */
    public static Date parseStr2Date(String date) {
        try {
            date = date.replace("Z", " UTC");
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            return utcFormat.parse(date);
        } catch (Exception e) {
            logger.error("格式化日期错误", e);
            return null;
        }
    }

    public static Date parseStrDate(String date) {
        try {
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd");
            return utcFormat.parse(date);
        } catch (Exception e) {
            logger.error("格式化日期错误", e);
            return null;
        }
    }


    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        try {
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return utcFormat.parse(date);
        } catch (Exception e) {
            logger.error("格式化日期错误", e);
            return null;
        }
    }

    /**
     * yyyyMMdd
     *
     * @param date
     * @return
     */
    public static Date parseStr3Date(String date) {
        try {
            SimpleDateFormat utcFormat = new SimpleDateFormat(yyyyMMdd);
            return utcFormat.parse(date);
        } catch (Exception e) {
            logger.error("格式化日期错误", e);
            return null;
        }
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }


    /**
     * 对日期的【秒】进行加/减
     *
     * @param date    日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date    日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date  日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date  日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date   日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date  日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }

    /**
     * 获取指定时间 yyyyMMdd
     *
     * @return
     */
    public static String getTightDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat(yyyyMMdd, Locale.CHINA);
        String time = df.format(calendar.getTime());
        return time;
    }

    /**
     * 获取当前时间的零点零分零秒   类型String
     *
     * @return
     */
    public static String getCurrDateZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(zero);
    }

    /**
     * 获取传入时间的零点零分零秒   类型String
     *
     * @return
     */
    public static String getCurrDateZero2(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(zero);
    }


    /**
     * 获取当前时间的23点零分零秒   类型String
     */
    public static String getCurrDateTwentyThree() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date thenThree = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(thenThree);
    }

    /**
     * 获取当前时间的零点零分零秒   类型String    自定义日期
     *
     * @return
     */
    public static String getCurrDateZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(zero);
    }

    /**
     * 获取当前时间的23点零分零秒   类型String  自定义日期 59分 59秒
     */
    public static String getCurrDateTwentyThree(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date thenThree = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(thenThree);
    }


    /**
     * 设置 日开始时间   如 2020-07-30 00:00:00  类型Date
     */
    public static Date getDayStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date time = calendar.getTime();
        return time;
    }

    /**
     * 日减一
     */
    public static Date deleteOneDay(Calendar calendar, Date date) {
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date time = calendar.getTime();
        return time;
    }

    //设置 日结束时间  如 2020-07-30 23:59:59  类型Date
    public static Date getDayEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        Date time = calendar.getTime();
        return time;
    }


    /**
     * @param startTime
     * @param activityGapTime
     * @return
     */
    public static Date addMinute(Date startTime, float activityGapTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        // 24小时制
        cal.add(Calendar.MINUTE, (int) activityGapTime);
        date = cal.getTime();
        try {
            return format.parse(format.format(date));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 计算时间差
     *
     * @param time 指定的时间，格式为：yyyy-MM-dd HH:mm:ss
     * @return 当前时间和指定时间的时间差（秒）
     * @throws ParseException
     */
    public static long getTimeDifference(String time) throws ParseException {
        long between = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String systemTime = sdf.format(new Date());
        Date end = null;
        Date begin = null;
        // 将截取到的时间字符串转化为时间格式的字符串
        end = sdf.parse(time);
        begin = sdf.parse(systemTime);
        //除以1000是为了转换成秒
        between = Math.abs(end.getTime() - begin.getTime()) / 1000;

        return between;
    }

    /**
     * 计算时间差
     *
     * @param date 指定的时间，格式为：yyyy-MM-dd HH:mm:ss
     * @return 当前时间和指定时间的时间差（秒）
     * @throws ParseException
     */
    public static Long getTimeDifference(Date date) throws ParseException {
        long between = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String systemTime = sdf.format(new Date()).toString();
        Date end = null;
        Date begin = null;
        // 将截取到的时间字符串转化为时间格式的字符串
        end = sdf.parse(sdf.format(date));
        begin = sdf.parse(systemTime);
        //除以1000是为了转换成秒
        between = Math.abs(end.getTime() - begin.getTime()) / 1000;

        return between;
    }


    public static List<String> get24Hours() {
        List<String> betweenList = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            betweenList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        return betweenList;
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        //同一年
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                //闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
            //不同年
        } else {
            return day2 - day1;
        }
    }

    /**
     * date2比date1多的分钟数
     *
     * @param start
     * @param end
     * @return
     */
    public static int differentMinute(Date start, Date end) {
        long nm = 1000 * 60;
        Long min = (end.getTime() - start.getTime()) / nm;

        return min.intValue();
    }

    /**
     * date2比date1多的分钟数
     *
     * @param start
     * @param end
     * @return
     */
    public static BigDecimal differentHour(Date start, Date end) {
        long nm = 1000 * 60 * 60;
        BigDecimal hour = (new BigDecimal(end.getTime()).subtract(new BigDecimal(start.getTime()))).divide(new BigDecimal(nm), 1, BigDecimal.ROUND_HALF_UP);

        return hour;
    }

    /**
     * date2比date1多的秒数
     *
     * @param start
     * @param end
     * @return
     */
    public static long differentSecond(Date start, Date end) {
        long nm = 1000;
        Long min = (end.getTime() - start.getTime()) / nm;

        return min;
    }

    /**
     * 获取当前系统的年份
     *
     * @return
     */
    public static Integer getCurrYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return Integer.valueOf(year);
    }

    /**
     * 获取今天是周几
     *
     * @return
     */
    public static int getWeek() {
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance();
        return weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }


    /**
     * 获得月第一天 2021-11-01 00:00:00
     *
     * @return
     */
    public static Date getFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得月最后一天  2021-11-30 23:59:59
     *
     * @return
     */
    public static Date getLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 本年第一天
     *
     * @return
     */
    public static Date getFirstYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 获取年的第一天
     *
     * @return
     */
    public static Date getCurrYearFirst(Date date) {
        Calendar currCal = Calendar.getInstance();
        currCal.setTime(date);
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取年的最后一天
     *
     * @return
     */
    public static Date getCurrYearLast(Date date) {
        Calendar currCal = Calendar.getInstance();
        currCal.setTime(date);
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }


    /**
     * 时间戳转Date对象
     *
     * @param timestamp 时间戳（毫秒）
     * @return
     */
    public static Date parseToDate(long timestamp) {
        LocalDateTime localDateTime = new Timestamp(timestamp).toLocalDateTime();
        return parseToDate(localDateTime);
    }

    private static Date parseToDate(LocalDateTime localDateTime) {
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zid);
        return Date.from(zdt.toInstant());
    }

    /**
     * @Description 校验时间格式是否为yyyy-MM-dd
     * @Date 2021/1/5 15:28
     * @Param [date]
     * @Return boolean
     */
    public static boolean isDateVail(String date) {
        // 指定的时间格式
        DateTimeFormatter dtf = DateTimeFormat.forPattern(DATE_FORMAT_02);
        boolean flag = true;
        try {
            dtf.parseDateTime(date);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static Date strDate(String dateTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT_02);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    /**
     * 获取当前时间的零分零秒   类型Date   只设置分钟 与 秒
     *
     * @return
     */
    public static Date getCurrDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 开始时间 大于 结束时间 返回true
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Boolean verifyDate(Date startTime, Date endTime) {
        long start = startTime.getTime();
        long end = endTime.getTime();
        if (start > end) {
            return true;
        }
        return false;
    }

}
