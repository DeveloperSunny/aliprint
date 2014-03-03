package com.elivoa.aliprint.components.ui;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.iminer.ui.services.progress.MessageProgressReporter.MessageStack;

/**
 * ProgressReport
 * 
 * <pre>
 * TODO Progress bar,
 * TODO message block, ajax refresh
 * TODO BackgroundRun.
 * </pre>
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Aug 6, 2012]
 */
@Import(library = { "_/ui-progressreport.js" })
public class ProgressReport {

	/**
	 * Period between two consecutive refreshes (in milli-seconds)
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "0.3")
	private float period;

	@Parameter
	private MessageStack messageStack;

	@Persist
	private MessageStack _messageStack;

	@InjectComponent
	private Zone zone;

	@Persist
	private String clientId;

	void setupRender() {
		this._messageStack = messageStack;
	}

	void afterRender() {
		JSONObject params = new JSONObject();

		params.put("period", period);
		params.put("id", getZoneId());
		params.put("progressBarId", getProgressBarId());
		params.put("URL", createEventLink());
		params.put("progressURL", createProgressEventLink());

		jsSupport.addInitializerCall(InitializationPriority.LATE, this.getClass().getSimpleName(), params);
	}

	private Object createEventLink() {
		Link link = resources.createEventLink("refresh");
		return link.toAbsoluteURI();
	}

	private Object createProgressEventLink() {
		Link link = resources.createEventLink("progress");
		return link.toAbsoluteURI();
	}

	public String getClientId() {
		if (null == clientId) {
			this.clientId = jsSupport.allocateClientId(this.resources);
		}
		return this.clientId;
	}

	public String getProgressBarId() {
		return String.format("progressbar_%s", getClientId());
	}

	public String getZoneId() {
		return String.format("progresszone_%s", getClientId());
	}

	public String getMessage() {
		return _messageStack.toString();
	}

	Object onProgress() {
		JSONObject json = new JSONObject();
		json.put("progress", this._messageStack.getProgress());
		if (this._messageStack.getError() != null) {
			json.put("error", this._messageStack.getError().getMessage());
		}
		return json;
	}

	Object onRefresh() {
		return zone.getBody();
	}

	/*
	 * Services
	 */
	@Inject
	private JavaScriptSupport jsSupport;

	@Inject
	private ComponentResources resources;
}
