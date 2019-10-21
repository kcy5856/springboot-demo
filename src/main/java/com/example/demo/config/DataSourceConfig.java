package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.common.enums.DataSourceType;
import com.example.demo.datasource.DynamicDataSource;

@Configuration
public class DataSourceConfig {
	@Primary
    @Bean(name = "firstDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.first")
	public DataSource firsttDataSource() {
		return DataSourceBuilder.create().build();
	}
	
    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.second")
	public DataSource secondDataSource() {
		return DataSourceBuilder.create().build();
	}
    
    @Bean(name = "dynamicDataSource")
    public DataSource dataSource() {
    	DynamicDataSource dynamicDataSource = new DynamicDataSource();
    	//设置默认数据源
    	dynamicDataSource.setDefaultTargetDataSource(firsttDataSource());
    	
    	//配置多数据源
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.MYSQL_1, firsttDataSource());
        dataSourceMap.put(DataSourceType.MYSQL_2, secondDataSource());
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
    	return new DataSourceTransactionManager(dataSource());
    }
	
}
