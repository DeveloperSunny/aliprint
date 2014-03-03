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
 *       From SortableMap.java Improved.
 * 
 * @author gb<elivoa@gmail.com> @date Mar 26, 2009 @version 0.2.0.0
 * 
 * @Modified By gb, @Date Mar 26, 2009 -
 * 
 * @param K1
 *            - Key Type in map.
 * @param K2
 *            - Data key type.
 */
public class CountableMapv2<K1, K2> {
	boolean output = true;

	private List<Pair<K2, Long>> data = new ArrayList<Pair<K2, Long>>();

	private Map<K1, Integer> map = new HashMap<K1, Integer>();

	public void putIfAbsence(K1 key, K2 dataKey, long value) {
		if (!map.containsKey(key)) {
			map.put(key, data.size());
			data.add(new Pair<K2, Long>(dataKey, value));
		}
	}

	public Pair<K2, Long> get(K1 key) {
		return map.containsKey(key) ? data.get(map.get(key)) : null;
	}

	public Long getCount(K1 key) {
		Pair<K2, Long> pair = get(key);
		return (null == pair) ? null : pair.getValue();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public long inc(K1 key, K2 dataKey) {
		return inc(key, dataKey, 1);
	}

	public long inc(K1 key, K2 dataKey, long step) {
		Pair<K2, Long> pair = get(key);
		if (null == pair) {
			pair = new Pair<K2, Long>(dataKey, step);
			map.put(key, data.size());
			data.add(pair);
			return step;
		} else {
			Long oldValue = pair.getValue();
			pair.setValue(oldValue + step);
			return oldValue + step;
		}
	}

	public Long put(K1 key, K2 dataKey, long value) {
		Pair<K2, Long> pair = get(key);
		if (null == pair) {
			pair = new Pair<K2, Long>(dataKey, value);
			map.put(key, data.size());
			data.add(pair);
			return null;
		} else {
			Long oldValue = pair.getValue();
			pair.setValue(value);
			return oldValue;
		}
	}

	public Set<K1> keySet() {
		return map.keySet();
	}

	public List<Pair<K2, Long>> toSortedList() {
		Collections.sort(data, Collections.reverseOrder(new LongComparator()));
		return data;
	}

	public List<Pair<K2, Long>> toSortedList(Comparator<Pair<K2, Long>> cmp) {
		Collections.sort(data, cmp);
		List<Pair<K2, Long>> list = data;
		this.data = null;
		return list;
	}

	public List<Pair<K2, Long>> toCopiedSortedList() {
		return toCopiedSortedList(Collections.reverseOrder(new LongComparator()));
	}

	public List<Pair<K2, Long>> toCopiedSortedList(Comparator<Pair<K2, Long>> cmp) {
		List<Pair<K2, Long>> dataTemp = new ArrayList<Pair<K2, Long>>(data.size());
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

	public Collection<Pair<K2, Long>> values() {
		return data;
	}

	public class IntegerComparator implements Comparator<Pair<K1, Integer>> {
		public int compare(Pair<K1, Integer> o1, Pair<K1, Integer> o2) {
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

	public class LongComparator implements Comparator<Pair<K2, Long>> {
		public int compare(Pair<K2, Long> o1, Pair<K2, Long> o2) {
			if (o1 == o2) {
				return 0;
			}
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			long ss = o1.getValue() - o2.getValue();
			if (ss == 0) {
				return 0;
			}
			return ss > 0 ? 1 : -1;
		}
	}

}
