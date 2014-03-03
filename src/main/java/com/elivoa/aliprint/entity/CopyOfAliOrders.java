package com.elivoa.aliprint.entity;

import java.util.List;

import com.elivoa.aliprint.data.APIResponse;
import com.google.common.collect.Lists;

public class CopyOfAliOrders {

	private boolean success;

	private int total;

	private List<AliOrder> orders;

	public CopyOfAliOrders() {
		// empty constructor
	}

	public CopyOfAliOrders(APIResponse resp) {
		APIResponse result = resp.getResp("result");
		if (null != result) {
			this.success = result.getBoolean("success");
			this.total = result.getInt("total");

			List<APIResponse> orderList = result.getRespList("toReturn");
			if (null != orderList) {
				this.orders = Lists.newArrayList();
				for (APIResponse raworder : orderList) {
					AliOrder order = new AliOrder(raworder);
					orders.add(order);
				}
			}
			System.out.println(result);
		}
	}

	// accessors

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<AliOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<AliOrder> orders) {
		this.orders = orders;
	}
}
