package com.cls.mymall.common.exception;

public enum BizCode {

    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    ;

    private final Integer code;
    private final String msg;

    BizCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
