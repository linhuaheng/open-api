package com.wwwarehouse.xdw.openapi.sdk.internal.util;

import com.wwwarehouse.xdw.openapi.sdk.FileItem;
import com.wwwarehouse.xdw.openapi.sdk.WhException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

public class HttpUtil {
	public static final String DEFAULT_CHARSET = "UTF-8";
	private static final String METHOD_POST = "POST";

	private HttpUtil() {
		throw new UnsupportedOperationException();
	}

	public static String buildQuery(Map<String, String> params, String charset)
			throws Exception {
		if ((params == null) || (params.isEmpty())) {
			return null;
		}
		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;
		for (Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			if (StringUtil.areNotEmpty(new String[]{name, value})) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}
				query.append(name).append("=").append(URLEncoder.encode(value, charset));
			}
		}
		return query.toString();
	}

	public static String doPost(String url, Map<String, String> params, int connectTimeout, int readTimeout)
			throws Exception {
		return doPost(url, params, "UTF-8", connectTimeout, readTimeout);
	}

	public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout)
			throws Exception {
		String ctype = "application/x-www-form-urlencoded;charset=" + charset;
		String query = buildQuery(params, charset);
		byte[] content = new byte[0];
		if (query != null) {
			content = query.getBytes(charset);
		}
		return doPost(url, ctype, content, connectTimeout, readTimeout);
	}

	public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, int connectTimeout, int readTimeout)
			throws Exception {
		if ((fileParams == null) || (fileParams.isEmpty())) {
			return doPost(url, params, "UTF-8", connectTimeout, readTimeout);
		}
		return doPost(url, params, fileParams, "UTF-8", connectTimeout, readTimeout);
	}

	public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset, int connectTimeout, int readTimeout)
			throws IOException, WhException {
		String boundary = System.currentTimeMillis() + "";
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			try {
				String ctype = "multipart/form-data;charset=" + charset + ";boundary=" + boundary;
				conn = getConnection(new URL(url), "POST", ctype);
				conn.setConnectTimeout(connectTimeout);
				conn.setReadTimeout(readTimeout);
			} catch (IOException e) {
				throw new WhException(e);
			}
			try {
				out = conn.getOutputStream();

				byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);
				Set<Entry<String, String>> textEntrySet = params.entrySet();
				for (Entry<String, String> textEntry : textEntrySet) {
					byte[] textBytes = getTextEntry((String) textEntry.getKey(), (String) textEntry.getValue(), charset);
					out.write(entryBoundaryBytes);
					out.write(textBytes);
				}
				Set<Entry<String, FileItem>> fileEntrySet = fileParams.entrySet();
				for (Entry<String, FileItem> fileEntry : fileEntrySet) {
					FileItem fileItem = (FileItem) fileEntry.getValue();
					byte[] fileBytes = getFileEntry((String) fileEntry.getKey(), fileItem.getFileName(), fileItem.getMimeType(), charset);

					out.write(entryBoundaryBytes);
					out.write(fileBytes);
					byte[] content = fileItem.getContent();

					content = content == null ? new byte[0] : content;
					out.write(content);
				}
				byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
				out.write(endBoundaryBytes);
				rsp = getResponseAsString(conn);
			} catch (IOException e) {
				throw new WhException(e);
			}
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rsp;
	}

	public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout)
			throws IOException {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			conn = getConnection(new URL(url), "POST", ctype);

			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);

			out = conn.getOutputStream();
			out.write(content);
			rsp = getResponseAsString(conn);
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rsp;
	}

	private static HttpURLConnection getConnection(URL url, String method, String ctype)
			throws IOException {
		HttpURLConnection conn = null;
		if ("https".equals(url.getProtocol())) {
			SSLContext ctx = null;
			try {
				ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0], new DefaultTrustManager[]{new DefaultTrustManager()}, new SecureRandom());
			} catch (Exception e) {
				throw new IOException(e);
			}
			HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
			connHttps.setSSLSocketFactory(ctx.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			conn = connHttps;
		} else {
			conn = (HttpURLConnection) url.openConnection();
		}
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "Iscs-Http-Client");
		conn.setRequestProperty("Content-Type", ctype);
		return conn;
	}

	protected static String getResponseAsString(HttpURLConnection conn)
			throws IOException {
		String charset = getResponseCharset(conn.getContentType());
		InputStream es = conn.getErrorStream();
		if (es == null) {
			return getStreamAsString(conn.getInputStream(), charset);
		}
		String msg = getStreamAsString(es, charset);
		if (StringUtil.isEmpty(msg)) {
			throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
		}
		throw new IOException(msg);
	}

	private static String getStreamAsString(InputStream stream, String charset)
			throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
			StringWriter writer = new StringWriter();

			char[] chars = new char[256];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}
			return writer.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	private static String getResponseCharset(String ctype) {
		String charset = "UTF-8";
		if (!StringUtil.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if ((pair.length != 2) ||
							(StringUtil.isEmpty(pair[1]))) {
						break;
					}
					charset = pair[1].trim();
					break;
				}
			}
		}
		return charset;
	}

	private static byte[] getTextEntry(String fieldName, String fieldValue, String charset)
			throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
		entry.append(fieldValue);
		return entry.toString().getBytes(charset);
	}

	private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset)
			throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\";filename=\"");
		entry.append(fileName);
		entry.append("\"\r\nContent-Type:");
		entry.append(mimeType);
		entry.append("\r\n\r\n");
		return entry.toString().getBytes(charset);
	}
}
