package com.example.demo.mytest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class JavaCoreTest {
	public static void main(String[] args) {
		
		Map<String, Object> map1 = new HashMap<>();
		map1.put("id", 1);
		map1.put("name", "naf");
		
		Map<String, Object> map2 = new HashMap<>();
		map2.put("id", 2);
		map2.put("name", "fff");
		
		List<Map<String, Object>> list1 = new ArrayList<>();
		list1.add(map1);
		list1.add(map2);
		
		List<Map<String, Object>> list = new ArrayList<>();
		list.addAll(list1);
		
		Iterator<Map<String, Object>> iterator = list.iterator();
		while(iterator.hasNext()) {
			Map<String, Object> next = iterator.next();
			
			next.put("code", "A");
		}
		
		System.out.println(JSON.toJSON(list));
	}
}
