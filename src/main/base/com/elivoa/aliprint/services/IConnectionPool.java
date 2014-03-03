package com.elivoa.aliprint.services;

import java.sql.Connection;

/**
 * @desc - IConnectionPool
 * 
 * @author bogao: elivoa|gmail @date Oct 26, 2008 @version v0.1.0
 * @modify bogao: elivoa|gmail @date Aug 9, 2010 @version v0.2.0
 * 
 * @Modified by gb, @date Oct 26, 2008 - Add Connection Inspector.
 * @Modified by gb, @date Dec 22, 2008
 * @Modified by gb, @date Mar 22, 2009 - move to Util.
 */
public interface IConnectionPool {

	/**
	 * @return Connection
	 */
	Connection getConnection();

	/**
	 * Destroy when application end.
	 */
	void destroy();

}
