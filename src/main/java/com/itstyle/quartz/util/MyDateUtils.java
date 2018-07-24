package com.itstyle.quartz.util;



import org.apache.commons.lang.time.DateFormatUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期工具类, 通过静态继承的方式, 扩展common-lang3中的DateUtils
 */
public class MyDateUtils  {

    /**
     * 日期规则定义
     */
    public static final String[] DATE_PATTERN = new String[]{
            "yyyyMMdd", //编号0
            "yyyyMMddHHmmss", //编号1
            "yyyyMMddHHmm", //编号2
            "yyyy-MM-dd HH:mm:ss", //编号3
            "yyyyMMdd HH:mm", //编号4
            "yyyy-MM-dd", //编号5
            "mm:ss",//编号6
            "yyyy-MM-dd HH:mm",//编号7
            "yyyy/MM/dd", //编号8
            "HH:mm:ss",//编号9
    };

    /**
     * 最强验证日期的正则表达式,添加了闰年的验证
     * YYYYMMDD
     * YYYY-MM-DD
     * YYYY/MM/DD
     * YYYY_MM_DD
     * YYYY.MM.DD的形式
     *
     * match : 2008-2-29 2008/02/29
     * not match : 2008-2-30   2007-2-29
     */
    public static final String YYYY_MM_DD_REGEX = "^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))$";

    /**
     * 获取今天的日期, 不包括时间. 如yyyy-MM-dd 00:00:00.000
     *
     * @return yyyy-MM-dd 00:00:00.000
     */
    public static Date todayDate() {
        Calendar result = Calendar.getInstance();
        result.set(Calendar.HOUR_OF_DAY, 0);
        result.set(Calendar.MINUTE, 0);
        result.set(Calendar.SECOND, 0);
        result.set(Calendar.MILLISECOND, 0);
        return result.getTime();
    }

    /**
     * 根据开始日期和结束日期, 计算日期差
     *
     * @param from 开始日期
     * @param to   结束日期
     * @return 两个日期之间的天数
     */
    public static int getDays(Date from, Date to) {
        int days = 0;
        if (from.before(to)) {
            while (from.before(to)) {
                days++;
                from = addDays(from, 1);
            }
        } else {
            days = -1;
        }
        return days;
    }


    /**
     * 获取时间段(闭区间)内的每一天
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 日期段内的每一天
     */
    public static Set<Date> getEveryDay(Date start, Date end) {
        Set<Date> result = new HashSet<Date>();
        while (start.getTime() <= end.getTime()) {
            result.add(start);
            start = addDays(start, 1);
        }
        return result;
    }

    /**
     * 根据今天的时间 添加 numDay 天的时间
     *
     * @param numDay 要改变的 天数
     * @return
     */
    public static Date addDays(Date date,int numDay) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numDay);//把日期往后增加一天.整数往后推,负数往前移动
        Date tempDay = calendar.getTime();
        return tempDay;
    }

    /**
     * 根据今天的时间 添加 numDay 天的时间
     *
     * @param numDay 要改变的 天数
     * @return
     */
    public static Date addDays(int numDay) {
        Date todayDate = MyDateUtils.todayDate();//取时间
        return addDays(todayDate,numDay);
    }

    /**
     * 将日期对象格式化为yyyy-MM-dd格式字符串
     *
     * @param date 日期对象
     * @return yyyy-MM-dd格式字符串
     */
    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, DATE_PATTERN[5]);
    }

    /**
     * 将日期对象格式化为yyyy-MM-dd HH:mm:ss格式字符串
     *
     * @param date 日期对象
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, DATE_PATTERN[3]);
    }

    /**
     * 将日期格式化为指定格式字符串
     *
     * @param date    日期对象
     * @param pattern 字符串格式
     * @return 日期字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, pattern);
    }

    public static Boolean isDate(String dateStr) {
        return checkTimePattern(dateStr, YYYY_MM_DD_REGEX);
    }
    public static Boolean checkTimePattern(String dateStr, String pattern) {
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(dateStr);
        return m.find();
    }


    public static void main(String[] args) {
        String str = "2008-2-21";
        Pattern p=Pattern.compile(YYYY_MM_DD_REGEX);
        Matcher m=p.matcher(str);
        boolean matches = m.matches();
        System.out.println(matches);
    }
}