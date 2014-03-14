package com.elivoa.aliprint.components.order;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.elivoa.aliprint.alisdk.AliToken;
import com.elivoa.aliprint.data.OrderStatus;
import com.elivoa.aliprint.data.Params;
import com.elivoa.aliprint.entity.AliOrder;
import com.elivoa.aliprint.entity.AliOrderEntity;
import com.elivoa.aliprint.entity.AliResult;
import com.elivoa.aliprint.services.AuthService;

public class OrderList {

	@Property
	AliResult<AliOrder> orders;

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

		Params params = Params.create().add("@withAlias").add("@withSenderInfo");
		orders = sdk.listOrders(token, OrderStatus.WAIT_SELLER_SEND, 10, 1, params, true, false);
		if (orders == null) {
			return false;
		}
		return true;
	}

	public boolean getIsFirstEntity() {
		return null != this._index && this._index == 0;
	}

	public String getSpecValue() {
		return this._entity.getSpecInfo().get(_specName);
	}

	public String getEntityQuantityHTML() {
		if (null != _entity) {
			Double q = _entity.getQuantity();
			if (q <= 1.0) {
				return String.valueOf(q.intValue());
			} else {
				return String.format("<span style='color:red'>%s</span>", String.valueOf(q.intValue()));
			}
		}
		return "[ERROR]";
	}

	public boolean getIsSend() {
		if (this._entity.getEntryStatus().equalsIgnoreCase(OrderStatus.WAIT_BUYER_RECEIVE.toString())
				|| this._entity.getEntryStatus().equalsIgnoreCase(OrderStatus.SUCCESS.toString())) {
			return true;
		}
		return false;
	}

	@Inject
	AuthService sdk;

	@SessionState
	AliToken token;
}
