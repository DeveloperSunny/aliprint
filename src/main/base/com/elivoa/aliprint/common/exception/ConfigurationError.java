package com.elivoa.aliprint.common.exception;

/**
 * @author bogao <br />
 * 
 */
public class ConfigurationError extends Error {

	private static final long serialVersionUID = 4361510811928124716L;

	public ConfigurationError() {
		super();
	}

	public ConfigurationError(String message, Throwable e) {
		super(message, e);
	}

	public ConfigurationError(String message) {
		super(message);
	}

	public ConfigurationError(String message, Object... objects) {
		super(String.format(message, objects));
	}

	public ConfigurationError(Throwable e) {
		super(e);
	}
}
