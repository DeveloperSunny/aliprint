package org.iminer.ui.services;

import org.iminer.ui.services.progress.ProgressReporter;

/**
 * ProgressKeeper
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Aug 6, 2012]
 */
public interface ProgressKeeper {

	int invoke(ProgressReporter task, int timeout);

}
