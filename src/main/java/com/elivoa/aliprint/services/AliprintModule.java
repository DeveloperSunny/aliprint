package com.elivoa.aliprint.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.SubModule;

import com.alibaba.openapi.client.policy.ClientPolicy;
import com.elivoa.aliprint.dal.ProductDao;
import com.elivoa.aliprint.dal.TokenDao;
import com.elivoa.aliprint.module.BaseModule;
import com.elivoa.aliprint.module.InfrastractureModule;
import com.elivoa.aliprint.services.impl.AuthServiceImpl;

@SubModule({

// base module
		BaseModule.class, InfrastractureModule.class

})
public class AliprintModule {

	public static String deploy = "local";

	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {

		configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0");

		/** production mode */
		if ("production".equals(deploy)) {
			configuration.add(SymbolConstants.PRODUCTION_MODE, "true");
			configuration.add(SymbolConstants.COMBINE_SCRIPTS, "true");
			configuration.add(SymbolConstants.COMPACT_JSON, "true");
			configuration.add(SymbolConstants.MINIFICATION_ENABLED, "true");
			configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "true");
			configuration.add("tapestry.file-check-interval", "10 m");
			// configuration.add(SymbolConstants.HOSTNAME, "arnetminer.org");
		}

		/** debugging mode */
		if ("local".equals(deploy)) {
			configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
			configuration.add(SymbolConstants.COMBINE_SCRIPTS, "false");
			configuration.add(SymbolConstants.COMPACT_JSON, "false");
			configuration.add(SymbolConstants.MINIFICATION_ENABLED, "false");
			configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
		}

		configuration.add(SymbolConstants.OMIT_GENERATOR_META, "true");
		configuration.add("tapestry.hmac-passphrase", "elivoa");

		// parameters
		configuration.add("com.elivoa.aliprint.appkey", "1010132");
		configuration.add("com.elivoa.aliprint.securitykey", "SGUd5dc6Dj2f");

		configuration.add("pagesize.product.alias", "10");

		// configuration.add(SymbolConstants.GZIP_COMPRESSION_ENABLED, "false");

		//

		// Contributions to ApplicationDefaults will override any contributions
		// to FactoryDefaults (with the same key). Here we're restricting the
		// supported locales to just "en" (English). As you add localised
		// message catalogs and other assets, you can extend this list of
		// locales (it's a comma separated series of locale names; the first
		// locale name is the default when there's no reasonable match).
		// configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en_US");

		// The factory default is true but during the early stages of an
		// application overriding to false is a good idea. In addition, this is
		// often overridden on the command line as
		// -Dtapestry.production-mode=false

	}

	/**
	 * Keep Tapestry from processing requests to the web service path.
	 * 
	 * @param configuration
	 *            {@link Configuration}
	 */
	public static void contributeIgnoredPathsFilter(final Configuration<String> configuration) {
		// configuration.add("/services/.*");
		// configuration.add("/api/.*");
		// configuration.add("/.*\\.do"); // skip struts
		// configuration.add("/person-statistics/.*");
		// configuration
		// .add("/static/ckfinder/core/connector/java/connector.java");
		// configuration.add("/index.jsp.*");
	}

	public static void bind(ServiceBinder binder) {

		// bind db connection pool service.
		binder.bind(IConnectionPool.class, C3P0ConnectionPoolImpl.class);
		binder.bind(ConnectionPool.class);

		binder.bind(AuthService.class, AuthServiceImpl.class).scope(ScopeConstants.DEFAULT);
		binder.bind(ClientPolicy.class);

		binder.bind(TokenDao.class);
		binder.bind(ProductDao.class);

		// binder.bind(QuerySuggestion.class);
		// binder.bind(QuerySuggestionBuild.class);

		// binder.bind(ArnetPageService.class, ArnetPageServiceImpl.class);
	}

}
