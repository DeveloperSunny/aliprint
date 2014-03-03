package com.elivoa.aliprint.components.ui;

import java.io.IOException;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.TrackableComponentEventCallback;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.iminer.ui.data.UIDialogContext;

/**
 * Dialog
 * 
 * <pre>
 * TODO support element. 
 * TODO support button component 
 * TODO support non-modal
 * TODO support fixed-click background to close.
 * 
 * TODO ++++ [BootStrap] dialog close button
 * </pre>
 * 
 * @author bogao [elivoa|gmail.com], Jul 24, 2012 At Tsinghua <BR>
 */
@SupportsInformalParameters
@Events(Dialog.Event_NAME)
public class Dialog implements ClientElement {

	public static final String Event_NAME = "DialogOpen";

	static final String defaultPopupComponentId = "dialogPopup";

	// TODO too bad apprach
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "a")
	String element;

	/**
	 * If provided, this is the event context, which will be provided via the
	 * DialogOpen
	 */
	@Parameter
	private Object[] context;

	/*
	 * Configs
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "600")
	Integer width;

	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "400")
	Integer height;

	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "true")
	Boolean keyboard;

	// true, false, static
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "true")
	String backdrop;

	// true, false, static
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "true")
	Boolean closeButton;

	/** links displayd in */
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "")
	Block label;

	@Parameter(value = "block:triggerTemplate")
	@Property(write = false)
	Block trigger;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	@Property(write = false)
	String triggerLinkStyle;

	/*
	 * fields
	 */
	@Property(write = false)
	UIDialogContext dialogContext;

	/*
	 * Client ID
	 */
	String clientId;

	@Override
	public String getClientId() {
		clientId = jsSupport.allocateClientId(resources);
		return clientId;
	}

	/*
	 * Events
	 */
	void setupRender() {

	}

	void beginRender() {
		dialogContext = new UIDialogContext(getClientId());

		// push environment
		environment.push(UIDialogContext.class, dialogContext);
	}

	void afterRender() {
		Link link = resources.createEventLink(EventConstants.ACTION, context);

		JSONObject params = new JSONObject();
		params.put("dialogId", dialogContext.getDialogId());
		params.put("zone", dialogContext.getDialogZone());
		params.put("url", link.toURI());

		params.put("width", this.width);
		params.put("height", this.height);

		// bootstrap options
		JSONObject options = new JSONObject();
		options.put("backdrop", backdrop);
		options.put("keyboard", keyboard);
		params.put("options", options);

		jsSupport.addInitializerCall("dialog", params);

		// pop environment
		environment.pop(UIDialogContext.class);
	}

	Object onAction(EventContext context) throws IOException {
		resources.triggerContextEvent(Event_NAME, context, eventCallback);

		if (eventCallback.isAborted())
			return null;

		Component component = resources.getContainerResources().getEmbeddedComponent(defaultPopupComponentId);
		DialogPopup dialogPopup = (DialogPopup) component;

		return dialogPopup.getRefreshBody();
	}

	/*
	 * Services
	 */
	@Inject
	JavaScriptSupport jsSupport;

	@Inject
	ComponentResources resources;

	@Environmental
	TrackableComponentEventCallback eventCallback;

	@Inject
	Environment environment;

	/*
	 * Deprecated
	 */
}
