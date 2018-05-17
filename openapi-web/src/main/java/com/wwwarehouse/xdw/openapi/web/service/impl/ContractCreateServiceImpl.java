package com.wwwarehouse.xdw.openapi.web.service.impl;

import com.wwwarehouse.xdw.datasync.model.AmAppkey;
import com.wwwarehouse.xdw.openapi.exception.ApiException;
import com.wwwarehouse.xdw.openapi.sdk.request.contract.ContractCreateRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.contract.ContractCreateResponse;
import com.wwwarehouse.xdw.openapi.web.service.ApiProcessService;
import org.springframework.stereotype.Service;

/**
 * Created by zhigang.huang on  2018/5/14
 * 接口处理的service
 */
@Service("contractCreateService")
public class ContractCreateServiceImpl implements ApiProcessService<ContractCreateRequest, ContractCreateResponse> {
	@Override
	public ContractCreateResponse execute(AmAppkey amAppkey, ContractCreateRequest request) throws ApiException {
		return new ContractCreateResponse();
	}
}
