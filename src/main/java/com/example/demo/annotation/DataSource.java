package com.example.demo.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.example.demo.common.enums.DataSourceType;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface DataSource {
	DataSourceType value() default DataSourceType.MYSQL_1;
}
