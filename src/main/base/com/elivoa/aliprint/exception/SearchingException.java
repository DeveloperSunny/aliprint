package com.elivoa.aliprint.exception;

/**
 * SearchingException
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Apr 9, 2012]
 */
public class SearchingException extends RuntimeException {

	private static final long serialVersionUID = -669027374884800155L;

	public SearchingException() {
		super();
	}

	public SearchingException(String message, Throwable cause) {
		super(message, cause);
	}

	public SearchingException(String message) {
		super(message);
	}

	public SearchingException(Throwable cause) {
		super(cause);
	}

}
