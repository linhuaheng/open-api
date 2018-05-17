package com.wwwarehouse.xdw.openapi.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wwwarehouse.commons.utils.AbsResponse;
import com.wwwarehouse.xdw.contractcenter.constant.ContractResultConstant;
import com.wwwarehouse.xdw.contractcenter.enums.ContractCenterDefinedCode;
import com.wwwarehouse.xdw.contractcenter.exception.ContractCenterException;
import com.wwwarehouse.xdw.contractcenter.model.DdTrade;
import com.wwwarehouse.xdw.contractcenter.model.DdTradeAddFee;
import com.wwwarehouse.xdw.contractcenter.model.DdTradeItem;
import com.wwwarehouse.xdw.contractcenter.service.DdTradeService;
import com.wwwarehouse.xdw.datasync.model.AmAppkey;
import com.wwwarehouse.xdw.datasync.model.IcTradeItem;
import com.wwwarehouse.xdw.openapi.exception.ApiException;
import com.wwwarehouse.xdw.openapi.sdk.request.contract.ContractCreateRequest;
import com.wwwarehouse.xdw.openapi.sdk.request.contract.TradeCreateRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.contract.ContractCreateResponse;
import com.wwwarehouse.xdw.openapi.sdk.response.contract.TradeCreateResponse;
import com.wwwarehouse.xdw.openapi.web.service.ApiProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhigang.huang on  2018/5/14
 * 接口处理的service
 */
@Service("tradeCreateService")
public class TradeCreateServiceImpl implements ApiProcessService<TradeCreateRequest, TradeCreateResponse> {
	private static Logger log = LoggerFactory.getLogger(TradeCreateServiceImpl.class);

	@Resource
	private DdTradeService ddTradeService;

	@Override
	public TradeCreateResponse execute(AmAppkey amAppkey, TradeCreateRequest request) throws ApiException {
		DdTrade ddTrade = new DdTrade();
		BeanUtils.copyProperties(request, ddTrade);
		AbsResponse abs = null;
		try {
			abs = ddTradeService.tradeHandler(ddTrade);
		} catch (ContractCenterException cce) {
			//对已知的错误，记录,需做人工处理
			log.error("订单[" + request.getTradeUkid() + "]处理异常:" + cce.getMessage(), cce);
		} catch (Exception e) {
			log.error("订单[" + request.getTradeUkid() + "]处理异常:" + JSONObject.toJSON(request), e);
		}

		return new TradeCreateResponse();
	}
}
