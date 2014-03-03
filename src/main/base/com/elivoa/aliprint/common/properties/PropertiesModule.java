package com.elivoa.aliprint.common.properties;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.slf4j.Logger;

import com.elivoa.aliprint.services.AliprintModule;

public class PropertiesModule {

	/*
	 * inline services
	 */
	public PropertiesFileSymbolProvider buildClasspathPropertiesFileSymbolProvider(Logger logger) {
		return new PropertiesFileSymbolProvider(logger, "resource.properties", true);
	}

	public PropertiesFileSymbolProvider buildBasicOverrideSymbolProvider(Logger logger) {
		return new PropertiesFileSymbolProvider(logger,
				String.format("resource_%s.properties", AliprintModule.deploy), true);
	}

//	public PropertiesFileSymbolProvider buildAssembleSymbolProvider(Logger logger) {
//		return new PropertiesFileSymbolProvider(logger, "assemble.properties", true);
//	}
//
//	public PropertiesFileSymbolProvider buildAssembleOverrideSymbolProvider(Logger logger) {
//		return new PropertiesFileSymbolProvider(logger,
//				String.format("assemble_%s.properties", AliprintModule.deploy), true);
//	}

	/*
	 * Add to contribute
	 */
	public static void contributeSymbolSource(
			//
			OrderedConfiguration<SymbolProvider> configuration, //
			@InjectService("ClasspathPropertiesFileSymbolProvider") SymbolProvider basicSP,
			@InjectService("BasicOverrideSymbolProvider") SymbolProvider basicOverrideSP
//			@InjectService("AssembleSymbolProvider") SymbolProvider assembleSP,
//			@InjectService("AssembleOverrideSymbolProvider") SymbolProvider assembleOverrideSP

	) {
		// basic configs
		configuration.add("basicProperties", basicSP, "after:SystemProperties", "before:ApplicationDefaults");
		configuration.add("basicOverrideProperties", basicOverrideSP, "before:basicProperties");

		// Page assimble configs.
//		configuration.add("assembleProperties", assembleSP, "after:basicOverrideProperties");
//		configuration.add("assembleOverrideProperties", assembleOverrideSP, "before:assembleProperties");
	}

	public static void bind(ServiceBinder binder) {
		// binder.bind(Resource.class).scope(ScopeConstants.DEFAULT);
	}

	public PropertiesFileSymbolProvider buildFilesystemPropertiesFileSymbolProvider(Logger logger) {
		return new PropertiesFileSymbolProvider(logger, "src/main/webapp/WEB-INF/test2.properties", false);
	}

}
