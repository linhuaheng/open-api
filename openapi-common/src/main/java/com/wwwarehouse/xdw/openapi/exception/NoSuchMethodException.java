package com.wwwarehouse.xdw.openapi.exception;

import com.wwwarehouse.commons.exception.IscsErrorCode;

public class NoSuchMethodException extends ApiException {

    private static final long serialVersionUID = 39293813401L;

    public NoSuchMethodException() {
        super();
    }

    public NoSuchMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchMethodException(String message) {
        super(message);
        int errorCode = IscsErrorCode.MethodNotExist.getErrorCode();
        String errorMsg = IscsErrorCode.MethodNotExist.getErrorText();
        setErrCode(errorCode);
        setErrMsg(errorMsg + ":" + message);
    }

    public NoSuchMethodException(int errCode, String errMsg) {
        super(errCode, errMsg);
    }

    public NoSuchMethodException(IscsErrorCode apiErrorCode, String errorMsg) {
        super(apiErrorCode, errorMsg);
    }
}
