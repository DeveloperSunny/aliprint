package org.iminer.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;
import org.iminer.ui.stacks.StackConstants;

import com.google.common.collect.Lists;

/**
 * BootstrapStack
 * 
 * @author bogao [elivoa|gmail.com], Aug 1, 2012 At Tsinghua <BR>
 */
public class UIStack implements JavaScriptStack {

	private final AssetSource assetSource;

	public UIStack(final AssetSource assetSource) {
		this.assetSource = assetSource;
	}

	public String getInitialization() {
		return null;
	}

	public List<Asset> getJavaScriptLibraries() {
		List<Asset> ret = new ArrayList<Asset>();

		ret.add(assetSource.getClasspathAsset("org/iminer/ui/assets/ui-events.js", null));
		ret.add(assetSource.getClasspathAsset("org/iminer/ui/assets/ui-dialog.js", null));

		// TODO rewrite click_once.js
		ret.add(assetSource.getClasspathAsset("org/iminer/mixins/click_once.js", null));

		// standalone scripts.
		// AMiner functions.
		ret.add(assetSource.getContextAsset("res/js/aminer/aminer_hover.js", null));
		ret.add(assetSource.getContextAsset("res/js/aminer/aminer_dialog.js", null));

		return ret;
	}

	public List<StylesheetLink> getStylesheets() {
		List<StylesheetLink> ret = new ArrayList<StylesheetLink>();

		// ret.add(new
		// StylesheetLink(assetSource.getContextAsset("res/bootstrap/css/bootstrap.customize.css",
		// null)));

		return ret;
	}

	public List<String> getStacks() {
		return Lists.newArrayList(StackConstants.JQueryStack, StackConstants.BootstrapStack);
	}

}