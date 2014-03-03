package com.elivoa.aliprint.components.ui;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;
import org.iminer.ui.data.UIDialogContext;

/**
 * Dialog
 * 
 * TODO support literal title and injected block title.
 * 
 * Example: Ajax Environment service.
 * 
 * @author bogao [elivoa|gmail.com], Jul 24, 2012 At Tsinghua <BR>
 */
@SupportsInformalParameters
public class DialogPopup {

	@Environmental
	@Property(write = false)
	private UIDialogContext context;

	// TODO Delete this persist in dialog.
	@Persist
	private UIDialogContext persistContext;

	/*
	 * customize parameters
	 */
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "")
	private Block title;

	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "true")
	private Boolean frame;

	/*
	 * parameters
	 */
	@Inject
	private Block withframe, nonframe, resourceBody;

	/*
	 * Events
	 */
	void setupRender() {
		context.setFrame(this.frame);
		persistContext = context;
		// System.out.println(persistContext.getClientId());
	}

	public Block getContainer() {
		return frame ? withframe : nonframe;
	}

	public Block getRefreshBody() {
		return resourceBody;
	}

	/*
	 * Support environmental
	 */
	void onPushEnv() {
		environment.push(UIDialogContext.class, this.persistContext);
	}

	void onPopEnv() {
		environment.pop(UIDialogContext.class);
	}

	/*
	 * Services
	 */
	@Inject
	private ComponentResources resources;

	@Inject
	private Environment environment;

}
