package com.elivoa.aliprint.common.dal;

import com.elivoa.aliprint.func.Strings;

/**
 * Limit
 * 
 * A Simple version.
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Dec 4, 2011]
 */
public class OrderBy {

	private String orderbyField;
	private QuerySort sort;

	private OrderBy() {

	}

	private OrderBy(String orderByField) {
		this.orderbyField = orderByField;
		this.sort = QuerySort.ASC;
	}

	private OrderBy(String orderByField, QuerySort sort) {
		this.orderbyField = orderByField;
		this.sort = sort;
	}

	public static OrderBy with(String orderby) {
		return new OrderBy(orderby);
	}

	public static OrderBy with(String orderby, QuerySort sort) {
		return new OrderBy(orderby, sort);
	}

	public static OrderBy empty() {
		return new OrderBy();
	}

	// accessors
	public String getOrderbyField() {
		return orderbyField;
	}

	public void setOrderbyField(String orderbyField) {
		this.orderbyField = orderbyField;
	}

	public String getOrderbySort() {
		if (this.sort == QuerySort.DESC) {
			return "DESC";
		} else {
			return "ASC";
		}
	}

	public QuerySort getSort() {
		return sort;
	}

	public void setSort(QuerySort sort) {
		this.sort = sort;
	}

	public boolean isValid() {
		if (Strings.isEmpty(this.orderbyField)) {
			return false;
		} else {
			return true;
		}
	}

}
