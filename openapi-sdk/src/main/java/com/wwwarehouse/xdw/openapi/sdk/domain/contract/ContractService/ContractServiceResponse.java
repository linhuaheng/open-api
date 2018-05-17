package com.wwwarehouse.xdw.openapi.sdk.domain.contract.ContractService;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ContractServiceResponse implements Serializable {
	private Integer resultCode;
	private List<Contract> data;

	@JsonProperty("resultCode")
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	@JsonProperty("resultCode")
	public Integer getResultCode() {
		return this.resultCode;
	}

	@JsonProperty("data")
	public void setData(List<Contract> data) {
		this.data = data;
	}

	@JsonProperty("data")
	public List<Contract> getData() {
		return this.data;
	}
}
