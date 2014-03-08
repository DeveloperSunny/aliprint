package com.elivoa.aliprint.pages.product;

import java.sql.SQLException;

import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;

import com.elivoa.aliprint.dal.ProductDao;
import com.elivoa.aliprint.entity.ProductAlias;

public class ProductAjax {

	@ActivationRequestParameter
	long id;

	@ActivationRequestParameter
	String alias;

	public JSONObject onSaveAlias() {
		ProductAlias model = new ProductAlias();
		model.setId(id);// URLDecoder.decode(alias)
		model.setAlias(alias);
		JSONObject json = new JSONObject();
		try {
			productDao.saveAlias(model);
			json.append("success", "true");
		} catch (SQLException e) {
			e.printStackTrace();
			json.append("success", "false");
		}
		return json;
	}

	public JSONObject onSaveAlias(long id, String alias) {
		ProductAlias model = new ProductAlias();
		model.setId(id);// URLDecoder.decode(alias)
		model.setAlias(alias);
		JSONObject json = new JSONObject();
		try {
			productDao.saveAlias(model);
			json.append("success", "true");
		} catch (SQLException e) {
			e.printStackTrace();
			json.append("success", "false");
		}
		return json;
	}

	@Inject
	ProductDao productDao;

	@Inject
	Request request;
}
