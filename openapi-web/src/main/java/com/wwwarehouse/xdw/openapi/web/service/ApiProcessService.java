package com.wwwarehouse.xdw.openapi.web.service;

import com.wwwarehouse.xdw.datasync.model.AmAppkey;
import com.wwwarehouse.xdw.openapi.exception.ApiException;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

/**
 * Created by zhigang.huang on  2018/5/14
 * 接口处理的service
 */
public interface ApiProcessService<T extends WhRequest<R>, R extends AbstractResponse> {
	public R execute(AmAppkey amAppkey, T request) throws ApiException;
}
