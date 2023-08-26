package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransactionDTO {
    private int transferId;
    private BigDecimal transferAmount;
    private String to;
    private String from;

    public TransactionDTO(int transferId, BigDecimal transferAmount, String from, String to) {
        this.transferId = transferId;
        this.transferAmount = transferAmount;
        this.from = from;
        this.to = to;
    }

    public TransactionDTO() {
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
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