package com.elivoa.aliprint.func.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc - REQCore
 * 
 * @author gb <elivoa@gmail.com>
 * 
 * @date Jul 3, 2009 @version 0.1.0.0 @by gb - initial.
 * 
 */
public class REQCore {
	/**
	 * http://localhost:8080/arnet/ajax/jiawei_han.html TO /ajax/jiawei_han.html
	 * 
	 * @param request
	 * @return
	 */
	public static String getDispatcherPath(HttpServletRequest request) {
		StringBuffer ff = request.getRequestURL();
		int idx = ff.indexOf("/", 10);
		idx = idx + request.getContextPath().length();
		return ff.substring(idx);
	}

	/**
	 * Get ip address.
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getContextPath(HttpServletRequest request) {
		String path = request.getContextPath();
		return path.equals("") ? "/" : path;
	}

	public static String getRequestURLFromRequest(HttpServletRequest request) {
		int serverPort = request.getServerPort();
		String path = request.getContextPath();
		StringBuilder sbs = new StringBuilder();
		sbs.append(request.getScheme()).append("://").append(request.getServerName());
		if (serverPort != 80) {
			sbs.append(":").append(serverPort);
		}
		sbs.append(path);
		if (sbs.charAt(sbs.length() - 1) == '/') {
			return sbs.substring(0, sbs.length() - 1);
		}
		return sbs.toString();
	}

	public static String getFullURL(HttpServletRequest req) {
		StringBuilder sb = new StringBuilder(req.getRequestURI());
		Enumeration<?> pnames = req.getParameterNames();
		if (pnames.hasMoreElements()) {
			sb.append("?");
		}
		while (pnames.hasMoreElements()) {
			Object keyObj = pnames.nextElement();
			if (null != keyObj && keyObj instanceof String) {
				String value = req.getParameter((String) keyObj);
				sb.append((String) keyObj).append("=").append(value.trim());
			}
			if (pnames.hasMoreElements()) {
				sb.append("&");
			}
		}
		return sb.toString();
	}

	/**
	 * first parameter, second attribute.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T _get(HttpServletRequest req, String key, T defaultValue, boolean keepInRequestAttribute,
			boolean allowEmpty, Class<T> type) {

		// get value
		String value = req.getParameter(key);
		T finalValue = null;
		if (null == value || (!allowEmpty && value.isEmpty())) {
			// TODO not allow int in attribute now.
			Object objv = req.getAttribute(key);
			if (null == objv) {
				finalValue = defaultValue;
			} else {
				if (objv.getClass() == type) {
					finalValue = (T) objv;
				} else {
					System.out.println("Error in REQ: Type not valid.");
					finalValue = defaultValue;
				}
			}
		}

		if (null == finalValue) {
			// confirm final value.
			if (null == value || (!allowEmpty && value.isEmpty())) {
				finalValue = defaultValue;
			} else {
				if (String.class == type) {
					finalValue = (T) value;
				} else {
					// need parse.
					try {
						if (Integer.class == type) {
							finalValue = (T) new Integer(Integer.parseInt(value));
						} else if (Double.class == type) {
							finalValue = (T) new Double(Double.parseDouble(value));
						} else if (Boolean.class == type) {
							finalValue = (T) new Boolean(Boolean.parseBoolean(value));
						}
					} catch (Exception e) {
						System.out.println("REQ:parse error: [" + value + "] .");
						finalValue = (T) defaultValue;
					}
				}
			}
		}

		// keep?
		if (keepInRequestAttribute) {
			req.setAttribute(key, finalValue);
		}
		return finalValue;
	}

}
