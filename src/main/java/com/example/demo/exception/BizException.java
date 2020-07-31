package com.example.demo.exception;

import com.example.demo.common.enums.ErrorEnum;

/**
 * 自定义业务异常
 * @author harmonyCloud
 *
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Integer code;
	private String message;
	private Object data;

	public BizException( String msg) {
		this.code = 500;
		this.message = msg;
	}
	
	public BizException(Integer code, String msg) {
		this.code = code;
		this.message = msg;
	}
	
	public BizException(Integer code, String msg, Object data) {
		this.code = code;
		this.message = msg;
		this.data = data;
	}
	
	public BizException(ErrorEnum error) {
		
	}
	
	
	public BizException(ErrorEnum error, String extMsg) {
		
		
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	
}
