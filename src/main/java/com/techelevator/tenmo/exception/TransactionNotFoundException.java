package com.techelevator.tenmo.exception;

public class TransactionNotFoundException extends DaoException{
    public TransactionNotFoundException() {
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }

    public TransactionNotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}
