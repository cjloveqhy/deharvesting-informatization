package com.cby.tcs.exception;

import com.freedom.cloud.exception.BasicFormatException;

public class PermissionException extends BasicFormatException {

    public PermissionException() {
        super();
    }

    public PermissionException(String message, Object... args) {
        super(message, args);
    }

    public PermissionException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    public PermissionException(Throwable cause) {
        super(cause);
    }

    protected PermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace, args);
    }
}
