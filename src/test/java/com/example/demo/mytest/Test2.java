package com.example.demo.mytest;

public class Test2 {
	public static void main(String[] args) {
		String ls = "";
		String[] split = ls.split(",");
		System.out.println(split.length);
		
		
		String sdf = "sdf";
		System.out.println(sdf.contains(""));

		for (int i = 0; i < 10; i++) {
			
			try {
				Long temp = System.currentTimeMillis();
				System.out.println(temp.doubleValue()/1000);
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
