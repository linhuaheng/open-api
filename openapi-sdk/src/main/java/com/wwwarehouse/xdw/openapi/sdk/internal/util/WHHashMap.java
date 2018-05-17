package com.wwwarehouse.xdw.openapi.sdk.internal.util;

import java.util.HashMap;
import java.util.Map;

public class WHHashMap extends HashMap<String, String> {
	private static final long serialVersionUID = 1391568378542671698L;

	public WHHashMap() {
	}

	public WHHashMap(Map<? extends String, ? extends String> m) {
		super(m);
	}

	public String put(String key, String value) {
		if (StringUtil.areNotEmpty(new String[]{key, value})) {
			return (String) super.put(key, value);
		}
		return null;
	}
}