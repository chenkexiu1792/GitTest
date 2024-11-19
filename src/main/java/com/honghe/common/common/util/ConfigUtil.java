package com.honghe.common.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * 读取配置文件
 *
 * Created by haoyc on 2015/12/25.
 */
public class ConfigUtil {

    static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);


    /**
     * 获取配置文件Key对应的Value值
     * @param key Key值
     * @return
     */
    public static final String getConfigValue(String key) {
        return getConfigValue(key,"");
    }


     /**
     * 获取配置文件Key对应的Value值
     * @param key Key值
     * @param defaultValue 默认值
     * @return
     */
    public static String getConfigValue(String key, String defaultValue) {
        return getProperties(key,defaultValue,"config.properties");
    }


    /**
     * 获取配置文件Key对应的Value值
     * @param key Key值
     * @return
     */
    public static String getPropertiesValue(String key) {
        return getPropertiesValue(key,"");
    }


    /**
     * 获取配置文件Key对应的Value值
     * @param key Key值
     * @param defaultValue 默认值
     * @return
     */
    public static String getPropertiesValue(String key, String defaultValue) {
        return getProperties(key,defaultValue,"application.properties");
    }


    /**
     * 读取配置文件
     * @param key Key值
     * @param defaultValue 默认值
     * @param fileName 读取的配置文件
     * @return
     */
    public static String getProperties(String key, String defaultValue,String fileName) {
        String value = "";
        try {
            Properties properties = new Properties();

            //先读取config目录的，没有再加载classpath的
            String filePath = System.getProperty("user.dir") + File.separator + "config" + File.separator + fileName;
            File file = new File(filePath);
            if (file.exists()){
                InputStream in = new FileInputStream(file);
                properties.load(in);
            } else {
                InputStream stream = ConfigUtil.class.getClassLoader().getResourceAsStream(fileName);
                properties.load(new InputStreamReader(stream, "UTF-8"));
                //properties.load(new InputStreamReader(ConfigUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
            }

            if (properties.containsKey(key) && !"".equals(properties.get(key))) {
                value = (String)properties.get(key);
            }else{
                value = defaultValue;
            }

        } catch (IOException e) {
            logger.debug("read config error=" + e.getMessage());
            return null;
        }

        return value;
    }
}
