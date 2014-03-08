package com.elivoa.aliprint.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.beust.jcommander.internal.Lists;
import com.elivoa.aliprint.entity.ProductAlias;
import com.elivoa.aliprint.services.ConnectionPool;
import com.elivoa.aliprint.services.IConnectionPool;

public class ProductDao {

	// load products from db.
	public List<ProductAlias> listProducts() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			ps = conn.prepareStatement("select id, serial, name, alias, create_time, update_time "
					+ "from product_alias");
			rs = ps.executeQuery();
			List<ProductAlias> list = Lists.newArrayList();
			while (rs.next()) {
				list.add(composeToken(rs));
			}
			return list;
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}

	private ProductAlias composeToken(ResultSet rs) throws SQLException {
		ProductAlias model = new ProductAlias();
		model.setId(rs.getLong("id"));
		model.setSerial(rs.getString("serial"));
		model.setName(rs.getString("name"));
		model.setAlias(rs.getString("alias"));
		model.setCreateTime(rs.getTimestamp("create_time"));
		model.setUpdateTime(rs.getTimestamp("update_time"));
		return model;
	}

	public void saveAlias(ProductAlias model) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = pool.getConnection();
			String sql = "insert into product_alias (id,serial,name,alias,create_time) values(?,?,?,?,current_timestamp()) "
					+ "on duplicate key update alias=?";
			ps = conn.prepareStatement(sql);

			ps.setLong(1, model.getId());
			ps.setString(2, model.getSerial());
			ps.setString(3, model.getName());
			ps.setString(4, model.getAlias());
			ps.setString(5, model.getAlias());
			ps.executeUpdate();
		} finally {
			ConnectionPool.close(conn, ps);
		}
	}

	// services

	@Inject
	IConnectionPool pool;

	public List<ProductAlias> getProductAlias(Collection<Long> idList) throws SQLException {
		if (null == idList || idList.size() == 0) {
			return null;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select id, serial, name, alias, create_time, update_time from product_alias where id in (");

			boolean first = true;
			for (Long id : idList) {
				if (!first) {
					sb.append(",");
				} else {
					first = false;
				}
				sb.append(id);
			}
			sb.append(")");
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			List<ProductAlias> list = Lists.newArrayList();
			while (rs.next()) {
				list.add(composeToken(rs));
			}
			return list;
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}
}
