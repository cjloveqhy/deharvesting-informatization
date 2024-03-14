package com.cby.tcs.exception;

import com.freedom.cloud.exception.BasicFormatException;

public class FarmDataTerminalException extends BasicFormatException {

    public FarmDataTerminalException() {
        super();
    }

    public FarmDataTerminalException(String message, Object... args) {
        super(message, args);
    }

    public FarmDataTerminalException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    public FarmDataTerminalException(Throwable cause) {
        super(cause);
    }

    protected FarmDataTerminalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace, args);
    }
}
