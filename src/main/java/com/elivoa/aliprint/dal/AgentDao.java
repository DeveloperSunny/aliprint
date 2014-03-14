package com.elivoa.aliprint.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.beust.jcommander.internal.Lists;
import com.elivoa.aliprint.entity.SellAgent;
import com.elivoa.aliprint.services.ConnectionPool;
import com.elivoa.aliprint.services.IConnectionPool;

public class AgentDao {

	// load agents from database;
	public List<SellAgent> listAgents() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			ps = conn.prepareStatement("select id, ali_id, taobao_id, contact_wangwang, company, name, sender_name,"
					+ " sender_phone, address, note, price_note, shop_address, shop_scale, "
					+ "update_time, create_time from agents");
			rs = ps.executeQuery();
			List<SellAgent> list = Lists.newArrayList();
			while (rs.next()) {
				list.add(composeToken(rs));
			}
			return list;
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}

	private SellAgent composeToken(ResultSet rs) throws SQLException {
		SellAgent model = new SellAgent();
		model.setId(rs.getInt("id"));
		model.setAliid(rs.getString("ali_id"));
		model.setTaobaoId(rs.getString("taobao_id"));
		model.setContactWangwang(rs.getString("contact_wangwang"));
		model.setCompany(rs.getString("company"));
		model.setName(rs.getString("name"));
		model.setSenderName(rs.getString("sender_name"));
		model.setSenderPhone(rs.getString("sender_phone"));
		model.setAddress(rs.getString("address"));
		model.setNote(rs.getString("note"));
		model.setPriceNote(rs.getString("price_note"));
		model.setShopAddress(rs.getString("shop_address"));
		model.setShopScale(rs.getString("shop_scale"));
		model.setUpdateTime(rs.getTimestamp("update_time"));
		model.setCreateTime(rs.getTimestamp("create_time"));
		return model;
	}

	public SellAgent getAgent(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			ps = conn.prepareStatement("select id, ali_id, taobao_id, contact_wangwang, company, name, sender_name,"
					+ " sender_phone, address, note, price_note, shop_address, shop_scale, "
					+ "update_time, create_time from agents where id = ?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				return composeToken(rs);
			}
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
		return null;
	}

	public SellAgent getAgent(String aliid) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			ps = conn.prepareStatement("select id, ali_id, taobao_id, contact_wangwang, company, name, sender_name,"
					+ " sender_phone, address, note, price_note, shop_address, shop_scale, "
					+ "update_time, create_time from agents where ali_id = ?");
			ps.setString(1, aliid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return composeToken(rs);
			}
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
		return null;
	}

	public void saveupdates(SellAgent model) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = pool.getConnection();
			String sql = "insert into agents(ali_id, taobao_id, contact_wangwang, company, name, sender_name,"
					+ " sender_phone, address, note, price_note, shop_address, shop_scale, create_time) "
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp()) on duplicate key update"
					+ " taobao_id=?, contact_wangwang=?, company=?, name=?, sender_name=?, sender_phone=?, "
					+ "address=?, note=?, price_note=?, shop_address=?, shop_scale=?";
			ps = conn.prepareStatement(sql);

			ps.setString(1, model.getAliid());
			ps.setString(2, model.getTaobaoId());
			ps.setString(3, model.getContactWangwang());
			ps.setString(4, model.getCompany());
			ps.setString(5, model.getName());
			ps.setString(6, model.getSenderName());
			ps.setString(7, model.getSenderPhone());
			ps.setString(8, model.getAddress());
			ps.setString(9, model.getNote());
			ps.setString(10, model.getPriceNote());
			ps.setString(11, model.getShopAddress());
			ps.setString(12, model.getShopScale());

			ps.setString(13, model.getTaobaoId());
			ps.setString(14, model.getContactWangwang());
			ps.setString(15, model.getCompany());
			ps.setString(16, model.getName());
			ps.setString(17, model.getSenderName());
			ps.setString(18, model.getSenderPhone());
			ps.setString(19, model.getAddress());
			ps.setString(20, model.getNote());
			ps.setString(21, model.getPriceNote());
			ps.setString(22, model.getShopAddress());
			ps.setString(23, model.getShopScale());

			ps.executeUpdate();
		} finally {
			ConnectionPool.close(conn, ps);
		}
	}

	// services

	@Inject
	IConnectionPool pool;

	public List<SellAgent> getAgents(Collection<String> idList) throws SQLException {
		if (null == idList || idList.size() == 0) {
			return null;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select id, ali_id, taobao_id, contact_wangwang, company, name, sender_name,"
					+ " sender_phone, address, note, price_note, shop_address, shop_scale, "
					+ "update_time, create_time from agents where ali_id in(");

			boolean first = true;
			for (String id : idList) {
				if (!first) {
					sb.append(",");
				} else {
					first = false;
				}
				sb.append("'").append(id).append("'");
			}
			sb.append(")");

			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			List<SellAgent> list = Lists.newArrayList();
			while (rs.next()) {
				list.add(composeToken(rs));
			}
			return list;
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}

}
