package com.elivoa.aliprint.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.elivoa.aliprint.data.APIResponse;
import com.google.common.collect.Maps;

/**
 * <code>
 * 
 * orderEntries=[
  {unitPrice=14800,
   snapshotImages=[{imageUrl=jpg, midImageUrl=jpg}],
   amount=14800,
   entryPayStatus=1,
   entryStatusStr=waitbuyerpay,
   quantity=1.0,
   mainMidImageUrl=http50x150.jpg,
   codStatus=0,
   entryStatus=WAIT_BUYER_PAY,
   categoryId=321,
   buyerSecuritySupport=true,
   discountPrice=14800,
   productPics=[ht],
   discount=1.0,
   price=14800,
   industrySecurityCodes=D,
   gmtModified=20140305195458000+0800,
   snapshotId=f:557332666333854_1,
   orderFrom=tb,
   mainSummImageUrl=htpg,
   actualPayFee=14800,
   productPic=/458/333/666233755/1151669818_1891461501.jpg,
   logisticsOrderId=-1,
   orderSourceType=common,
   sellerRateStatus=5,
   sourceId=37075968209,
   entryDiscount=0,
   id=557332666333854,
   tbId=557332666333854,
   logisticsStatus=1,
   fromOffer=true,
   promotionsFee=0,
   orderId=557332666333854,
   currencyCode=CNY,
   gmtCreate=20140305195456000+0800,
   buyerRateStatus=5,
   unit=件,
   specId=94d1d179497744028aa76873afdeba62,
   productName=2014年dazzle地素新款水溶蕾丝雪纺拼接绣花圆领套头羊毛毛衣,
   guaranteeSupport=false

   specInfoModel={specItems=[{specValue=薰衣草色, specName=颜色}, {specValue=S, specName=尺码}]}

   complaintStatus={
     valid=true,
     refundComplaintDoing=false,
     afterSalesComplaintDoing=false
   },
   bizSign={
     sign=0,
     salePromotion=false,
     yiFenSample=false,
     commonSample=false,
     binaryString=0,
     freePostage=false,
     mix=false,
     virtualGoods=false
   },
   
	
  }

 * </code>
 * 
 */
public class AliOrderEntity {

	private long id;// =557332666333854,
	private long tbId;// =557332666333854,
	private long orderId;// =557332666333854,
	private String specId;// =94d1d179497744028aa76873afdeba62,
	private String productName;// =2014年dazzle地素新款水溶蕾丝雪纺拼接绣花圆领套头羊毛毛衣, guaranteeSupport;// =false
	private String entryStatus;// =WAIT_BUYER_PAY,
	private String entryStatusStr;// =waitbuyerpay,

	private int unitPrice;// ;// =14800,
	private double discount;// =1.0,
	private int discountPrice;// =14800,
	private int price;// =14800,

	private double quantity;// =1.0,
	private String unit;// =件,
	private int amount;// =14800, ???
	private String mainMidImageUrl;// =http50x150.jpg,
	private String mainSummImageUrl;// =htpg,
	private String productPic;// =/458/333/666233755/1151669818_1891461501.jpg,

	private Timestamp gmtModified;// =20140305195458000+0800,
	private Timestamp gmtCreate;// =20140305195456000+0800,

	private int entryPayStatus;// =1,
	private int codStatus;// =0,

	private int categoryId;// =321,
	private boolean buyerSecuritySupport;// =true,
	private String industrySecurityCodes;// =D,
	private String snapshotId;// =f:557332666333854_1,
	private String orderFrom;// =tb,
	private int actualPayFee;// =14800,
	private int logisticsOrderId;// =-1,
	private String orderSourceType;// =common,
	private int sellerRateStatus;// =5,
	private long sourceId;// =37075968209,
	private int entryDiscount;// =0,
	private int logisticsStatus;// =1,
	private boolean fromOffer;// =true,
	private int promotionsFee;// =0,
	private String currencyCode;// =CNY,
	private int buyerRateStatus;// =5,

	// extra values;
	private String alias;

	// complaintStatus;// ={
	// valid=true,
	// refundComplaintDoing=false,
	// afterSalesComplaintDoing=false
	// },
	// bizSign={
	// sign=0,
	// salePromotion=false,
	// yiFenSample=false,
	// commonSample=false,
	// binaryString=0,
	// freePostage=false,
	// mix=false,
	// virtualGoods=false
	// },
	//
	// snapshotImages=[{imageUrl=jpg, midImageUrl=jpg}];
	// productPics;// =[ht],
	// ----------------------------------------------------

	// private id=547455850382940, // this is equals to order's, so ignore;
	// private int sourceId;// =1291679697;
	// private String specId;// =e4128fe39c985061f6ba876756ff4138;
	//
	// private String productName;// =现货！！8月29日呛口小辣椒秀高贵紫色杂毛衣粗针开衫外套,
	// private int price;
	// private int entryDiscount = 0;
	// private int quantity;// =1.0;
	//
	// private String entryStatus;// =waitbuyØerpay;
	// private int entryCodStatus = 0;
	// private List<String> productPic;//
	// =[http://img.china.alibaba.com:80//img/order/trading/049/283/058554745/980311532_1891461501.310x310.jpg],

	/*
	 * specInfo=[{specValue=紫色, specName=颜色}, {specValue=S, specName=尺码}],
	 */
	private Map<String, String> specInfo;

	public AliOrderEntity(APIResponse resp) {
		this.id = resp.getLong("id");
		this.tbId = resp.getLong("tbId");
		this.orderId = resp.getLong("orderId");
		this.specId = resp.getString("specId");
		this.productName = resp.getString("productName");
		this.entryStatus = resp.getString("entryStatus");
		this.unitPrice = resp.getInt("unitPrice");
		this.discount = resp.getDouble("discount");
		this.discountPrice = resp.getInt("discountPrice");
		this.price = resp.getInt("price");
		this.quantity = resp.getDouble("quantity");
		this.unit = resp.getString("unit");
		this.amount = resp.getInt("amount");
		this.mainMidImageUrl = resp.getString("mainMidImageUrl");
		this.mainSummImageUrl = resp.getString("mainSummImageUrl");
		this.productPic = resp.getString("productPic");
		this.gmtModified = resp.parseTime("gmtModified");
		this.gmtCreate = resp.parseTime("gmtCreate");

		this.entryPayStatus = resp.getInt("entryPayStatus");
		this.entryStatusStr = resp.getString("entryStatusStr");
		this.codStatus = resp.getInt("codStatus");
		this.categoryId = resp.getInt("categoryId");
		this.buyerSecuritySupport = resp.getBoolean("buyerSecuritySupport");
		this.industrySecurityCodes = resp.getString("industrySecurityCodes");
		this.snapshotId = resp.getString("snapshotId");
		this.orderFrom = resp.getString("orderFrom");
		this.actualPayFee = resp.getInt("actualPayFee");
		this.logisticsOrderId = resp.getInt("logisticsOrderId");
		this.orderSourceType = resp.getString("orderSourceType");
		this.sellerRateStatus = resp.getInt("sellerRateStatus");
		this.sourceId = resp.getLong("sourceId");
		this.entryDiscount = resp.getInt("entryDiscount");
		this.logisticsStatus = resp.getInt("logisticsStatus");
		this.fromOffer = resp.getBoolean("fromOffer");
		this.promotionsFee = resp.getInt("promotionsFee");
		this.currencyCode = resp.getString("currencyCode");
		this.buyerRateStatus = resp.getInt("buyerRateStatus");

		// specInfoModel={specItems=[{specValue=薰衣草色, specName=颜色}, {specValue=S, specName=尺码}]}

		//
		// private String[] productPic;//
		// =[http://img.china.alibaba.com:80//img/order/trading/049/283/058554745/980311532_1891461501.310x310.jpg],

		// order entities;
		// List<String> list = resp.getStringList("productPic");
		// this.productPic = Lists.newArrayList();
		// if (null != list) {
		// for (String item : list) {
		// this.productPic.add(item);
		// }
		// }
		//

		// order entity specInfo
		APIResponse simResp = resp.getResp("specInfoModel");
		if (null != simResp) {
			List<APIResponse> specRespList = simResp.getRespList("specItems");
			this.specInfo = Maps.newHashMap();
			if (null != specRespList) {
				for (APIResponse rr : specRespList) {
					this.specInfo.put(rr.getString("specName"), rr.getString("specValue"));
				}
			}
		}

	}

	// methods
	public String getShortName() {
		if (null != this.alias && this.alias.trim().length() > 0) {
			return this.alias;
		} else {
			return this.productName;
		}
	}

	// accessors

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getEntryDiscount() {
		return entryDiscount;
	}

	public void setEntryDiscount(int entryDiscount) {
		this.entryDiscount = entryDiscount;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getEntryStatus() {
		return entryStatus;
	}

	public void setEntryStatus(String entryStatus) {
		this.entryStatus = entryStatus;
	}

	public Map<String, String> getSpecInfo() {
		return specInfo;
	}

	public void setSpecInfo(Map<String, String> specInfo) {
		this.specInfo = specInfo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTbId() {
		return tbId;
	}

	public void setTbId(long tbId) {
		this.tbId = tbId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getMainMidImageUrl() {
		return mainMidImageUrl;
	}

	public void setMainMidImageUrl(String mainMidImageUrl) {
		this.mainMidImageUrl = mainMidImageUrl;
	}

	public String getMainSummImageUrl() {
		return mainSummImageUrl;
	}

	public void setMainSummImageUrl(String mainSummImageUrl) {
		this.mainSummImageUrl = mainSummImageUrl;
	}

	public String getProductPic() {
		return productPic;
	}

	public void setProductPic(String productPic) {
		this.productPic = productPic;
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

	public int getEntryPayStatus() {
		return entryPayStatus;
	}

	public void setEntryPayStatus(int entryPayStatus) {
		this.entryPayStatus = entryPayStatus;
	}

	public String getEntryStatusStr() {
		return entryStatusStr;
	}

	public void setEntryStatusStr(String entryStatusStr) {
		this.entryStatusStr = entryStatusStr;
	}

	public int getCodStatus() {
		return codStatus;
	}

	public void setCodStatus(int codStatus) {
		this.codStatus = codStatus;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public boolean isBuyerSecuritySupport() {
		return buyerSecuritySupport;
	}

	public void setBuyerSecuritySupport(boolean buyerSecuritySupport) {
		this.buyerSecuritySupport = buyerSecuritySupport;
	}

	public String getIndustrySecurityCodes() {
		return industrySecurityCodes;
	}

	public void setIndustrySecurityCodes(String industrySecurityCodes) {
		this.industrySecurityCodes = industrySecurityCodes;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public int getActualPayFee() {
		return actualPayFee;
	}

	public void setActualPayFee(int actualPayFee) {
		this.actualPayFee = actualPayFee;
	}

	public int getLogisticsOrderId() {
		return logisticsOrderId;
	}

	public void setLogisticsOrderId(int logisticsOrderId) {
		this.logisticsOrderId = logisticsOrderId;
	}

	public String getOrderSourceType() {
		return orderSourceType;
	}

	public void setOrderSourceType(String orderSourceType) {
		this.orderSourceType = orderSourceType;
	}

	public int getSellerRateStatus() {
		return sellerRateStatus;
	}

	public void setSellerRateStatus(int sellerRateStatus) {
		this.sellerRateStatus = sellerRateStatus;
	}

	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	public int getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(int logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

	public boolean isFromOffer() {
		return fromOffer;
	}

	public void setFromOffer(boolean fromOffer) {
		this.fromOffer = fromOffer;
	}

	public int getPromotionsFee() {
		return promotionsFee;
	}

	public void setPromotionsFee(int promotionsFee) {
		this.promotionsFee = promotionsFee;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getBuyerRateStatus() {
		return buyerRateStatus;
	}

	public void setBuyerRateStatus(int buyerRateStatus) {
		this.buyerRateStatus = buyerRateStatus;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
