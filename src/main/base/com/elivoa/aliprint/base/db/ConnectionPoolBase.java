package com.elivoa.aliprint.base.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elivoa.aliprint.func.Strings;
import com.elivoa.aliprint.services.IConnectionPool;

/**
 * @desc - ConnectionPoolBase
 * 
 * @author gb [elivoa AT gmail.com] @date Oct 26, 2008 @version 0.2.0.0
 * 
 * @Modified by gb, @date Oct 26, 2008 - Add Connection Inspector.
 * @Modified by gb, @date Dec 22, 2008
 * @Modified by gb, @date Mar 22, 2009 - move to Util.
 * @Modified by gb, @date Jan 24, 2010 - add two hook methods.
 * 
 */
@SuppressWarnings("unused")
public class ConnectionPoolBase {
	private static final Log LOG = LogFactory.getLog(ConnectionPoolBase.class);

	private String dbpool_impl;
	private String db_driver;
	private String db_url;
	private String db_user;
	private String db_pass;
	private int dbpool_max_statements;
	private int dbpool_max_pool_size;

	private String proxool_name;

	private IConnectionPool poolInstance;

	public ConnectionPoolBase(String dbpool_impl, String db_driver, String db_url, String db_user, String db_pass,
			int dbpool_max_statements, int dbpool_max_pool_size, String proxool_name) {
		this.dbpool_impl = dbpool_impl;
		this.db_driver = db_driver;
		this.db_url = db_url;
		this.db_user = db_user;
		this.db_pass = db_pass;
		this.dbpool_max_statements = dbpool_max_statements;
		this.dbpool_max_pool_size = dbpool_max_pool_size;

		this.proxool_name = proxool_name;

		// change(db_url, db_user, db_pass);
	}

	public void change(String url, String user, String pass, int dbpool_size) {
		if (null != poolInstance) {
			poolInstance.destroy();
		}
		// if ("c3p0".equals(dbpool_impl)) {
		// poolInstance = new C3P0ConnectionPoolImpl(db_driver, url, user, pass,
		// dbpool_max_statements,
		// dbpool_size);
		// } else {
		// poolInstance = new ProxoolConnectionPoolImpl(proxool_name, db_driver,
		// url, user, pass,
		// dbpool_max_statements, dbpool_size);
		// }
	}

	public void change(String ip, int port, String database, String user, String pass) {
		this.change(ip, port, database, user, pass, dbpool_max_pool_size);
	}

	public void change(String ip, int port, String database, String user, String pass, int poolSize) {
		String url = String
				.format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=UTF-8", ip, port, database);
		this.change(url, user, pass, poolSize);
	}

	public void change(String ip, String database, String user, String pass) {
		this.change(ip, 3306, database, user, pass, dbpool_max_pool_size);
	}

	public void change(String connString) {
		String[] split = Strings.safeTrim(connString).split("/");
		this.change(split[0], 3306, split[1], split[2], split[3], dbpool_max_pool_size);
	}

	public Connection getConnection() {
		try {
			if (null == poolInstance) {
				change(this.db_url, this.db_user, this.db_pass, this.dbpool_max_pool_size);
			}
			Connection conn = poolInstance.getConnection();
			if (null == conn) {
				throw new RuntimeException("Get Connection Failed.");
			}
			return conn;
		} catch (Throwable t) {
			connectionExceptionHook(t);
		}
		return null;
	}

	public void destroy() {
		poolInstance.destroy();
		destroyHook();
	}

	/**
	 * Implementers can override this hook method.
	 */
	protected void connectionExceptionHook(Throwable t) {
		System.out.println("####################################");
		System.out.println("## ERROR WHEN CONNECTING DATABASE ##");
		System.out.println("####################################");
		// default action.
		// System.exit(-1);
	}

	/**
	 * Implementers can override this hook method.
	 */
	protected void destroyHook() {
	}

	/*
	 * Static
	 */
	public static void close(ResultSet o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void close(Statement o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void close(Connection o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void close(Connection conn, Statement ps, ResultSet rs) {
		close(rs);
		close(ps);
		close(conn);
	}

	public static void close(Connection conn, Statement ps) {
		close(ps);
		close(conn);
	}

	public static void close(Statement ps, ResultSet rs) {
		close(rs);
		close(ps);
	}

}
