package com.wwwarehouse.xdw.openapi.exception;

import com.wwwarehouse.commons.exception.IscsErrorCode;
import com.wwwarehouse.commons.exception.IscsException;
import com.wwwarehouse.commons.utils.StringUtils;

public class ApiException extends IscsException {
    private static final long serialVersionUID = -238091758285157331L;
    private int errCode;
    private String errMsg;

    public ApiException() {
        super();
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.errMsg = message;
    }

    public ApiException(String message) {
        super(message);
        this.errMsg = message;
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(int errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ApiException(IscsErrorCode apiErrorCode, String errorMsg) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(apiErrorCode.getErrorText());
        if (StringUtils.isNotEmpty(errorMsg)) {
            strBuilder.append(":").append(errorMsg);
        }
        this.errCode = apiErrorCode.getErrorCode();
        this.errMsg = strBuilder.toString();
    }

    public int getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
