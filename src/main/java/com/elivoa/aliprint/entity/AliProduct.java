package com.elivoa.aliprint.entity;

import com.elivoa.aliprint.data.APIResponse;

public class AliProduct {

	// common
	private long offerId;// =36198853010
	private int qualityLevel;// =5,
	private String subject;// =呛口小辣椒同款12月24日圣诞秀经典格纹羊毛呢英伦大衣外套,
	private String offerStatus;// =outdated,
	private double productUnitWeight;// =0.6,

	// additional fields;
	private String alias;

	public AliProduct(APIResponse resp) {

		this.offerId = resp.getLong("offerId");
		this.qualityLevel = resp.getInt("qualityLevel");
		this.subject = resp.getString("subject");
		this.offerStatus = resp.getString("offerStatus");
		this.productUnitWeight = resp.parseDouble("productUnitWeight");

		// // order entities;
		// List<APIResponse> list = resp.getRespList("orderEntries");
		// if (null != list) {
		// this.orderEntities = Lists.newArrayList();
		// for (APIResponse respEntity : list) {
		// AliOrderEntity entity = new AliOrderEntity(respEntity);
		// this.orderEntities.add(entity);
		// }
		// }
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

}
