package com.elivoa.aliprint.common.exception;

import com.elivoa.aliprint.pages.ExceptionReport;

/**
 * PageNotFoundException
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Oct 16, 2012]
 */
public class PageNotFoundException extends RuntimeException implements ContextAwareException {

	private static final long serialVersionUID = -8516928686034047945L;

	public PageNotFoundException() {
		super();
	}

	public PageNotFoundException(String message, Throwable e) {
		super(message, e);
	}

	public PageNotFoundException(String message) {
		super(message);
	}

	public PageNotFoundException(Throwable e) {
		super(e);
	}

	public String getRedirectPageName() {
		return ExceptionReport.class.getSimpleName();
	}

	public Object[] getContext() {
		return new Object[] { super.getMessage() };
	}

}
