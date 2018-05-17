package com.wwwarehouse.xdw.openapi.sdk.response.contract;

import com.wwwarehouse.xdw.openapi.sdk.domain.contract.ContractService.ContractServiceResponse;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

public class ContractCreateResponse extends AbstractResponse {
	private ContractServiceResponse baseAreaServiceResponse;

	public void setBaseAreaServiceResponse(ContractServiceResponse baseAreaServiceResponse) {
		this.baseAreaServiceResponse = baseAreaServiceResponse;
	}

	public ContractServiceResponse getBaseAreaServiceResponse() {
		return this.baseAreaServiceResponse;
	}
}
