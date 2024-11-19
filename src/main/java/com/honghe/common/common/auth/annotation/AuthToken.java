package com.honghe.common.common.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 校验用户Token 超时时间
 * @author: wuxiao
 * @create: 2020-03-30 11:10
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthToken {
    boolean required() default true;
}
