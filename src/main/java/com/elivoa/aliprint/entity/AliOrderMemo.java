package com.elivoa.aliprint.entity;

import java.sql.Timestamp;

import com.elivoa.aliprint.data.APIResponse;

public class AliOrderMemo {
	private Long id;// 否 主键id
	private Timestamp gmtModified;// Date 否 修改时间
	private Timestamp gmtCreate;// Date 否 创建时间
	private Long orderId;// Long 否 订单id
	private String memberId;// String 否 会员id
	private String remark;// String 否 备注内容
	private String remarkIcon;// String 否 备注图标

	public AliOrderMemo() {
		// empty constructure
	}

	public static AliOrderMemo create(APIResponse resp) {
		if (null != resp) {
			return new AliOrderMemo(resp);
		}
		return null;
	}

	public AliOrderMemo(APIResponse resp) {
		if (null == resp) {
			return;
		}
		this.id = resp.getLong("id");
		this.gmtModified = resp.parseTime("gmtModified");
		this.gmtCreate = resp.parseTime("gmtCreate");
		this.orderId = resp.getLong("orderId");
		this.memberId = resp.getString("memberId");
		this.remark = resp.getString("remark");
		this.remarkIcon = resp.getString("remarkIcon");
	}

	public boolean isValid() {
		return this.remark != null && !this.remark.equalsIgnoreCase("");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Timestamp gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemarkIcon() {
		return remarkIcon;
	}

	public void setRemarkIcon(String remarkIcon) {
		this.remarkIcon = remarkIcon;
	}

}
