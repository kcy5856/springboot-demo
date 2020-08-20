package com.example.demo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

import com.example.demo.common.enums.DateStyle;

public class DateUtil {

	private final static String DATE_STANDARD_FORMAT = DateStyle.YYYY_MM_DD_HH_MM_SS.getValue();
	
	public static void main(String[] args) {
		String hours = "12";
		String minute = "8";
		String weeks = "0,1,2,3,4,5,6";
		String crontab = getCrdCrontab(hours, minute, weeks);
		System.out.println(crontab);
		
	}

	/**
	 * 获取系统当前时间
	 * @return
	 */
	public static Date getCurrentUtcTime() {
		StringBuffer UTCTimeBuffer = new StringBuffer();
		// 1、取得本地时间：
		Calendar cal = Calendar.getInstance();
		// 2、取得时间偏移量：
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		UTCTimeBuffer.append(year).append("-").append(month).append("-").append(day);
		UTCTimeBuffer.append("T").append(hour).append(":").append(minute).append(":").append(second).append("Z");
		Date date = string2Date(UTCTimeBuffer.toString(), DateStyle.YYYY_MM_DD_T_HH_MM_SS_Z_SSS.getValue());
		return date;
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
	 * 日期转换为UTC时间
	 * @param date
	 * @return
	 */
	public static String convertToUTC(Date date, DateStyle dateStyle) {
		if (Objects.isNull(date) || Objects.isNull(dateStyle)) {
			return null;
		}
	    DateFormat dateFormat = new SimpleDateFormat(dateStyle.getValue());
	    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    return dateFormat.format(date);
	}
	
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
		String utcString = convertToUTC(date, DateStyle.HHMMSS);
		
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

	/**
	 * localDate转Date
	 * @param localDate
	 * @return
	 */
	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * LocalDateTime转Date
	 * @param localDateTime
	 * @return
	 */
	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Date转LocalDate
	 * @param date
	 * @return
	 */
	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * Date转LocalDateTime
	 * @param date
	 * @return
	 */
	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static String date2String(Date date){
		if (date == null){
			return null;
		}
		return date2String(date, DATE_STANDARD_FORMAT);
	}

	public static String date2String(Date date, String pattern){
		if (date == null){
			return null;
		}
		if (com.example.demo.utils.StringUtils.isEmpty(pattern)) {
			pattern = DATE_STANDARD_FORMAT;
		}
		LocalDateTime localDateTime = asLocalDateTime(date);
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 毫秒时间戳转日期字符串
	 * @param millisecond
	 * @param pattern
	 * @return
	 */
	public static String timeStamp2Date(long millisecond, String pattern) {
		if (com.example.demo.utils.StringUtils.isEmpty(pattern)) {
			pattern = DATE_STANDARD_FORMAT;
		}
		LocalDateTime dateTime = LocalDateTime.ofEpochSecond(millisecond / 1000L, 0, ZoneOffset.ofHours(8));
		if (millisecond != 0) {
			return dateTime.format(DateTimeFormatter.ofPattern(pattern));
		}
		return null;
	}

	/**
	 * 日期/时间转时间戳-毫秒
	 * @param date 日期/时间
	 * @param pattern 时间格式
	 * @return  时间戳(毫秒)
	 */
	public static long date2TimeStamp(String date, String pattern) {
		long timeStamp ;
		if (com.example.demo.utils.StringUtils.isEmpty(pattern)) {
			pattern = DATE_STANDARD_FORMAT;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime localDateTime = LocalDateTime.parse(date,formatter);
		timeStamp = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
		return timeStamp;
	}

	/**
	 * 日期字符串转换为日期
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date string2Date(String dateString, String pattern){
		if (dateString == null){
			return null;
		}

		if (com.example.demo.utils.StringUtils.isEmpty(pattern)) {
			pattern = DATE_STANDARD_FORMAT;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime localDateTime = LocalDateTime.parse(dateString,formatter);
		return asDate(localDateTime);
	}
}
