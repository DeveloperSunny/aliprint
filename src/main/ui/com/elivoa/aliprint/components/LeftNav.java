package com.elivoa.aliprint.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.services.AuthService;

public class LeftNav {

	@SessionState(create = true)
	AliToken token;

	@Property
	private String pageName;

	void setupRender() {

	}

	public String getLoginId() {
		return token.getLoginId();
	}

	public String[] getPageNames() {
		return new String[] { "Index", "About", "Contact" };
	}

	public String getClassForPageName() {
		return resources.getPageName().equalsIgnoreCase(pageName) ? "current_page_item" : null;
	}

	@Inject
	AuthService aliservice;

	@Inject
	private ComponentResources resources;

}
