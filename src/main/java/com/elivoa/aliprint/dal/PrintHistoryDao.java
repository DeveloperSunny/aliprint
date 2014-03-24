package com.elivoa.aliprint.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.beust.jcommander.internal.Lists;
import com.elivoa.aliprint.entity.PrintHistory;
import com.elivoa.aliprint.services.ConnectionPool;
import com.elivoa.aliprint.services.IConnectionPool;

public class PrintHistoryDao {
	// 1 id bigint 20 0 0 1 0 0
	// 0 order_id bigint 20 0 1 0 0 0
	// 0 total_price double 0 0 1 0 0
	// 0 buyer_login_id varchar 255 0 1 0
	// 0 buyerMemo varchar 2048 0 0 0
	// 0 buyerFeedback varchar 2048 0 0 0
	// 0 toName varchar 1024 0 1 0
	// 0 toPhone varchar 255 0 1 0
	// 0 toMobile varchar 255 0 1 0
	// 0 toAddress varchar 2048 0 1 0
	// 0 toPost varchar 255 0 1 0
	// logistics_company
	// tracking_number

	// 0 create_time timestamp 0 0 1 0

	// load agents from database; // disabled.
	public List<PrintHistory> listPrintHistory() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			ps = conn.prepareStatement("select id,order_id, total_price, buyer_login_id, buyerMemo, "
					+ "buyerFeedback, toName,toPhone, toMobile, toAddress, "
					+ "toPost,logistics_company, tracking_number, create_time from print_history");
			rs = ps.executeQuery();
			List<PrintHistory> list = Lists.newArrayList();
			while (rs.next()) {
				list.add(composeToken(rs));
			}
			return list;
		} finally {
			ConnectionPool.close(conn, ps, rs);
		}
	}

	private PrintHistory composeToken(ResultSet rs) throws SQLException {
		PrintHistory model = new PrintHistory();
		// model.setId(rs.getInt("id"));
		// model.setAliid(rs.getString("ali_id"));
		// model.setTaobaoId(rs.getString("taobao_id"));
		// model.setContactWangwang(rs.getString("contact_wangwang"));
		// model.setCompany(rs.getString("company"));
		// model.setName(rs.getString("name"));
		// model.setSenderName(rs.getString("sender_name"));
		// model.setSenderPhone(rs.getString("sender_phone"));
		// model.setAddress(rs.getString("address"));
		// model.setNote(rs.getString("note"));
		// model.setPriceNote(rs.getString("price_note"));
		// model.setShopAddress(rs.getString("shop_address"));
		// model.setShopScale(rs.getString("shop_scale"));
		// model.setUpdateTime(rs.getTimestamp("update_time"));
		// model.setCreateTime(rs.getTimestamp("create_time"));
		// logistics_company, tracking_number,
		return model;
	}

	public PrintHistory getAgent(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			ps = conn.prepareStatement("select id,order_id, total_price, buyer_login_id, buyerMemo, "
					+ "buyerFeedback, toName,toPhone, toMobile, toAddress, "
					+ "toPost, logistics_company, tracking_number, create_time from print_history where id = ?");
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

	public void saveupdates(PrintHistory model) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = pool.getConnection();
			String sql = "insert into print_history(order_id, total_price, buyer_login_id, buyerMemo, "
					+ "buyerFeedback, toName, toPhone, toMobile, toAddress, toPost, logistics_company, tracking_number) "
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update"
					+ " order_id=?, total_price=?, buyer_login_id=?, buyerMemo=?, "
					+ "buyerFeedback=?, toName=?, toPhone=?, toMobile=?, toAddress=?, toPost=?, logistics_company=?, tracking_number=?";
			ps = conn.prepareStatement(sql);

			int i = 1;
			ps.setLong(i++, model.getOrderId());
			ps.setDouble(i++, model.getTotalPrice());
			ps.setString(i++, model.getBuyerLoginId());
			ps.setString(i++, model.getBuyerMemo());
			ps.setString(i++, model.getBuyerFeedback());
			ps.setString(i++, model.getToName());
			ps.setString(i++, model.getToPhone());
			ps.setString(i++, model.getToMobile());
			ps.setString(i++, model.getToAddress());
			ps.setString(i++, model.getToPost());
			ps.setString(i++, model.getLogisticsCompany());
			ps.setString(i++, model.getTrackingNumber());

			ps.setLong(i++, model.getOrderId());
			ps.setDouble(i++, model.getTotalPrice());
			ps.setString(i++, model.getBuyerLoginId());
			ps.setString(i++, model.getBuyerMemo());
			ps.setString(i++, model.getBuyerFeedback());
			ps.setString(i++, model.getToName());
			ps.setString(i++, model.getToPhone());
			ps.setString(i++, model.getToMobile());
			ps.setString(i++, model.getToAddress());
			ps.setString(i++, model.getToPost());
			ps.setString(i++, model.getLogisticsCompany());
			ps.setString(i++, model.getTrackingNumber());

			ps.executeUpdate();
		} finally {
			ConnectionPool.close(conn, ps);
		}
	}

	// services

	@Inject
	IConnectionPool pool;

	// get by ids.;// not used now;
	// public List<SellAgent> getAgents(Collection<String> idList) throws SQLException {
	// if (null == idList || idList.size() == 0) {
	// return null;
	// }
	// Connection conn = null;
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// try {
	// conn = pool.getConnection();
	// StringBuilder sb = new StringBuilder();
	// sb.append("select id, ali_id, taobao_id, contact_wangwang, company, name, sender_name,"
	// + " sender_phone, address, note, price_note, shop_address, shop_scale, "
	// + "update_time, create_time from agents where ali_id in(");
	//
	// boolean first = true;
	// for (String id : idList) {
	// if (!first) {
	// sb.append(",");
	// } else {
	// first = false;
	// }
	// sb.append("'").append(id).append("'");
	// }
	// sb.append(")");
	//
	// ps = conn.prepareStatement(sb.toString());
	// rs = ps.executeQuery();
	// List<SellAgent> list = Lists.newArrayList();
	// while (rs.next()) {
	// list.add(composeToken(rs));
	// }
	// return list;
	// } finally {
	// ConnectionPool.close(conn, ps, rs);
	// }
	// }

}
