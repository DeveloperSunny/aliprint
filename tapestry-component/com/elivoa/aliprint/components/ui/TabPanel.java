package com.elivoa.aliprint.components.ui;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.TapestryException;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.iminer.ui.data.TabContext;

public class TabPanel implements ClientElement {

	@Parameter(value = "", defaultPrefix = BindingConstants.LITERAL, allowNull = false)
	@Property
	private String theme;

	@Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL, allowNull = false)
	private String clientId;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String active;

	@SuppressWarnings("unused")
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String zone;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String tabs;

	private String assignedClientId;

	private String currentTabId;

	@SuppressWarnings("unused")
	@Property
	private int index;

	private String[] tabsCache;

	public TabPanel() {

	}

	// Testing purpose
	TabPanel(String tabs, String active, String currentTabId, JavaScriptSupport javaScriptSupport,
			ComponentResources resources, Environment environment) {
		this.tabs = tabs;
		this.active = active;
		this.currentTabId = currentTabId;
		this.javaScriptSupport = javaScriptSupport;
		this.resources = resources;
		this.environment = environment;
	}

	void setupRender() {
		if (tabs == null || getTabs().length == 0) {
			throw new IllegalArgumentException("You must specify atleast one tab");
		}

		if (active == null) {
			active = getTabs()[0];
		}

		assignedClientId = javaScriptSupport.allocateClientId(clientId);
	}

	void beginRender() {
		environment.push(TabContext.class, new TabContext() {

			public boolean isActiveTab(String tabId) {
				return active != null && active.equals(tabId);
			}

		});
	}

	void afterRender() {
		environment.pop(TabContext.class);
	}

	public String getClientId() {
		return assignedClientId;
	}

	Object onSelectTab(String selected) {
		active = selected;

		CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();

		boolean handled = resources.triggerEvent(EventConstants.SELECTED, new Object[] { selected }, callback);
		if (request.isXHR() & !handled) {
			throw new TapestryException(String.format("Event %s not handled", EventConstants.SELECTED), null);
		}
		return callback.getResult();
	}

	public String getCssClass() {
		return isActiveTab() ? "active" : "";
	}

	public boolean isActiveTab() {
		return currentTabId.equals(active);
	}

	public String getActive() {
		return active;
	}

	public Tab getCurrentTab() {
		return getTab(currentTabId);
	}

	private Tab getTab(String tabId) {
		return (Tab) resources.getContainerResources().getEmbeddedComponent(tabId);
	}

	public String[] getTabs() {
		if (tabsCache == null) {
			tabsCache = TapestryInternalUtils.splitAtCommas(tabs);
		}
		return tabsCache;
	}

	/*
	 * Accessors
	 */
	public String getCurrentTabId() {
		return currentTabId;
	}

	public void setCurrentTabId(String currentTabId) {
		this.currentTabId = currentTabId;
	}

	/*
	 * Services
	 */
	@Inject
	private JavaScriptSupport javaScriptSupport;

	@Inject
	private ComponentResources resources;

	@Inject
	private Environment environment;

	@Inject
	private Request request;

}