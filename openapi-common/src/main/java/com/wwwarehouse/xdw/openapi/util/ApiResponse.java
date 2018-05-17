package com.wwwarehouse.xdw.openapi.util;

import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

/**
 * Created by zhigang.huang on 2018/5/14.
 */
public class ApiResponse extends AbstractResponse{
	public ApiResponse(String code, String msg){
		super();
		super.setCode(code);
		super.setMsg(msg);
	}
}
