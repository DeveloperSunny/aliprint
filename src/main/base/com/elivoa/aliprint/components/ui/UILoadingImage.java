package com.elivoa.aliprint.components.ui;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * UILoadingImage
 * 
 * Only show a loading image.
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Feb 19, 2012]
 */
@Import(stylesheet = { "context:res/css/ui-suit.css" })
public class UILoadingImage {

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "100%")
	private String width;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "100%")
	private String height;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "circle")
	private String type;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL, value = "32")
	private String size;

	public String getImg() {
		String img = "/res/images/common/loading/circle-" + size + ".gif";
		if (type.equalsIgnoreCase("horizental")) {
			img = "/res/images/common/loading/horizental-80x12.gif";
		}
		return img;
	}

}
