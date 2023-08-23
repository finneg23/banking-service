package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransactionDTO {
    private BigDecimal amount;
    private String toUser;
    private String fromUser;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
}

/*
{
"toUser" : "Jessie",
"amount" : "100"
}
 */