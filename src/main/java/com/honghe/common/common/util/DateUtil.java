package com.honghe.common.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 日期时间工具类
 *
 * @author zhaowj
 */
public class DateUtil {

    private static final String datetimePattern = "yyyy-MM-dd HH:mm:ss";
    private static final String datetimeMinPattern = "yyyy-MM-dd HH:mm";
    private static final String datePattern = "yyyy-MM-dd";
    private static final String dateWithOutYearPattern = "MM-dd";
    private static final String dateMonthPattern = "yyyy-MM";
    private static final String timePattern = "HH:mm:ss";
    private static final String minutePattern = "HH:mm";

    private static final String datetimeShortPattern = "yyyyMMddHHmmss";
    private static final String dateShortPattern = "yyyyMMdd";
    private static final String timeShortPattern = "HHmmss";
    private static final String hourPattern = "HH";


    private DateUtil() {
    }

    static Logger logger = LoggerFactory.getLogger(DateUtil.class);


    public static String formatLongDatetimeString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        return format.format(date);
    }

    /**
     * 获得当前日期时间
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentDatetime() {
        return new SimpleDateFormat(datetimePattern).format(now());
    }

    /**
     * 格式化日期时间
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String formatDatetime(Date date) {
        return new SimpleDateFormat(datetimePattern).format(date);
    }

    public static String formatDateMinute(Date date) {
        if (null != date) {
            return new SimpleDateFormat(datetimeMinPattern).format(date);
        }
        return "";
    }

    public static String formatMinute(Date date) {
        if (null != date) {
            return new SimpleDateFormat(minutePattern).format(date);
        }
        return "";
    }

    /**
     * 格式化日期时间
     *
     * @param date
     * @param pattern 格式化模式，详见{@link SimpleDateFormat}构造器
     *                <code>SimpleDateFormat(String pattern)</code>
     * @return
     */
    public static String formatDatetime(Date date, String pattern) {
        SimpleDateFormat customFormat = (SimpleDateFormat) new SimpleDateFormat(datetimePattern)
                .clone();
        customFormat.applyPattern(pattern);
        return customFormat.format(date);
    }

    /**
     * 获得当前日期
     * <p>
     * 日期格式yyyy-MM-dd
     *
     * @return
     */
    public static String currentDate() {
        return new SimpleDateFormat(datePattern).format(now());
    }

    /**
     * 获得当前月份
     */
    public static String currentMonth() {
        return new SimpleDateFormat(dateMonthPattern).format(now());
    }

    /**
     * 获得当前日期 (短)
     * <p>
     * 日期格式yyyy-MM-dd
     *
     * @return
     */
    public static String currentShortDate() {
        return new SimpleDateFormat(dateShortPattern).format(now());
    }


    /**
     * 格式化日期
     * <p>
     * 日期格式yyyy-MM-dd
     *
     * @return
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat(datePattern).format(date);
    }

    /**
     * 获得当前时间
     * <p>
     * 时间格式HH:mm:ss
     *
     * @return
     */
    public static String currentTime() {
        return new SimpleDateFormat(timePattern).format(now());
    }


    /**
     * 获得当前时间 (短)
     * <p>
     * 时间格式HH:mm:ss
     *
     * @return
     */
    public static String currentShortTime() {
        return new SimpleDateFormat(timeShortPattern).format(now());
    }


    /**
     * 获得当前日期时间 (短)
     * <p>
     * 日期格式yyyy-MM-dd
     *
     * @return
     */
    public static String currentShortDateTime() {
        return new SimpleDateFormat(datetimeShortPattern).format(now());
    }

    /**
     * 格式化时间
     * <p>
     * 时间格式HH:mm:ss
     *
     * @return
     */
    public static String formatTime(Date date) {
        return new SimpleDateFormat(timePattern).format(date);
    }

    /**
     * 获得当前时间的<code>java.util.Date</code>对象
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 获得当前时间的String对象
     *
     * @return
     */
    public static String nowString() {
        return new SimpleDateFormat(datetimePattern).format(new Date());
    }

    public static Calendar calendar() {
        Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal;
    }

    /**
     * 获得当前时间的毫秒数
     * <p>
     * 详见{@link System#currentTimeMillis()}
     *
     * @return
     */
    public static long millis() {
        return System.currentTimeMillis();
    }

    /**
     * 获得当前Chinese月份
     *
     * @return
     */
    public static int month() {
        return calendar().get(Calendar.MONTH) + 1;
    }

    /**
     * 获得月份中的第几天
     *
     * @return
     */
    public static int dayOfMonth() {
        return calendar().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 今天是星期的第几天
     *
     * @return
     */
    public static int dayOfWeek() {
        return calendar().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 今天是年中的第几天
     *
     * @return
     */
    public static int dayOfYear() {
        return calendar().get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 判断原日期是否在目标日期之前
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean isBefore(Date src, Date dst) {
        return src.before(dst);
    }

    /**
     * 判断原日期是否在目标日期之后
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean isAfter(Date src, Date dst) {
        return src.after(dst);
    }

    /**
     * 判断两日期是否相同
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEqual(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    /**
     * 判断某个日期是否在某个日期范围
     *
     * @param beginDate 日期范围开始
     * @param endDate   日期范围结束
     * @param src       需要判断的日期
     * @return
     */
    public static boolean between(Date beginDate, Date endDate, Date src) {
        return beginDate.before(src) && endDate.after(src);
    }

    /**
     * 获得当前月的最后一天
     * <p>
     * HH:mm:ss为0，毫秒为999
     *
     * @return
     */
    public static Date lastDayOfMonth() {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_MONTH, 0); // M月置零
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);// 月份+1
        cal.set(Calendar.MILLISECOND, -1);// 毫秒-1
        return cal.getTime();
    }

    /**
     * 获取指定年月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        return new SimpleDateFormat(datePattern).format(cal.getTime());
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        return new SimpleDateFormat(datePattern).format(cal.getTime());
    }

    /**
     * 获取当年第一天
     * @return
     */
    public static Date firstDateOfYear() {
        Calendar cal = calendar();
        cal.set(Calendar.MONTH,0);  // 设置1月份
        cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        return cal.getTime();
    }

    /**
     * 获得当前月的第一天
     * <p>
     * HH:mm:ss SS为零
     *
     * @return
     */
    public static Date firstDayOfMonth() {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        return cal.getTime();
    }

    private static Date weekDay(int week) {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_WEEK, week);
        return cal.getTime();
    }

    /**
     * 获得周五日期
     * <p>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date friday() {
        return weekDay(Calendar.FRIDAY);
    }

    /**
     * 获得周六日期
     * <p>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date saturday() {
        return weekDay(Calendar.SATURDAY);
    }

    /**
     * 获得周日日期
     * <p>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date sunday() {
        return weekDay(Calendar.SUNDAY);
    }

    /**
     * 将字符串日期时间转换成java.util.Date类型
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @param datetime
     * @return
     */
    public static Date parseDateTime(String datetime) {
        try {
            return new SimpleDateFormat(datetimePattern).parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串日期转换成java.util.Date类型
     * <p>
     * 日期时间格式yyyy-MM-dd
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat(datePattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 比较月份大小
     */
    public static boolean compareMonth(String month1, String month2) {
        try {
            Date date1 = new SimpleDateFormat(dateMonthPattern).parse(month1);
            Date date2 = new SimpleDateFormat(dateMonthPattern).parse(month2);
            return date1.after(date2);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 将字符串日期转换成java.util.Date类型
     * <p>
     * 时间格式 HH:mm:ss
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date parseTime(String time) {
        try {
            return new SimpleDateFormat(timePattern).parse(time);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据自定义pattern将字符串日期转换成java.util.Date类型
     *
     * @param datetime
     * @return
     * @throws ParseException
     */
    public static Date parseDatetime(String datetime) {
        try {
            return new SimpleDateFormat(datetimePattern).parse(datetime);
        } catch (Exception e){
        }
        return null;
    }

    /**
     * 传入日期，之前或之后的日期
     *
     * @param date 日期
     * @param day  之后、之前的天数
     * @return yyyy-MM-dd
     */
    public static Date getTheOtherDate(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_MONTH, day);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();
    }


    public static  String getHour(Date date){
        try {
            return new SimpleDateFormat(hourPattern).format(date);
        } catch (Exception e) {
            logger.error("获取时间失败", e);
        }

        return null;
    }

    public static String getDateAfter(String date, int day) {
        Calendar now = Calendar.getInstance();

        try {
            now.setTime(new SimpleDateFormat(dateShortPattern).parse(date));
        } catch (ParseException e) {
            logger.error("获取时间失败", e);
        }
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return new SimpleDateFormat(dateShortPattern).format(now.getTime());
    }

    /**
     * @throws Exception
     * @reason:计算两个日期差几天，也可比较两个日期谁在前，谁在后
     * @param:只支持yyyyMMdd格式
     * @return：int 如果firstDate在secondDate之前，返回一个负整数；反之返回正整数
     */
    public static int getDiffBetweenTwoDate(String firstDate, String secondDate) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");//计算两天之差
        Date date1 = null;
        Date date2 = null;
        int cha = 0;
        try {
            date1 = myFormatter.parse(firstDate);//起始日期
            date2 = myFormatter.parse(secondDate);//终止日期
            long seconds = date1.getTime() - date2.getTime();//起始日期-终止日期=毫秒
            cha = (int) (seconds / (24 * 60 * 60 * 1000));//再除以每天多少毫秒(24*60*60*1000) ＝差几天
        } catch (Exception e) {
            logger.error("获取时间失败", e);
        }
        return cha;
    }

    /**
     * 获取两个日期之间的天数  yyyy-MM-dd
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 日期set
     */
    public static List<String> getSetBetweenDate(Date beginDate, Date endDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        List<String> dateList = new ArrayList<>();
        while (calendar.getTime().getTime() <= endDate.getTime()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(calendar.getTime());
            dateList.add(dateStr);
            calendar.add(Calendar.DAY_OF_MONTH, 1);//进行当前日期月份加1
        }
        return dateList;
    }


    /**
     * 排列排列周的次序(1,2,3,4,5,6,7)
     *
     * @param weekNum
     * @return 按升序排列
     */
    public static String sortWeek(String weekNum) {

        String[] strs = weekNum.split(",");// 使用正则表达式进行分割
        int[] is = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {// 遍历String数组，赋值给int数组
            is[i] = Integer.parseInt(strs[i]);
        }

        Arrays.sort(is);// 使用数组工具类进行排序，也可以自己使用冒泡或选择排序来进行排序

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < is.length; i++) {// 遍历进行拼接
            if (i == is.length - 1) {
                sb.append(is[i]);
            } else {
                sb.append(is[i] + ",");
            }
        }
        return sb.toString();
    }


    /**
     * 设置一天起始时间
     *
     * @param beginDate
     * @return
     */
    public static String dateAddBegin(String beginDate) {
        try {
            Date date = new SimpleDateFormat(datePattern).parse(beginDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            String dateStr = formatDatetime(calendar.getTime());

            return dateStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDate();
    }

    /**
     * 设置一天结束时间
     *
     * @param endDate
     * @return
     */
    public static String dateAddEnd(String endDate) {
        try {
            Date date = new SimpleDateFormat(datePattern).parse(endDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 24);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            String dateStr = formatDatetime(calendar.getTime());

            return dateStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDate();
    }

    /**
     * 根据日期获取当天是周几
     *
     * @param datetime 日期
     * @return 周几
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = sdf.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[w];
    }


    /**
     * 获得今天是今年的第几周
     *
     * @return
     */
    public static int getWeekOfYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.WEEK_OF_YEAR);
    }


    /**
     * 获取指定日期是当年的第几周
     *
     * @return
     */
    public static int getWeekOfYearByDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            //转换成date类型
            Date dateTypeTime = format.parse(date);
            //设置成指定时间
            calendar.setTime(dateTypeTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }


    /**
     * 获取当前的上一天日期
     */
    public static String getLastDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        return new SimpleDateFormat(datePattern).format(date);
    }

    /**
     * 获取指定日期的上一天日期
     */
    public static String getLastDay(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(datePattern).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, -1);
        Date d = calendar.getTime();
        return new SimpleDateFormat(datePattern).format(d);
    }

    /**
     * 获取某天是周几
     *
     * @param date 是为则默认今天日期、可自行设置“2013-06-03”格式的日期
     * @return 返回7是星期日、1是星期一、2是星期二、3是星期三、4是星期四、5是星期五、6是星期六
     */
    public static int getDayOfWeek(String date) {
        Calendar cal = calendar();
        if ("".equals(date)) {
            cal.setTime(new Date());
        } else {
            cal.setTime(new Date(parseDate(date).getTime()));
        }

        int day = cal.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
        } else {
            day--;
        }
        return day;
    }

    /**
     * 从本周开始获得历史周的星期日的日期
     *
     * @param weekNum 0为上周周日，1是上上周周日，类推
     * @return
     */
    public static String getPreviousWeekSunday(int weekNum) {
        int weeks = 0;
        weeks = 1 + (weekNum * 7);
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus - weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得当前日期与本周日相差的天数
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }


    // 从当前月开始获取历史月份的最后一天的日期
    public static String getPreviousMonthEnd(int month) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -month);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得当前年份
     *
     * @return
     */
    public static int getYear() {
        Calendar calendar = calendar();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * String 转换成日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date StringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                myDate = dateFormat.parse(date);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 日期转换上string
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String DateToString(Date date, String pattern) {
        String dateString = null;
        SimpleDateFormat dateFormat;
        if (pattern != null) {
            dateFormat = new SimpleDateFormat(pattern);
            dateString = dateFormat.format(date);
        }
        return dateString;
    }

    /**
     * 时间上加减
     *
     * @param date     时间
     * @param dateType 年、月、日
     * @param amount   加减数
     * @return
     */
    public static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 时间加减
     */
    public static String findDate(String date, int days) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat(datePattern).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, days);
        Date d = c.getTime();
        return new SimpleDateFormat(dateWithOutYearPattern).format(d);
    }

    /**
     * 过去七天
     */
    public static String lastWeekDate(String date) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat(datePattern).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, -7);
        Date d = c.getTime();
        return new SimpleDateFormat(datePattern).format(d);
    }


    /**
     * 获取上个月月份
     *
     * @return
     */
    public static String getLastMonth() {
        LocalDate today = LocalDate.now();
        today = today.minusMonths(1);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM");
        return formatters.format(today);
    }

    /**
     * 获取指定月的上个月月份
     *
     * @return
     */
    public static String getLastMonth(String month) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat(dateMonthPattern).parse(month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        return new SimpleDateFormat(dateMonthPattern).format(m);
    }

    /**
     * 获取某月天数
     */
    public static int getDaysOfMonth(String source) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDate.parse(source));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//根据年月 获取月份天数

    }

    /**
     * 获取昨天日期
     *
     * @return
     */
    public static String yesturdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date d = cal.getTime();
        return new SimpleDateFormat(datePattern).format(d);
    }

    public static Date parseDateTimeMin(String datetime) {
        try {
            return new SimpleDateFormat(datetimeMinPattern).parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}