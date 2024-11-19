package com.honghe.common.common.constants;

/**
 * Created by zhanghongbin on 2015/5/5.
 */
public final class Response {
    //  全局配置
    public final static int False = 0;  //错误
    public final static int True = 1;   //正确

    public static final String SUCCESS = "0";        //成功
    public static final String FAIL = "1";           //失败
    public static final String PARAM_ERROR = "-1";   //参数错误
    public static final String NO_METHOD = "-2";     //没有该方法
    public static final String INNER_ERROR = "-3";   //内部错误
    public static final String UNAUTHORIZED_ACCESS = "-4";  //未通过验证
    public static final String RESTRICTING_ACCESS = "-5";   //锁定
    /*
     * @Create wuxiao 2018/12/3 17:56
     */
    public static final String NULL_RESULT_ERROR = "-6";    //没有返回结果
    public static final String OUT_TIME_ERROR = "2000";     //读取超时
}
