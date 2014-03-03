package com.elivoa.aliprint.filter;

import javax.servlet.ServletException;

import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.Registry;

import com.elivoa.aliprint.bridge.T5RegistryHelper;

/**
 * TapestryIntegrationFilter - Provide access out of tapestry framework.
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Jun 28, 2011]
 */
public class TapestryIntegrationFilter extends TapestryFilter {

	@Override
	protected void init(Registry registry) throws ServletException {
		super.init(registry);

		T5RegistryHelper.setRegistry(registry);
	}

}
