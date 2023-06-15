package com.example.springbootaopredis.exception;

/**
 * 通用抛出异常处理
 * @author yuming
 *
 */
public class CommonExcept extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    public CommonExcept(String msg) {
        super(msg);
    }
}
