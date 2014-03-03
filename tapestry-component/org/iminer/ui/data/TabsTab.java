package org.iminer.ui.data;

/**
 * TabsTab
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [May 15, 2012]
 */
public class TabsTab {

	private String name;
	private String blockName;
	private String tips;

	public TabsTab() {
		super();
	}

	public TabsTab(String name, String blockName) {
		super();
		this.name = name;
		this.blockName = blockName;
	}

	public TabsTab(String name, String blockName, String tips) {
		super();
		this.name = name;
		this.blockName = blockName;
		this.tips = tips;
	}

	/*
	 * Accessors
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

}
