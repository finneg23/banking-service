package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransactionDao;
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

    public TransferController(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @RequestMapping(path = "/transactions", method = RequestMethod.GET)
    public List<TransactionDTO> getAllTransactions(Principal principal) {
    return transactionDao.allTransactionsByUsername(principal.getName());
    }

    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public TransactionDTO createTransaction(@Valid @RequestBody CreateTransactionDTO transaction, Principal principal) {
        return transactionDao.create(transaction,principal.getName());
    }

    @RequestMapping(path ="/transactions/{id}", method = RequestMethod.GET)
    public  TransactionDTO getTransactionById(@PathVariable int id ){
        return transactionDao.getTransactionById(id);
    }
}
