package com.elivoa.aliprint.common.exception;

/**
 * AccessDeniedException
 * 
 * 当访问资源权限不够时抛出此异常
 */
public class AccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = -669087374884800155L;

	public AccessDeniedException() {
		super();
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDeniedException(String message) {
		super(message);
	}

	public AccessDeniedException(Throwable cause) {
		super(cause);
	}

}
