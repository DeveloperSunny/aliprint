package com.elivoa.aliprint.data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.elivoa.aliprint.entity.AliOldResult;
import com.google.common.collect.Lists;

public class APIResponse {
	public LinkedHashMap<String, Object> data;

	public static APIResponse warp(Object result) {
		return new APIResponse(result);
	}

	@SuppressWarnings("unchecked")
	public APIResponse(Object result) {
		if (null == result) {
			data = new LinkedHashMap<String, Object>();
		} else {
			if (result instanceof LinkedHashMap) {
				this.data = (LinkedHashMap<String, Object>) result;
			}
		}
	}

	public AliOldResult toOrderList() {
		return new AliOldResult(this);
	}

	public APIResponse getResp(String key) {
		Object obj = data.get(key);
		if (null == obj) {
			return null;
		}
		if (obj instanceof LinkedHashMap) {
			return new APIResponse(obj);
		} else {
			throw new RuntimeException("object not in type LinkedHashMap");
		}
	}

	@SuppressWarnings("rawtypes")
	public List<APIResponse> getRespList(String key) {
		Object obj = data.get(key);
		if (null == obj) {
			return null;
		}
		if (obj instanceof List) {
			List list = (List) obj;
			List<APIResponse> resps = Lists.newArrayList();
			for (Object item : list) {
				APIResponse newresp = new APIResponse(item);
				resps.add(newresp);
			}
			return resps;
		} else {
			throw new RuntimeException("object not in type LinkedHashMap");
		}
	}

	@SuppressWarnings("rawtypes")
	public List<String> getStringList(String key) {
		Object obj = data.get(key);
		if (null == obj) {
			return null;
		}
		if (obj instanceof List) {
			List list = (List) obj;
			List<String> resps = Lists.newArrayList();
			for (Object item : list) {
				resps.add((String) item);
			}
			return resps;
		} else {
			throw new RuntimeException("object not in type String");
		}
	}

	public int getInt(String key) {
		Integer i = getInteger(key);
		return null == i ? 0 : i;
	}

	public Integer getInteger(String key) {
		Object obj = this.data.get(key);
		if (null != obj && obj instanceof Integer) {
			return (Integer) obj;
		}
		return null;
	}

	public boolean getBoolean(String key) {
		Object obj = this.data.get(key);
		if (null != obj && obj instanceof Boolean) {
			return (Boolean) obj;
		}
		return false;
	}

	// buggy-ocean 会把Long中比较小的数字转换成Integer返回回来，这里处理一下，需要判断如果是Integer，转换成long返回。二逼的欧森。
	public long getLong(String key) {
		Object obj = this.data.get(key);
		if (null == obj) {
			return 0;
		}
		if (obj instanceof Integer) {
			return ((Integer) obj).longValue();
		} else if (obj instanceof Long) {
			return (Long) obj;
		}
		return 0;
	}

	public String getString(String key) {
		return getValue(key, String.class);
	}

	public Timestamp getTimestamp(String key) {
		return getValue(key, Timestamp.class);
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(String key, Class<T> clazz) {
		Object obj = this.data.get(key);
		if (null != obj) {
			return (T) obj;
		}
		return null;
	}

	public double getDouble(String key) {
		Double d = getValue(key, Double.class);
		if (null == d) {
			return 0;
		}
		return d;
	}

	private static SimpleDateFormat orderTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static SimpleDateFormat newTimeFormat = new SimpleDateFormat("yyyyMMddhhmmssSSSZZZZ");

	public Timestamp parseTime(String key) {
		String timestring = this.getString(key);
		if (null == timestring) {
			return null;
		}
		try {
			Date date = orderTimeFormat.parse(timestring);
			if (null != date) {
				return new Timestamp(date.getTime());
			}
		} catch (ParseException e) {
			// second round
			try {
				Date date = newTimeFormat.parse(timestring);
				if (null != date) {
					return new Timestamp(date.getTime());
				}
			} catch (ParseException ex) {
				// second round
				ex.printStackTrace();
			}
		}
		return null;
	}

	public double parseDouble(String key) {
		String doubleString = getString(key);
		if (null == doubleString || "null".equalsIgnoreCase(doubleString)) {
			return 0;
		}
		try {
			return Double.parseDouble(doubleString);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
