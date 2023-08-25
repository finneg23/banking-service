package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.PrimaryAccountDTO;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    Account getAccount(String username);
    Account getAccountById(int accountId) throws AccountNotFoundException;
    PrimaryAccountDTO findAccountByUsername(String username);
    Account createAccount(Account account);
    Account updateBalance(int accountId);
    BigDecimal getBalancebyAccountId(int accountId) throws AccountNotFoundException;

}
