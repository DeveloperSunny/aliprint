package com.elivoa.aliprint.func.structure;

public class IntIntPair implements Comparable<IntIntPair> {
	private int key;
	private int value;

	public IntIntPair(int key, int value) {
		super();
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String toString() {
		String str;
		StringBuffer sb = new StringBuffer();

		str = String.valueOf(key);
		sb.append(str);
		sb.append("-");
		str = String.valueOf(value);
		sb.append(str);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		StringBuilder sb = new StringBuilder();
		sb.append(key).append("-").append(value);
		return sb.toString().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		IntIntPair pair = (IntIntPair) o;
		if (this.key == pair.key && this.value == pair.value) {
			return true;
		} else {
			return false;
		}
	}

	public int compareTo(IntIntPair o) {
		if (null == o)
			return -1;
		return this.value - o.value;
	}
	
}
