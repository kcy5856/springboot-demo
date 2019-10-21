package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.javassist.ClassMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.example.demo.annotation.DataSource;
import com.example.demo.annotation.MyAspect;
import com.example.demo.common.enums.DataSourceType;
import com.example.demo.common.tools.PropertiesHelper;
import com.example.demo.dao.ClassMapper;
import com.example.demo.dao.StudentMapper;
import com.example.demo.exception.BizException;
import com.example.demo.model.ClassModel;
import com.example.demo.service.DemoService;
import com.example.demo.thread.ThreadPool;

@Service
@PropertySource("classpath:business.properties")
public class DemoServiceImpl implements DemoService {
	Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private ClassMapper classMapper;
	
	@Autowired
	private Environment env;
	
	@Override
	@MyAspect("getName")
	public String getName(String val) {
		if (StringUtils.isEmpty(val)) {
			throw new BizException(1, "ss");
		}
		
		List<Map<String,Object>> listAllStudent = studentMapper.listAllStudent();
		logger.debug(JSON.toJSON(listAllStudent).toString());
		
		//Environment获取springboot配置
		logger.debug("name is:" + env.getProperty("custom.test.user"));
		
		logger.debug("self define properties: " + PropertiesHelper.getProperty("fas.vv"));
		
		return "hello, " + val;
	}
	
	@Override
	public void doSomeThing() {
		ThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(new Date());
				
			}
		});
		
	}
	
	@DataSource(DataSourceType.MYSQL_2)
	@Override
	public void printData() {
		System.out.println(classMapper.listAllClass());
		
	}

}
