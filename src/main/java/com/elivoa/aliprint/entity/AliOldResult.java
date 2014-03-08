package com.elivoa.aliprint.entity;

import java.util.List;

import com.elivoa.aliprint.data.APIResponse;
import com.google.common.collect.Lists;

/**
 * 旧版
 * 
 * @param <T>
 */
public class AliOldResult<T> {

	private boolean success;

	private int total;

	private List<T> returns;

	public AliOldResult() {
		// empty constructor
	}

	public AliOldResult(APIResponse resp) {
		APIResponse result = resp.getResp("result");
		// parse basic values;
		if (null != result) {
			this.success = result.getBoolean("success");
			this.total = result.getInt("total");
		}
	}

	public static AliOldResult<AliProduct> newProductListResult(APIResponse resp) {
		AliOldResult<AliProduct> result = new AliOldResult<AliProduct>(resp);
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

	public static AliOldResult<AliOldOrder> newOrderListResult(APIResponse resp) {
		AliOldResult<AliOldOrder> result = new AliOldResult<AliOldOrder>(resp); // for basic;
		APIResponse resultNode = resp.getResp("result");
		// parse basic values;
		if (null != result) {
			List<APIResponse> orderList = resultNode.getRespList("toReturn");
			if (null != orderList) {
				result.returns = Lists.newArrayList();
				for (APIResponse raworder : orderList) {
					AliOldOrder order = new AliOldOrder(raworder);
					result.returns.add(order);
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
