package com.elivoa.aliprint.services;

import java.io.Serializable;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.elivoa.aliprint.common.dal.Limit;
import com.elivoa.aliprint.common.dal.OrderBy;
import com.elivoa.aliprint.common.dal.QueryParameters;
import com.elivoa.aliprint.common.exception.EntityNotFoundException;
import com.elivoa.aliprint.entities.BaseEntity;
import com.elivoa.aliprint.func.datetime.DateUtil;
import com.elivoa.aliprint.services.dal.CommonDAO;

public abstract class BaseService<T extends BaseEntity> {

	@Inject
	protected CommonDAO dao;

	public void update(T t) {
		t.setUpdateTime(DateUtil.getCurrentTimestamp());
		dao.update(t);
	}

	public void delete(T t) {
		dao.delete(t.getClass(), t.getId());
	}

	public <PK extends Serializable> T find(Class<T> type, PK id) throws EntityNotFoundException {
		T t = dao.find(type, id);
		if (t == null) {
			throw new EntityNotFoundException(type, 0L);
		}
		return t;
	}

	public List<T> list(Class<T> type, Limit limit, OrderBy orderby) {
		String queryName = type.getSimpleName() + ".List";
		return dao.findWithNamedQuery(queryName, QueryParameters.empty().limit(limit).orderby(orderby).parameters());
	}

	public Long total(Class<T> type) {
		return dao.total(type);
	}

}
