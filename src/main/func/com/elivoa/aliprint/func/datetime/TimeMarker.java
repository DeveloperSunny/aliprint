package com.elivoa.aliprint.func.datetime;

import java.text.DecimalFormat;
import java.util.Date;

public class TimeMarker {
	private Date start;
	private Date root;

	private TimeMarker(Date date) {
		this.root = date;
		this.start = date;
	}

	public static TimeMarker startNewTimer() {
		return new TimeMarker(new Date());
	}

	public long restart() {
		long l = new Date().getTime() - start.getTime();
		this.start = new Date();
		this.root = start;
		return l;
	}

	public long mark() {
		long l = new Date().getTime() - start.getTime();
		this.start = new Date();
		return l;
	}

	public long now() {
		long l = new Date().getTime() - start.getTime();
		return l;
	}

	public long total() {
		long l = new Date().getTime() - root.getTime();
		return l;
	}

	private static DecimalFormat n2stringDF = new DecimalFormat("###.####");
	static {
		n2stringDF.setMinimumFractionDigits(0);
		n2stringDF.setMaximumFractionDigits(4);
	}

	public String passedSecond() {
		return n2stringDF.format(passedSecondFloat());
	}

	public float passedSecondFloat() {
		return now() / 1000f;
	}
}
