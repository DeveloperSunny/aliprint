package com.elivoa.aliprint.components.product;

import java.sql.SQLException;
import java.util.List;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.dal.ProductDao;
import com.elivoa.aliprint.entity.AliOldResult;
import com.elivoa.aliprint.entity.AliProduct;
import com.elivoa.aliprint.entity.ProductAlias;
import com.elivoa.aliprint.services.AuthService;

@Import(library = "context:js/ProductList.js")
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

	void setupRender() throws SQLException {
		this.itemsPerPage = itemsPerPage <= 0 ? defaultItemsPerPage : itemsPerPage;
		int page = (start / itemsPerPage) + 1;

		this.products = sdk.listProducts(token, itemsPerPage, page, null);

		// product alias from database;
		List<ProductAlias> aliasList = productDao.listProducts();
		for (AliProduct product : this.products.getReturns()) {
			for (ProductAlias alias : aliasList) {
				if (product.getOfferId() == alias.getId()) {
					product.setAlias(alias.getAlias());
				}
			}
		}

	}

	void afterRender() {
		jsSupport.addInitializerCall("ProductList", "");
	}

	public int getTotal() {
		if (null != this.products) {
			return this.products.getTotal();
		}
		return 0;
	}

	@Inject
	JavaScriptSupport jsSupport;

	@Inject
	AuthService sdk;

	@Inject
	ProductDao productDao;

	@SessionState
	AliToken token;

	@Inject
	@Symbol("pagesize.product.alias")
	int defaultItemsPerPage;
}
