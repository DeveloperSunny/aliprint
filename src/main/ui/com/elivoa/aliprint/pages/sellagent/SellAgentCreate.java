package com.elivoa.aliprint.pages.sellagent;

import java.sql.SQLException;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.elivoa.aliprint.dal.AgentDao;
import com.elivoa.aliprint.entity.SellAgent;

public class SellAgentCreate {

	@Property
	SellAgent agent;

	private Integer id;

	@Property
	Boolean edit;

	void onActivate(int id) {
		this.id = id;
	}

	void setupRender() throws SQLException {
		if (null != this.id) {
			this.agent = dao.getAgent(id);
			this.edit = true;
		} else {
			this.agent = new SellAgent();
		}
	}

	Object onSuccess() throws SQLException {
		dao.saveupdates(this.agent);
		return SellAgentIndex.class;
	}

	@Inject
	AgentDao dao;
}
