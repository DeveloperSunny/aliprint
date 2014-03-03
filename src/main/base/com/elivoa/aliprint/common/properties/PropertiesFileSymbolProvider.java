package com.elivoa.aliprint.common.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.ioc.util.CaseInsensitiveMap;
import org.slf4j.Logger;

public class PropertiesFileSymbolProvider implements SymbolProvider {

	private final Map<String, String> propertiesMap = new CaseInsensitiveMap<String>();

	/**
	 * Instantiate a new PropertiesFileSymbolProvider using a given resource
	 * name
	 * 
	 * @param logger
	 *            the logger to log error messages to
	 * @param resourceName
	 *            the name of the resource to load
	 * @param classPath
	 *            whether to look on the classpath or filesystem
	 */
	public PropertiesFileSymbolProvider(Logger logger, String resourceName, boolean classPath) {
		try {
			InputStream in;

			if (classPath) {
				in = this.getClass().getClassLoader().getResourceAsStream(resourceName);
				if (in == null) {
					in = ClassLoader.getSystemResourceAsStream(resourceName);
				}

				// ClassLoader.getSystemResourceAsStream() returns null if
				// the resource cannot be found on the classpath
				if (in == null)
					throw new FileNotFoundException();
			} else
				in = new FileInputStream(resourceName);

			initialize(logger, in);

		} catch (FileNotFoundException e) {
			String msg = "Could not find '" + resourceName + "'";

			logger.error(msg);

			throw new IllegalArgumentException(msg, e);
		}
	}

	/**
	 * Instantiate a PropertiesFileSymbolProvider using a given InputStream
	 * 
	 * @param logger
	 *            the logger
	 * @param in
	 *            an InputStream representing the resource
	 */
	public PropertiesFileSymbolProvider(Logger logger, InputStream in) {
		initialize(logger, in);
	}

	/**
	 * Instantiate a PropertiesFileSymbolProvider from a given URL.
	 * 
	 * @param logger
	 *            the logger
	 * @param url
	 *            an URL to open
	 */
	public PropertiesFileSymbolProvider(Logger logger, URL url) {
		try {
			initialize(logger, url.openStream());
		} catch (IOException e) {
			String msg = "IOException while opening URL '" + url + "': " + e.getMessage();

			logger.error(msg);

			throw new IllegalArgumentException(msg, e);
		}
	}

	private void initialize(Logger logger, InputStream in) {
		Properties properties = new Properties();

		try {
			properties.load(in);
			for (Object key : properties.keySet()) {
				propertiesMap.put((String) key, (String) properties.getProperty((String) key));
			}
			// propertiesMap.putAll(properties);
		} catch (IOException e) {
			String msg = "IOEception while loading properties: " + e.getMessage();

			logger.error(msg);

			throw new IllegalArgumentException(msg, e);
		}
	}

	public String valueForSymbol(String arg0) {
		return propertiesMap.get(arg0);
	}
}
