package com.wwwarehouse.xdw.openapi.enums;

import com.wwwarehouse.commons.exception.IscsErrorCode;
import com.wwwarehouse.xdw.openapi.exception.NoSuchMethodException;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.request.contract.ContractCreateRequest;
import com.wwwarehouse.xdw.openapi.sdk.request.contract.ContractDeleteRequest;
import com.wwwarehouse.xdw.openapi.sdk.request.contract.ContractQueryRequest;
import com.wwwarehouse.xdw.openapi.sdk.request.contract.TradeCreateRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

public enum ApiMethod {

	/**
	 * 创建订单
	 */
	CONTRACT_CREATE("contract.create", ContractCreateRequest.class),

	/**
	 * 创建订单
	 */
	TRADE_CREATE("trade.create", TradeCreateRequest.class),

	/**
	 * 查询订单
	 */
	CONTRACT_QUERY("contract.query", ContractQueryRequest.class),

	/**
	 * 删除订单
	 */
	CONTRACT_DELETE("contract.delete", ContractDeleteRequest.class),

	/**
	 * 查看系统当前时间
	 */
	SYSTEM_REALTIME("system.realtime", null),;


	private String value;
	private Class clazz;

	ApiMethod(String value, Class clazz) {
		this.value = value;
		this.clazz = clazz;
	}

	public static ApiMethod find(String nameOrValue) {
		for (ApiMethod m : ApiMethod.values()) {
			if (m.value.equals(nameOrValue) || m.name().equals(nameOrValue)) {
				return m;
			}
		}
		return null;
	}

	public static ApiMethod findCheck(String nameOrValue) throws NoSuchMethodException {
		ApiMethod method = find(nameOrValue);
		if (method == null) {
			throw new NoSuchMethodException(IscsErrorCode.MethodNotExist, nameOrValue);
		}
		return method;
	}

	public String getValue() {
		return this.value;
	}

	public <T extends WhRequest<R>, R extends AbstractResponse> Class<T> getClazz() {
		return this.clazz;
	}

	public boolean equalsByNV(String nameOrValue) {
		return this.value.equals(nameOrValue) || this.name().equals(nameOrValue);
	}
}
