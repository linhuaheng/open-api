package com.wwwarehouse.xdw.openapi.sdk.request;

import org.codehaus.jackson.annotate.JsonProperty;

public class Field {
	private String key;
	private String value;

	public Field() {
	}

	public Field(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@JsonProperty("key")
	public String getKey() {
		return this.key;
	}

	@JsonProperty("key")
	public void setKey(String key) {
		this.key = key;
	}

	@JsonProperty("value")
	public String getValue() {
		return this.value;
	}

	@JsonProperty("value")
	public void setValue(String value) {
		this.value = value;
	}
}
