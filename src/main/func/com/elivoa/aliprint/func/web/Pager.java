package com.elivoa.aliprint.func.web;

/**
 * @author vivo
 */
public class Pager {
	public static final String __START__ = "__start__";
	public static final String __ITEMS_PER_PAGE__ = "__itemsPerPage__";

	public static final int default_items_per_page = 20;

	protected boolean debug = false;

	protected int totalItems = 0;
	protected int start = 0;
	protected int itemsPerPage = default_items_per_page;

	public Pager() {
	}

	public Pager(int totalItems, int start, int itemsPerPage) {
		super();
		this.totalItems = totalItems;
		this.start = start;
		this.itemsPerPage = itemsPerPage;
	}

	protected int _totalPages;
	protected int _currentPage;

	public void calc() {
		_totalPages = totalItems / itemsPerPage + ((totalItems % itemsPerPage) > 0 ? 1 : 0);
		_currentPage = start / itemsPerPage + 1;
		if (debug) {
			System.out.println("Debug Pager:totalPages = " + _totalPages);
			System.out.println("Debug Pager:currentPage = " + _currentPage);
		}
	}

	public int getTotalPages() {
		return _totalPages;
	}

	public int getCurrentPage() {
		return _currentPage;
	}

	/*
	 * Modified Getter & setters
	 */
	public void setItemsPerPage(int itemsPerPage) {
		if (itemsPerPage > 0) {
			this.itemsPerPage = itemsPerPage;
		}
	}

	/*
	 * Getter & setters
	 */

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

}
