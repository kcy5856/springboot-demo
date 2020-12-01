package com.example.demo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.example.demo.common.enums.DateStyle;

public class DateUtil {

	public static void main(String[] args) {

		String UTC_FORMATTER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern(UTC_FORMATTER_PATTERN);
		LocalDateTime localDateTime = LocalDateTime.now(TimeZone.getTimeZone("UTC").toZoneId());
//		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println(Date.from(localDateTime.atZone(TimeZone.getTimeZone("UTC").toZoneId()).toInstant()));
		String nowStr = fmt.format(localDateTime);
		System.out.println(nowStr);

//		System.out.println(getCurrentTime());
//		System.out.println(getCurrentMilliSecond());
//		System.out.println(timeToString(new Date()));
//		System.out.println(timeToString(new Date(), TimeZone.getTimeZone("UTC")));
//		System.out.println(timeToString(new Date(), TimeZone.getTimeZone("UTC"), DateStyle.YYYY_MM_DD_T_HH_MM_SS_Z));
//		System.out.println(stringToDate("2017-09-28 17:07:05"));
//		System.out.println(stringToDate("2017-09-28 17:07:05", DateStyle.YYYY_MM_DD_HH_MM_SS));
//		System.out.println(stringToDate("2017-09-28 17:07:05", TimeZone.getTimeZone("UTC"), DateStyle.YYYY_MM_DD_HH_MM_SS));

		LocalDateTime localDateTime1 = toLocalDateTime(new Date(), TimeZone.getTimeZone("UTC"));
		System.out.println(localDateTime);
		System.out.println(LocalDateTime.now(TimeZone.getTimeZone("UTC").toZoneId()).format(DateTimeFormatter.ofPattern(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue())));
		System.out.println(localDateTime.format(DateTimeFormatter.ofPattern(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue())));
		Date date = toDate(localDateTime, TimeZone.getTimeZone("UTC"));
		System.out.println(dateToString(date));

//		String hours = "12";
//		String minute = "8";
//		String weeks = "0,1,2,3,4,5,6";
//		String crontab = getCrdCrontab(hours, minute, weeks);
//		System.out.println(crontab);
		
	}

	/**
	 * 获取时间
	 * @return
	 */
	public static Date getCurrentTime(){
		return new Date();
	}

	/**
	 * 获取时间戳
	 * @return
	 */
	public static long getCurrentMilliSecond(){
		return System.currentTimeMillis();
	}

	/**
	 * 时间转字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		return dateToString(date, TimeZone.getDefault());
	}

	/**
	 * 时间转字符串
	 * @param date
	 * @param zone
	 * @return
	 */
	public static String dateToString(Date date, TimeZone zone){
		return dateToString(date, zone, DateStyle.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 时间转字符串
	 * @param date
	 * @param dateStyle
	 * @return
	 */
	public static String dateToString(Date date, DateStyle dateStyle){
		return dateToString(date,  TimeZone.getDefault(), DateStyle.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 时间转字符串
	 * @param date
	 * @param zone
	 * @param dateStyle
	 * @return
	 */
	public static String dateToString(Date date, TimeZone zone, DateStyle dateStyle){
		LocalDateTime localDateTime = LocalDateTime.now(zone.toZoneId());
		return localDateTime.format(DateTimeFormatter.ofPattern(dateStyle.getValue()));
	}

	/**
	 * 字符串转时间
	 * @param dateString
	 * @return
	 */
	public static Date stringToDate(String dateString){
		return stringToDate(dateString, DateStyle.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 字符串转时间
	 * @param dateString
	 * @param dateStyle
	 * @return
	 */
	public static Date stringToDate(String dateString, DateStyle dateStyle){
		return stringToDate(dateString, TimeZone.getDefault(), dateStyle);
	}

	/**
	 * 指定时区的字符串转换为当前时区的字符串
	 * @param dateString
	 * @param zone
	 * @param dateStyle
	 * @return
	 */
	public static Date stringToDate(String dateString, TimeZone zone, DateStyle dateStyle){
		LocalDateTime localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(dateStyle.getValue()));
		return Date.from(localDateTime.atZone(zone.toZoneId()).toInstant());
	}

	/**
	 * localdatetime转date
	 * @param localDateTime
	 * @return
	 */
	public static Date toDate(LocalDateTime localDateTime){
		return toDate(localDateTime, TimeZone.getDefault());
	}

	/**
	 * localdatetime转date
	 * @param localDateTime
	 * @param timeZone
	 * @return
	 */
	public static Date toDate(LocalDateTime localDateTime, TimeZone timeZone){
		return Date.from(localDateTime.atZone(timeZone.toZoneId()).toInstant());
	}

	/**
	 * date转LocalDateTime
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date){
		return toLocalDateTime(date, TimeZone.getDefault());
	}

	/**
	 * date转LocalDateTime
	 * @param date
	 * @param zone
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date, TimeZone zone){
		return date.toInstant().atZone(zone.toZoneId()).toLocalDateTime();
	}







	/*-----------------------------------------   以下为获取crontab表达式使用	--------------------------------------*/

	/**
	 * 根据时间获取crd的crontab表达式
	 * @param hours
	 * @param minute
	 * @param weeks
	 * @return
	 */
	public static String getCrdCrontab(String hours, String minute, String weeks) {
		Date date = getDateTimeByHoursAndMinute(hours, minute);
		if (Objects.isNull(date)) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		String utcString = dateToString(date, TimeZone.getTimeZone("UTC"), DateStyle.HHMMSS);

		String temp = null;
		temp = utcString.substring(4, 6);
		buildTimeString(sb, temp);
		
		temp = utcString.substring(2, 4);
		buildTimeString(sb, temp);
		
		temp = utcString.substring(0, 2);
		buildTimeString(sb, temp);
		
		sb.append("* * ");
		
		if (StringUtils.isEmpty(weeks) || weeks.split(",").length == 7) {
			sb.append("*");
		}else {
			sb.append(weeks);
		}
		
		
		return sb.toString();
	}

	/**
	 * 根据小时和分钟获取日期时间
	 * @param hours
	 * @param minute
	 * @return
	 */
	public static Date getDateTimeByHoursAndMinute(String hours, String minute) {
		if (StringUtils.isEmpty(hours) || StringUtils.isEmpty(minute) || hours.length() > 2 || minute.length() > 2) {
			return null;
		}
		Calendar cal = Calendar.getInstance();

		if(hours.length() == 2 && hours.startsWith("0")) {
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours.substring(1)));
		}else {
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
		}

		if(minute.length() == 2 && minute.startsWith("0")) {
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours.substring(1)));
		}else {
			cal.set(Calendar.MINUTE, Integer.parseInt(minute));
		}

		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}
	
	/**
	 * 构建crontab时分秒表达式
	 * @param sb
	 * @param temp
	 */
	public static void buildTimeString(StringBuilder sb, String temp) {
		if (Objects.isNull(sb) || StringUtils.isEmpty(temp)) {
			
		}
		if("00".equals(temp)) {
			sb.append("* ");
		}else {
			if (temp.startsWith("0")) {
				sb.append(temp.charAt(1) + " ");
			}else {
				sb.append(temp + " ");
			}
		}		
	}

}
