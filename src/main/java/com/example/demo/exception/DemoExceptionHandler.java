package com.example.demo.exception;

import com.example.demo.common.enums.ErrorEnum;
import com.example.demo.model.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author harmonyCloud
 *
 */
@ControllerAdvice
@ResponseBody
public class DemoExceptionHandler {
	
    @ExceptionHandler({BizException.class})
    public Result bizExceptionHandler(BizException e) {
        Result response = new Result();
        response.setCode(e.getCode());
        response.setMsg(e.getMsg());
        response.setData(e.getData());
        return response;
    }

    @ExceptionHandler({Exception.class})
    public Result exceptionHandler(Exception e) {
        Result response = new Result();
        response.setCode(ErrorEnum.EXCEPTION.code());
        response.setMsg(e.getMessage());
        return response;
    }
	
}
