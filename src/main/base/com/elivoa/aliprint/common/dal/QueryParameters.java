package com.elivoa.aliprint.common.dal;

import java.util.HashMap;
import java.util.Map;

/**
 * QueryParameters
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Dec 3, 2011]
 * 
 * @author Roy Zuo roy.zuo[AT]gmail.com, [Dec. 10, 2011] at home.
 * 
 */
public class QueryParameters {

	public static final String LIMIT_START = "__limit_start";
	public static final String LIMIT_N = "__limit_N";
	public static final String ORDERBY = "__orderby";
	public static final String ORDERBY_SORT = "__sort";

	private Map<String, Object> parameters = null;

	private QueryParameters() {
		this.parameters = new HashMap<String, Object>();
	}

	private QueryParameters(String name, Object value) {
		this.parameters = new HashMap<String, Object>();
		this.parameters.put(name, value);
	}

	public static QueryParameters with() {
		return new QueryParameters();
	}

	public static QueryParameters with(String name, Object value) {
		return new QueryParameters(name, value);
	}

	public static QueryParameters empty() {
		return new QueryParameters();
	}

	public QueryParameters and(String name, Object value) {
		this.parameters.put(name, value);
		return this;
	}

	public QueryParameters limit(Limit limit) {
		if (null != limit && limit.isValid()) {
			return limit(limit.getStart(), limit.getN());
		}
		return this;
	}

	public QueryParameters limit(long start, int n) {
		this.parameters.put(LIMIT_START, start);
		this.parameters.put(LIMIT_N, n);
		return this;
	}

	public QueryParameters limit(int n) {
		return limit(0, n);
	}

	/**
	 * a simple version, support only one field.
	 */
	public QueryParameters orderby(OrderBy orderby) {
		if (null != orderby && orderby.isValid()) {
			this.parameters.put(ORDERBY, orderby.getOrderbyField());
			this.parameters.put(ORDERBY_SORT, orderby.getOrderbySort());
		}
		return this;
	}

	public Map<String, Object> parameters() {
		return this.parameters;
	}
}
