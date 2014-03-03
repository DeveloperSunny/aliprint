package com.elivoa.aliprint.func;

/**
 * Callback
 * 
 * Common Callback Function
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Jan 4, 2012]
 * @version 1.0
 */
public interface Callback<T, R> {
	R execute(T model);
}
