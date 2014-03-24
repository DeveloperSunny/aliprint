package com.elivoa.aliprint.components.order;

import org.apache.tapestry5.annotations.Parameter;
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

	@Parameter
	Integer start;

	@Parameter
	Integer itemsPerPage;

	Object setupRender() {
		// this.start = this.start < 0 ? 0 : this.start;
		// this.itemsPerPage = this.itemsPerPage <= 0 ? defaultItemsPerPage : itemsPerPage;

		int page = (start / itemsPerPage) + 1;

		orders = sdk.listOrders(token, OrderStatus.WAIT_SELLER_SEND, itemsPerPage, page,
				Params.create().add("@withAlias").add("@withSenderInfo"), true, true);
		if (orders == null) {
			return false;
		}
		return true;
	}

	public int getTotalItems() {
		return orders.getTotal();
	}

	// something in loop

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

	public String getEntityStatusStr() {
		if (OrderStatus.CANCEL.toString().equalsIgnoreCase(this._entity.getEntryStatus())) {
			return "已取消";
		} else if (OrderStatus.WAIT_BUYER_PAY.toString().equalsIgnoreCase(this._entity.getEntryStatus())) {
			return "未付款";
		}
		return null;
	}

	public String getEntityColor() {
		if (this._entity != null && this._entity.getSpecInfo() != null) {
			String color = this._entity.getSpecInfo().get("颜色");
			return color == null ? null : color;
		}
		return null;
	}

	public String getEntitySize() {
		if (this._entity != null && this._entity.getSpecInfo() != null) {
			String size = this._entity.getSpecInfo().get("尺码");
			return size == null ? null : size;
		}
		return null;
	}

	// service

	@Inject
	AuthService sdk;

	@SessionState
	AliToken token;
}
