package org.iminer.ui.data;

import org.apache.tapestry5.Link;

public class LinkItem {
	public static final String TAPESTRY_LINK = "TapestryLink";
	public static final String COMMON_LINK = "CommonLink";

	private String title;
	private String url;
	private Link link;
	private boolean newpage = false;
	private String linkType = TAPESTRY_LINK; // tapestry

	private String tooltip;

	public LinkItem() {
		super();
	}

	public LinkItem(String title, String href) {
		super();
		this.title = title;
		this.url = href;
	}

	public LinkItem(String title, String url, boolean newpage, String tooltop) {
		this(title, url, newpage, TAPESTRY_LINK, tooltop);
	}

	public LinkItem(String title, String url, boolean newpage, String linkType, String tooltip) {
		super();
		this.title = title;
		this.url = url;
		this.newpage = newpage;
		this.linkType = linkType;
		this.tooltip = tooltip;
	}

	public LinkItem(String title, Link link, boolean newpage, String tooltip) {
		super();
		this.title = title;
		this.link = link;
		this.newpage = newpage;
		this.tooltip = tooltip;
		this.linkType = TAPESTRY_LINK;
	}

	public LinkItem(String title, String url, Link link, boolean newpage, String tooltip) {
		super();
		this.title = title;
		this.link = link;
		this.url = url;
		this.newpage = newpage;
		this.tooltip = tooltip;
		this.linkType = TAPESTRY_LINK;
	}

	public boolean isTapestryStyleLink() {
		return TAPESTRY_LINK.equals(linkType);
	}

	/*
	 * Getter&Setters
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isNewpage() {
		return newpage;
	}

	public void setNewpage(boolean newpage) {
		this.newpage = newpage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

}
