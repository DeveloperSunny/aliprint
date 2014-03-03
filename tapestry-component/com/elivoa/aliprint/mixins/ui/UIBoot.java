package com.elivoa.aliprint.mixins.ui;

import static org.iminer.ui.stacks.StackConstants.BootstrapStack;
import static org.iminer.ui.stacks.StackConstants.JQueryStack;

import java.util.Set;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.google.common.collect.Sets;

/**
 * Bootstrap Popover support
 * 
 * NOTE! Need bootstrap-apptool.js
 * 
 * @author bogao [elivoa|gmail.com], Jul 23, 2012 At Tsinghua <BR>
 * 
 */
@MixinAfter
@SupportsInformalParameters
@Import(stack = { JQueryStack, BootstrapStack })
public class UIBoot {

	private static Set<String> avaliableRels;
	static {
		avaliableRels = Sets.newHashSet("popover", "tooltip");
	}

	void beginRender(MarkupWriter writer) {
		resources.renderInformalParameters(writer);
	}

	void afterRender(MarkupWriter writer) {
		String rel = resources.getInformalParameter("rel", String.class);
		if (null != rel && avaliableRels.contains(rel)) {
			// JSONObject options = new JSONObject();
			// List<String> names = resources.getInformalParameterNames();
			// if (null != names && names.size() > 0) {
			// for (String name : names) {
			// options.put(name, resources.getInformalParameter(name,
			// String.class));
			// }
			// }

			jsSupport.addScript("bootstrapEnable_%s('%s')", rel, String.format("#%s", element.getClientId()));
			// jsSupport.addScript("bootstrapEnable_%s('%s', %s)", rel,
			// String.format("#%s", element.getClientId()),
			// options.toCompactString());
		}
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

}
