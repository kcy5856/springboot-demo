package com.example.demo.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.example.demo.annotation.DataSource;
import com.example.demo.common.enums.DataSourceType;
import com.example.demo.datasource.DataSourceHolder;

@ConditionalOnExpression("${component.mysql}")
@Component
@Aspect
public class DataSourceAspect {
    @Before("execution(* com.example.demo.service..*.*(..))")
    public void setDataSource(JoinPoint joinPoint) {
    	//类注解
        DataSource annotationOfClass = joinPoint.getTarget().getClass().getAnnotation(DataSource.class);
        //方法名
        String methodName = joinPoint.getSignature().getName();
        //方法参数类型
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        
        try {
			Method method = joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);
			DataSource methodAnnotation = method.getAnnotation(DataSource.class);
			methodAnnotation = methodAnnotation == null?annotationOfClass:methodAnnotation;
			DataSourceType dataSourceEnum = methodAnnotation != null && methodAnnotation.value() !=null ? methodAnnotation.value():DataSourceType.MYSQL_1;
			DataSourceHolder.setDataSourceType(dataSourceEnum);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    @After("execution(* com.example.demo.service..*.*(..))")
    public void clearDataSource(JoinPoint joinPoint) {
    	DataSourceHolder.clearDataSourceType();
    }
}
