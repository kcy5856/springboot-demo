package com.example.demo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.common.ResponseData;

/**
 * 全局异常处理
 * @author harmonyCloud
 *
 */
@ControllerAdvice
@ResponseBody
public class DemoExceptionHandler {
	
    @ExceptionHandler({BizException.class})
    public ResponseData bizExceptionHandler(BizException e) {
    	ResponseData response = new ResponseData();
        response.setCode(e.getCode());
        response.setMessage(e.getMessage());
        response.setData(e.getData());
        return response;
    }

    @ExceptionHandler({Exception.class})
    public ResponseData exceptionHandler(Exception e) {
    	ResponseData response = new ResponseData();
        response.setCode(1);
        response.setMessage("erooor");
        
        return response;
    }
	
}
