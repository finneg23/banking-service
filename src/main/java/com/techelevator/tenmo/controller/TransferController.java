package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.TransactionDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public TransactionDTO createTransaction(@Valid @RequestBody Transaction transaction) {
        return transactionDao.createTransaction(transaction);
    }
}
