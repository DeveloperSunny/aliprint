package com.elivoa.aliprint.entity;

import java.util.List;

import com.elivoa.aliprint.data.APIResponse;
import com.google.common.collect.Lists;

public class AliResult<T> {

	private boolean success;

	private int total;

	private List<T> returns;

	public AliResult() {
		// empty constructor
	}

	public AliResult(APIResponse resp) {
		APIResponse result = resp.getResp("result");
		// parse basic values;
		if (null != result) {
			this.success = result.getBoolean("success");
			this.total = result.getInt("total");
		}
	}

	public static AliResult<AliOrder> newOrderListResult(APIResponse resp) {
		AliResult<AliOrder> result = new AliResult<AliOrder>(resp); // for basic;
		APIResponse resultNode = resp.getResp("result");
		// parse basic values;
		if (null != result) {
			List<APIResponse> orderList = resultNode.getRespList("toReturn");
			if (null != orderList) {
				result.returns = Lists.newArrayList();
				for (APIResponse raworder : orderList) {
					AliOrder order = new AliOrder(raworder);
					result.returns.add(order);
				}
			}
		}
		return result;
	}

	public static AliResult<AliProduct> newProductListResult(APIResponse resp) {
		AliResult<AliProduct> result = new AliResult<AliProduct>(resp);
		APIResponse resultNode = resp.getResp("result");
		// parse basic values;
		if (null != result) {
			List<APIResponse> orderList = resultNode.getRespList("toReturn");
			if (null != orderList) {
				result.returns = Lists.newArrayList();
				for (APIResponse raworder : orderList) {
					AliProduct product = new AliProduct(raworder);
					result.returns.add(product);
				}
			}
		}
		return result;
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

	public List<T> getReturns() {
		return returns;
	}

	public void setReturns(List<T> returns) {
		this.returns = returns;
	}

}
