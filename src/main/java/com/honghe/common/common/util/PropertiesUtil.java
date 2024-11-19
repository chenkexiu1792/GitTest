package com.honghe.common.common.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PropertiesUtil {

//    LogUtil.LogMsg logMsg = new LogUtil.LogMsg("Common", PropertiesUtil.class.getName());

    private final static String basePath = System.getProperty("user.dir");
    private String proFilePath;
    private volatile Map<String, Map<String, String>> cacheMap = new ConcurrentHashMap<>();
    private BufferedInputStream inputStream = null;
    private ResourceBundle bundle = null;

    private PropertiesUtil() {
    }

    private static class Properties {
        private static PropertiesUtil propertiesUtil = new PropertiesUtil();
    }

    public static PropertiesUtil instance() {
        return Properties.propertiesUtil;
    }

    public synchronized void setPath(String path) {
        proFilePath = basePath + path;
    }

    public synchronized String get(String key) {
        String value = "";
        Map<String, String> propertiesMap = cacheMap.get(proFilePath);
        if (propertiesMap != null) {
            value = propertiesMap.get(key);
        } else {
            int readNum = 3;
            while (readNum > 0) {//如果未读到信息，循环3次尝试
                value = readProFile(proFilePath, key);
                if ("".equals(value) || value == null) {//未读到信息
                    readNum--;
                    continue;
                } else {//读到信息
                    break;
                }
            }
        }
//        logMsg.setMsg("read properties: "+proFilePath+" key= "+key+" value= "+value);
//        LogUtil.info(logMsg);
        return value;
    }


    private String readProFile(String proFilePath, String key) {
        String value = "";

        try {
            File file = new File(proFilePath);
            if (file.exists()) {
                inputStream = new BufferedInputStream(new FileInputStream(proFilePath));
                bundle = new PropertyResourceBundle(inputStream);
                Enumeration<String> keys = bundle.getKeys();
                Map<String, String> keyValueMap = new HashMap<>();
                while (keys.hasMoreElements()) {
                    String proKey = keys.nextElement();
                    String proValue = bundle.getString(proKey);
                    if (proKey.equals(key)) {
                        value = proValue;
                    }
                    keyValueMap.put(proKey, proValue);
                }
                cacheMap.put(proFilePath, keyValueMap);
            } else {
                value = "";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return value;
    }


    /**
     * 获取配置文件字段信息，没有信息返回默认值
     *
     * @param key          字段关键key
     * @param defaultValue 字段默认值
     * @return
     */
    public synchronized String get(String key, String defaultValue) {
        String value = get(key);

        if ("".equals(value) || value == null) {
            value = defaultValue;
        }

        return value;
    }
}
