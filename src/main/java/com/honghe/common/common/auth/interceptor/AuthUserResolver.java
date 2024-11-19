package com.honghe.common.common.auth.interceptor;

import com.honghe.common.common.auth.CommonTokenResult;
import com.honghe.common.common.constants.Constants;
import com.honghe.common.common.auth.annotation.AuthToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @description: auth info resolve
 * @author: wuxiao
 * @create: 2020-03-30 17:16
 **/
@Configuration
public class AuthUserResolver implements HandlerMethodArgumentResolver{

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if(parameter.getParameterType().isAssignableFrom(CommonTokenResult.class) && parameter.hasParameterAnnotation(AuthToken.class)){
            return true;
        }
        return false;

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return nativeWebRequest.getAttribute(Constants.AUTH_TOKENRESULT, RequestAttributes.SCOPE_REQUEST);
    }
}
