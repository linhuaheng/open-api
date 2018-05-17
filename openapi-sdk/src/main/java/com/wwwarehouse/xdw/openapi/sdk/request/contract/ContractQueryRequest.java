package com.wwwarehouse.xdw.openapi.sdk.request.contract;

import com.wwwarehouse.xdw.openapi.sdk.internal.util.JsonUtil;
import com.wwwarehouse.xdw.openapi.sdk.request.AbstractRequest;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.contract.ContractQueryResponse;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class ContractQueryRequest extends AbstractRequest
		implements WhRequest<ContractQueryResponse> {
	private Long obuId;
	private Date startCreateTime;
	private Date endCreateTime;

	@Override
	public Long getObuId() {
		return obuId;
	}

	@Override
	public void setObuId(Long obuId) {
		this.obuId = obuId;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getApiMethod() {
		return "contract.query";
	}

	public String getAppJsonParams()
			throws IOException {
		Map<String, Object> pmap = new TreeMap();
		pmap.put("obuId", this.obuId);
		pmap.put("startCreateTime", this.startCreateTime);
		pmap.put("endCreateTime", this.endCreateTime);
		return JsonUtil.toJson(pmap);
	}

	public Class<ContractQueryResponse> getResponseClass() {
		return ContractQueryResponse.class;
	}
}
