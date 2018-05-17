package com.wwwarehouse.xdw.openapi.sdk.request;

import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

import java.io.IOException;
import java.util.Map;

public abstract interface WhRequest<T extends AbstractResponse> {
	public abstract String getApiMethod();

	public abstract Map<String, String> getSysParams();

	public abstract String getAppJsonParams()
			throws IOException;

	public abstract String getOtherParams()
			throws IOException;

	public abstract Class<T> getResponseClass();
}
