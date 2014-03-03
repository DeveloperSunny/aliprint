package org.iminer.ui.services.progress;

/**
 * ProgressTask
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Aug 6, 2012]
 */
public interface ProgressReporter {

	/*
	 * Progress reporter
	 */
	int run();

	ProgressReporter init(String type);

	int report(float progress, String messageLine, Object... params);

	int success();

	int success(String successMessage);

	int error(Throwable exception);

	int error(String errorMessage, Throwable exception);

	/*
	 * Timer
	 */
	public void timerStart();

	public long timerRestart();

	public long timerLoop();

	public long timerNow();

	public long timerTotal();

	public String timerPassedSecond();

	public float timerPassedSecondFloat();
}
