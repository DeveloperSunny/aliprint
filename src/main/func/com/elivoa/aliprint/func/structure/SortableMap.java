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
 * @Name SortableMap.java
 * @Desc ...
 * @StartDate 2008/10/26
 * @author vivo <mailto:elivoa@gmail.com>
 * @version 0.01
 * 
 * @ChangeHistory <ul>
 *                <li>Dec 9, 2008 vivo, Create.</li>
 *                <li>Dec 9, 2008 vivo, Improve.</li>
 *                </ul>
 * 
 * @Modified By gb, @Date Mar 26, 2009 -
 * 
 * @param <K>
 * @param <T>
 */
public class SortableMap<K, T> implements Map<K, T> {
	boolean output = true;

	private List<T> data = new ArrayList<T>();
	private Map<K, Integer> map = new HashMap<K, Integer>();

	public void pubIfAbsence(K key, T value) {
		if (!map.containsKey(key)) {
			map.put(key, data.size());
			data.add(value);
		}
	}

	public T get(Object key) {
		if (map.containsKey(key)) {
			return data.get(map.get(key));
		}
		return null;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public T put(K key, T value) {
		if (!map.containsKey(key)) {
			map.put(key, data.size());
			data.add(value);
			return null;
		} else {
			T oldValue = get(key);
			data.set(map.get(key), value);
			return oldValue;
		}
	}

	/*
	 * Not implemented.
	 */
	public Set<K> keySet() {
		return map.keySet();
	}

	public List<T> toSortedList(Comparator<T> cmp) {
		Collections.sort(data, cmp);
		List<T> list = data;
		this.data = null;
		return list;
	}

	public List<T> toCopiedSortedList(Comparator<T> cmp) {
		List<T> dataTemp = new ArrayList<T>(data.size());
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

	public boolean containsValue(Object value) {
		return data.contains(value);
	}

	public int size() {
		return data.size();
	}

	public Collection<T> values() {
		return data;
	}

	/**
	 * @deprecated
	 */
	public Set<java.util.Map.Entry<K, T>> entrySet() {
		return null;
	}

	/**
	 * @deprecated
	 */
	public void putAll(Map<? extends K, ? extends T> t) {

	}

	/**
	 * @deprecated
	 */
	public T remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

}
