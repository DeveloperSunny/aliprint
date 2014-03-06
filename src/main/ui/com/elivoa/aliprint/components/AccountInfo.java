package com.elivoa.aliprint.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.services.AuthService;

public class AccountInfo {

	// acount should be aliid.
	@Parameter
	String account;

	@SessionState(create = true)
	AliToken token;

	@Property
	String accountInfo;

	void setupRender() {
		// change id
		// sdk.changeToMemberId(token, account);

		// use memeber_id to get account info.
		Object result = sdk.getAccountInfo(token, String.format("b2b-%s", account));
		this.accountInfo = result.toString();
	}

	// services

	@Inject
	AuthService sdk;
}
