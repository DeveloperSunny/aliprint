package com.elivoa.aliprint.components.order;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.data.OrderStatus;
import com.elivoa.aliprint.entity.AliOrder;
import com.elivoa.aliprint.entity.AliOrderEntity;
import com.elivoa.aliprint.entity.AliResult;
import com.elivoa.aliprint.services.AuthService;

public class OrderList {

	@Property
	AliResult orders;

	// loop temp variables;
	@Property
	AliOrder _order;

	@Property
	AliOrderEntity _entity;

	@Property
	Integer _index;

	@Property
	String _specName;

	Object setupRender() {
		token.getMemberId();
		orders = sdk.listOrders(token, OrderStatus.waitbuyerpay, 20, 1, null);
		if (orders == null) {
			return false;
		}
		return true;
	}

	public String getSpecValue() {
		return this._entity.getSpecInfo().get(_specName);
	}

	@Inject
	AuthService sdk;

	@SessionState
	AliToken token;
}
