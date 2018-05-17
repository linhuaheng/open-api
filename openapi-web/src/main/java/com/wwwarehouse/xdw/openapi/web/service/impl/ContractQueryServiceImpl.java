package com.wwwarehouse.xdw.openapi.web.service.impl;

import com.wwwarehouse.xdw.datasync.model.AmAppkey;
import com.wwwarehouse.xdw.openapi.exception.ApiException;
import com.wwwarehouse.xdw.openapi.sdk.request.contract.ContractQueryRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.contract.ContractQueryResponse;
import com.wwwarehouse.xdw.openapi.web.service.ApiProcessService;
import org.springframework.stereotype.Service;

/**
 * Created by zhigang.huang on  2018/5/14
 * 接口处理的service
 */
@Service("contractQueryService")
public class ContractQueryServiceImpl implements ApiProcessService<ContractQueryRequest, ContractQueryResponse> {
	@Override
	public ContractQueryResponse execute(AmAppkey amAppkey, ContractQueryRequest request) throws ApiException {
		return new ContractQueryResponse();
	}
}
