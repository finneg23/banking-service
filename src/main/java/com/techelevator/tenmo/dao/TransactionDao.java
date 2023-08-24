package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.TransactionDTO;

import java.util.List;

public interface TransactionDao {
    List<Transaction> allTransactions();
    List<TransactionDTO> allTransactionsByUsername(String username);
    TransactionDTO createTransaction(Transaction transaction);
    TransactionDTO getTransactionById(int transactionId);

}
