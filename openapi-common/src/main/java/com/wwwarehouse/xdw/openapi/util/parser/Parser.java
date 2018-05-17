package com.wwwarehouse.xdw.openapi.util.parser;

import com.wwwarehouse.xdw.openapi.sdk.WhException;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

public abstract interface Parser {
	public abstract <T extends WhRequest<R>, R extends AbstractResponse>
		T parseRequest(String paramString, Class<T> paramClass)
			throws WhException;
}
