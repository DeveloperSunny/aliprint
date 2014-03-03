package com.elivoa.aliprint.func.redis;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class RedisHelper {

	public static Set<Long> convertToLongSet(Set<String> sets) {
		Set<Long> longSet = Sets.newLinkedHashSet();
		try {
			for (String value : sets) {
				longSet.add(Long.parseLong(value));
			}
		} catch (NumberFormatException e) {
			System.out.println("Fatal Error: parse long exception.");
			throw e;
			// if (logger.isErrorEnabled()) {
			// logger.error("Fatal Error: parse long exception.", e);
			// }
			// e.printStackTrace();
		}
		return longSet;
	}

	public static List<Long> convertToLongList(Collection<String> sets) {
		List<Long> longList = Lists.newArrayList();
		try {
			for (String value : sets) {
				longList.add(Long.parseLong(value));
			}
		} catch (NumberFormatException e) {
			System.out.println("Fatal Error: parse long exception.");
			throw e;
			// if (logger.isErrorEnabled()) {
			// logger.error("Fatal Error: parse long exception.", e);
			// }
			// e.printStackTrace();
		}
		return longList;
	}

}
