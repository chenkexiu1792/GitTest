package com.honghe.common.common.auth.interceptor;

import com.honghe.common.common.enums.ResultCode;
import com.honghe.common.common.result.CommonResult;
import com.honghe.common.common.result.Result;
import com.honghe.common.exception.AppRuntimeException;
import com.honghe.common.exception.CommandException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @description: 全局异常处理
 * @author: wuxiao
 * @create: 2020-03-26 19:54
 **/
@RestControllerAdvice("com.honghe.plugin")
public class GloablExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResult handleException(Exception exception) {
        if (exception instanceof CommandException) {
            CommandException e = (CommandException)exception;
            return CommonResult.fail(e.getCode(),e.getMsg());

        } else if (exception instanceof MethodArgumentNotValidException) {
            List<ObjectError> objectErrorList = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors();
            if (CollectionUtils.isEmpty(objectErrorList)) {
                return CommonResult.fail(objectErrorList.get(0).getDefaultMessage());
            }
        } else if (exception instanceof AppRuntimeException){
            // 添加对用户Token失效时的判断，并且返回不同的错误码
            AppRuntimeException appException = (AppRuntimeException)exception;
            return CommonResult.fail(String.valueOf(appException.getCode()),appException.getMsg());
        }

        return CommonResult.fail("发生错误: " + exception.getLocalizedMessage());
    }
}
