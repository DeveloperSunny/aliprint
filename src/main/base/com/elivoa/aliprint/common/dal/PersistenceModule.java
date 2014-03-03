package com.elivoa.aliprint.common.dal;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.elivoa.aliprint.services.dal.CommonDAO;
import com.elivoa.aliprint.services.dal.JPACommonDAO;

/**
 * This module is automatically included as part of the Tapestry IoC Registry,
 * it's a good place to configure and extend Tapestry, or to place your own
 * service definitions.
 */
public class PersistenceModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(CommonDAO.class, JPACommonDAO.class);
	}

	@Contribute(SymbolProvider.class)
	@FactoryDefaults
	public static void provideFactoryDefaults(final MappedConfiguration<String, String> configuration) {
		// configuration.add(JpaSymbols.PERSISTENCE_DESCRIPTOR,
		// "/META-INF/persistence.xml");
	}

	/**
	 * If you have additional packages containing entities, you may contribute
	 * them to the JpaEntityPackageManager service configuration.
	 */
	// @Contribute(JpaEntityPackageManager.class)
	// public static void providePackages(Configuration<String> configuration) {
	// configuration.add("org.example.myapp.domain");
	// configuration.add("com.acme.model");
	// }

	/**
	 * @CommitAfter Add to *DAO.
	 */
//	@Match("*DAO")
//	public static void adviseTransactionally(JpaTransactionAdvisor advisor, MethodAdviceReceiver receiver) {
//		advisor.addTransactionCommitAdvice(receiver);
//	}

}
