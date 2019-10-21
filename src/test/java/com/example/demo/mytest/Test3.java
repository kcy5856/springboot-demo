package com.example.demo.mytest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test3 {

	public static void main(String[] args) {
		Boolean t =true;
		Boolean tt= true;
		System.out.println(t==tt);
		
		for(int i=90; i<145; i++) {
			Integer a = i;
			int b = i;
			System.out.println(i);
			System.out.println(a ==b);
		}
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String format = sdf.format(new Date());
		System.out.println(format);
				
		
		List<String> strc = new ArrayList<String>();
		strc.add("2");
		strc.add("21");
		strc.add("4");
		strc.add("40");
		strc.add("1");
		strc.add("123");
		
		strc.sort((x, y)->Integer.parseInt(y) - Integer.parseInt(x));
		System.out.println(strc);
	}

}
