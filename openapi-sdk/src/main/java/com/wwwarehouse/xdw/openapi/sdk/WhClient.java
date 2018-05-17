package com.wwwarehouse.xdw.openapi.sdk;

import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

public abstract interface WhClient {
	public abstract <T extends AbstractResponse> T execute(WhRequest<T> paramWHRequest)
			throws WhException;
}
