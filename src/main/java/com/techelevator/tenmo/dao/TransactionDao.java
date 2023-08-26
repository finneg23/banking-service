package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CreateTransactionDTO;
import com.techelevator.tenmo.model.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {

    List<TransactionDTO> allTransactionsByUsername(String username);

    TransactionDTO create(CreateTransactionDTO transaction, Account account, boolean isSending);

    TransactionDTO updateTransactionStatus(TransactionDTO transaction, String status, Account myAccount);

    void updateAccounts(String receiver, String sender, BigDecimal amount);

    TransactionDTO getTransactionById(int transactionId);
}
