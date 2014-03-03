package com.elivoa.aliprint.common.path;

import org.apache.tapestry5.EventContext;

/**
 * IllegalPathEventContext - TODO Move to a better place.
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Feb 16, 2012]
 */
public class IllegalPathEventContext implements EventContext {

	private String illegalPath;

	public IllegalPathEventContext(String illegalPath) {
		this.illegalPath = illegalPath;
	}

	public int getCount() {
		return 0;
	}

	public <T> T get(Class<T> desiredType, int index) {
		// should never be called because the count is always zero
		return null;
	}

	public String getIllegalPath() {
		return illegalPath;
	}

	@Override
	public String[] toStrings() {
		return new String[] { String.format("IllegalPathEventContext[%s]", illegalPath) };
	}
}