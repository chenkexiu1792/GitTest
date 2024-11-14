package com.honghe.common.exception;

/**
 * @Author: wuxiao
 * @Date: 2018/10/26 15:34
 */
public class CommandException extends RuntimeException{
    /**
     * 序列化
     *
     * @param null
     * @return
     * @Create wuxiao 2018/12/4 18:29
     * @Update wuxiao 2018/12/4 18:29
     */
    private static final long serialVersionUID = -4387316237428691168L;
    private String code;
    private String msg;
    private String type;

    public CommandException() {
        this.code = "1";
        this.msg = "";
    }

    public CommandException(String msg) {
        this.code = "1";
        this.msg = msg;
    }

    public CommandException(String code, String msg){
        this.msg = msg;
        this.code = code;
    }
    public CommandException(String code, String msg, String type) {
        this.code = code;
        this.type = type;
        this.msg = msg;
    }

    public CommandException(String code, String msg, String type, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
