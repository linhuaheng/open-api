package com.wwwarehouse.xdw.openapi.sdk.internal.parser;

import com.wwwarehouse.xdw.openapi.sdk.WhException;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

public abstract interface Parser {
	public abstract <T extends AbstractResponse> T parse(String paramString, Class<T> paramClass)
			throws WhException;
}
