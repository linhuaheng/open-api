package com.wwwarehouse.xdw.openapi.sdk.response.contract;

import com.wwwarehouse.xdw.openapi.sdk.domain.contract.ContractService.ContractServiceResponse;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;
import org.codehaus.jackson.annotate.JsonProperty;

public class ContractDeleteResponse extends AbstractResponse {
	private ContractServiceResponse baseAreaServiceResponse;

	@JsonProperty("baseAreaServiceResponse")
	public void setBaseAreaServiceResponse(ContractServiceResponse baseAreaServiceResponse) {
		this.baseAreaServiceResponse = baseAreaServiceResponse;
	}

	@JsonProperty("baseAreaServiceResponse")
	public ContractServiceResponse getBaseAreaServiceResponse() {
		return this.baseAreaServiceResponse;
	}
}
