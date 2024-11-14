package com.honghe.common.common.enums;


/**
 * @Author youye
 * @Date 2019/3/16 16:17
 * @Description 返回值枚举
 **/
public enum ResultCode {
    // 数据格式错误
    DataFormatError(-9),
    // 存在子数据
    ExistSonData(-7),
    // 密码错误
    PasswordError(-5),
    // 数据量太大
    DataBigError(-10),
    // 未知错误
    UnKnowError(-110),
    // 重复
    Duplicate(-3),
    // 不存在
    NoExist(-2),
    // 参数错误
    ParamError(-1),
    TokenError(104),
    // 数据不存在：该条数据不存在，请刷新后重试
    DataNoExist(112),

    // 验证码错误：验证码错误
    ErrorVerificationCode(113),
    // 用户错误：没有权限
    UserNoPermissions(114),


    API_SUCCESS(0,"成功"),    // 对应以前的 0 值
    API_FAILED(1,"失败"),     // 对应以前的 1 值
    API_INNER_ERROR(1000,"接口内部错误"),        // 对应以前的 Center下ResultCode的 -4，本类的 -110
    API_PARAM_ERROR(1001,"参数错误"),           // 对应以前的 Center下ResultCode的 -1，本类的 -1

    API_PARAM_FORMAT_ERROR(1010,"参数格式错误"), // 对应以前的 Center下ResultCode的 -9，本类的 -9 和 103 和 109
    API_PARAM_TIME_FORMAT_ERROR(1011,"参数时间格式错误"), // 对应以前的 Center下ResultCode的 6，本类的 108
    API_PARAM_TIME_OVER_ERROR(1012,"参数时间过期"), // 对应以前的 Center下ResultCode的 6，本类的 -6
    API_PARAM_LENGth_ERROR(1013,"参数长度错误"), // 对应以前的 Center下ResultCode的 -10 和 4，本类的 -10 和 102 和 106

    API_VERIFICATION_CODE_ERROR(1050,"验证码错误"), // 本类的 113


    USER_NO_EXIST_ERROR(2000,"用户不存在"),    // 对应以前的 Center下ResultCode的 2，本类的 -2
    USER_PASSWORD_ERROR(2001,"用户密码错误"),   // 对应以前的 Center下ResultCode的 5，本类的 -5 和 107
    USER_TOKEN_ERROR(2002,"用户TOKEN过期"),    // 对应以前的 Center下ResultCode的 104，本类的 104
    USER_NAME_OR_PASSWORD_ERROR(2003,"用户名或密码错误"),
    USER_DUPLICATE_ERROR(2010,"用户数据重复"),  // 对应以前的 Center下ResultCode的 3，本类的 -3 和 105
    USER_NO_PERMISSION_ERROR(2011,"用户无权限"),
    USER_NO_ORG_ERROR(2012,"找不到该用户对应的组织"),

    USER_MOBILE_DUPLICATE_ERROR(2020,"用户手机号重复"),
    USER_EMAIL_DUPLICATE_ERROR(2021,"用户邮箱重复"),
    USER_CODE_DUPLICATE_ERROR(2022,"用户工号重复"),
    USER_CAMPUS_DUPLICATE_ERROR(2023,"机构重复"),
    USER_PASSWORD_DUPLICATE_ERROR(2024,"新密码和原密码相同！"),


    DATA_NO_EXIST_ERROR(3000,"数据不存在"),     // 对应以前的 Center下ResultCode的 2，本类的 -2 和 112
    DATA_DUPLICATE_ERROR(3001,"数据重复"),      // 对应以前的 Center下ResultCode的 3，本类的 -3 和 105
    DATA_DISABLE_ERROR(3002,"数据不可用"),      // 对应以前的 Center下ResultCode的 -11，本类的 101

    DATA_HAS_SON_ERROR(3010,"数据下存在子数据"), // 对应以前的 Center下ResultCode的 7，本类的 -7 和 110
    DATA_HAS_RELATION_ERROR(3011,"数据下存在关联数据"), // 对应以前的 Center下ResultCode的 8，本类的 111
    DATA_OVER_LIMIT_ERROR(3012,"数据超出限制");


    /**
     * #1000～1999 区间表示参数错误
     * #2000～2999 区间表示用户错误
     * #3000～3999 区间表示接口异常
     */

    private int value = 0;
    private String message = "";


    ResultCode(int value){
        this.value = value;
    }

    ResultCode(int value,String message){
        this.value = value;
        this.message = message;
    }


    public int value(){
        return value;
    }

    public String getMessage(){
        return this.message;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}