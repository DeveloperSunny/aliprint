package com.elivoa.aliprint.func.structure;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @desc - Pair
 * 
 *       Default comparator <int,double> desc.
 * 
 * @author gb [elivoa AT gmail.com] @date Mar 21, 2009 @version 0.1.0
 * @modify gb [elivoa AT gmail.com] @date Mar 21, 2009 @version 0.1.0
 * @modify gb [elivoa AT gmail.com] @date Sep 11, 2009 @version 0.1.3
 * 
 * @param <K>
 *            key
 * @param <V>
 *            value
 * 
 */
public class Pair<K, V> implements Comparable<Pair<K, V>>, Serializable {

	private static final long serialVersionUID = 8997397749477919171L;

	/** Key */
	protected K key;

	/** Value */
	protected V value;

	/** Type */
	protected String type;

	public Pair() {
		// empty constructor
	}

	public Pair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public Pair(K key, V value, String type) {
		super();
		this.key = key;
		this.value = value;
		this.type = type;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (null != obj && obj instanceof Pair) {
			if (this == obj) {
				return true;
			}
			Pair<K, V> pair = (Pair<K, V>) obj;
			if (this.key.equals(pair.getKey()) && this.value.equals(pair.getValue())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(key.toString()).append(",").append(value.toString()).append("]");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return key.hashCode() ^ value.hashCode();
	}

	/**
	 * This default compareTo method is in descending order.<BR>
	 * To Use ascending order, please use doubleComparator.
	 */
	@SuppressWarnings("unchecked")
	public int compareTo(Pair<K, V> o) {
		Pair<Integer, Double> pair = (Pair<Integer, Double>) o;
		if (pair.getValue() < (Double) this.value) {
			return -1;
		} else if (pair.getValue() > (Double) this.value) {
			return 1;
		} else {
			return 0;
		}
	}

	public static class IntStringPair extends Pair<Integer, String> {
		public IntStringPair(int key, String value) {
			super(key, value);
		}
	}

	public static PairIntegerComparator integerComparator = new PairIntegerComparator();

	private static class PairIntegerComparator implements Comparator<Pair<?, ?>> {

		public int compare(Pair<?, ?> o1, Pair<?, ?> o2) {
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			if (o1 == o2) {
				return 0;
			}
			if (null != o1.getValue() && null != o2.getValue()) {
				return (Integer) o1.getValue() - (Integer) o2.getValue();
			}
			return -1;
		}

	}

	public static PairLongComparator longComparator = new PairLongComparator();

	private static class KeyComparator implements Comparator<Pair<?, ?>> {

		public int compare(Pair<?, ?> o1, Pair<?, ?> o2) {
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			return 11;// o1.getKey()
		}

	}

	private static class PairLongComparator implements Comparator<Pair<?, ?>> {

		public int compare(Pair<?, ?> o1, Pair<?, ?> o2) {
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			if (null != o1.getValue() && null != o2.getValue()) {
				if ((Long) o1.getValue() == (Long) o2.getValue()) {
					return 0;
				} else {
					return (Long) o1.getValue() > (Long) o2.getValue() ? 1 : -1;
				}
			}
			return -1;
		}

	}

	public static PairDoubleComparator doubleComparator = new PairDoubleComparator();

	private static class PairDoubleComparator implements Comparator<Pair<?, ?>> {

		public int compare(Pair<?, ?> o1, Pair<?, ?> o2) {
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			if (null != o1.getValue() && null != o2.getValue()) {
				if ((Double) o1.getValue() == (Double) o2.getValue()) {
					return 0;
				} else {
					return (Double) o1.getValue() > (Double) o2.getValue() ? 1 : -1;
				}
			}
			return -1;
		}
	}

	/*
	 * Getter & Setters.
	 */
	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
