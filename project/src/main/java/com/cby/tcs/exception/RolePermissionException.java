package com.cby.tcs.exception;

import com.freedom.cloud.exception.BasicFormatException;

public class RolePermissionException extends BasicFormatException {

    public RolePermissionException() {
        super();
    }

    public RolePermissionException(String message, Object... args) {
        super(message, args);
    }

    public RolePermissionException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    public RolePermissionException(Throwable cause) {
        super(cause);
    }

    protected RolePermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace, args);
    }
}
