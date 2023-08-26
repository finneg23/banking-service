package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransactionDTO {
    private int transactionId;
    private BigDecimal amount;
    private String to;
    private String from;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public TransactionDTO(int transactionId, BigDecimal transferAmount, String from, String to, String status) {
        this.transactionId = transactionId;
        this.amount = transferAmount;
        this.from = from;
        this.to = to;
        this.status = status;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getTransferAmount() {
        return amount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.amount = transferAmount;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}

/*
{
"toUser" : "Jessie",
"amount" : "100"
}
 */