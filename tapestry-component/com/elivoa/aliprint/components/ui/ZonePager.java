package com.elivoa.aliprint.components.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class ZonePager {

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
	@Property
	String anchorName;

	@Property
	int _loopindex;

	@Property
	List<Integer> _loopList;

	void setupRender() {
		_loopList = new ArrayList<Integer>();

		System.out.println(total);
		for (int i = 1; i <= total; i++) {
			_loopList.add(i);
		}
	}

	public Object onGotoPage(int pageNumber) {
		final List<Object> rs = new ArrayList<Object>();
		componentResources.triggerEvent("changePage", new Object[] { pageNumber },
				new ComponentEventCallback<Object>() {
					@Override
					public boolean handleResult(Object result) {
						rs.add(result);
						return true;
					}
				});
		return rs.get(0);
	}

	public String getActive() {
		if (_loopindex == start) {
			return "active";
		}
		return "";
	}

	/*
	 * Services
	 */
	@Inject
	ComponentResources componentResources;

	@Inject
	PageRenderLinkSource pageRenderLinkSource;

}
