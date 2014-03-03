package com.elivoa.aliprint.common.exception;

import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ResponseRenderer;
import org.slf4j.Logger;

/**
 * ExceptionPageModule
 * 
 * Process Auto redirect to page on exception.
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Jan 8, 2012]
 */
public class ExceptionProcessingModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(ExceptionHandler.class, ExceptionHandlerImpl.class);
	}

	public RequestExceptionHandler decorateRequestExceptionHandler(ComponentClassResolver componentClassResolver,
			LinkSource linkSource, Request request, Response response, ExceptionHandler exceptionHandler,
			Object service, ResponseRenderer renderer, ComponentSource componentSource, Logger logger) {

		return new ConfigurableRequestExceptionHandler((RequestExceptionHandler) service, componentClassResolver,
				linkSource, request, response, exceptionHandler, renderer, componentSource, logger);
	}

}