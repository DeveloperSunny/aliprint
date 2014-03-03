package com.elivoa.aliprint.func.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import com.elivoa.aliprint.func.Strings;

/**
 * @author vivo
 * @desc: Need param: start;<BR>
 *        Set param: start, items_per_page
 */
public class BootstrapPager extends Pager {

	/** xxx?a=1&b=2... */
	/** changed to full url: /xx/xx/__start__/__itemsPerPage__ */
	/** changed to full url: /x?start=__start__&items_per_page=__itemsPerPage__ */
	private String urlTemplate = "";

	private static boolean showLastPage = false;

	public BootstrapPager() {
		super();
	}

	public BootstrapPager(int totalItems, int start, int itemsPerPage) {
		super(totalItems, start, itemsPerPage);
	}

	public BootstrapPager(int totalItems, int start, int itemsPerPage, String urlTemplate) {
		super(totalItems, start, itemsPerPage);
		this.urlTemplate = urlTemplate;
		if (null == this.urlTemplate) {
			this.urlTemplate = "#";
		}
	}

	public String getHTLM() {
		super.calc();
		if (_totalPages <= 0) {
			return "";
		}
		int lrlength = 4;

		StringBuilder sb = new StringBuilder();
		sb.append("<ul>");
		sb.append(warpPageNumber(1));

		if (_currentPage > lrlength) {
			sb.append("<li><a>...</a></li>");
		}

		int from = Math.max(_currentPage - lrlength, 2);
		int to1 = Math.max(_currentPage + lrlength, 2 * lrlength + 3);
		int to2 = Math.min(to1, _totalPages - 1);
		if (!showLastPage) {// debug fix
			to2 = Math.min(to1, _totalPages);
		}
		for (int page = from; page <= to2; page++) {
			sb.append(warpPageNumber(page));
		}

		if (_currentPage < _totalPages - lrlength - 1) {
			sb.append("<li><a>...</a></li>");
		}

		if (showLastPage) {// debug fix
			sb.append(warpPageNumber(_totalPages));
		}
		sb.append("</ul>");

		return sb.toString();
	}

	private StringBuilder warpPageNumber(int pageNumber) {
		StringBuilder sb = new StringBuilder();
		sb.append("<li ");
		if (_currentPage == pageNumber) {
			sb.append("class='active' ");
		}
		sb.append("><a ");

		String url = urlTemplate.replaceFirst("__start__", String.valueOf((pageNumber - 1) * itemsPerPage));
		url = url.replaceFirst("__itemsPerPage__", String.valueOf(itemsPerPage));

		sb.append("href='").append(url);
		sb.append("'>").append(pageNumber).append("</a></li>");
		return sb;
	}

	public static BootstrapPager receiveData(HttpServletRequest request) {
		BootstrapPager pager = new BootstrapPager();
		pager.setStart(REQ.getInt(request, "start", 0));
		// pager.setTotalItems(REQ.getInt(request, "total", 0));
		pager.setItemsPerPage(REQ.getInt(request, "items_per_page", Pager.default_items_per_page));
		return pager;
	}

	public static BootstrapPager receiveData(HttpServletRequest request, String suffix) {
		BootstrapPager pager = new BootstrapPager();
		pager.setStart(REQ.getInt(request, "start" + suffix, 0));
		pager.setItemsPerPage(REQ.getInt(request, "items_per_page" + suffix, Pager.default_items_per_page));
		return pager;
	}

	public void setToRequest(HttpServletRequest request) {
		request.setAttribute("start", getStart());
		request.setAttribute("items_per_page", getItemsPerPage());
	}

	public void setToRequest(HttpServletRequest request, String suffix) {
		request.setAttribute("start" + suffix, getStart());
		request.setAttribute("items_per_page" + suffix, getItemsPerPage());
	}

	/**
	 * Test
	 */
	public static void main(String[] args) {
		BootstrapPager pager = new BootstrapPager(699, 99, 50, "rini.do?aaa=bbb");
		for (int i = 0; i < 1000; i += 50) {
			pager.setStart(i);
			System.out.println(i + ">>" + pager.getHTLM());
		}
	}

	/*
	 * Modified Getter & setters
	 */
	public void setUrlTemplate(String urlTemplate) {
		this.urlTemplate = Strings.safeTrim(urlTemplate);
	}

	/*
	 * Getter & setters
	 */
	public String getUrlTemplate() {
		return urlTemplate;
	}

	@Inject
	PageRenderLinkSource pageRenderLinkSource;
}
