package com.elivoa.aliprint.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.alibaba.openapi.client.auth.AuthorizationToken;
import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.services.ConnectionPool;
import com.elivoa.aliprint.services.IConnectionPool;

public class TokenDao {

	/**
	 * Save token into db
	 * 
	 * @param token
	 * @throws SQLException
	 */
	public void saveToken(AliToken token) throws SQLException {
		AuthorizationToken authToken = token.getToken();
		if (null == authToken) {
			throw new RuntimeException("Authorization Token is null! Can't store null token into DB.");
		}

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = pool.getConnection();
			ps = conn
					.prepareStatement("insert into tokens (aliid, uid, owner, access_token, refresh_token, "
							+ "expires_time, refresh_token_timeout, create_time) values (?, ?, ?, ?, ?, ?, ?, current_timestamp())");

			ps.setLong(1, authToken.getAliId());
			ps.setString(2, authToken.getUid());
			ps.setString(3, authToken.getResource_owner());
			ps.setString(4, authToken.getAccess_token());
			ps.setString(5, authToken.getRefresh_token());
			ps.setTimestamp(6, new Timestamp(authToken.getExpires_time().getTime()));

			// sometimes in test environment, refresh token timeout is null;
			Date refreshTokenTimeout = authToken.getRefreshTokenTimeout();
			Timestamp refreshTokenTimeoutTimestamp = null;
			if (null != refreshTokenTimeout) {
				refreshTokenTimeoutTimestamp = new Timestamp(refreshTokenTimeout.getTime());
			}
			ps.setTimestamp(7, refreshTokenTimeoutTimestamp);

			ps.executeUpdate();
		} finally {
			ConnectionPool.close(conn, ps);
		}
	}

	public AliToken getTokenByAliid(long aliid) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			ps = conn.prepareStatement("select aliid, uid, owner, access_token, refresh_token, "
					+ "expires_time, refresh_token_timeout, update_time, create_time from tokens where aliid = ?");
			ps.setLong(1, aliid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return composeToken(rs);
			}
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
		return null;
	}

	public AliToken getTokenByOwner(String owner) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			ps = conn.prepareStatement("select aliid, uid, owner, access_token, refresh_token, "
					+ "expires_time, refresh_token_timeout, update_time, create_time from tokens where owner = ?");
			ps.setString(1, owner);
			rs = ps.executeQuery();
			if (rs.next()) {
				return composeToken(rs);
			}
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
		return null;
	}

	public AliToken getTokenByUid(String uid) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			ps = conn.prepareStatement("select aliid, uid, owner, access_token, refresh_token, "
					+ "expires_time, refresh_token_timeout, update_time, create_time from tokens where uid = ?");
			ps.setString(1, uid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return composeToken(rs);
			}
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
		return null;
	}

	private AliToken composeToken(ResultSet rs) throws SQLException {
		AliToken token = new AliToken();
		AuthorizationToken authToken = new AuthorizationToken();
		authToken.setAliId(rs.getLong("aliid"));
		authToken.setUid(rs.getString("uid"));
		authToken.setResource_owner(rs.getString("owner"));
		authToken.setAccess_token(rs.getString("access_token"));
		authToken.setRefresh_token(rs.getString("refresh_token"));

		// expires in
		Timestamp expires = rs.getTimestamp("expires_time");
		long expiresIn = (expires.getTime() - System.currentTimeMillis()) / 1000;
		authToken.setExpires_in(expiresIn);

		// refresh token timeout
		Timestamp refreshTokenTimeout = rs.getTimestamp("refresh_token_timeout");
		if (null != refreshTokenTimeout) {
			authToken.setRefreshTokenTimeout(new Date(refreshTokenTimeout.getTime()));
		}

		token.setUpdateTime(rs.getTimestamp("update_time"));
		token.setCreateTime(rs.getTimestamp("create_time"));

		token.setToken(authToken);
		return token;
	}

	// services

	@Inject
	IConnectionPool pool;
}
