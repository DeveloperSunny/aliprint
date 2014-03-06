package com.elivoa.aliprint.pages;

import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.services.AuthService;

public class Authorization {

	@Property
	@ActivationRequestParameter
	String code;

	@SessionState(create = true)
	AliToken token;

	@InjectPage
	Index index_page;

	Object onActivate() {
		System.out.printf("Authorization using code: %s...................\n", code);
		authService.authorize(token, code);
		return index_page;
	}

	@Inject
	AuthService authService;
}
