package com.elivoa.aliprint.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.c3p0.PooledDataSource;

/**
 * @desc - ConnectionPool
 * 
 * @author gb<elivoa@gmail.com> @date Oct 26, 2008 @version 0.2.0.0
 * 
 * @Modified by gb, @date Oct 26, 2008 - Add Connection Inspector.
 * @Modified by gb, @date Dec 22, 2008
 * @Modified by gb, @date Mar 22, 2009 - move to Util.
 * 
 */
public class C3P0ConnectionPoolImpl implements IConnectionPool {

	private static final Log log = LogFactory.getLog(C3P0ConnectionPoolImpl.class);

	private String dbpool_impl;
	private String db_driver;
	private String db_url;
	private String db_user;
	private String db_pass;
	private int dbpool_max_statements;
	private int dbpool_max_pool_size;

	@Inject
	public C3P0ConnectionPoolImpl(@Symbol("db.pool.impl") String dbpool_impl, @Symbol("db.driver") String db_driver,
			@Symbol("db.url") String db_url, @Symbol("db.user") String db_user, @Symbol("db.pass") String db_pass,
			@Symbol("db.pool.max_statements") int dbpool_max_statements,
			@Symbol("db.pool.max_pool_size") int dbpool_max_pool_size) {

		this.dbpool_impl = dbpool_impl;
		this.db_driver = db_driver;
		this.db_url = db_url;
		this.db_user = db_user;
		this.db_pass = db_pass;
		this.dbpool_max_statements = dbpool_max_statements;
		this.dbpool_max_pool_size = dbpool_max_pool_size;

		log.info("init connection pool");

		System.out.println("INFO: C3P0 Connect to " + db_url);

		try {
			long time = System.currentTimeMillis();

			// Note: your JDBC driver must be loaded [via Class.forName( ... )
			// or -Djdbc.properties]
			// prior to acquiring your DataSource!
			Class.forName(db_driver);
			log.debug("DB Driver:" + db_driver);

			// Acquire the DataSource... this is the only c3p0 specific code
			// here
			DataSource unpooled = DataSources.unpooledDataSource(db_url, db_user, db_pass);

			Map<String, Object> overrides = new HashMap<String, Object>();
			overrides.put("maxStatements", dbpool_max_statements);
			overrides.put("maxPoolSize", dbpool_max_pool_size);

			pooled = DataSources.pooledDataSource(unpooled, overrides);

			log.info("Init ConnectionPool: " + db_url + ", " + db_user + ", " + db_pass + ", with maxStatements="
					+ dbpool_max_statements + ", maxPoolSize=" + dbpool_max_pool_size + ". In "
					+ (System.currentTimeMillis() - time) + "ms.");

			// start Debug Thread.
			if (log.isDebugEnabled()) {
				new Thread(new Runnable() {
					public void run() {
						while (true) {
							logStatus();
							try {
								Thread.sleep(20000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				});// .start();
			}

		} catch (Exception e) {
			log.error("Init ConnectionPool Exception:" + e + "\n" + e.getStackTrace());
			e.printStackTrace();
		}
	}

	private DataSource pooled;

	public void destroy() {
		try {
			DataSources.destroy(pooled);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void logStatus() {
		try {
			PooledDataSource pds = (PooledDataSource) pooled;
			StringBuilder sb = new StringBuilder();
			sb.append("Status:(");
			sb.append(pds.getNumConnectionsDefaultUser()).append(" conn, ");
			sb.append(pds.getNumBusyConnectionsDefaultUser()).append(" busy, ");
			sb.append(pds.getNumIdleConnectionsDefaultUser()).append(" idle");
			sb.append(")");
			log.debug(sb.toString());

		} catch (SQLException e) {
			log.error(e + " : " + e.getMessage() + "\n" + e.getStackTrace());
		} catch (Exception e) {
			log.error(e + " : " + e.getMessage() + "\n" + e.getStackTrace());
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			return pooled.getConnection();
		} catch (Exception e) {
			log.error("Cannot get Connection:" + e + " : " + e.getMessage() + "\n" + e.getStackTrace());
		}
		return null;
	}

	/***********************************************************************************************
	 * Test
	 **********************************************************************************************/
	public static void main(String[] argv) {
		long time = System.currentTimeMillis();
		List<Connection> list = new ArrayList<Connection>();

		for (int i = 0; i < 20; i++) {
			long time2 = System.currentTimeMillis();
			// ConnectionPool.getInstance().test();
			Connection connection = null;// =
			// ConnectionPool.getInstance().getConnection();
			list.add(connection);
			// if (i % 2 == 0) {
			// }
			System.out.println(i + ":" + (System.currentTimeMillis() - time2) + "//" + connection);
		}
		System.out.println(System.currentTimeMillis() - time);
	}

	//
	// public void change(String ip, int port, String database, String user,
	// String pass) {
	// this.change(ip, port, database, user, pass, dbpool_max_pool_size);
	// }
	//
	// public void change(String ip, int port, String database, String user,
	// String pass, int poolSize) {
	// String url = String
	// .format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=UTF-8",
	// ip, port, database);
	// this.change(url, user, pass, poolSize);
	// }
	//
	// public void change(String ip, String database, String user, String pass)
	// {
	// this.change(ip, 3306, database, user, pass, dbpool_max_pool_size);
	// }
	//
	// public void change(String connString) {
	// String[] split = Strings.safeTrim(connString).split("/");
	// this.change(split[0], 3306, split[1], split[2], split[3],
	// dbpool_max_pool_size);
	// }

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
