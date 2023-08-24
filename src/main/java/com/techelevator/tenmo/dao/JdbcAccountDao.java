package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
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
        String sql =    "SELECT tenmo_user.username, balance " +
                        "FROM account " +
                        "JOIN tenmo_user ON tenmo_user.user_id = account.user_idgit commit;";
                SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.next()) {
           account= mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public Account getAccountById(int accountId) throws AccountNotFoundException {
        String sql =    "SELECT account_id, balance " +
                "FROM account " +
                "WHERE account_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            if (results.next()) {
                return mapRowToAccount(results);
            }
        throw new AccountNotFoundException("Account " + accountId + " was not found.");
    }

    @Override
    public Account findAccountsByUsername(String username) {
       Account byUsername= null;
        String sql =    "SELECT tenmo_user.username, account_id, balance " +
                "FROM account " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                "WHERE tenmo_user.username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()) {
            byUsername =mapRowToAccount(results);
        }
        return byUsername;
    }

    @Override
    public Account createAccount(Account account) {
        Account newAccount = null;
        String sql = "INSERT INTO account (user_id, balance) " +
                "VALUES (?, 1000) RETURNING account_id;";
        Integer newAccountId;
        try {
            newAccountId = jdbcTemplate.queryForObject(sql, Integer.class, account.getUserId());
            newAccount = getAccountById(newAccountId);
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to database.");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("The integrity of the data will be compromised.");
        } catch(NullPointerException e) {
            throw new DaoException("The account id was found to be null.");
        } catch(AccountNotFoundException e) {
            throw new DaoException("Account was not created properly.");
        }
        return newAccount;
    }

    @Override
    public Account updateBalance(int accountId) {
        Account accountToUpdate = null;
        int rowsAffected = 0;
        String sql1 =    "UPDATE account SET balance = balance + ? " +
                        "FROM account JOIN transaction ON account.user_id = transaction.to_user_id " +
                        "WHERE account_id = ? AND from_user_id != to_user_id;";

        String sql2 =    "UPDATE account SET balance = balance - ? " +
                        "FROM account JOIN transaction ON account.user_id = transaction.from_user_id " +
                        "WHERE account_id = ? AND amount < balance AND from_user_id != to_user_id;";
        return null;
        //UPDATE account SET balance = balance + transaction.amount where transaction.transaction_id = ?
        // AND status = 'APPROVED';
    }

    private Account mapRowToAccount(SqlRowSet results) {
        Account account = null;
        account.setAccountId(results.getInt("account_id"));
        account.setUsername(results.getString("username"));
        account.setBalance(results.getBigDecimal("balance"));
        return account;
    }
}
