package com.cby.tcs.exception;

import com.freedom.cloud.exception.BasicFormatException;

public class FarmCottonPickerException extends BasicFormatException {

    public FarmCottonPickerException() {
        super();
    }

    public FarmCottonPickerException(String message, Object... args) {
        super(message, args);
    }

    public FarmCottonPickerException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    public FarmCottonPickerException(Throwable cause) {
        super(cause);
    }

    protected FarmCottonPickerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace, args);
    }
}
