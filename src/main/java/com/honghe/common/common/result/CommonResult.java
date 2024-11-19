package com.honghe.common.common.result;



import com.honghe.common.common.constants.Response;
import com.honghe.common.common.enums.ResultCode;

import java.io.Serializable;

/**
 * Created by WuXiao on 2017/3/28 0028.
 */
public class CommonResult<T> implements Serializable {

    private String code;
    private String msg;
    private String type;
    private T data;

    public CommonResult() {
        this.code = Response.FAIL;
        this.msg = "";
    }


    public CommonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult(String code, String msg, T data, String type) {
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
//  默认方法

    public static CommonResult success() {
        return successMsg("请求成功");
    }

    public static CommonResult successMsg(String msg) {
        CommonResult apiBean = new CommonResult(Response.SUCCESS, msg, null);
        return apiBean;
    }

    public static CommonResult success(Object data, String msg, String type) {
        CommonResult apiBean = new CommonResult(Response.SUCCESS, msg, data, type);
        return apiBean;
    }

    public static CommonResult success(Object data, String msg) {
        CommonResult apiBean = new CommonResult(Response.SUCCESS, msg, data);
        return apiBean;
    }

    public static CommonResult success(Object data) {
        CommonResult apiBean = new CommonResult(Response.SUCCESS, "", data);
        return apiBean;
    }

    public static CommonResult fail() {
        return fail("请求失败");
    }

    public static CommonResult fail(String msg) {
        CommonResult apiBean = new CommonResult(Response.FAIL, msg);
        return apiBean;
    }

    public static CommonResult fail(String code, String msg) {
        CommonResult apiBean = new CommonResult(code, msg);
        return apiBean;
    }

    public static CommonResult fail(ResultCode code, String msg) {
        CommonResult apiBean = new CommonResult(code.toString(), msg);
        return apiBean;
    }

    public static CommonResult fail(String code, String msg ,String type) {
        CommonResult apiBean = new CommonResult(code, msg, null, type);
        return apiBean;
    }

    public static CommonResult paramFail(String type){
        return CommonResult.fail(ResultCode.ParamError.toString(),"参数错误",type);
    }

    public static CommonResult paramFail(String message,String type){
        return CommonResult.fail(ResultCode.ParamError.toString(),message,type);
    }


    public static CommonResult sessionError() {
        CommonResult apiBean = new CommonResult(Response.OUT_TIME_ERROR, "登陆超时");
        return apiBean;
    }

}
