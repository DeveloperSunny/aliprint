package com.elivoa.aliprint.components.ui;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.iminer.ui.data.TabContext;

public class Tab {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL, allowNull = false)
	private String title;

	@Parameter(value = "literal:false", allowNull = false)
	private boolean disabled;

	@Environmental
	private TabContext tabContext;

	@Inject
	private ComponentResources resources;

	boolean beginRender() {
		return isActiveAndEnabled();
	}

	private boolean isActiveAndEnabled() {
		return tabContext.isActiveTab(resources.getId()) && !disabled;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
