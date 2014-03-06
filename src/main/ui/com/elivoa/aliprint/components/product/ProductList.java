package com.elivoa.aliprint.components.product;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.entity.AliProduct;
import com.elivoa.aliprint.entity.AliOldResult;
import com.elivoa.aliprint.services.AuthService;

public class ProductList {

	@Property
	Integer sss;

	@Property
	AliOldResult<AliProduct> products;

	@Property
	AliProduct _product;

	@Parameter
	int start;

	@Parameter
	int itemsPerPage;

	void setupRender() {
		if (itemsPerPage <= 0) {
			itemsPerPage = 20;
		}
		int page = (start / itemsPerPage) + 1;
		this.products = sdk.listProducts(token, itemsPerPage, page, null);
	}

	@Inject
	AuthService sdk;

	@SessionState
	AliToken token;

	public int getTotal() {
		if (null != this.products) {
			return this.products.getTotal();
		}
		return 0;
	}
}
