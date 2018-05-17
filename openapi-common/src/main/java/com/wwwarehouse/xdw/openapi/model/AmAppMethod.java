package com.wwwarehouse.xdw.openapi.model;

import java.io.Serializable;

/**
 * AppMethod entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AmAppMethod implements Serializable{

	// Fields

	private Long methodUkid;
	private Long appUkid;
	private String appKey;
	private String interfaceName;
	private String apiMethodName;
	private String methodDesc;
	private String httpMethod;
	private String dataJoinName;
	private String dataFormat;
	private String responseFormat;
	private String destTable;
	private Long byConfig;
	private String callType;

	// Constructors

	/** default constructor */
	public AmAppMethod() {
	}

	/** minimal constructor */
	public AmAppMethod(Long methodUkid) {
		this.methodUkid = methodUkid;
	}

	/** full constructor */
	public AmAppMethod(Long methodUkid, Long appUkid, String interfaceName,
					   String apiMethodName, String methodDesc, String httpMethod,
					   String dataJoinName, String dataFormat, String responseFormat,
					   String destTable, Long byConfig, String callType) {
		this.methodUkid = methodUkid;
		this.appUkid = appUkid;
		this.interfaceName = interfaceName;
		this.apiMethodName = apiMethodName;
		this.methodDesc = methodDesc;
		this.httpMethod = httpMethod;
		this.dataJoinName = dataJoinName;
		this.dataFormat = dataFormat;
		this.responseFormat = responseFormat;
		this.destTable = destTable;
		this.byConfig = byConfig;
		this.callType = callType;
	}

	// Property accessors

	public Long getMethodUkid() {
		return this.methodUkid;
	}

	public void setMethodUkid(Long methodUkid) {
		this.methodUkid = methodUkid;
	}

	public Long getAppUkid() {
		return this.appUkid;
	}

	public void setAppUkid(Long appUkid) {
		this.appUkid = appUkid;
	}

	public String getInterfaceName() {
		return this.interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getApiMethodName() {
		return this.apiMethodName;
	}

	public void setApiMethodName(String apiMethodName) {
		this.apiMethodName = apiMethodName;
	}

	public String getMethodDesc() {
		return this.methodDesc;
	}

	public void setMethodDesc(String methodDesc) {
		this.methodDesc = methodDesc;
	}

	public String getHttpMethod() {
		return this.httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getDataJoinName() {
		return this.dataJoinName;
	}

	public void setDataJoinName(String dataJoinName) {
		this.dataJoinName = dataJoinName;
	}

	public String getDataFormat() {
		return this.dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getResponseFormat() {
		return this.responseFormat;
	}

	public void setResponseFormat(String responseFormat) {
		this.responseFormat = responseFormat;
	}

	public String getDestTable() {
		return this.destTable;
	}

	public void setDestTable(String destTable) {
		this.destTable = destTable;
	}

	public Long getByConfig() {
		return this.byConfig;
	}

	public void setByConfig(Long byConfig) {
		this.byConfig = byConfig;
	}

	public String getCallType() {
		return this.callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
}