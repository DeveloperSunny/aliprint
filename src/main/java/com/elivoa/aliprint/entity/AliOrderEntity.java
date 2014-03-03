package com.elivoa.aliprint.entity;

import java.util.List;
import java.util.Map;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.elivoa.aliprint.data.APIResponse;

public class AliOrderEntity {

	// private id=547455850382940, // this is equals to order's, so ignore;
	private int sourceId;// =1291679697;
	private String specId;// =e4128fe39c985061f6ba876756ff4138;

	private String productName;// =现货！！8月29日呛口小辣椒秀高贵紫色杂毛衣粗针开衫外套,
	private int price;
	private int entryDiscount = 0;
	private int quantity;// =1.0;

	private String entryStatus;// =waitbuyØerpay;
	private int entryCodStatus = 0;
	private List<String> productPic;// =[http://img.china.alibaba.com:80//img/order/trading/049/283/058554745/980311532_1891461501.310x310.jpg],

	/*
	 * specInfo=[{specValue=紫色, specName=颜色}, {specValue=S, specName=尺码}],
	 */
	private Map<String, String> specInfo;

	public AliOrderEntity(APIResponse resp) {
		this.sourceId = resp.getInt("sourceId");
		this.specId = resp.getString("specId");// =e4128fe39c985061f6ba876756ff4138;

		this.productName = resp.getString("productName");
		this.price = resp.getInt("price");
		this.entryDiscount = resp.getInt("entryDiscount");
		this.quantity = resp.getInt("quantity");

		this.entryStatus = resp.getString("entryStatus");
		this.entryCodStatus = resp.getInt("entryCodStatus");

		//
		// private String[] productPic;//
		// =[http://img.china.alibaba.com:80//img/order/trading/049/283/058554745/980311532_1891461501.310x310.jpg],

		// order entities;
		List<String> list = resp.getStringList("productPic");
		this.productPic = Lists.newArrayList();
		if (null != list) {
			for (String item : list) {
				this.productPic.add(item);
			}
		}

		// order entity specInfo
		List<APIResponse> specRespList = resp.getRespList("specInfo");
		this.specInfo = Maps.newHashMap();
		if (null != specRespList) {
			for (APIResponse rr : specRespList) {
				this.specInfo.put(rr.getString("specName"), rr.getString("specValue"));
			}
		}

	}

	public int getSourceId() {
		return sourceId;
	}

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

	public int getQuantity() {
		return quantity;
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

	public int getEntryCodStatus() {
		return entryCodStatus;
	}

	public void setEntryCodStatus(int entryCodStatus) {
		this.entryCodStatus = entryCodStatus;
	}

	public List<String> getProductPic() {
		return productPic;
	}

	public void setProductPic(List<String> productPic) {
		this.productPic = productPic;
	}

	public Map<String, String> getSpecInfo() {
		return specInfo;
	}

	public void setSpecInfo(Map<String, String> specInfo) {
		this.specInfo = specInfo;
	}

}
