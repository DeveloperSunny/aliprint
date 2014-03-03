package com.elivoa.aliprint.common.exception;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.ExceptionReporter;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ResponseRenderer;
import org.slf4j.Logger;

import com.elivoa.aliprint.pages.ExceptionReport;

/**
 * ConfigurableRequestExceptionHandler
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Jan 8, 2012]
 */
public class ConfigurableRequestExceptionHandler implements RequestExceptionHandler {

	private final String exceptionReportPage = ExceptionReport.class.getSimpleName();

	private Logger logger;
	private ExceptionHandler exceptionHandler;
	private RequestExceptionHandler defaultRequestExceptionHandler;
	private LinkSource linkSource;
	private Response response;
	private Request request;
	private ResponseRenderer renderer;
	private ComponentSource componentSource;

	public ConfigurableRequestExceptionHandler(RequestExceptionHandler requestExceptionHandler,
			ComponentClassResolver componentClassResolver, LinkSource linkSource, Request request, Response response,
			ExceptionHandler exceptionHandler, ResponseRenderer renderer, ComponentSource componentSource,
			Logger logger) {
		this.defaultRequestExceptionHandler = requestExceptionHandler;
		this.linkSource = linkSource;
		this.request = request;
		this.response = response;
		this.exceptionHandler = exceptionHandler;
		this.renderer = renderer;
		this.componentSource = componentSource;
		this.logger = logger;
	}

	protected Object[] formExceptionContext(Throwable exception) {
		if (exception instanceof ContextAwareException)
			return ((ContextAwareException) exception).getContext();
		if (exception.getMessage() == null)
			return new Object[0];
		return new Object[] { exception.getMessage() };
	}

	public void handleRequestException(Throwable exception) throws IOException {

		long start = System.currentTimeMillis();

		Throwable cause = exception;
		Throwable rootCause = exception;

		// Find ContextAwareException
		// This will walk through all exception stacks of every exceptions.
		int checkDepth = 4;
		while (checkDepth-- > 0 && null != cause && !(cause instanceof ContextAwareException)) {
			cause = cause.getCause();
		}

		// count by root cause exception.
		checkDepth = 10;
		while (checkDepth-- > 0 && null != rootCause && null != rootCause.getCause()) {
			rootCause = rootCause.getCause();
		}
		if (null != rootCause) {
			StackTraceElement[] stackTrace = rootCause.getStackTrace();
			if (null != stackTrace) {
				StringBuilder sb = new StringBuilder();
				sb.append(rootCause).append(" :: ").append(rootCause.getMessage()).append("\n");

				if (stackTrace.length > 0) {
					sb.append(">>>").append(stackTrace[0].toString());
				}
				if (stackTrace.length > 1) {
					sb.append("\n>>>");
					sb.append(stackTrace[1].toString());
				}
				if (stackTrace.length > 2) {
					sb.append("\n>>>");
					sb.append(stackTrace[2].toString());
				}
				if (stackTrace.length > 3) {
					sb.append("\n>>>");
					sb.append(stackTrace[3].toString());
				}
//				ErrorCounter counter = ErrorCounter.getInstance();
//				long dur = System.currentTimeMillis() - start;
//				counter.count(sb.toString(), dur);
			}
		}

//		long dur = System.currentTimeMillis() - start;
//		PerformanceTimeCount.getInstance().logOne(PerformanceTimeCount.EXCEPTION_PROCESS_TIME, dur);

		// if is other exception
		if (null == cause || !(cause instanceof ContextAwareException)) {
			cause = exception;

			if (logger.isErrorEnabled()) {
				StringBuilder sb = new StringBuilder();
				sb.append("\n\tException:\t").append(exception.getClass().getName());
				sb.append("\n\tMessage:\t").append(exception.getMessage());
				sb.append("\n\tRequest URL:\t").append(request.getPath());
				sb.append("\n\tRefer URL:\t").append(request.getHeader("referer"));
				sb.append("\n\tUser-Agent:\t").append(request.getHeader("user-agent"));
				sb.append("\n\tHost :\t").append(request.getHeader("host"));
				sb.append("\n\t-----------------------------------------");
				logger.error(sb.toString());
				// comment this because the next line will through it.
				// logger.error("Unexpected runtime exception: " +
				// exception.getMessage(), exception);
			}

			defaultRequestExceptionHandler.handleRequestException(exception);
			return;
		}

		// process with ContextAwareException
		ContextAwareException caException = (ContextAwareException) cause;

		// redirect to ExceptionReport.
		if (exceptionReportPage.equalsIgnoreCase(caException.getRedirectPageName())) {
			ExceptionReporter exceptionReport = (ExceptionReporter) componentSource.getPage(exceptionReportPage);
			exceptionReport.reportException(cause);
			renderer.renderPageMarkupResponse(exceptionReportPage);
			return;
		}

		// redirect to redirectPage.
		Link link = linkSource.createPageRenderLink(caException.getRedirectPageName(), false,
				caException.getContext());

		// log
		if (logger.isErrorEnabled()) {
			// TODO Need to throw away the wrapped exceptions first?
			logger.error("Catch and process Exception: %s, redirect to %s.", cause, link.toAbsoluteURI());
		}

		try {
			if (request.isXHR()) {
				OutputStream os = response.getOutputStream("application/json;charset=UTF-8");
				os.write(("{\"script\":\"window.location.replace('" + link.toAbsoluteURI() + "');\"}").getBytes());
				os.close();
			} else
				response.sendRedirect(link);
		}
		// This could throw exceptions if this is already a render request, but
		// it's user's responsibility not to abuse the mechanism.
		catch (Exception e) {
			// Nothing to do but delegate
			defaultRequestExceptionHandler.handleRequestException(exception);
		}
	}
}
