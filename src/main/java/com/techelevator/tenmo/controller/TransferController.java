package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CreateTransactionDTO;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.TransactionDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private TransactionDao transactionDao;
    private AccountDao accountDao;

    public TransferController(TransactionDao transactionDao, AccountDao accountDao) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/transactions", method = RequestMethod.GET)
    public List<TransactionDTO> getAllTransactions(Principal principal) {
    return transactionDao.allTransactionsByUsername(principal.getName());
    }

    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public TransactionDTO createTransaction(@Valid @RequestBody CreateTransactionDTO transaction, Principal principal) {
        // need to get the account of current user
        Account myAccount = accountDao.getAccount(principal.getName());

        // using CreateTransactionDTO for getting --receiver username-- and --amountTransfer--
        // using Account for getting --sender username-- of sender and --current balance--
        return transactionDao.create(transaction,myAccount);
    }

    @RequestMapping(path ="/transactions/{id}", method = RequestMethod.GET)
    public  TransactionDTO getTransactionById(@PathVariable int id ){
        return transactionDao.getTransactionById(id);
    }
}
