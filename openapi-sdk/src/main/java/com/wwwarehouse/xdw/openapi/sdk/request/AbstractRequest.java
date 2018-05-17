package com.wwwarehouse.xdw.openapi.sdk.request;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRequest {
	private final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected String timestamp = this.sdf.format(new Date());
	protected String version = "1.0";
	protected Long obuId;
	protected String method;
	protected String sign;

	public Map<String, String> getSysParams() {
		Map<String, String> sysParams = new HashMap();
		sysParams.put("method", this.method);
		sysParams.put("timestamp", this.timestamp);
		sysParams.put("v", this.version);

		return sysParams;
	}

	protected Long getObuId() {
		return this.obuId;
	}

	protected void setObuId(Long obuId) {
		this.obuId = obuId;
	}

	protected String getMethod() {
		return this.method;
	}

	protected void setMethod(String method) {
		this.method = method;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	protected String getVersion() {
		return this.version;
	}

	protected void setVersion(String version) {
		this.version = version;
	}

	protected String getSign() {
		return this.sign;
	}

	protected void setSign(String sign) {
		this.sign = sign;
	}

	public String getOtherParams() throws IOException {
		return null;
	}
}
