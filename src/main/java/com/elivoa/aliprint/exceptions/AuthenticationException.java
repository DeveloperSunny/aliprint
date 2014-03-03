package com.elivoa.aliprint.exceptions;

/**
 * If this exception occured, jump to authentication page to authenticate again.
 * 
 * @author gb
 * 
 * @date Apr 20, 2009 @version 0.1.0 @by gb - initial.
 * @date Jan 22, 2010 @version 0.2.0 @by gb - move.
 */
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 7769433528074500095L;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message, Throwable e) {
		super(message, e);
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(Throwable e) {
		super(e);
	}
}
