package com.cby.tcs.exception;

import com.freedom.cloud.exception.BasicFormatException;

public class UserRoleException extends BasicFormatException {

    public UserRoleException() {
        super();
    }

    public UserRoleException(String message, Object... args) {
        super(message, args);
    }

    public UserRoleException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    public UserRoleException(Throwable cause) {
        super(cause);
    }

    protected UserRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace, args);
    }
}
