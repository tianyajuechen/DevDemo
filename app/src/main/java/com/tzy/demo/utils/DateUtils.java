package com.tzy.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String getCurrentTime() {
        Date currentTime = new Date(System.currentTimeMillis());
        SimpleDateFormat timeFormatter24 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return timeFormatter24.format(currentTime);
    }


    public static final String FORMAT1 = "yyyy-MM-dd";
    public static final String FORMAT2 = "yyyyMMdd_HHmmss_SSS";
    public static final String FORMAT3 = "MM月dd日 HH:mm";
    public static final String FORMAT4 = "MM月dd日";

    /**
     * 获取当前日期
     *
     * @param format 日期格式
     */
    public static String getCurrDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(new Date());
    }

    /**
     * 获取当前日期
     *
     * @return 2016-11-11
     */
    public static String getCurrDate() {
        return getCurrDate(FORMAT1);
    }

    /**
     * 格式化时间
     * @param timeMillis
     * @param format
     * @return
     */
    public static String formatDate(long timeMillis, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(new Date(timeMillis));
    }
}
