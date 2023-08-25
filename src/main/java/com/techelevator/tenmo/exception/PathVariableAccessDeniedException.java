package com.techelevator.tenmo.exception;

public class PathVariableAccessDeniedException extends Exception{
    public PathVariableAccessDeniedException(String message) {
        super(message);
    }

    public PathVariableAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PathVariableAccessDeniedException(Throwable cause) {
        super(cause);
    }

    public PathVariableAccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
