package com.elivoa.aliprint.components.sellagent;

import java.sql.SQLException;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.elivoa.aliprint.dal.AgentDao;
import com.elivoa.aliprint.entity.SellAgent;

public class SellAgentList {

	@Property
	List<SellAgent> agents;

	@Property
	SellAgent _agent;

	void setupRender() {
		try {
			List<SellAgent> agents = dao.listAgents();
			this.agents = agents;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Inject
	AgentDao dao;
}
