package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class MyAspect {
	Logger logger = LoggerFactory.getLogger(MyAspect.class);
	
	//@Pointcut("execution(* com.example.demo.service..*.*(..))")
	@Pointcut("@annotation(com.example.demo.annotation.MyAspect)")
	public void myCut() {}
	
	@Around("myCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		//logger.debug("around begin ...");
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
		//logger.debug("around end ...");
		return result;
	}
	
	@Before("myCut()")
	public void before() {
		//logger.debug("before ...");
	}
	
	@After("myCut()")
	public void after() {
		//logger.debug("after ... ");
	}
	
	@AfterReturning("myCut()")
	public void afterReturing(JoinPoint joinPoint) {
		/*
		 * MethodSignature signature = (MethodSignature)joinPoint.getSignature(); Method
		 * method = signature.getMethod(); com.example.demo.annotation.MyAspect myAspect
		 * = method.getAnnotation(com.example.demo.annotation.MyAspect.class);
		 * logger.debug("after returning..." + myAspect.value());
		 */
	}

}
