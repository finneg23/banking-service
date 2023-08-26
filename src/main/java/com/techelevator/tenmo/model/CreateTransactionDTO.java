package com.techelevator.tenmo.model;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class CreateTransactionDTO {
    @Positive(message = "Your transfer/request must be a positive number.")
    private BigDecimal transferAmount;
    private String otherPartyUsername;

    public CreateTransactionDTO(BigDecimal transferAmount, String otherPartyUsername) {
        this.transferAmount = transferAmount;
        this.otherPartyUsername = otherPartyUsername;
    }


    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getOtherPartyUsername() {
        return otherPartyUsername;
    }

    public void setTo(String to) {
        this.otherPartyUsername = to;
    }
}
