package com.wwwarehouse.xdw.openapi.sdk.internal.parser;

import com.wwwarehouse.xdw.openapi.sdk.WhException;
import com.wwwarehouse.xdw.openapi.sdk.internal.JSON.JSON;
import com.wwwarehouse.xdw.openapi.sdk.internal.util.StringUtil;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.util.Map;

public class JsonParser implements Parser {
	private final ObjectMapper mapper = new ObjectMapper();

	public JsonParser() {
		this.mapper.getDeserializationConfig().set(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

		this.mapper.getDeserializationConfig().set(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		this.mapper.getDeserializationConfig().set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public <T extends AbstractResponse> T parse(String json, Class<T> responseClass)
			throws WhException {
		T response = null;
		try {
			if (StringUtil.isEmpty(json)) {
				throw new WhException("response json is empty!");
			}
			response = fromJson(json, responseClass);
			if (response != null) {
				response.setMsg(json);
			}
		} catch (Exception e) {
			throw new WhException(e);
		}
		return response;
	}

	public <T extends AbstractResponse> T fromJson(String json, Class<T> responseClass)
			throws Exception {
		ObjectNode rootNode = null;
		try {
			rootNode = (ObjectNode) this.mapper.readTree(json);
		} catch (Exception e) {
			rootNode = (ObjectNode) this.mapper.readTree(JSON.toString(JSON.parse(json)));
		}
		String innerJson = (((Map.Entry) rootNode.getFields().next()).getValue()).toString();
		return this.mapper.readValue(innerJson, responseClass);
	}
}
