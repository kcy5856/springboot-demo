package com.example.demo.mytest;

import java.util.ArrayList;
import java.util.List;

public class Test3 {

	public static void main(String[] args) {
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
