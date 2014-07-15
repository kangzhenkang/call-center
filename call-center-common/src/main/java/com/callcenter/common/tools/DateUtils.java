
package com.callcenter.common.tools;

import com.taobao.pamirs.schedule.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author wangyue-ds6
 * @since 2014-02-28
 */
public class DateUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
	
	public static long getServerTime() {
		return System.currentTimeMillis();
	}
	
	/**
	 * 格式化日期,默认返回yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化显示当前日期
	 * @param format
	 * @return
	 */
	public static String format(String format) {
		return format(new Date(), format);
	}

	/**
	 * 日期格式化
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			LOGGER.warn("日期格式化失败.{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 时间格式化， 传入毫秒
	 * @param time
	 * @return
	 */
	public static String dateFormat(long time) {
		return format(new Date(time), "yyyy-MM-dd HH:mm:ss");
	}


    public static  boolean compareDateWithCronExpression(CronExpression cronExpression){
        Date currentDate = new Date(getServerTime());
        //系统时间 精确到yyyy-MM-dd HH:mm
        String currentTime = DateUtils.format(currentDate,"yyyy-MM-dd HH:mm");
        //用户定制时间 精确到yyyy-MM-dd HH:mm
        String customerTime = DateUtils.format(cronExpression.getNextValidTimeAfter(currentDate),"yyyy-MM-dd HH:mm");
        return currentTime.equals(customerTime);
    }

    /**
     * 系统时间和Cron表达式时间相差毫秒值
     * @param cronExpression
     * @return
     */
    public static  long differMillisecondsWithCronExpression(CronExpression cronExpression){
        Long  currentMilliseconds = System.currentTimeMillis();
        Date currentDate = new Date(currentMilliseconds);
        Date customerDate = cronExpression.getNextValidTimeAfter(currentDate);
        Long  customerMilliseconds =customerDate.getTime();
        return Math.abs((customerMilliseconds-currentMilliseconds))/1000;
    }

    /**
     * 根据两个时间的毫秒值 通过yyyy-MM-dd HH:mm时间格式比较是否相等
     * @param firstMilliseconds 第一个毫秒值
     * @param secondMilliseconds 第一个毫秒值
     * @return 是否相等 true:相等 false:不相等
     */

    public static  boolean compareMilliseconds(Long firstMilliseconds,Long secondMilliseconds){
        return DateUtils.format(new Date(firstMilliseconds),"yyyy-MM-dd HH:mm").equals(DateUtils.format(new Date(secondMilliseconds),"yyyy-MM-dd HH:mm"));
    }
 /**
     * 根据两个时间的毫秒值 通过yyyy-MM-dd HH:mm时间格式比较是否相等以及两个毫秒值相减误差小雨10秒
     * @param firstMilliseconds 第一个毫秒值
     * @param secondMilliseconds 第一个毫秒值
     * @param secondMilliseconds 第一个毫秒值
     * @return 是否相等 true:相等 false:不相等
     */

    public static  boolean compareMillisecondsAndDeviationTime(Long firstMilliseconds,Long secondMilliseconds,int differMilliSeconds){
        return DateUtils.format(new Date(firstMilliseconds),"yyyy-MM-dd HH:mm").equals(DateUtils.format(new Date(secondMilliseconds),"yyyy-MM-dd HH:mm"))
                && (((firstMilliseconds - secondMilliseconds) / 1000) <= differMilliSeconds) ;
    }

    public static  Date getNextValidTimeAfter(String cronExpressionStr, Date currentDate) throws ParseException {
        CronExpression cronExpression = new CronExpression(cronExpressionStr);
        return cronExpression.getNextValidTimeAfter(currentDate);
    }

    public static  Long getNextValidTimeAfter(String cronExpressionStr) throws ParseException {
        Long  currentMilliseconds = System.currentTimeMillis();
        Date currentDate = new Date(currentMilliseconds);
        CronExpression cronExpression = new CronExpression(cronExpressionStr);
        return cronExpression.getNextValidTimeAfter(currentDate).getTime();
    }

}
