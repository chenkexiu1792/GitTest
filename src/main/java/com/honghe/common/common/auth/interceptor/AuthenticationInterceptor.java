package com.honghe.common.common.auth.interceptor;

import com.honghe.common.common.auth.CommonTokenResult;
import com.honghe.common.common.auth.annotation.AuthToken;
import com.honghe.common.common.constants.Constants;
import com.honghe.common.common.auth.annotation.LoginToken;
import com.honghe.common.common.auth.annotation.PassToken;
import com.honghe.common.common.auth.service.TokenService;
import com.honghe.common.common.enums.ResultCode;
import com.honghe.common.exception.AppRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @description: token 拦截验证
 * @author: wuxiao
 * @create: 2020-03-26 19:53
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)object;
        Method method = handlerMethod.getMethod();

        // 检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(AuthToken.class)) {
            AuthToken userAuthToken = method.getAnnotation(AuthToken.class);
            if (userAuthToken.required()) {
                CommonTokenResult tokenResult = checkUserAuthToken(httpServletRequest,true);

                // 给请求设置用户信息属性
                httpServletRequest.setAttribute(Constants.AUTH_TOKENRESULT, tokenResult);
            }
        } else if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken userLoginToken = method.getAnnotation(LoginToken.class);
            if (userLoginToken.required()) {
                CommonTokenResult tokenResult = checkUserAuthToken(httpServletRequest,false);
                httpServletRequest.setAttribute(Constants.AUTH_TOKENRESULT, tokenResult);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }



    /**
     * 校验用户Token
     * @param httpServletRequest 请求
     * @param checkTimeOut 是否校验超时时间
     * @return
     */
    private CommonTokenResult checkUserAuthToken(HttpServletRequest httpServletRequest,boolean checkTimeOut) {
        // 从 http 请求头中取出用户Token
        String accessToken = httpServletRequest.getHeader(Constants.AUTH_XACCESSTOKEN);
        if (null == accessToken) {
            //throw new RuntimeException("用户Token认证失败,请重新登录!");
            throw new AppRuntimeException(ResultCode.ParamError.toString(), "参数错误");
        }

        // 校验用户Token 是否有效
        CommonTokenResult result = tokenService.checkToken(accessToken);
        if(null == result || !accessToken.equals(result.getToken())){
            //throw new RuntimeException("用户Token 超时或失效,请重新登录!");
            throw new AppRuntimeException(ResultCode.TokenError.toString(), "用户Token超时或失效,请重新登录!");
        }

        long nowTimeStamp = new Date().getTime();
        if (checkTimeOut && nowTimeStamp - result.getTimestamp() > Constants.TOKEN_EXPIRES_SECOND){
            //throw new RuntimeException("用户Token 超时或失效,请重新登录!");
            throw new AppRuntimeException(ResultCode.TokenError.toString(), "用户Token超时或失效,请重新登录!");
        }

        return result;
    }
}
