package com.example.demo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

import com.example.demo.common.enums.DateStyle;

public class DateUtil {
	
	public static void main(String[] args) {
		String hours = "12";
		String minute = "8";
		String weeks = "0,1,2,3,4,5,6";
		String crontab = getCrdCrontab(hours, minute, weeks);
		System.out.println(crontab);
		
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
	 * @param date
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
	
	
}
