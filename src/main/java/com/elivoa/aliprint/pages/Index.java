package com.elivoa.aliprint.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Response;

import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.exceptions.NeedAuthenticationException;
import com.elivoa.aliprint.services.AuthService;

/**
 * Start page of application aliprint.
 */
public class Index {

	private static String auth_redirect_url = "http://gw.open.1688.com/auth/authorize.htm?client_id=1010132&site=china&redirect_uri=http://localhost:8080/aliprint/authorization&_aop_signature=14C23331781F7594FB5FA10C32CE8AE4DD13FB4D";

	@Property(write = false)
	@SessionState(create = true)
	AliToken token;

	Object onActivate() throws MalformedURLException {

		// authenticate process.
		try { // TODO exception not needed.
			boolean pass = aliservice.authenticate(this.token);
			if (!pass) {
				// if (!this.token.isAccessTokenAvailable()) {
				return new URL(auth_redirect_url);
			}
			// response.setStatus(302);
			// response.setHeader("Location", auth_redirect_url);
			// return null;
		} catch (NeedAuthenticationException e) {
			e.printStackTrace();
			return new URL(auth_redirect_url);
		}
		// authentication first
		return null;
	}

	Object setupRender() {
		// URL url = null;
		// try {
		// url = new URL("http://www.google.com");
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// }
		// return url;
		return true;
	}

	Object onActionFromAbc() {
		URL url = null;
		try {
			url = new URL("http://www.google.com");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;

	}

	public Date getCurrentTime() {
		return new Date();
	}

	public Long getAliid() {
		return this.token.getAliid();
	}

	// services

	@Inject
	private AuthService aliservice;

	@Inject
	Response response;

	// symbols

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

}
