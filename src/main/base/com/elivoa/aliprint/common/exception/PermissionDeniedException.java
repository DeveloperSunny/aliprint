package com.elivoa.aliprint.common.exception;

public class PermissionDeniedException extends RuntimeException {

	private static final long serialVersionUID = -7956898992254446654L;

	public PermissionDeniedException() {
		super();
	}

	public PermissionDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionDeniedException(String message) {
		super(message);
	}

	public PermissionDeniedException(Throwable cause) {
		super(cause);
	}

}
