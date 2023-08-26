package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransactionDTO;

import java.util.List;

public interface TransactionDao {

    List<TransactionDTO> allTransactionsByUsername(String username);

    TransactionDTO create(TransactionDTO transaction, Account account);

    TransactionDTO getTransactionById(int transactionId);

}
