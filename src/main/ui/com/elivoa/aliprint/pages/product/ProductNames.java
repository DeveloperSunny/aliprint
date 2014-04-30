package com.elivoa.aliprint.pages.product;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Response;

import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.components.product.ProductList;
import com.elivoa.aliprint.exceptions.NeedAuthenticationException;
import com.elivoa.aliprint.services.AuthService;

/**
 * Start page of application aliprint.
 */
public class ProductNames {

	@Property(write = false)
	@SessionState(create = true)
	AliToken token;

	@Component
	ProductList productList;

	@Property
	int start;

	@Property
	int itemsPerPage;

	Object onActivate(int start, int itemsPerPage) throws MalformedURLException {
		this.start = start;
		this.itemsPerPage = itemsPerPage <= 0 ? defaultItemsPerPage : itemsPerPage;

		// authenticate process.
		try {
			boolean pass = aliservice.authenticate(this.token);
			if (!pass) {
				return new URL(auth_url);
			}
		} catch (NeedAuthenticationException e) {
			e.printStackTrace();
			return new URL(auth_url);
		}
		// authentication first
		return null;
	}

	Object setupRender() {
		// aliservice.listProducts(token, 20, 1, null);
		return true;
	}

	public Long getAliid() {
		return this.token.getAliid();
	}

	@Cached
	public String getPagerTemplate() {
		return pageRenderLinkSource.createPageRenderLinkWithContext(ProductNames.class, "__start__",
				"__itemsPerPage__").toURI();
	}

	public int getPagerTotal() {
		if (null != productList) {
			return productList.getTotal();
		}
		return 0;
	}

	// services

	@Inject
	private AuthService aliservice;

	@Inject
	Response response;

	@Inject
	PageRenderLinkSource pageRenderLinkSource;

	// symbols
	@Inject
	@Symbol("com.elivoa.aliprint.authurl")
	String auth_url;

	@Property(write = false)
	@Inject
	@Symbol("com.elivoa.aliprint.appkey")
	String appkey;

	@Property(write = false)
	@Inject
	@Symbol("com.elivoa.aliprint.securitykey")
	String securitykey;

	@Property
	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	private String tapestryVersion;

	@Inject
	@Symbol("pagesize.product.alias")
	int defaultItemsPerPage;
}
