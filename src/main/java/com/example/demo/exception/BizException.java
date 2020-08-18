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
	private String msg;
	private Object data;

	public BizException( String msg) {
		this.code = 500;
		this.msg = msg;
	}
	
	public BizException(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public BizException(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public BizException(ErrorEnum error) {
		this.code = error.code();
		this.msg = error.msg();
	}
	
	
	public BizException(ErrorEnum error, String extMsg) {
		this.code = error.code();
		this.msg = error.msg() + ": " + extMsg;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String message) {
		this.msg = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	
}
