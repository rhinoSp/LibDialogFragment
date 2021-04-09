package com.rhino.dialog.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.rhino.log.LogUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * <p>The utils of time</p>
 *
 * @author LuoLin
 * @since Create on 2016/10/31.
 **/
public class TimeUtils {

    /**
     * The milliseconds in one second
     */
    public static final int MILLISECONDS_PER_SECOND = 1000;
    /**
     * The second in one minute
     */
    public static final int SECOND_PER_MINUTE = 60;
    /**
     * The minute in one hour
     */
    public static final int MINUTE_PER_HOUR = 60;
    /**
     * The second in one hour
     */
    public static final int SECOND_PER_HOUR = 3600;
    /**
     * The hour in one day
     */
    public static final int HOUR_PER_DAY = 24;
    /**
     * The minute in one day
     */
    public static final int MINUTE_PER_DAY = 1440;
    /**
     * The second in one day
     */
    public static final int SECOND_PER_DAY = 86400;
    /**
     * The milliseconds in one day
     */
    public static final int MILLISECONDS_PER_DAY = 86400000;
    /**
     * The day in one week
     */
    public static final int DAY_PRE_WEEK = 7;
    /**
     * The month in one year
     */
    public static final int MONTH_PRE_YEAR = 12;

    /**
     * Get the time zone.
     *
     * @return the time zone.
     */
    public int getTimeZone() {
        Calendar cal = Calendar.getInstance();
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        return ((zoneOffset + dstOffset) / MILLISECONDS_PER_SECOND) / (MINUTE_PER_HOUR * SECOND_PER_MINUTE);
    }

    /**
     * Get the offset of time zone.
     *
     * @return the offset of time zone.
     */
    public int getTimeZoneOffset() {
        return TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 1000;
    }

    /**
     * Get the day count of month.
     *
     * @param year  year
     * @param month month 1-12
     * @return the day count of month.
     */
    public int getMonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get the value of field.
     *
     * @param field Calendar.YEAR
     * @return the value of field
     */
    public int getCalendarField(int field) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(field);
    }

    /**
     * Get the local timestamp(second)
     *
     * @return second
     */
    public int getLocalSecond() {
        return (int) (System.currentTimeMillis() / 1000 + getTimeZoneOffset());
    }

    /**
     * Get the utc timestamp(second)
     *
     * @return second
     */
    public static int getUtcSecond() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * Get the utc timestamp(milliseconds)
     *
     * @return milliseconds
     */
    public static long getUtcMilliseconds() {
        return System.currentTimeMillis();
    }

    /**
     * Get the local timestamp(milliseconds)
     *
     * @return milliseconds
     */
    public int getLocalMilliseconds() {
        return (int) (System.currentTimeMillis() / 1000 + getTimeZoneOffset());
    }

    /**
     * Get the time string by timestamp(second)
     *
     * @param time   the timestamp
     * @param format the format string like yyyy-MM-dd
     * @return the time string like 2016-12-17
     */
    public static String formatTime(int time, String format) {
        return formatTime(time * 1000L, format);
    }

    /**
     * Get the time sting by timestamp(milliseconds)
     *
     * @param time   the timestamp
     * @param format the format string like yyyy-MM-dd
     * @return the time string like 2016-12-17
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatTime(Long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }


    /**
     * Get the current time sting.
     *
     * @param format the format string like yyyy-MM-dd
     * @return the time string like 2016-12-17
     */
    public static String formatCurrentTime(String format) {
        return formatTime(getUtcMilliseconds(), format);
    }

    /**
     * Get the timestamp by time string
     *
     * @param timeStr the time string like 2016-12-17
     * @param format  the format string like yyyy-MM-dd
     * @return the timestamp
     */
    @SuppressLint("SimpleDateFormat")
    public static long getTimeStampByTimeString(String timeStr, String format) {
        long time = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date d = sdf.parse(timeStr);
            time = d.getTime();
        } catch (ParseException e) {
            LogUtils.e(e);
        }
        return time;
    }

    /**
     * get the time stamp by time string
     *
     * @param time   the time string like 2016-12-17
     * @param format the format string like yyyy-MM-dd
     * @return milliseconds
     */
    @SuppressLint("SimpleDateFormat")
    public static long getMillisSecond(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            LogUtils.e(e);
        }
        return 0L;
    }

    /**
     * Second time convert to String like(12:45:30)
     *
     * @param totalSeconds the total second time
     * @return the string like 12:45:30 or 45:30
     */
    public static String second2TimeString(int totalSeconds) {
        DecimalFormat df = new DecimalFormat("00");
        StringBuilder sb = new StringBuilder();
        int hours = totalSeconds / SECOND_PER_HOUR;
        int minutes = (totalSeconds / SECOND_PER_MINUTE) % MINUTE_PER_HOUR;
        int seconds = totalSeconds % SECOND_PER_MINUTE;
        if (0 < hours) {
            sb.append(df.format(hours)).append(":");
        }
        sb.append(df.format(minutes)).append(":").append(df.format(seconds));
        return sb.toString();
    }

    /**
     * Second time convert to String like(02.03'04")
     *
     * @param totalSeconds the total second time
     * @return the string like 02.03'04" or 03'04"
     */
    public static String second2TimeString1(int totalSeconds) {
        DecimalFormat df = new DecimalFormat("00");
        StringBuilder sb = new StringBuilder();
        int hours = totalSeconds / SECOND_PER_HOUR;
        int minutes = (totalSeconds / SECOND_PER_MINUTE) % MINUTE_PER_HOUR;
        int seconds = totalSeconds % SECOND_PER_MINUTE;
        if (0 < hours) {
            sb.append(df.format(hours)).append(".");
        }
        sb.append(df.format(minutes)).append("\'").append(df.format(seconds)).append("\"");
        return sb.toString();
    }

    /**
     * Second time convert to String like(现在，xx秒钟前，xx分钟前，xx小时前，xx天前)
     *
     * @param second the total second time
     * @return the string like “现在，xx秒钟前，xx分钟前，xx小时前，xx天前”
     */
    public static String second2TimeString2(int second) {
        if (second <= 0) {
            return "现在";
        }
        if ((second < 60)) {
            return second + "秒钟前";
        }
        if ((second < 3600)) {
            return second / 60 + "分钟前";
        }
        if ((second < 3600 * 24)) {
            return second / 3600 + "小时前";
        }
        return second / 3600 / 24 + "天前";
    }

    /**
     * Format time date
     *
     * @param timeDate yyyy-MM-dd HH:mm:ss
     * @return 现在，xx秒钟前，xx分钟前，xx小时前，xx天前
     */
    public static String formatTimeDate(String timeDate) {
        long timestamp = getTimeStampByTimeString(timeDate, "yyyy-MM-dd HH:mm:ss");
        long currentTimestamp = getUtcMilliseconds();
        int second = (int) ((currentTimestamp - timestamp) / 1000);
        return second2TimeString2(second);
    }

}
