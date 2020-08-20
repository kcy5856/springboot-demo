package com.example.demo.utils;

import com.example.demo.common.enums.ErrorEnum;
import com.example.demo.model.common.Result;

public class ResultUtil {
    static final int RESPONSE_STATUS_SUCCESS = 0;

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(RESPONSE_STATUS_SUCCESS);
        result.setData(object);
        return result;
    }

    public static Result successWithMsg(String msg) {
        Result result = new Result();
        result.setCode(RESPONSE_STATUS_SUCCESS);
        result.setMsg(msg);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error() {
        Result result = new Result();
        result.setCode(ErrorEnum.ERROR.code());
        return result;
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(ErrorEnum.ERROR.code());
        result.setMsg(msg);
        return result;
    }

    public static Result error(ErrorEnum errorEnum) {
        Result result = new Result();
        result.setCode(errorEnum.code());
        result.setMsg(errorEnum.msg());
        return result;
    }

    public static Result error(ErrorEnum errorEnum, String ext) {
        Result result = new Result();
        result.setCode(errorEnum.code());
        result.setMsg(errorEnum.msg() + ": " + ext);
        return result;
    }
}