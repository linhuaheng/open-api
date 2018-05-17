package com.wwwarehouse.xdw.openapi.web.controller;

import com.alibaba.fastjson.JSON;
import com.wwwarehouse.commons.exception.ApiCheckException;
import com.wwwarehouse.commons.exception.IscsErrorCode;
import com.wwwarehouse.commons.spring.SpringContextUtil;
import com.wwwarehouse.commons.utils.*;
import com.wwwarehouse.commons.utils.concurrent.ThreadExecutor;
import com.wwwarehouse.commons.web.ReqUtil;
import com.wwwarehouse.xdw.datasync.model.AmAppkey;
import com.wwwarehouse.xdw.datasync.model.AmRequestLog;
import com.wwwarehouse.xdw.datasync.service.IAmAppkeyService;
import com.wwwarehouse.xdw.openapi.enums.ApiMethod;
import com.wwwarehouse.xdw.openapi.exception.ApiException;
import com.wwwarehouse.xdw.openapi.sdk.WhException;
import com.wwwarehouse.xdw.openapi.sdk.internal.util.CodecUtil;
import com.wwwarehouse.xdw.openapi.sdk.internal.util.StringUtil;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;
import com.wwwarehouse.xdw.openapi.util.ApiResponse;
import com.wwwarehouse.xdw.openapi.util.parser.Parser;
import com.wwwarehouse.xdw.openapi.util.parser.ParserFactory;
import com.wwwarehouse.xdw.openapi.web.service.ApiProcessService;
import com.wwwarehouse.xdw.openapi.web.util.MongoDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Huang Zhigang
 */
@Controller
@RequestMapping("/openapi")
public class OpenApiController {
	private static final Logger log = LogManager.getLogger(OpenApiController.class);
	private String appKey;
	private String v;
	private String method;
	private String sign;
	private String secret;
	private String timestamp;
	private String clientIp;
	private boolean isHmac = false;
	private String data;
	private static long OVERLAP_MINUTE = 10 * 60 * 1000;//服务器允许最大时间差，10分钟

	@Resource
	private IAmAppkeyService amAppkeyService;
	@Resource
	private MongoDao<AmRequestLog> requestLogMongoDao;

	//获取来源，判断是属于哪个平台，ERP，api过来的。
	@ResponseBody
	@RequestMapping(value = "/do")
	public AbstractResponse init(HttpServletRequest request) {
		long startMis = System.currentTimeMillis();
		long startCallMis = 0L;//调用业务方法的开始时间
		Map<String, String> params = getParamMap(request);

		//数据完整性校验
		int errorCode = 0;
		String errorMsg = null;

		AbstractResponse abstractResponse = null;
		WhRequest whRequest = null;
		try {
			validate();
			ApiMethod apiMethod = ApiMethod.findCheck(method);
			AmAppkey app = getApp(appKey);
			if (app == null || app.getStatus() != 1) {
				throw new ApiException(IscsErrorCode.ApiCallError.getErrorCode(), "应用不存在或未启用！");
			}
			this.secret = app.getAppSecret();

			//判断签名
			signCheck(params, this.secret, this.isHmac);

			String dataFormat = app.getDataFormat();
			whRequest = parse(this.data, apiMethod.getClazz(), dataFormat);
			if (whRequest == null) {
				throw new NoSuchMethodException(method);
			}
			validateIp(app.getWhiteIp());

			startCallMis = System.currentTimeMillis();
			abstractResponse = process(app, whRequest);
		} catch (ApiException e) {
			errorCode = e.getErrCode();
			errorMsg = e.getErrMsg();
			abstractResponse = new ApiResponse(String.valueOf(errorCode), errorMsg);
		} catch (Exception e) {
			log.error("", e);
			errorCode = IscsErrorCode.ApiCallError.getErrorCode();
			errorMsg = e.getMessage();
			abstractResponse = new ApiResponse(String.valueOf(errorCode), errorMsg);
		} finally {
			//record log
			insertLog(startMis, startCallMis, whRequest, abstractResponse);
		}

		return abstractResponse;
	}

	private void insertLog(long startMis, long startCallMis,
						   WhRequest whRequest, AbstractResponse respone) {
		ThreadExecutor.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				long endMis = System.currentTimeMillis();
				long subMis = endMis - startMis;
				long callSubMis = startCallMis == 0L ? 0L : endMis - startCallMis;

				AmRequestLog data = new AmRequestLog();
				data.setMethodName(whRequest.getApiMethod());
				data.setRequestDate(new Date(startMis));
				data.setEndDate(new Date(endMis));
				data.setTimeRange(subMis);//存储进入方法后消耗的时间
				data.setPageNum(callSubMis);//临时存储接口调用消耗的时间
				data.setParamString(whRequest == null ? null : JSON.toJSONString(whRequest));
				if (respone != null) {
					data.setResponseContent(JSON.toJSONString(respone));
					data.setDoResult(respone.getCode());
				} else {
					data.setResponseContent(null);
					data.setDoResult("异常");
				}
				data.setRequestIp(ServerUtil.getServerIp());
				requestLogMongoDao.insert(data);

				long saveSubRange = System.currentTimeMillis() - endMis;
				if (log.isInfoEnabled()) {
					log.info("dump_mondb={},耗時={}", "OpenApiController", saveSubRange);
				}
			}
		});
	}

	/**
	 * 处理请求
	 *
	 * @param amAppkey
	 * @param request
	 * @param <T>
	 * @return
	 */
	private <T extends AbstractResponse> T process(AmAppkey amAppkey, WhRequest<T> request) throws ApiException{
		ApiProcessService apiProcessService = getApiProcessService(request.getApiMethod());
		return (T)apiProcessService.execute(amAppkey, request);
	}

	private ApiProcessService getApiProcessService(String apiName) {
		String beanName = StringUtils.replaceUnderlineAndfirstToUpper(apiName, ".", "") + "Service";
		return (ApiProcessService) SpringContextUtil.getBean(beanName);
	}

	private AmAppkey getApp(String appKey) {
		Long platformId = Long.valueOf("1");
		String appType = "IOA";

		AmAppkey amAppkey = amAppkeyService.getByAppkey(platformId, appKey, appType);
		//todo 添加缓存
		return amAppkey;
	}

	/**
	 * 获取参数列表
	 *
	 * @param request
	 * @return
	 */
	private Map<String, String> getParamMap(HttpServletRequest request) {
		this.clientIp = ReqUtil.getClientIp(request);
		Map<String, String> params = new TreeMap<>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			if (StringUtils.isNotEmpty(name)) {
				String value = request.getParameter(name);
				if (!"sign".equals(name)) {// sign 不添加到paramMap中
					params.put(name, value);
				}

				fill(name, value);
			}
		}
		return params;
	}

	private void fill(String name, String value) {
		if ("sign".equals(name)) {
			this.sign = value;
		} else if ("method".equals(name)) {
			this.method = value;
		} else if ("app_key".equals(name)) {
			this.appKey = value;
		} else if ("v".equals(name)) {
			this.v = value;
		} else if ("timestamp".equals(name)) {
			this.timestamp = value;
		} else if ("data".equals(name)) {
			this.data = value;
		}
	}

	/**
	 * 数据完整性校验
	 *
	 * @throws ApiCheckException
	 */
	protected void validate() throws ApiCheckException {
		RequestCheckUtils.checkNotEmpty(this.appKey, "appKey");
		RequestCheckUtils.checkNotEmpty(this.data, "data");
		RequestCheckUtils.checkNotEmpty(this.method, "method");
		RequestCheckUtils.checkNotEmpty(this.timestamp, "timestamp");
		RequestCheckUtils.checkNotEmpty(this.v, "v");
		RequestCheckUtils.checkNotEmpty(this.sign, "sign");
		validateTime();
	}

	private void validateTime() throws ApiCheckException {
		RequestCheckUtils.checkNotEmpty(this.timestamp, "timestamp");
		try {
			Date requestTime = DateUtil.parseDateTime(this.timestamp);
			if (requestTime == null) {
				throw new ApiCheckException(IscsErrorCode.InvalidArguments, "时间格式不正确:'yyyy-MM-dd HH:mm:ss'," + this.timestamp);
			}
			if (System.currentTimeMillis() - requestTime.getTime() > OVERLAP_MINUTE) {
				throw new ApiCheckException(IscsErrorCode.InvalidArguments, "与服务器时间不同步:" + this.timestamp);
			}
		} catch (Exception e) {
			ApiCheckException ae = new ApiCheckException(e.getMessage(), e);
			ae.setErrCode(IscsErrorCode.ParseException.getErrorCode());
			ae.setErrMsg(IscsErrorCode.ParseException.getErrorText());
			throw ae;
		}
	}

	private void validateIp(String whiteIps) throws ApiCheckException {
		if (StringUtils.isNotEmpty(whiteIps) && whiteIps.contains(this.clientIp)) {
			throw new ApiCheckException(110, "Not included in white ips:" + this.clientIp);
		}
	}

	private void signCheck(Map<String, String> params, String secret,
						   boolean isHmac) throws Exception {
		String reSign = sign(params, secret);
		if (!sign.equals(reSign)) {
			int errorCode = IscsErrorCode.SignError.getErrorCode();
			throw new ApiCheckException(errorCode, IscsErrorCode.SignError.getErrorText());
		}
	}

	private String sign(Map<String, String> pmap, String appSecret) throws Exception {

		StringBuilder sb = new StringBuilder(appSecret);
		for (Map.Entry<String, String> entry : pmap.entrySet()) {
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

	private <T extends WhRequest<R>, R extends AbstractResponse> T parse(String rsp, Class<T> responseClass, String format)
			throws WhException {
		Parser parser;
		if ("JSON".endsWith(format)) {
			parser = ParserFactory.getJsonParser();
		} else {
			parser = ParserFactory.getXmlParser();
		}

		return parser.parseRequest(rsp, responseClass);
	}
}
