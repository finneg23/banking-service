package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.exception.TransactionNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.CreateTransactionDTO;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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

    @GetMapping(path = "/transactions")
    public List<TransactionDTO> getAllTransactions(Principal principal) {
    return transactionDao.allTransactionsByUsername(principal.getName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/transactions")
    public TransactionDTO createTransaction(@Valid @RequestBody CreateTransactionDTO transaction, Principal principal) {
        Account myAccount = accountDao.getAccount(principal.getName());
        return transactionDao.create(transaction,myAccount);
    }

    @GetMapping(path ="/transactions/{id}")
    public  TransactionDTO getTransactionById(@PathVariable int id, Principal principal){
            TransactionDTO transactionById = transactionDao.getTransactionById(id);
        if (transactionById.getTo().equals(principal.getName()) || transactionById.getFrom().equals(principal.getName())) {
            return transactionById;
        }
        throw new TransactionNotFoundException("You are not a part of this transaction.");
    }

    @ExceptionHandler
    public void handleTransactionNotFoundException(TransactionNotFoundException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
