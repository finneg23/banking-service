package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransactionDTO {
    private int transactionId;
    private BigDecimal amount;
    private String to;
    private String from;

    public TransactionDTO(int transactionId, BigDecimal transferAmount, String from, String to) {
        this.transactionId = transactionId;
        this.amount = transferAmount;
        this.from = from;
        this.to = to;
    }

    public TransactionDTO() {
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