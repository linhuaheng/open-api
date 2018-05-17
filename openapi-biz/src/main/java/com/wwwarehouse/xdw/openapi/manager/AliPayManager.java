package com.wwwarehouse.xdw.openapi.manager;

import com.wwwarehouse.commons.utils.AbsResponse;
import com.wwwarehouse.xdw.datasync.model.AmAppSubscription;
import com.wwwarehouse.xdw.datasync.model.PayParamsDTO;

/**
 * Created by xiuli.yang on 2017/6/12.
 */
public interface AliPayManager {

    AbsResponse aliPayValidate(AmAppSubscription appSuber, PayParamsDTO payParamsDTO) throws Exception;

    String aliAnalyzeData(String request) throws Exception;
}
