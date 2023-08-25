package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.CreateTransactionDTO;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.TransactionDTO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public interface TransactionDao {
    List<Transaction> allTransactions();
    List<TransactionDTO> allTransactionsByUsername(String username);
//    TransactionDTO createTransaction(Transaction transaction);
    public TransactionDTO create(CreateTransactionDTO transaction, String username);

    TransactionDTO getTransactionById(int transactionId);

}
