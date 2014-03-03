package com.elivoa.aliprint.func.web;

/**
 * URLTool
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Nov 11, 2010]
 */
public class URLTool {

	public static String fixURL(String url) {
		return fixURL(url, null);
	}

	public static String fixURL(String url, String defaultURL) {
		if (null == url || "".equals(url.trim())) {
			return defaultURL;
		}
		url = url.trim();
		String lowercasedURL = url.toLowerCase();
		if (lowercasedURL.startsWith("http://") || lowercasedURL.startsWith("https://")
				|| lowercasedURL.startsWith("ftp://")) {
			return url;
		} else {
			return String.format("%s%s", "http://", url);
		}
	}

}
