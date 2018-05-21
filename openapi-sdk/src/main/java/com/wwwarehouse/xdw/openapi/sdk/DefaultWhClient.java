package com.wwwarehouse.xdw.openapi.sdk;

import com.wwwarehouse.xdw.openapi.sdk.internal.parser.Parser;
import com.wwwarehouse.xdw.openapi.sdk.internal.parser.ParserFactory;
import com.wwwarehouse.xdw.openapi.sdk.internal.util.CodecUtil;
import com.wwwarehouse.xdw.openapi.sdk.internal.util.HttpUtil;
import com.wwwarehouse.xdw.openapi.sdk.internal.util.StringUtil;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.request.WhUploadRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class DefaultWhClient implements WhClient {
	public static final String CHARSET_UTF8 = "UTF-8";
	public static final String KEY_SIGN = "sign";
	private static final String JSON_PARAM_KEY = "data";
	private static final String OTHER_PARAM_KEY = "other";
	public static final String KEY_APP_KEY = "app_key";
	public static final String KEY_ACCESS_TOKEN = "access_token";
	public static final String KEY_METHOD = "method";
	private String serverUrl;
	private String accessToken;
	private int connectTimeout = 30000;
	private int readTimeout = 30000;
	private String appKey;
	private String appSecret;

	public DefaultWhClient(String serverUrl, String accessToken, String appKey, String appSecret) {
		this.serverUrl = serverUrl;
		this.accessToken = accessToken;
		this.appKey = appKey;
		this.appSecret = appSecret;
	}

	public DefaultWhClient(String serverUrl, String accessToken, String appKey, String appSecret, int connectTimeout, int readTimeout) {
		this(serverUrl, accessToken, appKey, appSecret);
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}

	@Override
	public <T extends AbstractResponse> T execute(WhRequest<T> paramWHRequest)
			throws WhException {
		try {
			String url = buildUrl(paramWHRequest);
			Map<String, String> params = new HashMap();
			String json = paramWHRequest.getAppJsonParams();
			params.put(JSON_PARAM_KEY, json);
			if (paramWHRequest.getOtherParams() != null) {
				params.put(OTHER_PARAM_KEY, paramWHRequest.getOtherParams());
			}
			String rsp = null;
			if ((paramWHRequest instanceof WhUploadRequest)) {
				rsp = HttpUtil.doPost(url, params, ((WhUploadRequest) paramWHRequest).getFileParams(), this.connectTimeout, this.readTimeout);
			} else {
				rsp = HttpUtil.doPost(url, params, this.connectTimeout, this.readTimeout);
			}
			T resp = parse(rsp, paramWHRequest.getResponseClass());
			StringBuffer sb = new StringBuffer();
			sb.append(url).append("&").append(JSON_PARAM_KEY).append("=").append(json);

			resp.setUrl(sb.toString());

			return resp;
		} catch (Exception e) {
			throw new WhException("服务器连接超时，请重试");
		}
	}

	private <T extends AbstractResponse> String buildUrl(WhRequest<T> request)
			throws Exception {
		Map<String, String> sysParams = request.getSysParams();

		Map<String, String> pmap = new TreeMap();
		pmap.put(JSON_PARAM_KEY, request.getAppJsonParams());
		sysParams.put(KEY_METHOD, request.getApiMethod());
		sysParams.put(KEY_ACCESS_TOKEN, this.accessToken);
		sysParams.put(KEY_APP_KEY, this.appKey);

		pmap.putAll(sysParams);
		String sign = sign(pmap, this.appSecret);
		sysParams.put(KEY_SIGN, sign);

		StringBuilder sb = new StringBuilder(this.serverUrl);

		sb.append("?");

		sb.append(HttpUtil.buildQuery(sysParams, CHARSET_UTF8));

		return sb.toString();
	}

	private <T extends AbstractResponse> T parse(String rsp, Class<T> responseClass)
			throws WhException {
		Parser parser;

		if (this.serverUrl.endsWith("json")) {
			parser = ParserFactory.getJsonParser();
		} else {
			parser = ParserFactory.getXmlParser();
		}

		return parser.parse(rsp, responseClass);
	}

	private String sign(Map<String, String> params, String appSecret) throws Exception {
		StringBuilder sb = new StringBuilder(appSecret);
		for (Entry<String, String> entry : params.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();
			if (StringUtil.areNotEmpty(new String[]{name, value})) {
				sb.append(name).append(value);
			}
		}

		sb.append(appSecret);

		String result = CodecUtil.md5(sb.toString());
		return result;
	}
}
