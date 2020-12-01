package com.example.demo.mytest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Test2 {
	public static void main(String[] args) {
		String ls = "";
		String[] split = ls.split(",");
		System.out.println(split.length);
		
		
		String sdf = "sdf";
		System.out.println(sdf.contains(""));

//		for (int i = 0; i < 10; i++) {
//
//			try {
//				Long temp = System.currentTimeMillis();
//				System.out.println(temp.doubleValue()/1000);
//				Thread.sleep(5);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}

		SimpleDateFormat sdfor = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		SimpleDateFormat sdforaa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			sdfor.setTimeZone(TimeZone.getTimeZone("UTC"));
			String time = "2020-11-27T10:38:06.1518383853Z";
			String pus = time.substring(0, time.indexOf(".")) + "Z";
			Date parse = sdfor.parse(pus);
			System.out.println(parse);

			String format = sdforaa.format(parse);
			System.out.println(format);
		} catch (ParseException e) {
			e.printStackTrace();
		}


	}
}
