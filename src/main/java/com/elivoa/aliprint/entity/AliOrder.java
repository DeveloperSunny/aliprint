package com.elivoa.aliprint.entity;

import java.util.Date;
import java.util.List;

import com.beust.jcommander.internal.Lists;
import com.elivoa.aliprint.data.APIResponse;
import com.elivoa.aliprint.data.OrderStatus;

public class AliOrder {

	private Long id; // 订单号
	private String status; // 订单状态。CANCEL交易关闭，SUCCESS交易成功，WAIT_BUYER_PAY等待买家付款，WAIT_SELLER_SEND等待卖家发货，WAIT_BUYER_RECEIVE等待买家确认收货，WAIT_SELLER_ACT分阶段等待卖家操作，WAIT_BUYER_CONFIRM_ACTION分阶段等待买家确认卖家操作，WAIT_SELLER_PUSH分阶段等待卖家推进，WAIT_LOGISTICS_TAKE_IN等待物流公司揽件COD，WAIT_BUYER_SIGN等待买家签收COD，SIGN_IN_SUCCESS买家已签收COD，SIGN_IN_FAILED签收失败COD

	private Date gmtModified; // 修改时间
	private Date gmtCreate; // 创建时间
	private Date gmtPayment; // 支付时间
	private Date gmtGoodsSend; // 发货时间
	private Date gmtConfirmed; // 确认时间

	private Long discount; // 折扣，单位分
	private Long carriage; // 运费，单位分
	private Long sumPayment; // 付款总金额，单位（分）订单需要支付的总金额=产品总金额+运费-折扣金额-抵价券（如果有的话），如果是COD订单，不包括COD服务费

	private Long refundPayment; // 退款金额，单位分
	private String refundStatus; // 退款状态。WAIT_SELLER_AGREE等待卖家同意退款协议，REFUND_SUCCESS退款成功，REFUND_CLOSED退款关闭，WAIT_BUYER_MODIFY等待买家修改，WAIT_BUYER_SEND等待买家退货，WAIT_SELLER_RECEIVE等待卖家确认收货

	private Integer buyerRateStatus; // 买家评价状态。5未评价，4已评价
	private Integer sellerRateStatus; // 卖家评价状态。5未评价，4已评价

	private String toFullName;// ** can't be fetch form new version of API because of privacy.
	private String toMobile;// ** same to above
	private String toPost; // 收货人邮编
	private String toArea; // 收货人地址

	private String alipayTradeId; // 支付宝交易号
	private String sellerCompanyName; // 卖家公司名称
	private String sellerEmail; // 卖家email
	private String buyerCompanyName; // 买家公司名称
	private String buyerEmail; // 买家email
	private Long sumProductPayment; // 总货品金额
	private List stepOrderList; // 子支付单
	private boolean stepPayAll; // 是否一次性付款
	private int payStatus; // 支付状态。1:等待买家付款，2:已付款，4:交易关闭，6:交易成功，8:交易被系统关闭
	private int logisticsStatus; // 物流状态。8:还未创建物流订单；1:未发货；2:已发货；3:已收货，交易成功；4:已经退货，交易失败；5:部分发货，交易成功
	private String stepAgreementPath; // 分阶段协议地址
	private int codStatus; // cod状态。0:初始值，20:接单，-20:不接单，2:接单超时，30:揽收成功，-30:揽收失败，3:揽收超时，100:签收成功，-100:签收失败，10:订单等候发送给物流公司，-1:用户取消物流订单
	private boolean codAudit; // 是否COD订单并且清算成功
	private Long codFee; // cod服务费
	private Long codBuyerFee; // 买家承担的服务费
	private Long codSellerFee; // 卖家承担的服务费
	private Long codActualFee; // cod交易的实付款（买家实际支付给物流的费用）
	private Date gmtSign; // 买家签收时间(COD)
	private String codFeeDividend; // cod三家分润
	private Long codInitFee; // cod服务费初始值
	private Long codBuyerInitFee; // 买家承担的服务费初始值

	private String buyerMemberId; // 买家id
	private String sellerMemberId; // 卖家id
	private Long sellerUserId; // 卖家数字id
	private Long buyerUserId; // 买家数字id
	private String buyerAlipayId; // 买家支付宝id
	private String sellerAlipayId; // 卖家支付宝id
	private String sellerLoginId; // 卖家登录id
	private String buyerLoginId; // 买家登录id

	private String tradeType; // 交易类型。UNIFY统一交易流程，STEPPAY分阶段交易，COD货到付款交易，CERTIFICATE信用凭证支付交易
	private String tradeTypeStr; // 交易类型string。6统一交易流程，7分阶段交易，8货到付款交易，9信用凭证支付交易

	private List<AliOrderEntity> entities; // 订单明细

	private String closeReason; // 关闭原因
	private String buyerFeedback; // 买家留言
	private AliOrderMemo buyerOrderMemo; // 买家备注
	private AliOrderMemo sellerOrderMemo; // 卖家备注
	private AliOrderMemo[] orderMemoList; // 备注列表

	// private LogisticsOrderModel[] logisticsOrderList; // 物流单列表
	// private OrderInvoiceModel orderInvoiceModel; // 发票信息

	private SellAgent sellAgent;

	public AliOrder(APIResponse resp) {

		status = resp.getString("status");
		gmtModified = resp.parseTime("gmtModified");
		gmtCreate = resp.parseTime("gmtCreate");
		refundStatus = resp.getString("refundStatus");
		buyerRateStatus = resp.getInteger("buyerRateStatus");
		sellerRateStatus = resp.getInteger("sellerRateStatus");
		gmtPayment = resp.parseTime("gmtPayment");
		gmtGoodsSend = resp.parseTime("gmtGoodsSend");
		gmtConfirmed = resp.parseTime("gmtConfirmed");
		discount = resp.getLong("discount");
		carriage = resp.getLong("carriage");
		refundPayment = resp.getLong("refundPayment");
		sumPayment = resp.getLong("sumPayment");
		closeReason = resp.getString("closeReason");
		buyerFeedback = resp.getString("buyerFeedback");
		toPost = resp.getString("toPost");
		toArea = resp.getString("toArea");
		alipayTradeId = resp.getString("alipayTradeId");
		sellerCompanyName = resp.getString("sellerCompanyName");
		sellerEmail = resp.getString("sellerEmail");
		buyerCompanyName = resp.getString("buyerCompanyName");
		buyerEmail = resp.getString("buyerEmail");
		sumProductPayment = resp.getLong("sumProductPayment");
		stepPayAll = resp.getBoolean("stepPayAll");
		payStatus = resp.getInt("payStatus");
		logisticsStatus = resp.getInt("logisticsStatus");
		stepAgreementPath = resp.getString("stepAgreementPath");
		codStatus = resp.getInt("codStatus");
		codAudit = resp.getBoolean("codAudit");
		codFee = resp.getLong("codFee");
		codBuyerFee = resp.getLong("codBuyerFee");
		codSellerFee = resp.getLong("codSellerFee");
		codActualFee = resp.getLong("codActualFee");
		gmtSign = resp.parseTime("gmtSign");
		codFeeDividend = resp.getString("codFeeDividend");
		codInitFee = resp.getLong("codInitFee");
		codBuyerInitFee = resp.getLong("codBuyerInitFee");
		id = resp.getLong("id");
		buyerMemberId = resp.getString("buyerMemberId");
		sellerMemberId = resp.getString("sellerMemberId");
		sellerUserId = resp.getLong("sellerUserId");
		buyerUserId = resp.getLong("buyerUserId");
		buyerAlipayId = resp.getString("buyerAlipayId");
		sellerAlipayId = resp.getString("sellerAlipayId");
		sellerLoginId = resp.getString("sellerLoginId");
		buyerLoginId = resp.getString("buyerLoginId");
		tradeType = resp.getString("tradeType");
		tradeTypeStr = resp.getString("tradeTypeStr");

		// List orderEntries = resp.getList("orderEntries");

		List<APIResponse> list = resp.getRespList("orderEntries");
		if (null != list) {
			this.entities = Lists.newArrayList();
			for (APIResponse respEntity : list) {
				AliOrderEntity entity = new AliOrderEntity(respEntity);
				this.entities.add(entity);
			}
		}

		// memos;
		this.buyerOrderMemo = AliOrderMemo.create(resp.getResp("buyerOrderMemo"));
		this.sellerOrderMemo = AliOrderMemo.create(resp.getResp("sellerOrderMemo"));

		List<APIResponse> respList = resp.getRespList("orderMemoList");
		// OrderMemoModel[] orderMemoList =resp.getOrderMemoModel[]("orderMemoList");
		System.out.println("done" + respList);
		// if (this.buyerOrderMemo != null || this.sellerOrderMemo != null || respList != null) {
		// System.out.println("not null");
		// }

		// TODO parse them.

		// List stepOrderList =resp.getList("stepOrderList");
		// LogisticsOrderModel[] logisticsOrderList
		// =resp.getLogisticsOrderModel[]("logisticsOrderList");
		// OrderInvoiceModel orderInvoiceModel =resp.getOrderInvoiceModel("orderInvoiceModel");
	}

	// methods

	public String getOrderDetailsLink() {
		return String.format("http://trade.1688.com/order/unify_seller_detail.htm?orderId=%s", this.id);
	}

	public Double getCarriageDouble() {
		return new Double(this.carriage / 100);
	}

	public Double getSumPaymentDouble() {
		return new Double(this.sumPayment / 100);
	}

	public int getTotalProducts() {
		if (null != this.entities) {
			int total = 0;
			for (AliOrderEntity entity : entities) {
				total += entity.getQuantity();
			}
			return total;
		}
		return 0;
	}

	public String getPartlyDelivery() {
		if (null != this.entities) {
			int delivered = 0;
			for (AliOrderEntity entity : entities) {
				if (entity.getEntryStatus().equalsIgnoreCase(OrderStatus.WAIT_BUYER_RECEIVE.toString())
						|| entity.getEntryStatus().equalsIgnoreCase(OrderStatus.SUCCESS.toString())) {
					delivered++;
				}
			}
			if (delivered == entities.size()) {
				return "已发货";
			} else if (delivered == 0) {
				return "未发货";
			} else {
				return "部分发货";
			}
		}
		return "-";
	}

	// 唐敏，13888335593 ，，云南省 昆明市 五华区 昆明市北市区五华区实力郡城8幢3单元202
	public String getAddressToSplit() {
		String address = this.toArea;
		if (null != address && address.trim().endsWith("，")) {
			address = address.substring(0, address.length() - 1);
		}
		return String.format("%s,%s,,%s", this.toFullName, this.toMobile, address);
	}

	// accessors

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Integer getBuyerRateStatus() {
		return buyerRateStatus;
	}

	public void setBuyerRateStatus(Integer buyerRateStatus) {
		this.buyerRateStatus = buyerRateStatus;
	}

	public Integer getSellerRateStatus() {
		return sellerRateStatus;
	}

	public void setSellerRateStatus(Integer sellerRateStatus) {
		this.sellerRateStatus = sellerRateStatus;
	}

	public Date getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(Date gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public Date getGmtGoodsSend() {
		return gmtGoodsSend;
	}

	public void setGmtGoodsSend(Date gmtGoodsSend) {
		this.gmtGoodsSend = gmtGoodsSend;
	}

	public Date getGmtConfirmed() {
		return gmtConfirmed;
	}

	public void setGmtConfirmed(Date gmtConfirmed) {
		this.gmtConfirmed = gmtConfirmed;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getCarriage() {
		return carriage;
	}

	public void setCarriage(Long carriage) {
		this.carriage = carriage;
	}

	public Long getRefundPayment() {
		return refundPayment;
	}

	public void setRefundPayment(Long refundPayment) {
		this.refundPayment = refundPayment;
	}

	public Long getSumPayment() {
		return sumPayment;
	}

	public void setSumPayment(Long sumPayment) {
		this.sumPayment = sumPayment;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public String getBuyerFeedback() {
		return buyerFeedback;
	}

	public void setBuyerFeedback(String buyerFeedback) {
		this.buyerFeedback = buyerFeedback;
	}

	public String getToPost() {
		return toPost;
	}

	public void setToPost(String toPost) {
		this.toPost = toPost;
	}

	public String getToArea() {
		return toArea;
	}

	public void setToArea(String toArea) {
		this.toArea = toArea;
	}

	public String getAlipayTradeId() {
		return alipayTradeId;
	}

	public void setAlipayTradeId(String alipayTradeId) {
		this.alipayTradeId = alipayTradeId;
	}

	public String getSellerCompanyName() {
		return sellerCompanyName;
	}

	public void setSellerCompanyName(String sellerCompanyName) {
		this.sellerCompanyName = sellerCompanyName;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getBuyerCompanyName() {
		return buyerCompanyName;
	}

	public void setBuyerCompanyName(String buyerCompanyName) {
		this.buyerCompanyName = buyerCompanyName;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public Long getSumProductPayment() {
		return sumProductPayment;
	}

	public void setSumProductPayment(Long sumProductPayment) {
		this.sumProductPayment = sumProductPayment;
	}

	public List getStepOrderList() {
		return stepOrderList;
	}

	public void setStepOrderList(List stepOrderList) {
		this.stepOrderList = stepOrderList;
	}

	public boolean isStepPayAll() {
		return stepPayAll;
	}

	public void setStepPayAll(boolean stepPayAll) {
		this.stepPayAll = stepPayAll;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public int getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(int logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

	public String getStepAgreementPath() {
		return stepAgreementPath;
	}

	public void setStepAgreementPath(String stepAgreementPath) {
		this.stepAgreementPath = stepAgreementPath;
	}

	public int getCodStatus() {
		return codStatus;
	}

	public void setCodStatus(int codStatus) {
		this.codStatus = codStatus;
	}

	public boolean isCodAudit() {
		return codAudit;
	}

	public void setCodAudit(boolean codAudit) {
		this.codAudit = codAudit;
	}

	public Long getCodFee() {
		return codFee;
	}

	public void setCodFee(Long codFee) {
		this.codFee = codFee;
	}

	public Long getCodBuyerFee() {
		return codBuyerFee;
	}

	public void setCodBuyerFee(Long codBuyerFee) {
		this.codBuyerFee = codBuyerFee;
	}

	public Long getCodSellerFee() {
		return codSellerFee;
	}

	public void setCodSellerFee(Long codSellerFee) {
		this.codSellerFee = codSellerFee;
	}

	public Long getCodActualFee() {
		return codActualFee;
	}

	public void setCodActualFee(Long codActualFee) {
		this.codActualFee = codActualFee;
	}

	public Date getGmtSign() {
		return gmtSign;
	}

	public void setGmtSign(Date gmtSign) {
		this.gmtSign = gmtSign;
	}

	public String getCodFeeDividend() {
		return codFeeDividend;
	}

	public void setCodFeeDividend(String codFeeDividend) {
		this.codFeeDividend = codFeeDividend;
	}

	public Long getCodInitFee() {
		return codInitFee;
	}

	public void setCodInitFee(Long codInitFee) {
		this.codInitFee = codInitFee;
	}

	public Long getCodBuyerInitFee() {
		return codBuyerInitFee;
	}

	public void setCodBuyerInitFee(Long codBuyerInitFee) {
		this.codBuyerInitFee = codBuyerInitFee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBuyerMemberId() {
		return buyerMemberId;
	}

	public void setBuyerMemberId(String buyerMemberId) {
		this.buyerMemberId = buyerMemberId;
	}

	public String getSellerMemberId() {
		return sellerMemberId;
	}

	public void setSellerMemberId(String sellerMemberId) {
		this.sellerMemberId = sellerMemberId;
	}

	public Long getSellerUserId() {
		return sellerUserId;
	}

	public void setSellerUserId(Long sellerUserId) {
		this.sellerUserId = sellerUserId;
	}

	public Long getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(Long buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public String getBuyerAlipayId() {
		return buyerAlipayId;
	}

	public void setBuyerAlipayId(String buyerAlipayId) {
		this.buyerAlipayId = buyerAlipayId;
	}

	public String getSellerAlipayId() {
		return sellerAlipayId;
	}

	public void setSellerAlipayId(String sellerAlipayId) {
		this.sellerAlipayId = sellerAlipayId;
	}

	public String getSellerLoginId() {
		return sellerLoginId;
	}

	public void setSellerLoginId(String sellerLoginId) {
		this.sellerLoginId = sellerLoginId;
	}

	public String getBuyerLoginId() {
		return buyerLoginId;
	}

	public void setBuyerLoginId(String buyerLoginId) {
		this.buyerLoginId = buyerLoginId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeTypeStr() {
		return tradeTypeStr;
	}

	public void setTradeTypeStr(String tradeTypeStr) {
		this.tradeTypeStr = tradeTypeStr;
	}

	public List<AliOrderEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<AliOrderEntity> entities) {
		this.entities = entities;
	}

	public AliOrderMemo getBuyerOrderMemo() {
		return buyerOrderMemo;
	}

	public void setBuyerOrderMemo(AliOrderMemo buyerOrderMemo) {
		this.buyerOrderMemo = buyerOrderMemo;
	}

	public AliOrderMemo getSellerOrderMemo() {
		return sellerOrderMemo;
	}

	public void setSellerOrderMemo(AliOrderMemo sellerOrderMemo) {
		this.sellerOrderMemo = sellerOrderMemo;
	}

	public AliOrderMemo[] getOrderMemoList() {
		return orderMemoList;
	}

	public void setOrderMemoList(AliOrderMemo[] orderMemoList) {
		this.orderMemoList = orderMemoList;
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

	public SellAgent getSellAgent() {
		return sellAgent;
	}

	public void setSellAgent(SellAgent sellAgent) {
		this.sellAgent = sellAgent;
	}

}
