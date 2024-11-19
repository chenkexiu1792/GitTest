package com.honghe.common.common.util.bean;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * bean和map转换工具类
 *
 * @auther yuk
 * @Time 2017/10/10 12:58
 */
public class BeanToMapUtil {
    private static Logger logger = LoggerFactory.getLogger(BeanToMapUtil.class);

    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
    public static <T> T transMap2Bean2(Map<String, Object> map, T obj) {
        if (map == null || obj == null) {
            return null;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            logger.error("transMap2Bean2 Error ", e);
        }

        return obj;
    }

    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static <T> T transMap2Bean(Map<String, Object> map, T obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                try {
                    if (map.containsKey(key)) {
                        Object value = map.get(key);
                        // 得到property对应的setter方法
                        Method setter = property.getWriteMethod();
                        setter.invoke(obj, value);
                    }
                } catch (Exception e) {
                    logger.error("transMap2Bean Error(key：" + key + ") ", e);
                }
            }

        } catch (Exception e) {
            logger.error("transMap2Bean Error ", e);
        }
        return obj;

    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!"class".equals(key)) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.error("transBean2Map Error ", e);
        }

        return map;

    }
}
