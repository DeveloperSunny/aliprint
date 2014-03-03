package com.elivoa.aliprint.func;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @desc - IDRange
 * 
 *       Support parse String to in arrays. for example "[0-100,200-250,300-,...]"
 * 
 * @author gb<elivoa@gmail.com> @date Feb 28, 2009 @version 0.1.0.1
 * 
 * @ModifiedDate Mar 13, 2009 [0.1.0.1] - Add some docs. And toDo "Extract to Util Project".
 * 
 * @TODO add docs to this.
 */
public class NumberRange {
	public static final Log log = LogFactory.getLog(NumberRange.class);

	private List<int[]> range = new ArrayList<int[]>();
	private String rangeString;

	public static NumberRange parse(String rangeString) {
		NumberRange inst = new NumberRange();
		inst.reset(rangeString);
		return inst;
	}

	public static NumberRange parse(List<int[]> range) {
		NumberRange inst = new NumberRange();
		inst.reset(range);
		return inst;
	}

	public void reset(String rangeString) {
		this.rangeString = rangeString;
		this.range = parseRange(rangeString);
	}

	public void reset(List<int[]> range) {
		this.range = range;
		this.rangeString = rangeToString(range);
	}

	/**
	 * @param rangesString
	 * @return
	 */
	public static List<int[]> parseRange(String rangesString) {
		List<int[]> result = new ArrayList<int[]>();
		rangesString = rangesString.trim().replaceAll("(\\[|\\])", "");
		String[] rangeStringArray = rangesString.split(",");
		if (null == rangeStringArray || rangeStringArray.length == 0) {
			throw new RuntimeException("Range not valid. Example:[0-100,200-250,300-,...]");
		}
		for (String rangeString : rangeStringArray) {
			int index = rangeString.indexOf("-");
			if (index < 1 || index > rangeString.length() - 1) {
				log.error("Sub Range Not Valid: " + rangesString);
				continue;
			}
			// add range
			int start = Integer.parseInt(rangeString.substring(0, index));
			// support [0-]
			int end = (index == rangeString.length() - 1) ? -1 : Integer.parseInt(rangeString
					.substring(index + 1, rangeString.length()));
			if (start >= 0 && ((end > 0 && end > start) || end == -1)) {
				result.add(new int[] { start, end });
			} else {
				log.error("Sub Range Not Valid: " + rangesString);
			}
		}
		return result;
	}

	public static String rangeToString(List<int[]> range) {
		if (null == range || range.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < range.size(); i++) {// int[] is : range) {
			sb.append(range.get(i)[0]).append("-").append(range.get(i)[1]);
			if (i < range.size()) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public String generateSqlCondition(String idFieldName) {
		StringBuilder sbSql = new StringBuilder("(");
		for (int i = 0; i < range.size(); i++) {
			sbSql.append("(");
			sbSql.append(idFieldName).append(">=").append(getStartId(i));
			if (getEndId(i) != -1) { // support [xxx-] but it must be the last one.
				sbSql.append(" and ").append(idFieldName).append("<=").append(getEndId(i));
			}
			sbSql.append(")");
			if (i < range.size() - 1) {
				sbSql.append(" or ");
			}
		}
		sbSql.append(")");
		return sbSql.toString();
	}

	/*
	 * Getter and setter
	 */
	public List<int[]> getRange() {
		return range;
	}

	public String getRangeString() {
		return rangeString;
	}

	public int size() {
		return range.size();
	}

	public int getStartId(int i) {
		return range.get(i)[0];
	}

	public int getEndId(int i) {
		return range.get(i)[1];
	}

	public int getExtendValiue(int i, int idx) {
		return range.get(i)[idx];
	}

	public void add(int[] aRange) {
		range.add(aRange);
		// TODO is bad.
		this.rangeString = getRangeString();
	}

	public boolean isEmpty() {
		return range.isEmpty();
	}

}
