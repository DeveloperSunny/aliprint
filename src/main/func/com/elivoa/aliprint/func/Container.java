package com.elivoa.aliprint.func;

/**
 * Container
 * 
 * Object Container
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Jan 4, 2012]
 * @version 1.0
 */
public class Container<T> {
	private T object;

	public static <T> Container<T> create() {
		return new Container<T>();
	}

	public static <T> Container<T> create(T object) {
		Container<T> container = new Container<T>();
		container.set(object);
		return container;
	}

	public boolean set(T object) {
		this.object = object;
		return true;
	}

	public T get() {
		return object;
	}

}
