package com.elivoa.aliprint.common.exception;

/**
 * ContextAwareException
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Jan 8, 2012]
 */
public interface ContextAwareException {

	/**
	 * @return Page name to redirect when this Exception throws out.
	 */
	String getRedirectPageName();

	/**
	 * @return Context of redirected page. Usually contains source page and message.
	 */
	Object[] getContext();

}
