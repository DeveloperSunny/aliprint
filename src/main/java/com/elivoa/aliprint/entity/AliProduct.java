package com.elivoa.aliprint.entity;

import java.util.List;

import com.elivoa.aliprint.data.APIResponse;

/**
 * <code>
 {imageList=[
   {size310x310URL=  http://img.china.alibaba.com/img/ibank/2013/853/714/1092417358_1891461501.310x310.jpg,
    summURL=http://img.china.alibaba.com/img/ibank/2013/853/714/1092417358_1891461501.summ.jpg,
    size64x64URL=http://img.china.alibaba.com/img/ibank/2013/853/714/1092417358_1891461501.64x64.jpg,
    imageURI=img/ibank/2013/853/714/1092417358_1891461501.jpg,
    originalImageURI=http://img.china.alibaba.com/img/ibank/2013/853/714/1092417358_1891461501.jpg
   }, {...}]
 * </code>
 * 
 */
public class AliProduct {

	// common
	private long offerId;// =36198853010
	private int qualityLevel;// =5,
	private String subject;// =呛口小辣椒同款12月24日圣诞秀经典格纹羊毛呢英伦大衣外套,
	private String offerStatus;// =outdated,
	private double productUnitWeight;// =0.6,
	private String thumbnail;
	private int unitPrice;
	private String detailsUrl;

	// additional fields;
	private String alias;

	public AliProduct(APIResponse resp) {

		this.offerId = resp.getLong("offerId");
		this.qualityLevel = resp.getInt("qualityLevel");
		this.subject = resp.getString("subject");
		this.offerStatus = resp.getString("offerStatus");
		this.productUnitWeight = resp.parseDouble("productUnitWeight");
		this.unitPrice = resp.getInt("unitPrice");
		this.detailsUrl = resp.getString("detailsUrl");
		// image pictures; not all of them.
		List<APIResponse> list = resp.getRespList("imageList");
		if (null != list) {
			for (APIResponse item : list) {
				this.thumbnail = item.getString("size310x310URL");
				break;// TODO not load all of the pictures;
			}
		}
	}

	public Double getUnitPriceDouble() {
		return new Double(this.unitPrice) / 100;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}

	public double getProductUnitWeight() {
		return productUnitWeight;
	}

	public void setProductUnitWeight(double productUnitWeight) {
		this.productUnitWeight = productUnitWeight;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public int getQualityLevel() {
		return qualityLevel;
	}

	public void setQualityLevel(int qualityLevel) {
		this.qualityLevel = qualityLevel;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDetailsUrl() {
		return detailsUrl;
	}

	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}

}
