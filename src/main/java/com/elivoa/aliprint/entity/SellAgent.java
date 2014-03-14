package com.elivoa.aliprint.entity;

import java.sql.Timestamp;

public class SellAgent {
	private int id;

	/** should be buyerMemberId */
	private String aliid;
	private String taobaoId;
	private String contactWangwang;
	private String company;
	private String name;
	private String senderName;
	private String senderPhone;
	private String address;
	private String note;
	private String priceNote;
	private String shopAddress;
	private String shopScale;
	private Timestamp updateTime;
	private Timestamp createTime;

	public SellAgent() {
		// empty constructure
	}

//	public SellAgent(String senderName, String senderPhone, String address) {
//		super();
//		this.senderName = senderName;
//		this.senderPhone = senderPhone;
//		this.address = address;
//	}

	public String getAddressWithNewLine() {
		if (null != this.address) {
			return this.address.replace("\n", "<br />");
		}
		return "";
	}

	// accessors

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAliid() {
		return aliid;
	}

	public void setAliid(String aliid) {
		this.aliid = aliid;
	}

	public String getTaobaoId() {
		return taobaoId;
	}

	public void setTaobaoId(String taobaoId) {
		this.taobaoId = taobaoId;
	}

	public String getContactWangwang() {
		return contactWangwang;
	}

	public void setContactWangwang(String contactWangwang) {
		this.contactWangwang = contactWangwang;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPriceNote() {
		return priceNote;
	}

	public void setPriceNote(String priceNote) {
		this.priceNote = priceNote;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopScale() {
		return shopScale;
	}

	public void setShopScale(String shopScale) {
		this.shopScale = shopScale;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
