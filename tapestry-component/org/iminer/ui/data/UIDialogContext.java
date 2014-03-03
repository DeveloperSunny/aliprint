package org.iminer.ui.data;

public class UIDialogContext {

	private String clientId;

	private boolean frame;

	public DialogOptions options = new DialogOptions();

	public UIDialogContext(String clientId) {
		this.clientId = clientId;
	}

	public static class DialogOptions {
		public boolean keyboard = true;
		public boolean backdrop = true;
	}

	/*
	 * Methods
	 */
	public String getRefreshZone() {
		return isFrame() ? getDialogZone() : getDialogId();
	}

	public String getDialogId() {
		return String.format("%sDialog", getClientId());
	}

	public String getDialogZone() {
		return String.format("%sZone", getClientId());
	}

	/*
	 * Accessors
	 */
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public boolean isFrame() {
		return frame;
	}

	public void setFrame(boolean frame) {
		this.frame = frame;
	}

}
