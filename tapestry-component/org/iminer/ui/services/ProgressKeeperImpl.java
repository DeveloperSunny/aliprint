package org.iminer.ui.services;

import org.iminer.ui.services.progress.ProgressReporter;

/**
 * ProgressKeeperImpl
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Aug 6, 2012]
 */
public class ProgressKeeperImpl implements ProgressKeeper {

	/**
	 * TODO + timeout
	 */
	@Override
	public int invoke(final ProgressReporter task, final int timeout) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				task.run();
			}
		});
		thread.start();
		return 0;
	}
}
