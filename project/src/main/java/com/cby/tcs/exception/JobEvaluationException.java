package com.cby.tcs.exception;

import com.freedom.cloud.exception.BasicFormatException;

public class JobEvaluationException extends BasicFormatException {
    public JobEvaluationException(){
        super();
    }
    public JobEvaluationException(String message, Object... args) {
        super(message, args);
    }

    public JobEvaluationException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    public JobEvaluationException(Throwable cause) {
        super(cause);
    }

    protected JobEvaluationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace, args);
    }
}
