package com.elivoa.aliprint.components.ui;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * DialogTrigger
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Jun 18, 2012]
 */
public class DialogTrigger {

	@Parameter
	private String dialog;

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String value;

	void beginRender(MarkupWriter writer) {
		// String clientId = jsSupport.allocateClientId(resources);
		String elementName = resources.getElementName("a");

		Element e = writer.element(elementName, /* "id", clientId, */"href", "javascript:void(0)", "onclick",
				String.format("%s_trigger()", dialog));
		resources.renderInformalParameters(writer);
		e.cdata(value);
	}

	boolean beforeRenderBody() {
		return false;
	}

	void afterRender(MarkupWriter writer) {
		writer.end();
	}

	/*
	 * Services
	 */
	@Inject
	private ComponentResources resources;

	@Environmental
	private JavaScriptSupport jsSupport;

}
