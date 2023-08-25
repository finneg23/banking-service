package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private int transactionId;
    private String fromUsername;
    private String toUsername;
    private String status;
    private BigDecimal amount;
    private LocalDate timestamp;

    public Transaction(int transactionId, String fromUsername, String toUsername, String status, BigDecimal amount, LocalDate timestamp) {
        this.transactionId = transactionId;
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.status = status;
        this.amount = amount;
        this.timestamp = timestamp;
    }


    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String getFromUserId) {
        this.fromUsername = fromUsername;
    }

    public String getToUsername() {
        return getToUsername();
    }

    public void setToUserId(String getToUsername) {
        this.toUsername = toUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + transactionId +
                ", from='" + fromUsername + '\'' +
                ", to='" + toUsername + '\'';
    }
}
