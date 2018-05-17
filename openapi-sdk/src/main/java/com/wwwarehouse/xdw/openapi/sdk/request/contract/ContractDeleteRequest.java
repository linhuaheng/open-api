package com.wwwarehouse.xdw.openapi.sdk.request.contract;

import com.wwwarehouse.xdw.openapi.sdk.internal.util.JsonUtil;
import com.wwwarehouse.xdw.openapi.sdk.request.AbstractRequest;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.contract.ContractDeleteResponse;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class ContractDeleteRequest extends AbstractRequest
		implements WhRequest<ContractDeleteResponse> {
	private Long contractUkid;

	public Long getContractUkid() {
		return contractUkid;
	}

	public void setContractUkid(Long contractUkid) {
		this.contractUkid = contractUkid;
	}

	public String getApiMethod() {
		return "contract.delete";
	}

	public String getAppJsonParams()
			throws IOException {
		Map<String, Object> pmap = new TreeMap();
		pmap.put("contractUkid", this.contractUkid);
		return JsonUtil.toJson(pmap);
	}

	public Class<ContractDeleteResponse> getResponseClass() {
		return ContractDeleteResponse.class;
	}
}
