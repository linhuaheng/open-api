package com.wwwarehouse.xdw.openapi.util.parser;

import com.wwwarehouse.xdw.openapi.sdk.WhException;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

public class XmlParser implements Parser {
	public <T extends WhRequest<R>, R extends AbstractResponse> T parseRequest(String json, Class<T> responseClass)
			throws WhException {
		return null;
	}
}
