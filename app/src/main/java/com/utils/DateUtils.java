package com.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.blankj.utilcode.util.StringUtils.isEmpty;
import static java.lang.Math.random;

/**
 * 日期工具类
 *
 * @date 2013-04-03
 */
public class DateUtils {
    /**
     * 完整时间格式
     */
    private static final String REGEX = "yyyy-MM-dd HH:mm:ss";

    /**
     * 显示时间
     *
     * @param time
     * @return
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static String showTime(String time) {
        String showtime = "";
        try {
            if (isEmpty(time)) {
                return showtime;
            }
            long currentTime = System.currentTimeMillis();
            SimpleDateFormat formatter = new SimpleDateFormat(REGEX);
            Date date = formatter.parse(time);
            long passtime = currentTime - date.getTime();
            long second = passtime / 1000;
            if (second < 0) {
                showtime = "刚刚";
            } else if (second >= 0 && second < 60) {
                showtime = second + "秒前";
            } else if (second >= 60 && second < 60 * 60) {
                showtime = (second / 60 + 1) + "分钟前";
            } else if (second >= 60 * 60 && second < 60 * 60 * 24) {
                showtime = (second / 60 / 60) + "小时前";
            } else {
                showtime = showDate(time);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return showtime;
    }


    /**
     * 返回yyyy-MM-dd HH:mm:ss格式字符串的日期部分
     *
     * @param time
     * @return
     */
    public static String showDate(String time) {
        if (isEmpty(time)) {
            return "";
        }
        return time.split(" ")[0];
    }

    /**
     * 获取两个时间间隔的天数
     *
     * @param v1
     * @param v2
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDay(Object v1, Object v2, String regex) {
        try {
            if (v1 != null && v2 != null) {
                SimpleDateFormat formatter = new SimpleDateFormat(regex);
                Date date1 = formatter.parse(String.valueOf(v1));
                Date date2 = formatter.parse(String.valueOf(v2));
                long day = date2.getTime() - date1.getTime();
                day = day / 1000 / 60 / 60 / 24;
                return String.valueOf(day);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getZhongWenTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    public static String getYMTime(String date) {
        String dateStr = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = dateFormat.parse(date);
            SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
            dateStr = format.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 比较日期是否相等
     */
    public static Boolean DateCompress(String oldDate, String newDate) {
        boolean bool = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date, date1;
        try {
            date = sdf.parse(oldDate);
            date1 = sdf.parse(newDate);
            bool = date.equals(date1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * 判断 传入的日期 是否比今天早
     *
     * @param time
     * @return true 比今天早 (今天也算)， false 比今天晚
     * @throws ParseException
     */
    public static Boolean DateCompress(String time) {
        boolean bool = false;
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date, date1;
        try {
            date = sdf.parse(time);
            date1 = sdf.parse(sdf.format(nowdate));
            boolean flag = date.before(nowdate);
            bool = flag;
            if (date.equals(date1)) {
                bool = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static Date stringToDate(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            // Fri Feb 24 00:00:00 CST 2012
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 2012-02-24
        date = java.sql.Date.valueOf(str);

        return date;
    }

    /**
     * 将服务端时间2014-07-22 01:34:11转为 yyyy-MM-dd格式
     *
     * @param serviceTime
     * @return
     */
    public static String serviceTimeToYearDay(String serviceTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = sdf.parse(serviceTime);
            SimpleDateFormat resultSdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            serviceTime = resultSdf.format(date);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return serviceTime;

    }

    /**
     * 获取系统当前日期 yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(new Date());
    }

    /**
     * 获取多少天之前日期
     *
     * @param beforDay
     * @return
     */
    public static String getBeforDate(int beforDay, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }
        long currTime = System.currentTimeMillis();
        long time = currTime / 1000 - beforDay * 24 * 60 * 60;
        String str = DateUtils.toString(time, pattern);
        return str;
    }

    /**
     * 将一个字符串转换成日期格式
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date toDate(String date, String pattern) {
        if ("".equals("" + date)) {
            return null;
        }
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date newDate = new Date();
        try {
            newDate = sdf.parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDate;
    }

    /**
     * 把日期转换成字符串型
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String toString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }
        String dateString = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            dateString = sdf.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dateString;
    }

    public static String toString(Long time, String pattern) {
        if (time > 0) {
            if (time.toString().length() == 10) {
                time = time * 1000;
            }
            Date date = new Date(time);
            String str = DateUtils.toString(date, pattern);
            return str;
        }
        return "";
    }


    /**
     * 获取上个月的开始结束时间
     *
     * @return
     */
    public static String[] getLastMonth() {
        // 取得系统当前时间
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        // 取得系统当前时间所在月第一天时间对象
        cal.set(Calendar.DAY_OF_MONTH, 1);

        // 日期减一,取得上月最后一天时间对象
        cal.add(Calendar.DAY_OF_MONTH, -1);

        // 输出上月最后一天日期
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String months = "";
        String days = "";

        if (month > 1) {
            month--;
        } else {
            year--;
            month = 12;
        }
        if (!(String.valueOf(month).length() > 1)) {
            months = "0" + month;
        } else {
            months = String.valueOf(month);
        }
        if (!(String.valueOf(day).length() > 1)) {
            days = "0" + day;
        } else {
            days = String.valueOf(day);
        }
        String firstDay = "" + year + "-" + months + "-01";
        String lastDay = "" + year + "-" + months + "-" + days;

        String[] lastMonth = new String[2];
        lastMonth[0] = firstDay;
        lastMonth[1] = lastDay;

        //  System.out.println(lastMonth[0] + "||" + lastMonth[1]);
        return lastMonth;
    }


    /**
     * 获取当月的开始结束时间
     *
     * @return
     */
    public static String[] getCurrentMonth() {
        // 取得系统当前时间
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        // 取得系统当前时间所在月第一天时间对象
        cal.set(Calendar.DAY_OF_MONTH, 1);

        // 日期减一,取得上月最后一天时间对象
        cal.add(Calendar.DAY_OF_MONTH, -1);

        // 输出上月最后一天日期
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String months = "";
        String days = "";


        if (!(String.valueOf(month).length() > 1)) {
            months = "0" + month;
        } else {
            months = String.valueOf(month);
        }
        if (!(String.valueOf(day).length() > 1)) {
            days = "0" + day;
        } else {
            days = String.valueOf(day);
        }
        String firstDay = "" + year + "-" + months + "-01";
        String lastDay = "" + year + "-" + months + "-" + days;

        String[] currentMonth = new String[2];
        currentMonth[0] = firstDay;
        currentMonth[1] = lastDay;

        //  System.out.println(lastMonth[0] + "||" + lastMonth[1]);
        return currentMonth;
    }


    public static int getDateline() {

        return (int) (System.currentTimeMillis() / 1000);
    }


    public static long getDatelineLong() {

        return System.currentTimeMillis() / 1000;
    }

    public static long getDatelineLong(String date, String pattern) {
        return toDate(date, pattern).getTime();
    }

    public static int getDateline(String date) {
        return (int) (toDate(date, "yyyy-MM-dd").getTime() / 1000);
    }

    public static int getDateline(String date, String pattern) {
        return (int) (toDate(date, pattern).getTime() / 1000);
    }

    /**
     * 获取时间戳  日期范围：2011-09-01 08:00:00到当前  时间范围为8-23点
     *
     * @return
     */
    public static long getRandomTimeMillis() {
        long s = DateUtils.toDate("2011-09-01 08:00:00", "yyyy-MM-dd HH:mm:ss").getTime();
        long e = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            long t = (long) (random() * (e - s));
            Long r = s + t;
            String str = DateUtils.toString(r, "HH");
            int h = Integer.valueOf(str);
            if (h > 8 && h < 23) {
//				System.out.println(DateUtil.toString(r, "yyyy-MM-dd HH:mm:ss"));
                return r.longValue();
            }
        }
        long day = (long) (10 * random());
        return e - day * 24 * 60 * 60 * 1000;
    }

    /**
     * 检查当前时间是否包含在区间内
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean containsCurrTime(Long startTime, Long endTime) {
        if (startTime == null || endTime == null) {
            return false;
        }
        Long curr = DateUtils.getDatelineLong();
        if (curr.longValue() > startTime.longValue() && curr.longValue() < endTime.longValue())
            return true;
        return false;
    }

    /**
     * 检查当前时间是否包含在区间内
     *
     * @param startTime
     * @param endTime
     * @return 0 时间区间错误  1 在区间内 2 当前时间小于开始区间值 3 当前时间大于结束区间值
     */
    public static int CurrTimeStatus(Long startTime, Long endTime) {
        if (startTime == null || endTime == null) {
            return 0;
        }
        Long curr = DateUtils.getDatelineLong();
        if (curr.longValue() > startTime.longValue() && curr.longValue() < endTime.longValue())
            return 1;
        else if (curr.longValue() < startTime.longValue())
            return 2;
        else if (curr.longValue() > endTime.longValue())
            return 3;
        return 0;
    }

    /**
     * 获得当前日期N个月之前或之后的时间戳
     *
     * @author hqh
     * createTime:2013-9-11下午7:09:54
     * e-mail:hqh1689@163.com
     */
    public static Long getTime(int month) {
        long time = 0L;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        time = calendar.getTimeInMillis();
        return time;
    }
}
