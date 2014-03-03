package org.iminer.ui.services.progress;

import java.util.List;

import org.slf4j.Logger;

import com.google.common.collect.Lists;

/**
 * ProgressTask
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Aug 6, 2012]
 * @author Bo Gao elivoa[AT]gamil.com, [Nov 19, 2012]
 */
public class MessageProgressReporter extends AbstractTimerProgressReporter implements ProgressReporter {

	private String prefix;

	private MessageStack messageStack;

	/*
	 * Constructors
	 */
	public MessageProgressReporter() {
		init(null);
	}

	public MessageProgressReporter(String prefix, MessageStack messageStack) {
		this.messageStack = messageStack;
		init(prefix);
	}

	@Override
	public ProgressReporter init(String prefix) {
		this.prefix = prefix;
		this.timerStart();
		this.useMgrLogger();
		return this;
	}

	public int run() {
		// hook
		return 0;
	}

	public int _report(Float progress, String messageLine, Throwable error) {
		if (true) {// debug
			if (logger.isInfoEnabled()) {
				if (null == progress) {
					progress = 0f;
				}
				logger.info(String.format("[%s %3.0f%%] %s", prefix, progress, messageLine));
			}
		}
		if (null != messageStack) {
			return messageStack.add(progress, messageLine, error);
		}
		return -1;
	}

	@Override
	public int report(float progress, String messageLine, Object... params) {
		return _report(progress, String.format(messageLine, params), null);
	}

	@Override
	public int success() {
		return success(null);
	}

	@Override
	public int success(String successMessage) {
		return _report(100f, successMessage, null);
	}

	@Override
	public int error(Throwable exception) {
		return _report(null, null, exception);
	}

	@Override
	public int error(String errorMessage, Throwable exception) {
		return _report(null, errorMessage, exception);
	}

	public static class MessageStack {
		private List<String> messages;
		private float progress;
		private Throwable error;

		public MessageStack() {
			this.messages = Lists.newArrayList();
		}

		public int add(Float progress, String message, Throwable error) {
			if (null != progress) {
				this.progress = progress;
			} else {
				progress = 0.01f;
			}
			if (null != error) {
				this.error = error;
			}
			if (null == message) {
				message = error.getMessage();
			}
			this.messages.add(String.format("[%.1f%%] %s", progress, message));
			return 0;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (String message : messages) {
				sb.append(message);
				sb.append("<br/>");
			}
			return sb.toString();
		}

		public void clear() {
			this.messages.clear();
		}

		public float getProgress() {
			return progress;
		}

		public void setProgress(float progress) {
			this.progress = progress;
		}

		public Throwable getError() {
			return error;
		}

		public void setError(Throwable error) {
			this.error = error;
		}

	}

	private Logger logger;

	public void useMgrLogger() {
		// this.logger = LogHelper.getMgrLogger();
	}

	public void useDisambiguationLogger() {
		// this.logger = LogHelper.getDisambiguationLogger();
	}

	/*
	 * accessors
	 */
	public String getName() {
		return prefix;
	}

	public void setName(String name) {
		this.prefix = name;
	}
}
