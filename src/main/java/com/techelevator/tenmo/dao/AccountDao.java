package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountDao {
    List<Account> getAllAccounts();
    Account getAccountById(int accountId) throws AccountNotFoundException;
    List<Account> findAccountsByUsername(String username);
    Account createAccount(Account account);
    Account updateBalance(int accountId);

}
