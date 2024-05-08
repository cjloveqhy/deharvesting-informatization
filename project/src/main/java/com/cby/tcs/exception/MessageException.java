package com.cby.tcs.exception;

import com.freedom.cloud.exception.BasicFormatException;

public class MessageException extends BasicFormatException {

    public MessageException() {
        super();
    }

    public MessageException(String message, Object... args) {
        super(message, args);
    }

    public MessageException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    protected MessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace, args);
    }
}
