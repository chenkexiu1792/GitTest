package com.honghe.common.common.result;

import com.honghe.common.common.util.TipsMessage;

/**
 * @Description 返回结果实体对象
 * @Author sunchao
 * @Date: 2017-11-29 14:43
 * @Mender: libing
 */
public final class Result {

    private int code;
    private boolean status;
    private String msg;
    private Object result;

    // 会议服务返回值
    private boolean success;
    private String message;

    // 存储服务返回值
    private Object data;

    public Result() {
    }

    public static Result paramFail = new Result(Code.ParamError.value(), false, TipsMessage.PARAM_ERROR);
    public static Result programFail = new Result(Code.UnKnowError.value(), false, TipsMessage.SERVICE_ERROR);

    public Result(int statusCode, boolean status, String msgInfo) {
        this.msg = msgInfo;
        this.code = statusCode;
        this.status = status;
    }

    public Result(int statusCode, boolean status) {
        this.code = statusCode;
        this.status = status;
    }

    public static Result success(Object result) {
        Result r = new Result(Code.Success.value,true,null);
        r.setResult(result);
        return r;
    }


    public static Result failed(String message) {
        return new Result(Code.InnerError.value,false,message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public enum Code {
        LimitAccessError(-5),   // 限制访问: 暂时无法访问，请稍后重试
        AuthError(-4),          // 无权限访问:暂无访问权限，请申请权限后重新访问
        UnKnowError(-3),        // 接口内部错误: 接口内部错误
        InnerError(-2),         // 应该是无此方法，该错误码有出入：NO_SUCH_METHOD	没有此方法	没有此方法
        ParamError(-1),         // 请求参数错误: 输入错误，请重新输入
        Success(0),             // 接口执行成功
        ParamDisableError(101), // 参数数据不可用: 请输入正确的XXX
        ParamLargeError(102),   // 参数数据量过大: XXX参数最多支持XXX
        ParamFormatError(103),  // 参数数据格式错误: 请输入正确的XXX
        TokenError(104),        // Token错误或Token失效
        DataRepeatError(105),   // 数据重复：请勿重复添加
        FieldOverError(106),    // 字段长度超出限制：XXX最多输入XXX个字
        PasswordError(107),     // 用户名或密码错误
        TimeFormatError(108),   // 时间格式错误：请输入正确格式的时间
        DateFormatError(109),   // 日期格式错误：请输入正确格式的日期
        ExistSubData(110),      // 存在子节点：请先将该节点下的子节点删除，再删除该节点
        ExistDeviceData(111),   // 存在关联的设备：请先将该地点所绑定的设备进行删除，再删除该地点
        DataNoExist(112);       // 数据不存在：该条数据不存在，请刷新后重试

        private Code(int value) {
            this.value = value;
        }

        private int value = 0;

        public int value() {
            return value;
        }
    }

    public enum Msg {
        InnerError("内部错误"),
        Success("执行成功"),
        ParamError("参数错误"),
        UnKnowError("内部错误"),
        AuthError("权限错误");

        private Msg(String msg) {
            this.msg = msg;
        }

        private String msg = "";

        public String value() {
            return msg;
        }
    }

}