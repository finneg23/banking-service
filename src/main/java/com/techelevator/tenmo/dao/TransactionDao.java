package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;

import java.util.List;

public interface TransactionDao {
    List<Transaction> allTransactions();
    List<Transaction> allTransactionsByUserId();

}
