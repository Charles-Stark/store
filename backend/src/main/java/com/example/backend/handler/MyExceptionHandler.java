package com.example.backend.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.example.backend.constant.Status;
import com.example.backend.exception.BaseException;
import com.example.backend.pojo.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ApiResult baseExceptionHandler(BaseException exception) {
        return ApiResult.ofException(exception);
    }

    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public ApiResult notLoginExceptionHandler(NotLoginException exception) {
        return ApiResult.ofStatus(Status.NOT_LOGGED_IN);
    }

    @ExceptionHandler
    @ResponseBody
    public ApiResult errorHandler(Exception exception) {
        return ApiResult.ofError();
    }

}
