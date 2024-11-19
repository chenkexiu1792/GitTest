package com.honghe.common.common.constants;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
public class Constants {

    // redis
    public static final String WECHAT_INFO = "wechatInfo";
    public static final String WECHAT_API_TOKEN = "wechatApiToken";
    //redis key值超时时长
    public static final Integer KEYTIMEOUT = 3600;


    //  Token秘钥 请勿泄露
    public static final String TOKEN_SECRET = "HHT-SmartOffice";
    public static final String TOKEN_CLAIM_KEY = "tokenKey";
    //  接口token缓存时长
    public static final int TOKEN_EXPIRES_DAY = 7;
    public static final int TOKEN_EXPIRES_MINUTES = 150;
    public static final long TOKEN_EXPIRES_SECOND = (1000*60*60*24*7);
    public static final int TOKEN_EXPIRES_VERIFY_SECOND = 600;
    //  认证信息
    public static final String AUTH_TOKENRESULT = "tokenResult";
    public static final String AUTH_XACCESSTOKEN= "x-access-token";

    //  全局配置
    public final static int False = 0;
    public final static int True = 1;

    //  AV设备大类型
    //  Token秘钥 请勿泄露
    public static final String AV_CENTRALCONTROL = "CentralControl";
    public static final String AV_DEVICE = "AvDevice";
    //
//    public final static String kCode_Auth__Error = "-7";       //  接口参数加密验证错误
//    public final static String kCode_Application_Error = "-6"; //  接口调用应用来源错误
//    public final static String kCode_API_Timeout_Error = "-5"; //  接口调用超时或参数错误
//    public final static String kCode_Save_Error = "-4";        //  存储失败
//    public final static String kCode_TransCode__Error = "-3";  //  解码失败
//    public final static String kCode_Image_Error = "-2";       //  图片不合格
//    public final static String kCode_Params_Error = "-1";      //  参数错误
//    public final static String kCode_Success = "0";            //  成功
//    public final static String kCode_Fail = "1";               //  失败
//    public final static String kCode_InnerError = "3";         //  內部錯誤
//    public final static String kCode_Warn = "4";               //  警告提示
//    public final static String kCode_UnAuth_Error = "2000";    //  没有权限
//    public final static String kCode_Session_Error = "2001";   //  登陆超时
//    public final static String kCode_Base_Error = "1000";      //  請求北京基礎數據出錯。


    //  cookie保存
//    public static final String kHashCookie = "kHashCookie";
//    public static final String kCookie_Spokeo = "spokeo";
//    public static final String kCookie_icris = "icris";
//    public static final String kCookie_neighborwho = "neighborwho";
//    public static final String kCookie_indeed = "indeed";
//    public static final String kCookie_familytreenow = "familytreenow";
//    public static final String kCookie_veromi = "veromi";
//    public static final String[] kCookies = new String[]{
//            kCookie_Spokeo, kCookie_icris, kCookie_neighborwho, kCookie_indeed, kCookie_familytreenow, kCookie_veromi
//    };

    public static final String kCharSet_UTF8 = "UTF-8";


    // API 公共参数
    public static final String kParam_Application = "application";
    public static final String kParam_ApplicationValue = "applicationValue";
    public static final String kParam_DataSource = "dataSource";
    public static final String kParam_RestTime = "restTime";
    public static final String kParam_RestAuth = "restAuth";


}
