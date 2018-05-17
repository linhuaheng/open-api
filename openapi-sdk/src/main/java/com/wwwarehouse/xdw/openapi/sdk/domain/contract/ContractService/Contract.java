package com.wwwarehouse.xdw.openapi.sdk.domain.contract.ContractService;

import java.io.Serializable;

public class Contract implements Serializable {
	private Long contractUkid;
	private String contractId;
	private Long supplierBusinessId;
	private Long demanderBusinessId;
	private Integer contractStatus;

	public Long getContractUkid() {
		return contractUkid;
	}

	public void setContractUkid(Long contractUkid) {
		this.contractUkid = contractUkid;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Long getSupplierBusinessId() {
		return supplierBusinessId;
	}

	public void setSupplierBusinessId(Long supplierBusinessId) {
		this.supplierBusinessId = supplierBusinessId;
	}

	public Long getDemanderBusinessId() {
		return demanderBusinessId;
	}

	public void setDemanderBusinessId(Long demanderBusinessId) {
		this.demanderBusinessId = demanderBusinessId;
	}

	public Integer getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}
}
