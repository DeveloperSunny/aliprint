package com.elivoa.aliprint.func.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @desc - CountableMap
 * 
 *       From SortableMap.java
 * 
 * @author gb<elivoa@gmail.com> @date Mar 26, 2009 @version 0.1.0.0
 * 
 * @Modified By gb, @Date Mar 26, 2009 -
 * 
 * @param T
 *            key Type
 */
public class CountableMap<T> {
	boolean output = true;

	private List<Pair<T, Integer>> data = new ArrayList<Pair<T, Integer>>();
	private Map<T, Integer> map = new HashMap<T, Integer>();

	public void putIfAbsence(T key, Integer value) {
		if (!map.containsKey(key)) {
			map.put(key, data.size());
			data.add(new Pair<T, Integer>(key, value));
		}
	}

	public Pair<T, Integer> get(T key) {
		return map.containsKey(key) ? data.get(map.get(key)) : null;
	}

	public Integer getCount(T key) {
		Pair<T, Integer> pair = get(key);
		return (null == pair) ? null : pair.getValue();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public int inc(T key) {
		return inc(key, 1);
	}

	public void inc(T[] keys) {
		if (null != keys) {
			for (T key : keys) {
				inc(key);
			}
		}
	}

	public void inc(Collection<T> keys) {
		if (null != keys) {
			for (T key : keys) {
				inc(key);
			}
		}
	}

	public int inc(T key, Integer step) {
		Pair<T, Integer> pair = get(key);
		if (null == pair) {
			pair = new Pair<T, Integer>(key, step);
			map.put(key, data.size());
			data.add(pair);
			return step;
		} else {
			Integer oldValue = pair.getValue();
			pair.setValue(oldValue + step);
			return oldValue + step;
		}
	}

	public Integer put(T key, Integer value) {
		Pair<T, Integer> pair = get(key);
		if (null == pair) {
			pair = new Pair<T, Integer>(key, value);
			map.put(key, data.size());
			data.add(pair);
			return null;
		} else {
			Integer oldValue = pair.getValue();
			pair.setValue(value);
			return oldValue;
		}
	}

	/*
	 * Not implemented.
	 */
	public Set<T> keySet() {
		return map.keySet();
	}

	public List<Pair<T, Integer>> toSortedList() {
		Collections.sort(data, Collections.reverseOrder(new IntegerComparator()));
		return data;
	}

	public List<Pair<T, Integer>> toSortedList(Comparator<Pair<T, Integer>> cmp) {
		Collections.sort(data, cmp);
		List<Pair<T, Integer>> list = data;
		this.data = null;
		return list;
	}

	public List<Pair<T, Integer>> toCopiedSortedList() {
		return toCopiedSortedList(Collections.reverseOrder(new IntegerComparator()));
	}

	public List<Pair<T, Integer>> toCopiedSortedList(Comparator<Pair<T, Integer>> cmp) {
		List<Pair<T, Integer>> dataTemp = new ArrayList<Pair<T, Integer>>(data.size());
		dataTemp.addAll(data);
		Collections.sort(dataTemp, cmp);
		return dataTemp;
	}

	public void clear() {
		map.clear();
		data.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public int size() {
		return data.size();
	}

	public Collection<Pair<T, Integer>> values() {
		return data;
	}

	public class IntegerComparator implements Comparator<Pair<T, Integer>> {
		public int compare(Pair<T, Integer> o1, Pair<T, Integer> o2) {
			if (o1 == o2) {
				return 0;
			}
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			return o1.getValue() - o2.getValue();
		}
	}

}
