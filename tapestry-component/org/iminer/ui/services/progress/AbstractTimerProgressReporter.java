package org.iminer.ui.services.progress;

import java.text.DecimalFormat;

/**
 * AbstractTimerProgressReporter
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Nov 19, 2012]
 */
public abstract class AbstractTimerProgressReporter implements ProgressReporter {
	private long root;
	private long start;

	/*
	 * Timer
	 */
	public void timerStart() {
		this.root = System.currentTimeMillis();
		this.start = root;
	}

	public long timerRestart() {
		long l = timerLoop();
		this.root = start;
		return l;
	}

	public long timerLoop() {
		long l = System.currentTimeMillis() - start;
		this.start = System.currentTimeMillis();
		return l;
	}

	public long timerNow() {
		return System.currentTimeMillis() - start;
	}

	public long timerTotal() {
		return System.currentTimeMillis() - root;
	}

	private static DecimalFormat n2stringDF = new DecimalFormat("###.####");
	static {
		n2stringDF.setMinimumFractionDigits(0);
		n2stringDF.setMaximumFractionDigits(4);
	}

	public String timerPassedSecond() {
		return n2stringDF.format(timerPassedSecondFloat());
	}

	public float timerPassedSecondFloat() {
		return timerNow() / 1000f;
	}

}
