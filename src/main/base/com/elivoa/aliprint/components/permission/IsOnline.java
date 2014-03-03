package com.elivoa.aliprint.components.permission;

import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

/**
 * Return true if Running on Server,
 * 
 * @TODO simple version, check if port is 80.
 */
public class IsOnline extends AbstractConditional {

	@Override
	protected boolean test() {
		if (request.getServerPort() == 80) {
			// TODO apply to all domains
			// String request.getServerName().String().endsWith("aminer.org")
			return true;
		}
		return false;
	}

	/*
	 * Services
	 */
	@Inject
	private Request request;
}
