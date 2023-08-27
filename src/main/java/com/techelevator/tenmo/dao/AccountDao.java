package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.PrimaryAccountDTO;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    Account getAccount(String username);
    PrimaryAccountDTO findAccountByUsername(String username);

    //      Account getAccountById(int accountId) throws AccountNotFoundException;
    //      BigDecimal getBalanceByAccountId(int accountId) throws AccountNotFoundException;
    //      Account createAccount(String username);
}

