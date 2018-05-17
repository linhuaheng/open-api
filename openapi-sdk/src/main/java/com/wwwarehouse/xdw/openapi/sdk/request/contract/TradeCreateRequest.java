package com.wwwarehouse.xdw.openapi.sdk.request.contract;

import com.wwwarehouse.xdw.openapi.sdk.internal.util.JsonUtil;
import com.wwwarehouse.xdw.openapi.sdk.request.AbstractRequest;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.contract.ContractCreateResponse;
import com.wwwarehouse.xdw.openapi.sdk.response.contract.TradeCreateResponse;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class TradeCreateRequest extends AbstractRequest
		implements WhRequest<TradeCreateResponse> {
	private Long tradeUkid;

	/**
	 * 交易编号
	 */
	private String tradeId;

	/**
	 * 平台ID
	 */
	private Long platformId;
	/**
	 * 店铺ID
	 */
	private Long shopId;

	public Long getTradeUkid() {
		return tradeUkid;
	}

	public void setTradeUkid(Long tradeUkid) {
		this.tradeUkid = tradeUkid;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Override
	public String getApiMethod() {
		return "trade.create";
	}

	@Override
	public String getAppJsonParams() throws IOException {
		Map<String, Object> pmap = new TreeMap();
		pmap.put("tradeUkid", this.tradeUkid);
		pmap.put("tradeId", this.tradeId);
		pmap.put("platformId", this.platformId);
		pmap.put("shopId", this.shopId);
		return JsonUtil.toJson(pmap);
	}

	@Override
	public Class<TradeCreateResponse> getResponseClass() {
		return TradeCreateResponse.class;
	}
}
