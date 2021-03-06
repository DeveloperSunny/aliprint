package com.elivoa.aliprint.components.permission;

import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.alibaba.aliprint.services.auth.Authenticator;

/**
 * IsAdmin
 * 
 * Checks if the user is Administrator
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Jan 5, 2012]
 * @version 1.0
 */
public class IsAdmin extends AbstractConditional {

	@Inject
	private Authenticator authenticator;

	@Override
	protected boolean test() {
		return authenticator.isAdmin();
	}

}
