package com.elivoa.aliprint.func.debug;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class DebugTool {
	public static void readSystemIn(String... msgs) {
		System.out.println("DebugTool::Paused. Input to continue.");
		for (String msg : msgs) {
			System.out.println(msg);
		}
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String deepToString(List<?> list) {
		if (null == list) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (Object t : list) {
			sb.append(t.toString()).append(", ");
		}
		return sb.toString();
	}

	public static String getStackTraceOneLine(Throwable t) {
		return getStackTrace(t, " ");
	}

	public static String getStackTraceWeb(Throwable t) {
		return getStackTrace(t, "<br />");
	}

	public static String getStackTrace(Throwable t, String newLineSpliter) {
		StringOutputStream sos = new StringOutputStream();
		t.printStackTrace(new PrintStream(sos));
		return sos.toString().replaceAll("\n", newLineSpliter);
	}

	public static String[] getStackTraceAsList(Throwable t) {
		StringOutputStream sos = new StringOutputStream();
		t.printStackTrace(new PrintStream(sos));
		return sos.toString().split("\n");
	}

	public static void main(String[] args) {
		System.out.println(DebugTool.getStackTraceWeb(new NullPointerException()));
	}
}
