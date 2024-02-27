package com.cby.tcs.exception;

import com.freedom.cloud.exception.BasicFormatException;

public class UserException extends BasicFormatException {

    public UserException() {
        super();
    }

    public UserException(String message, Object... args) {
        super(message, args);
    }

    public UserException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    protected UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace, args);
    }
}
