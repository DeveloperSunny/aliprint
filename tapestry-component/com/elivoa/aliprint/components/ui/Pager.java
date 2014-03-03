package com.elivoa.aliprint.components.ui;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import com.elivoa.aliprint.func.web.BootstrapPager;

public class Pager {

	@Parameter
	@Property
	int total;

	@Parameter("0")
	@Property
	Long start;

	@Parameter("20")
	@Property
	Integer n;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	@Property
	String theme;

	@Parameter
	String linkTemplate;

	private BootstrapPager pager;

	void setupRender() {
		pager = new BootstrapPager(total, null == start ? 0 : start.intValue(), n, linkTemplate);
	}

	public String getPagerHtml() {
		return this.pager.getHTLM();
	}

	/*
	 * Services
	 */
	@Inject
	ComponentResources componentResources;

	@Inject
	PageRenderLinkSource pageRenderLinkSource;

}
