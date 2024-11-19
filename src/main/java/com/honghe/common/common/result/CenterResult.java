package com.honghe.common.common.result;

import com.honghe.common.common.util.TipsMessage;

/**
 * @Description 返回结果实体对象
 * @Author youye
 * @Date: 2019-03-16 14:43
 */
public final class CenterResult {

    private String code;
    private boolean success;
    private String message;
    private Object result;

    public CenterResult(){
        this("1",false,"","");
    }

    public CenterResult(String code, boolean success, String message, Object result) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public CenterResult(String code, boolean success) {
        this(code,success, "", "");
    }

    public CenterResult(String code, boolean success, String message) {
        this(code,success, message, "");
    }

	public CenterResult(String code, boolean success, Object result) {
        this(code,success, "", result);
	}


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return (null == message ? "" : message);
    }

    public void setMessage(String message) {
        this.message = (null == message ? "" : message);
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * 参数错误返回值
     */
    public static CenterResult getParamError(){
        return getParamError(TipsMessage.PARAM_ERROR);
    }

    /**
     * 参数错误返回值
     */
    public static CenterResult getParamError(String message){
        CenterResult newResult = new CenterResult();
        newResult.setCode("-1");
        newResult.setSuccess(false);
        newResult.setMessage(message);
        return newResult;
    }

    /**
     * 接口成功回值
     * @param result 返回值
     */
    public static CenterResult getSuccess(Object result){
        return getSuccess(result,"");
    }

    /**
     * 接口成功返回值
     * @param result 返回值
     * @param message 提示信息
     */
    public static CenterResult getSuccess(Object result, String message){
        CenterResult newResult = new CenterResult();
        newResult.setCode("0");
        newResult.setSuccess(true);
        newResult.setMessage(message);
        newResult.setResult(result);
        return newResult;
    }

    /**
     * 接口失败返回值
     * @param result 返回值
     */
    public static CenterResult getFail(Object result){
        return getFail(result,"操作失败");
    }

    /**
     * 接口失败返回值
     * @param result 返回值
     * @param message 提示信息
     */
    public static CenterResult getFail(Object result,String message){
        return getFail("1",result,message);
    }

    /**
     * 接口失败返回值
     * @param resultCode 错误码
     * @param result 返回值
     * @param message 提示信息
     */
    public static CenterResult getFail(String resultCode,Object result,String message){
        CenterResult newResult = new CenterResult();
        newResult.setCode(resultCode);
        newResult.setSuccess(false);
        newResult.setMessage(message);
        newResult.setResult(result);
        return newResult;
    }

    /**
     * 接口失败返回值
     * @param resultCode 错误码
     * @param result 返回值
     * @param message 提示信息
     */
    public static CenterResult getResult(String resultCode,Object result,String message){
        CenterResult newResult = new CenterResult();
        newResult.setCode(resultCode);
        newResult.setSuccess(false);
        newResult.setMessage(message);
        newResult.setResult(result);
        return newResult;
    }

}