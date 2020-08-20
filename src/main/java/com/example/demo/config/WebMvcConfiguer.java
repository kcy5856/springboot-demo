package com.example.demo.config;

import com.example.demo.interceptor.AuthCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.MyInterceptor;

@Configuration
public class WebMvcConfiguer implements WebMvcConfigurer {

	@Autowired
	private AuthCheckInterceptor authCheckInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authCheckInterceptor).addPathPatterns("/easy/hello/**");
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/easy/**");
	}
}
