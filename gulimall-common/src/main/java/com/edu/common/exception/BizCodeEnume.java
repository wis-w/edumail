package com.edu.common.exception;

public enum BizCodeEnume {
    UNKNOW_EXCPTION(10000,"系统未知异常"),
    VAILD_EXCPTION(10001,"参数校验失败");
    private Integer code;
    private String message;

    BizCodeEnume(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
