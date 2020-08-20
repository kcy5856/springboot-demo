package com.example.demo.common.enums;

public enum DateStyle {
	STANDARD("yyyy-MM-dd HH:mm:ss"),
	YYYYMMDD("yyyyMMdd"),
	HHMMSS("HHmmss"),
	YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
	YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
	YYYY_MM_DD_T_HH_MM_SS_Z_SSS("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	private String value;

	private DateStyle(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
