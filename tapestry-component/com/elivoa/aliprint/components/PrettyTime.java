// Copied from Tapestry5 corelib: output.java

package com.elivoa.aliprint.components;

import java.security.Timestamp;
import java.util.Date;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;

/**
 * A component for formatting time. Use PrettyTime.java
 * 
 * @tapestrydoc
 */
@SupportsInformalParameters
public class PrettyTime {
	/**
	 * The value to be output (before formatting). If the formatted value is blank, no output is
	 * produced.
	 */
	@Parameter(required = true, autoconnect = true)
	private Object value;

	/**
	 * The element name, derived from the component template. This can even be overridden manually
	 * if desired (for example, to sometimes render a surrounding element and other times not).
	 */
	@Parameter("componentResources.elementName")
	private String elementName;

	@Inject
	private ComponentResources resources;

	private org.ocpsoft.prettytime.PrettyTime pt = new org.ocpsoft.prettytime.PrettyTime();

	boolean beginRender(MarkupWriter writer) {
		if (value == null) {
			return false;
		}
		String formatted = null;
		if (value instanceof Timestamp) {
			formatted = pt.format(((Timestamp) value).getTimestamp());
		} else if (value instanceof Date) {
			formatted = pt.format((Date) value);
		}

		if (InternalUtils.isNonBlank(formatted)) {
			if (elementName != null) {
				writer.element(elementName);

				resources.renderInformalParameters(writer);
			}

			writer.writeRaw(formatted);

			if (elementName != null)
				writer.end();
		}

		return false;
	}

}