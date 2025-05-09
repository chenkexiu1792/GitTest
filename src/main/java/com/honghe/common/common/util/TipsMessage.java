package com.honghe.common.common.util;

/**
 * @Description log日志或异常提示语
 * @Author sunchao
 * @Date: 2017-11-29 17:15
 * @Mender:
 */
public class TipsMessage {

    //请求成功
    public static final int SUCCESS_CODE = 0;
    //请求失败
    public static final int FAILED_CODE = 1;

    public static final String FAILED_MSG = "请求失败";

    //参数错误
    public static final int PARAM_ERROR_CODE = -1;

    //内部错误
    public static final int INNER_ERROR_CODE = -3;


    public static final String MSG_KEY = "msg";

    public static final String REQUEST_SUCCESS = "请求成功";

    public static final String REQUEST_ERROR = "请求失败";

    public static final String SAVE_ERROR = "保存失败";

    public static final String UPDATE_ERROR = "更新失败";

    public static final String DELETE_ERROR = "删除失败";

    public static final String SELECT_ERROR = "查询失败";

    public static final String PARAM_ERROR = "参数错误";

    public static final String PARES_ERROR = "转换失败";


    public static final String SERVICE_ERROR = "后端服务异常";

    public static final String SERVICE_TIMEOUT = "后端服务超时异常";


    public static final String SERVICE_CLIENT_MSG = "请求失败，请检查请求数据，请求类型等是否正确";

    public static final String USER_PARAM_ERROR = "用户名密码不能为空";

    public static final String USER_NOT_EXIST = "用户不存在";

    public static final String USER_EXIST = "用户已存在";

    public static final String FORMAT_DATE_MSG = "日期格式错误";

    // 请求的读取数据超时
    public static final String API_REQ_SOCKET_TIMEOUT_ERROR = "读取数据超时";

    // 请求的获取链接超时
    public static final String API_REQ_CONN_TIMEOUT_ERROR = "获取http连接超时";

    public static final int USER_EXIST_CODE = -1;

    public static final int USER_NOT_EXIST_CODE = -1;

    public static final int PASSWORD_ERROR_CODE = -2;

    public static final String USER_PASSWORD_ERROR = "用户密码错误";

    public static final String USER_LOGIN_ERROR = "用户登录失败";

    public static final String SERVICE_RESPONSE_ERROR = "获取服务响应数据异常";

    public static final String THREAD_ERROR = "线程发生异常";

    // 未知错误
    public static final String ERROR = "未知错误";

    public static final int RETRY_TIME = 3;
    public static final String FTP_FROM="server";
    public static final String FTP_APPLIED="已申请";
    public static final String VIDEO_FORMAT = "mp4";
    public static final String VIDEO_TYPE = "avi,wmv,mov,rm,rmvb,mpg,mpeg,mp4,vob,flv,f4v,mkv,3gp,m4v,ts,asf";
    public static final String IMAGE_TYPE = "jpg,jpeg,png,bmp,gif";
    public static final String DOC_TYPE = "doc,docx,ppt,pptx,xlsx,xls,pdf,txt";
    public static final String DSS_TYPE = "dss";
    public static final String VIDEO_TYPE_NOTRANS = "swf";
}
