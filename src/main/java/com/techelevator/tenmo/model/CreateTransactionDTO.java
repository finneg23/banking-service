package com.techelevator.tenmo.model;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class CreateTransactionDTO {
    @Positive(message = "the amount much be greather than 0")
    private BigDecimal transferAmount;
    private String to;

    public CreateTransactionDTO(BigDecimal transferAmount, String to) {
        this.transferAmount = transferAmount;
        this.to = to;
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
}
