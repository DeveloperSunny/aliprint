package com.elivoa.aliprint.components.ui;

import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.Component;
import org.iminer.ui.data.TabsTab;

/**
 * Tabs
 * 
 * Actually this is a Layout. Now is a component.
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [May 15, 2012]
 * 
 * @deprecated use TabPannel instead, when it support ajax.
 */
@SuppressWarnings("unused")
public class Tabs implements ClientElement {

	/*
	 * Parameters
	 */
	@Parameter(allowNull = false)
	private List<TabsTab> tabs;

	@Parameter
	private String currentTab;

	@Persist
	private String _currentTab;
	
	@Parameter(value = "literal:auto")
	@Property
	private String width;

	@Parameter(value = "literal:normal", defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String size;

	/*
	 * Fields
	 */
	@Persist
	@Property
	private List<TabsTab> _tabs;

	@Property
	private TabsTab _tab;

	@InjectContainer
	private Component component;

	@InjectComponent
	private Zone zone;

	/*
	 * Events
	 */
	Object setupRender() {
		this._tabs = this.tabs;
		if (null == this._tabs || this._tabs.size() == 0) {
			return false;
		}

		this._currentTab = currentTab;
		
		// init currentTab
		if (null == this._currentTab) {
			this._currentTab = tabs.get(0).getBlockName();
		}
		return null;
	}

	/**
	 * @TODO !Important, use static client ID, one page can only has one tabs
	 *       component.
	 */
	public String getClientId() {
		return "_todo_tabsClientId";
	}

	Object onChangeTab(String tab) {
		this._currentTab = tab;
		return this.zone.getBody();
	}

	public Block getContent() {
		return component.getComponentResources().getBlock(_currentTab);
	}

	public String getSelectedStyleName() {
		return this._tab.getBlockName().equalsIgnoreCase(this._currentTab) ? "active" : "";
	}

	
	
	public String getCurrentTab() {
		return _currentTab;
	}



	/*
	 * Services
	 */
	@Inject
	private ComponentResources resources;

}
