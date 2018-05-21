package com.wwwarehouse.xdw.datasync.web.controller;

import com.wwwarehouse.commons.json.JsonUtils;
import com.wwwarehouse.xdw.openapi.sdk.DefaultWhClient;
import com.wwwarehouse.xdw.openapi.sdk.WhClient;
import com.wwwarehouse.xdw.openapi.sdk.WhException;
import com.wwwarehouse.xdw.openapi.sdk.request.WhRequest;
import com.wwwarehouse.xdw.openapi.sdk.request.contract.TradeCreateRequest;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;
import com.wwwarehouse.xdw.openapi.sdk.response.contract.TradeCreateResponse;
import org.junit.Test;

/**
 * @author chengwei
 * @date 2018/5/15 10:53
 */
public class OpenApiTestDemo {

    private WhClient whClient = null;

    @Test
    public void apiTest() throws Exception{
        String apiUrl = "http://192.168.72.155:8080/openapi/do";
        String accessToken = "";
        String appKey = "F5FA8C15F2EAE0";
        String appSecret = "b06568639e20467";
        whClient = new DefaultWhClient(apiUrl, accessToken,
                appKey, appSecret, 30000, 25000);

//        ContractQueryRequest request = new ContractQueryRequest();
//
//        request.setObuId(12L);
//        request.setStartCreateTime(DateUtil.addDays(new Date(), -10));
//        request.setEndCreateTime(new Date());
//        ContractQueryResponse response = this.execute(request);
//        System.out.println(JsonUtils.toJson(response));

        TradeCreateRequest request = new TradeCreateRequest();
        request.setTradeUkid(1234321L);
        request.setTradeId("123");
        request.setPlatformId(10L);
        request.setShopId(2007L);

        TradeCreateResponse response = this.execute(request);
        System.out.println(JsonUtils.toJson(response));
    }

    public static void main(String[] args){
        System.out.println("GODS");
    }

    <T extends AbstractResponse> T execute(WhRequest<T> request)
            throws WhException {
        T response = this.whClient.execute(request);
        return response;
    }

}
