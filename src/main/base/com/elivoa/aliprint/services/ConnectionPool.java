package com.elivoa.aliprint.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.elivoa.aliprint.base.db.ConnectionPoolBase;
import com.elivoa.aliprint.bridge.T5RegistryHelper;


/**
 * @Name ConnectionPool.java
 * @Desc ...
 * @StartDate 2008/10/26
 * @author vivo elivoa|gmail.com
 * @version 0.01
 * 
 * @ChangeHistory <ul>
 *                <li>2008-10-26 vivo, Create.</li>
 *                <li>2008-12-22 vivo, Add Connection Inspector.</li>
 *                <li>2009-01-12 vivo, modification.</li>
 *                </ul>
 */
@SuppressWarnings("unused")
public class ConnectionPool extends ConnectionPoolBase {

	private static final Log log = LogFactory.getLog(ConnectionPool.class);

	private static IConnectionPool instance;

	public synchronized static IConnectionPool getInstance() {
		if (null == instance) {
			instance = T5RegistryHelper.getService(IConnectionPool.class);
		}
		return instance;
	}

	private String dbpool_impl;
	private String db_driver;
	private String db_url;
	private String db_user;
	private String db_pass;
	private int dbpool_max_statements;
	private int dbpool_max_pool_size;

	@Inject
	public ConnectionPool(@Symbol("db.pool.impl") String dbpool_impl, @Symbol("db.driver") String db_driver,
			@Symbol("db.url") String db_url, @Symbol("db.user") String db_user, @Symbol("db.pass") String db_pass,
			@Symbol("db.pool.max_statements") int dbpool_max_statements,
			@Symbol("db.pool.max_pool_size") int dbpool_max_pool_size) {

		super(dbpool_impl, db_driver, db_url, db_user, db_pass, dbpool_max_statements, dbpool_max_pool_size,
				"arent-proxool");
		this.dbpool_impl = dbpool_impl;
		this.db_driver = db_driver;
		this.db_url = db_url;
		this.db_user = db_user;
		this.db_pass = db_pass;
		this.dbpool_max_statements = dbpool_max_statements;
		this.dbpool_max_pool_size = dbpool_max_pool_size;

		log.info("init connection pool");
	}

	/*
	 * Test
	 */
	public void test() {
		try {
			// get hold of a Connection an do stuff, in the usual way
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;

			try {
				IConnectionPool pool = ConnectionPool.getInstance();
				con = ConnectionPool.getInstance().getConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT * FROM foo");
				while (rs.next()) {
					// System.out.println(rs.getString(1) + "/" +
					// rs.getString(2));
				}

				//
				// fetch a JNDI-bound DataSource
				// InitialContext ictx = new InitialContext();
				// DataSource ds = (DataSource) ictx.lookup(
				// "java:comp/env/jdbc/myDataSource" );

				// make sure it's a c3p0 PooledDataSource
				// if (pooled instanceof PooledDataSource) {
				// PooledDataSource pds = (PooledDataSource) pooled;
				// System.err.println("num_connections: " +
				// pds.getNumConnectionsDefaultUser());
				// System.err.println("num_busy_connections: "
				// + pds.getNumBusyConnectionsDefaultUser());
				// System.err.println("num_idle_connections: "
				// + pds.getNumIdleConnectionsDefaultUser());
				// System.err.println();
				// } else
				// System.err.println("Not a c3p0 PooledDataSource!");

				//
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// i try to be neurotic about ResourceManagement,
				// explicitly closing each resource
				// but if you are in the habit of only closing
				// parent resources (e.g. the Connection) and
				// letting them close their children, all
				// c3p0 DataSources will properly deal.
				close(rs);
				close(stmt);
				close(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***********************************************************************************************
	 * Test
	 **********************************************************************************************/
	public static void main(String[] argv) {
		long time = System.currentTimeMillis();
		List<Connection> list = new ArrayList<Connection>();
		IConnectionPool pool = ConnectionPool.getInstance();
		pool.getConnection();
		// pool.change("166.111.134.216", 3306, "psn_sync", "root",
		// "eserver4-09");
		for (int i = 0; i < 11; i++) {
			long time2 = System.currentTimeMillis();
			// ConnectionPool.getInstance().test();
			Connection connection = pool.getConnection();
			list.add(connection);
			// if (i % 2 == 0) {
			System.out.println(i + ":" + (System.currentTimeMillis() - time2) + "//" + connection);
			close(connection);
			// }
		}
		System.out.println(System.currentTimeMillis() - time);
	}

}
