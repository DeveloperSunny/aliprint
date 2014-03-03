package com.elivoa.aliprint.mixins.ui;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.TapestryException;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.iminer.ui.data.UIDialogContext;

/**
 * Bootstrap Popover support
 * 
 * NOTE! Need bootstrap-apptool.js
 * 
 * TODO Support closeAfterAction
 * 
 * TODO Support close
 * 
 * @author bogao [elivoa|gmail.com], Jul 25, 2012 At HOME <BR>
 */
@MixinAfter
@SupportsInformalParameters
public class UIDialogAction {

	@Environmental(false)
	@Property(write = false)
	private UIDialogContext context;

	@InjectContainer
	private ClientElement clientElement;

	void setupRender() {
		if (null == context) {
			throw new TapestryException("mixin ui/dialogAction can only be used in ui.dialog component.", null);
		}
	}

	void beginRender(MarkupWriter writer) {
		resources.renderInformalParameters(writer);
	}

	void afterRender(MarkupWriter writer) {
		String action = resources.getInformalParameter("action", String.class);
		if ("cancel".equalsIgnoreCase(action)) {
			// jsSupport
		}

		JSONObject params = new JSONObject();
		params.put("dialog", context.getDialogId());
		params.put("element", clientElement.getClientId());
		params.put("action", action);
		jsSupport.addInitializerCall("dialogAction", params);

		// jsSupport.addScript("bootstrapEnable_%s('%s')", rel,
		// String.format("#%s", element.getClientId()));
	}

	/*
	 * Services
	 */
	@Inject
	private ComponentResources resources;

	@Environmental
	private JavaScriptSupport jsSupport;

	@InjectContainer
	private ClientElement element;

	@Inject
	private Environment environment;

}
