package com.example.demo.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.example.demo.common.enums.DataSourceType;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {

        DataSourceType dataSource = DataSourceHolder.getDataSourceType();
        return dataSource;
	}

}
