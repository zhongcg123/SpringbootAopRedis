package com.example.springbootaopredis.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 功能描述: 卷烟接口调用返回码
 *
 * @Date    2022/8/30 13:46
 * @Author  liangjian
 */
@AllArgsConstructor
@Getter
public enum ResultCodeEnum {

    OK(200,"执行成功"),
    ERROR(500,"服务器异常,请联系管理员"),
    FAIL(-1,"执行失败"),
    NO_HANDLER_FOUND_ERROR(404,"路径不存在，请检查路径是否正确"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED_ERROR(405,"没有权限，请联系管理员授权"),
    CONSTRAINT_VIOLATION_ERROR(400,"参数校验失败");

    private Integer code;

    private String value;

}
