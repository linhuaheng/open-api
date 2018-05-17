package com.wwwarehouse.xdw.openapi.sdk.request.contract;

import com.wwwarehouse.xdw.openapi.sdk.internal.util.JsonUtil;
import com.wwwarehouse.xdw.openapi.sdk.request.AbstractRequest;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.contract.ContractCreateResponse;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class ContractCreateRequest extends AbstractRequest
		implements WhRequest<ContractCreateResponse> {
	private Integer parentId;

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public String getApiMethod() {
		return "contract.create";
	}

	public String getAppJsonParams() throws IOException {
		Map<String, Object> pmap = new TreeMap();
		pmap.put("parent_id", this.parentId);
		return JsonUtil.toJson(pmap);
	}

	public Class<ContractCreateResponse> getResponseClass() {
		return ContractCreateResponse.class;
	}
}
