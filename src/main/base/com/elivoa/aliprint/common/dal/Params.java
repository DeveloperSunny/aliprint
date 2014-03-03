package com.elivoa.aliprint.common.dal;

import java.util.HashMap;
import java.util.Map;

/**
 * WhereParameters
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Dec 3, 2011]
 * 
 * @deprecated Not finished.
 */
public class Params {

	public static final int EQ = 1;
	public static final int GT = 2;
	public static final int LT = 3;

	private Map<String, WhereParam> parameters = null;

	private Params(String name, Class<?> type, Object value) {
		this.parameters = new HashMap<String, WhereParam>();
		this.parameters.put(name, null);
	}

	public static Params with(String name, Class<?> type, Object value) {
		return new Params(name, type, value);
	}

	public Params and(String name, Object value) {
		// this.parameters.put(name, value);
		return this;
	}

	public Map<String, Object> parameters() {
		// return this.parameters;
		return null;
	}

	public class WhereParam {
		public int type;
		public String name;
		public Class<?> valueType;
		public Object value;

		public WhereParam(int type, String name, Class<?> valueType, Object value) {
			super();
			this.type = type;
			this.name = name;
			this.valueType = valueType;
			this.value = value;
		}
	}
}
