package com.elivoa.aliprint.common.exception;

import java.util.Map;

import org.apache.tapestry5.services.ExceptionReporter;

public interface ExceptionHandler {

	public Map<Class<? extends RuntimeException>, Class<? extends ExceptionReporter>> getConfiguration();

}
