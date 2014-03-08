package com.elivoa.aliprint.entity;

import java.sql.Timestamp;
import java.util.List;

import com.beust.jcommander.internal.Lists;
import com.elivoa.aliprint.data.APIResponse;

/**
 * 旧版订单列表；只有发件地址是有用的；
 */
public class AliOldOrder {

	// common
	private long id;// = 547455850372940;
	private int tradeType;// =6
	private String status;// =waitbuyerpay,
	private Timestamp gmtModified; // =2014-02-25 19:37:13,
	private Timestamp gmtCreate; // =2014-02-25 19:37:13,

	// ali member
	private String sellerMemberId;// =b2b-1700826905,
	private String buyerMemberId;// =b2b-1024184029,
	private int buyerRateStatus;// =5,
	private int sellerRateStatus;// =5,

	private List<AliOrderEntity> orderEntities;

	// alipay
	private String alipayTradeId;// =2014022500001000690032435530,
	private String sellerAlipayId;// =2088902863476281,
	private String buyerAlipayId;// =2088802380720699,
	private int sumPayment;// =26700,
	private int discount = 0;
	private int carriage;// =1400, ---- ????

	// address
	private String toFullName;// =张瑞
	private String toMobile;// =18671999078,
	private String toArea;// =湖北省 荆门市 掇刀区 五一路东站36号,
	private String toPost;// =448000, ---- ????

	public AliOldOrder(APIResponse resp) {

		// // common
		this.id = resp.getLong("id");
		this.tradeType = resp.getInt("tradeType");
		this.status = resp.getString("status");

		this.gmtModified = resp.parseTime("gmtModified");
		this.gmtCreate = resp.parseTime("gmtCreate");// parseOrderGMTTime(resp, "gmtCreate");

		this.sellerMemberId = resp.getString("sellerMemberId");
		this.buyerMemberId = resp.getString("buyerMemberId");
		this.sellerRateStatus = resp.getInt("sellerRateStatus");
		this.buyerRateStatus = resp.getInt("buyerRateStatus");

		this.alipayTradeId = resp.getString("alipayTradeId");
		this.sellerAlipayId = resp.getString("sellerAlipayId");
		this.buyerAlipayId = resp.getString("buyerAlipayId");

		this.sumPayment = resp.getInt("sumPayment");
		this.discount = resp.getInt("discount");
		this.carriage = resp.getInt("carriage");

		// address
		this.toFullName = resp.getString("toFullName");
		this.toMobile = resp.getString("toMobile");
		this.toArea = resp.getString("toArea");
		this.toPost = resp.getString("toPost");

		// order entities; // TODO disable this. no use.
//		List<APIResponse> list = resp.getRespList("orderEntries");
//		if (null != list) {
//			this.orderEntities = Lists.newArrayList();
//			for (APIResponse respEntity : list) {
//				AliOrderEntity entity = new AliOrderEntity(respEntity);
//				this.orderEntities.add(entity);
//			}
//		}
	}

	// accessors

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTradeType() {
		return tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getSellerMemberId() {
		return sellerMemberId;
	}

	public void setSellerMemberId(String sellerMemberId) {
		this.sellerMemberId = sellerMemberId;
	}

	public String getBuyerMemberId() {
		return buyerMemberId;
	}

	public void setBuyerMemberId(String buyerMemberId) {
		this.buyerMemberId = buyerMemberId;
	}

	public int getBuyerRateStatus() {
		return buyerRateStatus;
	}

	public void setBuyerRateStatus(int buyerRateStatus) {
		this.buyerRateStatus = buyerRateStatus;
	}

	public int getSellerRateStatus() {
		return sellerRateStatus;
	}

	public void setSellerRateStatus(int sellerRateStatus) {
		this.sellerRateStatus = sellerRateStatus;
	}

	public List<AliOrderEntity> getOrderEntities() {
		return orderEntities;
	}

	public void setOrderEntities(List<AliOrderEntity> orderEntities) {
		this.orderEntities = orderEntities;
	}

	public String getAlipayTradeId() {
		return alipayTradeId;
	}

	public void setAlipayTradeId(String alipayTradeId) {
		this.alipayTradeId = alipayTradeId;
	}

	public String getSellerAlipayId() {
		return sellerAlipayId;
	}

	public void setSellerAlipayId(String sellerAlipayId) {
		this.sellerAlipayId = sellerAlipayId;
	}

	public String getBuyerAlipayId() {
		return buyerAlipayId;
	}

	public void setBuyerAlipayId(String buyerAlipayId) {
		this.buyerAlipayId = buyerAlipayId;
	}

	public int getSumPayment() {
		return sumPayment;
	}

	public double getSumPaymentDouble() {
		return new Double(sumPayment) / 100;
	}

	public void setSumPayment(int sumPayment) {
		this.sumPayment = sumPayment;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getCarriage() {
		return carriage;
	}

	public double getCarriageDouble() {
		return new Double(carriage) / 100;
	}

	public void setCarriage(int carriage) {
		this.carriage = carriage;
	}

	public String getToFullName() {
		return toFullName;
	}

	public void setToFullName(String toFullName) {
		this.toFullName = toFullName;
	}

	public String getToMobile() {
		return toMobile;
	}

	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}

	public String getToArea() {
		return toArea;
	}

	public void setToArea(String toArea) {
		this.toArea = toArea;
	}

	public String getToPost() {
		return toPost;
	}

	public void setToPost(String toPost) {
		this.toPost = toPost;
	}

}
