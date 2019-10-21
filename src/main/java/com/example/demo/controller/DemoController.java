package com.example.demo.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.cache.CacheManager;
import com.example.demo.service.DemoService;

@RestController
@RequestMapping("/demo")
public class DemoController {
	Logger logger = LoggerFactory.getLogger(DemoController.class);
	
	@Autowired
	private DemoService demoService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private CacheManager cache;

	@RequestMapping(value="/change", method = RequestMethod.GET)
	private String changeParam(@RequestParam(value="isPublic", required = false) Boolean isPublic) {
		logger.debug("isPublic: {}", isPublic);
		logger.info(cache.get("gao").toString());
		return "" + isPublic;
	}
	
	@RequestMapping(value="/hello", method = RequestMethod.GET)
	private String getHello(@RequestParam(value="name") String name) {
		cache.set("gao", "jin");
		Object bean = applicationContext.getBean("demoServiceImpl");
		if (!Objects.isNull(bean)) {
			logger.info(bean.getClass().toString());
		}
		String val = demoService.getName(name);
		logger.debug(val);
		demoService.printData();
		return val;
	}
	
}
