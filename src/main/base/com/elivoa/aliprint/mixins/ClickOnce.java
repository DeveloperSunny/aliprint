package com.elivoa.aliprint.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * ClickOnce
 * 
 * A simple mixin that uses JavaScript to observe an element, detecting whether it has been clicked. The click will
 * be ignored if any element using this mixin has already been clicked.
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Feb 20, 2012]
 */
@Import(/* stack = StackConstants.JQueryStack, */library = "click_once.js")
public class ClickOnce {

	// TODO need default?
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String maskZone;

	@Parameter
	private String[] maskZones;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String zone;

	@Property
	private String _zone;

	void setupRender() {
		// System.out.println(">>> " + this.maskZone);
		// System.out.println(">" + this.zone);

		this._zone = (null == maskZone) ? this.zone : this.maskZone;
		if (null == this._zone) {
			System.out.println("[warrning] ClickOnce empty zone.");
		}
	}

	public void afterRender() {

		// Tell the Tapestry.Initializer to do the initializing of a ClickOnce,
		// which it will do when the DOM has been
		// fully loaded.

		JSONObject spec = new JSONObject();
		spec.put("elementId", clientElement.getClientId());
		spec.put("zone", this._zone);
		javaScriptSupport.addInitializerCall("clickOnce", spec);
	}

	/*
	 * Services
	 */
	@Inject
	private JavaScriptSupport javaScriptSupport;

	@InjectContainer
	private ClientElement clientElement;

}