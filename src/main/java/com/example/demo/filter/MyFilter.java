package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@WebFilter(filterName = "myFilter", urlPatterns = "/api/*")
public class MyFilter implements Filter {
	Logger logger = LoggerFactory.getLogger(MyFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

//		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//		System.out.println(httpServletRequest.getServletPath());		//server地址，不带项目名称
//		System.out.println(httpServletRequest.getMethod());				//请求方式
//		System.out.println(httpServletRequest.getSession().getAttribute("user"));
//		System.out.println(httpServletRequest.getRequestURI());			//带项目名称的请求地址
//		System.out.println(httpServletRequest.getCookies()[0].getName());

		chain.doFilter(request, response);
		
	}

}
