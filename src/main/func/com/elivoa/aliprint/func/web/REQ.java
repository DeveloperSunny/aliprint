package com.elivoa.aliprint.func.web;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc - REQ, many shortcuts.
 * 
 * @author gb <elivoa@gmail.com>
 * 
 * @date Jul 3, 2009 @version 0.1.0.0 @by gb - initial.
 * @date Jul 3, 2009 @version 0.1.0.0 @by gb - initial.
 * @author Bo Gao elivoa[AT]gamil.com, [Jan 25, 2011] + getFullURL
 */
public class REQ extends REQCore {

	/*
	 * Get String
	 */
	public static String get(HttpServletRequest req, String key) {
		return _get(req, key, null, false, true, String.class);
	}

	public static String get(HttpServletRequest req, String key, String defaultValue) {
		return _get(req, key, defaultValue, false, true, String.class);
	}

	public static String getNoEmpty(HttpServletRequest req, String key) {
		return _get(req, key, null, false, false, String.class);
	}

	public static String getNoEmpty(HttpServletRequest req, String key, String defaultValue) {
		return _get(req, key, defaultValue, false, false, String.class);
	}

	public static String getnKeep(HttpServletRequest req, String key) {
		return _get(req, key, null, true, true, String.class);
	}

	public static String getnKeep(HttpServletRequest req, String key, String defaultValue) {
		return _get(req, key, defaultValue, true, true, String.class);
	}

	public static String getnKeepNoEmpty(HttpServletRequest req, String key) {
		return _get(req, key, null, true, false, String.class);
	}

	public static String getnKeepNoEmpty(HttpServletRequest req, String key, String defaultValue) {
		return _get(req, key, defaultValue, true, false, String.class);
	}

	/*
	 * get int
	 */
	public static Integer getInt(HttpServletRequest req, String key) {
		return _get(req, key, null, false, true, Integer.class);
	}

	public static Integer getInt(HttpServletRequest req, String key, Integer defaultValue) {
		return _get(req, key, defaultValue, false, true, Integer.class);
	}

	public static Integer getIntNoEmpty(HttpServletRequest req, String key) {
		return _get(req, key, null, false, false, Integer.class);
	}

	public static Integer getIntNoEmpty(HttpServletRequest req, String key, Integer defaultValue) {
		return _get(req, key, defaultValue, false, false, Integer.class);
	}

	public static Integer getIntnKeep(HttpServletRequest req, String key) {
		return _get(req, key, null, true, true, Integer.class);
	}

	public static Integer getIntnKeep(HttpServletRequest req, String key, Integer defaultValue) {
		return _get(req, key, defaultValue, true, true, Integer.class);
	}

	public static Integer getIntnKeepNoEmpty(HttpServletRequest req, String key) {
		return _get(req, key, null, true, false, Integer.class);
	}

	public static Integer getIntnKeepNoEmpty(HttpServletRequest req, String key, Integer defaultValue) {
		return _get(req, key, defaultValue, true, false, Integer.class);
	}

	/*
	 * get double
	 */
	public static Double getDouble(HttpServletRequest req, String key) {
		return _get(req, key, null, false, true, Double.class);
	}

	public static Double getInt(HttpServletRequest req, String key, Double defaultValue) {
		return _get(req, key, defaultValue, false, true, Double.class);
	}

	/*
	 * not managed below.
	 */
	/**
	 * get boolean
	 */
	public static Boolean getBoolean(HttpServletRequest request, String key) {
		return getBoolean(request, key, null);
	}

	public static Boolean getBoolean(HttpServletRequest request, String key, Boolean defaultValue) {
		Boolean bool = null;
		try {
			String strb = request.getParameter(key);
			if (strb != null) {
				bool = Boolean.parseBoolean(request.getParameter(key));
			}
		} catch (NumberFormatException e) {
		}
		if (null == bool) {
			bool = (Boolean) request.getAttribute(key);
		}
		return bool == null ? defaultValue : bool;
	}

	public static Boolean getBooleannKeep(HttpServletRequest request, String key, Boolean defaultValue) {
		Boolean bool = null;
		try {
			String strb = request.getParameter(key);
			if (strb != null) {
				bool = Boolean.parseBoolean(request.getParameter(key));
			}
		} catch (NumberFormatException e) {
		}
		if (null == bool) {
			bool = (Boolean) request.getAttribute(key);
		}
		boolean finalvalue = (bool == null ? defaultValue : bool);
		request.setAttribute("key", finalvalue);
		return finalvalue;
	}

	/*
	 * Attribute
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(HttpServletRequest request, String key, Class<T> clazz) {
		return (T) request.getAttribute(key);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(HttpServletRequest request, String key) {
		return (T) request.getAttribute(key);
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> getMapAttribute(HttpServletRequest request, String key) {
		return (Map<K, V>) request.getAttribute(key);
	}

	@SuppressWarnings("unchecked")
	public static <K> Set<K> getSetAttribute(HttpServletRequest request, String key) {
		return (Set<K>) request.getAttribute(key);
	}

	@SuppressWarnings("unchecked")
	public static <K> List<K> getListAttribute(HttpServletRequest request, String key) {
		return (List<K>) request.getAttribute(key);
	}

	/*
	 * Set to pageContext
	 */

}
