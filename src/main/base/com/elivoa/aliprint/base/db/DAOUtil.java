package com.elivoa.aliprint.base.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.elivoa.aliprint.services.ConnectionPool;

/**
 * @Name CommonDAO.java
 * @Desc ...
 * @StartDate 2008/10/26
 * @author vivo <mailto:elivoa@gmail.com>
 * @version 0.01
 * 
 * @ChangeHistory <ul>
 *                <li>Nov 11, 2008 vivo, Create.</li>
 *                <li>Nov 11, 2008 vivo, Improve.</li>
 *                </ul>
 */
public class DAOUtil {

	private static final boolean debug = true;

	public static void executeSql(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ps = conn.prepareStatement(sql);
			ps.execute();
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}

	public static int queryForInt(String sql) {
		Integer intValue = -1;
		try {
			intValue = queryValue(sql, Integer.class, null);
		} catch (SQLException e) {
			System.err.println(e);
		}
		return (null == intValue) ? -1 : intValue;
	}

	public static <T> T queryValue(String sql, Class<T> clazz, String param)
			throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ps = conn.prepareStatement(sql);
			if (null != param) {
				ps.setString(1, param);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return getFromResultSet(rs, 1, clazz);
			}
			return null;
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}

	public static <T> T queryValue(String sql, Class<T> clazz, int param)
			throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, param);
			rs = ps.executeQuery();
			if (rs.next()) {
				return getFromResultSet(rs, 1, clazz);
			}
			return null;
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}

	/**
	 * Support Integer, Long, String
	 */
	@SuppressWarnings("unchecked")
	private static <T> T getFromResultSet(ResultSet rs, int idx, Class<T> clazz)
			throws SQLException {
		if (clazz.equals(Integer.class)) {
			return (T) (Object) rs.getInt(idx);
		} else if (clazz.equals(Long.class)) {
			return (T) (Object) rs.getLong(idx);
		} else if (clazz.equals(String.class)) {
			return (T) (Object) rs.getString(idx);
		} else {
			throw new RuntimeException("Unsupported type " + clazz.getName());
		}
	}

	public static List<String> querySimpleStrings(String sql)
			throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			List<String> result = new ArrayList<String>();
			while (rs.next()) {
				result.add(rs.getString(1));
			}
			return result;
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}

	public List<Integer> querySimpleIntegers(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			List<Integer> result = new ArrayList<Integer>();
			while (rs.next()) {
				result.add(rs.getInt(1));
			}
			return result;
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}

	public static int[] query2Int(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				return new int[] { rs.getInt(1), rs.getInt(2) };
			} else {
				return null;
			}
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}

	public Integer[] query2Int(String sql, Object... params)
			throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				Object param = params[i];
				if (param instanceof String) {
					ps.setString(i + 1, (String) param);
				} else if (param instanceof Integer) {
					ps.setInt(i + 1, (Integer) param);
				} else if (param instanceof Double) {
					ps.setDouble(i + 1, (Double) param);
				} else {
					throw new RuntimeException(
							"CommonDao: Not supported type :"
									+ param.getClass()
									+ ", please continue maintain this method.");
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return new Integer[] { rs.getInt(1), rs.getInt(2) };
			} else {
				return null;
			}
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}
}
