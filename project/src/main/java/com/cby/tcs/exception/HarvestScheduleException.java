package com.cby.tcs.exception;

import com.freedom.cloud.exception.BasicFormatException;

public class HarvestScheduleException extends BasicFormatException {

    public HarvestScheduleException() {
        super();
    }

    public HarvestScheduleException(String message, Object... args) {
        super(message, args);
    }

    public HarvestScheduleException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    public HarvestScheduleException(Throwable cause) {
        super(cause);
    }

    protected HarvestScheduleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace, args);
    }
}
