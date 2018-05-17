package com.wwwarehouse.xdw.openapi.sdk.request;

import com.wwwarehouse.xdw.openapi.sdk.FileItem;
import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

import java.util.Map;

public abstract interface WhUploadRequest<T extends AbstractResponse>
		extends WhRequest<T> {
	public abstract Map<String, FileItem> getFileParams();
}
