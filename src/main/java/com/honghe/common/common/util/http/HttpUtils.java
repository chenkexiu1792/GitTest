package com.honghe.common.common.util.http;

import com.alibaba.fastjson.JSONObject;
import com.honghe.common.common.result.Result;
import com.honghe.common.common.util.StringUtil;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * 发送http请求工具类
 *
 * @auther yuk
 * @create 2017-10-11 13:55
 */
@Component
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    final static int CONNECT_TIME = 60000;//连接超时
    final static int SOCKET_TIME = 60000;//获取数据超时


    @Resource
    private RestTemplate restTemplate;

    /**
     * post方式请求 可根据传入的类型返回相应的类型
     *
     * @param url
     * @return java.lang.Object
     * @Create wuxiao 2018/9/29 0:45
     * @Update wuxiao 2018/9/29 0:45
     */
    public <T> T post(String url, Map<String, Object> params, Class<T> type) {
        MultiValueMap<String, Object> paramsMap = new LinkedMultiValueMap<String, Object>();
        paramsMap.setAll(params);
        return restTemplate.postForEntity(url, paramsMap, type).getBody();
    }

//    public static Result httpPostJson(String url, String jsonParam) {
//        Result result = new Result();
//        //post请求返回结果
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        //设置请求和传输超时时间
//        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME).setConnectTimeout(CONNECT_TIME).build();
//        JSONObject jsonResult = null;
//        HttpPost method = new HttpPost(url);
//        method.setConfig(requestConfig);
//        try {
//            if (null != jsonParam){
//                StringEntity entity = new StringEntity(jsonParam, "UTF-8");
//                entity.setContentEncoding("UTF-8");
//                entity.setContentType("application/json");
//                method.setEntity(entity);
//            }
//
//            HttpResponse response = httpClient.execute(method);
//            url = URLDecoder.decode(url, "UTF-8");
//            /**请求发送成功，并得到响应**/
//            if (response.getStatusLine().getStatusCode() == 200) {
//                String str = "";
//                try {
//                    /**读取服务器返回过来的json字符串数据**/
//                    str = EntityUtils.toString(response.getEntity());
//                    /**把json字符串转换成json对象**/
//                    jsonResult = JSON.parseObject(str);
//                    if (jsonResult == null) {
//                        jsonResult = new JSONObject();
//                        jsonResult.put("result", str);
//                    }
//                    result.setResult(jsonResult);
//                } catch (Exception e) {
//                    result.setCode(Result.Code.UnKnowError.value());
//                    result.setMsg("UnKnowError");
//                }
//            }
//        } catch (IOException e) {
//            logger.error("httpPostJson请求异常，url:" + url + ",param:" + jsonParam, e);
//            result.setCode(Result.Code.UnKnowError.value());
//            result.setMsg("UnKnowError");
//        }
//        return result;
//    }

    /**
     * POST请求
     * 对 String code ，boolean success ，String message， Object result 返回类型Result做转换
     */
    public static Result jsonPost(String urlStr, Map<String, Object> params) {
        Result result = new Result();
        String paramStr = JSONObject.toJSONString(params);
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(paramStr);
            out.flush();
            out.close();
            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();

            if (null != res && !res.equals("")) {
                JSONObject jsonObject = JSONObject.parseObject(res);
                if (jsonObject == null || jsonObject.get("success") == null) {
                    logger.error("jsonPost请求异常，url:" + urlStr + ",param:" + params);
                    result = Result.programFail;
                }
                Boolean success = (Boolean) jsonObject.get("success");
                String code = (String) jsonObject.get("code");
                result.setStatus(success);
                result.setCode(Integer.valueOf(code));
                result.setResult(jsonObject.get("result"));
                if (jsonObject.get("message") != null) {
                    String message = (String) jsonObject.get("message");
                    result.setMsg(message);
                }
            }
        } catch (IOException e) {
            logger.error("jsonPost请求异常，url:" + urlStr + ",param:" + params, e);
            result = Result.programFail;
        }
        return result;
    }

    /**
     * get请求
     * 对 String code ，boolean success ，String message， Object result 返回类型Result做转换
     */
    public static Result httpGet(String url) {
        Result result = new Result();
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            request.addHeader("Content-Type", "application/json");
            request.addHeader("charset", "UTF-8");
            //连接超时和请求超时时间设置
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000).build();
            request.setConfig(requestConfig);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity(), "GBK");
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.parseObject(strResult);
                if (jsonResult == null || jsonResult.get("success") == null) {
                    logger.error("httpGet请求异常，url:" + url);
                    result = Result.programFail;
                }
                Boolean success = (Boolean) jsonResult.get("success");
                String code = (String) jsonResult.get("code");
                result.setStatus(success);
                result.setCode(Integer.valueOf(code));
                result.setResult(jsonResult.get("result"));
                if (jsonResult.get("message") != null) {
                    String message = (String) jsonResult.get("message");
                    result.setMsg(message);
                }
                url = URLDecoder.decode(url, "UTF-8");
            }
        } catch (IOException e) {
            logger.error("httpGet请求异常，url:" + url, e);
        }
        return result;
    }

    /**
     * @param url    接口地址(无参数)
     * @param params 拼接参数集合
     * @Description get请求URL拼接参数
     */
    public static String getAppendUrl(String url, Map<String, String> params) {
        Map<String, String> map = new HashMap<>();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    map.put(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
                } else {
                    map.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (map != null && !map.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (StringUtil.isEmpty(buffer.toString())) {
                    buffer.append("?");
                } else {
                    buffer.append("&");
                }
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
            }
            url += buffer.toString();
        }
        return url;
    }
}