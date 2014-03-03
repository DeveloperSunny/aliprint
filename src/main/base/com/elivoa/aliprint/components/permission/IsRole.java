package com.elivoa.aliprint.components.permission;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.alibaba.aliprint.services.auth.Authenticator;

/**
 * IsRole
 * 
 * Checks if the user is Role.
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Jan 5, 2012]
 * @version 1.0
 * 
 */
public class IsRole extends AbstractConditional {

	@Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
	private String roles;

	@Override
	protected boolean test() {
		if (null != roles) {
			String[] split = roles.split(",");
			for (String role : split) {
				authenticator.isRole(role);
				return true;
			}
			return false;
		}
		return authenticator.isLoggedIn();
	}

	/*
	 * Services
	 */
	@Inject
	private Authenticator authenticator;

}
