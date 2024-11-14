package com.honghe.common.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.honghe.common.common.constants.Constants;
import com.honghe.common.common.enums.ResultCode;
import com.honghe.common.common.result.Result;
import com.honghe.common.exception.AppRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParamUtil {

    protected static Logger logger = LoggerFactory.getLogger(new ParamUtil().getClass());

    public static Map<String, Object> format(String json) {
        List<Integer> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        StringBuilder builder = new StringBuilder();

        JSONArray jsonArray = JSONArray.parseArray(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(Integer.parseInt(jsonArray.get(i).toString()));
            builder.append(jsonArray.get(i).toString()).append(",");
        }
        map.put("format", builder.deleteCharAt(builder.length() - 1));
        map.put("list", list);
        return map;
    }

    /**
     * 检查map中参数存在或是否为空
     *
     * @param map
     * @param paraName
     * @return
     */
    public static boolean checkParam(Map map, String... paraName) {
        for (String s : paraName) {
            if (!map.containsKey(s) || "".equals(map.get(s)) || null == map.get(s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkParam(String... params) throws AppRuntimeException {
        if (params == null || params.length == 0) {
            throw new AppRuntimeException("-1", "参数为空");
        }

        for (String param : params) {
            if (param == null || "".equals(param)) {
                throw new AppRuntimeException(ResultCode.ParamError.toString(), "参数错误");
            }
        }

        return true;
    }
    /**
     * 判断参数是否存在为空的
     *
     * @param objects
     * @return 存在：true；不存在：false
     */
    public static boolean isOneEmpty(Object... objects) {
        boolean flag = false;
        for (Object o : objects) {
            if (o == null) {
                flag = true;
                break;
            }
            if (o instanceof String) {
                if ("".equals(o)) {
                    flag = true;
                    break;
                }
            } else if (o instanceof Integer) {
                if (Integer.parseInt(String.valueOf(o)) < 0) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 判断参数是否全部为空
     *
     * @param objects
     * @return 是：true；不是：false
     */
    public static boolean isEmpty(Object... objects) {
        return isOneEmpty(objects);
    }


//    public static boolean isEmpty(String value) {
//        return value == null || "".equals(value);
//    }

    /**
     * 判断参数是否为int型
     *
     * @param objects
     * @return 不是：true；是：false
     */
    public static boolean isNotInteger(Object... objects) {
        boolean flag = false;
        for (Object o : objects) {
            try {
                Integer.parseInt(String.valueOf(o));
            } catch (Exception e) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断参数是否为日期格式
     *
     * @param objects
     * @return 不是：true；是：false
     */
    public static boolean isNotDate(Object... objects) {
        boolean flag = false;
        for (Object o : objects) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.parse(String.valueOf(o));
            } catch (Exception e) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断参数是否为时间格式
     *
     * @param objects
     * @return 不是：true；是：false
     */
    public static boolean isNotDateTime(Object... objects) {
        boolean flag = false;
        for (Object o : objects) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.parse(String.valueOf(o));
            } catch (Exception e) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static Map getPageRows(String page, String pageSize) {
        Map map = new HashMap();
        int page_int = Integer.parseInt(page);
        int pageSize_int = Integer.parseInt(pageSize);

        int startNum = (page_int - 1) * pageSize_int;
        int endNum = page_int * pageSize_int;

        map.put("startNum", startNum);
        map.put("endNum", endNum);
        return map;
    }

    /**
     * 获取request参数
     *
     * @param request http请求信息
     * @return 参数map
     */
    public static Map<String, Object> getParams(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        Map<String, Object> params = new HashMap<>();
        String type = request.getHeader("Content-Type");
        if ("GET".equalsIgnoreCase(request.getMethod()) || !type.contains("application/json")) {
            parameters = request.getParameterMap();
            int len;
            for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                len = entry.getValue().length;
                if (len == 1) {
                    params.put(entry.getKey(), entry.getValue()[0]);
                } else if (len > 1) {
                    params.put(entry.getKey(), entry.getValue());
                }
            }
        } else {
            try {
                ServletInputStream inputStream = request.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line + "\n");
                }
                String strContent = content.toString();
                params = (Map<String, Object>) JSON.parse(strContent);
            } catch (IOException e) {
                Logger logger = LoggerFactory.getLogger(ParamUtil.class);
                logger.error("getParams异常", e);
            }
        }
        return params;
    }

    /* ------------------------------------------------------ 私有方法：校验参数值 -----------------------------------------  */

    /**
     * @param restTime 时间戳（时间戳到秒级）
     * @return boolean true:不超时，false：超时
     * @Description 校验时间是否超时
     * @date 2018年12月17日 上午10:28:03
     */
    public static boolean checkRestTime(Object restTime, String timeOut) {
        if (restTime != null && restTime.toString().length() >= 10) {
            String restStr = restTime.toString();
            if (restStr.length() > 10) {
                restStr = restStr.substring(0, 10);
            }
            String nowTimeStr = String.valueOf(new Date().getTime()).substring(0, 10);
            logger.info("oldTimeStr=" + restStr + ">>> nowTimeStr=" + nowTimeStr);

            long restValue = Long.valueOf(restStr);
            long nowTimeValue = Long.valueOf(nowTimeStr);
            long gapTime = nowTimeValue - restValue;

            return (gapTime > -(Integer.valueOf(timeOut)) && gapTime < Integer.valueOf(timeOut));
        }

        return false;
    }

    /**
     * @param params 参数
     * @return boolean true:可用，false：错误
     * @Description 校验参数加密串是否正确
     * @date 2018年12月17日 上午10:28:03
     */
    public static String generateRestAuth(Map<String, Object> params) {
        Object applicationValue = params.get(Constants.kParam_ApplicationValue);
        params.remove(Constants.kParam_ApplicationValue);
        params.remove(Constants.kParam_RestAuth);

        String urlParams = "";
        String[] keys = (String[]) params.keySet().toArray(new String[params.size()]);
        Arrays.sort(keys);
        for (String key : keys) {
            String value = (String) params.get(key);
            try {
                urlParams = urlParams + key + "=" + URLEncoder.encode(value, "UTF-8").replaceAll("\\*", "%2A") + "&";
            } catch (UnsupportedEncodingException e) {
                logger.info("Un supported Encoding Exception");
            }
        }

        urlParams = urlParams + applicationValue;

        logger.info("urlParams=" + urlParams);
        //String paramsStr = urlParams.replaceAll("&" + restAuthValue, "").replaceAll(restAuthValue + "&", "");

//        return MD5Util.getMD5(urlParams.getBytes());
        return MD5Util.MD5(urlParams);
    }


    /**
     * 公共处理返回结果方法
     *
     * @param result 鸿合i学返回的结果
     * @return 处理后的结果
     */
    public static Object parseResult(Result result) {
        try {
            if (result != null) {
                Map<String, Object> map = (Map<String, Object>) result.getResult();

                if ((boolean) map.get("success")) {
                    return map.get("success");
                }
            }
        } catch (Exception e) {
//            logMsg.setMsg("parse result error");
//            logMsg.setE(e);
//            LogUtil.error(logMsg);
            e.printStackTrace();
            return false;
        }
        return false;
    }


}
