package com.example.demo.common.enums;

public enum ErrorEnum {
    PARAM_NULL(10000, "param null", "参数为空"),
    PARAM_ERROR(10002, "param wrong", "参数错误"),
    DATA_NOT_EXIST(10003, "data not exist", "数据不存在"),
    DATA_EXIST(10004, "data exist", "数据已存在"),
    CONNECT_FAIL(20000, "connect fail", "连接失败"),
    HTTP_ERROR(20001, "https call error.", "访问外部服务异常"),
    EXCEPTION(9999, "execute exception", "处理异常"),
    ERROR(-9999, "execute error", "处理失败");

    private Integer code;
    private String enMsg;
    private String chMsg;

    ErrorEnum(Integer code, String enMsg, String chMsg){
        this.code = code;
        this.enMsg = enMsg;
        this.chMsg = chMsg;
    }

    public Integer code(){
        return this.code;
    }
    public String msg(){
        //根据语言设置获取中文或英文

        return this.enMsg;
    }

    public static ErrorEnum valueOf(int code){
        ErrorEnum[] values = ErrorEnum.values();
        int length = values.length;

        for (int i=0; i<length; i++){
            if (values[i].code == code){
                return values[i];
            }
        }

        throw new IllegalArgumentException("no match ErrorEnum for [" + code + "]");
    }

}
