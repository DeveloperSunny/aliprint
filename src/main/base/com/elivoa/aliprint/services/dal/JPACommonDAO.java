package com.elivoa.aliprint.services.dal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.iminer.common.dal.QueryParameters;
import org.iminer.func.Callback;
import org.slf4j.Logger;

import com.google.common.collect.Maps;

public class JPACommonDAO implements CommonDAO {

	public <T> T create(T t) {
		entityManager.persist(t);
		entityManager.flush();
		entityManager.refresh(t);
		return t;
	}

	public <T> T update(T type) {
		entityManager.merge(type);
		return type;
	}

	public <T, PK extends Serializable> void delete(Class<T> type, PK id) {
		T ref = (T) entityManager.find(type, id);
		entityManager.remove(ref);
	}

	public <T, PK extends Serializable> T find(Class<T> type, PK id) {
		return (T) entityManager.find(type, id);
	}

	public <T> T findUniqueWithNamedQuery(String queryName) {
		return getSingleResultAllowNull(entityManager.createNamedQuery(queryName));
	}

	public <T> T findUniqueWithNamedQuery(String queryName, Map<String, Object> params) {
		Set<Entry<String, Object>> rawParameters = params.entrySet();
		Query query = entityManager.createNamedQuery(queryName);

		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());

		}
		return getSingleResultAllowNull(query);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findWithNamedQuery(String queryName) {
		return entityManager.createNamedQuery(queryName).getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findWithNamedQuery(String queryName, Map<String, Object> params) {
		Set<Entry<String, Object>> rawParameters = params.entrySet();
		Query query = entityManager.createNamedQuery(queryName);

		// if has limit.
		if (params.containsKey(QueryParameters.LIMIT_START)) {
			int start = new Integer(params.get(QueryParameters.LIMIT_START).toString());
			int n = (Integer) params.get(QueryParameters.LIMIT_N);
			params.remove(QueryParameters.LIMIT_START);
			params.remove(QueryParameters.LIMIT_N);
			query.setFirstResult(start);
			query.setMaxResults(n);
		}

		// if has order by.
		if (params.containsKey(QueryParameters.ORDERBY)) {
			String orderbyField = (String) params.get(QueryParameters.ORDERBY);
			params.remove(QueryParameters.ORDERBY);
			query.setParameter("orderby", String.format("%s", orderbyField));
			if (params.containsKey(QueryParameters.ORDERBY_SORT)) {
				params.remove(QueryParameters.ORDERBY_SORT);
			}
		}

		// Add parameters
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.getResultList();
	}

	public Long total(Class<?> type) {
		Query query = entityManager.createQuery(String.format("SELECT COUNT(e) FROM %s e", type.getSimpleName()));
		Long total = getSingleResultAllowNull(query);
		return total;
	}

	public Long delete(Class<?> type) {
		Query query = entityManager.createQuery(String.format("DELETE FROM %s e", type.getSimpleName()));
		return (long) query.executeUpdate();
	}
	
	public Long totalWithNamedQuery(String queryName, Map<String, Object> params) {
		Set<Entry<String, Object>> rawParameters = params.entrySet();
		Query query = entityManager.createNamedQuery(queryName);

		// if has limit.
		if (params.containsKey(QueryParameters.LIMIT_START)) {
			int start = new Integer(params.get(QueryParameters.LIMIT_START).toString());
			int n = (Integer) params.get(QueryParameters.LIMIT_N);
			params.remove(QueryParameters.LIMIT_START);
			params.remove(QueryParameters.LIMIT_N);
			query.setFirstResult(start);
			query.setMaxResults(n);
		}

		// Add parameters
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return getSingleResultAllowNull(query);
	}

	public Long totalWithNamedQuery(String queryName) {
		Query query = entityManager.createNamedQuery(queryName);
		return getSingleResultAllowNull(query);
	}

	/*
	 * Get Next ID of a table
	 */
	public <T> T maxId(Class<?> type) {
		Query query = entityManager.createQuery(String.format("SELECT max(e.id) FROM %s e", type.getSimpleName()));
		T total = getSingleResultAllowNull(query);
		return total;
	}

	public List<String> groupListWithNamedQuery(String queryName, Map<String, Object> params) {
		Set<Entry<String, Object>> rawParameters = params.entrySet();
		Query query = entityManager.createNamedQuery(queryName);

		// if has limit.
		if (params.containsKey(QueryParameters.LIMIT_START)) {
			int start = new Integer(params.get(QueryParameters.LIMIT_START).toString());
			int n = (Integer) params.get(QueryParameters.LIMIT_N);
			params.remove(QueryParameters.LIMIT_START);
			params.remove(QueryParameters.LIMIT_N);
			query.setFirstResult(start);
			query.setMaxResults(n);
		}

		// Add parameters
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return getGroupbyListResults(query);
	}

	public Map<String, Long> groupMapWithNamedQuery(String queryName, Map<String, Object> params) {
		Set<Entry<String, Object>> rawParameters = params.entrySet();
		Query query = entityManager.createNamedQuery(queryName);

		// if has limit.
		if (params.containsKey(QueryParameters.LIMIT_START)) {
			int start = new Integer(params.get(QueryParameters.LIMIT_START).toString());
			int n = (Integer) params.get(QueryParameters.LIMIT_N);
			params.remove(QueryParameters.LIMIT_START);
			params.remove(QueryParameters.LIMIT_N);
			query.setFirstResult(start);
			query.setMaxResults(n);
		}

		// Add parameters
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return getGroupbyMapResults(query);
	}

	public Map<String, Long> groupMapWithNamedQuery(String queryName) {
		Query query = entityManager.createNamedQuery(queryName);
		return getGroupbyMapResults(query);
	}

	public List<String> groupListWithNamedQuery(String queryName) {
		Query query = entityManager.createNamedQuery(queryName);
		return getGroupbyListResults(query);
	}

	/**
	 * 从Query中读出单一返回值，通常用于SELECT COUNT(1)
	 * 
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSingleResultAllowNull(Query query) {
		query.setMaxResults(1);
		List<?> list = query.getResultList();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (T) list.get(0);
	}

	/**
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Long> getGroupbyMapResults(Query query) {
		List<Object[]> tuples = query.getResultList();
		Map<String, Long> results = new HashMap<String, Long>();
		for (Object[] tuple : tuples) {
			String name = tuple[0].toString();
			Long value = Long.parseLong(tuple[1].toString());
			results.put(name, value);
		}
		return results;
	}

	/**
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getGroupbyListResults(Query query) {
		List<Object[]> tuples = query.getResultList();
		List<String> results = new ArrayList<String>();
		for (Object[] tuple : tuples) {
			results.add(tuple[0].toString());
		}
		return results;
	}

	@Override
	public <T, PK extends Serializable> List<T> batchFind(Class<T> type, Collection<PK> ids) {
		return _batchFind(type, ids);
	}

	@Override
	public <T, KEY extends Serializable> List<T> batchFindWithField(Class<T> type, Collection<KEY> ids,
			String fieldName) {
		return _batchFindWithFields(type, ids, fieldName);
	}

	/**
	 * @deprecated
	 */
	@Override
	public <T, PK extends Serializable> Map<PK, T> batchFindReturnMap(Class<T> type, Collection<PK> ids) {
		List<T> list = _batchFind(type, ids);
		if (null != list) {
			Map<PK, T> map = Maps.newHashMap();
			for (T t : list) {
				// map.put(t.null, null)
				// TODO I can't continue...
			}
		}
		return null;
	}

	private <T, PK extends Serializable> List<T> _batchFind(Class<T> type, Collection<PK> ids) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(type);

		Root<T> root = cq.from(type);
		cq.select(root);
		cq.where(root.get("id").in(ids));

		TypedQuery<T> q = entityManager.createQuery(cq);
		List<T> results = q.getResultList();
		return results;
	}

	private <T, KEY extends Serializable> List<T> _batchFindWithFields(Class<T> type, Collection<KEY> ids,
			String fieldName) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(type);

		Root<T> root = cq.from(type);
		cq.select(root);
		cq.where(root.get(fieldName).in(ids));

		TypedQuery<T> q = entityManager.createQuery(cq);
		List<T> results = q.getResultList();
		return results;
	}

	@Override
	public <T> int updateWithNamedQuery(String queryName) {
		return this.updateWithNamedQuery(queryName, null);
	}

	@Override
	public <T> int updateWithNamedQuery(String queryName, Map<String, Object> params) {
		Query query = entityManager.createNamedQuery(queryName);

		// Add parameters
		if (null != params) {
			Set<Entry<String, Object>> rawParameters = params.entrySet();
			for (Entry<String, Object> entry : rawParameters) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}

		return query.executeUpdate();
	}

	/*
	 * Important! must return id in callback.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Serializable> void Walk(Class<T> type, Callback<T, Integer> callback) {
		int limit = 365;
		int count = 0;
		int nStartId = -1;

		List<T> models = null;
		while (true) {
			StringBuilder sb = new StringBuilder();
			sb.append("select m from ").append(type.getSimpleName()).append(" m");
			sb.append(" where m.id > :id order by m.id asc");
			Query query = entityManager.createQuery(sb.toString());
			query.setMaxResults(limit);// safe option.
			query.setParameter("id", nStartId);
			models = query.getResultList();

			if (null != models && models.size() > 0) {
				for (T model : models) {
					nStartId = callback.execute(model);

					// progress reporter
					if (count++ % 10000 == 0) {
						if (logger.isInfoEnabled()) {
							logger.info(String.format("walk %s, %d", type.getSimpleName(), count));
						}
					}

				}
			} else {
				break;
			}
		}
		models = null;
	}

	/*
	 * services
	 */
	@Inject
	EntityManager entityManager;

	@Inject
	Logger logger;
}
