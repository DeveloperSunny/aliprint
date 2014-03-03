package com.elivoa.aliprint.module;

import org.apache.tapestry5.ioc.ServiceBinder;

/**
 * BaseModule
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [May 10, 2012]
 * @author Bo Gao elivoa[AT]gamil.com, [Sep 6, 2012]
 * @author Bo Gao elivoa[AT]gamil.com, [Dec 27, 2012] - add clean feed list clean executre.
 */
public class BaseModule {

	public static void bind(ServiceBinder binder) {

		/*
		 * Basic Redis
		 */
		// binder.bind(RedisConnectionPool.class, JedisConnectionPool.class);
		// binder.bind(RedisLake.class);

		/*
		 * Notifications
		 */
		// binder.bind(Notifications.class);
		// binder.bind(NotificationService.class,
		// NotificationServiceImplWithCache.class);
		// binder.bind(PubSubService.class, PubSubServiceImpl.class);

		/*
		 * Register Event Handlers
		 */
		// binder.bind(ProfileEditEventHandler.class).eagerLoad();
		// binder.bind(ProfileEditHistoryEventHandler.class).eagerLoad();
		// binder.bind(PublicationAddEventHandler.class).eagerLoad();
		// binder.bind(PublicationAnnotationEventHandler.class).eagerLoad();
		// binder.bind(FollowAuthorEventHandler.class).eagerLoad();
		// binder.bind(FollowPublicationEventHandler.class).eagerLoad();
		// binder.bind(AuthorStatisticsChangedEventHandler.class).eagerLoad();
		// binder.bind(PostEventHandler.class).eagerLoad();
		// binder.bind(PostReplyEventHandler.class).eagerLoad();
		// binder.bind(CommentEventHandler.class).eagerLoad();
		// binder.bind(ShareEventHandler.class).eagerLoad();
		// binder.bind(BindEventHandler.class).eagerLoad();

		/*
		 * Services Binds
		 */
		// binder.bind(AppendixService.class, AppendixServiceImpl.class);

	}

	/**
	 * Pub/Sub invoker
	 */
	// @Startup
	// public static void scheduleJobs(PeriodicExecutor executor,
	// final PubSubService pubsubService) {
	// executor.addJob(ScheduleUtils.secondlySchedule(1), "Event Execute",
	// new Runnable() {
	// public void run() {
	// pubsubService.processEvents();
	// }
	// });
	// }

	// @Startup
	// public static void scheduleNotificationTrim(PeriodicExecutor executor,
	// final NotificationService notificationService) {
	// executor.addJob(ScheduleUtils.secondlySchedule(8), "feed list trim",
	// new Runnable() {
	// public void run() {
	// notificationService.processFeedCleanQueue();
	// }
	// });
	// }

}
