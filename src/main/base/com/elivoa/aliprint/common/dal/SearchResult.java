package com.elivoa.aliprint.common.dal;

import java.util.List;

/**
 * @author royzuo
 * 
 * @param <T>
 */
public class SearchResult<T> {
	private List<T> results;
	private Long totalRecords;

	public SearchResult(List<T> results, Long totalRecords) {
		this.results = results;
		this.totalRecords = totalRecords;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
}
