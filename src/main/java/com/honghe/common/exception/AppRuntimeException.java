package com.honghe.common.exception;


public class AppRuntimeException extends RuntimeException {

    private String code;
    private String msg;

    public AppRuntimeException() {
        this.code = "001";
        this.msg = "";
    }

    public AppRuntimeException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AppRuntimeException(String message) {
        this.code = "002";
        this.msg = message;
    }

    @Override
    public String getLocalizedMessage() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
