package com.example.springbootaopredis.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    public Result<?> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.buildFailure(ResultCodeEnum.NO_HANDLER_FOUND_ERROR.getCode(), ResultCodeEnum.NO_HANDLER_FOUND_ERROR.getValue());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
    public Result<?> handleNullPointerException(NullPointerException e) {
        log.error(e.getMessage() +":",e);
        return Result.buildFailure(ResultCodeEnum.ERROR.getCode(), "空指针异常：" + e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.buildFailure(ResultCodeEnum.ERROR.getCode(), e.getMessage());
    }

    /**
     * 请求参数不是JSON异常
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> httpMessageNotReadableException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.buildFailure(ResultCodeEnum.ERROR.getCode(), "请求参数读取失败");
    }

    /**
     * 处理 json 请求体调用接口校验失败抛出的异常
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handlerException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMsg = bindingResult.getFieldErrors().stream().map(o -> o.getDefaultMessage()).collect(Collectors.joining(","));
        return Result.buildFailure(ResultCodeEnum.CONSTRAINT_VIOLATION_ERROR.getCode(), errorMsg);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> HttpRequestMethodNotSupportedException(Exception e) {
        ResultCodeEnum resultCodeEnum = ResultCodeEnum.HTTP_REQUEST_METHOD_NOT_SUPPORTED_ERROR;
        return Result.buildFailure(resultCodeEnum.getCode(), resultCodeEnum.getValue());
    }


}
