package com.elivoa.aliprint.exceptions;

/**
 * If this exception occured, jump to authentication page to authenticate again.
 * 
 * @author gb
 * 
 * @date Apr 20, 2009 @version 0.1.0 @by gb - initial.
 * @date Jan 22, 2010 @version 0.2.0 @by gb - move.
 */
public class NeedAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 7769433528074500095L;

	public NeedAuthenticationException() {
		super();
	}

	public NeedAuthenticationException(String message, Throwable e) {
		super(message, e);
	}

	public NeedAuthenticationException(String message) {
		super(message);
	}

	public NeedAuthenticationException(Throwable e) {
		super(e);
	}

}
