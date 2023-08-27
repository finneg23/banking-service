package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.PrimaryAccountDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcAccountDao implements AccountDao{
    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccount(String username) {
        Account account = null;
        String sql =    "SELECT tenmo_user.username, account.balance, account.account_id " +
                        "FROM account " +
                        "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                        "WHERE tenmo_user.username = ?;";
                SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()) {
           account= mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public PrimaryAccountDTO findAccountByUsername(String username) {
        PrimaryAccountDTO foundAccount = new PrimaryAccountDTO();
        String sql =    "SELECT tenmo_user.username, balance " +
                "FROM account " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                "WHERE tenmo_user.username ILIKE ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()) {
            foundAccount.setUsername(username);
            foundAccount.setBalance(results.getBigDecimal("balance"));
        }
        return foundAccount;
    }



    private Account mapRowToAccount(SqlRowSet results) {
        Account account = new Account();
        account.setAccountId(results.getInt("account_id"));
        account.setUsername(results.getString("username"));
        account.setBalance(results.getBigDecimal("balance"));
        return account;
    }

//    @Override
//    public BigDecimal getBalanceByAccountId(int accountId) throws AccountNotFoundException {
//        String sql =    "SELECT balance " +
//                "FROM account " +
//                "WHERE account_id = ?;";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
//        if (results.next()) {
//            return results.getBigDecimal("balance");
//        }
//        throw new AccountNotFoundException("Account " + accountId + " was not found.");
//    }

//    @Override
//    public Account getAccountById(int accountId) throws AccountNotFoundException {
//        String sql =    "SELECT account_id, balance " +
//                "FROM account " +
//                "WHERE account_id = ?;";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
//        if (results.next()) {
//            return mapRowToAccount(results);
//        }
//        throw new AccountNotFoundException("Account " + accountId + " was not found.");
//    }

//    @Override
//    public Account createAccount(String loggedInUser) {
//        Account newAccount = null;
//        String sql = "INSERT INTO account (user_id, balance) " +
//                "VALUES ((SELECT user_id FROM tenmo_user WHERE username ILIKE ?), 1000) RETURNING account_id;";
//        Integer newAccountId;
//        try {
//            newAccountId = jdbcTemplate.queryForObject(sql, Integer.class, loggedInUser);
//            newAccount = getAccountById(newAccountId);
//        } catch(CannotGetJdbcConnectionException e) {
//            throw new DaoException("Error connecting to database.");
//        } catch(DataIntegrityViolationException e) {
//            throw new DaoException("The integrity of the data will be compromised.");
//        } catch(NullPointerException e) {
//            throw new DaoException("The account id was found to be null.");
//        } catch(AccountNotFoundException e) {
//            throw new DaoException("Account was not created properly.");
//        }
//        return newAccount;
//    }
}
