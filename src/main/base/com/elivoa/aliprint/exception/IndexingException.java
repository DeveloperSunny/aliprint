package com.elivoa.aliprint.exception;

/**
 * AccessDeniedException
 */
public class IndexingException extends RuntimeException {

	private static final long serialVersionUID = -669027374884800155L;

	public IndexingException() {
		super();
	}

	public IndexingException(String message, Throwable cause) {
		super(message, cause);
	}

	public IndexingException(String message) {
		super(message);
	}

	public IndexingException(Throwable cause) {
		super(cause);
	}

}
