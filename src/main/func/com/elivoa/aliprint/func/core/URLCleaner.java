package com.elivoa.aliprint.func.core;

import javax.servlet.http.HttpServletRequest;

import com.elivoa.aliprint.dev.CodeSnippets;

/**
 * URLCleaner
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Jan 4, 2012]
 */
public class URLCleaner {

	@CodeSnippets
	public static <T> T selectFirstValid(T... items) {
		if (null != items) {
			for (T t : items) {
				if (null != t) {
					return t;
				}
			}
		}
		return null;
	}

	@CodeSnippets
	public static String getURLPrefix(HttpServletRequest request) {
		StringBuilder basePath = new StringBuilder();
		basePath.append(request.getScheme()).append("://");
		basePath.append(request.getServerName()).append(":")
				.append(request.getServerPort());
		basePath.append(request.getContextPath());// .append("/");
		return basePath.toString();
	}

	public static String ensureAbsoluteURL(HttpServletRequest request,
			String url) {
		if (url.startsWith("/")) {
			url = String.format("%s%s", getURLPrefix(request), url);
		}
		return url;
	}

}
