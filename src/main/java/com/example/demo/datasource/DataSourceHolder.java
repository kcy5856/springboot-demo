package com.example.demo.datasource;

import com.example.demo.common.enums.DataSourceType;

public class DataSourceHolder {
    // 使用ThreadLocal保证线程安全
    private static final ThreadLocal<DataSourceType> TYPE = new ThreadLocal<DataSourceType>();

    // 往当前线程里设置数据源类型
    public static void setDataSourceType(DataSourceType dataSourceEnum) {
        if (dataSourceEnum == null) {
            throw new NullPointerException();
        }
        System.err.println("[将当前数据源改为]：" + dataSourceEnum.name());
        TYPE.set(dataSourceEnum);
    }

    // 获取数据源类型
    public static DataSourceType getDataSourceType() {
    	DataSourceType dataSourceEnum = TYPE.get() == null ? DataSourceType.MYSQL_1 : TYPE.get();
        System.err.println("[获取当前数据源的类型为]：" + dataSourceEnum);
        return dataSourceEnum;
    }

    // 清空数据类型
    public static void clearDataSourceType() {
        TYPE.remove();
    }

}
