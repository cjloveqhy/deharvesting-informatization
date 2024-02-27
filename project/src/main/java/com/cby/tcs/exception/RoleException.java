package com.cby.tcs.exception;

import com.freedom.cloud.exception.BasicFormatException;

public class RoleException extends BasicFormatException {

    public RoleException() {
        super();
    }

    public RoleException(String message, Object... args) {
        super(message, args);
    }

    public RoleException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    public RoleException(Throwable cause) {
        super(cause);
    }

    protected RoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace, args);
    }
}
