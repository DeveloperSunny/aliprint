package com.elivoa.aliprint.bridge;


import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.test.PageTester;

public class T5RegistryHelper {

	public static Registry registry;

	public static void setRegistry(Registry registry) {
		T5RegistryHelper.registry = registry;
	}

	public static <T> T getService(Class<T> serviceInterface) {
		initStandalone();
		return registry.getService(serviceInterface);
	}

	public synchronized static void initStandalone() {
		if (null == registry) {
			System.out.println("== IOC Start =============");
			PageTester pageTester = new PageTester("org.iminer", "Iminer", "web");
			setRegistry(pageTester.getRegistry());
		}
	}

	/**
	 * Standalone only, cleanup.
	 */
	public static void shutdown() {
		System.out.println("== IOC Shutdown =============");
		// for operations done from this thread
		registry.cleanupThread();
		// call this to allow services clean shutdown
		registry.shutdown();
	}

	/**
	 * This is a test.
	 */
	public static void main2(String[] args) {
//		CommonDAO commonDao = T5RegistryHelper.getService(CommonDAO.class);
//		User user = commonDao.findUniqueWithNamedQuery(UserConstants.NQ_BY_USERNAME,
//				QueryParameters.with("username", Strings.safeTrim("vivo")).parameters());
//		System.out.println(user);
//
//		// SocialNetworkService sns =
//		// T5RegistryHelper.getService(SocialNetworkService.class);
//		// List<SocialNetworkRelation> relations = sns.getOutRelations(1458619,
//		// SocialNetworkService.RELATIONTYPE_BASIC);
//		// if (null != relations) {
//		// for (SocialNetworkRelation relation : relations) {
//		// System.out.println(relation);
//		// }
//		// }
//		//
//		// T5RegistryHelper.shutdown();
//
//		// SearchService searchService =
//		// T5RegistryHelper.getService(SearchService.class);
//		// SearchCriteria sc = new SearchCriteria();
//		// sc.setQuery("jie tang");
//		// SearchResultMeta a = searchService.searchExperts(sc);
//		// System.out.println(a.getData());
//		T5RegistryHelper.shutdown();
	}

//	public static void main(String[] args) {
//		PageTester tester = new PageTester("org.iminer", "Iminer");
//		PublicationService service = tester.getService(PublicationService.class);
//		List<Publication> pubs = service.getPublicationsByConferenceId(345);
//		for (Publication pub : pubs) {
//			System.out.println(pub);
//		}
//	}

}
