package com.elivoa.aliprint.func.core;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * ExceptionUtil
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Dec 17, 2011]
 * @version 1.0
 */
public class ExceptionUtil {
	public static String getDescription(Throwable e) {
		return e.getMessage();
	}

	public static String getStactTraceAsString(Throwable e) {
		PrintWriter writer = new PrintWriter(new StringWriter(), false);
		e.printStackTrace(writer);
		return writer.toString();
	}

}
