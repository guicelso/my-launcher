package com.unisoc.engineermode.core.exception;

/* JADX INFO: loaded from: classes4.dex */
public class OperationFailedException extends EmException {
    private ErrorCode code;

    public OperationFailedException() {
        this.code = ErrorCode.UNKNOWN;
    }

    public OperationFailedException(String msg) {
        super(msg);
        this.code = ErrorCode.UNKNOWN;
        this.code = ErrorCode.UNKNOWN;
    }

    public OperationFailedException(ErrorCode code) {
        this.code = ErrorCode.UNKNOWN;
        this.code = code;
    }

    public OperationFailedException(ErrorCode code, String msg) {
        super(msg);
        this.code = ErrorCode.UNKNOWN;
        this.code = code;
    }

    public ErrorCode getCode() {
        return this.code;
    }
}
