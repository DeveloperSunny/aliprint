package com.elivoa.aliprint.func.structure;

public class IntFloatPair implements Comparable {

	private int key;
	private float value;

	public IntFloatPair(int key, float value) {
		super();
		this.key = key;
		this.value = value;
	}

	public int compareTo(Object _object) {
		IntFloatPair bean = (IntFloatPair) _object;
		if (bean.value < this.value) {
			return 1;
		} else if (bean.value > this.value) {
			return -1;
		} else {
			return 0;
		}
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

}
