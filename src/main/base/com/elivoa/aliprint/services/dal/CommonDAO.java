package com.elivoa.aliprint.services.dal;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.jpa.annotations.CommitAfter;
import org.iminer.func.Callback;
import org.perf4j.aop.Profiled;

import com.alibaba.aliprint.dev.annotation.ProfileAll;

@ProfileAll
public interface CommonDAO {

	/**
	 * Creates a new object for the given type. After a call to this method the
	 * entity will be persisted into database and then refreshed. Also current
	 * persistent Session will be flushed.
	 */
	@Profiled(tag = "CommonDAO.{$method}", message = "{$0}")
	@CommitAfter
	<T> T create(T t);

	/**
	 * Updates the given object
	 */
	@Profiled(tag = "CommonDAO.{$method}", message = "{$0}")
	@CommitAfter
	<T> T update(T t);

	/**
	 * Deletes the given object by id
	 */
	@Profiled(tag = "CommonDAO.{$method}", message = "{$0, $1}")
	@CommitAfter
	<T, PK extends Serializable> void delete(Class<T> type, PK id);

	@Profiled(tag = "CommonDAO.{$method}", message = "{$0}")
	@CommitAfter
	Long delete(Class<?> type);
	
	/**
	 * Finds an object by id
	 */
	@Profiled(tag = "CommonDAO.{$method}", message = "{$0, $1}")
	<T, PK extends Serializable> T find(Class<T> type, PK id);

	/**
	 * Finds a list of objects for the given query name
	 */
	@Profiled(tag = "CommonDAO.{$method}:{$0}", message = "params:{$return.size()}")
	<T> List<T> findWithNamedQuery(String queryName);

	/**
	 * Find a query with parameters
	 */
	@Profiled(tag = "CommonDAO.{$method}:{$0}", message = "params:{$1};{$return.size()}")
	<T> List<T> findWithNamedQuery(String queryName, Map<String, Object> params);

	/**
	 * Returns one result, query without parameters
	 */
	@Profiled(tag = "CommonDAO.{$method}:{$0}", message = "")
	<T> T findUniqueWithNamedQuery(String queryName);

	/**
	 * Returns just one result with a named query and parameters
	 */
	@Profiled(tag = "CommonDAO.{$method}:{$0}", message = "params:{$1}")
	<T> T findUniqueWithNamedQuery(String queryName, Map<String, Object> params);

	/**
	 * Total: Find count(*) from table
	 */
	@Profiled(tag = "CommonDAO.{$method}", message = "{$0}")
	Long total(Class<?> type);

	Long totalWithNamedQuery(String queryName, Map<String, Object> params);

	Long totalWithNamedQuery(String queryName);

	<T> T maxId(Class<?> type);

	/*
	 * Update
	 */
	@Profiled(tag = "CommonDAO.{$method}:{$0}", message = "")
	@CommitAfter
	<T> int updateWithNamedQuery(String queryName);

	@Profiled(tag = "CommonDAO.{$method}:{$0}", message = "params:{$1}")
	@CommitAfter
	<T> int updateWithNamedQuery(String queryName, Map<String, Object> params);

	/**
	 * Batch fetch, use select T from Table in ...
	 */
	@Profiled(tag = "CommonDAO.{$method}", message = "{$0, $1}")
	<T, PK extends Serializable> List<T> batchFind(Class<T> type, Collection<PK> ids);

	@Profiled(tag = "CommonDAO.{$method}", message = "{$0, $1}")
	<T, KEY extends Serializable> List<T> batchFindWithField(Class<T> type, Collection<KEY> ids, String fieldName);

	@Deprecated
	@Profiled(tag = "CommonDAO.{$method}", message = "{$0, $1}")
	<T, PK extends Serializable> Map<PK, T> batchFindReturnMap(Class<T> type, Collection<PK> ids);

	Map<String, Long> groupMapWithNamedQuery(String queryName, Map<String, Object> params);

	Map<String, Long> groupMapWithNamedQuery(String queryName);

	List<String> groupListWithNamedQuery(String queryName, Map<String, Object> params);

	List<String> groupListWithNamedQuery(String queryName);

	/*
	 * Walk
	 */
	<T extends Serializable> void Walk(Class<T> type, Callback<T, Integer> callback);

}
