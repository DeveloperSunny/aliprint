package com.elivoa.aliprint.entity;

import java.util.List;

import com.elivoa.aliprint.data.APIResponse;
import com.google.common.collect.Lists;

public class AliResult<T> {

	private int realPrePageSize;
	private String lastStartRow;
	private int total;

	private List<T> models;

	public AliResult() {
		// empty constructor
	}

	public AliResult(APIResponse resp) {
	}

	public static AliResult<AliOrder> newOrderListResult(APIResponse resp) {
		// System.out.println("===========================================================");
		// System.out.println(resp.data);
		AliResult<AliOrder> result = new AliResult<AliOrder>(resp); // for basic;
		APIResponse list = resp.getResp("orderListResult");
		// parse basic values;
		if (null != result) {
			result.total = list.getInt("totalCount");
			result.realPrePageSize = list.getInt("realPrePageSize");
			result.lastStartRow = list.getString("lastStartRow");
			List<APIResponse> modelList = list.getRespList("modelList");
			if (null != modelList) {
				result.models = Lists.newArrayList();
				for (APIResponse raworder : modelList) {
					AliOrder order = new AliOrder(raworder);
					result.models.add(order);
				}
			}
		}
		return result;
	}

	// accessors

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getModels() {
		return models;
	}

	public void setModels(List<T> models) {
		this.models = models;
	}

	public int getRealPrePageSize() {
		return realPrePageSize;
	}

	public void setRealPrePageSize(int realPrePageSize) {
		this.realPrePageSize = realPrePageSize;
	}

	public String getLastStartRow() {
		return lastStartRow;
	}

	public void setLastStartRow(String lastStartRow) {
		this.lastStartRow = lastStartRow;
	}

}
